package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.stream.Stream;

public class RecruitsFindCoverFromTargetGoal<T extends AbstractRecruitEntity> extends Goal {
    private static final int REVALIDATE_INTERVAL = 60;
    private static final int PROJECTILE_SCAN_COOLDOWN = 10;
    private static final int CAN_USE_COOLDOWN = 5;
    private static final int BASE_RING_SAMPLES = 6;
    private static final int MAX_RINGS = 8;
    private static final int FALLBACK_SAMPLES = 12;
    private static final int MAX_Y_SEARCH = 2;
    private static final double MAX_CANDIDATE_BUFFER = MAX_RINGS * 24 + FALLBACK_SAMPLES;

    private final T mob;
    private final double speed;
    private final int searchRadius;
    private final RandomSource  random;

    private final int[] candidateXYZ  = new int[(int) MAX_CANDIDATE_BUFFER * 3];
    private final double[] candidateDsq = new double[(int) MAX_CANDIDATE_BUFFER];
    private final Vec3[] losVecScratch = new Vec3[2];

    private LivingEntity attacker;
    private int coverX;
    private int coverY;
    private int coverZ;
    private boolean hasCoverPos;

    private int revalidateTicks;
    private int projectileScanTicks;
    private int canUseCooldown;

    public RecruitsFindCoverFromTargetGoal(T mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = RecruitsWariumConfig.RECRUIT_COVER_RADIUS.get();
        this.random = mob.getRandom();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!RecruitsWariumConfig.SHOULD_RECRUITS_RUN_TO_COVER.get()) return false;
        if(this.mob.getVehicle() == null) return false;
        if (this.mob.getFollowState() != 0 || this.mob.getShouldFollow() || this.mob.getShouldMovePos())  return false;
        if(!RecruitsWariumUtils.isWariumGun(this.mob.getMainHandItem().getItem())) return false;

        --canUseCooldown;

        if (canUseCooldown > 0) return false;
        canUseCooldown = CAN_USE_COOLDOWN;

        attacker = mob.getTarget();

        if (attacker == null) {
            if (!tryFindAttackerFromProjectiles()) return false;
        }

        if (attacker == null || !attacker.isAlive()) return false;

        if(!shouldTakeCoverFromTarget(attacker)) return false;

        if (!RecruitsWariumUtils.targetHasLineOfSight(this.mob, attacker)) return false;

        if (!findCover()) return false;

        PathNavigation nav  = mob.getNavigation();
        BlockPos dest = new BlockPos(coverX, coverY, coverZ);
        Path path = nav.createPath(dest, 1);
        if (path == null) {
            hasCoverPos = false;
            return false;
        }

        nav.moveTo(path, speed);
        revalidateTicks = REVALIDATE_INTERVAL;
        return true;
    }

    private boolean shouldTakeCoverFromTarget(LivingEntity entity) {
        return RecruitsWariumUtils.isWariumGun(entity.getMainHandItem().getItem()) || entity.getVehicle() != null;
    }

    @Override
    public boolean requiresUpdateEveryTick() { return true; }

    @Override
    public boolean canContinueToUse() {
        if (!hasCoverPos) return false;
        if (attacker == null || !attacker.isAlive()) return false;

        if (!RecruitsWariumUtils.targetHasLineOfSight(this.mob, attacker)) return false;

        return !mob.getNavigation().isDone();
    }



    @Override
    public void start() {
    }

    @Override
    public void tick() {
        if (attacker == null || !hasCoverPos) return;

        if (revalidateTicks <= 0) {
            revalidateTicks = REVALIDATE_INTERVAL;

            if (findCover()) {
                PathNavigation nav  = mob.getNavigation();
                Path path = nav.createPath(new BlockPos(coverX, coverY, coverZ), 1);

                if (path != null) {
                    nav.moveTo(path, speed);
                }
            }
        } else {
            --revalidateTicks;
        }
    }

    @Override
    public void stop() {
        hasCoverPos = false;
        attacker = null;
        mob.getNavigation().stop();
    }

    private boolean tryFindAttackerFromProjectiles() {
        if (projectileScanTicks > 0) {
            --projectileScanTicks;

            return attacker != null;
        } else {
            projectileScanTicks = PROJECTILE_SCAN_COOLDOWN;

            double scanRadius = RecruitsWariumConfig.BULLET_SUPPRESSION_RADIUS.get() * 2.0;
            AABB aabb = mob.getBoundingBox().inflate(scanRadius);

            List<Projectile> nearby = mob.level().getEntitiesOfClass(Projectile.class, aabb);
            if (nearby.isEmpty()) return false;

            LivingEntity bestOwner = null;
            double       bestDsq   = Double.MAX_VALUE;

            for (int i = 0, n = nearby.size(); i < n; i++) {
                Entity raw = nearby.get(i).getOwner();
                if (!(raw instanceof LivingEntity owner)) continue;
                if (!mob.canAttack(owner))                 continue;
                if (!mob.shouldAttack(owner))              continue;
                if (!RecruitsWariumUtils.isWariumGun(owner.getMainHandItem().getItem())) continue;

                double dsq = mob.distanceToSqr(nearby.get(i));
                if (dsq < bestDsq) {
                    bestDsq   = dsq;
                    bestOwner = owner;
                }
            }

            if (bestOwner == null) return false;

            if (mob.getTarget() == null && mob.canAttack(bestOwner)) {
                mob.setTarget(bestOwner);
            }
            attacker = bestOwner;
            return true;
        }
    }

    private boolean findCover() {
        Level level = mob.level();
        BlockPos pos = this.mob.blockPosition();
        Vec3 eyePos  = attacker.getEyePosition();

        int candidateCount = 0;
        int ringLimit = Math.min(MAX_RINGS, searchRadius);

        for (int ring = 1; ring <= ringLimit; ring++) {
            int samples = BASE_RING_SAMPLES * ring;

            for (int i = 0; i < samples; i++) {
                double angle = (2.0 * Math.PI * i) / samples;
                int dx = (int) Math.round(Math.cos(angle) * ring);
                int dz = (int) Math.round(Math.sin(angle) * ring);

                int sy = findStandableY(level, pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                if (sy == Integer.MIN_VALUE) continue;

                Vec3 sampleEye = new Vec3(pos.getX() + dx + 0.5, sy + 1.0, pos.getZ() + dz + 0.5);
                if (vectorLineOfSight(eyePos, sampleEye)) continue;

                int slot = candidateCount * 3;
                candidateXYZ[slot] = pos.getX() + dx;
                candidateXYZ[slot + 1] = sy;
                candidateXYZ[slot + 2] = pos.getZ() + dz;

                double cdx = (pos.getX() + dx + 0.5) - mob.getX();
                double cdy = sy - mob.getY();
                double cdz = (pos.getZ() + dz + 0.5) - mob.getZ();
                candidateDsq[candidateCount] = cdx * cdx + cdy * cdy + cdz * cdz;

                candidateCount++;
            }

            if (candidateCount > 0) {
                return selectNearest(candidateCount);
            }
        }

        for (int i = 0; i < FALLBACK_SAMPLES; i++) {
            int rx = pos.getX() + random.nextInt(searchRadius * 2 + 1) - searchRadius;
            int rz = pos.getZ() + random.nextInt(searchRadius * 2 + 1) - searchRadius;

            int sy = findStandableY(level, rx, pos.getY(), rz);
            if (sy == Integer.MIN_VALUE) continue;

            Vec3 sampleEye = new Vec3(rx + 0.5, sy + 1.0, rz + 0.5);
            if (!vectorLineOfSight(eyePos, sampleEye)) {
                coverX = rx;
                coverY = sy;
                coverZ = rz;
                hasCoverPos = true;
                return true;
            }
        }

        hasCoverPos = false;
        return false;
    }

    private boolean selectNearest(int count) {
        int bestIdx = 0;
        double bestDsq = candidateDsq[0];

        for (int i = 1; i < count; i++) {
            if (candidateDsq[i] < bestDsq) {
                bestDsq = candidateDsq[i];
                bestIdx = i;
            }
        }

        int slot = bestIdx * 3;
        coverX = candidateXYZ[slot];
        coverY = candidateXYZ[slot + 1];
        coverZ = candidateXYZ[slot + 2];
        hasCoverPos = true;
        return true;
    }

    private int findStandableY(Level level, int x, int baseY, int z) {
        for (int dy = -MAX_Y_SEARCH; dy <= MAX_Y_SEARCH; dy++) {
            int y = baseY + dy;

            BlockPos pos = new BlockPos(x, y, z);
            BlockPos posAbove = pos.above();
            BlockPos posBelow = pos.below();

            BlockState floorState = level.getBlockState(posBelow);

            if (!level.isEmptyBlock(pos) || !floorState.isCollisionShapeFullBlock(level, posBelow) || !level.isEmptyBlock(posAbove)) continue;

            return y;
        }

        return Integer.MIN_VALUE;
    }

    private boolean vectorLineOfSight(Vec3 from, Vec3 to) {
        ClipContext ctx = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
        return this.mob.level().clip(ctx).getType() == HitResult.Type.MISS;
    }
}

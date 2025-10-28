package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.stream.Stream;

public class RecruitsFindCoverFromTargetGoal<T extends AbstractRecruitEntity> extends Goal {

    private final T mob;
    private LivingEntity attacker;
    private final double speed;
    private final int searchRadius;
    private final RandomSource random;

    private BlockPos coverPos;
    private Path coverPath;
    private int revalidateTicks = 0;
    private static final int REVALIDATE_INTERVAL = 60; // re-issue path every N ticks
    private static final int RING_SAMPLES = 16; // samples per ring
    private static final int MAX_RINGS = 8; // how many rings to check (increase coverage)
    private static final int MIN_DISTANCE_TO_COVER_SQ = 2; // 2 blocks^2

    public RecruitsFindCoverFromTargetGoal(T mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = RecruitsWariumConfig.RECRUIT_COVER_RADIUS.get();
        this.random = mob.getRandom();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if(!RecruitsWariumConfig.SHOULD_RECRUITS_RUN_TO_COVER.get())
            return false;

        boolean state = this.mob.getFollowState() == 0;
        boolean move = !this.mob.getShouldMovePos();
        boolean follow = !this.mob.getShouldFollow();

        if(state && move && follow) {
            this.attacker = this.mob.getTarget();

            if(this.attacker == null) {
                List<AbstractArrow> entities = this.mob.level().getEntitiesOfClass(AbstractArrow.class, this.mob.getBoundingBox().inflate((Integer) RecruitsWariumConfig.BULLET_SUPPRESSION_RADIUS.get() * 1.5));

                if(!entities.isEmpty()) {
                    Stream<AbstractArrow> abstractArrowStream = entities.stream().filter((e) -> e.inGroundTime < 20 && e.getOwner() instanceof LivingEntity entity && this.mob.canAttack(entity) && this.mob.shouldAttack(entity));

                    entities.sort(Comparator.comparingDouble(this.mob::distanceToSqr));

                    Optional<AbstractArrow> optionalAbstractArrow = entities.stream().findAny();

                    if(optionalAbstractArrow.isPresent()) {
                        Entity owner = optionalAbstractArrow.get().getOwner();

                        if(owner instanceof LivingEntity lvOwner) {
                            this.attacker = lvOwner;
                        }
                    }
                }
            }

            if (this.attacker == null || !this.attacker.isAlive()) return false;

            if(this.attacker instanceof Mob mobAttacker) {
                if(!mobAttacker.getSensing().hasLineOfSight(this.mob)) return false;
            } else {
                if (!canBeSeenBy(attacker, mob)) return false;
            }

            this.coverPos = findCover();
            if (this.coverPos == null) return false;

            PathNavigation nav = this.mob.getNavigation();
            this.coverPath = nav.createPath(coverPos, 1);
            if (this.coverPath == null) return false;

            nav.moveTo(coverPath, speed);
            this.revalidateTicks = REVALIDATE_INTERVAL;
            return true;
        }

        return false;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.attacker == null || !this.attacker.isAlive()) return false;
        if (this.coverPos == null) return false;

        // Stop if attacker no longer sees the mob
        if (!canBeSeenBy(attacker, mob)) return false;

        // Stop if we are sufficiently close to cover
        double distSq = this.mob.distanceToSqr(Vec3.atCenterOf(this.coverPos));
        if (distSq <= MIN_DISTANCE_TO_COVER_SQ) return false;

        // Otherwise, continue as long as the nav still has work or our path is valid
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        if (this.coverPos != null) {
            PathNavigation nav = this.mob.getNavigation();
            this.coverPath = nav.createPath(coverPos, 1);
            if (this.coverPath != null) nav.moveTo(coverPath, speed);
            this.revalidateTicks = REVALIDATE_INTERVAL;
        }
    }

    @Override
    public void tick() {
        if (this.attacker == null) return;

        if (this.revalidateTicks <= 0) {
            this.coverPos = findCover();

            if(this.coverPos != null) {
                PathNavigation nav = this.mob.getNavigation();
                this.coverPath = nav.createPath(coverPos, 1);
                if (this.coverPath != null) nav.moveTo(coverPath, speed);
                this.revalidateTicks = REVALIDATE_INTERVAL;
            }

        }

        --this.revalidateTicks;
    }

    @Override
    public void stop() {
        this.coverPos = null;
        this.coverPath = null;
        this.mob.getNavigation().stop();
        this.attacker = null;
    }

    // ---------------------
    // COVER SEARCH
    // ---------------------
    private BlockPos findCover() {
        Level level = mob.level();
        BlockPos mobPos = this.mob.blockPosition();
        Vec3 attackerEye = attacker.getEyePosition();
        BlockPos best = null;
        double bestDistSq = Double.MAX_VALUE;

        // Search expanding rings around mob (deterministic and directional)
        for (int ring = 1; ring <= Math.min(MAX_RINGS, searchRadius); ring++) {
            int ringRadius = ring; // ring radius in blocks
            int samples = RING_SAMPLES;

            for (int i = 0; i < samples; i++) {
                double angle = 2.0 * Math.PI * (double)i / (double)samples;
                double dx = Math.round(Math.cos(angle) * ringRadius);
                double dz = Math.round(Math.sin(angle) * ringRadius);
                BlockPos sample = mobPos.offset((int)dx, 0, (int)dz);

                // Scan vertically to find a standable spot (top-most non-solid space with solid under)
                BlockPos standing = findStandable(level, sample);
                if (standing == null) continue;

                // Candidate center point to check LOS to
                Vec3 sampleCenter = Vec3.atCenterOf(standing);

                // If attacker cannot see this candidate (i.e., it's blocked) -> candidate cover
                // Note: we check LOS from attacker's eye to a point slightly above ground (center)
                if (!hasLineOfSight(level, attackerEye, sampleCenter)) {
                    // Check pathability: see if the mob's navigation can reach it
                    PathNavigation nav = mob.getNavigation();
                    Path path = nav.createPath(standing, 1);
                    if (path == null) continue; // unreachable

                    // prefer nearer positions
                    double dsq = mob.distanceToSqr(sampleCenter);
                    if (dsq < bestDistSq) {
                        bestDistSq = dsq;
                        best = standing;
                    }
                }
            }

            // If we found any cover on this ring, return nearest one
            if (best != null) return best;
        }

        // fallback: try a few random nearby samples as a last resort
        for (int i = 0; i < 16 && best == null; i++) {
            int rx = mobPos.getX() + random.nextInt(searchRadius * 2 + 1) - searchRadius;
            int rz = mobPos.getZ() + random.nextInt(searchRadius * 2 + 1) - searchRadius;
            BlockPos sample = new BlockPos(rx, mobPos.getY(), rz);
            BlockPos standing = findStandable(level, sample);
            if (standing == null) continue;
            Vec3 sampleCenter = Vec3.atCenterOf(standing);
            if (!hasLineOfSight(level, attackerEye, sampleCenter)) {
                PathNavigation nav = mob.getNavigation();
                Path path = nav.createPath(standing, 0);
                if (path != null) return standing;
            }
        }

        return null;
    }

    /**
     * Find a standable BlockPos near the given sample pos.
     * Returns the BlockPos of the air-space where the mob should stand (y), or null if none.
     */
    private BlockPos findStandable(Level level, BlockPos sample) {
        // We look from mob's Y-2 .. Y+2 to allow some vertical variance
        int baseY = this.mob.blockPosition().getY();
        for (int dy = -2; dy <= 2; dy++) {
            BlockPos pos = new BlockPos(sample.getX(), baseY + dy, sample.getZ());
            // require the block at pos to be air (space for body) and block below to be solid ground
            if (level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above()) && level.getBlockState(pos.below()).isCollisionShapeFullBlock(level, pos.below())) {
                return pos;
            }
        }
        return null;
    }

    // ---------------------
    // LINE-OF-SIGHT HELPERS
    // ---------------------
    private static boolean canBeSeenBy(LivingEntity viewer, Entity target) {
        Level level = viewer.level();
        Vec3 from = viewer.getEyePosition();
        Vec3 to = target.getEyePosition();
        return hasLineOfSight(level, from, to);
    }

    private static boolean hasLineOfSight(Level level, Vec3 from, Vec3 to) {
        // If ray hits something before reaching `to`, then LOS is blocked.
        ClipContext context = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
        HitResult result = level.clip(context);
        // MISS means no block was hit between the two points -> clear sight
        // If result is BLOCK, it's blocking; if it's MISS we treat as visible.
        return result.getType() == HitResult.Type.MISS;
    }
}

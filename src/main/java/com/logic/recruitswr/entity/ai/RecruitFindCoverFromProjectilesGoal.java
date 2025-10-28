package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RecruitFindCoverFromProjectilesGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;
    private final double speedModifier;

    private Path path;

    private List<Projectile> enemyProjectiles;

    public RecruitFindCoverFromProjectilesGoal(T mob, double speedModifier) {
        this.recruit = mob;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if(RecruitsWariumConfig.SHOULD_RECRUITS_RUN_TO_COVER.get()) {
            boolean state = this.recruit.getFollowState() == 0;
            boolean move = !this.recruit.getShouldMovePos();
            boolean follow = !this.recruit.getShouldFollow();

            if(state && move && follow) {
                List<AbstractArrow> entities = this.recruit.level().getEntitiesOfClass(AbstractArrow.class, this.recruit.getBoundingBox().inflate((Integer) RecruitsWariumConfig.BULLET_SUPPRESSION_RADIUS.get() * 1.5));

                if(!entities.isEmpty()) {
                    Stream<AbstractArrow> abstractArrowStream = entities.stream().filter((e) -> e.inGroundTime < 20);

                    enemyProjectiles = new ArrayList<>(abstractArrowStream.toList());

                    enemyProjectiles.sort(Comparator.comparingDouble(this.recruit::distanceToSqr));

                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public void start() {
        Optional<Projectile> optionalProjectile = enemyProjectiles.stream().findFirst();

        if(optionalProjectile.isPresent()) {
            Projectile projectile = enemyProjectiles.stream().findFirst().get();

            Entity owner = projectile.getOwner();

            if(owner instanceof LivingEntity target) {
                List<BlockPos> coverPositions = this.getPotentialPositions(recruit, target);

                coverPositions.sort(Comparator.comparingDouble((position) -> {
                    return recruit.distanceToSqr(position.getCenter());
                }));

                coverPositions.stream().findFirst().ifPresent((e) -> this.path = this.recruit.getNavigation().createPath(e.getX(), e.getY(), e.getZ(), 1));


            }
        }

        super.start();
    }

    @Override
    public void tick() {
        if(path != null) {
            recruit.getNavigation().moveTo(this.path, this.speedModifier);
        }
    }

    public boolean canContinueToUse() {
        return this.path != null && !this.path.isDone();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public List<BlockPos> getPotentialPositions(AbstractRecruitEntity entity, LivingEntity target) {
        List<BlockPos> coverPositions = new ArrayList<>();
        BlockPos entityPos = entity.blockPosition();
        Level world = entity.level();

        int radius = RecruitsWariumConfig.RECRUIT_COVER_RADIUS.get();

        for (int x = (int) -radius; x <= radius; x++) {
            for (int z = (int) -radius; z <= radius; z++) {
                for (int y = (int) -radius; y <= radius; y++) { // Check around eye height
                    BlockPos blockPos = entityPos.offset(x, y, z);
                    BlockState blockState = world.getBlockState(blockPos);

                    if (blockState.isSolid()) {
                        BlockPos coverPos = isPositionValid(blockPos.offset(0, (int) entity.getEyeHeight(), 0), target);

                        if (coverPos != null) {
                            coverPositions.add(coverPos);
                        }
                    }
                }
            }
        }

        return coverPositions;
    }

    private BlockPos isPositionValid(BlockPos blockPos, LivingEntity target) {
        if(target != null) {
            if(!RecruitsWariumUtils.hasLineOfSight(target, new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), blockPos)) {
                return blockPos;
            }
        }

        return null;
    }
}

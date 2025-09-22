package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecruitsFindCoverGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;
    private final double speedModifier;
    private LivingEntity target;

    public RecruitsFindCoverGoal(T mob, double speedModifier) {
        this.recruit = mob;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if(RecruitsWariumConfig.SHOULD_RECRUITS_RUN_TO_COVER.get()) {
            this.target = this.recruit.getTarget();

            if(target != null) {
                if(target.hasLineOfSight(recruit)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void start() {
        List<BlockPos> coverPositions = this.getPotentialPositions(recruit);

        coverPositions.sort(Comparator.comparingDouble((position) -> {
            return recruit.distanceToSqr(position.getCenter());
        }));


        coverPositions.stream().findFirst().ifPresent((e) -> recruit.getNavigation().moveTo(e.getX(), e.getY(), e.getZ(), speedModifier));


        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public List<BlockPos> getPotentialPositions(AbstractRecruitEntity entity) {
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
                        // Find a walkable spot behind the block
                        BlockPos coverPos = findWalkableCover(blockPos, entity);

                        if (coverPos != null) {
                            coverPositions.add(coverPos);
                        }
                    }
                }
            }
        }
        return coverPositions;
    }

    private BlockPos findWalkableCover(BlockPos blockPos, AbstractRecruitEntity entity) {
        Level world = entity.level();
        // Check if the space behind the block is walkable
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos checkPos = blockPos.relative(dir);
            if (world.getBlockState(checkPos).isAir() && world.getBlockState(checkPos.above()).isAir()) {
                if(target != null) {
                    if(!hasLineOfSight(target, new Vec3(entity.getX(), entity.getEyeY(), entity.getZ()))) {
                        return checkPos;
                    }
                }
            }
        }

        return null;
    }

    public static boolean hasLineOfSight(LivingEntity entity, Vec3 vec31) {
        Vec3 vec3 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
        if (vec31.distanceTo(vec3) > (double)64.0F) {
            return false;
        } else {
            return entity.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS;
        }
    }
}

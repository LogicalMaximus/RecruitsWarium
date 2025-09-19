package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.mcreator.crustychunks.block.entity.AimerNodeBlockEntity;
import net.mcreator.crustychunks.block.entity.NodeTriggerBlockEntity;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.item.AimerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class RecruitRangedWariumAimerGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;

    private LivingEntity target;

    public RecruitRangedWariumAimerGoal(T recruit) {
        this.recruit = recruit;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.recruit.getTarget();

        if (livingentity != null && livingentity.isAlive() && this.isHoldingAimer(this.recruit)) {
            this.target = livingentity;
            float distance = this.target.distanceTo(this.recruit);
            boolean shouldRanged = this.recruit.getShouldRanged();
            boolean canAttack = this.recruit.canAttack(this.target);
            boolean notPassive = this.recruit.getState() != 3;
            boolean notNeedsToGetFood = !this.recruit.needsToGetFood();
            boolean canSee = this.recruit.getSensing().hasLineOfSight(this.target);
            if (!canSee) {
                this.recruit.setTarget((LivingEntity)null);
                return false;
            } else {
                return notNeedsToGetFood && canAttack && notPassive && shouldRanged;
            }
        } else {
            return false;
        }
    }

    private boolean isHoldingAimer(AbstractRecruitEntity recruit) {
        return recruit.getMainHandItem().getItem() == CrustyChunksModItems.AIMER.get();
    }

    @Override
    public void start() {
        this.recruit.setAggressive(true);

        super.start();
    }

    @Override
    public void stop() {
        this.recruit.setAggressive(false);

        super.stop();
    }

    @Override
    public void tick() {
        ItemStack itemStack = this.recruit.getMainHandItem();

        if(itemStack.getItem() instanceof AimerItem aimerItem) {
            CompoundTag tag = itemStack.getOrCreateTag();

            BlockPos aimerPos = new BlockPos((int) tag.getDouble("POSX"), (int) tag.getDouble("POSY"), (int) tag.getDouble("POSZ"));

            BlockEntity blockEntity = this.recruit.level().getBlockEntity(aimerPos);

            if(blockEntity instanceof AimerNodeBlockEntity nodeBlockEntity) {
                List<BlockEntity> nodeTriggers = this.getAimerNodesAroundPos(aimerPos, RecruitsWariumConfig.AIMER_NODE_RADIUS.get());

                for(BlockEntity node : nodeTriggers) {
                    calculatePitchAndYaw(node);
                }

                aimerItem.onEntitySwing(itemStack, this.recruit);
            }
        }

        super.tick();
    }

    private void calculatePitchAndYaw(BlockEntity node) {
        Vec3 targetPos = this.target.getEyePosition();
        BlockPos triggerPos = node.getBlockPos();

        double dx = targetPos.x - triggerPos.getX();
        double dy = targetPos.y - triggerPos.getY();
        double dz = targetPos.z - triggerPos.getZ();

        float yaw = (float) Math.atan(dy/dx);

        float pitch = (float) Math.atan(Math.sqrt(dx * dx + dy * dy)/dz);

        node.getPersistentData().putDouble("Pitch", pitch);
        node.getPersistentData().putDouble("Yaw", yaw);
    }

    private List<BlockEntity> getAimerNodesAroundPos(BlockPos pos, int radius) {
        List<BlockEntity> nodes = new ArrayList<>();

        for (int x = (int) -radius; x <= radius; x++) {
            for (int z = (int) -radius; z <= radius; z++) {
                for (int y = (int) -radius; y <= radius; y++) { // Check around eye height
                    BlockPos blockPos = pos.offset(x, y, z);

                    BlockEntity blockEntity = this.recruit.level().getBlockEntity(blockPos);

                    if(blockEntity instanceof NodeTriggerBlockEntity) {
                        nodes.add(blockEntity);
                    }
                }
            }
        }

        return nodes;
    }
}

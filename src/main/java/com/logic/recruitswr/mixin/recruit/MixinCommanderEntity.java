package com.logic.recruitswr.mixin.recruit;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.entity.ai.RecruitAvoidEntityGoal;
import com.talhanation.recruits.entities.AbstractLeaderEntity;
import com.talhanation.recruits.entities.CommanderEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommanderEntity.class)
public abstract class MixinCommanderEntity extends AbstractLeaderEntity {

    public MixinCommanderEntity(EntityType<? extends AbstractLeaderEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public double getMeleeStartRange() {
        if(RecruitsWariumConfig.SHOULD_COMMANDERS_AVOID_ENEMIES.get()) {
            return (double)1.0F;
        } else {
            return super.getMeleeStartRange();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new RecruitAvoidEntityGoal<>(this, LivingEntity.class, (livingEntity -> RecruitsWariumConfig.SHOULD_COMMANDERS_AVOID_ENEMIES.get() && this.distanceTo(livingEntity) < 32), 64.0F, 1.35D, 1.22D, this::shouldAttack));
    }
}

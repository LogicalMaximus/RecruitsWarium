package com.logic.recruitswr.mixin;

import com.logic.recruitswr.entity.ai.RecruitWariumStrategicFire;
import com.talhanation.recruits.entities.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowmanEntity.class)
public abstract class MixinBowmanEntity extends AbstractRecruitEntity implements IRangedRecruit, IStrategicFire {
    public MixinBowmanEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new RecruitWariumStrategicFire(((BowmanEntity) (Object)this)));
    }
}

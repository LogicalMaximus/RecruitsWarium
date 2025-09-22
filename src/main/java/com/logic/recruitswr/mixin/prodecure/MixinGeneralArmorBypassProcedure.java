package com.logic.recruitswr.mixin.prodecure;

import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.mcreator.crustychunks.procedures.GeneralArmorBypassProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GeneralArmorBypassProcedure.class)
public class MixinGeneralArmorBypassProcedure {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, CallbackInfo ci) {
        if(immediatesourceentity instanceof AbstractArrow arrow) {
            if(entity instanceof LivingEntity target) {
                if(arrow.getOwner() instanceof AbstractRecruitEntity recruit) {
                    if(!recruit.canAttack(target)) {
                        ci.cancel();
                    }
                }
            }
        }
    }


}

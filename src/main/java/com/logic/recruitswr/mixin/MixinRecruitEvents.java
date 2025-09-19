package com.logic.recruitswr.mixin;

import com.talhanation.recruits.RecruitEvents;
import net.mcreator.crustychunks.entity.RocketEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecruitEvents.class)
public class MixinRecruitEvents {

    @Inject(method = "onProjectileImpact", at = @At("HEAD"), cancellable = true, remap = false)
    public void onProjectileImpact(ProjectileImpactEvent event, CallbackInfo ci) {

    }
}

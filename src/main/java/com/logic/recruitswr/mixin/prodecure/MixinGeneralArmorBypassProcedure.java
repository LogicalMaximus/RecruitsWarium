package com.logic.recruitswr.mixin;

import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.mcreator.crustychunks.entity.*;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.procedures.GeneralArmorBypassProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

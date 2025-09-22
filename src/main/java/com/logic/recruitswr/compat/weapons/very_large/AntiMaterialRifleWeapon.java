package com.logic.recruitswr.compat.weapons.very_large;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class AntiMaterialRifleWeapon extends WariumWeapon {
    public AntiMaterialRifleWeapon() {
        super(CrustyChunksModItems.SINGLE_SHOT_RIFLE.get());

        ammo.addAll(AmmoTypes.VERY_LARGE.getItems());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_ANTI_MATERIAL_RIFLE_AMMO.get();
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {
        CrustyChunksMod.queueServerWork(1, () -> {
            if (!world.isClientSide()) {
                world.playSound((Player)null, pos, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:mediumshot")), SoundSource.NEUTRAL, 15.0F, (float)Mth.nextDouble(RandomSource.create(), 0.7, 0.8));
            } else {
                world.playLocalSound(pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:mediumshot")), SoundSource.NEUTRAL, 15.0F, (float)Mth.nextDouble(RandomSource.create(), 0.7, 0.8), false);
            }

            if (!world.isClientSide()) {
                world.playSound((Player)null, pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:medium_small_explosion_distant")), SoundSource.NEUTRAL, 40.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
            } else {
                world.playLocalSound(pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:medium_small_explosion_distant")), SoundSource.NEUTRAL, 40.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
            }



            if (!world.isClientSide()) {
                world.playSound((Player)null, pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:autocannonshot")), SoundSource.NEUTRAL, 20.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
            } else {
                world.playLocalSound(pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:autocannonshot")), SoundSource.NEUTRAL, 20.0F, (float)Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
            }


        });
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_ANTI_MATERIAL_RIFLE_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_ANTI_MATERIAL_RIFLE_RELOAD.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage(3.0F);
        abstractArrow.setKnockback(1);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 5);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 8.0F, (float) Mth.nextDouble(RandomSource.create(), 0.1, (double)0.1 + RecruitsWariumConfig.AIM_INACCURACY.get()));

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.BOLTRELOAD.get();
    }
}

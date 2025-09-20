package com.logic.recruitswr.compat.weapons.rocket;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
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

public class ArmorPeelerLauncherWeapon extends WariumWeapon {
    public ArmorPeelerLauncherWeapon() {
        super(CrustyChunksModItems.ARMOR_PEELER_ANIMATED.get());

        ammo.add(CrustyChunksModItems.ARMOR_PEELER_ROCKET.get());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_ARMOR_PEELER_AMMO.get();
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {
        if (world instanceof Level) {
            if (!world.isClientSide()) {
                world.playSound((Player)null, pos, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:rocket_launch")), SoundSource.NEUTRAL, 10.0F, (float)Mth.nextDouble(RandomSource.create(), 1.3, 1.4));
            } else {
                world.playLocalSound(pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:rocket_launch")), SoundSource.NEUTRAL, 10.0F, (float)Mth.nextDouble(RandomSource.create(), 1.3, 1.4), false);
            }
        }

        if (world instanceof Level) {
            if (!world.isClientSide()) {
                world.playSound((Player)null, pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:peelerpodfar")), SoundSource.NEUTRAL, 80.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
            } else {
                world.playLocalSound(pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:peelerpodfar")), SoundSource.NEUTRAL, 80.0F, (float)Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
            }
        }
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_ARMOR_PEELER_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_ARMOR_PEELER_RELOAD.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage(0.1F);
        abstractArrow.setKnockback(0);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 4);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 6.0F, (float) Mth.nextDouble(RandomSource.create(), 3.0, (double)3.0 + RecruitsWariumConfig.AIM_INACCURACY.get()));

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.BOLTRELOAD.get();
    }
}

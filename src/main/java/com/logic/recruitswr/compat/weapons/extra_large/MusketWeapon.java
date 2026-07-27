package com.logic.recruitswr.compat.weapons.extra_large;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.mcreator.crustychunks.procedures.ExtraLargeFireSoundProcedure;
import net.mcreator.crustychunks.procedures.ShotgunFireSoundProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class MusketWeapon extends WariumWeapon {

    public MusketWeapon() {
        super(CrustyChunksModItems.MUSKET.get());

        ammo.addAll(AmmoTypes.EXTRA_LARGE.getItems());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_MUSKET_RIFLE_AMMO.get();
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {
        ShotgunFireSoundProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
        ExtraLargeFireSoundProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());

    }

    @Override
    public double getBaseWeaponInaccuracy() {
        return RecruitsWariumConfig.MUSKET_RIFLE_INACCURACY.get();
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_MUSKET_RIFLE_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_MUSKET_RIFLE_RELOAD.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage(1.0F);
        abstractArrow.setKnockback(1);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 1);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 8.0F, (float) Mth.nextDouble(RandomSource.create(), 0.1, (double)0.1 + RecruitsWariumConfig.BULLET_INACCURACY.get()));

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.BOLTRELOAD.get();
    }
}

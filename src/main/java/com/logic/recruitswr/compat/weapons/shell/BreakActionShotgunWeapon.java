package com.logic.recruitswr.compat.weapons.shell;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class BreakActionShotgunWeapon extends WariumWeapon {
    public BreakActionShotgunWeapon() {
        super(CrustyChunksModItems.BREAK_ACTION_SHOTGUN_ANIMATED.get());

        ammo.addAll(AmmoTypes.SHELL.getItems());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_BREAK_ACTION_SHOTGUN_AMMO.get();
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_BREAK_ACTION_SHOTGUN_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_BREAK_ACTION_SHOTGUN_RELOAD.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage(0.1F);
        abstractArrow.setKnockback(0);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 4);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, (float) Mth.nextDouble(RandomSource.create(), 5.9, 6.1), 6.0F);

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.SHOTGUNRELOAD.get();
    }

    @Override
    public int getBulletAmount() {
        return 30;
    }

    @Override
    public int attackRadius() {
        return 24;
    }
}

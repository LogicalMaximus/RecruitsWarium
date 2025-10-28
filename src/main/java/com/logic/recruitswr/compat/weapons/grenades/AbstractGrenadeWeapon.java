package com.logic.recruitswr.compat.weapons.grenades;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public abstract class AbstractGrenadeWeapon extends WariumWeapon {

    public AbstractGrenadeWeapon(Item weapon) {
        super(weapon);
    }

    @Override
    public int getMaxAmmo() {
        return 0;
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {

    }

    @Override
    public double getBaseWeaponInaccuracy() {
        return 0;
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.GRENADE_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return 0;
    }

    @Override
    public SoundEvent getLoadSound() {
        return null;
    }

    @Override
    public boolean isGun() {
        return false;
    }

    @Override
    public boolean isGrenade() {
        return true;
    }

}

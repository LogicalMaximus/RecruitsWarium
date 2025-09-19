package com.logic.recruitswr.compat.weapons.very_large;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

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
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 8.0F, (float)0.1);

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.BOLTRELOAD.get();
    }
}

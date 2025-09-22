package com.logic.recruitswr.compat.weapons.small;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.mcreator.crustychunks.procedures.SmallCasingDropProcedure;
import net.mcreator.crustychunks.procedures.StealthPistolFireSoundProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public class StealthPistolWeapon extends WariumWeapon {

    public StealthPistolWeapon() {
        super(CrustyChunksModItems.STEALTH_PISTOL.get());

        ammo.addAll(AmmoTypes.SMALL.getItems());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_STEALTH_PISTOL_AMMO.get();
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        SmallCasingDropProcedure.execute(world, x, y, z);
        StealthPistolFireSoundProcedure.execute(world, x, y, z);
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_STEALTH_PISTOL_AMMO.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_STEALTH_PISTOL_AMMO.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage(0.1F);
        abstractArrow.setKnockback(0);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 1);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 5.0F, (float)0.5);

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return CrustyChunksModSounds.PISTOLACTION.get();
    }
}

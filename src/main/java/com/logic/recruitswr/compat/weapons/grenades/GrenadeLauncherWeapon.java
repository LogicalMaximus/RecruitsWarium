package com.logic.recruitswr.compat.weapons.grenades;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumSoundUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class GrenadeLauncherWeapon extends WariumWeapon {
    public GrenadeLauncherWeapon() {
        super(CrustyChunksModItems.GRENADE_LAUNCHER.get());

        this.ammo.addAll(AmmoTypes.GRENADE.getItems());
    }

    @Override
    public int getMaxAmmo() {
        return RecruitsWariumConfig.MAX_GRENADE_LAUNCHER_AMMO.get();
    }

    public boolean isIndirectFire() {
        return true;
    }

    @Override
    protected void playShootSounds(Level world, BlockPos pos) {
        RecruitsWariumSoundUtils.playGrenadeLauncher(world, pos);
    }

    public boolean isSecondary() {
        return true;
    }

    @Override
    public double getBaseWeaponInaccuracy() {
        return RecruitsWariumConfig.GRENADE_LAUNCHER_INACCURACY.get();
    }

    @Override
    public int getAttackCooldown() {
        return RecruitsWariumConfig.MAX_GRENADE_LAUNCHER_COOLDOWN.get();
    }

    @Override
    public int getWeaponLoadTime() {
        return RecruitsWariumConfig.MAX_GRENADE_LAUNCHER_RELOAD.get();
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage((double)3.0F);
        abstractArrow.setKnockback(1);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 5);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 3.5F, (float) Mth.nextDouble(RandomSource.create(), 0.1, (double)0.1 + RecruitsWariumConfig.BULLET_INACCURACY.get()));

        return abstractArrow;
    }

    @Override
    public SoundEvent getLoadSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end"));
    }
}

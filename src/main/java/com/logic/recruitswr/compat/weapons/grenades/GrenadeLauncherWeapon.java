package com.logic.recruitswr.compat.weapons.grenades;

import com.logic.recruitswr.compat.AmmoTypes;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumSoundUtils;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
        LivingEntity target = null;
        boolean flag = false;

        if(livingEntity instanceof Mob mob) {
            target = mob.getTarget();

            if(target != null) {
                if(!mob.hasLineOfSight(target)) {
                    flag = true;
                }
                else {
                    flag = false;
                }
            }
            else {
                flag = false;
            }

        } else {
            flag = false;
        }

        if(flag && target != null) {
            abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeHeight() + 1, livingEntity.getZ());

            double dx = target.getX() - livingEntity.getX();
            double dz = target.getZ() - livingEntity.getZ();
            double dy = target.getY() - abstractArrow.getY();

            double horizontalDist = Math.sqrt(dx * dx + dz * dz);

            double LOB_SCALE = 0.055;
            double MAX_LOB = 1.2;
            double MIN_RANGE = 6.0;

            double lob = Math.min(horizontalDist * LOB_SCALE, MAX_LOB);

            double yAim = dy + horizontalDist * lob;

            livingEntity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(livingEntity.getX() + dx, abstractArrow.getY() + yAim, livingEntity.getZ() + dz));

            abstractArrow.shoot(dx, yAim, dz, 1.9F, 0.5F);
        }
        else {
            directFire(livingEntity, abstractArrow);
        }

        return abstractArrow;
    }

    public boolean isExplosive() {
        return true;
    }

    private static void directFire(LivingEntity livingEntity, AbstractArrow abstractArrow) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage((double)3.0F);
        abstractArrow.setKnockback(1);
        abstractArrow.setSilent(true);
        abstractArrow.setPierceLevel((byte) 5);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());
        abstractArrow.shoot(livingEntity.getLookAngle().x, livingEntity.getLookAngle().y, livingEntity.getLookAngle().z, 3.5F, (float) Mth.nextDouble(RandomSource.create(), 0.1, (double)0.1 + RecruitsWariumConfig.BULLET_INACCURACY.get()));
    }

    @Override
    public SoundEvent getLoadSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.loading_end"));
    }
}

package com.logic.recruitswr.utils;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Random;

public class RecruitsWariumUtils {
    private static final Random random = new Random();

    private static final ArrayList<Item> GUNS = new ArrayList<>();

    public static boolean isWariumGun(Item item) {
        if(GUNS.contains(item)) {
            return true;
        }

        return false;
    }

    public static boolean hasLineOfSight(LivingEntity entity, Vec3 vec31, BlockPos pos) {
        Vec3 vec3 = entity.getEyePosition(1.0F);
        if (vec31.distanceTo(vec3) > (double)512.0F) {
            return false;
        } else {
            Level level = entity.level();

            ClipContext context = new ClipContext(entity.getEyePosition(), vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);

            BlockHitResult result = level.clip(context);

            return result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(new BlockPos(pos));
        }
    }

    public static double getAngleDistanceModifier(double distance, int x, int i) {
        double modifier = distance / (double)x;
        return (modifier - (double)random.nextInt(-i, i)) / 100.0;
    }

    public static float getForceDistanceModifier(double distance, double base) {
        double modifier = 0.0;
        if (distance > 4000.0) {
            modifier = base * 0.09;
        } else if (distance > 3750.0) {
            modifier = base * 0.075;
        } else if (distance > 3500.0) {
            modifier = base * 0.055;
        } else if (distance > 3000.0) {
            modifier = base * 0.03;
        } else if (distance > 2500.0) {
            modifier = base * 0.01;
        }

        return (float)modifier;
    }

    public static double getAngleHeightModifier(double distance, double heightDiff, double modifier) {
        if (distance >= 2000.0) {
            return heightDiff * 1.15 * modifier;
        } else if (distance >= 1750.0) {
            return heightDiff * 1.05 * modifier;
        } else if (distance >= 1500.0) {
            return heightDiff * 0.6 * modifier;
        } else if (distance >= 1250.0) {
            return heightDiff * 0.5 * modifier;
        } else if (distance >= 1000.0) {
            return heightDiff * 0.4 * modifier;
        } else if (distance >= 750.0) {
            return heightDiff * 0.3 * modifier;
        } else {
            return distance >= 500.0 ? heightDiff * 0.2 * modifier : 0.0;
        }
    }

     static {
        GUNS.add(CrustyChunksModItems.AUTO_PISTOL.get());
        GUNS.add(CrustyChunksModItems.SEMI_AUTOMATIC_PISTOL_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.STEALTH_PISTOL.get());
        GUNS.add(CrustyChunksModItems.REVOLVER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.MACHINE_GUN.get());
        GUNS.add(CrustyChunksModItems.MACHINE_CARBINE.get());
        GUNS.add(CrustyChunksModItems.LEVER_RIFLE.get());
        GUNS.add(CrustyChunksModItems.SEMI_AUTOMATIC_RIFLE_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.AUTOMATIC_RIFLE.get());
        GUNS.add(CrustyChunksModItems.BURST_RIFLE.get());
        GUNS.add(CrustyChunksModItems.LMG_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BATTLE_RIFLE.get());
        GUNS.add(CrustyChunksModItems.BOLT_ACTION_RIFLE_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BREAK_ACTION_SHOTGUN_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.PUMP_ACTION_SHOTGUN_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BREECH_RIFLE.get());
        GUNS.add(CrustyChunksModItems.FLAME_THROWER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.ARMOR_PEELER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.ARMOR_PEELER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.GRENADE_LAUNCHER.get());
     }
}

package com.logic.recruitswr.utils;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class RecruitsWariumUtils {
    private static final ArrayList<Item> GUNS = new ArrayList<>();

    public static boolean isWariumGun(Item item) {
        if(GUNS.contains(item)) {
            return true;
        }

        return false;
    }

    public static boolean hasLineOfSight(LivingEntity entity, Vec3 vec31) {
        Vec3 vec3 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
        if (vec31.distanceTo(vec3) > (double)64.0F) {
            return false;
        } else {
            return entity.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS;
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
     }
}

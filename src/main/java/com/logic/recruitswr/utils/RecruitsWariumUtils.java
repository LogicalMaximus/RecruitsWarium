package com.logic.recruitswr.utils;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;

import java.util.ArrayList;

public class RecruitsWariumUtils {
    private static final ArrayList<Item> GUNS = new ArrayList<>();

    public static boolean isWariumGun(Item item) {
        if(GUNS.contains(item)) {
            return true;
        }

        return false;
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

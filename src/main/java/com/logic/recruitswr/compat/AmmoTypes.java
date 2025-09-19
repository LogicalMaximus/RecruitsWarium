package com.logic.recruitswr.compat;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public enum AmmoTypes {
        SMALL(List.of(CrustyChunksModItems.SMALLBULLET.get(), CrustyChunksModItems.SMALL_HOLLOW_POINT_BULLET.get(), CrustyChunksModItems.SMALL_STEALTH_BULLET.get())),
        MEDIUM(List.of(CrustyChunksModItems.BULLET.get(), CrustyChunksModItems.MEDIUM_AP_BULLET.get(), CrustyChunksModItems.MEDIUM_STEALTH_BULLET.get())),
        LARGE(List.of(CrustyChunksModItems.LARGE_BULLET.get(), CrustyChunksModItems.AP_LARGE_BULLET.get(), CrustyChunksModItems.STEALTH_LARGE_BULLET.get())),
        SHELL(List.of(CrustyChunksModItems.SLUG_SHELL.get(), CrustyChunksModItems.SHOTGUN_SHELL.get(), CrustyChunksModItems.BIRD_SHOT.get())),
        VERY_LARGE(List.of(CrustyChunksModItems.SMALL_SHELL.get())),
        EXTRA_LARGE(List.of(CrustyChunksModItems.EXTRA_LARGE_BULLET.get())),
        FLAME(List.of(CrustyChunksModItems.PETROLIUM_BUCKET.get()))
    ;

    private final List<Item> ammo;

    AmmoTypes(List<Item> ammo) {
        this.ammo = ammo;
    }

    public List<Item> getItems() {
        return ammo;
    }
}

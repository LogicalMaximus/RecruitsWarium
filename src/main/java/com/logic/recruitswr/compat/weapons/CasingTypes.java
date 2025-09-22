package com.logic.recruitswr.compat.weapons;

import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;

import java.util.List;

public enum CasingTypes {
    SMALL(CrustyChunksModItems.SMALL_CASING.get()),
    MEDIUM(CrustyChunksModItems.MEDIUM_CASING.get()),
    LARGE(CrustyChunksModItems.LARGE_CASING.get()),
    SHELL(CrustyChunksModItems.HUGE_CASING.get()),
    VERY_LARGE(CrustyChunksModItems.EXTRA_LARGE_CASING.get()),
    EXTRA_LARGE(CrustyChunksModItems.EXTRA_LARGE_CASING.get()),
    FLAME(null)
            ;

    private final Item item;

    CasingTypes(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}

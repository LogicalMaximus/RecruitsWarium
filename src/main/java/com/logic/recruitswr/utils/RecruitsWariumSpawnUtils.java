package com.logic.recruitswr.utils;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;
import java.util.Random;

public class RecruitsWariumSpawnUtils {
    public static final Random random = new Random();

    public static void setRangedBullets(AbstractRecruitEntity recruit) {
        WariumWeapon weapon = WariumWeapons.getWeaponFromItem(recruit.getMainHandItem().getItem());

        if(weapon != null) {
            for(int i = 0; i < 3; i++) {
                int j = random.nextInt(64);

                Optional<Item> optionalItem = weapon.getAmmo().stream().findAny();

                if(optionalItem.isPresent()) {
                    Item ammo = optionalItem.get();

                    ItemStack arrows = new ItemStack(ammo);
                    arrows.setCount(j);
                    recruit.inventory.setItem(6 + i, arrows);
                }
            }
        }
    }
}

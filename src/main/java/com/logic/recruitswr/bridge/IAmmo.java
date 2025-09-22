package com.logic.recruitswr.bridge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;

public interface IAmmo {

    EntityType<? extends AbstractArrow> getProjecile();

    Item getCasing();

}

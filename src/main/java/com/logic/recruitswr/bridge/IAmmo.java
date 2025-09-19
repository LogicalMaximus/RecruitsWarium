package com.logic.recruitswr.bridge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;

public interface IAmmo {

    EntityType<? extends AbstractArrow> getProjecile();

}

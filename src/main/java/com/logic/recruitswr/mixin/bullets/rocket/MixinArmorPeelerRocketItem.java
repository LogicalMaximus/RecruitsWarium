package com.logic.recruitswr.mixin.bullets.rocket;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.ArmorPeelerRocketItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorPeelerRocketItem.class)
public abstract class MixinArmorPeelerRocketItem extends Item implements IAmmo {
    public MixinArmorPeelerRocketItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.ROCKET.get();
    }
}

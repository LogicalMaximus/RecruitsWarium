package com.logic.recruitswr.mixin.grenade.item;

import com.logic.recruitswr.bridge.IGrenade;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.GrenadeItem;
import net.mcreator.crustychunks.item.IncendiaryGrenadeItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IncendiaryGrenadeItem.class)
public abstract class MixinIncediaryGrenadeItem extends Item implements IGrenade {
    public MixinIncediaryGrenadeItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.INCENDIARY_GRENADE_PROJECTILE.get();
    }
}

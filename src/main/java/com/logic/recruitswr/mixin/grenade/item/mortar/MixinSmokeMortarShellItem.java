package com.logic.recruitswr.mixin.grenade.item.mortar;

import com.logic.recruitswr.bridge.IGrenade;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.SmokeMortarShellItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmokeMortarShellItem.class)
public abstract class MixinSmokeMortarShellItem extends Item implements IGrenade {
    public MixinSmokeMortarShellItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.MORTAR_PROJECTILE.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.FLAME.getItem();
    }
}

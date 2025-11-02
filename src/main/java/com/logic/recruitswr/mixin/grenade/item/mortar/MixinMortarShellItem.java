package com.logic.recruitswr.mixin.grenade.item.mortar;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.MortarShellItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MortarShellItem.class)
public abstract class MixinMortarShellItem extends Item implements IAmmo {
    public MixinMortarShellItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.SMOKE_MORTAR_PROJECTILE.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.FLAME.getItem();
    }
}

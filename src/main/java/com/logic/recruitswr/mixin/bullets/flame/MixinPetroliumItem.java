package com.logic.recruitswr.mixin.bullets.flame;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.PetroliumItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PetroliumItem.class)
public abstract class MixinPetroliumItem extends BucketItem implements IAmmo {
    public MixinPetroliumItem(Fluid p_40689_, Properties p_40690_) {
        super(p_40689_, p_40690_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.FLAME_THROWER_EMBER.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.FLAME.getItem();
    }
}

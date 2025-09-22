package com.logic.recruitswr.mixin.bullets.extra_large;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.ExtraLargeBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExtraLargeBulletItem.class)
public abstract class MixinExtraLargeBulletItem extends Item implements IAmmo {
    public MixinExtraLargeBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.EXTRA_LARGE_BULLET_FIRE.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.EXTRA_LARGE.getItem();
    }
}

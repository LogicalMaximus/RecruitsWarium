package com.logic.recruitswr.mixin.bullets.large;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.APLargeBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(APLargeBulletItem.class)
public abstract class MixinAPLargeBulletItem extends Item implements IAmmo {
    public MixinAPLargeBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.LARGE_AP_BULLET.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.LARGE.getItem();
    }
}

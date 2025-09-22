package com.logic.recruitswr.mixin.bullets.small;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.SmallbulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmallbulletItem.class)
public abstract class MixinSmallbulletItem extends Item implements IAmmo {
    public MixinSmallbulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.SMALL_BULLET_ALT.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.SMALL.getItem();
    }
}

package com.logic.recruitswr.mixin.bullets.large;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.LargeBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LargeBulletItem.class)
public abstract class MixinLargeBulletItem extends Item implements IAmmo {
    public MixinLargeBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.LARGE_BULLET_FIRE_PROJECTILE.get();
    }
}

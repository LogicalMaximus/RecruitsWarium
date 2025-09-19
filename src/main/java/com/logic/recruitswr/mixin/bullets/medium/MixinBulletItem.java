package com.logic.recruitswr.mixin.bullets.medium;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.BulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BulletItem.class)
public abstract class MixinBulletItem extends Item implements IAmmo {
    public MixinBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @NotNull EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.BULLETFIRE_PROJECTILE.get();
    }
}

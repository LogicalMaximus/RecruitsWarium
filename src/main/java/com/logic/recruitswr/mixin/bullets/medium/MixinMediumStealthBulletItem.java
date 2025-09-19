package com.logic.recruitswr.mixin.bullets.medium;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.MediumStealthBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MediumStealthBulletItem.class)
public abstract class MixinMediumStealthBulletItem extends Item implements IAmmo {
    public MixinMediumStealthBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @NotNull EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.STEALTH_MEDIUM_BULLET.get();
    }
}

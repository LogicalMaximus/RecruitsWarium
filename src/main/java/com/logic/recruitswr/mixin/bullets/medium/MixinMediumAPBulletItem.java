package com.logic.recruitswr.mixin.bullets.medium;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.MediumAPBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MediumAPBulletItem.class)
public abstract class MixinMediumAPBulletItem extends Item implements IAmmo {

    public MixinMediumAPBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @NotNull EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.AP_MEDIUM_BULLET.get();
    }
}

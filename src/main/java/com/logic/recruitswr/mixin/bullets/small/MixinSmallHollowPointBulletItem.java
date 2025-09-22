package com.logic.recruitswr.mixin.bullets.small;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.SmallHollowPointBulletItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmallHollowPointBulletItem.class)
public abstract class MixinSmallHollowPointBulletItem extends Item implements IAmmo {
    public MixinSmallHollowPointBulletItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public @NotNull EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.SMALL_BULLET_HP.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.SMALL.getItem();
    }
}

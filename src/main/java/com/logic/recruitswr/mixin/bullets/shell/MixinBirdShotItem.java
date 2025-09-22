package com.logic.recruitswr.mixin.bullets.shell;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.compat.weapons.CasingTypes;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.BirdShotItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BirdShotItem.class)
public abstract class MixinBirdShotItem extends Item implements IAmmo {
    public MixinBirdShotItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.BIRDSHOT_PARTICLE.get();
    }

    @Override
    public Item getCasing() {
        return CasingTypes.SHELL.getItem();
    }
}

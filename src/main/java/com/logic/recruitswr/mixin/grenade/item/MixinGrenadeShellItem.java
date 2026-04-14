package com.logic.recruitswr.mixin.grenade.item;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.GrenadeShellItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrenadeShellItem.class)
public abstract class MixinGrenadeShellItem implements IAmmo {

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.IMPACT_GRENADE_PROJECTILE.get();
    }
}

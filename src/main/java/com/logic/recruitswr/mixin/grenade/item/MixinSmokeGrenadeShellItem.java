package com.logic.recruitswr.mixin.grenade.item;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.SmokeGrenadeShellItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmokeGrenadeShellItem.class)
public abstract class MixinSmokeGrenadeShellItem implements IAmmo {

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.SMOKE_IMPACT_GRENADE.get();
    }
}

package com.logic.recruitswr.mixin.bullets.shell;

import com.logic.recruitswr.bridge.IAmmo;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.item.ShotgunShellItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShotgunShellItem.class)
public abstract class MixinShotgunShellItem extends Item implements IAmmo {
    public MixinShotgunShellItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.TINYPROJECTILE.get();
    }
}

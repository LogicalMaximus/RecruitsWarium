package com.logic.recruitswr.mixin.grenade;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.bridge.IGrenade;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.entity.IncendiaryGrenadeProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.procedures.GrenadeProjectileWhileProjectileFlyingTickProcedure;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IncendiaryGrenadeProjectileEntity.class)
public abstract class MixinIncendiaryGrenadeProjectileEntity extends AbstractArrow implements ItemSupplier, IGrenade {

    protected MixinIncendiaryGrenadeProjectileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void tick() {
        super.tick();
        GrenadeProjectileWhileProjectileFlyingTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());

        if(!RecruitsWariumConfig.SHOULD_RECRUITS_GRENADES_STAY_ON_GROUND.get()) {
            if (this.inGround) {
                this.discard();
            }
        }
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.INCENDIARY_GRENADE_PROJECTILE.get();
    }
}

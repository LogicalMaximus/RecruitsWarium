package com.logic.recruitswr.mixin.grenade;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.entity.SmokeGrenadeProjectileEntity;
import net.mcreator.crustychunks.procedures.GrenadeProjectileWhileProjectileFlyingTickProcedure;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SmokeGrenadeProjectileEntity.class)
public abstract class MixinSmokeGrenadeProjectileEntity extends AbstractArrow implements ItemSupplier {
    protected MixinSmokeGrenadeProjectileEntity(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
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
}

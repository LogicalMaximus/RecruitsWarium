package com.logic.recruitswr.mixin.grenade;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.bridge.IGrenade;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import net.mcreator.crustychunks.entity.SmokeGrenadeProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.procedures.GrenadeProjectileWhileProjectileFlyingTickProcedure;
import net.mcreator.crustychunks.procedures.SmokeGrenadeHitProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SmokeGrenadeProjectileEntity.class)
public abstract class MixinSmokeGrenadeProjectileEntity extends AbstractArrow implements ItemSupplier, IGrenade {
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

        if(this.inGroundTime >= RecruitsWariumConfig.GRENADE_FUSE_TIME.get()) {
            BlockPos blockPos = this.blockPosition();

            SmokeGrenadeHitProcedure.execute(this.level(), (double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), this);

            this.discard();
        }
    }

    @Override
    public EntityType<? extends AbstractArrow> getProjecile() {
        return CrustyChunksModEntities.SMOKE_GRENADE_PROJECTILE.get();
    }

    @Overwrite
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);

        if(!RecruitsWariumConfig.SHOULD_RECRUITS_GRENADES_STAY_ON_GROUND.get()) {
            SmokeGrenadeHitProcedure.execute(this.level(), (double)blockHitResult.getBlockPos().getX(), (double)blockHitResult.getBlockPos().getY(), (double)blockHitResult.getBlockPos().getZ(), this);
        }

    }

}

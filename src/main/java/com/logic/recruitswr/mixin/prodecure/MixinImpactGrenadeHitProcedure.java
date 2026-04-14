package com.logic.recruitswr.mixin.prodecure;

import com.logic.recruitswr.utils.RecruitsWariumUtils;
import net.mcreator.crustychunks.procedures.ImpactGrenadeHitProcedure;
import net.mcreator.crustychunks.procedures.TinyExplosionProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ImpactGrenadeHitProcedure.class)
public class MixinImpactGrenadeHitProcedure {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void execute(LevelAccessor world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if(immediatesourceentity instanceof Projectile projectile) {
                RecruitsWariumUtils.explodeGrenade(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ(), projectile.getOwner());
            } else {
                TinyExplosionProcedure.execute(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
            }

            if (!immediatesourceentity.level().isClientSide()) {
                immediatesourceentity.discard();
            }

        }
    }
}

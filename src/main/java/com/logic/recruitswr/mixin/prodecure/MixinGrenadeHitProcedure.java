package com.logic.recruitswr.mixin.prodecure;

import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.procedures.GrenadeHitProcedure;
import net.mcreator.crustychunks.procedures.TinyExplosionProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GrenadeHitProcedure.class)
public class MixinGrenadeHitProcedure {
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void execute(LevelAccessor world, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            CrustyChunksMod.queueServerWork(20, () -> {
                TinyExplosionProcedure.execute(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());

                immediatesourceentity.discard();
            });
        }
    }
}

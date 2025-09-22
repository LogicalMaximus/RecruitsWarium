package com.logic.recruitswr.mixin;

import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.entity.HEATEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.procedures.DamagesProcedure;
import net.mcreator.crustychunks.procedures.RocketHitProcedure;
import net.mcreator.crustychunks.procedures.TinyExplosionProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RocketHitProcedure.class)
public class MixinRocketHitProcedure {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity instanceof AbstractArrow arrow) {
            DamagesProcedure.execute(world, x, y, z);
            CrustyChunksMod.queueServerWork(2, () -> TinyExplosionProcedure.execute(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ()));
            if (world instanceof ServerLevel) {
                ServerLevel projectileLevel = (ServerLevel)world;
                Projectile _entityToSpawn = ((new Object() {
                    public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                        AbstractArrow entityToSpawn = new HEATEntity((EntityType) CrustyChunksModEntities.HEAT.get(), level);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage((double)damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        entityToSpawn.setPierceLevel(piercing);
                        entityToSpawn.setSecondsOnFire(100);
                        entityToSpawn.setCritArrow(true);
                        return entityToSpawn;
                    }
                })).getArrow(projectileLevel, arrow.getOwner(), 10.0F, 1, (byte)10);
                _entityToSpawn.setPos(immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
                _entityToSpawn.shoot(immediatesourceentity.getDeltaMovement().x(), immediatesourceentity.getDeltaMovement().y(), immediatesourceentity.getDeltaMovement().z(), 4.0F, 0.0F);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

        }
    }
}

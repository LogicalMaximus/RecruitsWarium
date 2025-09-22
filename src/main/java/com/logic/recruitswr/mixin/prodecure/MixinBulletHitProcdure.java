package com.logic.recruitswr.mixin.prodecure;

import net.mcreator.crustychunks.entity.BulletfireProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.procedures.BulletHitProcedure;
import net.mcreator.crustychunks.procedures.SmallBulletHitProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BulletHitProcedure.class)
public abstract class MixinBulletHitProcdure {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity != null) {
            if (world.getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.create(new ResourceLocation("crusty_chunks:shatterable")))) {
                world.destroyBlock(BlockPos.containing(x, y, z), false);
                if (world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break")), SoundSource.NEUTRAL, 3.0F, 1.0F);
                    } else {
                        _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break")), SoundSource.NEUTRAL, 3.0F, 1.0F, false);
                    }
                }

                double var10000;
                if (immediatesourceentity instanceof Projectile) {
                    Projectile _projEnt = (Projectile)immediatesourceentity;
                    var10000 = _projEnt.getDeltaMovement().length();
                } else {
                    var10000 = (double)0.0F;
                }

                if (var10000 > (double)2.0F && world instanceof ServerLevel) {
                    ServerLevel projectileLevel = (ServerLevel)world;
                    Projectile _entityToSpawn = ((new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                            AbstractArrow entityToSpawn = new BulletfireProjectileEntity((EntityType) CrustyChunksModEntities.BULLETFIRE_PROJECTILE.get(), level);
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.setBaseDamage((double)damage);
                            entityToSpawn.setKnockback(knockback);
                            entityToSpawn.setSilent(true);
                            entityToSpawn.setPierceLevel(piercing);
                            entityToSpawn.setCritArrow(true);
                            return entityToSpawn;
                        }
                    })).getArrow(projectileLevel, ((Projectile) immediatesourceentity).getOwner(), 2.0F, 1, (byte)50);
                    _entityToSpawn.setPos(immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
                    double var10001 = immediatesourceentity.getLookAngle().x * (double)-1.0F;
                    double var10002 = immediatesourceentity.getLookAngle().y * (double)-1.0F;
                    double var10003 = immediatesourceentity.getLookAngle().z;
                    double var10004;
                    if (immediatesourceentity instanceof Projectile) {
                        Projectile _projEnt = (Projectile)immediatesourceentity;
                        var10004 = _projEnt.getDeltaMovement().length();
                    } else {
                        var10004 = (double)0.0F;
                    }

                    _entityToSpawn.shoot(var10001, var10002, var10003, (float)(var10004 - 0.2), 2.0F);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.create(new ResourceLocation("crusty_chunks:chippable")))) {
                world.destroyBlock(BlockPos.containing(x, y, z), false);
            }

            SmallBulletHitProcedure.execute(world, x, y, z);
        }
    }

}

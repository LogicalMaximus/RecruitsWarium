package com.logic.recruitswr.mixin.prodecure;

import net.mcreator.crustychunks.entity.HVParticleProjectileEntity;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.procedures.DamagesProcedure;
import net.mcreator.crustychunks.procedures.HEATProjectileHitsBlockProcedure;
import net.mcreator.crustychunks.procedures.MediumExplosionProcedure;
import net.mcreator.crustychunks.procedures.SmallExplosionProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HEATProjectileHitsBlockProcedure.class)
public class MixinHEATProjectileHitsBlockProcedure {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
        if (immediatesourceentity instanceof AbstractArrow arrow) {
            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == CrustyChunksModBlocks.BATTLE_CANNON_BREECH.get() && ((new Object() {
                public boolean getValue(LevelAccessor world, BlockPos pos, String tag) {
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    return blockEntity != null ? blockEntity.getPersistentData().getBoolean(tag) : false;
                }
            })).getValue(world, BlockPos.containing(x, y, z), "Loaded")) {
                SmallExplosionProcedure.execute(world, x + (double)0.5F, y + (double)0.5F, z + (double)0.5F);
                world.destroyBlock(BlockPos.containing(x, y, z), false);
            }

            if (world.getBlockState(BlockPos.containing(x, y, z)).getBlock() == CrustyChunksModBlocks.ARTILLERYBREECH.get() && ((new Object() {
                public boolean getValue(LevelAccessor world, BlockPos pos, String tag) {
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    return blockEntity != null ? blockEntity.getPersistentData().getBoolean(tag) : false;
                }
            })).getValue(world, BlockPos.containing(x, y, z), "Loaded")) {
                MediumExplosionProcedure.execute(world, x + (double)0.5F, y + (double)0.5F, z + (double)0.5F);
                world.destroyBlock(BlockPos.containing(x, y, z), false);
            }

            DamagesProcedure.execute(world, x, y, z);
            if (world.getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.create(new ResourceLocation("crusty_chunks:pennable"))) || world.getBlockState(BlockPos.containing(x, y, z)).getDestroySpeed(world, BlockPos.containing(x, y, z)) < 15.0F && world.getBlockState(BlockPos.containing(x, y, z)).getDestroySpeed(world, BlockPos.containing(x, y, z)) >= 0.0F && !world.getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.create(new ResourceLocation("crusty_chunks:dirts"))) && !world.getBlockState(BlockPos.containing(x, y, z)).is(BlockTags.create(new ResourceLocation("crusty_chunks:sands")))) {
                world.destroyBlock(BlockPos.containing(x, y, z), false);

                for(int index0 = 0; index0 < 35; ++index0) {
                    if (world instanceof ServerLevel) {
                        ServerLevel projectileLevel = (ServerLevel)world;
                        Projectile _entityToSpawn = ((new Object() {
                            public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                AbstractArrow entityToSpawn = new HVParticleProjectileEntity((EntityType) CrustyChunksModEntities.HV_PARTICLE_PROJECTILE.get(), level);
                                entityToSpawn.setOwner(shooter);
                                entityToSpawn.setBaseDamage((double)damage);
                                entityToSpawn.setKnockback(knockback);
                                entityToSpawn.setSilent(true);
                                entityToSpawn.setCritArrow(true);
                                return entityToSpawn;
                            }
                        })).getArrow(projectileLevel, arrow.getOwner(), 5.0F, 1);
                        _entityToSpawn.setPos(immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
                        _entityToSpawn.shoot(immediatesourceentity.getDeltaMovement().x(), immediatesourceentity.getDeltaMovement().y(), immediatesourceentity.getDeltaMovement().z(), 2.5F, 37.0F);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                    }
                }

                for(int index1 = 0; index1 < 10; ++index1) {
                    if (world instanceof ServerLevel) {
                        ServerLevel projectileLevel = (ServerLevel)world;
                        Projectile _entityToSpawn = ((new Object() {
                            public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                AbstractArrow entityToSpawn = new HVParticleProjectileEntity((EntityType)CrustyChunksModEntities.HV_PARTICLE_PROJECTILE.get(), level);
                                entityToSpawn.setOwner(shooter);
                                entityToSpawn.setBaseDamage((double)damage);
                                entityToSpawn.setKnockback(knockback);
                                entityToSpawn.setSilent(true);
                                entityToSpawn.setSecondsOnFire(100);
                                entityToSpawn.setCritArrow(true);
                                return entityToSpawn;
                            }
                        })).getArrow(projectileLevel, arrow.getOwner(), 5.0F, 1);
                        _entityToSpawn.setPos(immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
                        _entityToSpawn.shoot(immediatesourceentity.getDeltaMovement().x(), immediatesourceentity.getDeltaMovement().y(), immediatesourceentity.getDeltaMovement().z(), 5.0F, 2.0F);
                        projectileLevel.addFreshEntity(_entityToSpawn);
                    }
                }
            }

        }
    }
}

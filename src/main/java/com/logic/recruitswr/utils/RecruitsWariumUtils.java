package com.logic.recruitswr.utils;

import com.google.common.collect.UnmodifiableIterator;
import net.mcreator.crustychunks.CrustyChunksMod;
import net.mcreator.crustychunks.entity.ParticleProjectileEntity;
import net.mcreator.crustychunks.entity.SmallClientEffectEntity;
import net.mcreator.crustychunks.entity.SplashEffectClientBypassEntity;
import net.mcreator.crustychunks.init.CrustyChunksModEntities;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RecruitsWariumUtils {
    private static final Random random = new Random();

    private static final ArrayList<Item> GUNS = new ArrayList<>();

    public static boolean isWariumGun(Item item) {
        if(GUNS.contains(item)) {
            return true;
        }

        return false;
    }

    public static boolean hasLineOfSight(LivingEntity entity, Vec3 vec31, BlockPos pos) {
        Vec3 vec3 = entity.getEyePosition(1.0F);
        if (vec31.distanceTo(vec3) > (double)512.0F) {
            return false;
        } else {
            Level level = entity.level();

            ClipContext context = new ClipContext(entity.getEyePosition(), vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);

            BlockHitResult result = level.clip(context);

            return result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(new BlockPos(pos));
        }
    }

    public static double getAngleDistanceModifier(double distance, int x, int i) {
        double modifier = distance / (double)x;
        return (modifier - (double)random.nextInt(-i, i)) / 100.0;
    }

    public static float getForceDistanceModifier(double distance, double base) {
        double modifier = 0.0;
        if (distance > 4000.0) {
            modifier = base * 0.09;
        } else if (distance > 3750.0) {
            modifier = base * 0.075;
        } else if (distance > 3500.0) {
            modifier = base * 0.055;
        } else if (distance > 3000.0) {
            modifier = base * 0.03;
        } else if (distance > 2500.0) {
            modifier = base * 0.01;
        }

        return (float)modifier;
    }

    public static double getAngleHeightModifier(double distance, double heightDiff, double modifier) {
        if (distance >= 2000.0) {
            return heightDiff * 1.15 * modifier;
        } else if (distance >= 1750.0) {
            return heightDiff * 1.05 * modifier;
        } else if (distance >= 1500.0) {
            return heightDiff * 0.6 * modifier;
        } else if (distance >= 1250.0) {
            return heightDiff * 0.5 * modifier;
        } else if (distance >= 1000.0) {
            return heightDiff * 0.4 * modifier;
        } else if (distance >= 750.0) {
            return heightDiff * 0.3 * modifier;
        } else {
            return distance >= 500.0 ? heightDiff * 0.2 * modifier : 0.0;
        }
    }

    public static void explodeGrenade(LevelAccessor world, double x, double y, double z, Entity source) {
        boolean found = false;
        double sx = (double)0.0F;
        double sy = (double)0.0F;
        double sz = (double)0.0F;
        sx = (double)-4.0F;
        found = false;

        for(int index0 = 0; index0 < 8; ++index0) {
            sy = (double)-3.0F;

            for(int index1 = 0; index1 < 4; ++index1) {
                sz = (double)-4.0F;

                for(int index2 = 0; index2 < 8; ++index2) {
                    if (Math.sqrt(Math.pow(sx, (double)2.0F) + Math.pow(sz, (double)2.0F)) < (double)3.0F && world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz)).getBlock() == Blocks.GRASS_BLOCK) {
                        BlockPos _bp = BlockPos.containing(x + sx, y + sy, z + sz);
                        BlockState _bs = Blocks.DIRT.defaultBlockState();
                        BlockState _bso = world.getBlockState(_bp);
                        UnmodifiableIterator var20 = _bso.getValues().entrySet().iterator();

                        while(var20.hasNext()) {
                            Map.Entry<Property<?>, Comparable<?>> entry = (Map.Entry)var20.next();
                            Property _property = _bs.getBlock().getStateDefinition().getProperty(((Property)entry.getKey()).getName());
                            if (_property != null && _bs.getValue(_property) != null) {
                                try {
                                    _bs = (BlockState)_bs.setValue(_property, (Comparable)entry.getValue());
                                } catch (Exception var24) {
                                }
                            }
                        }

                        world.setBlock(_bp, _bs, 3);
                    }

                    ++sz;
                }

                ++sy;
            }

            ++sx;
        }

        if (world.getFluidState(BlockPos.containing(x, y + (double)1.0F, z)).createLegacyBlock().getBlock() instanceof LiquidBlock) {
            if (world instanceof ServerLevel) {
                ServerLevel projectileLevel = (ServerLevel)world;
                Projectile _entityToSpawn = ((new Object() {
                    public Projectile getArrow(Level level, float damage, int knockback) {
                        AbstractArrow entityToSpawn = new SplashEffectClientBypassEntity((EntityType) CrustyChunksModEntities.SPLASH_EFFECT_CLIENT_BYPASS.get(), level);
                        entityToSpawn.setBaseDamage((double)damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        return entityToSpawn;
                    }
                })).getArrow(projectileLevel, 0.0F, 0);
                _entityToSpawn.setPos(x, y + (double)1.5F, z);
                _entityToSpawn.shoot((double)0.0F, (double)1.0F, (double)0.0F, 0.0F, 180.0F);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (world instanceof Level) {
                Level _level = (Level)world;
                if (!_level.isClientSide()) {
                    _level.explode(source, x + (double)0.0F, y + (double)0.0F, z + (double)0.0F, 2.0F, Level.ExplosionInteraction.TNT);
                }
            }
        } else if (world instanceof ServerLevel) {
            ServerLevel projectileLevel = (ServerLevel)world;
            Projectile _entityToSpawn = ((new Object() {
                public Projectile getArrow(Level level, float damage, int knockback) {
                    AbstractArrow entityToSpawn = new SmallClientEffectEntity((EntityType)CrustyChunksModEntities.SMALL_CLIENT_EFFECT.get(), level);
                    entityToSpawn.setBaseDamage((double)damage);
                    entityToSpawn.setKnockback(knockback);
                    entityToSpawn.setSilent(true);
                    return entityToSpawn;
                }
            })).getArrow(projectileLevel, 0.0F, 0);
            _entityToSpawn.setPos(x, y + (double)1.5F, z);
            _entityToSpawn.shoot((double)0.0F, (double)1.0F, (double)0.0F, 0.0F, 180.0F);
            projectileLevel.addFreshEntity(_entityToSpawn);
        }

        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:medium_small_explosion_distant")), SoundSource.MASTER, 60.0F, (float) Mth.nextDouble(RandomSource.create(), 0.8, 0.9));
            } else {
                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:medium_small_explosion_distant")), SoundSource.MASTER, 60.0F, (float)Mth.nextDouble(RandomSource.create(), 0.8, 0.9), false);
            }
        }

        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:smallexplosion")), SoundSource.MASTER, 15.0F, (float)Mth.nextDouble(RandomSource.create(), 1.1, 1.2));
            } else {
                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:smallexplosion")), SoundSource.MASTER, 15.0F, (float)Mth.nextDouble(RandomSource.create(), 1.1, 1.2), false);
            }
        }

        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:farblast")), SoundSource.MASTER, 200.0F, (float)Mth.nextDouble(RandomSource.create(), 1.3, 1.4));
            } else {
                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("crusty_chunks:farblast")), SoundSource.MASTER, 200.0F, (float)Mth.nextDouble(RandomSource.create(), 1.3, 1.4), false);
            }
        }

        for(int index3 = 0; index3 < 100; ++index3) {
            if (world instanceof ServerLevel projectileLevel) {
                Projectile _entityToSpawn = ((new Object() {
                    public Projectile getArrow(Level level, float damage, int knockback, byte piercing) {
                        AbstractArrow entityToSpawn = new ParticleProjectileEntity((EntityType)CrustyChunksModEntities.PARTICLE_PROJECTILE.get(), level);
                        entityToSpawn.setBaseDamage((double)damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);
                        entityToSpawn.setPierceLevel(piercing);
                        return entityToSpawn;
                    }
                })).getArrow(projectileLevel, 0.0F, 3, (byte)2);
                _entityToSpawn.setPos(x, y + (double)0.5F, z);
                _entityToSpawn.shoot(Mth.nextDouble(RandomSource.create(), (double)-1.0F, (double)1.0F), 0.2, Mth.nextDouble(RandomSource.create(), (double)-1.0F, (double)1.0F), (float)Mth.nextDouble(RandomSource.create(), 0.8, 1.2), 45.0F);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }
        }

        CrustyChunksMod.queueServerWork(2, () -> {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.explode(source, x, y, z, 2.0F, Level.ExplosionInteraction.TNT);
                }
            }

            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.explode(source, x + (double)0.0F, y + (double)0.0F, z + (double)0.0F, 4.0F, Level.ExplosionInteraction.NONE);
                }
            }

        });
        if (world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.FLASH, x, y, z, 1, (double)0.5F, (double)0.5F, (double)0.5F, 0.6);
        }

    }

     static {
        GUNS.add(CrustyChunksModItems.AUTO_PISTOL.get());
        GUNS.add(CrustyChunksModItems.SEMI_AUTOMATIC_PISTOL_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.STEALTH_PISTOL.get());
        GUNS.add(CrustyChunksModItems.REVOLVER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.MACHINE_GUN.get());
        GUNS.add(CrustyChunksModItems.MACHINE_CARBINE.get());
        GUNS.add(CrustyChunksModItems.LEVER_RIFLE.get());
        GUNS.add(CrustyChunksModItems.SEMI_AUTOMATIC_RIFLE_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.AUTOMATIC_RIFLE.get());
        GUNS.add(CrustyChunksModItems.BURST_RIFLE.get());
        GUNS.add(CrustyChunksModItems.LMG_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BATTLE_RIFLE.get());
        GUNS.add(CrustyChunksModItems.BOLT_ACTION_RIFLE_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BREAK_ACTION_SHOTGUN_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.PUMP_ACTION_SHOTGUN_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.BREECH_RIFLE.get());
        GUNS.add(CrustyChunksModItems.FLAME_THROWER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.ARMOR_PEELER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.ARMOR_PEELER_ANIMATED.get());
        GUNS.add(CrustyChunksModItems.GRENADE_LAUNCHER.get());
     }
}

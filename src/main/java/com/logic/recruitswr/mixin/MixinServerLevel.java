package com.logic.recruitswr.mixin;

import com.logic.recruitswr.entity.ParticleEntity;
import com.logic.recruitswr.particles.WRParticleData;
import com.logic.recruitswr.registries.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import oshi.util.tuples.Pair;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements WorldGenLevel {
    protected MixinServerLevel(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Override
    public void addParticle(ParticleOptions particleOptions, double x, double y, double z, double motionX, double motionY, double motionZ) {
        if(WRParticleData.particleSizeMap.containsKey(particleOptions) && WRParticleData.lifeTime.containsKey(particleOptions)) {
            ParticleEntity particleEntity = ModEntities.PARTICLE_ENTITY.get().create(this);

            particleEntity.setLifeTime(WRParticleData.lifeTime.get(particleOptions));

            Pair<Float, Float> floatFloatPair = WRParticleData.particleSizeMap.get(particleOptions);

            particleEntity.setDimensions(floatFloatPair.getA(), floatFloatPair.getB());

            this.addFreshEntity(particleEntity);
        }
    }
}

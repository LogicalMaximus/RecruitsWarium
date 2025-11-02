package com.logic.recruitswr.particles;

import net.mcreator.crustychunks.init.CrustyChunksModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class WRParticleData {
    private static final RandomSource random = RandomSource.create();

    public static final Map<ParticleType<?>, Pair<Float, Float>> particleSizeMap = new HashMap<>();
    public static final Map<ParticleType<?>, Integer> lifeTime = new HashMap<>();

    static {
        particleSizeMap.put(CrustyChunksModParticleTypes.LARGE_SMOKE.get(), new Pair<>(5.0F, 5.0F));
        lifeTime.put(CrustyChunksModParticleTypes.LARGE_SMOKE.get(), Math.max(1, 140 + (random.nextInt(160) - 80)));

        particleSizeMap.put(CrustyChunksModParticleTypes.FIREBALL.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.FIREBALL.get(), Math.max(1, 359 + (random.nextInt(78) - 39)));

        particleSizeMap.put(CrustyChunksModParticleTypes.SAND.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.SAND.get(), Math.max(1, 80 + (random.nextInt(20) - 10)));

        particleSizeMap.put(CrustyChunksModParticleTypes.DUST.get(), new Pair<>(0.3F, 1.0F));
        lifeTime.put(CrustyChunksModParticleTypes.DUST.get(), Math.max(1, 100 + (random.nextInt(20) - 10)));

        particleSizeMap.put(CrustyChunksModParticleTypes.WHITE_DUST.get(), new Pair<>(0.3F, 1.0F));
        lifeTime.put(CrustyChunksModParticleTypes.WHITE_DUST.get(), Math.max(1, 100 + (random.nextInt(20) - 10)));

        particleSizeMap.put(CrustyChunksModParticleTypes.SMOKE_SCREEN.get(), new Pair<>(3.0F, 45.0F));
        lifeTime.put(CrustyChunksModParticleTypes.SMOKE_SCREEN.get(), Math.max(1, 270 + (random.nextInt(160) - 80)));

        particleSizeMap.put(CrustyChunksModParticleTypes.PUFF.get(), new Pair<>(5.0F, 5.0F));
        lifeTime.put(CrustyChunksModParticleTypes.PUFF.get(), 140);

        particleSizeMap.put(CrustyChunksModParticleTypes.TRACER.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.TRACER.get(), 9);

        particleSizeMap.put(CrustyChunksModParticleTypes.CAMP_SMOKE.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.CAMP_SMOKE.get(), 119);

        particleSizeMap.put(CrustyChunksModParticleTypes.SMALL_TRACER.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.SMALL_TRACER.get(), 9);

        particleSizeMap.put(CrustyChunksModParticleTypes.SPARKS.get(), new Pair<>(0.3F, 0.3F));
        lifeTime.put(CrustyChunksModParticleTypes.SPARKS.get(), 39);

        particleSizeMap.put(CrustyChunksModParticleTypes.SMALL_PUFF.get(), new Pair<>(3.0F, 3.0F));
        lifeTime.put(CrustyChunksModParticleTypes.SMALL_PUFF.get(), Math.max(1, 59 + (random.nextInt(40) - 20)));

        particleSizeMap.put(CrustyChunksModParticleTypes.HUGE_SPARKS.get(), new Pair<>(0.5F, 0.5F));
        lifeTime.put(CrustyChunksModParticleTypes.HUGE_SPARKS.get(), 39);

        particleSizeMap.put(CrustyChunksModParticleTypes.SHOCK_WAVE.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.SHOCK_WAVE.get(), 45);

        particleSizeMap.put(CrustyChunksModParticleTypes.HUGE_FIREBALL.get(), new Pair<>(0.2F, 0.2F));
        lifeTime.put(CrustyChunksModParticleTypes.HUGE_FIREBALL.get(), Math.max(1, 630 + (random.nextInt(18) - 9)));

        particleSizeMap.put(CrustyChunksModParticleTypes.GROUND_HUGE_SMOKE.get(), new Pair<>(0.2F, 15.0F));
        lifeTime.put(CrustyChunksModParticleTypes.GROUND_HUGE_SMOKE.get(), Math.max(1, 3000 + (random.nextInt(1200) - 600)));

        particleSizeMap.put(CrustyChunksModParticleTypes.SPLASH_PUFF.get(), new Pair<>(5.0F, 5.0F));
        lifeTime.put(CrustyChunksModParticleTypes.SPLASH_PUFF.get(), 140);

        particleSizeMap.put(CrustyChunksModParticleTypes.SMOKE.get(), new Pair<>(1.0F, 1.0F));
        lifeTime.put(CrustyChunksModParticleTypes.SMOKE.get(), Math.max(1, 200 + (random.nextInt(10) - 5)));

        particleSizeMap.put(CrustyChunksModParticleTypes.GAS_CLOUD.get(), new Pair<>(3.0F, 45.0F));
        lifeTime.put(CrustyChunksModParticleTypes.GAS_CLOUD.get(), Math.max(1, 270 + (random.nextInt(160) - 80)));
    }
}

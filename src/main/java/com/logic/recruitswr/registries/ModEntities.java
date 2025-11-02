package com.logic.recruitswr.registries;

import com.logic.recruitswr.RecruitsWarium;
import com.logic.recruitswr.entity.ParticleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final RegistryObject<EntityType<ParticleEntity>> PARTICLE_ENTITY;

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RecruitsWarium.MODID);

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    static {
        PARTICLE_ENTITY = ENTITY_TYPES.register("particle_entity", () -> EntityType.Builder.of(ParticleEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).build("particle_entity"));

    }
}

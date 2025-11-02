package com.logic.recruitswr.client;

import com.logic.recruitswr.RecruitsWarium;
import com.logic.recruitswr.client.renderer.ParticleEntityRenderer;
import com.logic.recruitswr.registries.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RecruitsWarium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //MenuScreens.register(ModMenuTypes.SQUAD_SPAWNER_MENU.get(), SquadSpawnerScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(ModEntities.PARTICLE_ENTITY.get(), ParticleEntityRenderer::new);
    }
}

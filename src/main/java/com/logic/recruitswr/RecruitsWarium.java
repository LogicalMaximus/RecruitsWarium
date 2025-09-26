package com.logic.recruitswr;

import com.logic.recruitswr.commands.MercenaryPatrolSpawnCommand;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.events.MercenaryPatrolSpawner;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RecruitsWarium.MODID)
public class RecruitsWarium {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "recruitswr";

    private static final Logger LOGGER = LogUtils.getLogger();


    public RecruitsWarium() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, RecruitsWariumConfig.SPEC, "recruit-warium-server.toml");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new MercenaryPatrolSpawner());
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        MercenaryPatrolSpawnCommand.register(event.getDispatcher());
    }
}

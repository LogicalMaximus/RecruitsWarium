package com.logic.recruitswr.events;

import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.world.RecruitsPatrolSpawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class MercenaryPatrolSpawner {

    private static final Map<ServerLevel, RecruitsPatrolSpawn> MERCENARY_PATROL = new HashMap();

    private MinecraftServer server;

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        server = event.getServer();
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide) {
            Level var3 = event.level;
            if (var3 instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)var3;
                if ((Boolean) RecruitsServerConfig.ShouldRecruitPatrolsSpawn.get()) {
                    MERCENARY_PATROL.computeIfAbsent(serverWorld, (serverLevel) -> new RecruitsPatrolSpawn(serverWorld));
                    RecruitsPatrolSpawn spawner = (RecruitsPatrolSpawn) MERCENARY_PATROL.get(serverWorld);
                    spawner.tick();
                }
            }
        }
    }

}

package com.logic.recruitswr.commands;

import com.logic.recruitswr.patrol.MercenaryPatrolSpawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.talhanation.recruits.world.PillagerPatrolSpawn;
import com.talhanation.recruits.world.RecruitsPatrolSpawn;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class MercenaryPatrolSpawnCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalBuilder = (LiteralArgumentBuilder) Commands.literal("recruitswr").requires((source) -> source.hasPermission(2));
        literalBuilder.then((((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("recruitPatrol").then(Commands.literal("tiny").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnTinyPatrol(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))).then(Commands.literal("small").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnSmallPatrol(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))).then(Commands.literal("medium").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnMediumPatrol(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))).then(Commands.literal("large").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnLargePatrol(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))).then(Commands.literal("huge").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnHugePatrol(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))).then(Commands.literal("caravan").executes((commandSource) -> {
            MercenaryPatrolSpawn.spawnCaravan(((CommandSourceStack)commandSource.getSource()).getEntity().getOnPos().above(), ((CommandSourceStack)commandSource.getSource()).getLevel());
            return 0;
        }))));
        dispatcher.register(literalBuilder);
    }
}

package com.logic.recruitswr.patrol;

import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.*;
import com.talhanation.recruits.entities.ai.villager.FollowCaravanOwner;
import com.talhanation.recruits.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.Heightmap;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class MercenaryPatrolSpawn {
    public static final Random random = new Random();
    public static int timer;
    public static double chance;
    public final ServerLevel world;

    public MercenaryPatrolSpawn(ServerLevel level) {
        this.world = level;
        timer = getSpawnInterval();
        chance = (Double) RecruitsServerConfig.RecruitPatrolsSpawnChance.get();
    }

    public void tick() {
        if (timer > 0) {
            --timer;
        }

        if (timer <= 0) {
            if (this.world.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING) && RecruitsWariumConfig.SHOULD_MERCENARIES_SPAWN.get()) {
                double rnd = (double)random.nextInt(100);
                if (rnd <= chance && attemptSpawnPatrol(this.world)) {
                }
            }

            timer = getSpawnInterval();
        }

    }

    public static boolean attemptSpawnPatrol(ServerLevel world) {
        Player player = world.getRandomPlayer();
        if (player == null) {
            return true;
        } else {
            if (!player.getCommandSenderWorld().dimensionType().hasRaids()) {
                player = world.getRandomPlayer();
            }

            BlockPos blockpos = new BlockPos(player.getOnPos());
            BlockPos blockpos2 = func_221244_a(blockpos, 90, random, world);
            if (blockpos2 != null && func_226559_a_(blockpos2, world) && blockpos2.distSqr(blockpos) > (double)200.0F) {
                BlockPos upPos = new BlockPos(blockpos2.getX(), blockpos2.getY() + 2, blockpos2.getZ());
                int i = random.nextInt(13);
                switch (i) {
                    case 0:
                    case 9:
                        spawnSmallPatrol(upPos, world);
                        break;
                    case 1:
                    case 2:
                        spawnLargePatrol(upPos, world);
                        break;
                    case 3:
                    case 4:
                        spawnHugePatrol(upPos, world);
                        break;
                    case 5:
                        spawnPatrol(upPos, world);
                    case 6:
                        spawnTinyPatrol(upPos, world);
                        break;
                    case 7:
                    case 8:
                        spawnRoadPatrol(upPos, world);
                        break;
                    case 10:
                    case 11:
                        spawnMediumPatrol(upPos, world);
                        break;
                    default:
                        spawnCaravan(upPos, world);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static int getSpawnInterval() {
        int minutes = (Integer)RecruitsServerConfig.RecruitPatrolSpawnInterval.get();
        return 1200 * minutes;
    }

    public static void spawnCaravan(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Caravan Leader");
        createVillager(world, upPos, patrolLeader);
        Villager villagerGuide = createVillager(world, upPos, patrolLeader);
        createLlama(world, upPos, villagerGuide);
        createLlama(world, upPos, villagerGuide);
        Villager villagerGuide2 = createVillager(world, upPos, patrolLeader);
        createMule(world, upPos, villagerGuide2);
        createMule(world, upPos, villagerGuide2);
        Villager villagerGuide3 = createVillager(world, upPos, patrolLeader);
        createHorse(world, upPos, villagerGuide3);
        createHorse(world, upPos, villagerGuide3);
        Villager villagerGuide4 = createVillager(world, upPos, patrolLeader);
        createMule(world, upPos, villagerGuide4);
        createMule(world, upPos, villagerGuide4);
        createPatrolRecruit(world, upPos, patrolLeader, "Caravan Guard");
        createPatrolRecruit(world, upPos, patrolLeader, "Caravan Guard");
        createPatrolRecruit(world, upPos, patrolLeader, "Caravan Guard");
        createPatrolShieldman(world, upPos, patrolLeader, "Caravan Guard", false);
        createPatrolShieldman(world, upPos, patrolLeader, "Caravan Guard", true);
        createPatrolHorseman(world, upPos, patrolLeader, "Caravan Guard", true);
        createPatrolHorseman(world, upPos, patrolLeader, "Caravan Guard", false);
        createPatrolHorseman(world, upPos, patrolLeader, "Caravan Guard", false);
        createPatrolNomad(world, upPos, patrolLeader, "Caravan Guard");
        createPatrolNomad(world, upPos, patrolLeader, "Caravan Guard");
        createPatrolNomad(world, upPos, patrolLeader, "Caravan Guard");
        createVillager(world, upPos, patrolLeader);
        createVillager(world, upPos, patrolLeader);
        createWanderingTrader(world, upPos, patrolLeader);
        createWanderingTrader(world, upPos, patrolLeader);
    }

    public static void createWanderingTrader(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader) {
        WanderingTrader villager = (WanderingTrader) EntityType.WANDERING_TRADER.create(world);
        villager.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        villager.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        villager.setPersistenceRequired();
        villager.goalSelector.addGoal(0, new FollowCaravanOwner(villager, patrolLeader.getUUID()));
        world.addFreshEntity(villager);
    }

    public static void createHorse(ServerLevel world, BlockPos upPos, Villager villager) {
        Horse horse = (Horse)EntityType.HORSE.create(world);
        horse.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        horse.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        horse.setPersistenceRequired();
        horse.setTamed(true);
        horse.equipSaddle((SoundSource)null);
        horse.setLeashedTo(villager, true);
        world.addFreshEntity(horse);
    }

    public static void createLlama(ServerLevel world, BlockPos upPos, Villager villager) {
        Llama llama = (Llama)EntityType.LLAMA.create(world);
        llama.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        llama.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        llama.setPersistenceRequired();
        llama.setTamed(true);
        llama.setChest(true);
        llama.getPersistentData().putInt("Strength", 5);
        llama.setLeashedTo(villager, true);
        llama.getPersistentData().putBoolean("Caravan", true);

        world.addFreshEntity(llama);
    }

    public static Villager createVillager(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader) {
        Villager villager = (Villager)EntityType.VILLAGER.create(world);
        villager.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        villager.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        villager.setPersistenceRequired();
        villager.goalSelector.addGoal(0, new FollowCaravanOwner(villager, patrolLeader.getUUID()));
        world.addFreshEntity(villager);
        return villager;
    }

    public static void createMule(ServerLevel world, BlockPos upPos, LivingEntity villager) {
        Mule mule = (Mule)EntityType.MULE.create(world);
        mule.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        mule.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        mule.setPersistenceRequired();
        mule.setTamed(true);
        mule.setChest(true);
        mule.setLeashedTo(villager, true);
        mule.getPersistentData().putBoolean("Caravan", true);

        world.addFreshEntity(mule);
    }

    public static void spawnHugePatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", false);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", false);
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
    }

    public static void spawnLargePatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
    }

    public static void spawnMediumPatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolCrossbowman(world, upPos, patrolLeader);
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
    }

    public static void spawnSmallPatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolBowman(world, upPos, patrolLeader);
        createPatrolBowman(world, upPos, patrolLeader);
    }

    public static void spawnTinyPatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolShieldman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolBowman(world, upPos, patrolLeader);
    }

    public static void spawnRoadPatrol(BlockPos upPos, ServerLevel world) {
        RecruitEntity patrolLeader = createPatrolLeader(world, upPos, "Mercenary Leader");
        createPatrolRecruit(world, upPos, patrolLeader, "Mercenary");
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
        createPatrolHorseman(world, upPos, patrolLeader, "Mercenary", true);
        createPatrolNomad(world, upPos, patrolLeader, "Mercenary");
    }

    @Nullable
    public static BlockPos func_221244_a(BlockPos p_221244_1_, int spread, Random random, ServerLevel world) {
        BlockPos blockpos = null;

        for(int i = 0; i < 10; ++i) {
            int j = p_221244_1_.getX() + random.nextInt(spread * 2) - spread;
            int k = p_221244_1_.getZ() + random.nextInt(spread * 2) - spread;
            int l = world.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, world, blockpos1, EntityType.WANDERING_TRADER)) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }

    public static boolean func_226559_a_(BlockPos p_226559_1_, ServerLevel world) {
        for(BlockPos blockpos : BlockPos.betweenClosed(p_226559_1_, p_226559_1_.offset(1, 2, 1))) {
            if (!world.getBlockState(blockpos).getBlockSupportShape(world, blockpos).isEmpty() || !world.getFluidState(blockpos).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static void setRecruitFood(AbstractRecruitEntity recruit) {
        setRecruitFood(recruit, 0);
    }

    public static void setRecruitFood(AbstractRecruitEntity recruit, int bonus) {
        int k = random.nextInt(8);
        ItemStack food;
        switch (k) {
            case 1 -> food = new ItemStack(Items.COOKED_COD);
            case 2 -> food = new ItemStack(Items.MELON_SLICE);
            case 3 -> food = new ItemStack(Items.COOKED_RABBIT);
            case 4 -> food = new ItemStack(Items.COOKED_BEEF);
            case 5 -> food = new ItemStack(Items.COOKED_CHICKEN);
            case 6 -> food = new ItemStack(Items.COOKED_MUTTON);
            case 7 -> food = new ItemStack(Items.BAKED_POTATO);
            default -> food = new ItemStack(Items.BREAD);
        }

        int i = random.nextInt(14);
        food.setCount(6 + i + bonus);
        recruit.inventory.setItem(7, food);
    }

    public static RecruitEntity createPatrolLeader(ServerLevel world, BlockPos upPos, String name) {
        RecruitEntity patrolLeader = (RecruitEntity)((EntityType) ModEntityTypes.RECRUIT.get()).create(world);
        patrolLeader.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        patrolLeader.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        patrolLeader.setPersistenceRequired();
        patrolLeader.setXpLevel(1 + random.nextInt(2));
        patrolLeader.addLevelBuffsForLevel(patrolLeader.getXpLevel());
        patrolLeader.setHunger(100.0F);
        patrolLeader.setMoral(100.0F);
        patrolLeader.setCost(55);
        patrolLeader.setState(2);
        patrolLeader.setXp(random.nextInt(200));
        patrolLeader.setCustomName(Component.literal(name));
        patrolLeader.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        patrolLeader.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        setRecruitFood(patrolLeader);
        world.addFreshEntity(patrolLeader);
        return patrolLeader;
    }

    public static void createPatrolRecruit(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader, String name) {
        RecruitEntity recruitEntity = (RecruitEntity)((EntityType)ModEntityTypes.RECRUIT.get()).create(world);
        recruitEntity.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        recruitEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        recruitEntity.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        recruitEntity.setPersistenceRequired();
        recruitEntity.setXpLevel(Math.max(1, random.nextInt(3)));
        recruitEntity.addLevelBuffsForLevel(recruitEntity.getXpLevel());
        recruitEntity.setHunger(80.0F);
        recruitEntity.setMoral(65.0F);
        recruitEntity.setCost(9);
        recruitEntity.setState(2);
        recruitEntity.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        recruitEntity.setShouldProtect(true);
        recruitEntity.setXp(random.nextInt(80));
        recruitEntity.setCustomName(Component.literal(name));
        setRecruitFood(recruitEntity);
        world.addFreshEntity(recruitEntity);
    }

    public static void createPatrolBowman(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader) {
        BowmanEntity bowman = (BowmanEntity)((EntityType)ModEntityTypes.BOWMAN.get()).create(world);
        bowman.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        bowman.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        bowman.setPersistenceRequired();
        bowman.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        bowman.setXpLevel(Math.max(1, random.nextInt(3)));
        bowman.addLevelBuffsForLevel(bowman.getXpLevel());
        bowman.setHunger(80.0F);
        bowman.setMoral(65.0F);
        bowman.setCost(16);
        bowman.setState(2);
        bowman.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        bowman.setShouldProtect(true);
        bowman.setXp(random.nextInt(120));
        bowman.setCustomName(Component.literal("Mercenary"));
        setRecruitFood(bowman);
        world.addFreshEntity(bowman);
    }

    public static void createPatrolShieldman(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader, String name, boolean banner) {
        RecruitShieldmanEntity shieldmanEntity = (RecruitShieldmanEntity)((EntityType)ModEntityTypes.RECRUIT_SHIELDMAN.get()).create(world);
        shieldmanEntity.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        shieldmanEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        shieldmanEntity.setPersistenceRequired();
        shieldmanEntity.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        shieldmanEntity.setXpLevel(Math.max(1, random.nextInt(3)));
        shieldmanEntity.addLevelBuffsForLevel(shieldmanEntity.getXpLevel());
        shieldmanEntity.setHunger(80.0F);
        shieldmanEntity.setMoral(65.0F);
        shieldmanEntity.setCost(12);
        shieldmanEntity.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        shieldmanEntity.setShouldProtect(true);
        shieldmanEntity.setState(2);
        shieldmanEntity.setXp(random.nextInt(120));
        shieldmanEntity.setCustomName(Component.literal(name));
        if (banner) {
            ItemStack stack = new ItemStack(Items.GREEN_BANNER);
            stack.setCount(1);
            shieldmanEntity.setItemSlot(EquipmentSlot.HEAD, stack);
        }

        setRecruitFood(shieldmanEntity);
        world.addFreshEntity(shieldmanEntity);
    }

    public static void createPatrolHorseman(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader, String name, boolean banner) {
        HorsemanEntity horseman = (HorsemanEntity)((EntityType)ModEntityTypes.HORSEMAN.get()).create(world);
        horseman.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        horseman.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        horseman.setPersistenceRequired();
        horseman.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        horseman.isPatrol = true;
        horseman.setXpLevel(Math.max(1, random.nextInt(3)));
        horseman.addLevelBuffsForLevel(horseman.getXpLevel());
        horseman.setHunger(80.0F);
        horseman.setMoral(75.0F);
        horseman.setCost(30);
        horseman.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        horseman.setShouldProtect(true);
        horseman.setState(2);
        horseman.setXp(random.nextInt(120));
        horseman.setCustomName(Component.literal(name));
        if (banner) {
            ItemStack stack = new ItemStack(Items.GREEN_BANNER);
            stack.setCount(1);
            horseman.setItemSlot(EquipmentSlot.HEAD, stack);
        }

        setRecruitFood(horseman);
        world.addFreshEntity(horseman);
    }

    public static void createPatrolNomad(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader, String name) {
        NomadEntity nomad = (NomadEntity)((EntityType)ModEntityTypes.NOMAD.get()).create(world);
        nomad.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        nomad.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        nomad.setPersistenceRequired();
        nomad.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        nomad.isPatrol = true;
        nomad.setXpLevel(1 + random.nextInt(3));
        nomad.addLevelBuffsForLevel(nomad.getXpLevel());
        nomad.setHunger(80.0F);
        nomad.setMoral(75.0F);
        nomad.setCost(30);
        nomad.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        nomad.setShouldProtect(true);
        nomad.setXp(random.nextInt(120));
        nomad.setCustomName(Component.literal(name));
        nomad.setState(2);
        setRecruitFood(nomad);
        world.addFreshEntity(nomad);
    }

    public static void createPatrolCrossbowman(ServerLevel world, BlockPos upPos, RecruitEntity patrolLeader) {
        CrossBowmanEntity crossBowman = (CrossBowmanEntity)((EntityType)ModEntityTypes.CROSSBOWMAN.get()).create(world);
        crossBowman.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        crossBowman.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        crossBowman.setPersistenceRequired();
        crossBowman.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        crossBowman.setXpLevel(Math.max(1, random.nextInt(3)));
        crossBowman.addLevelBuffsForLevel(crossBowman.getXpLevel());
        crossBowman.setHunger(80.0F);
        crossBowman.setMoral(65.0F);
        crossBowman.setCost(16);
        crossBowman.setProtectUUID(Optional.of(patrolLeader.getUUID()));
        crossBowman.setShouldProtect(true);
        crossBowman.setXp(random.nextInt(120));
        crossBowman.setState(2);
        crossBowman.setCustomName(Component.literal("Mercenary"));
        setRecruitFood(crossBowman);
        world.addFreshEntity(crossBowman);
    }

    public static void createRecruit(ServerLevel world, BlockPos upPos, AbstractLeaderEntity leader) {
        RecruitEntity recruitEntity = (RecruitEntity)((EntityType)ModEntityTypes.RECRUIT.get()).create(world);
        recruitEntity.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        recruitEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);

        recruitEntity.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        recruitEntity.setPersistenceRequired();
        recruitEntity.setXpLevel(Math.max(1, random.nextInt(3)));
        recruitEntity.addLevelBuffsForLevel(recruitEntity.getXpLevel());
        recruitEntity.setHunger(80.0F);
        recruitEntity.setMoral(65.0F);
        recruitEntity.setState(2);
        recruitEntity.setCost(9);
        recruitEntity.setXp(random.nextInt(80));
        recruitEntity.setCustomName(Component.literal("Mercenary"));
        setRecruitFood(recruitEntity);
        world.addFreshEntity(recruitEntity);
    }

    public static PatrolLeaderEntity createCompanionPatrolLeader(BlockPos upPos, ServerLevel world) {
        PatrolLeaderEntity leader = (PatrolLeaderEntity)((EntityType)ModEntityTypes.PATROL_LEADER.get()).create(world);
        leader.moveTo((double)upPos.getX() + (double)0.5F, (double)upPos.getY() + (double)0.5F, (double)upPos.getZ() + (double)0.5F, random.nextFloat() * 360.0F - 180.0F, 0.0F);
        leader.finalizeSpawn(world, world.getCurrentDifficultyAt(upPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
        AbstractRecruitEntity.applySpawnValues(leader);

        leader.setXpLevel(Math.max(1, random.nextInt(4)));
        leader.despawnTimer = (Integer)RecruitsServerConfig.RecruitPatrolDespawnTime.get() * 20 * 60;
        setRecruitFood(leader);
        leader.addLevelBuffsForLevel(leader.getXpLevel());
        leader.setHunger(80.0F);
        leader.setMoral(65.0F);
        leader.setCost(50);
        leader.setXp(random.nextInt(120));
        leader.setState(2);
        leader.setCustomName(Component.literal("Mercenary Leader"));
        return leader;
    }

    public static void spawnPatrol(BlockPos upPos, ServerLevel world) {
        PatrolLeaderEntity leader = createCompanionPatrolLeader(upPos, world);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        createRecruit(world, upPos, leader);
        world.addFreshEntity(leader);
    }

}

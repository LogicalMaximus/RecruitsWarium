package com.logic.recruitswr.utils;

import net.mcreator.crustychunks.procedures.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class RecruitsWariumSoundUtils {

    public static void playMediumRifleSounds(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        CasingDropProcedure.execute(world, x,y,z);
        MediumFireSoundProcedure.execute(world, x,y,z);
    }

    public static void playPistolSounds(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        SmallCasingDropProcedure.execute(world, x,y,z);
        PistolFireSoundProcedure.execute(world, x,y,z);
    }

    public static void playShotgunSounds(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        ShotgunFireSoundProcedure.execute(world, x, y, z);
    }

    public static void playLargeRifleSounds(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        CasingDropProcedure.execute(world, x, y, z);
        LargeFireSoundProcedure.execute(world, x, y, z);
    }


    
}

package com.logic.recruitswr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RecruitsWariumConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> ADDITIONAL_SHOOT_DELAY;

    public static final ForgeConfigSpec.ConfigValue<Integer> AIMER_NODE_RADIUS;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTOMATIC_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTOMATIC_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTOMATIC_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTO_PISTOL_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTO_PISTOL_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_AUTO_PISTOL_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PISTOL_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PISTOL_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PISTOL_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_STEALTH_PISTOL_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_STEALTH_PISTOL_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_STEALTH_PISTOL_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_REVOLVER_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_REVOLVER_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_REVOLVER_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SUBMACHINEGUN_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SUBMACHINEGUN_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SUBMACHINEGUN_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_MACHINE_CARBINE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_MACHINE_CARBINE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_MACHINE_CARBINE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVER_ACTION_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVER_ACTION_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LEVER_ACTION_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SEMI_AUTOMATIC_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SEMI_AUTOMATIC_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SEMI_AUTOMATIC_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BURST_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BURST_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BURST_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LIGHT_MACHINE_GUN_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LIGHT_MACHINE_GUN_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_LIGHT_MACHINE_GUN_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLE_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLE_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLE_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BOLT_ACTION_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BOLT_ACTION_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BOLT_ACTION_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREAK_ACTION_SHOTGUN_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREAK_ACTION_SHOTGUN_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREAK_ACTION_SHOTGUN_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PUMP_ACTION_SHOTGUN_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PUMP_ACTION_SHOTGUN_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_PUMP_ACTION_SHOTGUN_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ANTI_MATERIAL_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ANTI_MATERIAL_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ANTI_MATERIAL_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREECH_RIFLE_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREECH_RIFLE_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_BREECH_RIFLE_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ARMOR_PEELER_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ARMOR_PEELER_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ARMOR_PEELER_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Boolean> MAGIC_TURRET_SHOOT;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_FLAME_THROWER_AMMO;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_FLAME_THROWER_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_FLAME_THROWER_RELOAD;

    public static final ForgeConfigSpec.ConfigValue<Float> AIM_INACCURACY;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_TARGET_MONSTERS;

    static {
        BUILDER.push("Recruit Warium Server Config");

        ADDITIONAL_SHOOT_DELAY = BUILDER.comment("\nAn Additional Delay Added To The Weapon Cooldown To Create The Max Weapon Cooldown \n\t(takes effect after restart)").define("AdditionalShootDelay", 3);

        AIMER_NODE_RADIUS = BUILDER.comment("\nThe Radius That The Aimer Will Look For Trigger Nodes At Target \n\t(takes effect after restart)").define("AimerNodeRadius", 16);

        MAGIC_TURRET_SHOOT = BUILDER.comment("\nWhether The Recruit Can Shoot Turrets Without Redstone Activation \n\t(takes effect after restart)").define("MagicTurretShoot", true);

        MAX_AUTOMATIC_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For Automatic Rifle With The Recruits \n\t(takes effect after restart)").define("MaxAutomaticRifleAmmo", 30);
        MAX_AUTOMATIC_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For Automatic Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAutomaticRifleCooldown", 4);
        MAX_AUTOMATIC_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For Automatic Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAutomaticRifleReload", 60);

        MAX_AUTO_PISTOL_AMMO = BUILDER.comment("\nThe Amount Of Ammo For Automatic Pistol With The Recruits \n\t(takes effect after restart)").define("MaxAutomaticPistolAmmo", 15);
        MAX_AUTO_PISTOL_COOLDOWN = BUILDER.comment("\nThe Cooldown For Automatic Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAutomaticPistolCooldown", 5);
        MAX_AUTO_PISTOL_RELOAD = BUILDER.comment("\nThe Reload Time For Automatic Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAutomaticPistolReload", 30);

        MAX_PISTOL_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Pistol With The Recruits \n\t(takes effect after restart)").define("MaxPistolAmmo", 15);
        MAX_PISTOL_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxPistolCooldown", 12);
        MAX_PISTOL_RELOAD = BUILDER.comment("\nThe Reload Time For The Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxPistolReload", 20);

        MAX_STEALTH_PISTOL_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Stealth Pistol With The Recruits \n\t(takes effect after restart)").define("MaxStealthPistolAmmo", 15);
        MAX_STEALTH_PISTOL_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Stealth Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxStealthPistolCooldown", 12);
        MAX_STEALTH_PISTOL_RELOAD = BUILDER.comment("\nThe Reload Time For The Stealth Pistol With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxStealthPistolReload", 30);

        MAX_REVOLVER_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Revolver With The Recruits \n\t(takes effect after restart)").define("MaxRevolverAmmo", 8);
        MAX_REVOLVER_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Revolver With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxRevolverCooldown", 7);
        MAX_REVOLVER_RELOAD = BUILDER.comment("\nThe Reload Time For The Revolver With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxRevolverReload", 45);

        MAX_SUBMACHINEGUN_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Sub Machine Gun With The Recruits \n\t(takes effect after restart)").define("MaxSubMachineGunAmmo", 40);
        MAX_SUBMACHINEGUN_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Sub Machine Gun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxSubMachineGunCooldown", 3);
        MAX_SUBMACHINEGUN_RELOAD = BUILDER.comment("\nThe Reload Time For The Sub Machine Gun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxSubMachineGunReload", 35);

        MAX_MACHINE_CARBINE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Machine Carbine With The Recruits \n\t(takes effect after restart)").define("MaxMachineCarbineAmmo", 40);
        MAX_MACHINE_CARBINE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Machine Carbine With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxMachineCarbineCooldown", 10);
        MAX_MACHINE_CARBINE_RELOAD = BUILDER.comment("\nThe Reload Time For The Machine Carbine With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxMachineCarbineReload", 45);

        MAX_LEVER_ACTION_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Lever Action With The Recruits \n\t(takes effect after restart)").define("MaxLeverActionAmmo", 10);
        MAX_LEVER_ACTION_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Lever Action With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxLeverActionCooldown", 15);
        MAX_LEVER_ACTION_RELOAD = BUILDER.comment("\nThe Reload Time For The Lever Action With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxLeverActionReload", 80);

        MAX_SEMI_AUTOMATIC_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For Semi-Automatic Rifle With The Recruits \n\t(takes effect after restart)").define("MaxSemiAutomaticRifleAmmo", 30);
        MAX_SEMI_AUTOMATIC_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For Semi-Automatic Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxSemiAutomaticRifleCooldown", 12);
        MAX_SEMI_AUTOMATIC_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For Semi-Automatic Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxSemiAutomaticRifleReload", 70);

        MAX_BURST_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Burst Rifle With The Recruits \n\t(takes effect after restart)").define("MaxBurstRifleAmmo", 30);
        MAX_BURST_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Burst Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBurstRifleCooldown", 25);
        MAX_BURST_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For The Burst Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBurstRifleReload", 60);

        MAX_LIGHT_MACHINE_GUN_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Light Machine Gun With The Recruits \n\t(takes effect after restart)").define("MaxLightMachineGunAmmo", 100);
        MAX_LIGHT_MACHINE_GUN_COOLDOWN = BUILDER.comment("\nThe Cooldown For Light Machine Gun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxLightMachineGunCooldown", 3);
        MAX_LIGHT_MACHINE_GUN_RELOAD = BUILDER.comment("\nThe Reload Time For Light Machine Gun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxLightMachineGunReload", 120);

        MAX_BATTLE_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Battle Rifle With The Recruits \n\t(takes effect after restart)").define("MaxBattleRifleAmmo", 20);
        MAX_BATTLE_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Battle Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBattleRifleCooldown", 14);
        MAX_BATTLE_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For The Battle Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBattleRifleReload", 60);

        MAX_BOLT_ACTION_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Bolt Action Rifle With The Recruits \n\t(takes effect after restart)").define("MaxBoltActionRifleAmmo", 8);
        MAX_BOLT_ACTION_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Bolt Action Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBoltActionRifleCooldown", 12);
        MAX_BOLT_ACTION_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For The Bolt Action Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBoltActionRifleReload", 40);

        MAX_BREAK_ACTION_SHOTGUN_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Break Action Shotgun With The Recruits \n\t(takes effect after restart)").define("MaxBreakActionShotgunAmmo", 2);
        MAX_BREAK_ACTION_SHOTGUN_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Break Action Shotgun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBreakActionShotgunCooldown", 12);
        MAX_BREAK_ACTION_SHOTGUN_RELOAD = BUILDER.comment("\nThe Reload Time For Break Action Shotgun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBreakActionShotgunReload", 70);

        MAX_PUMP_ACTION_SHOTGUN_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Pump Action Shotgun With The Recruits \n\t(takes effect after restart)").define("MaxPumpActionShotgunAmmo", 8);
        MAX_PUMP_ACTION_SHOTGUN_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Pump Action Shotgun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxPumpActionShotgunCooldown", 10);
        MAX_PUMP_ACTION_SHOTGUN_RELOAD = BUILDER.comment("\nThe Reload Time For Pump Action Shotgun With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxPumpActionShotgunReload", 50);

        MAX_ANTI_MATERIAL_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Anti-Material Rifle With The Recruits \n\t(takes effect after restart)").define("MaxAntiMaterialRifleAmmo", 1);
        MAX_ANTI_MATERIAL_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Anti-Material Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAntiMaterialRifleCooldown", 10);
        MAX_ANTI_MATERIAL_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For Anti-Material Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxAntiMaterialRifleReload", 80);

        MAX_BREECH_RIFLE_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Breech Rifle With The Recruits \n\t(takes effect after restart)").define("MaxBreechRifleAmmo", 1);
        MAX_BREECH_RIFLE_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Breech Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBreechRifleCooldown", 10);
        MAX_BREECH_RIFLE_RELOAD = BUILDER.comment("\nThe Reload Time For The Breech Rifle With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxBreechRifleReload", 90);

        MAX_ARMOR_PEELER_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Armor Peeler Launcher With The Recruits \n\t(takes effect after restart)").define("MaxArmorPeelerAmmo", 1);
        MAX_ARMOR_PEELER_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Armor Peeler Launcher With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxArmorPeelerCooldown", 10);
        MAX_ARMOR_PEELER_RELOAD = BUILDER.comment("\nThe Reload Time For The Armor Peeler Launcher With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxArmorPeelerReload", 120);

        MAX_FLAME_THROWER_AMMO = BUILDER.comment("\nThe Amount Of Ammo For The Flame Thrower With The Recruits \n\t(takes effect after restart)").define("MaxFlameThrowerAmmo", 1000);
        MAX_FLAME_THROWER_COOLDOWN = BUILDER.comment("\nThe Cooldown For The Flame Thrower With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxFlameThrowerCooldown", 1);
        MAX_FLAME_THROWER_RELOAD = BUILDER.comment("\nThe Reload Time For The Flame Thrower With The Recruits In Ticks \n\t(takes effect after restart)").define("MaxFlameThrowerReload", 120);

        AIM_INACCURACY = BUILDER.comment("\nInaccuracy Added To The Recruits Aim So They Don't Have Aimbot \n\t(takes effect after restart)").define("AimInaccuracy", 0.8F);

        SHOULD_TARGET_MONSTERS = BUILDER.comment("\nWhether Recruits Should Target Monsters By Default, This includes Warium AI Robots \n\t(takes effect after restart)").define("ShouldRecruitsTargetMonsters", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

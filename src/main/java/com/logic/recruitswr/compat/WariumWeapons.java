package com.logic.recruitswr.compat;

import com.logic.recruitswr.compat.weapons.extra_large.BreechRifleWeapon;
import com.logic.recruitswr.compat.weapons.flame.FlameThrowerWeapon;
import com.logic.recruitswr.compat.weapons.large.BattleRifleWeapon;
import com.logic.recruitswr.compat.weapons.large.BoltActionRifleWeapon;
import com.logic.recruitswr.compat.weapons.medium.*;
import com.logic.recruitswr.compat.weapons.rocket.ArmorPeelerLauncherWeapon;
import com.logic.recruitswr.compat.weapons.shell.BreakActionShotgunWeapon;
import com.logic.recruitswr.compat.weapons.shell.PumpActionShotgunWeapon;
import com.logic.recruitswr.compat.weapons.small.*;
import com.logic.recruitswr.compat.weapons.very_large.AntiMaterialRifleWeapon;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class WariumWeapons {
    private static final HashMap<Item, WariumWeapon> ITEM_TO_WEAPON = new HashMap<>();

    public static final AutomaticRifleWeapon AUTOMATIC_RIFLE_WEAPON = new AutomaticRifleWeapon();

    public static final AutomaticPistolWeapon AUTOMATIC_PISTOL_WEAPON = new AutomaticPistolWeapon();

    public static final PistolWeapon PISTOL_WEAPON = new PistolWeapon();

    public static final StealthPistolWeapon STEALTH_PISTOL_WEAPON = new StealthPistolWeapon();

    public static final RevolverWeapon REVOLVER_WEAPON = new RevolverWeapon();

    public static final SubmachineGunWeapon SUBMACHINE_GUN_WEAPON = new SubmachineGunWeapon();

    public static final MachineCarbineWeapon MACHINE_CARBINE_WEAPON = new MachineCarbineWeapon();

    public static final LeverActionWeapon LEVER_ACTION_WEAPON = new LeverActionWeapon();

    public static final SemiAutomaticRifleWeapon SEMI_AUTOMATIC_RIFLE_WEAPON = new SemiAutomaticRifleWeapon();

    public static final BurstRifleWeapon BURST_RIFLE_WEAPON = new BurstRifleWeapon();

    public static final LightMachineGunWeapon LIGHT_MACHINE_GUN_WEAPON = new LightMachineGunWeapon();

    public static final BattleRifleWeapon BATTLE_RIFLE_WEAPON = new BattleRifleWeapon();

    public static final BoltActionRifleWeapon BOLT_ACTION_RIFLE_WEAPON = new BoltActionRifleWeapon();

    public static final BreakActionShotgunWeapon BREAK_ACTION_SHOTGUN_WEAPON = new BreakActionShotgunWeapon();

    public static final PumpActionShotgunWeapon PUMP_ACTION_SHOTGUN_WEAPON = new PumpActionShotgunWeapon();

    public static final AntiMaterialRifleWeapon ANTI_MATERIAL_RIFLE_WEAPON = new AntiMaterialRifleWeapon();

    public static final BreechRifleWeapon BREECH_RIFLE_WEAPON = new BreechRifleWeapon();

    public static final ArmorPeelerLauncherWeapon ARMOR_PEELER_LAUNCHER_WEAPON = new ArmorPeelerLauncherWeapon();

    public static final FlameThrowerWeapon FLAME_THROWER_WEAPON = new FlameThrowerWeapon();

    public static final BoltActionRifleWeapon SCOPE_BOLT_ACTION_RIFLE_WEAPON = new BoltActionRifleWeapon();

    public static final BreechRifleWeapon SCOPE_BREECH_RIFLE_WEAPON = new BreechRifleWeapon();

    public static WariumWeapon getWeaponFromItem(Item item) {
        return ITEM_TO_WEAPON.get(item);
    }

    static {
        ITEM_TO_WEAPON.put(AUTOMATIC_RIFLE_WEAPON.getWeapon(), AUTOMATIC_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(AUTOMATIC_PISTOL_WEAPON.getWeapon(), AUTOMATIC_PISTOL_WEAPON);
        ITEM_TO_WEAPON.put(PISTOL_WEAPON.getWeapon(), PISTOL_WEAPON);
        ITEM_TO_WEAPON.put(STEALTH_PISTOL_WEAPON.getWeapon(), STEALTH_PISTOL_WEAPON);
        ITEM_TO_WEAPON.put(REVOLVER_WEAPON.getWeapon(), REVOLVER_WEAPON);
        ITEM_TO_WEAPON.put(SUBMACHINE_GUN_WEAPON.getWeapon(), SUBMACHINE_GUN_WEAPON);
        ITEM_TO_WEAPON.put(MACHINE_CARBINE_WEAPON.getWeapon(), MACHINE_CARBINE_WEAPON);
        ITEM_TO_WEAPON.put(LEVER_ACTION_WEAPON.getWeapon(), LEVER_ACTION_WEAPON);
        ITEM_TO_WEAPON.put(SEMI_AUTOMATIC_RIFLE_WEAPON.getWeapon(), SEMI_AUTOMATIC_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(BURST_RIFLE_WEAPON.getWeapon(), BURST_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(LIGHT_MACHINE_GUN_WEAPON.getWeapon(), LIGHT_MACHINE_GUN_WEAPON);
        ITEM_TO_WEAPON.put(BATTLE_RIFLE_WEAPON.getWeapon(), BATTLE_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(BOLT_ACTION_RIFLE_WEAPON.getWeapon(), BOLT_ACTION_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(BREAK_ACTION_SHOTGUN_WEAPON.getWeapon(), BREAK_ACTION_SHOTGUN_WEAPON);
        ITEM_TO_WEAPON.put(PUMP_ACTION_SHOTGUN_WEAPON.getWeapon(), PUMP_ACTION_SHOTGUN_WEAPON);
        ITEM_TO_WEAPON.put(ANTI_MATERIAL_RIFLE_WEAPON.getWeapon(), ANTI_MATERIAL_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(BREECH_RIFLE_WEAPON.getWeapon(), BREECH_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(ARMOR_PEELER_LAUNCHER_WEAPON.getWeapon(), ARMOR_PEELER_LAUNCHER_WEAPON);
        ITEM_TO_WEAPON.put(FLAME_THROWER_WEAPON.getWeapon(), FLAME_THROWER_WEAPON);
        ITEM_TO_WEAPON.put(CrustyChunksModItems.SCOPED_BREECH_RIFLE.get(), SCOPE_BREECH_RIFLE_WEAPON);
        ITEM_TO_WEAPON.put(CrustyChunksModItems.SCOPED_BOLT_ACTION_RIFLE_ANIMATED.get(), SCOPE_BOLT_ACTION_RIFLE_WEAPON);
    }

}

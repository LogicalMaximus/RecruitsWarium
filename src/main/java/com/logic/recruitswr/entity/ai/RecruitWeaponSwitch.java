package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

public class RecruitWeaponSwitch<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;

    private int gunSlot = 0;

    private ItemStack itemStack;

    public RecruitWeaponSwitch(T mob) {
        this.recruit = mob;
    }

    @Override
    public boolean canUse() {
        if(!RecruitsWariumConfig.SHOULD_RECRUITS_WEAPON_SWITCH.get())
            return false;

        WariumWeapon weapon = WariumWeapons.getWeaponFromItem(this.recruit.getMainHandItem().getItem());
        LivingEntity target = this.recruit.getTarget();


        if(!RecruitsWariumUtils.isWariumGun(this.recruit.getMainHandItem().getItem())) {
            this.gunSlot = this.findGunItem();

            if(gunSlot != -1) {
                itemStack = this.recruit.inventory.getItem(gunSlot);

                if(this.hasAmmo(itemStack)) {
                    return true;
                }
            }
        } else if(weapon != null) {
            if(target != null) {
                float distance = this.recruit.distanceTo(target);

                if(target.getVehicle() != null) {

                    if(!weapon.isAntiVehicle()) {
                        this.gunSlot = this.findATGunItemWithAmmo();

                        if(gunSlot != -1) {
                            itemStack = this.recruit.inventory.getItem(gunSlot);

                            return true;
                        }
                    }
                } else if(!this.recruit.hasLineOfSight(target)) {

                    if(!weapon.isIndirectFire()) {
                        this.gunSlot = this.findIndirectFireGunItemWithAmmo();

                        if(gunSlot != -1) {
                            itemStack = this.recruit.inventory.getItem(gunSlot);

                            return true;
                        }
                    }
                } else if(weapon.isSecondary()) {

                    this.gunSlot = this.findPrimaryGunItemWithAmmo();

                    if(gunSlot != -1) {
                        itemStack = this.recruit.inventory.getItem(gunSlot);

                        return true;
                    }
                } else if(distance > weapon.attackRadius()) {
                    this.gunSlot = this.findLongRangedGunItemWithAmmo(distance );

                    if(gunSlot != -1) {
                        itemStack = this.recruit.inventory.getItem(gunSlot);

                        return true;
                    }
                }

            }

        } else if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get()) {
            if(!this.hasAmmo(this.recruit.getMainHandItem())) {
                this.gunSlot = this.findGunItemWithAmmo();

                if(gunSlot != -1) {
                    itemStack = this.recruit.inventory.getItem(gunSlot);

                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasAmmo(ItemStack itemStack) {
        if(!RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get())
            return true;

        if(RecruitsWariumUtils.isWariumGun(itemStack.getItem())) {
            WariumWeapon weapon = WariumWeapons.getWeaponFromItem(itemStack.getItem());

            return weapon.getAmmo(this.recruit) != -1 || weapon.hasAmmo(itemStack);
        }

        return false;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        if(itemStack != null) {
            ItemStack beforeItem = this.recruit.inventory.getItem(5);
            this.recruit.getInventory().setItem(gunSlot, beforeItem);

            this.recruit.getInventory().setItem(5, itemStack);
            this.recruit.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
        }

        super.start();
    }

    @Override
    public void stop() {
        this.gunSlot = -1;

        super.stop();
    }

    private int findGunItem() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            if(RecruitsWariumUtils.isWariumGun(this.recruit.inventory.getItem(i).getItem())) {
                return i;
            }
        }

        return -1;
    }

    private int findPrimaryGunItemWithAmmo() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(RecruitsWariumUtils.isWariumGun(item.getItem()) && hasAmmo(item)) {
                WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item.getItem());

                if(!weapon.isSecondary()) {
                    return i;
                }
            }
        }

        return -1;
    }

    private int findGunItemWithAmmo() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(RecruitsWariumUtils.isWariumGun(item.getItem()) && hasAmmo(item)) {
                return i;
            }
        }

        return -1;
    }

    private int findLongRangedGunItemWithAmmo(double distance) {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(RecruitsWariumUtils.isWariumGun(item.getItem()) && hasAmmo(item)) {
                WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item.getItem());

                if(weapon.attackRadius() >= distance) {
                    return i;
                }
            }
        }

        return -1;
    }

    private int findATGunItemWithAmmo() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(RecruitsWariumUtils.isWariumGun(item.getItem()) && hasAmmo(item)) {
                WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item.getItem());

                if(weapon.isAntiVehicle()) {
                    return i;
                }
            }
        }

        return -1;
    }

    private int findIndirectFireGunItemWithAmmo() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(RecruitsWariumUtils.isWariumGun(item.getItem()) && hasAmmo(item)) {
                WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item.getItem());

                if(weapon.isIndirectFire()) {
                    return i;
                }
            }
        }

        return -1;
    }

}

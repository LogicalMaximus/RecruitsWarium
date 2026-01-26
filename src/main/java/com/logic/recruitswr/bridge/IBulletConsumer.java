package com.logic.recruitswr.bridge;

import net.minecraft.world.phys.Vec3;

public interface IBulletConsumer {
    boolean needsBullets();

    boolean recruits_warium$hasAmmo();

    Vec3 handleInaccuracy(Vec3 eyePosition, Vec3 targetPos, double inaccuracyRadians);

    double calculateInaccuracy(Vec3 targetPos, double additionalInaccuracyRadians);

    void changePose();

    void setPoseCooldown(int poseCooldown);

    int getPoseCooldown();

    Boolean isFleeing();

    void setIsFleeing(boolean isProne);

    void setWeaponSwitchCooldown(int weaponSwitchCooldown);

    int getWeaponSwitchCooldown();
}

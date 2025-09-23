package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.BowmanEntity;
import com.talhanation.recruits.entities.ai.RecruitRangedBowAttackGoal;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;

public class RecruitWariumStrategicFire extends Goal {
    private final Boolean consumeArrows;
    private BlockPos pos;
    private BowmanEntity bowman;
    private int attackIntervalMin;
    private int attackTime = -1;
    private int attackIntervalMax;
    private WariumWeapon weapon;

    public RecruitWariumStrategicFire(BowmanEntity bowman) {
        this.bowman = bowman;
        this.consumeArrows = (Boolean) RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get();
    }

    public boolean canUse() {
        Item item = this.bowman.getMainHandItem().getItem();

        if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() && !((IBulletConsumer)bowman).recruits_warium$hasAmmo())
            return false;

        if(RecruitsWariumUtils.isWariumGun(item)) {
            this.weapon = WariumWeapons.getWeaponFromItem(item);

            if (this.bowman.getTarget() == null && this.bowman.getShouldStrategicFire()  && this.bowman.getFollowState() != 5 && !this.bowman.needsToGetFood() && !this.bowman.getShouldMount()) {
                return true;
            } else {
                this.pos = null;
            }
        }

        return false;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void stop() {
        super.stop();
        this.attackTime = -1;
        this.bowman.stopUsingItem();
        this.bowman.clearArrowsPos();
    }

    public void tick() {
        attackIntervalMax = this.weapon.getAttackCooldown() + RecruitsWariumConfig.ADDITIONAL_SHOOT_DELAY.get();
        attackIntervalMin = this.weapon.getAttackCooldown();

        this.pos = this.bowman.StrategicFirePos();

        if(this.attackTime <= 0) {
            Vec3 vec3 = new Vec3((double) this.pos.getX(), (double) (this.pos.getY()), (double) this.pos.getZ());

            if(RecruitsWariumConfig.REALISTIC_LOOK_CONTROL.get()) {
                this.bowman.getLookControl().setLookAt(vec3.x, vec3.y, vec3.z, 60.0F, 90.0F);
            } else {
                this.bowman.lookAt(EntityAnchorArgument.Anchor.EYES, vec3);
            }

            if(!RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() || this.weapon.hasAmmo(this.bowman.getMainHandItem())) {
                double d0 = this.bowman.distanceToSqr((double)this.pos.getX(), this.bowman.getY(), (double)this.pos.getZ());

                this.weapon.performRangedAttack(this.bowman);

                float f = Mth.sqrt((float)d0) / 44.0F;

                this.attackTime = Mth.floor(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
            }
            else {
                this.attackTime = this.weapon.reloadWeapon(this.bowman);
            }
        }

        attackTime--;
    }
}

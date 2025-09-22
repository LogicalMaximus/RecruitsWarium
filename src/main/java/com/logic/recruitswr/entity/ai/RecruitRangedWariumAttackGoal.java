package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class RecruitRangedWariumAttackGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;
    private final double speedModifier;
    private LivingEntity target;
    private int attackTime = -1;
    private int seeTime;
    private final double stopRange;
    private boolean consumeArrows;
    private WariumWeapon weapon;

    public RecruitRangedWariumAttackGoal(T mob, double speedModifier, double stopRange) {
        this.recruit = mob;
        this.speedModifier = speedModifier;
        this.stopRange = stopRange;
        this.setFlags(EnumSet.of(Flag.LOOK));
        this.consumeArrows = (Boolean) RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get();
    }

    @Override
    public boolean canUse() {
        if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() && !((IBulletConsumer)recruit).recruits_warium$hasAmmo())
            return false;

        LivingEntity livingentity = this.recruit.getTarget();

        if (livingentity != null && livingentity.isAlive() && isHoldingGun(this.recruit)) {
            this.target = livingentity;
            float distance = this.target.distanceTo(this.recruit);
            boolean canTackMovePos = this.canAttackMovePos();
            boolean shouldRanged = this.recruit.getShouldRanged();
            boolean canAttack = this.recruit.canAttack(this.target);
            boolean notPassive = this.recruit.getState() != 3;
            boolean notNeedsToGetFood = !this.recruit.needsToGetFood();
            boolean canSee = this.recruit.getSensing().hasLineOfSight(this.target);
            if (!canSee) {
                this.recruit.setTarget((LivingEntity)null);
                return false;
            } else {
                this.weapon = WariumWeapons.getWeaponFromItem(this.recruit.getMainHandItem().getItem());

                if(this.weapon != null) {
                    return (double)distance >= this.stopRange && canTackMovePos && notNeedsToGetFood && canAttack && notPassive && shouldRanged;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private boolean hasAmmo() {
        return !this.consumeArrows || this.recruit.getInventory().hasAnyMatching((item) -> this.weapon.getAmmo().contains(item.getItem()));
    }

    private boolean isHoldingGun(T recruit) {
        return RecruitsWariumUtils.isWariumGun(recruit.getMainHandItem().getItem());
    }

    public boolean canContinueToUse() {
        return this.canUse() && isHoldingGun(this.recruit);
    }

    public void start() {
        super.start();
        this.recruit.setAggressive(true);

    }

    public void stop() {
        super.stop();
        this.recruit.setAggressive(false);
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.recruit.stopUsingItem();

    }

    @Override
    public void tick() {
        if (this.target != null && this.target.isAlive()) {
            double distance = this.target.distanceToSqr(this.recruit);
            boolean isClose = distance < (double)this.weapon.attackRadius();
            boolean isFar = distance > (double)this.weapon.attackRadius();
            boolean isTooFar = distance >= (double)4500.0F;
            boolean inRange = !isFar;
            boolean canSee = this.recruit.getSensing().hasLineOfSight(this.target);
            if (canSee) {
                ++this.seeTime;
            } else {


                this.seeTime = 0;
            }

            if (isTooFar) {
                this.recruit.setTarget((LivingEntity)null);
                this.stop();
                return;
            }

            if (this.recruit.getShouldFollow() && this.recruit.getOwner() != null) {
                this.handleFollow(this.recruit.getOwner(), inRange, isFar, isClose);
            } else if (this.recruit.getShouldHoldPos() && this.recruit.getHoldPos() != null) {
                this.handleHoldPos(this.recruit.getHoldPos(), inRange, isFar, isClose);
            } else {
                this.handleWander(inRange, isFar, isClose);
            }

            this.recruit.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.getEyePosition(1.0F));

            ItemStack itemStack = this.recruit.getMainHandItem();

            if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() && !this.weapon.hasAmmo(itemStack)) {
                this.attackTime = this.weapon.reloadWeapon(this.recruit);
            }

            if (this.attackTime <= 0) {

                if (!canSee && this.seeTime < -60) {
                    //((IPose)this.recruit).setAimingPose(RecruitPose.IDLE_GUN);
                } else if (canSee) {
                    this.weapon.performRangedAttack(this.recruit);

                    float f = Mth.sqrt((float)distance) / this.weapon.attackRadius();
                    float attackIntervalMax = this.weapon.getAttackCooldown() + RecruitsWariumConfig.ADDITIONAL_SHOOT_DELAY.get();
                    float attackIntervalMin = this.weapon.getAttackCooldown();

                    this.attackTime = Mth.floor(f * (float)(attackIntervalMax - attackIntervalMin) + (float)attackIntervalMin);
                }
            }

        }

        --this.attackTime;

        super.tick();
    }

    private boolean canAttackMovePos() {
        LivingEntity target = this.recruit.getTarget();
        BlockPos pos = this.recruit.getMovePos();
        if (target != null && pos != null && this.recruit.getShouldMovePos()) {
            boolean targetIsFar = target.distanceToSqr(this.recruit) >= (double)320.0F;
            boolean posIsClose = pos.distSqr(this.recruit.getOnPos()) <= (double)150.0F;
            boolean posIsFar = pos.distSqr(this.recruit.getOnPos()) > (double)150.0F;
            if (posIsFar) {
                return false;
            }

            if (posIsClose && targetIsFar) {
                return false;
            }
        }

        return true;
    }

    private void handleFollow(@NotNull LivingEntity owner, boolean inRange, boolean isFar, boolean isClose) {
        boolean ownerClose = owner.distanceToSqr(this.recruit) <= (double)100.0F;
        if (ownerClose) {
            if (inRange) {
                this.recruit.getNavigation().stop();
            }

            if (isFar) {
                this.recruit.getNavigation().moveTo(this.target, this.speedModifier);
            }
        }

    }

    private void handleHoldPos(@NotNull Vec3 pos, boolean inRange, boolean isFar, boolean isClose) {
        boolean posClose = pos.distanceToSqr(this.recruit.position()) <= (double)50.0F;
        if (posClose && inRange) {
            this.recruit.getNavigation().stop();
        }

    }

    private void handleWander(boolean inRange, boolean isFar, boolean isClose) {
        if (inRange) {
            this.recruit.getNavigation().stop();
        }

        if (isFar) {
            this.recruit.getNavigation().moveTo(this.target, this.speedModifier);
        }

    }

}

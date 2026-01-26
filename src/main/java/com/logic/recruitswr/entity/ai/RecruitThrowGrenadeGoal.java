package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IGrenade;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RecruitThrowGrenadeGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;

    private LivingEntity target;

    private boolean isRunning = false;

    private int throwDelay;

    private ItemStack itemStack;

    private int itemSlot;

    public RecruitThrowGrenadeGoal(T mob) {
        this.recruit = mob;
    }

    @Override
    public boolean canUse() {
        if(!RecruitsWariumConfig.SHOULD_RECRUITS_THROW_GRENADES.get())
            return false;

        if(!this.recruit.getShouldRanged())
            return false;

        this.target = this.recruit.getTarget();

        if(this.target == null)
            return false;

        if(this.recruit.distanceToSqr(this.target) < 24)
            return false;

        this.itemSlot = this.getAmmo(this.recruit);

        if(itemSlot == -1)
            return false;

        if(this.recruit.getRandom().nextDouble() < RecruitsWariumConfig.RECRUIT_GRENADE_THROW_CHANCE.get()) {
            return true;
        }

        return false;
    }

    public boolean canContinueToUse() {
        return isRunning;
    }

    public boolean isInterruptable() {
        return false;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        itemStack = recruit.inventory.getItem(itemSlot).copy();

        isRunning = true;

        throwDelay = RecruitsWariumConfig.RECRUIT_GRENADE_THROW_DELAY.get();

        super.start();
    }

    @Override
    public void tick() {
        if(this.throwDelay <= 0) {
            if(itemStack != null) {
                Item item = itemStack.getItem();

                if(item instanceof IGrenade grenade) {
                    EntityType<? extends AbstractArrow> projecile = grenade.getProjecile();

                    AbstractArrow abstractArrow = projecile.create(this.recruit.level());

                    AbstractArrow shootArrow = WariumWeapons.GRENADE_WEAPON.shootArrow(this.recruit, this.target, abstractArrow, 0, 0, 0);

                    this.recruit.level().addFreshEntity(shootArrow);

                    this.recruit.getInventory().removeItemType(item, 1);

                    this.throwDelay = RecruitsWariumConfig.RECRUIT_GRENADE_THROW_DELAY.get();
                    isRunning = false;
                }
            }
        }

        throwDelay--;

        super.tick();
    }

    @Override
    public void stop() {
        super.stop();

        throwDelay = 0;
        isRunning = false;
    }

    public int getAmmo(AbstractRecruitEntity recruit) {
        for(int i = 0; i < recruit.inventory.getContainerSize(); i++) {
            ItemStack itemStack = recruit.inventory.getItem(i);

            if(itemStack != null) {
                if(itemStack.getCount() > 0) {
                    Item item = itemStack.getItem();

                    if(WariumWeapons.GRENADE_WEAPON.getAmmo().contains(item) && item instanceof IGrenade) {
                        return i;
                    }
                }
            }
        }


        return -1;
    }
}

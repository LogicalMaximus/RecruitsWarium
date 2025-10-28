package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RecruitFleeGoal extends Goal {
    protected final AbstractRecruitEntity mob;

    private int fleeTicks = 0;

    public RecruitFleeGoal(AbstractRecruitEntity recruit) {
        this.mob = recruit;
    }

    public boolean canUse() {
        return true;
    }

    @Override
    public void start() {
        if(!((IBulletConsumer)this.mob).isFleeing()) {
            if(this.mob.getMorale() < RecruitsWariumConfig.RECRUIT_FLEE_MORALE.get()) {
                if(this.mob.getRandom().nextDouble() < RecruitsWariumConfig.RECRUIT_FLEE_CHANCE.get()) {
                    ((IBulletConsumer)this.mob).setIsFleeing(true);

                    this.fleeTicks = 0;
                }
            }
        }
        else {
            if(fleeTicks >= RecruitsWariumConfig.RECRUIT_FLEE_TIME.get()) {
                ((IBulletConsumer)this.mob).setIsFleeing(false);
            }

            fleeTicks++;
        }



        super.start();
    }
}

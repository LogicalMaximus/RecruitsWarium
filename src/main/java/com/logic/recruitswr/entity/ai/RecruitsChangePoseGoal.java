package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class RecruitsChangePoseGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;
    private LivingEntity target;

    public RecruitsChangePoseGoal(T mob) {
        this.recruit = mob;
    }

    @Override
    public boolean canUse() {
        if(RecruitsWariumConfig.SHOULD_RECRUITS_CHANGE_POSE.get()) {
            this.target = this.recruit.getTarget();

            return true;
        }

        return false;
    }

    @Override
    public void tick() {
        if(target != null) {
            if(this.recruit.getRandom().nextFloat() < RecruitsWariumConfig.RECRUIT_POSE_CHANCE.get()) {
                if(((IBulletConsumer)this.recruit).getPoseCooldown() <= 0) {
                    ((IBulletConsumer)this.recruit).changePose();

                    ((IBulletConsumer)this.recruit).setPoseCooldown(RecruitsWariumConfig.RECRUIT_POSE_COOLDOWN.get());
                }
            }
        } else {
            this.recruit.setPose(Pose.STANDING);
        }

        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}

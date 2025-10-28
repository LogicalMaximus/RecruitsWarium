package com.logic.recruitswr.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public class RecruitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

    private double walkSpeedModifier;

    public RecruitAvoidEntityGoal(PathfinderMob p_25040_, Class<T> p_25041_, Predicate<LivingEntity> p_25042_, float p_25043_, double p_25044_, double p_25045_, Predicate<LivingEntity> p_25046_) {
        super(p_25040_, p_25041_, p_25042_, p_25043_, p_25044_, p_25045_, p_25046_);

        this.walkSpeedModifier = p_25044_;
    }

    public void tick() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);

        super.tick();
    }
}

package com.logic.recruitswr.mixin.ai;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.entities.ai.RecruitUpkeepPosGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecruitUpkeepPosGoal.class)
public abstract class MixinRecruitUpkeepPosGoal extends Goal {
    @Shadow(remap = false)
    public AbstractRecruitEntity recruit;

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public boolean canUse() {
        if(this.recruit.getUpkeepPos() != null) {
            return this.recruit.needsToGetFood() || ((IBulletConsumer)recruit).needsBullets();
        }

        return false;
    }
}

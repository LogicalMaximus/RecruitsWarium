package com.logic.recruitswr.mixin;

import com.logic.recruitswr.entity.ai.RecruitWariumStrategicFire;
import com.talhanation.recruits.entities.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowmanEntity.class)
public abstract class MixinBowmanEntity extends AbstractRecruitEntity implements IRangedRecruit, IStrategicFire {
    public MixinBowmanEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new RecruitWariumStrategicFire(((BowmanEntity) (Object)this)));
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createLivingAttributes().add(Attributes.MAX_HEALTH, (double)20.0F).add(Attributes.MOVEMENT_SPEED, 0.31).add((Attribute) ForgeMod.SWIM_SPEED.get(), 0.3).add(Attributes.KNOCKBACK_RESISTANCE, 0.05).add(Attributes.ATTACK_DAMAGE, (double)0.5F).add(Attributes.FOLLOW_RANGE, 128.0F).add((Attribute)ForgeMod.ENTITY_REACH.get(), (double)0.0F).add(Attributes.ATTACK_SPEED);
    }


}

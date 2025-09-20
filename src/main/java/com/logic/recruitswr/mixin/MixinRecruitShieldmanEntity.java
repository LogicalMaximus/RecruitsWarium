package com.logic.recruitswr.mixin;

import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.entities.RecruitShieldmanEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RecruitShieldmanEntity.class)
public abstract class MixinRecruitShieldmanEntity extends AbstractRecruitEntity {
    public MixinRecruitShieldmanEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static AttributeSupplier.Builder setAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, (double)25.0F).add(Attributes.MOVEMENT_SPEED, (double)0.25F).add((Attribute) ForgeMod.SWIM_SPEED.get(), 0.3).add(Attributes.KNOCKBACK_RESISTANCE, 0.2).add(Attributes.ATTACK_DAMAGE, (double)1.0F).add(Attributes.FOLLOW_RANGE, 128.0F).add((Attribute)ForgeMod.ENTITY_REACH.get(), (double)0.0F).add(Attributes.ATTACK_SPEED);
    }
}

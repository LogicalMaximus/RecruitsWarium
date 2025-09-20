package com.logic.recruitswr.mixin;

import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.entities.RecruitEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RecruitEntity.class)
public abstract class MixinRecruitEntity extends AbstractRecruitEntity {
    public MixinRecruitEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, (double)20.0F).add(Attributes.MOVEMENT_SPEED, 0.3).add((Attribute) ForgeMod.SWIM_SPEED.get(), 0.3).add(Attributes.KNOCKBACK_RESISTANCE, 0.1).add(Attributes.ATTACK_DAMAGE, (double)0.5F).add(Attributes.FOLLOW_RANGE, 128.0F).add((Attribute)ForgeMod.ENTITY_REACH.get(), (double)0.0F).add(Attributes.ATTACK_SPEED);
    }


}

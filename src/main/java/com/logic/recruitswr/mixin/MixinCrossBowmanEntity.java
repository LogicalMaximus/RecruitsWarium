package com.logic.recruitswr.mixin;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.entities.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossBowmanEntity.class)
public abstract class MixinCrossBowmanEntity extends AbstractRecruitEntity implements CrossbowAttackMob, IRangedRecruit, IStrategicFire {

    public MixinCrossBowmanEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    public void wantsToPickUp(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        Item item = this.getMainHandItem().getItem();

        if (RecruitsWariumUtils.isWariumGun(item)) {
            WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item);

            if(weapon.getAmmo().contains(itemStack.getItem())) {
                cir.setReturnValue(true);
            }
        }
    }
}

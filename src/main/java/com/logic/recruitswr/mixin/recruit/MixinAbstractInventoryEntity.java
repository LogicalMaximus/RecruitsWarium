package com.logic.recruitswr.mixin.recruit;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.pathfinding.AsyncPathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractInventoryEntity.class)
public abstract class MixinAbstractInventoryEntity extends AsyncPathfinderMob {

    protected MixinAbstractInventoryEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
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

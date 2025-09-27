package com.logic.recruitswr.mixin.recruit;

import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.*;
import com.talhanation.recruits.world.RecruitsPatrolSpawn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

    /**
     * @author LogicalMaximus
     * @reason Make CrossBowman Not Spawn With Arrows If Weapon Is Warium Weapon
     */
    @Overwrite(remap = false)
    public void initSpawn() {
        this.setCustomName(Component.literal("Crossbowman"));
        this.setCost((Integer) RecruitsServerConfig.CrossbowmanCost.get());
        this.setEquipment();
        this.setDropEquipment();
        this.setRandomSpawnBonus();
        this.setPersistenceRequired();
        this.setGroup(2);
        if ((Boolean)RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() && !RecruitsWariumUtils.isWariumGun(this.getMainHandItem().getItem())) {
            if (Main.isMusketModLoaded && IWeapon.isMusketModWeapon(this.getMainHandItem())) {
                int i = this.getRandom().nextInt(32);
                ItemStack arrows = ((Item) ForgeRegistries.ITEMS.getDelegateOrThrow(ResourceLocation.tryParse("musketmod:cartridge")).get()).getDefaultInstance();
                arrows.setCount(14 + i);
                this.inventory.setItem(6, arrows);
            } else {
                RecruitsPatrolSpawn.setRangedArrows(this);
            }
        }

        AbstractRecruitEntity.applySpawnValues(this);
    }
}

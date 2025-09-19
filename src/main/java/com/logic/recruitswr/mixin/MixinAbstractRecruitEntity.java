package com.logic.recruitswr.mixin;

import com.logic.recruitswr.bridge.IPose;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.entity.ai.RecruitRangedWariumAimerGoal;
import com.logic.recruitswr.entity.ai.RecruitRangedWariumAttackGoal;
import com.logic.recruitswr.entity.ai.RecruitWariumStrategicFire;
import com.logic.recruitswr.entity.poses.RecruitPose;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.entities.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractRecruitEntity.class)
public abstract class MixinAbstractRecruitEntity extends AbstractInventoryEntity implements IPose {

    @Unique
    private RecruitPose pose;

    public MixinAbstractRecruitEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new RecruitRangedWariumAttackGoal<>(((AbstractRecruitEntity) (Object)this), 1.0,  3.0));
        this.goalSelector.addGoal(2, new RecruitRangedWariumAimerGoal<>(((AbstractRecruitEntity) (Object)this)));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putString("pose", this.pose.toString());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        if(nbt.contains("pose")) {
            this.pose = RecruitPose.valueOf(nbt.getString("pose"));
        }
    }

    @Override
    public RecruitPose getAimingPose() {
        return pose;
    }

    @Override
    public void setAimingPose(RecruitPose pose) {
        this.pose = pose;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void upkeepReequip(@NotNull Container container) {
        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!this.canEatItemStack(itemstack) && this.wantsToPickUp(itemstack)) {
                label63: {
                    if (this.canEquipItem(itemstack)) {
                        ItemStack equipment = itemstack.copy();
                        equipment.setCount(1);
                        this.equipItem(equipment);
                        itemstack.shrink(1);
                    }

                    if (((AbstractRecruitEntity)(Object)this)  instanceof CrossBowmanEntity) {
                        CrossBowmanEntity crossBowmanEntity = (CrossBowmanEntity)((AbstractRecruitEntity)(Object)this) ;
                        if (Main.isMusketModLoaded && IWeapon.isMusketModWeapon(crossBowmanEntity.getMainHandItem()) && itemstack.getDescriptionId().contains("cartridge")) {
                            if (this.canTakeCartridge()) {
                                ItemStack equipment = itemstack.copy();
                                this.inventory.addItem(equipment);
                                itemstack.shrink(equipment.getCount());
                            }
                            break label63;
                        }
                    }

                    if (this instanceof IRangedRecruit && itemstack.is(ItemTags.ARROWS) && this.canTakeArrows()) {
                        ItemStack equipment = itemstack.copy();
                        this.inventory.addItem(equipment);
                        itemstack.shrink(equipment.getCount());
                    }

                    Item item = this.getMainHandItem().getItem();

                    if (RecruitsWariumUtils.isWariumGun(item)) {
                        WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item);

                        if(weapon.getAmmo().contains(item)) {
                            ItemStack equipment = itemstack.copy();
                            this.inventory.addItem(equipment);
                            itemstack.shrink(equipment.getCount());
                        }
                    }
                }
            }

            if (((AbstractRecruitEntity)(Object)this) instanceof CaptainEntity && Main.isSmallShipsLoaded) {
                if (itemstack.getDescriptionId().contains("cannon_ball")) {
                    if (this.canTakeCannonBalls()) {
                        ItemStack equipment = itemstack.copy();
                        this.inventory.addItem(equipment);
                        itemstack.shrink(equipment.getCount());
                    }
                } else if (itemstack.is(ItemTags.PLANKS)) {
                    if (this.canTakePlanks()) {
                        ItemStack equipment = itemstack.copy();
                        this.inventory.addItem(equipment);
                        itemstack.shrink(equipment.getCount());
                    }
                } else if (itemstack.is(Items.IRON_NUGGET) && this.canTakeIronNuggets()) {
                    ItemStack equipment = itemstack.copy();
                    this.inventory.addItem(equipment);
                    itemstack.shrink(equipment.getCount());
                }
            }
        }

    }

    @Shadow(remap = false)
    public boolean canEatItemStack(ItemStack stack) {
        throw new AssertionError();
    }

}

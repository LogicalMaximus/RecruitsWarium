package com.logic.recruitswr.mixin.recruit;

import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.entity.ai.RecruitRangedWariumAimerGoal;
import com.logic.recruitswr.entity.ai.RecruitRangedWariumAttackGoal;
import com.logic.recruitswr.entity.ai.RecruitsFindCoverGoal;
import com.logic.recruitswr.entity.ai.WRNearestAttackableTargetGoal;
import com.logic.recruitswr.utils.RecruitsWariumSpawnUtils;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.entities.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractRecruitEntity.class)
public abstract class MixinAbstractRecruitEntity extends AbstractInventoryEntity implements IBulletConsumer {

    public MixinAbstractRecruitEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new RecruitsFindCoverGoal<>(((AbstractRecruitEntity) (Object)this), 1.15));
        this.goalSelector.addGoal(2, new RecruitRangedWariumAttackGoal<>(((AbstractRecruitEntity) (Object)this), 1.0,  1.0));
        this.goalSelector.addGoal(2, new RecruitRangedWariumAimerGoal<>(((AbstractRecruitEntity) (Object)this)));

        if(RecruitsWariumConfig.SHOULD_TARGET_MONSTERS.get()) {
            this.targetSelector.addGoal(2, new WRNearestAttackableTargetGoal<>(this, Monster.class, false));
        }
    }

    @Override
    public boolean needsBullets() {
        int timer = this.getUpkeepTimer();

        return !recruits_warium$hasAmmo();
    }

    @Shadow(remap = false)
    public int getUpkeepTimer() {
        throw new AssertionError();
    }

    @Override
    public boolean recruits_warium$hasAmmo() {
        Item item = this.getMainHandItem().getItem();

        if (RecruitsWariumUtils.isWariumGun(item)) {
            WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item);

            if(weapon.getAmmo(((AbstractRecruitEntity) (Object)this)) != -1) {
                return true;
            }
        }

        return false;
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
                }
            }

            Item item = this.getMainHandItem().getItem();

            if (RecruitsWariumUtils.isWariumGun(item)) {
                WariumWeapon weapon = WariumWeapons.getWeaponFromItem(item);

                if(weapon.getAmmo().contains(itemstack.getItem())) {
                    ItemStack equipment = itemstack.copy();
                    this.inventory.addItem(equipment);
                    itemstack.shrink(equipment.getCount());
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

    @Inject(method = "hurt", at = @At("HEAD"))
    public void hurt(DamageSource dmg, float amt, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = dmg.getEntity();

        if(entity instanceof AbstractArrow) {
            if(!RecruitsWariumConfig.FRIENDLY_FIRE.get()) {
                Entity owner = dmg.getDirectEntity();

                if(owner instanceof AbstractRecruitEntity recruit) {
                    if(!recruit.canAttack((((AbstractRecruitEntity) (Object)this)))) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }

    }

    @Inject(method = "setEquipment", at = @At("TAIL"), remap = false)
    public void initSpawn(CallbackInfo ci) {
        RecruitsWariumSpawnUtils.setRangedBullets(((AbstractRecruitEntity) (Object)this));
    }

}

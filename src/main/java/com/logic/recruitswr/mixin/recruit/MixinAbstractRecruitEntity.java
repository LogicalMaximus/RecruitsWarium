package com.logic.recruitswr.mixin.recruit;

import com.google.common.collect.ImmutableMap;
import com.logic.recruitswr.bridge.IBulletConsumer;
import com.logic.recruitswr.bridge.ISuppressiveFire;
import com.logic.recruitswr.compat.WariumWeapon;
import com.logic.recruitswr.compat.WariumWeapons;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.logic.recruitswr.entity.ai.*;
import com.logic.recruitswr.utils.RecruitsWariumSpawnUtils;
import com.logic.recruitswr.utils.RecruitsWariumUtils;
import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.entities.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(AbstractRecruitEntity.class)
public abstract class MixinAbstractRecruitEntity extends AbstractInventoryEntity implements IBulletConsumer, ISuppressiveFire {

    @Unique
    private static final EntityDataAccessor<Boolean> WR_FLEEING;

    @Unique
    private static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.6F, 1.8F);

    @Unique
    private static final Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder().put(Pose.STANDING, STANDING_DIMENSIONS).put(Pose.SLEEPING, SLEEPING_DIMENSIONS).put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F)).put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();

    @Unique
    private int poseCooldown;

    public MixinAbstractRecruitEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new RecruitPanicGoal(((AbstractRecruitEntity) (Object)this), 1.35));
        this.goalSelector.addGoal(1, new RecruitAvoidEntityGoal<>(this, LivingEntity.class, (livingEntity -> this.getFollowState() == 0 && !this.getShouldMovePos() && !this.getShouldFollow() && this.getMainHandItem() != null && RecruitsWariumUtils.isWariumGun(this.getMainHandItem().getItem()) && this.distanceTo(livingEntity) < 16), 64.0F, 1.25D, 1.4D, this::shouldAttack));
        this.goalSelector.addGoal(2, new RecruitsFindCoverFromTargetGoal<>(((AbstractRecruitEntity) (Object)this), 1.25));
        this.goalSelector.addGoal(2, new RecruitFleeGoal(((AbstractRecruitEntity) (Object)this)));
        this.goalSelector.addGoal(2, new RecruitsChangePoseGoal<>(((AbstractRecruitEntity) (Object)this)));
        this.goalSelector.addGoal(3, new RecruitRangedSuppressGoal<>(((AbstractRecruitEntity) (Object)this), 1.0,  1.0));
        this.goalSelector.addGoal(3, new RecruitRangedWariumAttackGoal<>(((AbstractRecruitEntity) (Object)this), 1.0,  1.0));
        this.goalSelector.addGoal(3, new RecruitThrowGrenadeGoal<>(((AbstractRecruitEntity) (Object)this)));
        this.goalSelector.addGoal(3, new RecruitRangedWariumAimerGoal<>(((AbstractRecruitEntity) (Object)this)));
    }

    @Inject(method = "defineSynchedData", at =@At("TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(WR_FLEEING, false);
    }

    @Inject(method = "addAdditionalSaveData", at =@At("TAIL"))
    public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putBoolean("isWRFleeing", this.isFleeing());
    }

    @Inject(method = "readAdditionalSaveData", at =@At("TAIL"))
    public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.setIsFleeing(nbt.getBoolean("isWRFleeing"));
    }

    @Override
    public Boolean isFleeing() {
        return (Boolean)this.entityData.get(WR_FLEEING);
    }

    public void setIsFleeing(boolean isProne) {
        this.entityData.set(WR_FLEEING, isProne);
    }

    @Override
    public boolean needsBullets() {
        return !recruits_warium$hasAmmo();
    }

    @Shadow(remap = false)
    public boolean shouldAttack(LivingEntity target) {
        throw new AssertionError();
    }

    @Shadow(remap = false)
    public boolean getShouldMovePos() {
        throw new AssertionError();
    }

    @Shadow(remap = false)
    public boolean getShouldFollow() {
        throw new AssertionError();
    }

    public EntityDimensions getDimensions(Pose pose) {
        return POSES.getOrDefault(pose, STANDING_DIMENSIONS);
    }

    @Override
    public void changePose() {
        if(this.getPose() == Pose.STANDING) {
            this.setIsFleeing(false);
            this.setPose(Pose.CROUCHING);
        } else {
            this.setIsFleeing(false);
            this.setPose(Pose.STANDING);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap=false)
    public float getStandingEyeHeight(Pose p_36259_, EntityDimensions p_36260_) {
        switch (p_36259_) {
            case SWIMMING:
            case FALL_FLYING:
            case SPIN_ATTACK:
                return 0.4F;
            case CROUCHING:
                return 1.27F;
            default:
                return p_36260_.height * 0.98F;
        }
    }

    @Override
    public void setPoseCooldown(int poseCooldown) {
        this.poseCooldown = poseCooldown;
    }

    @Override
    public int getPoseCooldown() {
        return poseCooldown;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        poseCooldown--;

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

    @Shadow public abstract float getMorale();

    @Shadow protected abstract void registerGoals();

    @Shadow @Final private static EntityDataAccessor<Boolean> IS_FOLLOWING;

    @Shadow public abstract boolean canAttack(@NotNull LivingEntity target);

    @Shadow public abstract int getFollowState();

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

    @Override
    public MoralState getMoraleState() {
            if (0.0F <= this.getMorale() && this.getMorale() < 20.0F)return MoralState.CONFUSED;

            else if (20.0F <= this.getMorale() && this.getMorale() < 40.0F)return MoralState.LOW_MORALE;

            else return MoralState.HIGH_MORALE;
    }

    @Override
    public Vec3 handleInaccuracy(Vec3 eyePosition, Vec3 targetPos, double inaccuracyRadians) {
        Vec3 lookTarget = new Vec3(eyePosition.x, eyePosition.y, eyePosition.z);

        Vec3 direction = targetPos.subtract(eyePosition).normalize();

        double yawOffset = (random.nextDouble() * 2.0 - 1.0) * inaccuracyRadians;
        double pitchOffset = (random.nextDouble() * 2.0 - 1.0) * inaccuracyRadians;

        double yaw = Math.atan2(direction.z, direction.x);
        double pitch = Math.asin(direction.y);

        yaw += yawOffset;
        pitch += pitchOffset;

        double x = Math.cos(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch);
        double z = Math.cos(pitch) * Math.sin(yaw);

        Vec3 inaccurateDir = new Vec3(x, y, z).normalize();

        double distance = targetPos.distanceTo(eyePosition);

        return lookTarget.add(inaccurateDir.scale(distance));
    }

    @Override
    public double calculateInaccuracy(Vec3 targetPos, double additionalInaccuracyRadians) {
        double inaccuracy = 0;

        double baseInaccuracy = RecruitsWariumConfig.BASE_INACCURACY.get();

        double inaccuracyDeduction = RecruitsWariumConfig.ACCURACY_GAIN_PER_LEVEL.get() * ( (AbstractRecruitEntity) (Object)this).getXpLevel();

        inaccuracy = baseInaccuracy - inaccuracyDeduction + additionalInaccuracyRadians;

        double minimumInaccuracy = RecruitsWariumConfig.MINIMUM_INACCURACY.get();

        if(inaccuracy <= minimumInaccuracy) {
            inaccuracy = minimumInaccuracy;
        }

        MoralState moralState = this.getMoraleState();

        if(moralState == MoralState.LOW_MORALE) {
            inaccuracy = inaccuracy * RecruitsWariumConfig.LOW_MORALE_INACCURACY.get();
        } else if (moralState == MoralState.CONFUSED) {
            inaccuracy = inaccuracy * RecruitsWariumConfig.CONFUSED_INACCURACY.get();;
        }

        if(this.getPose() == Pose.CROUCHING) {
            inaccuracy = inaccuracy * 0.75;
        } else if (this.getPose() == Pose.SWIMMING) {
            inaccuracy = inaccuracy * 0.50;
        }

        double scaledInaccuracy = inaccuracy * (1 + ((Math.PI * 2 * Math.sqrt(this.distanceToSqr(targetPos))) / 21600));

        double inaccuracyRadians = Math.toRadians(scaledInaccuracy);

        if(this.random.nextDouble() < 0.5) {
            inaccuracyRadians = -inaccuracyRadians;
        }

        return inaccuracyRadians;
    }

    static {
        WR_FLEEING = SynchedEntityData.defineId(AbstractRecruitEntity.class, EntityDataSerializers.BOOLEAN);
    }
}

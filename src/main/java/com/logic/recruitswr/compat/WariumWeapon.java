package com.logic.recruitswr.compat;

import com.logic.recruitswr.bridge.IAmmo;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.mcreator.crustychunks.init.CrustyChunksModParticleTypes;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class WariumWeapon implements IWeapon {
    private final Item weapon;

    protected final ArrayList<Item> ammo = new ArrayList<>();

    public WariumWeapon(Item weapon) {
        this.weapon = weapon;
    }

    @Override
    public Item getWeapon() {
        return weapon;
    }

    public int addAmmo(ItemStack stack, int amount) {
        int remainder = 0;

        int maxAmmo = this.getMaxAmmo();

        double currentAmount = stack.getOrCreateTag().getDouble("Ammo");

        double totalAmount = (int) (currentAmount + amount);

        if(totalAmount > maxAmmo) {
            remainder = (int) (totalAmount - maxAmmo);

            totalAmount = maxAmmo;
        }

        stack.getOrCreateTag().putDouble("Ammo", totalAmount);

        return remainder;
    }

    public void consumeAmmo(ItemStack stack, int amount) {
        double currentAmount = stack.getOrCreateTag().getDouble("Ammo");

        int totalAmount = (int) (currentAmount - amount);

        if(totalAmount < 0) {
            totalAmount = 0;
        }

        stack.getOrCreateTag().putDouble("Ammo", totalAmount);
    }

    public boolean hasAmmo(ItemStack stack) {
        return stack.getOrCreateTag().getDouble("Ammo") > 0;
    }

    @Override
    public boolean isLoaded(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getDouble("Ammo") > 0;
    }

    public List<Item> getAmmo() {
        return ammo;
    }

    public abstract int getMaxAmmo();

    @Override
    public double getMoveSpeedAmp() {
        return 0;
    }

    @Override
    public float getProjectileSpeed() {
        return 0;
    }

    @Override
    public AbstractHurtingProjectile getProjectile(LivingEntity livingEntity) {
        return null;
    }

    @Override
    public AbstractArrow getProjectileArrow(LivingEntity livingEntity) {
        return null;
    }

    @Override
    public AbstractHurtingProjectile shoot(LivingEntity livingEntity, AbstractHurtingProjectile abstractHurtingProjectile, double v, double v1, double v2) {

        return null;
    }

    @Override
    public boolean isGun() {
        return false;
    }

    @Override
    public boolean canMelee() {
        return false;
    }

    @Override
    public boolean isBow() {
        return false;
    }

    @Override
    public boolean isCrossBow() {
        return false;
    }

    public boolean isAmmoMagazine() {
        return false;
    }

    @Override
    public void performRangedAttackIWeapon(AbstractRecruitEntity abstractRecruitEntity, double v, double v1, double v2, float v3) {

    }

    @Override
    public void setLoaded(ItemStack itemStack, boolean b) {

    }

    @Override
    public SoundEvent getShootSound() {
        return null;
    }

    protected abstract void playShootSounds(Level world, BlockPos pos);

    public int getBulletAmount() {
        return 1;
    }

    public int getShotAmount() {
        return 1;
    }

    public int attackRadius() {
        return 196;
    }

    public void performRangedAttack(AbstractRecruitEntity recruit) {
        Item item = null;

        CompoundTag tag = recruit.getMainHandItem().getOrCreateTag();

        Optional<Holder<EntityType<?>>> holder = ForgeRegistries.ENTITY_TYPES.getHolder(ResourceLocation.tryParse(tag.getString("Projectile")));

        EntityType<? extends AbstractArrow> entityType = null;

        if(holder.isPresent()) {
            entityType = (EntityType<? extends AbstractArrow>) holder.get().value();
        }

        if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() ) {

            if(this.hasAmmo(recruit.getMainHandItem())) {
                if(entityType != null) {
                    if(tag.contains("Casing")) {
                        Optional<Holder<Item>> itemHolder = ForgeRegistries.ITEMS.getHolder(ResourceLocation.tryParse(tag.getString("Casing")));

                        if(itemHolder.isPresent()) {
                            item = itemHolder.get().value();
                        }
                    }

                    this.shootProjectiles(item, recruit, entityType, true);
                } else {
                    ItemStack ammoStack = recruit.inventory.getItem(this.getAmmo(recruit));

                    if(ammoStack != null) {
                        item = ammoStack.getItem();

                        entityType = ((IAmmo)item).getProjecile();

                        this.shootProjectiles(item, recruit, entityType, true);
                    } else {

                        recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(CrustyChunksModSounds.DRYFIRE.get().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);
                    }
                }
            } else {

                recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(CrustyChunksModSounds.DRYFIRE.get().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);
            }

        } else {
            ItemStack ammoStack = recruit.inventory.getItem(this.getAmmo(recruit));

            if(entityType != null) {

                this.shootProjectiles(null, recruit, entityType, false);
            } else if(ammoStack != null) {
                item = ammoStack.getItem();

                entityType = ((IAmmo)item).getProjecile();

                this.shootProjectiles(null, recruit, entityType, true);
            } else {
                item = this.getAmmo().get(0);

                this.shootProjectiles(null, recruit, ((IAmmo)item).getProjecile(), false);
            }
        }
    }

    private void shootProjectiles(Item item, AbstractRecruitEntity recruit, EntityType<? extends AbstractArrow> entityType, boolean consumeAmmo) {
        Level level = recruit.level();

        for (int i = 0; i < this.getShotAmount(); i++) {
            for(int j = 0; j < this.getBulletAmount(); j++) {
                AbstractArrow abstractArrow = entityType.create(level);

                AbstractArrow projectile = this.shootArrow(recruit, abstractArrow, 0, 0, 0);

                level.addFreshEntity(projectile);
            }

            level.addParticle((SimpleParticleType) CrustyChunksModParticleTypes.GUN_SMOKE.get(), recruit.getX() + recruit.getLookAngle().x * 0.8, recruit.getY() + (double)recruit.getEyeHeight() - 0.1, recruit.getZ() + recruit.getLookAngle().z * 0.8, recruit.getLookAngle().x * 0.2 + Mth.nextDouble(RandomSource.create(), -0.05, 0.05), recruit.getLookAngle().y * 0.2 + Mth.nextDouble(RandomSource.create(), -0.05, 0.05), recruit.getLookAngle().z * 0.2 + Mth.nextDouble(RandomSource.create(), -0.05, 0.05));

            this.playShootSounds(level, recruit.blockPosition());

            if(item != null && RecruitsWariumConfig.SHOULD_RECRUITS_DROP_CASINGS.get()) {
                Vec3 eyePosition = recruit.getEyePosition().subtract(0, 0.5, 0);

                ItemEntity entityToSpawn = new ItemEntity(level, eyePosition.x, eyePosition.y, eyePosition.z, new ItemStack((ItemLike) CrustyChunksModItems.MEDIUM_CASING.get()));
                entityToSpawn.setPickUpDelay(10);
                level.addFreshEntity(entityToSpawn);
            }

            if(consumeAmmo) {
                this.consumeAmmo(recruit.getMainHandItem(), 1);
            }
        }
    }

    public int getAmmo(AbstractRecruitEntity recruit) {
        for(int i = 0; i < recruit.inventory.getContainerSize(); i++) {
            ItemStack itemStack = recruit.inventory.getItem(i);

            if(itemStack != null) {
                if(itemStack.getCount() > 0) {
                    Item item = itemStack.getItem();

                    if(this.getAmmo().contains(item) && item instanceof IAmmo) {
                        return i;
                    }
                }
            }
        }


        return -1;
    }

    public int reloadWeapon(AbstractRecruitEntity recruit) {
        CompoundTag tag = recruit.getMainHandItem().getOrCreateTag();

        int ammoLocation = this.getAmmo(recruit);

        if(ammoLocation != -1) {
            ItemStack stack = recruit.inventory.getItem(ammoLocation);

            EntityType<? extends AbstractArrow> projecile = ((IAmmo) stack.getItem()).getProjecile();

            ResourceLocation entityID = ForgeRegistries.ENTITY_TYPES.getKey(projecile);

            if(entityID != null) {
                tag.putString("Projectile", entityID.toString());
            }

            Item casing = ((IAmmo) stack.getItem()).getCasing();

            if(casing != null) {
                ResourceLocation casingID = ForgeRegistries.ITEMS.getKey(casing);

                tag.putString("Casing", casingID.toString());
            }

            recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(this.getLoadSound().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);

            int remainder;

            if(this.isAmmoMagazine()) {
                remainder = this.addAmmo(recruit.getMainHandItem(), this.getMaxAmmo());

                stack.setCount(remainder - 1);

            } else {
                remainder = this.addAmmo(recruit.getMainHandItem(), stack.getCount());

                if(remainder > 0 ) {
                    stack.setCount(remainder);
                } else {
                    recruit.inventory.removeItem(ammoLocation, stack.getCount());

                    stack = null;
                }

                double amount = tag.getDouble("Ammo");

                while (amount < this.getMaxAmmo()) {
                    int spot = this.getAmmo(recruit);

                    if(spot == -1) break;

                    ItemStack filler = recruit.inventory.getItem(spot);

                    remainder = this.addAmmo(recruit.getMainHandItem(), filler.getCount());

                    if(remainder > 0 ) {
                        filler.setCount(remainder);
                    } else {
                        recruit.inventory.removeItem(spot, filler.getCount());

                        filler = null;
                    }

                    amount = tag.getDouble("Ammo");
                }


            }

            return this.getWeaponLoadTime();
        }

        return 0;
    }

}

package com.logic.recruitswr.compat;

import com.logic.recruitswr.bridge.IAmmo;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.mcreator.crustychunks.init.CrustyChunksModSounds;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        Optional<Holder<EntityType<?>>> holder = ForgeRegistries.ENTITY_TYPES.getHolder(ResourceLocation.tryParse(recruit.getMainHandItem().getOrCreateTag().getString("Projectile")));

        EntityType<? extends AbstractArrow> entityType = null;

        if(holder.isPresent()) {
            entityType = (EntityType<? extends AbstractArrow>) holder.get().value();
        }

        if(RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get() ) {

            if(this.hasAmmo(recruit.getMainHandItem())) {
                if(entityType != null) {

                    this.shootProjectiles(recruit, entityType, true);
                } else {
                    ItemStack ammoStack = this.getAmmo(recruit);

                    if(ammoStack != null) {
                        entityType = ((IAmmo)ammoStack.getItem()).getProjecile();

                        this.shootProjectiles(recruit, entityType, true);
                    } else {

                        recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(CrustyChunksModSounds.DRYFIRE.get().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);
                    }
                }
            } else {

                recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(CrustyChunksModSounds.DRYFIRE.get().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);
            }

        } else {
            ItemStack ammoStack = this.getAmmo(recruit);

            if(entityType != null) {

                this.shootProjectiles(recruit, entityType, false);

            } else if(ammoStack != null) {
                entityType = ((IAmmo)ammoStack.getItem()).getProjecile();

                this.shootProjectiles(recruit, entityType, true);
            } else {
                Item item = this.getAmmo().get(0);

                this.shootProjectiles(recruit, ((IAmmo)item).getProjecile(), false);
            }
        }
    }

    private void shootProjectiles(AbstractRecruitEntity recruit, EntityType<? extends AbstractArrow> entityType, boolean consumeAmmo) {
        for (int i = 0; i < this.getShotAmount(); i++) {
            for(int j = 0; j < this.getBulletAmount(); j++) {
                AbstractArrow abstractArrow = entityType.create(recruit.level());

                AbstractArrow projectile = this.shootArrow(recruit, abstractArrow, 0, 0, 0);

                recruit.level().addFreshEntity(projectile);
            }

            if(consumeAmmo) {
                this.consumeAmmo(recruit.getMainHandItem(), 1);
            }
        }
    }

    private ItemStack getAmmo(AbstractRecruitEntity recruit) {
        for(int i = 0; i < recruit.inventory.getContainerSize(); i++) {
            ItemStack itemStack = recruit.inventory.getItem(i);

            if(itemStack != null) {
                if(itemStack.getCount() > 0) {
                    Item item = itemStack.getItem();

                    if(this.getAmmo().contains(item) && item instanceof IAmmo) {
                        return itemStack;
                    }
                }
            }
        }


        return null;
    }

    public int reloadWeapon(AbstractRecruitEntity recruit) {
        ItemStack stack = this.getAmmo(recruit);

        if(stack != null) {
            int remainder;

            if(this.isAmmoMagazine()) {
                remainder = this.addAmmo(recruit.getMainHandItem(), this.getMaxAmmo());
            } else {
                remainder = this.addAmmo(recruit.getMainHandItem(), stack.getCount());
            }

            EntityType<? extends AbstractArrow> projecile = ((IAmmo) stack.getItem()).getProjecile();

            ResourceLocation entityID = ForgeRegistries.ENTITY_TYPES.getKey(projecile);

            if(entityID != null) {
                recruit.getMainHandItem().getOrCreateTag().putString("Projectile", entityID.toString());
            }

            if(remainder > 0 ) {
                stack.setCount(remainder);
            } else {
                stack.setCount(0);
            }



            recruit.level().playSound(null, recruit.blockPosition(), SoundEvent.createVariableRangeEvent(this.getLoadSound().getLocation()), SoundSource.NEUTRAL, 2.5F, 1.0F);


            return this.getWeaponLoadTime();
        }

        return 0;
    }

}

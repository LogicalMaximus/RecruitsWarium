package com.logic.recruitswr.compat.weapons.grenades;

import com.logic.recruitswr.utils.RecruitsWariumUtils;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class GrenadeWeapon extends AbstractGrenadeWeapon {

    public GrenadeWeapon() {
        super(CrustyChunksModItems.GRENADE.get());

        this.getAmmo().add(CrustyChunksModItems.GRENADE.get());
        this.getAmmo().add(CrustyChunksModItems.INCENDIARY_GRENADE.get());
        this.getAmmo().add(CrustyChunksModItems.SMOKE_GRENADE.get());
    }

    public AbstractArrow shootArrow(LivingEntity livingEntity, LivingEntity target, AbstractArrow abstractArrow, double v, double v1, double v2) {
        abstractArrow.setOwner(livingEntity);
        abstractArrow.setBaseDamage((double)1.0);
        abstractArrow.setKnockback(1);
        abstractArrow.setSilent(true);

        abstractArrow.setPos(livingEntity.getX(), livingEntity.getEyeY() - 0.1, livingEntity.getZ());

        double distance = livingEntity.distanceToSqr(target.getX(), target.getY(), target.getZ());
        double heightDiff = target.getY() - livingEntity.getY();
        double d0 = target.getX() - livingEntity.getX();
        double d1 = target.getY() - abstractArrow.getY() + (double)target.getEyeHeight();
        double d2 = target.getZ() - livingEntity.getZ();
        double d3 = (double) Mth.sqrt((float)(d0 * d0 + d2 * d2));
        double angle = RecruitsWariumUtils.getAngleDistanceModifier(distance, 47, 4) + RecruitsWariumUtils.getAngleHeightModifier(distance, heightDiff, 1.0) / 100.0;
        float force = 1.9F + RecruitsWariumUtils.getForceDistanceModifier(distance, 1.899999976158142);
        float accuracy = 0.15F;

        livingEntity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(d0, d1 + d3 * angle, d2));

        abstractArrow.shoot(d0, d1 + d3 * angle, d2, force, accuracy);

        return abstractArrow;
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity livingEntity, AbstractArrow abstractArrow, double v, double v1, double v2) {
        return null;
    }

    public boolean isExplosive() {
        return true;
    }
}

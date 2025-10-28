package com.logic.recruitswr.events;

import com.logic.recruitswr.RecruitsWarium;
import com.logic.recruitswr.config.RecruitsWariumConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = RecruitsWarium.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ForgeEvents {

    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();

        List<AbstractRecruitEntity> recruits = projectile.level().getEntitiesOfClass(AbstractRecruitEntity.class, projectile.getBoundingBox().inflate(RecruitsWariumConfig.BULLET_SUPPRESSION_RADIUS.get()));

        Entity owner = projectile.getOwner();

        if(owner instanceof LivingEntity lv) {
            for(AbstractRecruitEntity recruit : recruits) {
                if(recruit.canAttack(lv)) {
                    recruit.setMoral(recruit.getMorale() - RecruitsWariumConfig.MORALE_SUPPRESSION_LOSS.get());
                }
            }
        }
    }

}

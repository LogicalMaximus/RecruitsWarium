package com.logic.recruitswr.entity.ai;

import com.logic.recruitswr.bridge.IGrenade;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.entities.AssassinEntity;
import com.talhanation.recruits.pathfinding.AsyncPathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FleeGrenade extends Goal {
    private final AsyncPathfinderMob entity;
    private int cooldown = 0;

    private Projectile runFromEntity;
    protected Path path;

    public FleeGrenade(AsyncPathfinderMob creatureEntity) {
        this.entity = creatureEntity;
    }

    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.runFromEntity != null && this.runFromEntity.isAlive();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void start() {
        List<Projectile> entities = this.entity.level().getEntitiesOfClass(Projectile.class, this.entity.getBoundingBox().inflate((double)20.0F));

        Stream<Projectile> projectileStream = entities.stream().filter((e) -> e instanceof IGrenade);

        List<Projectile> tntEntities = new ArrayList<>(projectileStream.toList());

        tntEntities.sort(Comparator.comparingDouble(this.entity::distanceTo));

        Optional<Projectile> optionalProjectile = tntEntities.stream().findFirst();

        if (optionalProjectile.isPresent()) {
            this.runFromEntity = optionalProjectile.get();

            Vec3 vec3 = DefaultRandomPos.getPosAway(this.entity, 20, 7, this.runFromEntity.position());

            if(vec3 != null) {
                this.path = this.entity.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 1);
            }

        } else {
            this.runFromEntity = null;
            this.setFleeing(false);
        }
    }

    @Override
    public void tick() {
        if(this.runFromEntity != null && this.runFromEntity.isAlive() && this.entity.distanceTo(this.runFromEntity) < 12) {
            if(this.path != null) {
                this.entity.getNavigation().moveTo(this.path, 1.5F);
                this.setFleeing(true);
            }
            else {
                Vec3 vec3 = DefaultRandomPos.getPosAway(this.entity, 20, 7, this.runFromEntity.position());

                if(vec3 != null) {
                this.path = this.entity.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 1);
                }
            }
        }
        else {
            this.setFleeing(false);
        }

        super.tick();
    }

    private void setFleeing(boolean fleeing) {
        AsyncPathfinderMob var3 = this.entity;
        if (var3 instanceof AbstractRecruitEntity recruit) {
            recruit.setFleeing(fleeing);
        }

        var3 = this.entity;
        if (var3 instanceof AssassinEntity assassin) {
            assassin.setFleeing(fleeing);
        }

    }
}

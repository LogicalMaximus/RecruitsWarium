package com.logic.recruitswr.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ParticleEntity extends Entity {
    private int lifeTime;

    public ParticleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);

        this.dimensions = EntityDimensions.scalable(0.2F, 0.2F);
        this.noPhysics = true;
        this.lifeTime = 0;
    }

    public void setDimensions(float x, float y) {
        this.dimensions = EntityDimensions.scalable(x, y);
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public void tick() {
        if(this.lifeTime <= 0) {
            this.discard();
        }

        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}

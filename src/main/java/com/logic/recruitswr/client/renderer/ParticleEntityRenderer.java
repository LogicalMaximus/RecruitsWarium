package com.logic.recruitswr.client.renderer;

import com.logic.recruitswr.entity.ParticleEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ParticleEntityRenderer extends EntityRenderer<ParticleEntity> {
    public ParticleEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(ParticleEntity p_114482_) {
        return null;
    }
}

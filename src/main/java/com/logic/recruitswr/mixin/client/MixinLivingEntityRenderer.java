package com.logic.recruitswr.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
    protected MixinLivingEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Inject(method = "setupRotations", at = @At("TAIL"))
    protected void setupRotations(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_, CallbackInfo ci) {
        if(p_115317_ instanceof AbstractRecruitEntity) {
            if (p_115317_.isVisuallySwimming()) {
                p_115318_.mulPose(Axis.XP.rotationDegrees(-90.0F));
                p_115318_.translate(0.0F, -1.0F, 0.3F);

            }
        }
    }
}

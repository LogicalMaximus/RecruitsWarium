package com.logic.recruitswr.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.talhanation.recruits.client.models.RecruitVillagerModel;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecruitVillagerModel.class)
public abstract class MixinRecruitVillagerModel extends HumanoidModel<AbstractRecruitEntity> {

    @Unique
    private ModelPart root;

    @Unique
    private ModelPart head;

    @Unique
    private ModelPart leftLeg;

    @Unique
    private ModelPart rightLeg;

    @Unique
    private ModelPart rightArm;

    @Unique
    private ModelPart leftArm;


    public MixinRecruitVillagerModel(ModelPart p_170677_) {
        super(p_170677_);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(ModelPart part, CallbackInfo ci) {
        this.root = part;
        this.head = part.getChild("head");
        this.leftLeg = part.getChild("left_leg");
        this.rightLeg = part.getChild("right_leg");
        this.leftArm = part.getChild("left_arm");
        this.rightArm = part.getChild("right_arm");
    }

    public ModelPart root() {
        return this.root;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public void translateToHand(HumanoidArm p_102925_, PoseStack p_102926_) {
        this.getArm(p_102925_).translateAndRotate(p_102926_);
    }
}

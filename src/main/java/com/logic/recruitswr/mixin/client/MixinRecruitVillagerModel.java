package com.logic.recruitswr.mixin.client;

import com.logic.recruitswr.bridge.IPose;
import com.logic.recruitswr.entity.poses.RecruitPose;
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

    @Inject(method = "setupAnim(Lcom/talhanation/recruits/entities/AbstractRecruitEntity;FFFFF)V", at = @At("HEAD"), remap = false)
    public void setupAnim(AbstractRecruitEntity recruit, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_, CallbackInfo ci) {
        RecruitPose pose = ((IPose)recruit).getAimingPose();

        if(pose == RecruitPose.SIGHT_GUN) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, recruit, true);
        }
        else if(pose == RecruitPose.IDLE_GUN) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
        }
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

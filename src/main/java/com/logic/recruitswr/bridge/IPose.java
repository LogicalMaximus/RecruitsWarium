package com.logic.recruitswr.bridge;

import com.logic.recruitswr.entity.poses.RecruitPose;

public interface IPose {


    RecruitPose getAimingPose();

    void setAimingPose(RecruitPose pose);
}

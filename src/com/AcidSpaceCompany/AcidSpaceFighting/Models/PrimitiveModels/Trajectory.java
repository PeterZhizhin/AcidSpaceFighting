package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public interface Trajectory {

    Point getPoint(float time);

    float getAngle(float time);

}

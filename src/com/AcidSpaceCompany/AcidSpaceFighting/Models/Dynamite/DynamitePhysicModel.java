package com.AcidSpaceCompany.AcidSpaceFighting.Models.Dynamite;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class DynamitePhysicModel extends PhysicModel {

    private boolean inFirstTime=true;

    public void doSpecialActionA() {
        health=0;
        if (inFirstTime) {
        World.explode(getCentre(), body.getMaxLength());
            inFirstTime=false;
        }
    }

    public DynamitePhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

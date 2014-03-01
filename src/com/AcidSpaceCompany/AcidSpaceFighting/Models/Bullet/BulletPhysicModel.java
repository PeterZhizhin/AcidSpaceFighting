package com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class BulletPhysicModel extends PhysicModel {

    private boolean firstTime;

    public void update(float time) {
        health-= time*0.1f;
    }




    public void useForce(Point posOfForce, Point force) {
        if (force.length()>=500000000)  {
            firstTime=!firstTime;
            if (firstTime) {
                 health=0;
                 World.explode(getCentre(), body.getMaxLength());
            }
        }
        super.useForce(posOfForce, force);
    }

    public BulletPhysicModel(GeometricModel body, float mass) {
        super(body, mass);
        firstTime=true;
    }

}

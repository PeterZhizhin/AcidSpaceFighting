package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Effects.Explosion;
import com.company.Physic.PhysicModel;
import com.company.World;

public class DynamitePhysicModel extends PhysicModel {

    private boolean inFirstTime=true;

    public void doSpecialActionA(float deltaTime) {
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

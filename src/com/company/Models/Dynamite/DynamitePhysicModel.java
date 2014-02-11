package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Effects.Explosion;
import com.company.Physic.PhysicModel;
import com.company.World;

public class DynamitePhysicModel extends PhysicModel {

    public void doSpecialActionA(float deltaTime) {
        health=0;
        World.explode(getCentre(), body.getMaxLength());
    }

    public DynamitePhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

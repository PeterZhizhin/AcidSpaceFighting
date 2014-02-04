package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;

public class EnginePhysicModel extends PhysicModel {

    private static final float power = 1000000f;

    public void doSpecialActionA(float deltaTime) {
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power));
    }

    public void doSpecialActionB(float deltaTime) {
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).negate().multiply(power));
    }

    public void doSpecialActionC(float deltaTime) {
        w = 0;
        speedVector.set(0, 0);
    }

    public EnginePhysicModel(GeometricModel body, Point[] conetionPoints, float mass) {
        super(body, conetionPoints, mass);
    }

}

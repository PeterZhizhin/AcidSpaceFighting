package com.company.Models.RocketEngine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Geometry.Segment;
import com.company.Physic.PhysicModel;

public class RocketEnginePhysicModel extends PhysicModel{

    private static final float power=10000f;

    public void doSpecialActionA(float deltaTime) {
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power));
        //System.out.println(new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power)+" "+speedVector.getLength());
    }

    public void doSpecialActionB(float deltaTime) {
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).negate().multiply(power));
        //System.out.println(new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power).negate()+" "+speedVector.getLength());
    }

    public void doSpecialActionC(float deltaTime) {
        w=0;
        speedVector.set(0, 0);
    }

    public RocketEnginePhysicModel(GeometricModel body, float mass) {
        super(body, mass);
    }

}

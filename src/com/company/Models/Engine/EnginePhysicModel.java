package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;

public class EnginePhysicModel extends PhysicModel {

    private static final float power = 1000000f;
    private static final float deltaActivity = 1f;
    private float activity = 0f;
    private boolean isPressed = false;
    private EngineModel engMod;

    public void update(float deltaTime) {
        if (isPressed) {
            if (activity < 1f)
                activity += deltaActivity * deltaTime;
        } else {
            if (activity > 0)
                activity -= deltaActivity * deltaTime;
        }
        activity = Math.min(1f, Math.max(0f, activity));
        engMod.setActivity(activity);
        isPressed = false;
        if (activity>0)
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power*activity));
    }

    public void doSpecialActionA(float deltaTime) {
        isPressed = true;
    }

    public void doSpecialActionB(float deltaTime) {
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).negate().multiply(power));
        isPressed = true;
    }

    public void doSpecialActionC(float deltaTime) {
        w = 0;
        speedVector.set(0, 0);
    }

    public EnginePhysicModel(EngineModel e, GeometricModel body, Point[] conetionPoints, float mass) {
        super(body, conetionPoints, mass);
        engMod=e;
    }

}

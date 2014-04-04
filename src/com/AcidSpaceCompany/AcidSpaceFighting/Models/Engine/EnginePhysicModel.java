package com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class EnginePhysicModel extends PhysicModel {

    private static final float power = 16000000f;
    private static final float deltaActivity = 1f;
    private boolean isPressed = false;

    public void update(float deltaTime) {
        if (isPressed) {
            if (activity < 1f)
                activity += deltaActivity * deltaTime;
        } else {
            if (activity > 0)
                activity -= deltaActivity * deltaTime;
        }
        activity = Math.min(1f, Math.max(0f, activity));
        isPressed = false;
        if (activity>0)
        useForce(body.getCentre(), new Point(Math.cos(body.getAngle()), Math.sin(body.getAngle())).multiply(power*activity));

        timer--;
    }

    private int timer=0;
    public void doSpecialActionA() {
        isPressed = true;

        if (timer<=0) {
        SoundBase.playEngine();
            timer=5;
        }
    }

    public EnginePhysicModel(GeometricModel body, Point[] connetionPoints, float mass) {
        super(body, connetionPoints, mass);
    }

}

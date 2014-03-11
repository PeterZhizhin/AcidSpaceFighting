package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

public class MapLayer {

    private static float angle;
    private static final float PI2= (float) (Math.PI*2);
    private static final float deltaAngle=0.1f;

    public static void update(float dt) {
        angle+=deltaAngle*dt;
        if (angle>PI2) angle-=PI2;
    }

    public static void draw() {

    }
}

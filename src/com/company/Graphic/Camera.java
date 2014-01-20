package com.company.Graphic;

import org.lwjgl.opengl.GL11;

public class Camera {

    private static float xPos, yPos, scale;

    public static void init() {
        xPos=0;
        yPos=0;
        scale=1;
    }

    public static void move(int dX, int dY) {
         xPos+=dX*scale;
        yPos+=dY*scale;
    }

    public static void reScale(int reScale) {
        if (reScale>0) scale/=1.3f;
        else
        if (reScale<0) scale*=1.3f;
    }

    public static void translatePoint(float x, float y) {
        GL11.glVertex2f((x-xPos)/scale, (y-yPos)/scale);
    }

}

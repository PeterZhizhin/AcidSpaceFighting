package com.company.Graphic;

import com.company.Geometry.Point;
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

    public static String getMessage() {
        return "Coordinates: "+xPos+" ; "+yPos+" Scale: "+scale;
    }

    public static void translatePoint(float x, float y) {
        GL11.glVertex2f((x-xPos)/scale, (y-yPos)/scale);
    }

    public static void translatePoint(Point p) {
        GL11.glVertex2f((p.getX()-xPos)/scale, (p.getY()-yPos)/scale);
    }

    public static Point repairPoint(float x, float y) {
        return new Point(x*scale+xPos, y*scale+yPos);
    }

}

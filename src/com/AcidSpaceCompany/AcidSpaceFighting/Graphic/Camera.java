package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Camera {

    private static float xPos, yPos, scale;

    public static float getX(){
           return xPos;
    }

    public static float getY(){
            return yPos;
    }

    public static float getScale() {
        return scale;
    }

    public static void init() {
        xPos = 0;
        yPos = 0;
        scale = 4f;
    }

    public static void move(Point delta)
    {
        move((int)delta.x, (int)delta.y);
    }

    public static void move(int dX, int dY) {
        xPos += dX * scale;
        yPos += dY * scale;
    }

    public static void setPosition(float x, float y) {
        float xWidth = Display.getWidth() * scale / 2;
        float xHeight = Display.getHeight() * scale / 2;
        xPos = x - xWidth;
        yPos = y - xHeight;
    }

    public static void setPosition(Point p) {
        setPosition(p.x, p.y);
    }

    public static void reScale(int reScale) {
        if (reScale > 0) scale /= 1.3f;
        else if (reScale < 0) scale *= 1.3f;
    }

    public static void setScaleByDistance(float dist) {
        Camera.scale=dist/Display.getWidth();
    }

    public static String getMessage() {
        return "Coordinates: " + xPos + " ; " + yPos + " Scale: " + scale;
    }

    public static Point getSourcePoint(Point p) {
        return new Point(p.getX() *scale + xPos, p.getY() *scale + yPos);
    }

    public static float translateDistance(float f) {
         return f/scale;
    }

    public static float untranslateDistance(float f) {
        return f*scale;
    }

    public static void translatePoint(Point p) {
        translatePoint(p.x, p.y);
    }

    public static void translatePoint(float x, float y) {
        GL11.glVertex2f((x - xPos) / scale, (y - yPos) / scale);
    }

    //return point in the world by point on the screen
    public static Point repairPoint(float x, float y) {
        return new Point(x * scale + xPos, y * scale + yPos);
    }

}

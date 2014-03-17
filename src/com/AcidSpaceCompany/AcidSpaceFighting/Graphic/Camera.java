package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Camera {

    private static float xPos, yPos, scale;
    private static float xPosObject, yPosObject;
    private static float cameraSpeed=250f;

    public static float getX(){
           return xPos;
    }

    public static float getY(){
            return yPos;
    }

    public static void init() {
        xPos = 0;
        yPos = 0;
        scale = 2f;
    }

    public static void update(float dt) {
        if (xPos-Point.epsilon>xPosObject) xPos=Math.max(xPosObject, xPos-dt*cameraSpeed);
        else if (xPos+Point.epsilon<xPosObject) xPos=Math.min(xPosObject, xPos+dt*cameraSpeed);

        if (yPos-Point.epsilon>yPosObject) yPos=Math.max(yPosObject, yPos-dt*cameraSpeed);
        else if (yPos+Point.epsilon<yPosObject) yPos=Math.min(yPosObject, yPos+dt*cameraSpeed);
    }

    public static void setPosition(float x, float y) {
        float xWidth = Display.getWidth() * scale / 2;
        float xHeight = Display.getHeight() * scale / 2;
        xPos = x - xWidth;
        yPos = y - xHeight;
        xPosObject = xPos;
        yPosObject = yPos;
    }

    public static void moveTo(float x, float y) {
        float xWidth = Display.getWidth() * scale / 2;
        float xHeight = Display.getHeight() * scale / 2;
        xPosObject = x - xWidth;
        yPosObject = y - xHeight;
    }

    public static void setPosition(Point p) {
        setPosition(p.x, p.y);
    }

    public static void moveTo(Point p) {
        moveTo(p.x, p.y);
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
    public static Point translateWindowWorld(float x, float y) {
        return new Point(x * scale + xPos, y * scale + yPos);
    }
    public static Point translateWindowWorld(Point point)
    {
        return translateWindowWorld(point.getX(), point.getY());
    }

}

package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Camera {

    private static float xPos, yPos, scale;
    private static float xPosObject, yPosObject, scaleObject;
    private static float cameraSpeed=10000f;
    private static float scaleSpeed=50f;

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
        scaleObject=2f;
    }

    private static void setScale(float f) {
          scale=f;
    }

    public static void update(float dt) {

        float cameraSpeed=untranslateDistance(Camera.cameraSpeed);


        if (scale-Point.epsilon>scaleObject) setScale(Math.max(scaleObject, scale-dt*scaleSpeed));
        else if (scale+Point.epsilon<scaleObject) setScale(Math.min(scaleObject, scale+dt*scaleSpeed));
        else {

            if (xPos-Point.epsilon>xPosObject) xPos=Math.max(xPosObject, xPos-dt*cameraSpeed);
            else if (xPos+Point.epsilon<xPosObject) xPos=Math.min(xPosObject, xPos+dt*cameraSpeed);

            if (yPos-Point.epsilon>yPosObject) yPos=Math.max(yPosObject, yPos-dt*cameraSpeed);
            else if (yPos+Point.epsilon<yPosObject) yPos=Math.min(yPosObject, yPos+dt*cameraSpeed);

        }

        //if (Mouse.getDWheel()!=0)
            reScale(Mouse.getDWheel());
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
        if (reScale > 0) scaleObject /= 1.3f;
        else if (reScale < 0) scaleObject *= 1.3f;
    }

    public static String getMessage() {
        return "Coordinates: " + xPos + " ; " + yPos + " Scale: " + scale;
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

    public static Point getTranslatedPoint(float x, float y) {
         return new Point((x - xPos) / scale, (y - yPos) / scale);
    }

    //return point in the world by point on the screen
    public static Point untranslatePoint(float x, float y) {
        return new Point(x * scale + xPos, y * scale + yPos);
    }

    public static Point untranslatePoint(Point point)
    {
        return untranslatePoint(point.getX(), point.getY());
    }

}

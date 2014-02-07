package com.company.Graphic;

import com.company.Geometry.Point;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Camera {

    private static float xPos, yPos, scale;

    public static void init() {
        xPos = 0;
        yPos = 0;
        scale = 1;
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

    public static void reScale(int reScale) {
        if (reScale > 0) scale /= 1.3f;
        else if (reScale < 0) scale *= 1.3f;
    }

    public static String getMessage() {
        return "Coordinates: " + xPos + " ; " + yPos + " Scale: " + scale;
    }

    public static void translatePoint(float x, float y) {
        GL11.glVertex2f((x - xPos) / scale, (y - yPos) / scale);
    }

    public static Point getTranslatedPoint(Point p) {
        return new Point((p.getX() - xPos) / scale, (p.getY() - yPos) / scale);
    }

    public static Point getSourcePoint(Point p) {
        return new Point(p.getX() *scale + xPos, p.getY() *scale + yPos);
    }

    public static void translatePoint(Point p) {
        GL11.glVertex2f((p.getX() - xPos) / scale, (p.getY() - yPos) / scale);
    }

    public static Point repairPoint(float x, float y) {
        return new Point(x * scale + xPos, y * scale + yPos);
    }

}

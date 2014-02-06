package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;
import com.company.World;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GunGraphicModel extends GraphicModel {

    private Random rnd = new Random();

    public void drawBackgroundLayer() {
        glColor4f(0f, 0f, 1f, rnd.nextFloat() / 3);
        Point nearest = World.getNearestPhysicModel(shape.getCentre());
        glBegin(GL_LINES);
        for (int i = 0; i < 10; i++) {
            Camera.translatePoint(nearest.getX() + 10f - rnd.nextFloat() * 20, nearest.getY() + 10f - rnd.nextFloat() * 20);
            Camera.translatePoint(shape.getCentre().getX() + 10f - rnd.nextFloat() * 20, shape.getCentre().getY() + 10f - rnd.nextFloat() * 20);
        }
        glEnd();
    }

    public void drawTopLayer() {

        //body
        glColor3f(0.7f, 0.7f, 0.7f);
        glBegin(GL_POLYGON);
        for (int i = 0; i < shape.getPointCount(); i++)
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();

        //frame
        glColor3f(0.15f, 0.15f, 0.15f);
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < shape.getPointCount(); i++)
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();

        glBegin(GL_LINES);
        Camera.translatePoint(shape.getCentre().getX(), shape.getCentre().getY());
        Camera.translatePoint((float) (shape.getCentre().getX() + 50 * Math.cos(shape.getAngle())),
                (float) (shape.getCentre().getY() + 50 * Math.sin(shape.getAngle())));
        glEnd();

    }

    public GunGraphicModel(GeometricModel body) {
        super(body);
    }

}

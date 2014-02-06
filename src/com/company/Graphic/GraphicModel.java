package com.company.Graphic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;

import java.util.Random;

import static com.company.Graphic.Camera.getTranslatedPoint;
import static org.lwjgl.opengl.GL11.*;

public class GraphicModel {

    protected GeometricModel shape;
    protected PhysicModel body;
    private float[] color = new float[3];

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        for (int i = 0; i < shape.getPointCount(); i++) {
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        }
        glEnd();
    }

    public void drawHealthLine() {
            Point p=getTranslatedPoint(shape.getCentre());

        glBegin(GL_QUADS);
        glColor4f(0.2f, 1f, 0.2f, 0.5f);
        glVertex2f(p.x-25, p.y-5);
        float dmg50=body.getHealth()*50;
        glVertex2f(p.x-25+dmg50, p.y-5);
        glVertex2f(p.x-25+dmg50, p.y+5);
        glVertex2f(p.x-25, p.y+5);
        glEnd();

        glBegin(GL_LINE_LOOP);
            glColor3f(0, 0, 0);
            glVertex2f(p.x-25, p.y-5);
            glVertex2f(p.x+25, p.y-5);
            glVertex2f(p.x+25, p.y+5);
            glVertex2f(p.x-25, p.y+5);
            glColor3f(0.2f, 1f, 0.2f);
        glEnd();
    }

    public void setPhysicModel(PhysicModel p) {
        body=p;
    }

    public GraphicModel(GeometricModel body) {

        Random rnd = new Random();
        color[0] = rnd.nextFloat() / 2 + 0.5f;
        color[1] = rnd.nextFloat() / 2 + 0.5f;
        color[2] = rnd.nextFloat() / 2 + 0.5f;

        if (body != null) {
            shape = body;
        }

    }


}

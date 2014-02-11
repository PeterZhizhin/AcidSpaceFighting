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

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
    }

    public void drawHealthLine() {


        Point p=getTranslatedPoint(shape.getCentre());
        float dmg50=body.getHealth()*50;

        glColor4f(0.2f, 1f, 0.2f, 0.5f);
        glBegin(GL_QUADS);
        glVertex2f(p.x-25, p.y-5);
        glVertex2f(p.x-25+dmg50, p.y-5);
        glVertex2f(p.x-25+dmg50, p.y+5);
        glVertex2f(p.x-25, p.y+5);
        glEnd();

        glColor3f(0, 0, 0);
        glBegin(GL_LINE_LOOP);
            glVertex2f(p.x-25, p.y-5);
            glVertex2f(p.x+25, p.y-5);
            glVertex2f(p.x+25, p.y+5);
            glVertex2f(p.x - 25, p.y + 5);
        glEnd();
    }

    public void setPhysicModel(PhysicModel p) {
        body=p;
    }

    public GraphicModel(GeometricModel body) {
        if (body != null) {
            shape = body;
        }

    }


}

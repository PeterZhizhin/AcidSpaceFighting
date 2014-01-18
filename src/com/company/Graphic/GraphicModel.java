package com.company.Graphic;

import com.company.Geometry.GeometricModel;

import static org.lwjgl.opengl.GL11.*;

public class GraphicModel {

    private GeometricModel shape;

    public void draw() {
        glColor3f(1f, 0f, 1f);
          glBegin(GL_POLYGON);
           for (int i=0; i<shape.getPointCount(); i++)
               glVertex2f(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();
    }

    public GraphicModel(GeometricModel body) {
           shape=body;
    }



}

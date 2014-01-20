package com.company.Graphic;

import com.company.Geometry.GeometricModel;
import com.company.Model;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GraphicModel {

    private GeometricModel shape;
    private float[] color=new float[3];

    public void draw() {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_LINE_LOOP);
           for (int i=0; i<shape.getPointCount(); i++) {
               Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
           }
        glEnd();
    }

    public GraphicModel(GeometricModel body) {
           shape=body;
        Random rnd=new Random();
        color[0]=rnd.nextFloat()/2+0.5f;
        color[1]=rnd.nextFloat()/2+0.5f;
        color[2]=rnd.nextFloat()/2+0.5f;
    }



}

package com.company.Graphic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;

import java.util.LinkedList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GraphicModel {

    protected GeometricModel shape;
    private float[] color=new float[3];
    private PhysicModel ourPhysicModel;
    protected LinkedList<Point> trajectory;
    private int step=0;
    private static final int stepLimit=10;

    public void drawBackgroundLayer() {
        float colorStep=0.1f/ trajectory.size();
        float currentColor=0.1f;

        glBegin(GL_LINES);
        glColor4f(color[0], color[1], color[2], currentColor);
        Camera.translatePoint(trajectory.get(0).getX(), trajectory.get(0).getY());
        Camera.translatePoint(shape.getCentre().getX(), shape.getCentre().getY());

        for (int i=0; i< trajectory.size()-1; i++) {

            glColor4f(color[0], color[1], color[2], currentColor);
            Camera.translatePoint(trajectory.get(i).getX(), trajectory.get(i).getY());
            Camera.translatePoint(trajectory.get(i+1).getX(), trajectory.get(i+1).getY());
            currentColor-=colorStep;
        }


        glEnd();

        if (step==stepLimit) {
            trajectory.remove(trajectory.size()-1);
            trajectory.add(0, new Point(shape.getCentre()));
            step=0;
        }
        else step++;
    }

    public void drawTopLayer() {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
           for (int i=0; i<shape.getPointCount(); i++) {
               Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
           }
        glEnd();

    }

    public GraphicModel(GeometricModel body, PhysicModel our) {

        Random rnd=new Random();
        color[0]=rnd.nextFloat()/2+0.5f;
        color[1]=rnd.nextFloat()/2+0.5f;
        color[2]=rnd.nextFloat()/2+0.5f;
        trajectory =new LinkedList<Point>();

        if (body!=null) {
        ourPhysicModel=our;
           shape=body;


        for (int i=0; i<50; i++)
            trajectory.add(new Point(shape.getCentre()));   }

    }



}

package com.company.Models.Asteroid;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class AsteroidGraphicModel extends GraphicModel {

    private int step=0;
    private static final int stepLimit=8;

        public void draw() {
            //tail
            float colorStep=0.8f/ trajectory.size();
            float currentColor=0.8f;

            glBegin(GL_LINE_LOOP);

            Point normal=Point.getNormal(shape.getCentre(), trajectory.get(0));
            normal.normalise();
            currentColor-=colorStep;
            normal=normal.multiply(currentColor * shape.getMaxLength());
            glColor4f(0.5f, 0.5f, 0f, currentColor);

            Camera.translatePoint(shape.getCentre().getX()+normal.getX(), shape.getCentre().getY()+normal.getY());
            Camera.translatePoint(shape.getCentre().getX()-normal.getX(), shape.getCentre().getY()-normal.getY());
            Camera.translatePoint(trajectory.get(0).getX()-normal.getX(), trajectory.get(0).getY()-normal.getY());
            Camera.translatePoint(trajectory.get(0).getX()+normal.getX(), trajectory.get(0).getY()+normal.getY());
            glEnd();


            for (int i=0; i< trajectory.size()-1; i++) {

                glBegin(GL_LINE_LOOP);

                normal=Point.getNormal(trajectory.get(i), trajectory.get(i+1));
                normal.normalise();
                currentColor-=colorStep;
                normal=normal.multiply(currentColor * shape.getMaxLength());
                glColor4f(0.5f, 0.5f, 0f, currentColor);

                Camera.translatePoint(trajectory.get(i).getX()+normal.getX(), trajectory.get(i).getY()+normal.getY());
                Camera.translatePoint(trajectory.get(i).getX()-normal.getX(), trajectory.get(i).getY()-normal.getY());
                Camera.translatePoint(trajectory.get(i+1).getX()-normal.getX(), trajectory.get(i+1).getY()-normal.getY());
                Camera.translatePoint(trajectory.get(i+1).getX()+normal.getX(), trajectory.get(i+1).getY()+normal.getY());
                glEnd();
            }


            if (step>=stepLimit) {
                trajectory.remove(trajectory.size()-1);
                trajectory.add(0, new Point(shape.getCentre()));
                step=0;
            }
            else step++;

            //body
            glColor3f(1f, 1f, 0.6f);
            glBegin(GL_POLYGON);
            for (int i=0; i<shape.getPointCount(); i++)
                Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
            glEnd();

            //frame
            glColor3f(0.5f, 0.5f, 0f);
            glBegin(GL_LINE_LOOP);
            for (int i=0; i<shape.getPointCount(); i++)
                Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
            glEnd();

        }

        public AsteroidGraphicModel(GeometricModel body) {
            super(body, null);
        }

    }

package com.company.Models.GunEngine.RocketEngine.RocketBase;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class RocketBaseGraphicModel extends GraphicModel{

    private int step=0;
    private static final int stepLimit=8;
    private Random rnd=new Random();

    public void drawTopLayer() {


        //tail
        float colorStep=1f/ trajectory.size();
        float currentColor=1f;

        glBegin(GL_QUADS);

        Point normal=Point.getBisection(trajectory.get(0), trajectory.get(1), trajectory.get(2));
        normal.normalise();
        currentColor-=colorStep;
        normal=normal.multiply(currentColor * shape.getMaxLength());
        glColor4f(1f, 0.68359375f*currentColor+0.10546875f*(1-currentColor), 0.01953125f, 1f);

        Camera.translatePoint(shape.getCentre().getX()+normal.getX(), shape.getCentre().getY()+normal.getY());
        Camera.translatePoint(shape.getCentre().getX()-normal.getX(), shape.getCentre().getY()-normal.getY());
        Camera.translatePoint(trajectory.get(0).getX()-normal.getX(), trajectory.get(0).getY()-normal.getY());
        Camera.translatePoint(trajectory.get(0).getX()+normal.getX(), trajectory.get(0).getY()+normal.getY());
        glEnd();


        for (int i=0; i< trajectory.size()-3; i++) {

            glBegin(GL_POLYGON);


            Camera.translatePoint(trajectory.get(i).getX()+normal.getX(), trajectory.get(i).getY()+normal.getY());
            Camera.translatePoint(trajectory.get(i).getX()-normal.getX(), trajectory.get(i).getY()-normal.getY());

            normal=Point.getBisection(trajectory.get(i+1), trajectory.get(i+2), trajectory.get(i+3));
            normal.normalise();
            currentColor-=colorStep;
            normal=normal.multiply(currentColor * shape.getMaxLength());
            glColor4f(1f, 0.68359375f * currentColor + 0.10546875f * (1 - currentColor), 0.01953125f, 1f);

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
        glColor3f(0.7f, 0.7f, 0.7f);
        glBegin(GL_POLYGON);
        for (int i=0; i<shape.getPointCount(); i++)
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();

        //frame
        glColor3f(0.15f, 0.15f, 0.15f);
        glBegin(GL_LINE_LOOP);
        for (int i=0; i<shape.getPointCount(); i++)
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();

    }

    public RocketBaseGraphicModel(GeometricModel body) {
        super(body, null);
        for (int i=0; i<30; i++)
            trajectory.remove(0);
    }

}

package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class EngineGraphicModel extends GraphicModel {

    private int step = 0;
    private static final int stepLimit = 2;
    private Random rnd = new Random();
    private float activity=0f;

    public void setActivity(float a) {
        activity=a;
    }

    public void drawBackgroundLayer() {


        //tail
        float colorStep = 1f / trajectory.size();
        float currentColor = 1f;

        glBegin(GL_QUADS);

        Point normal = Point.getBisection(trajectory.get(0), trajectory.get(1), trajectory.get(2));
        normal.setLength(1);
        currentColor -= colorStep;
        normal = normal.multiply(currentColor * shape.getMaxLength());
        glColor4f(1f, 0.68359375f * currentColor + 0.10546875f * (1 - currentColor), 0.01953125f, 1f);

        Camera.translatePoint(shape.getCentre().getX() + normal.getX(), shape.getCentre().getY() + normal.getY());
        Camera.translatePoint(shape.getCentre().getX() - normal.getX(), shape.getCentre().getY() - normal.getY());
        Camera.translatePoint(trajectory.get(0).getX() - normal.getX(), trajectory.get(0).getY() - normal.getY());
        Camera.translatePoint(trajectory.get(0).getX() + normal.getX(), trajectory.get(0).getY() + normal.getY());
        glEnd();


        for (int i = 0; i < trajectory.size() - 3; i++) {

            glBegin(GL_POLYGON);


            Camera.translatePoint(trajectory.get(i).getX() + normal.getX(), trajectory.get(i).getY() + normal.getY());
            Camera.translatePoint(trajectory.get(i).getX() - normal.getX(), trajectory.get(i).getY() - normal.getY());

            normal = Point.getBisection(trajectory.get(i + 1), trajectory.get(i + 2), trajectory.get(i + 3));
            normal.setLength(1);
            currentColor -= colorStep;
            normal = normal.multiply(currentColor * shape.getMaxLength());
            glColor4f(1f, 0.01953125f, 0.3f, 0.68359375f * currentColor + 0.10546875f * (1 - currentColor));

            Camera.translatePoint(trajectory.get(i + 1).getX() - normal.getX(), trajectory.get(i + 1).getY() - normal.getY());
            Camera.translatePoint(trajectory.get(i + 1).getX() + normal.getX(), trajectory.get(i + 1).getY() + normal.getY());
            glEnd();
        }


        if (step >= stepLimit) {
            trajectory.remove(trajectory.size() - 1);
            trajectory.add(0, new Point(shape.getCentre()));
            step = 0;
        } else step++;
    }

    public void drawTopLayer() {

        //body
        glColor3f(0.3f+activity*2/3, 0.3f, 0.3f);
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

    public EngineGraphicModel(GeometricModel body) {
        super(body, null);
        for (int i = 0; i < 30; i++)
            trajectory.remove(0);
    }

}

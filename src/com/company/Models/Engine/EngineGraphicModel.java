package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.Tale;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class EngineGraphicModel extends GraphicModel {

    private Tale t;
    private float activity=0f;

    public void setActivity(float a) {
        activity=a;
    }

    public void drawBackgroundLayer() {
         t.addPoint(shape.getCentre(),//.add(new Point(Math.cos(shape.getAngle()), Math.sin(shape.getAngle())).multiply(shape.getMaxLength()*activity).negate()),
                 shape.getMaxLength()*activity/2);
        t.draw();
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
        super(body);
        t=new Tale(new Color(1f, 1f, 0f), new Color(1f, 0f, 0f), 20, 10, 5);
    }

}

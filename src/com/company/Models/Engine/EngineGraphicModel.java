package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.Tale;

import java.util.Random;

import static com.company.Geometry.Point.mixPoints;
import static org.lwjgl.opengl.GL11.*;

public class EngineGraphicModel extends GraphicModel {

    private Tale t;
    private float activity=0f;

    public void drawBackgroundLayer() {
        activity=body.getActivity();
         t.addPoint(shape.getCentre().add(new Point(Math.cos(shape.getAngle()), Math.sin(shape.getAngle())).multiply(shape.getMaxLength()*activity*2).negate()),
                 shape.getMaxLength()*activity/2);
        t.draw();
    }

    public void drawTopLayer() {

        //body
        //glColor3f(0.3f+activity*2/3, 0.3f, 0.3f);
        glColor3f(0.3f, 0.3f, 0.3f);
        glBegin(GL_POLYGON);
        for (int i = 0; i < shape.getPointCount(); i++)
            Camera.translatePoint(shape.getPoint(i).getX(), shape.getPoint(i).getY());
        glEnd();

        glColor3f(1f, 1f, 1f);
        glBegin(GL_POLYGON);
        Camera.translatePoint(shape.getPoint(0).getX(), shape.getPoint(0).getY());
        Point pMixed=mixPoints(shape.getPoint(0), shape.getPoint(1), activity);
        Camera.translatePoint(pMixed.getX(), pMixed.getY());
        pMixed=mixPoints(shape.getPoint(4), shape.getPoint(3), activity);
        Camera.translatePoint(pMixed.getX(), pMixed.getY());
        Camera.translatePoint(shape.getPoint(4).getX(), shape.getPoint(4).getY());

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
        t=new Tale(new Color(1f, 1f, 0f), new Color(1f, 0f, 0f), 20, 20, 5, true);
    }

}

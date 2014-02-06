package com.company.Models.Base;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class BaseGraphicModel extends GraphicModel {

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {


        //body
        glColor3f(0.7f, 0.7f, 0.7f);
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

    }

    public BaseGraphicModel(GeometricModel body) {
        super(body);
    }

}

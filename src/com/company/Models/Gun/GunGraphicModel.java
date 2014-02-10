package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;


import static org.lwjgl.opengl.GL11.*;

public class GunGraphicModel extends GraphicModel {

    public void drawTopLayer() {

        //body
        glColor3f(0.5f+body.getActivity()/2, 0.5f, 0.5f);
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
        Camera.translatePoint(shape.getCentre());
        Camera.translatePoint(shape.getPoint(2));
        glEnd();

    }

    public GunGraphicModel(GeometricModel body) {
        super(body);
    }

}

package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Controls.FontDrawer;
import com.company.Graphic.GraphicModel;

import static org.lwjgl.opengl.GL11.*;

public class DynamiteGraphicModel extends GraphicModel {

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

        glEnable(GL_TEXTURE_2D);
        FontDrawer.drawString(body.getCentre().x-40, body.getCentre().y-10, "PRESS X TO BOOM", new Color(1f, 0.5f, 0f), false);
        glDisable(GL_TEXTURE_2D);

    }

    public DynamiteGraphicModel(GeometricModel body) {
        super(body);
    }

}

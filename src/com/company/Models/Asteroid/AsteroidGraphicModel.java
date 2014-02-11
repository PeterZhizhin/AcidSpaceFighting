package com.company.Models.Asteroid;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.Tale;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class AsteroidGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength()/2);
        t.draw();
    }

    public void drawTopLayer() {
        //todo: realise
    }

    public AsteroidGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(new Color(1f, 1f, 0.6f), new Color(1f, 1f, 0.6f, 0f), 0, 20, 3, 8, false);
    }

}

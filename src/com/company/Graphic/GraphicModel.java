package com.company.Graphic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;

import java.util.Random;

import static com.company.Graphic.Camera.getTranslatedPoint;
import static org.lwjgl.opengl.GL11.*;

public class GraphicModel {

    protected GeometricModel shape;
    protected PhysicModel body;

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
    }

    public void drawHealth() {
        int hp=Math.round(body.getHealth()*4)+7;
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), hp);
    }

    public void setPhysicModel(PhysicModel p) {
        body=p;
    }

    public GraphicModel(GeometricModel body) {
        if (body != null) {
            shape = body;
        }

    }


}

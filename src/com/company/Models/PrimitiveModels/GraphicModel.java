package com.company.Models.PrimitiveModels;

import com.company.Graphic.TextureDrawer;

public abstract class GraphicModel implements Destroyable{

    protected GeometricModel shape;
    protected PhysicModel body;

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
    }

    public void drawHealth() {
        int hp=Math.round(body.getHealth()*4)+8;
        if (hp!=12) {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), hp); }
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

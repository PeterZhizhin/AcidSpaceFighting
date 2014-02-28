package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public abstract class GraphicModel implements Destroyable{

    protected GeometricModel shape;
    protected PhysicModel body;

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
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

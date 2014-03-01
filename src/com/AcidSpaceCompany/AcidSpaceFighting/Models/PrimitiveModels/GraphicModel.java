package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

public abstract class GraphicModel implements Destroyable{

    protected GeometricModel shape;
    protected PhysicModel body;
    protected boolean noNeedMore=false;

    public void setNoNeedMore() {
        noNeedMore=true;
    }

    public boolean getIsNoNeedMore() {
        return noNeedMore;
    }

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

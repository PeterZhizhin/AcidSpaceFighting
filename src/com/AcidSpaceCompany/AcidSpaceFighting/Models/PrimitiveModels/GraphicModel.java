package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public abstract class GraphicModel implements Destroyable{

    protected GeometricModel shape;
    protected PhysicModel body;
    protected boolean noNeedMore=false;
    protected Point deltaPos;

    public void setNoNeedMore() {
        noNeedMore=true;
    }

    public boolean getIsNoNeedMore() {
        return noNeedMore;
    }

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer(boolean isSelected) {
    }

    public void setPhysicModel(PhysicModel p) {
        body=p;
    }

    public void update(float dt) {
        if (deltaPos.x>Point.epsilon) deltaPos.x=Math.min(0, deltaPos.x-dt);
        else
        if (deltaPos.x<-Point.epsilon) deltaPos.x=Math.max(0, deltaPos.x+dt);

        if (deltaPos.y>Point.epsilon) deltaPos.y=Math.min(0, deltaPos.y - dt);
        else
        if (deltaPos.y<-Point.epsilon) deltaPos.y=Math.max(0, deltaPos.y+dt);

        System.out.println(deltaPos.length());
    }

    public void setDeltaPos(float x, float y) {
        deltaPos.set(deltaPos.x+x, deltaPos.y+y);
    }

    public GraphicModel(GeometricModel body) {
        if (body != null) {
            shape = body;
        }
        deltaPos=new Point(0, 0);
    }


}

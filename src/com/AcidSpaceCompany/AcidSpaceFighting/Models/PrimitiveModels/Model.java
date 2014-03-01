package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class Model{

    private GraphicModel graphic;
    private PhysicModel physic;
    private static int lastNumber=0;
    private int number;

    public boolean getIsNoNeedMore() {
        return physic.getIsNoNeedMore();
    }

    public void destroy() {
        graphic.destroy();
        physic.destroy();
    }

    public GraphicModel getGraphicModel() {
        return graphic;
    }

    public PhysicModel getPhysicModel() {
        return physic;
    }

    public void useForce(Point where, Point toWhere) {
        physic.useForce(where, toWhere);
    }

    public void update(float time) {
        physic.update(time);
    }

    public void drawTopLayer() {
        graphic.drawTopLayer();
    }

    public void drawBackgroundLayer() {
        graphic.drawBackgroundLayer();
    }

    public boolean crossThem(Model p, float deltaTime) {
        return physic.crossThem(p.physic, deltaTime);
    }

    public void updateMotion(float deltaTime) {
        physic.updateMotion(deltaTime);
    }

    public void updateStaticForces(Model p, float deltaTime) {
        physic.applyStaticForces(p.physic, deltaTime);
    }

    public Point getCenter() {
        return physic.getCentre();
    }

    public void setPhysicModel(PhysicModel p) {
        p.setNumber(number);
        physic = p;
    }

    public void setGraphicModel(GraphicModel g) {
        graphic = g;
    }

    public void doSpecialActionA() {
        physic.doSpecialActionA();
    }

    public Model(GraphicModel g, PhysicModel p) {
        graphic = g;
        number=lastNumber;
        lastNumber++;
        if (p!=null)
            p.setNumber(number);
        physic = p;
    }
}

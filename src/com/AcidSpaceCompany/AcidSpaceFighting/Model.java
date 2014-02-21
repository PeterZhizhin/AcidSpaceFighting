package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class Model{

    private GraphicModel graphic;
    private PhysicModel physic;
    private static int lastNumber=0;
    private int number;

    public void destroy() {
        graphic.destroy();
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

    public void drawHealth() {
        graphic.drawHealth();
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

    public int getNumber() {
        return number;
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

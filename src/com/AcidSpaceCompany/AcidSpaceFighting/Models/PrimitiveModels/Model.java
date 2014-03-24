package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class Model{

    private GraphicModel graphic;
    private PhysicModel physic;
    private static int lastNumber=0;
    private int number;

    private boolean isSelected=false;

    public void select() {
        isSelected=true;
    }

    public void unselect() {
        isSelected=false;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public boolean getIsComplex()
    {
        return false;
    }

    public void setAngle(float f) {
        physic.setAngle(f);
    }

    public float getAngle() {
        return physic.getAngle();
    }

    /**
     * Получает модель по точке.
     * @param point Точка.
     * @return null - если в модели точка не содержится. Или модель, которой точка принадлежит
     */
    public Model getModelUnderPoint(Point point)
    {
       if (physic.containsPoint(point))
           return this;
        else
           return null;
    }

    public boolean containsPoint(Point point)
    {
        return physic.containsPoint(point);
    }

    public void moveGeometricModel(Point dS)
    {
        physic.moveGeometric(dS);
    }

    public float getMaxWidth() {
        return physic.getMaxWidth();
    }

    public boolean getIsNoNeedMore() {
        return physic.getIsNoNeedMore();
    }

    public void setIsNoNeedMore()
    {
        physic.setIsNoNeedMore();
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
        graphic.drawTopLayer(isSelected);
    }

    public void drawConnections() {
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

package com.company;

import com.company.Geometry.Point;
import com.company.Graphic.GraphicModel;
import com.company.Physic.PhysicModel;

public class Model {

    protected GraphicModel graphic;
    protected PhysicModel physic;
    private static int lastNumber=0;
    private int number;

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

    public void drawHealthLine() {
        graphic.drawHealthLine();
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

    public Model(GraphicModel g, PhysicModel p) {
        graphic = g;
        p.setNumber(number);
        number=lastNumber;
        lastNumber++;
        physic = p;
    }
}

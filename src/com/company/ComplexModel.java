package com.company;

import com.company.Graphic.ComplexGraphicModel;
import com.company.Physic.ComplexPhysicModel;

public class ComplexModel {

    private ComplexGraphicModel graphic;
    private ComplexPhysicModel physic;

    public void draw() {
        graphic.drawTopLayer();
    }

    public void crossThem(ComplexModel p, int deltaTime) {
        physic.crossThem(p.physic, deltaTime);
    }

    public void updateMotion(int deltaTime) {
        physic.updateMotion(deltaTime);
    }

    public ComplexModel(ComplexGraphicModel g, ComplexPhysicModel p) {
        graphic=g;
        physic=p;
    }
}

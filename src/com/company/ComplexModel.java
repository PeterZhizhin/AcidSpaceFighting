package com.company;

import com.company.Graphic.ComplexGraphicModel;
import com.company.Graphic.GraphicModel;
import com.company.Physic.ComplexPhysicModel;
import com.company.Physic.PhysicModel;

public class ComplexModel {

    private ComplexGraphicModel graphic;
    private ComplexPhysicModel physic;

    public void draw() {
        graphic.draw();
    }

    public void crossThem(ComplexModel p) {
        physic.crossThem(p.physic);
    }

    public void updateMotion() {
        physic.updateMotion();
    }

    public ComplexModel(ComplexGraphicModel g, ComplexPhysicModel p) {
        graphic=g;
        physic=p;
    }
}

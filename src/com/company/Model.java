package com.company;

import com.company.Graphic.GraphicModel;
import com.company.Physic.PhysicModel;

public class Model {

    private GraphicModel graphic;
    private PhysicModel physic;

    public void draw() {
        graphic.draw();
    }

    public void crossThem(Model p) {
        physic.crossThem(p.physic);
    }

    public void updateMotion() {
        physic.updateMotion();
    }

    public Model(GraphicModel g, PhysicModel p) {
        graphic=g;
        physic=p;
    }
}

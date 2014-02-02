package com.company;

import com.company.Graphic.ComplexGraphicModel;
import com.company.Physic.ComplexPhysicModel;

public class ComplexModel extends  Model {

    public ComplexGraphicModel graModel;
    public ComplexPhysicModel phyModel;

    public void add(Model m) {
        graModel.add(m.graphic);
        phyModel.add(m.physic);
    }

    public ComplexModel() {
        super(null, null);
        graModel=new ComplexGraphicModel();
        phyModel=new ComplexPhysicModel();
        graphic=graModel;
        physic=phyModel;
    }
}

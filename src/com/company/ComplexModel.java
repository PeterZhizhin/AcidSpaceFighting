package com.company;

import com.company.Graphic.ComplexGraphicModel;
import com.company.Physic.ComplexPhysicModel;

public class ComplexModel extends Model {

    public ComplexGraphicModel graModel;
    public ComplexPhysicModel phyModel;

    public void update(float time) {
        phyModel.update(time);
    }

    public void removeGraphicModel(int num) {
         graModel.remove(num);
    }

    public void add(Model m, int num) {
        graModel.add(m.getGraphicModel());
        phyModel.add(m.getPhysicModel(), num);
    }

    public void setBase(Model m) {
        graModel.setBase(m.getGraphicModel());
        phyModel.setBase(m.getPhysicModel());
    }

    public ComplexModel(Model firstModel) {
        super(null, null);
        phyModel = new ComplexPhysicModel(this, firstModel.getPhysicModel());
        graModel = new ComplexGraphicModel(phyModel);
        graModel.setBase(firstModel.getGraphicModel());
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }
}

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
        graModel.add(m.graphic);
        phyModel.add(m.physic, num);
    }

    public void setBase(Model m) {
        graModel.setBase(m.graphic);
        phyModel.setBase(m.physic);
    }

    public ComplexModel(Model firstModel) {
        super(null, null);
        phyModel = new ComplexPhysicModel(this, firstModel.physic);
        graModel = new ComplexGraphicModel(phyModel);
        graModel.setBase(firstModel.graphic);
        graphic = graModel;
        physic = phyModel;
    }
}

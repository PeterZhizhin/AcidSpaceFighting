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

    public ComplexModel() {
        super(null, null);
        graModel = new ComplexGraphicModel();
        phyModel = new ComplexPhysicModel(this);
        graphic = graModel;
        physic = phyModel;
    }
}

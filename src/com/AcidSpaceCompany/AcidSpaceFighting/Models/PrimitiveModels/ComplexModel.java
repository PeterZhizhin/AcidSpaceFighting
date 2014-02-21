package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Model;

public class ComplexModel extends Model {

    public ComplexGraphicModel graModel;
    public ComplexPhysicModel phyModel;

    public void update(float time) {
        phyModel.update(time);
    }

    public void add(Model m, int num) {
        graModel.add(m.getGraphicModel());
        phyModel.add(m.getPhysicModel(), num);
    }

    public ComplexModel(Model firstModel) {
        super(null, null);
        phyModel = new ComplexPhysicModel(this, firstModel.getPhysicModel());
        graModel = new ComplexGraphicModel();
        graModel.setBase(firstModel.getGraphicModel());
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }
}

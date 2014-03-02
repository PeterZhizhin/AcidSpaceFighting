package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.World;

import java.util.ArrayList;

public class ComplexModel extends Model {

    public ComplexGraphicModel graModel;
    public ComplexPhysicModel phyModel;
    private ArrayList<Model> models;

    public int getSize() {
        return models.size();
    }

    public Model getModel(int num) {
        return models.get(num);
    }

    public void update(float time) {
        phyModel.update(time);
        for (int i=0; i<models.size(); i++) {
            if (models.get(i).getIsNoNeedMore())
            {
                models.get(i).destroy();
                World.explode(models.get(i).getCenter(), models.get(i).getMaxWidth());
                models.remove(i);
            }
        }
    }

    public void add(Model m, int num) {
        models.add(m);
        phyModel.add(m.getPhysicModel(), num);
    }

    public ComplexModel(Model firstModel) {
        super(null, null);
        models=new ArrayList<Model>();
        models.add(firstModel);
        phyModel = new ComplexPhysicModel(this, firstModel.getPhysicModel());
        graModel = new ComplexGraphicModel(this);
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }
}

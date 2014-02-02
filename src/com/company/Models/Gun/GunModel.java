package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class GunModel extends Model {

    public GunModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g=new GunGeometricModel(x, y, radius);
        PhysicModel p = new GunPhysicModel(g, radius);
        graphic=new GunGraphicModel(g);
        physic=p;
    }
}

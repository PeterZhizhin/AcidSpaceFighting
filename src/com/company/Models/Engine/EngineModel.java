package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Model;
import com.company.Models.Base.BaseGeometricModel;
import com.company.Models.Base.BaseGraphicModel;
import com.company.Physic.PhysicModel;

public class EngineModel extends Model {

    public EngineModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g=new EngineGeometricModel(x, y, radius);
        PhysicModel p = new EnginePhysicModel(g, radius);
        graphic=new EngineGraphicModel(g);
        physic=p;
    }
}

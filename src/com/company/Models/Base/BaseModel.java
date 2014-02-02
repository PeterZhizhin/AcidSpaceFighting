package com.company.Models.Base;

import com.company.Geometry.GeometricModel;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class BaseModel extends Model {

    public BaseModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g=new BaseGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, radius);
        graphic=new BaseGraphicModel(g);
        physic=p;
    }
}

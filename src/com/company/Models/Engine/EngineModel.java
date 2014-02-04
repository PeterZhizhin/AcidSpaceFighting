package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Base.BaseGeometricModel;
import com.company.Models.Base.BaseGraphicModel;
import com.company.Physic.PhysicModel;

public class EngineModel extends Model {

    public EngineModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g = new EngineGeometricModel(x, y, radius);
        PhysicModel p = new EnginePhysicModel(g, new Point[]{
                new Point(x + radius / 2, y + radius / 2)
        }, radius);
        graphic = new EngineGraphicModel(g);
        physic = p;
    }
}

package com.company.Models.Engine;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Model;
import com.company.Models.PrimitiveModels.PhysicModel;

public class EngineModel extends Model {

    public EngineModel(float x, float y, float radius) {
        this(x, y, radius, 0);
    }

    public EngineModel(float x, float y, float radius, float angle) {
        super(null, null);
        GeometricModel g = new EngineGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new EnginePhysicModel(g, new Point[]{
                new Point(x + radius / 2, y + radius / 2)
        }, radius);
        GraphicModel g2=new EngineGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
}

package com.company.Models.Engine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Base.BaseGeometricModel;
import com.company.Models.Base.BaseGraphicModel;
import com.company.Physic.PhysicModel;

public class EngineModel extends Model {

    EngineGraphicModel gr;

    public void setActivity(float f) {
        gr.setActivity(f);
    }

    public EngineModel(float x, float y, float radius) {
        this(x, y, radius, 0);
    }

    public EngineModel(float x, float y, float radius, float angle) {
        super(null, null);
        GeometricModel g = new EngineGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new EnginePhysicModel(this, g, new Point[]{
                new Point(x + radius / 2, y + radius / 2)
        }, radius);
        gr = new EngineGraphicModel(g);
        graphic=gr;
        physic = p;
    }
}

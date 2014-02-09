package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Base.BaseGeometricModel;
import com.company.Physic.PhysicModel;

public class DynamiteModel extends Model {

    public DynamiteModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g = new BaseGeometricModel(x, y, radius);
        PhysicModel p = new DynamitePhysicModel(g, new Point[]{
                new Point(x + radius / 2, y),
                new Point(x + radius / 2, y + radius),
                new Point(x, y + radius / 2),
                new Point(x + radius, y + radius / 2)
        }, radius);
        graphic = new DynamiteGraphicModel(g);
        physic = p;
        graphic.setPhysicModel(p);
    }
}

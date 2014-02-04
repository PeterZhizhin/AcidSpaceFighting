package com.company.Models.Base;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class BaseModel extends Model {

    public BaseModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g = new BaseGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, new Point[]{
                new Point(x + radius / 2, y),
                new Point(x + radius / 2, y + radius),
                new Point(x, y + radius / 2),
                new Point(x + radius, y + radius / 2)
        }, radius);
        graphic = new BaseGraphicModel(g);
        physic = p;
    }
}

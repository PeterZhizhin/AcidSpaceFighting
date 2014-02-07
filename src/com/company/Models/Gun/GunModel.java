package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class GunModel extends Model {

    public GunModel(float x, float y, float radius, float angle) {
        super(null, null);
        GeometricModel g = new GunGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new GunPhysicModel(g, new Point[]{new Point(x + radius / 2, y + radius / 2)}, radius);
        graphic = new GunGraphicModel(g);
        physic = p;
        graphic.setPhysicModel(p);
    }

    public GunModel(float x, float y, float radius) {
        this(x, y, radius, 0f);
    }
}

package com.company.Models.Bullet;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class BulletModel extends Model {

    public BulletModel(float x, float y, float radius, float mass) {
        super(null, null);
        GeometricModel g = new BulletGeometricModel(x, y, radius);
        PhysicModel p = new BulletPhysicModel(g, new Point[]{}, mass);
        graphic = new BulletGraphicModel(g);
        physic = p;
        graphic.setPhysicModel(p);
    }
}

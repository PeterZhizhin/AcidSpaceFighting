package com.company.Models.Bullet;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.GraphicModel;
import com.company.Model;
import com.company.Models.Base.BaseGraphicModel;
import com.company.Physic.PhysicModel;

public class BulletModel extends Model {

    public BulletModel(float x, float y, float radius, float mass) {
        super(null, null);
        GeometricModel g = new BulletGeometricModel(x, y, radius);
        PhysicModel p = new BulletPhysicModel(g, new Point[]{}, mass);
        GraphicModel g2=new BaseGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
}

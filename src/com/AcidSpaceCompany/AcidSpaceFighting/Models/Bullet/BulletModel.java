package com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class BulletModel extends Model {


    public int getType() {
        return 1;
    }

    public BulletModel(float x, float y, float radius) {
        super(null, null, radius);
        GeometricModel g = new BulletGeometricModel(x, y, radius);
        PhysicModel p = new BulletPhysicModel(g, new Point[]{}, radius*radius);
        GraphicModel g2=new BulletGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }


    public BulletModel(float x, float y, float radius, float angle, float speedX, float speedY, float speedW)
    {
        this(x, y, radius);
        setAngle(angle);
        setSpeeds(speedX, speedY, speedW);
    }
}

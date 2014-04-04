package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class GunModel extends Model {

    public int getType() {
        return 4;
    }

    public GunModel(float x, float y, float radius, float angle) {
        super(null, null, radius);
        GeometricModel g = new GunGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new GunPhysicModel(g, new Point[]{new Point(x + radius / 2, y + radius / 2)}, radius*radius);
        GraphicModel g2=new GunGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }

    public GunModel(float x, float y, float radius) {
        this(x, y, radius, 0f);
    }


    public GunModel(float x, float y, float radius, float angle, float speedX, float speedY, float speedW)
    {
        this(x, y, radius);
        setAngle(angle);
        setSpeeds(speedX, speedY, speedW);
    }
}

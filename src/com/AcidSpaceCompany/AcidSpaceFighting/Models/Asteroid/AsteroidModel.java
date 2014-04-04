package com.AcidSpaceCompany.AcidSpaceFighting.Models.Asteroid;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class AsteroidModel extends Model {

    public int getType() {
        return 0;
    }

    public AsteroidModel(float x, float y, float radius) {
       super(null, null, radius);
        GeometricModel g = new AsteroidGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, new Point[]{}, radius*radius);
        GraphicModel g2=new AsteroidGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }

    public AsteroidModel(float x, float y, float radius, float angle, float speedX, float speedY, float speedW)
    {
        this(x, y, radius);
        setAngle(angle);
        setSpeeds(speedX, speedY, speedW);
    }
}

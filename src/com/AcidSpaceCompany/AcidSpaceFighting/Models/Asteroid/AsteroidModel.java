package com.AcidSpaceCompany.AcidSpaceFighting.Models.Asteroid;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class AsteroidModel extends Model {

    public AsteroidModel(float x, float y, float radius, float mass) {
       super(null, null);
        GeometricModel g = new AsteroidGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, new Point[]{}, mass);
        GraphicModel g2=new AsteroidGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
    public AsteroidModel(float x, float y, float radius, float mass, Point speed)
    {
        super(null, null);
        GeometricModel g = new AsteroidGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, new Point[]{}, mass, speed);
        GraphicModel g2=new AsteroidGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
}

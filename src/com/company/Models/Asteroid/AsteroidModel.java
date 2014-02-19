package com.company.Models.Asteroid;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Model;
import com.company.Models.PrimitiveModels.PhysicModel;

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

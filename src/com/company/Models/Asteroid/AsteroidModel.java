package com.company.Models.Asteroid;

import com.company.Geometry.GeometricModel;
import com.company.Model;
import com.company.Physic.PhysicModel;

public class AsteroidModel extends Model {

    public AsteroidModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g=new AsteroidGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, radius);
        graphic=new AsteroidGraphicModel(g);
        physic=p;
    }
}

package com.company.Models.GunEngine;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Physic.PhysicModel;
import com.company.World;

public class GunPhysicModel extends PhysicModel{

    public void doSpecialActionA(float deltaTime) {
        GeometricModel g=new AsteroidGeometricModel(getCentre().getX(), getCentre().getY(), 100f);
        PhysicModel p = new PhysicModel(g, 1000f);
        Model m=new Model(new AsteroidGraphicModel(g), p);
        g.move(new Point(300f, 300f));
        World.addModel(m);
    }

    public GunPhysicModel(GeometricModel body, float mass) {
        super(body, mass);
    }

}

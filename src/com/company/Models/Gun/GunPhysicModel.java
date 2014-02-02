package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Physic.PhysicModel;
import com.company.World;

public class GunPhysicModel extends PhysicModel{

    public void doSpecialActionA(float deltaTime) {
        World.addModel(new AsteroidModel(getCentre().getX()+300f, getCentre().getY()+300f, 100f));
    }

    public GunPhysicModel(GeometricModel body, float mass) {
        super(body, mass);
    }

}

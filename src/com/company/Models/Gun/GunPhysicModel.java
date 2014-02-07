package com.company.Models.Gun;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Physic.PhysicModel;
import com.company.World;

public class GunPhysicModel extends PhysicModel {

    private static final float timeLimit=1f;

    public void doSpecialActionA(float deltaTime) {
        if (activity<=0) {
        World.addModel(new AsteroidModel(getCentre().getX() + 300f, getCentre().getY() + 300f, 100f, 100f));
            activity=timeLimit;
        }
    }

    public void update(float time) {
        if (activity>0)
            activity-=time;
    }

    public GunPhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

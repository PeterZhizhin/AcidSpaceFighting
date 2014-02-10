package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Effects.Explosion;
import com.company.Model;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Physic.PhysicModel;
import com.company.World;

public class DynamitePhysicModel extends PhysicModel {

    public void doSpecialActionA(float deltaTime) {
        health=0;
        Explosion a=new Explosion(getCentre(), 100);
        World.addEffect(a);
    }

    public DynamitePhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

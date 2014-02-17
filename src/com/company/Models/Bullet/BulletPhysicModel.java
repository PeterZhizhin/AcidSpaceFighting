package com.company.Models.Bullet;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Effects.Explosion;
import com.company.Physic.PhysicModel;
import com.company.World;

public class BulletPhysicModel extends PhysicModel {

    private boolean firstTime;

    public void update(float time) {
        health-= time*0.1f;
    }




    public void useForce(Point posOfForce, Point force) {
        if (force.length()>=100000000)  {
            firstTime=!firstTime;
            if (firstTime) {
                 health=0;
                 World.explode(getCentre(), body.getMaxLength());
            }
        }
        super.useForce(posOfForce, force);
    }

    public BulletPhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
        firstTime=true;
    }

}

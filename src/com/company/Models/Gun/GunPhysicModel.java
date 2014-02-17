package com.company.Models.Gun;

import com.company.Audio.Sound;
import com.company.Audio.SoundBase;
import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Effects.Explosion;
import com.company.Model;
import com.company.Models.Bullet.BulletModel;
import com.company.Physic.PhysicModel;
import com.company.World;

import static com.company.World.addEffect;

public class GunPhysicModel extends PhysicModel {

    private static final float timeLimit=1f;

    public void doSpecialActionA() {
        if (activity<=0) {

            Point force=body.getPoint(2).add(getCentre().negate()).setLength(body.getMaxLength());

            Model m=new BulletModel(getCentre().getX()+force.x, getCentre().getY() + force.y, 100f, 100f);

            force=force.setLength(100000000);
            m.useForce(m.getCenter(), force);
            World.addModel(m);



            useForce(getCentre(), force.multiply(-1));
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

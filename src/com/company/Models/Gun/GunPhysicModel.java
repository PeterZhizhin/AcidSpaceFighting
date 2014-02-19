package com.company.Models.Gun;

import com.company.Audio.SoundBase;
import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Model;
import com.company.Models.Bullet.BulletModel;
import com.company.Models.PrimitiveModels.PhysicModel;
import com.company.World;

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
            SoundBase.playGun();
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

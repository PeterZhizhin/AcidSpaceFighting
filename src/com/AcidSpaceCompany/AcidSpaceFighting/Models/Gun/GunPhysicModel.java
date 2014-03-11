package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet.BulletModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class GunPhysicModel extends PhysicModel {

    private static final float timeLimit=1f;

    public void doSpecialActionA() {
        if (activity<=0) {

            Point force=body.getPoint(1).add(body.getPoint(2)).add(getCentre().negate()).setLength(body.getMaxLength());

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

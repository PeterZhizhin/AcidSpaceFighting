package com.company.Models.Connector;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Physic.PhysicModel;
import com.company.World;

public class ConnectorPhysicModel extends PhysicModel {


    public void doSpecialActionA() {
        /*health=0;
        if (inFirstTime) {
        World.explode(getCentre(), body.getMaxLength());
            inFirstTime=false;
        }*/
    }

    public ConnectorPhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

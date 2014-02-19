package com.company.Models.Connector;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Models.PrimitiveModels.PhysicModel;

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

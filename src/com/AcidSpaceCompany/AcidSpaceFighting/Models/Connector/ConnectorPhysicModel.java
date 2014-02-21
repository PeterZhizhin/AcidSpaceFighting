package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

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

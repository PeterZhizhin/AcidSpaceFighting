package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class ConnectorPhysicModel extends PhysicModel {

    public void doSpecialAction() {
        health=0;
    }

    public ConnectorPhysicModel(GeometricModel body, Point[] conns, float mass) {
        super(body, conns, mass);
    }

}

package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.Base.BaseGeometricModel;

public class ConnectorGeometricModel extends BaseGeometricModel {

    public float getConnectionDistanceCoef() {
        return 5f;
    }

    public ConnectorGeometricModel(float x, float y, float r) {
        super(x, y, r);
    }


}

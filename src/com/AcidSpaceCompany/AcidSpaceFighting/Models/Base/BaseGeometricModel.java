package com.AcidSpaceCompany.AcidSpaceFighting.Models.Base;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class BaseGeometricModel extends GeometricModel {

    public float getConnectionDistanceCoef() {
        return 4f;
    }

    private static Point[] getVertexes(float x, float y, float w) {
        Point[] p = new Point[4];
        p[0] = new Point(x, y);
        p[1] = new Point(x + w, y);
        p[2] = new Point(x + w, y + w);
        p[3] = new Point(x, y + w);
        return p;
    }

    public BaseGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

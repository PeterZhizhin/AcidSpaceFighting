package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class GunGeometricModel extends GeometricModel {

    public float getConnectionDistanceCoef() {
        return 2f;
    }

    public void rotate(float f) {
        super.rotate(f);
    }

    private static Point[] getVertexes(float x, float y, float w) {
        Point[] p = new Point[4];

        p[0] = new Point(x, y + w);

        p[1] = new Point(x, y );

        p[2] = new Point(x + w, y);

        p[3] = new Point(x + w , y + w);

        return p;
    }

    public GunGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

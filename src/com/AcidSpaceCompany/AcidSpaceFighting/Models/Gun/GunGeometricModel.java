package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class GunGeometricModel extends GeometricModel {

    public void rotate(float f) {
        super.rotate(f);
    }

    private static Point[] getVertexes(float x, float y, float w) {
        Point[] p = new Point[5];
        float wPer8 = w / 8;
        float wPer4 = w / 4;
        float wPer2 = w / 2;

        p[0] = new Point(x, y + w);

        p[1] = new Point(x + wPer8, y + w - wPer4);

        p[2] = new Point(x + wPer2, y + wPer2);

        p[3] = new Point(x + w - wPer8, y + w - wPer4);

        p[4] = new Point(x + w, y + w);

        return p;
    }

    public GunGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

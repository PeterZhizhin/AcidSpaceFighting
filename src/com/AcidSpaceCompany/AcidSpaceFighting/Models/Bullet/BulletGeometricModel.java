package com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class BulletGeometricModel extends GeometricModel {
    private static final double PI8 = Math.PI / 4;

    private static Point[] getVertexes(float x, float y, float r) {
        Point[] p = new Point[8];

        for (int i = 0; i < 8; i++) {
            p[i] = new Point((float) (x + r * Math.cos(PI8 * i)), (float) (y + r * Math.sin(PI8 * i)));
        }
        return p;
    }

    public BulletGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

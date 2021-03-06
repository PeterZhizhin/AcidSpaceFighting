package com.AcidSpaceCompany.AcidSpaceFighting.Models.Asteroid;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class AsteroidGeometricModel extends GeometricModel {
    private static final double PI8 = Math.PI / 8;

    public float getConnectionDistanceCoef() {
        return 0f;
    }

    private static Point[] getVertexes(float x, float y, float r) {
        Point[] p = new Point[16];

        for (int i = 0; i < 16; i++) {
            p[i] = new Point((float) (x + r * Math.cos(PI8 * i)), (float) (y + r * Math.sin(PI8 * i)));
        }
        return p;
    }

    public AsteroidGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

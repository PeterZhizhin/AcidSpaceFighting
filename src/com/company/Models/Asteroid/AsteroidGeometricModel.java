package com.company.Models.Asteroid;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Geometry.Segment;

import static com.company.Geometry.Point.getAngle;

public class AsteroidGeometricModel extends GeometricModel {
    private static final double PI8 = Math.PI / 8;

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

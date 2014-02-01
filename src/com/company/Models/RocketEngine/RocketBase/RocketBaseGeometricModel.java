package com.company.Models.RocketEngine.RocketBase;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;

public class RocketBaseGeometricModel extends GeometricModel {

    private static Point[] getVertexes(float x, float y, float w) {
        Point[] p=new Point[4];
        p[0]=new Point(x, y);
        p[1]=new Point(x+w, y);
        p[2]=new Point(x+w, y+w);
        p[3]=new Point(x, y+w);
        return p;
    }

    public RocketBaseGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}
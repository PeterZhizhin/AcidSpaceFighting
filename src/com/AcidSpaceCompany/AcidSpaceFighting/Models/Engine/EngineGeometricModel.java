package com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class EngineGeometricModel extends GeometricModel {

    public void rotate(float f) {
        super.rotate(f);
    }

    private static Point[] getVertexes(float x, float y, float w) {
        Point[] p = new Point[4];

        /*p[0] = new Point(x, y);
        p[1] = new Point(x + w / 2, y);
        p[2] = new Point(x + w, y + w / 2);
        p[3] = new Point(x + w / 2, y + w);
        p[4] = new Point(x, y + w); */

        p[0] = new Point(x, y);
        p[1] = new Point(x + w, y);
        p[2] = new Point(x + w, y + w);
        p[3] = new Point(x, y + w);


        return p;
    }

    public EngineGeometricModel(float x, float y, float r) {
        super(getVertexes(x, y, r));
    }


}

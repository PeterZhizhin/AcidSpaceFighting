package com.company.Models.Gun;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Model;
import com.company.Models.PrimitiveModels.PhysicModel;

public class GunModel extends Model {

    public GunModel(float x, float y, float radius, float angle) {
        super(null, null);
        GeometricModel g = new GunGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new GunPhysicModel(g, new Point[]{new Point(x + radius / 2, y + radius / 2)}, radius);
        GraphicModel g2=new GunGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }

    public GunModel(float x, float y, float radius) {
        this(x, y, radius, 0f);
    }
}

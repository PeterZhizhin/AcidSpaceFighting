package com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class EngineModel extends Model {

    public EngineModel(float x, float y, float radius) {
        this(x, y, radius, 0);
    }

    public EngineModel(float x, float y, float radius, float angle) {
        super(null, null);
        GeometricModel g = new EngineGeometricModel(x, y, radius);
        g.rotate(angle);
        PhysicModel p = new EnginePhysicModel(g, new Point[]{
                new Point(x + radius / 2, y + radius / 2)
        }, radius);
        GraphicModel g2=new EngineGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
}

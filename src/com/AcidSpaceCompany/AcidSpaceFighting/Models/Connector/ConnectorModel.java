package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Base.BaseGeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class ConnectorModel extends Model {

    public int getType() {
        return 3;
    }

    public ConnectorModel(float x, float y, float radius) {
        super(null, null, radius);
        GeometricModel g = new ConnectorGeometricModel(x, y, radius);
        PhysicModel p = new ConnectorPhysicModel(g, new Point[]{
                new Point(x + radius / 2, y),
                new Point(x + radius / 2, y + radius),
                new Point(x, y + radius / 2),
                new Point(x + radius, y + radius / 2)
        }, radius*radius);
        GraphicModel g2=new ConnectorGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }

    public ConnectorModel(float x, float y, float radius, float angle, float speedX, float speedY, float speedW)
    {
        this(x, y, radius);
        setAngle(angle);
        setSpeeds(speedX, speedY, speedW);
    }
}

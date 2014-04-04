package com.AcidSpaceCompany.AcidSpaceFighting.Models.Base;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.PhysicModel;

public class BaseModel extends Model {

    public int getType() {
        return 2;
    }

    public BaseModel(float x, float y, float radius) {
        super(null, null, radius);
        GeometricModel g = new BaseGeometricModel(x, y, radius);
        PhysicModel p = new PhysicModel(g, new Point[]{
                new Point(x + radius / 2, y),
                new Point(x + radius / 2, y + radius),
                new Point(x, y + radius / 2),
                new Point(x + radius, y + radius / 2)
        }, radius*radius);
        GraphicModel g2=new BaseGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }


    public BaseModel(float x, float y, float radius, float angle, float speedX, float speedY, float speedW)
    {
        this(x, y, radius);
        setAngle(angle);
        setSpeeds(speedX, speedY, speedW);
    }
}

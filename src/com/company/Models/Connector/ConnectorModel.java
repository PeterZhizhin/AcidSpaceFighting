package com.company.Models.Connector;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.GraphicModel;
import com.company.Model;
import com.company.Models.Base.BaseGeometricModel;
import com.company.Physic.PhysicModel;

public class ConnectorModel extends Model {

    public ConnectorModel(float x, float y, float radius) {
        super(null, null);
        GeometricModel g = new BaseGeometricModel(x, y, radius);
        PhysicModel p = new ConnectorPhysicModel(g, new Point[]{
                new Point(x + radius / 2, y),
                new Point(x + radius / 2, y + radius),
                new Point(x, y + radius / 2),
                new Point(x + radius, y + radius / 2)
        }, radius);
        GraphicModel g2=new ConnectorGraphicModel(g);
        setGraphicModel(g2);
        setPhysicModel(p);
        g2.setPhysicModel(p);
    }
}

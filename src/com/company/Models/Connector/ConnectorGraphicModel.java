package com.company.Models.Connector;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.TextureDrawer;

public class ConnectorGraphicModel extends GraphicModel {

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), 3);
    }

    public ConnectorGraphicModel(GeometricModel body) {
        super(body);
    }

}
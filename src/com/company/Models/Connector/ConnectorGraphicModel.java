package com.company.Models.Connector;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Graphic.TextureDrawer;

public class ConnectorGraphicModel extends GraphicModel {

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), 3);
    }

    public ConnectorGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

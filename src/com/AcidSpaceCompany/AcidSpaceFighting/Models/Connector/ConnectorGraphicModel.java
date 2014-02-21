package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

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
package com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class ConnectorGraphicModel extends GraphicModel {

    public void drawTopLayer(boolean isSelected) {
        TextureDrawer.drawBlock(shape.getPoint(0).add(deltaPos), shape.getPoint(1).add(deltaPos),
                shape.getPoint(2).add(deltaPos), shape.getPoint(3).add(deltaPos), 2, body.getHealth(), isSelected);
    }

    public ConnectorGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

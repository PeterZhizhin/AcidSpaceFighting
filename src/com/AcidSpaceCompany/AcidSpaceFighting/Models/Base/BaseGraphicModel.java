package com.AcidSpaceCompany.AcidSpaceFighting.Models.Base;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class BaseGraphicModel extends GraphicModel {

    public void drawTopLayer(boolean isSelected) {
        TextureDrawer.drawBlock
                (shape.getPoint(0).add(deltaPos),  shape.getPoint(1).add(deltaPos),
                        shape.getPoint(2).add(deltaPos), shape.getPoint(3).add(deltaPos), 3, body.getHealth(), isSelected);
    }

    public BaseGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class GunGraphicModel extends GraphicModel {

    public void drawTopLayer(boolean isSelected) {
        TextureDrawer.drawBlock(shape.getPoint(0).add(deltaPos), shape.getPoint(1).add(deltaPos),
                shape.getPoint(2).add(deltaPos), shape.getPoint(3).add(deltaPos), 4, body.getHealth(), isSelected);
    }

    public GunGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

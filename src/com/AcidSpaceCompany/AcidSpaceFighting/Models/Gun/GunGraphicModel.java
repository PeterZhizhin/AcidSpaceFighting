package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class GunGraphicModel extends GraphicModel {

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(3), shape.getPoint(4), 5);
    }

    public GunGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

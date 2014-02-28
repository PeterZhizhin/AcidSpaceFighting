package com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class GunGraphicModel extends GraphicModel {

    public void drawTopLayer() {
        TextureDrawer.drawBlock(shape.getPoint(0), shape.getPoint(1),
                shape.getPoint(2), shape.getPoint(3), 4, body.getHealth());
    }

    public GunGraphicModel(GeometricModel body) {
        super(body);
    }

    @Override
    public void destroy() {

    }
}

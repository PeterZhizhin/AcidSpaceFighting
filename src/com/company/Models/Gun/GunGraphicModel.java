package com.company.Models.Gun;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Graphic.TextureDrawer;

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

package com.company.Models.Base;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.TextureDrawer;

public class BaseGraphicModel extends GraphicModel {

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0),  shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), 2);
    }

    public BaseGraphicModel(GeometricModel body) {
        super(body);
    }

}

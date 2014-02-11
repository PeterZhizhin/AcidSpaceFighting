package com.company.Models.Dynamite;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.TextureDrawer;

public class DynamiteGraphicModel extends GraphicModel {

    public void drawBackgroundLayer() {
    }

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), 2);
    }

    public DynamiteGraphicModel(GeometricModel body) {
        super(body);
    }

}

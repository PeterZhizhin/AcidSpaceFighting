package com.company.Models.Bullet;

import com.company.Geometry.GeometricModel;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Tale;

public class BulletGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength()/2);
        t.draw();
    }

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(3), shape.getPoint(4), 4);
    }

    public BulletGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(new Color(1f, 1f, 0.6f), new Color(1f, 1f, 0.6f, 0f), 0, 20, 3, 0, false);
    }

}

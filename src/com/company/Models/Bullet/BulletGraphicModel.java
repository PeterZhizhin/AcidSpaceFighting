package com.company.Models.Bullet;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Graphic.Controls.Color;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Effects.Tale;
import com.company.World;

public class BulletGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength());
    }

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(3), shape.getPoint(4), 4);
    }

    public BulletGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(new Color(1f, 1f, 0f), new Color(1f, 0f, 0f), 0, 20, 5, 4, true);
        World.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }
}

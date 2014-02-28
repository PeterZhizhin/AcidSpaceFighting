package com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls.Color;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Tale;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class BulletGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength());
    }

    public void drawTopLayer() {
        TextureDrawer.drawBlock(shape.getPoint(0), shape.getPoint(2), shape.getPoint(4), shape.getPoint(6), 5, body.getHealth());
    }

    public BulletGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(0, 20, 5, 4, true);
        World.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }
}

package com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Tale;
import com.AcidSpaceCompany.AcidSpaceFighting.Worlds.OurWorld;

public class BulletGraphicModel extends GraphicModel {

    private Tale t;

    public void drawTopLayer(boolean isSelected) {
        t.addPoint(shape.getCentre(), shape.getMaxLength());
        TextureDrawer.drawBlock(
                shape.getPoint(0).add(deltaPos), shape.getPoint(2).add(deltaPos),
                shape.getPoint(4).add(deltaPos), shape.getPoint(6).add(deltaPos), 5, body.getHealth(), isSelected);
    }

    public BulletGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(body.getCentre(), 0, 20, 0.05f, 4, false);
        OurWorld.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }
}

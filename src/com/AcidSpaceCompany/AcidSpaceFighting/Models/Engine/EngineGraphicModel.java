package com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Tale;
import com.AcidSpaceCompany.AcidSpaceFighting.Worlds.OurWorld;

public class EngineGraphicModel extends GraphicModel {

    private Tale t;

    public void drawTopLayer(boolean isSelected) {
        float activity = body.getActivity();
        t.addPoint(shape.getCentre(),//.add(new Point(Math.cos(shape.getAngle()), Math.sin(shape.getAngle())).multiply(shape.getMaxLength()* activity *2).negate()),
                shape.getMaxLength()* activity /2);
        TextureDrawer.drawBlock(shape.getPoint(0).add(deltaPos), shape.getPoint(1).add(deltaPos),
                shape.getPoint(2).add(deltaPos), shape.getPoint(3).add(deltaPos), 1, body.getHealth(), isSelected);
    }

    public EngineGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(body.getCentre(),20, 20, 0.05f, 20, true);
        OurWorld.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }

}

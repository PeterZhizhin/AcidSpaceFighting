package com.company.Models.Engine;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Effects.Tale;
import com.company.World;

public class EngineGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        float activity = body.getActivity();
         t.addPoint(shape.getCentre().add(new Point(Math.cos(shape.getAngle()), Math.sin(shape.getAngle())).multiply(shape.getMaxLength()* activity *2).negate()),
                 shape.getMaxLength()* activity /2);
        t.draw();
    }

    public void drawTopLayer() {
        TextureDrawer.drawQuad(shape.getPoint(0), shape.getPoint(1), shape.getPoint(2), shape.getPoint(3), 1);
    }

    public EngineGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(new Color(1f, 1f, 0f), new Color(1f, 0f, 0f), 20, 20, 5, 16, true);
        World.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }

}

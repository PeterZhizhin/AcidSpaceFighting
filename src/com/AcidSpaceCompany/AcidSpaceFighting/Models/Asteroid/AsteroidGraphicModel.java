package com.AcidSpaceCompany.AcidSpaceFighting.Models.Asteroid;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GraphicModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Tale;
import com.AcidSpaceCompany.AcidSpaceFighting.Worlds.OurWorld;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class AsteroidGraphicModel extends GraphicModel {

    private Tale t;

    public void drawTopLayer(boolean isSelected) {
        t.addPoint(shape.getCentre(), shape.getMaxLength() / 2);
        glEnd();
        glBegin(GL_TRIANGLE_FAN);

        glTexCoord2f(0.875f, 0.125f);
        Camera.translatePoint(shape.getCentre());

        float dAngle= (float) (Math.PI*2/shape.getPointCount());
        float angle= (float) (-Math.PI/4);
        for (int i=0; i<shape.getPointCount(); i++)
        {

            glTexCoord2d(0.875f+0.125*Math.cos(angle), 0.125f+0.125*Math.sin(angle));

            Camera.translatePoint(shape.getPoint(i));
            angle+=dAngle;
        }

        glTexCoord2d(0.875f+0.125*Math.cos(angle), 0.125f+0.125*Math.sin(angle));

        Camera.translatePoint(shape.getPoint(0));
        glEnd();
        glBegin(GL_QUADS);
    }

    public AsteroidGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(body.getCentre(),0, 20, 0.05f, 8, false);
        OurWorld.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }
}

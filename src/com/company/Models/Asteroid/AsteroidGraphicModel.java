package com.company.Models.Asteroid;

import com.company.Models.PrimitiveModels.GeometricModel;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Models.PrimitiveModels.GraphicModel;
import com.company.Graphic.Effects.Tale;
import com.company.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class AsteroidGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength() / 2);
    }

    public void drawTopLayer() {
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
        t=new Tale(new Color(1f, 1f, 0.6f), new Color(1f, 1f, 0.6f, 0f), 0, 20, 3, 8, false);
        World.addEffect(t);
    }

    public void destroy() {
        t.destroy();
    }
}

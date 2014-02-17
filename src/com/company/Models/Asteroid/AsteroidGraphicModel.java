package com.company.Models.Asteroid;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.GraphicModel;
import com.company.Graphic.Tale;
import com.company.Graphic.TextureDrawer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class AsteroidGraphicModel extends GraphicModel {

    private Tale t;

    public void drawBackgroundLayer() {
        t.addPoint(shape.getCentre(), shape.getMaxLength()/2);
        t.draw();
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

            if (angle<=Math.PI/4)
                glTexCoord2f(1, 0.25f-(float) (0.25f*Math.tan(angle)));
            else
            if (angle<=3*Math.PI/4)
                glTexCoord2f(1f-(float) (0.25f*Math.tan(angle)), 0f);
            else
            if (angle<=5*Math.PI/4)
                glTexCoord2f(0.75f, (float) (0.25f*Math.tan(angle)));
            else
            if (angle<=7*Math.PI/4)
                glTexCoord2f(0.75f+(float) (0.25f*Math.tan(angle)), 0.25f);

            Camera.translatePoint(shape.getPoint(i));
            angle+=dAngle;
        }
        glEnd();
        glBegin(GL_QUADS);
    }

    public AsteroidGraphicModel(GeometricModel body) {
        super(body);
        t=new Tale(new Color(1f, 1f, 0.6f), new Color(1f, 1f, 0.6f, 0f), 0, 20, 3, 8, false);
    }

}

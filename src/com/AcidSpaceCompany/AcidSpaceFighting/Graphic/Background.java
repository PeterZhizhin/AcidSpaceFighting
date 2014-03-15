package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.World;
import org.lwjgl.opengl.Display;

import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.*;
import static org.lwjgl.opengl.GL11.glColor3f;

public class Background {

    public static void draw() {
        glColor3f(1f, 1f, 1f);

        TextureDrawer.drawBackground(!World.getPhysicActivity());

        ShadersBase.use(ShadersBase.defaultShader);
        if (World.getPhysicActivity()) {
        float width=Display.getWidth();

        Point p1=Camera.getSourcePoint(new Point(0, 0));
        Point p2=Camera.getSourcePoint(new Point(Display.getWidth(), Display.getHeight()));

        while (Camera.translateDistance(width)<=500) {
            width*=2;
        }

        int startX= (int) (p1.x/width)-1;
        startX*=width;

        int startY= (int) (p1.y/width)-1;
        startY*=width;

        int startX2= (int) (p2.x/width)+1;
        startX2*=width;

        int startY2= (int) (p2.y/width)+1;
        startY2*=width;

        startDrawNoise();
        for (int i=startX; i<startX2; i+=width) {
            for (int j=startY; j<startY2; j+=width) {
                drawQuad(new Point(i, j), new Point(i + width, j),
                        new Point(i + width, j + width), new Point(i, j + width));
            }
        }
        }
        finishDraw();

    }

}

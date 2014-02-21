package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls.Color;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls.FontDrawer;
import org.lwjgl.opengl.Display;

import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.drawQuad;
import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.finishDraw;
import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.startDrawNoise;
import static org.lwjgl.opengl.GL11.*;

public class Background {

    public static void draw() {
        glEnable(GL_TEXTURE_2D);
        TextureDrawer.drawBackground();

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
        finishDraw();

        glEnable(GL_TEXTURE_2D);

        FontDrawer.drawString(10, 10,
                Camera.getSourcePoint(new Point(10, 10)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(Display.getWidth()-150, Display.getHeight()-50,
                Camera.getSourcePoint(new Point(Display.getWidth()-150, Display.getHeight()-50)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(10, Display.getHeight()-50,
                Camera.getSourcePoint(new Point(10, Display.getHeight()-50)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(Display.getWidth()-150, 10,
                Camera.getSourcePoint(new Point(Display.getWidth()-150, 10)).toString(),
                new Color(1f, 1f, 1f), false);

        glDisable(GL_TEXTURE_2D);
    }

}

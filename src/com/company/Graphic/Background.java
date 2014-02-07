package com.company.Graphic;

import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Controls.FontDrawer;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Background {

    public static void draw() {
        glColor3f(0.1f, 0.1f, 0.1f);
        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(Display.getWidth(), 0);
        glVertex2f(Display.getWidth(), Display.getHeight());
        glVertex2f(0, Display.getHeight());
        glEnd();

        glColor3f(1, 1, 1);
        glBegin(GL_LINES);
        Camera.translatePoint(-100, 0);
        Camera.translatePoint(100, 0);
        Camera.translatePoint(0, -100);
        Camera.translatePoint(0, 100);
        glEnd();

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

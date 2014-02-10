package com.company.Graphic;

import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Controls.FontDrawer;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Background {

    public static void draw() {


        glColor3f(0.2f, 0.2f, 0.2f);
        glBegin(GL_QUADS);
        glVertex2f(0, Display.getHeight());
        glVertex2f(Display.getWidth(), Display.getHeight());
        glVertex2f(Display.getWidth(), 0);
        glVertex2f(0, 0);
        glEnd();

        Point p1=Camera.getSourcePoint(new Point(0, 0));
        Point p2=Camera.getSourcePoint(new Point(Display.getWidth(), Display.getHeight()));

        float width=Display.getWidth();
        float height=Display.getHeight();
        float width2=width/2;
        float height2=height/2;

        int startX= (int) (p1.x/width)-1;
        startX*=width;

        int startY= (int) (p1.y/height)-1;
        startY*=height;

        int startX2= (int) (p2.x/width)+1;
        startX2*=width;

        int startY2= (int) (p2.y/height)+1;
        startY2*=height;

        glBegin(GL_QUADS);
        for (int i=startX; i<startX2; i+=width) {
            for (int j=startY; j<startY2; j+=height) {

                 glColor3f(0.3f, 0.3f, 0.3f);

                Camera.translatePoint(i, j);
                Camera.translatePoint(i + width2, j);
                Camera.translatePoint(i + width2, j + height2);
                Camera.translatePoint(i, j + height2);


                Camera.translatePoint(i+width2, j+ height2);
                Camera.translatePoint(i + width, j+ height2);
                Camera.translatePoint(i + width, j + height);
                Camera.translatePoint(i+width2, j + height);

            }
        }
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

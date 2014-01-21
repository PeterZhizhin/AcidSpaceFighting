package com.company.Graphic;

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
    }

}

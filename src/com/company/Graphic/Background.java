package com.company.Graphic;

import static org.lwjgl.opengl.GL11.*;

public class Background {

    public static void draw() {
        glColor3f(1, 1, 1);
        glBegin(GL_LINES);
            Camera.translatePoint(-100, 0);
            Camera.translatePoint(100, 0);
            Camera.translatePoint(0, -100);
            Camera.translatePoint(0, 100);
        glEnd();
    }

}

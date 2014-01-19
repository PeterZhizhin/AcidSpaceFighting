package com.company.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glOrtho;

public class GLHelper {
    public static void initGL(int width, int heigth)
    {
        glClearColor(0f, 0f, 0f, 1f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(2f);
        glOrtho(0, width, heigth, 0, 1, 0);
    }
}

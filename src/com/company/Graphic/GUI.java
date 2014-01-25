package com.company.Graphic;

import com.company.Graphic.Controls.Color;
import com.company.Graphic.Controls.FontDrawer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class GUI {

    private static boolean lastStateButtonIsPressed=false;
    private static int mouseDownX=0;
    private static int mouseDownY=0;

    public static void init() {
        FontDrawer.init();
    }

    public static void update() {

        boolean isPressed= Mouse.isButtonDown(0);
        if (isPressed) {
            int nowX=Mouse.getX();
            int nowY=Display.getHeight()-Mouse.getY();
              if (lastStateButtonIsPressed) {

                 Camera.move(mouseDownX-nowX, mouseDownY-nowY);
              }
                  mouseDownX=nowX;
                  mouseDownY=nowY;
        }
        lastStateButtonIsPressed=isPressed;
        Camera.reScale(Mouse.getDWheel());

    }

    public static void draw() {

        glEnable(GL_TEXTURE_2D);
        FontDrawer.drawString(10, 10, "TEST OpenGL draw chars", new Color(1f, 1f, 1f), true);
        glDisable(GL_TEXTURE_2D);

        glColor3f(1, 1, 1);
        glBegin(GL_LINES);
        Camera.translatePoint(-100, 0);
        Camera.translatePoint(100, 0);
        Camera.translatePoint(0, -100);
        Camera.translatePoint(0, 100);
        glEnd();


        float centerX=Display.getWidth()/2f;
        float centerY=Display.getHeight()/2f;

        glBegin(GL_TRIANGLES);


        glColor4f(0f, 0f, 0f, 0.6f);
        glVertex2f(0, 0);
        glVertex2f(Display.getWidth(), 0);
        glColor4f(0f, 0f, 0f, 0f);
        glVertex2f(centerX, centerY);


        glColor4f(0f, 0f, 0f, 0.6f);
        glVertex2f(0, Display.getHeight());
        glVertex2f(Display.getWidth(), Display.getHeight());
        glColor4f(0f, 0f, 0f, 0f);
        glVertex2f(centerX, centerY);

        glColor4f(0f, 0f, 0f, 0.6f);
        glVertex2f(0, 0);
        glVertex2f(0, Display.getHeight());
        glColor4f(0f, 0f, 0f, 0f);
        glVertex2f(centerX, centerY);

        glColor4f(0f, 0f, 0f, 0.6f);
        glVertex2f(Display.getWidth(), 0);
        glVertex2f(Display.getWidth(), Display.getHeight());
        glColor4f(0f, 0f, 0f, 0f);
        glVertex2f(centerX, centerY);
        glEnd();
    }


}

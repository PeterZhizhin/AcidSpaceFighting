package com.company.Graphic;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GUI {

    private static boolean lastStateButtonIsPressed=false;
    private static int mouseDownX=0;
    private static int mouseDownY=0;

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

    }


}

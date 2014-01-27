package com.company.Graphic;

import com.company.Graphic.Controls.Button;
import com.company.Graphic.Controls.Control;
import com.company.Graphic.Controls.FontDrawer;
import com.company.Graphic.Controls.Label;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class GUI {

    private static boolean lastStateButtonIsPressed=false;
    private static int mouseDownX=0;
    private static int mouseDownY=0;
    private static ArrayList<Control> controls;

    public static void init() {
        FontDrawer.init();
        controls=new ArrayList<Control>();
        controls.add(new Label(20, 20, "It is text label which is control, read it.", true));
        controls.add(new Label(20, 50, "It is small text label which is control, read it.", false));
    }

    public static void update() {

        int x = Mouse.getX();
        int y=Display.getHeight()-Mouse.getY();
        boolean buttonIsPressed=Mouse.isButtonDown(0);

        for (Control control : controls) {
            control.update(x, y, buttonIsPressed);
        }

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

        glBegin(GL_QUADS);
        for (Control control : controls) {
            control.drawBackground();
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);
        for (Control control : controls) {
            control.drawTitle();
        }
        glDisable(GL_TEXTURE_2D);

    }


}

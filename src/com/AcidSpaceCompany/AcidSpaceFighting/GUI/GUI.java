package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Control;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.FontDrawer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;

public abstract class GUI {

    private boolean lastStateButtonIsPressed = false;
    private int mouseDownX = 0;
    private int mouseDownY = 0;
    protected LinkedList<Control> controls;

    public GUI() {
        FontDrawer.init();
        controls = new LinkedList<Control>();
    }

    public Point getDeltaMouse()
    {
        int nowX = Mouse.getX();
        int nowY = Display.getHeight() - Mouse.getY();
        Point result = new Point(nowX - mouseDownX, nowY - mouseDownY);
        mouseDownX = nowX;
        mouseDownY = nowY;
        return result;
    }

    public boolean isKeyPressed(boolean isLeft)
    {
        return Mouse.isButtonDown(isLeft ? 0 : 1);
    }

    public void update() {

        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
        boolean buttonIsPressed = Mouse.isButtonDown(0);

        for (Control control : controls) {
            control.update(x, y, buttonIsPressed);
        }


    }

    public void draw() {

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

package com.company.Graphic;

import com.company.GameState;
import com.company.Graphic.Controls.Button;
import com.company.Graphic.Controls.Control;
import com.company.Graphic.Controls.FontDrawer;
import com.company.Graphic.Controls.Label;
import com.company.Window;
import com.company.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

import static com.company.BasicWindow.exit;
import static org.lwjgl.opengl.GL11.*;

public class GUI {

    private static boolean lastStateButtonIsPressed = false;
    private static int mouseDownX = 0;
    private static int mouseDownY = 0;
    private static ArrayList<Control> controls;

    public static void init() {
        FontDrawer.init();
        controls = new ArrayList<Control>();
        Button startButton = new Button(10, 50, 100, 20, "Start Game");
        startButton.setEvent(new Runnable() {
            @Override
            public void run() {
                Window.initWorld();
            }
        });
        Button resumeButton = new Button(10, 80, 100, 20, "Resume Game");
        resumeButton.setEvent(new Runnable() {
            @Override
            public void run() {
                Window.resumeGame();
            }
        });
        controls.add(startButton);
        controls.add(resumeButton);
    }

    public static void update() {

        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
        boolean buttonIsPressed = Mouse.isButtonDown(0);

        for (Control control : controls) {
            control.update(x, y, buttonIsPressed);
        }

        boolean isPressed = Mouse.isButtonDown(0);
        if (isPressed) {
            int nowX = Mouse.getX();
            int nowY = Display.getHeight() - Mouse.getY();
            if (lastStateButtonIsPressed) {

                Camera.move(mouseDownX - nowX, mouseDownY - nowY);
            }
            mouseDownX = nowX;
            mouseDownY = nowY;
        }
        lastStateButtonIsPressed = isPressed;
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

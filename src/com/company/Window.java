package com.company;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.*;

public class Window {

    private static boolean isWorking = true;
    private static World world =new World();

    private static void initDisplay() {
        try {
            int w = 1000;
            int h = 700;
            Display.setDisplayMode(new DisplayMode(w, h));
            Display.setVSyncEnabled(true);
            Display.create();
            glClearColor(0f, 0f, 0f, 1f);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glLineWidth(2f);
            glOrtho(0, w, h, 0, 1, 0);
            Mouse.create();
            System.out.println("GL window initialise successfully");
        } catch (LWJGLException e) {
            System.err.println("Failed to init GL window");
            exit();
        }
    }

    private static void initUpdateThread() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isWorking) {
                    world.update();
                } else cancel();
            }
        }, 0, 10);
    }

    private static void drawWhileWorks() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            world.draw();
            Display.sync(60);
            Display.update();
        }
    }

    private static void exit() {
        isWorking = false;
        Mouse.destroy();
        Display.destroy();
        System.out.println("Exiting");
        System.exit(0);
    }

    public static void main(String[] args) {
        initDisplay();
        initUpdateThread();
        drawWhileWorks();
        exit();
    }
}

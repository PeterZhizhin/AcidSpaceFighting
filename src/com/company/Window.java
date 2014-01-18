package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.*;

public class Window {

    private static boolean isWorking = true;
    private static float colorfull=1;
    private static GeometricModel g;

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

            Point[] pts=new Point[3];
            pts[0]=new Point(100f, 100f);
            pts[1]=new Point(300f, 100f);
            pts[2]=new Point(300f, 300f);
            g=new GeometricModel(pts);

        } catch (LWJGLException e) {
            System.err.println("Failed to init GL window");
            exit();
        }
    }

    private static void initUpdateThread() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isWorking)
                {
                    update();
                }
                else cancel();
            }
        }, 0, 20);
    }

    private static void update() {
        g.rotate(0.1f);
    }

    private static void drawWhileWorks() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            float colorfullABS=Math.abs(colorfull);

            glBegin(GL_TRIANGLES);
            glColor3f(colorfullABS, 0f, 0f);
            glVertex2f(g.getPoint(0).getX(), g.getPoint(0).getY());
            glColor3f(0f, colorfullABS, 0f);
            glVertex2f(g.getPoint(1).getX(), g.getPoint(1).getY());
            glColor3f(0f, 0f, colorfullABS);
            glVertex2f(g.getPoint(2).getX(), g.getPoint(2).getY());

            glEnd();



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

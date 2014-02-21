package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glOrtho;


/**
 * Абстрактный класс главного окна. Считает FPS, рисует и обновляет экран методами наследника
 * Abstract class of main window. It counts FPS. It can drawTopLayer, update with methods of child class
 */
public abstract class BasicWindow {
    private String title;
    public boolean isWorking = true;
    private long lastFPS = getWorkTime();
    private int FPS = 0;
    private int frameRate;
    private long lastFrame = getWorkTime();

    /**
     * Метод, позволяющий легко получить текущее время работы программы
     * Method which helps you to get system time easily
     *
     * @return Время работы программы.
     * Current system time.
     */
    private long getWorkTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Метод для получения изменения времени между кадрами (потом будет передан в update(int deltaTime))
     *
     * @return deltaTime between last frame and current time (it will be deltaTime in update(int deltaTime))
     */
    private int getTimeSinceLastRedraw() {
        long time = getWorkTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public void setTitle(String s) {
        Display.setTitle(s);
    }

    /**
     * Метод для обновления информации о FPS
     * Method which updates information about FPS
     */
    private void updateFPS() {
        //Если с момента последнего обновления FPS прошло больше 1 секунды, то надо обновить в заголовок этот FPS
        //If time since last FPS updates more than 1 sec then we update FPS info to title
        if (getWorkTime() - lastFPS > 1000) {
            setTitle(title + ": " + FPS);
            lastFPS += 1000;
            FPS = 0;
        }
        //Добавляем кадр
        //Add frame
        FPS++;
    }

    /**
     * Инициализация дисплея и уход в бесконечный цикл обновления
     * Display initialization and going to forever update() drawTopLayer() cycle.
     *
     * @param width     Ширина окна. Window Width.
     * @param height    Высота окна. Window Height.
     * @param frameRate Блокировка фреймрейта (почему-то не работает). Lock frame rate (I don't know why but it does not work)
     * @param title     Заголовок окна. Window title.
     */
    public BasicWindow(int width, int height, int frameRate, String title) {
        try {
            AL.create();
            this.title = title;
            this.frameRate = frameRate;
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(false);
            Display.sync(frameRate);
            Mouse.create();
            TextureDrawer.init();
            glClearColor(0f, 0f, 0f, 1f);
            glEnable(GL_BLEND);
            glEnable(GL_POINT_SMOOTH);
            glEnable(GL_LINE_SMOOTH);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glLineWidth(2f);
            glOrtho(0, width, height, 0, 1, 0);
            glEnable(GL_POINT_SMOOTH);
            glEnable(GL_LINE_SMOOTH);
            glEnable(GL_POLYGON_SMOOTH);
            glEnable(GL_TEXTURE_2D);
        } catch (LWJGLException e) {
            System.err.println("Failed to setup display");
            exit();
        }
    }

    public void startWorking() {

        while (!Display.isCloseRequested() && isWorking) {
            update(getTimeSinceLastRedraw());
            updateFPS();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            draw();
            Display.sync(frameRate);
            Display.update();
        }
        exit();
    }

    /**
     * Обновление
     * Update
     *
     * @param deltaTime Время с предыдущего обновления. Time since last update.
     */
    protected void update(int deltaTime) {
    }

    /**
     * Рисовашки
     * Drawings
     */
    protected void draw() {
    }

    /**
     * Очищаем память
     * Clear your memory
     */
    public static void exit() {
        System.out.println("[BasicWindow] Exiting");
        SoundBase.dispose();
        AL.destroy();
        Display.destroy();
        Mouse.destroy();
        System.exit(0);
    }
}

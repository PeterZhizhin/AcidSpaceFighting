package com.company;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glOrtho;


/**Абстрактный класс главного окна. Считает FPS, рисует и обновляет экран методами наследника
 * Abstract class of main window. It counts FPS. It can draw, update with methods of child class
 */
public abstract class BasicWindow {
    //Заголовок. Не содержит в себе упоминание о FPS
    //Title. It does not include any information about FPS.
    protected static String title;

    /**
     *Метод, позволяющий легко получить текущее время работы программы
     *Method which helps you to get system time easily
     * @return Время работы программы.
     *         Current system time.
     */
    protected long getTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    //Переменные для подсчёта FPS.
    //Variables for FPS counting
    private long lastFPS;
    private int FPS = 0;

    //Переменная, содержащая в себе время, когда был отрисован предыдущий кадр.
    //Variable for time when last frame was drawn.
    private long lastFrame = 0;

    /**
     * Метод для получения изменения времени между кадрами (потом будет передан в update(int deltaTime))
     * @return deltaTime between last frame and current time (it will be deltaTime in update(int deltaTime))
     */
    private int getDelta()
    {
        long time = getTime();
        int delta = (int)(time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Метод для обновления информации о FPS
     * Method which updates information about FPS
     */
    private void updateFPS()
    {
        //Если с момента последнего обновления FPS прошло больше 1 секунды, то надо обновить в заголовок этот FPS
        //If time since last FPS updates more than 1 sec then we update FPS info to title
        if (getTime() - lastFPS > 1000)
        {
            Display.setTitle(title + ": " + FPS);
            lastFPS += 1000;
            FPS = 0;
        }
        //Добавляем кадр
        //Add frame
        FPS++;
    }

    /**
     *
     * Инициализация дисплея и уход в бесконечный цикл обновления
     * Display initialization and going to forever update() draw() cycle.
     * @param width Ширина окна. Window Width.
     * @param height Высота окна. Window Height.
     * @param frameRate Блокировка фреймрейта (почему-то не работает). Lock frame rate (I don't know why but it does not work)
     * @param title Заголовок окна. Window title.
     */
    protected void initDisplay(int width, int height, int frameRate, String title)
    {
        //Инициализация дисплея
        //Display initialization
        BasicWindow.title = title;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(false);
            Display.sync(frameRate);
        }
        catch (LWJGLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        glClearColor(0f, 0f, 0f, 1f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(2f);
        glOrtho(0, width, height, 0, 1, 0);

    }

    protected void goToUpdateDrawCycle(int frameRate)
    {
        //Инициализация обновления FPS
        //FPS updating initialization
        getDelta();
        lastFPS = getTime();


        //Отправляемся в бексконечный цикл отрисовки!
        //Go to forever update() draw() cycle!
        goToForeverCycle(frameRate);
    }

    /**
     * Бесконечный цикл отрисовки
     * Forever update() draw() cycle.
     */
    private void goToForeverCycle(int frameRate)
    {
        try
        {
        //Работаем пока не нужно закрыть дисплей
        //Work while we don't need to close display
        while (!Display.isCloseRequested())
        {
            //Обновляем время работы программы
            //Update working time information
            int delta = getDelta();

            //Обновляем (метод должен быть переопределён у наследника)
            //Update (this method needed to be overriden from children
            update(delta);

            //Рисуем на окне
            //Draw some things
            draw();

            Display.sync(frameRate);

            //Обновляем информацию о FPS
            //Update FPS info
            updateFPS();

            //Обновляем экран
            //Update display
            Display.update();
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

            Display.destroy();
            //Отчистка памяти у вашего окна
            //Destroy some your shit to clear memory
            destroy();

        }

        System.exit(0);
    }

    /**
     * Обновление
     * Update
     * @param deltaTime Время с предыдущего обновления. Time since last update.
     */
    protected void update(int deltaTime)
    {

    }

    /**
     * Рисовашки
     * Drawings
     */
    protected void draw()
    {

    }

    /**
     * Отчищаем память
     * Clear your memory
     */
    protected void destroy()
    {

    }
}

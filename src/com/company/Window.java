package com.company;

import com.company.Audio.SoundBase;
import com.company.Graphic.Background;
import com.company.Graphic.Camera;
import com.company.Graphic.GUI;

/**
 * User class which helps allows you to make update() drawTopLayer() cycle easily
 * It has FPS inside title, and you should also use deltaTime
 * <p/>
 * Пользовательский класс для отрисовки. С помощью него легко реализовать цикл update() drawTopLayer() без лишнего мусора
 * Из фич: подсчет FPS и вывод в заголовок, возможность использования deltaTime (независимость выполнения логики от цикла обновления)
 */
public class Window extends BasicWindow {

    private static GameState gameState;
    private static boolean isInited = false;



    public static void initWorld()
    {
        World.init();
        Camera.init();
        isInited = true;
        gameState = GameState.GAME;
    }

    public static void resumeGame()
    {
        if (!isInited)
            initWorld();
        else
            gameState = GameState.GAME;
    }

    public static void pauseGame()
    {
        gameState = GameState.MENU;
    }

    public Window() {
        super(1200, 700, 10000, "Sample");
        gameState = GameState.MENU;
        SoundBase.init();
        GUI.init();
        startWorking();
    }

    public void setTitle(String s) {
        String addition = "";
        switch (gameState)
        {
            case GAME:
                addition = World.getMessage();
                break;
            case MENU:
                addition = "Acid Space Fighting";
                break;
        }
        super.setTitle(s + " " + addition);
    }

    @Override
    protected void update(int deltaTime) {
        switch (gameState)
        {
            case MENU:
                GUI.update();
                break;
            case GAME:
                World.update(deltaTime / 1000f);
                break;
        }

    }

    @Override
    protected void draw() {
        switch (gameState)
        {
            case MENU:
                GUI.draw();
                break;
            case GAME:
                Background.draw();
                World.draw();
                break;
        }
    }

}
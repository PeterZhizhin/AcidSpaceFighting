package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Menu;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.*;
import org.lwjgl.input.Keyboard;

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

    private static Menu menu;

    public static void initWorld()
    {
        World.init();
        Camera.init();
        isInited = true;
        gameState = GameState.GAME;
    }

    public static void resumeGame()
    {
        if (isInited)
            gameState = GameState.GAME;
    }

    public static void pauseGame()
    {
        gameState = GameState.MENU;
    }

    private static void toggleGameState()
    {
        if (gameState == GameState.GAME) {
            pauseGame();
        }
        else
            resumeGame();
    }

    public Window() {
        super(1200, 700, 10000, "Window; ");
        gameState = GameState.MENU;
        menu = new Menu();
        SoundBase.init();
        ShadersBase.init();
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
        while (Keyboard.next())
            if (isInited & (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) & Keyboard.getEventKeyState())
                    toggleGameState();

        switch (gameState)
        {
            case MENU:
                menu.update();
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
                menu.draw();
                break;
            case GAME:
                World.draw();
                break;
        }
    }

}
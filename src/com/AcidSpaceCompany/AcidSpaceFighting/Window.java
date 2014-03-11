package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.GUI;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Menu;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.*;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.FontDrawer;
import org.lwjgl.input.Keyboard;

import java.security.Key;

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
    private static HUD hud;

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
        if (gameState == GameState.GAME)
            pauseGame();
        else
            resumeGame();
    }

    public Window() {
        super(1200, 700, 10000, "Window; ");
        gameState = GameState.MENU;
        menu = new Menu();
        hud = new HUD();
        SoundBase.init();
        ShadersBase.init();
        FontDrawer.init();
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
                hud.update();
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
                hud.draw();
                World.draw();
                break;
        }
    }

}
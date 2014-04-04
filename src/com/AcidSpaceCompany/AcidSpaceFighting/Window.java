package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;
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

    private static boolean change = false;
    private static boolean isMenu = true;
    private static Menu menu;

    public static void initServer()
    {
        OurWorld.initServer();
        Camera.init();
        isMenu=false;
    }

    public static void initClient()
    {
        OurWorld.initClient();
        Camera.init();
        isMenu=false;
    }

    public static void initLocal()
    {
        OurWorld.initLocal();
        Camera.init();
        isMenu=false;
    }

    public Window() {
        super(1200, 700, 10000, "Window; ");
        menu = new Menu();
        SoundBase.init();
        ShadersBase.init();
        HUD.init();
        startWorking();
    }

    public void setTitle(String s) {
        super.setTitle(s + " Acid Space Fighting");
    }

    public static void changeState() {
        isMenu=!isMenu;
    }

    @Override
    protected void update(int deltaTime) {
        boolean current=Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
            if (current&&!change)
                changeState();
        change=current;

        if (isMenu) menu.update();
        else
            OurWorld.update(deltaTime / 1000f);
    }

    @Override
    protected void draw() {
        if (isMenu) menu.draw();
        else
            OurWorld.draw();
    }

}
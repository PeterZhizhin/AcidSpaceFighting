package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.Window;
import org.lwjgl.input.Keyboard;

public class Menu extends GUI {


    private void resume()
    {
        Window.resumeGame();
    }

    private void start()
    {
        Window.initWorld();
    }

    public Menu()
    {
        super();
        Button startButton = new Button(10, 50, 100, 20, "Start Game");
        startButton.setEvent(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
        Button resumeButton = new Button(10, 80, 100, 20, "Resume Game");
        resumeButton.setEvent(new Runnable() {
            @Override
            public void run() {
                resume();
            }
        });
        controls.add(startButton);
        controls.add(resumeButton);
    }

}

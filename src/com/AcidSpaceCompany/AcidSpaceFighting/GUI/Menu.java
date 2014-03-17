package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.Window;

import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.*;

public class Menu extends Form {

    private void resume() {
        Window.changeState();
    }

    private void start() {
        Window.initWorld();
    }

    public void draw() {
        drawBackground(true);
        super.draw();
    }

    public Menu()
    {
        super();
        Button startButton = new Button(10, 50, 300, 40, "Start Game");
        startButton.setEvent(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
        Button resumeButton = new Button(10, 90, 300, 40, "Resume Game");
        resumeButton.setEvent(new Runnable() {
            @Override
            public void run() {
                resume();
            }
        });
        Button quit = new Button(10, 130, 300, 40, "Quit");
        quit.setEvent(new Runnable() {
            @Override
            public void run() {
                Window.exit();
            }
        });
        controls.add(startButton);
        controls.add(resumeButton);
        controls.add(quit);
    }

}

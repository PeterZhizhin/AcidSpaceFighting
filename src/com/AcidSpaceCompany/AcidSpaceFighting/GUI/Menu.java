package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.Window;

import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.*;

public class Menu extends Form {

    private void resume() {
        Window.changeState();
    }

    public void draw() {
        drawBackground(true);
        super.draw();
    }

    public Menu()
    {
        super();
        Button startButton = new Button(10, 50, 300, 40, "Start Local");
        startButton.setEvent(Window::initLocal);
        Button startButton2 = new Button(10, 90, 300, 40, "Start Server");
        startButton2.setEvent(Window::initServer);
        Button startButton3 = new Button(10, 130, 300, 40, "Start Client");
        startButton3.setEvent(Window::initClient);
        Button resumeButton = new Button(10, 170, 300, 40, "Resume Game");
        resumeButton.setEvent(this::resume);
        Button quit = new Button(10, 210, 300, 40, "Quit");
        quit.setEvent(Window::exit);
        controls.add(startButton);
        controls.add(startButton2);
        controls.add(startButton3);
        controls.add(resumeButton);
        controls.add(quit);
    }

}

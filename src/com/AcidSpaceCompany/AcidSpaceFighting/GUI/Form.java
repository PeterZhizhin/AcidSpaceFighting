package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Control;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.ShadersBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.LinkedList;

import static com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class Form {

    protected LinkedList<Control> controls;

    public Form() {
        controls = new LinkedList<Control>();
    }

    public void update() {

        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
        boolean buttonIsPressed = Mouse.isButtonDown(0);

        for (Control control : controls) {
            control.update(x, y, buttonIsPressed);
        }
    }

    private void drawBackgrounds() {
        for (Control control : controls) {
            control.drawBackground();
        }
    }

    private void drawTitles() {
        for (Control control : controls) {
            control.drawTitle();
        }
    }

    private static float BCKGRND=0;
    private static final float speed=1f;

    public void draw() {

        ShadersBase.use(ShadersBase.blackAndWhite);
        ShadersBase.setFloatValue(ShadersBase.stateForBAWID, 1f+Mouse.getY()*1f/Display.getHeight()+1f-Mouse.getX()*1f/Display.getWidth());
        drawNUARBackground();

        BCKGRND-=speed;
        if (BCKGRND<=-512) BCKGRND+=512;


        startDrawNoise();
        glBegin(GL_QUADS);
        for (float i=BCKGRND; i<Display.getWidth(); i+=512) {
            for (float j=BCKGRND; j<Display.getHeight(); j+=512) {
                drawUntranslatedQuad(i, j, i + 512, j + 512);
            }
        }
        glEnd();
        finishDraw();

        startDrawControls();
        glBegin(GL_QUADS);
        drawBackgrounds();
        glEnd();
        TextureDrawer.startDrawText();
        glBegin(GL_QUADS);
        drawTitles();
        glEnd();
    }


}

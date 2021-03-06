package com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

import static org.lwjgl.opengl.GL11.glColor4f;

public class Button extends Control {

    private boolean oldIsDown;
    protected int isSelected = 1;
    private Runnable event;

    public Button(int x, int y, int w, int h, String s) {
        super(x, y, w, h);
        text = s;
    }

    public void drawBackground() {
        int isSelectedInverted = gradations - isSelected - 1;
        colors[isSelectedInverted].bind();
        TextureDrawer.drawUntranslatedQuad(x, y, x2, y2);
    }

    public void setEvent(Runnable e) {
        event = e;
    }

    public void update(int xPos, int yPos, boolean isDown) {
        if (contains(xPos, yPos)) {
            if (isDown && !oldIsDown)
                event.run();
            if (isSelected < gradations - 1) isSelected++;
        } else if (isSelected > 0) isSelected--;
        oldIsDown = isDown;
    }
}

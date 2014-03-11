package com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Font;

public class Label extends Control {

    public Label(int x, int y, String s) {
        super(x, y, 0, 0);
        text = s;
    }

    public void update(int x, int y, boolean isDown) {
        super.update(x, y, isDown);
    }

    public void drawTitle() {
        niceWhite.bind();
        Font.drawString(x, y, 32, text);
    }

    public void drawBackground() {
    }
}

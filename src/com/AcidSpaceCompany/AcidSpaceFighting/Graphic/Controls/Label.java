package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls;

public class Label extends Control {

    private boolean isBig;

    public Label(int x, int y, String s, boolean isBig) {
        super(x, y, 0, 0);
        text = s;
        this.isBig = isBig;
    }

    public void update(int x, int y, boolean isDown) {
        super.update(x, y, isDown);
    }

    public void drawTitle() {
        FontDrawer.drawString(x, y, text, niceWhite, isBig);
    }

    public void drawBackground() {
    }
}

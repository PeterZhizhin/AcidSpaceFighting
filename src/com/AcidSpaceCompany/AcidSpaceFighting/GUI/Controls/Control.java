package com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls;

import static org.lwjgl.opengl.GL11.glVertex2f;

public class Control extends Rectangle {
    protected String text;

    public static Color niceWhite = new Color(1f, 1f, 1f);
    public static Color niceGray = new Color(0.06640625f, 0.06640625f, 0.06640625f);
    protected static Color[] colors;
    protected static boolean isInitialised = false;
    protected static final int gradations = 15;

    public void drawBackground() {
        niceGray.bind();
        glVertex2f(x, y);
        glVertex2f(x, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y);
        System.out.println(x + " " + y + " " + x2 + " " + y2);
    }

    public void drawTitle() {
        FontDrawer.drawString(x + 10, y, text, niceWhite, false);
    }

    public void setText(String s) {
        text = s;
    }

    public String getText() {
        return text;
    }

    public void update(int x, int y, boolean isDown) {
    }

    public Control(int x, int y, int w, int h) {
        super(x, y, w, h);
        text = "";
        if (!isInitialised) {
            colors = new Color[gradations];

            for (int i = 1; i < gradations - 1; i++) {
                colors[i] = new Color(
                        (Control.niceGray.r * i + Control.niceWhite.r * (gradations - i)) / gradations,
                        (Control.niceGray.g * i + Control.niceWhite.g * (gradations - i)) / gradations,
                        (Control.niceGray.b * i + Control.niceWhite.b * (gradations - i)) / gradations);
            }
            colors[0] = Control.niceWhite;
            colors[gradations - 1] = Control.niceGray;

            isInitialised = true;
        }
    }

}

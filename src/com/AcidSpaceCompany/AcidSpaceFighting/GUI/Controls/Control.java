package com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Font;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class Control extends Rectangle {
    protected String text;

    public static Color niceWhite = new Color(1f, 1f, 1f);
    public static Color niceGray = new Color(0.06640625f, 0.06640625f, 0.06640625f);
    protected static Color[] colors;
    protected static boolean isInitialised = false;
    protected static final int gradations = 15;

    public void drawBackground() {
        niceGray.bind();
        TextureDrawer.drawUntranslatedQuad(x, y, x2, y2);
    }

    public void drawTitle() {
        niceWhite.bind();
        Font.drawString(x, y, 32, text);
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

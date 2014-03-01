package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls;

import java.awt.*;


public class FontDrawer {

    private static TrueTypeFont fontBig;
    private static TrueTypeFont fontSmall;

    public static void init() {
        try {

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, FontDrawer.class.getResourceAsStream("SourceCodePro-Regular.ttf"));
            awtFont2 = awtFont2.deriveFont(14f);
            fontSmall = new TrueTypeFont(awtFont2, true);

            Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, FontDrawer.class.getResourceAsStream("SourceCodePro-Regular.ttf"));
            awtFont1 = awtFont1.deriveFont(28f);
            fontBig = new TrueTypeFont(awtFont1, true);

        } catch (Exception e) {
            System.err.println("Failed to load font");
        }
    }

    public static void drawString(float x, float y, String s, Color color, boolean big) {
        color.bind();
        if (big)
            fontBig.drawString(x, y + 20, s, 1f, -1f);
        else fontSmall.drawString(x, y + 20, s, 1f, -1f);
    }

}

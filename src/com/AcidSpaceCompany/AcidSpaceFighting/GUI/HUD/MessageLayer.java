package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class MessageLayer {

    private static float opacity=0f;
    private static String title="";
    private static String text="";

    public static void update(float dt) {
        if (opacity>=0)
            opacity-=dt/5f;
    }

    public static void addAchive(String title, String text) {
        opacity=1f;
        MessageLayer.title=title;
        MessageLayer.text=text;
    }

    public static void draw() {
        if (opacity>=0) {
            if (opacity>0.8f) TextureDrawer.drawMessage((opacity - 0.8f) / 0.2f, title, text);
            else
            if (opacity<0.2f) TextureDrawer.drawMessage((0.2f - opacity) / 0.2f, title, text);
            else TextureDrawer.drawMessage(0f, title, text);
        }
    }

}

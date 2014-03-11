package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;

public class AchiveLayer {

    private static float opacity=0f;

    public static void update(float dt) {
        if (opacity>=0)
            opacity-=dt/5f;
    }

    public static void addAchive(Achive a) {
        opacity=1f;
    }

    public static void draw() {
        if (opacity>=0) {
            if (opacity>0.8f) TextureDrawer.drawAchives((opacity - 0.8f) / 0.2f);
            else
            if (opacity<0.2f) TextureDrawer.drawAchives((0.2f-opacity)/0.2f);
            else TextureDrawer.drawAchives(0f);
        }
    }

}

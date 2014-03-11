package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;

public class HUD {


    public static void update(float dt) {
        AchiveLayer.update(dt);
        MapLayer.update(dt);
    }

    public static void addAchive(Achive a) {
        AchiveLayer.addAchive(a);
    }

    public static void draw() {
        AchiveLayer.draw();
        MapLayer.draw();
    }



}

package com.AcidSpaceCompany.AcidSpaceFighting.GUI;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;

public class HUD extends GUI {

    public HUD()
    {
        super();
        //Добавляем компоненты
    }

    public void update()
    {
        super.update();
        //Перетаскиваем камеру
        if (isKeyPressed(true))
            Camera.move(getDeltaMouse());
    }
}

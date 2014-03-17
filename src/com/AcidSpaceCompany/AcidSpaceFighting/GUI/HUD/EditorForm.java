package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Font;
import org.lwjgl.opengl.Display;

public class EditorForm extends Form {

    public EditorForm()
    {
        super();
            Button b1 = new Button(10, 10, 100, 40, "Done");
            b1.setEvent(new Runnable() {
                @Override
                public void run() {
                    HUD.returnToGame();
                }
            });
            controls.add(b1);
    }

}

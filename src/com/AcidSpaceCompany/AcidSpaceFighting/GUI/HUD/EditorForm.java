package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;

public class EditorForm extends Form {

    public EditorForm()
    {
        super();
            Button b1 = new Button(10, 10, 100, 40, "Done");
            b1.setEvent(() -> {
                EditorLayer.deactivate();
                HUD.returnToGame();
            });
            controls.add(b1);
    }

}

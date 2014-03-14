package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls.Button;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Font;
import org.lwjgl.opengl.Display;

public class Question extends Form {

    public Question(String[] answers, Runnable[] actions)
    {
        super();
        int maxLength=0;
        for (String answer : answers)
            if (answer.length() > maxLength) maxLength = answer.length();
        int width= (int) Font.getWidth(32, maxLength);
        int x= (Display.getWidth()-width)/2;
        int y=50;

        for (int i=0; i<answers.length; i++) {
            Button b1 = new Button(x, y, width, 40, answers[i]);
            b1.setEvent(actions[i]);
            controls.add(b1);
            y+=50;
        }
    }

}

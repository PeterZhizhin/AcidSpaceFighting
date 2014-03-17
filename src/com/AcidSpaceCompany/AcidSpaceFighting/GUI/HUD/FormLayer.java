package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;

public class FormLayer {

    private static Form q;

    public static void update() {
            q.update();
    }

    public static void askQuestion(String[] answers, Runnable[] actions) {
        q=new Question(answers, actions);
    }

    public static void draw() {
        q.draw();
    }

}

package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class QuestionLayer{

    private static Question q;

    public static void update() {
        if (q!=null)
            q.update();
    }

    public static void askQuestion(String[] answers, Runnable[] actions) {
        q=new Question(answers, actions);
        World.setQuestionMode(true);
    }

    public static void hideQuestion() {
        q=null;
        World.setQuestionMode(false);
    }

    public static void draw() {
        if (q!=null) q.draw();
    }

}

package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;

public class HUD {


    public static void update(float dt) {
        MessageLayer.update(dt);
        MapLayer.update(dt);
        QuestionLayer.update();
    }

    public static void addAchive(Achive a) {
        showMessage(a.getTitle(), a.getText());
    }

    public static void showMessage(String title, String text) {
        MessageLayer.addAchive(title, text);
    }

    public static void askQuestion(String[] answers, Runnable[] actions) {
        QuestionLayer.askQuestion(answers, actions);
    }

    public static void hideQuestion() {
        QuestionLayer.hideQuestion();
    }

    public static void draw() {
        MessageLayer.draw();
        QuestionLayer.draw();
        MapLayer.draw();
    }



}

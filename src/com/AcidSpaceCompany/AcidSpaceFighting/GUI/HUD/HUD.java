package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.Worlds.OurWorld;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Message;

public class HUD {

    public enum Input {game, form, editor}
    private static Input input=Input.editor;

    public static boolean getIsRealTime() {
        return input==Input.game;
    }

    public static void init() {
         EditorLayer.init();
    }

    public static void update(float dt) {
        MessageLayer.update(dt);
        MapLayer.update(dt);
        switch (input) {
            case game:
                OurWorld.updatePhysic(dt);
                OurWorld.getPlayerShip().updateKeyboardInput();
                break;
            case form: FormLayer.update();
                break;
            case editor: EditorLayer.update();
                break;
        }

    }

    public static void addAchive(Message a) {
        showMessage(a.getTitle(), a.getText());
    }

    public static void addAchive(Achive a) {
        showMessage(a.getTitle(), a.getText());
    }

    public static void showMessage(String title, String text) {
        MessageLayer.addAchive(title, text);
    }

    public static void startEditor() {
        input=Input.editor;
    }

    public static void returnToGame() {
        input=Input.game;
    }

    public static void askQuestion(String[] answers, Runnable[] actions) {
        FormLayer.askQuestion(answers, actions);
        input=Input.form;
    }

    public static void hideQuestion() {
        input=Input.game;
    }

    public static void draw() {
        switch (input) {
            case form:
                FormLayer.draw();
                break;
            case editor:
                EditorLayer.draw();
                break;
        }
        MessageLayer.draw();
        MapLayer.draw();
    }



}

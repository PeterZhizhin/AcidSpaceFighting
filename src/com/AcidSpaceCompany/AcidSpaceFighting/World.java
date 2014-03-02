package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Background;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Explosion;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.LinkedList;


public class World {

    private static ArrayList<Model> models;
    private static LinkedList<Effect> effects;

    public static void explode(Point center, float power) {
        Explosion e=new Explosion(center, power/5);
        addEffect(e);
        SoundBase.playExplosion();

        sortEffects();

        explosionBuffer.add(center);
        explosionPowerBuffer.add(power);
    }

    private static void sortEffects() {
        for (int i=0; i<effects.size()-1; i++) {

            int min=i;
            for (int j=i+1; j<effects.size(); j++)
                if (effects.get(j).getEfectType()<effects.get(min).getEfectType()) min=j;
            if (min!=i) {
                Effect e = effects.get(i);
                effects.set(i, effects.get(min));
                effects.set(min, e);
            }

        }
    }

    private static LinkedList<Model> addModelBuffer;
    public static void addModel(Model m) {
        addModelBuffer.add(m);
    }

    public static void addEffect(Effect p) {
        effects.add(p);
    }

    public static String getMessage() {
        return Camera.getMessage() + " Models: " + models.size()+" Effects: "+effects.size();
    }

    public static void draw() {
        Camera.setPosition(player1.getPoint());

        Background.draw();

        for (Effect effect : effects) {
            effect.draw();
        }

        TextureDrawer.startDrawTextures();
        for (Model model : models) {
            model.drawBackgroundLayer();
        }

        for (Model model : models) {
            model.drawTopLayer();
        }

        HUD.draw();

    }

    private static void updateCameraPosition() {
        Camera.reScale(Mouse.getDWheel());

        /*


    private static boolean lastStateButtonIsPressed;
    private static int mouseDownX, mouseDownY;

        boolean isPressed = Mouse.isButtonDown(0);
        if (isPressed) {
            int nowX = Mouse.getX();
            int nowY = Display.getHeight() - Mouse.getY();
            if (lastStateButtonIsPressed) {

                Camera.move(mouseDownX - nowX, mouseDownY - nowY);
            }
            mouseDownX = nowX;
            mouseDownY = nowY;
        }
        lastStateButtonIsPressed = isPressed;*/
    }

    private static void updateKeyboardInput() {
            if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
                player1.turnLeft();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                player1.throttle();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                player1.turnRight();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_A))
                player1.fireLeft();

            if (Keyboard.isKeyDown(Keyboard.KEY_D))
                player1.fireRight();

         if (Keyboard.isKeyDown(Keyboard.KEY_S))
                player1.explode();

            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8))
                player2.throttle();
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7))
                player2.turnLeft();
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9))
                player2.turnRight();
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4))
                player2.fireLeft();
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6))
                player2.fireRight();



        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            Window.pauseGame();
    }

    private static void updateModels(float deltaTime) {
        boolean wasIntersection = true;
        for (int n=0; wasIntersection & n <= 5; n++) {
            n++;
            wasIntersection = false;
            for (int i = 0; i < models.size() - 1; i++)
                for (int j = i + 1; j < models.size(); j++) {
                    if (wasIntersection)
                        models.get(i).crossThem(models.get(j), deltaTime);
                    else
                        wasIntersection = models.get(i).crossThem(models.get(j), deltaTime);
                }
        }

        for (int i = 0; i < models.size() - 1; i++)
            for (int j = i + 1; j < models.size(); j++) {
                models.get(i).updateStaticForces(models.get(j), deltaTime);
            }

        for (Model model : models) {
            model.updateMotion(deltaTime);
            model.update(deltaTime);
        }
    }

    private static void addAndRemoveModels() {

        for (int i=0; i<models.size(); i++) {
            if (models.get(i).getIsNoNeedMore())
            {
                models.get(i).destroy();
                models.remove(i);
            }
        }

        if (addModelBuffer.size() > 0) {
            models.addAll(addModelBuffer);
            addModelBuffer.clear();
        }
    }

    private static ArrayList<Point> explosionBuffer;
    private static ArrayList<Float> explosionPowerBuffer;
    private static void updateExplosions() {
        while (explosionBuffer.size() > 0)
    {
        for (Model m: models)
        {
            Point force=m.getCenter().negate().add(explosionBuffer.get(0)).negate();
            float l=force.length();
            l+=1;
            force=force.setLength(1);
            float power=explosionPowerBuffer.get(0);
            force=force.multiply(0.4f*power*power*power*power/l);
            m.useForce(m.getCenter(), force);
        }
        explosionBuffer.remove(0);
        explosionPowerBuffer.remove(0);
    }}

    private static void updateEffects(float deltaTime) {
        for (int i=0; i<effects.size(); i++) {
            Effect p =effects.get(i);
            p.update(deltaTime);
            if (p.noNeedMore()) {
                effects.remove(i);
            }
        }
    }

    public static void update(float deltaTime) {

        updateCameraPosition();
        updateKeyboardInput();
        updateModels(deltaTime);
        addAndRemoveModels();
        updateExplosions();
        updateEffects(deltaTime);
    }

    private static SpaceShip player1;
    private static SpaceShip player2;

    public static void init() {
        effects=new LinkedList<Effect>();

        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        explosionBuffer=new ArrayList<Point>();
        explosionPowerBuffer=new ArrayList<Float>();

        player1 = new SpaceShip(0,0);
        World.addModel(player1.getBody());

        player2 = new SpaceShip(10000,10000);
        World.addModel(player2.getBody());
    }

}
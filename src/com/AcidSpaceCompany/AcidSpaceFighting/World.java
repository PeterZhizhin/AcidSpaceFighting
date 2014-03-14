package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Audio.SoundBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Background;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Explosion;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Plot;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.PlotBase;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;


public class World {

    private static ArrayList<Model> models;
    private static LinkedList<Effect> effects;
    private static boolean physicIsActive=true;

    public static void setPhysicActivity(boolean active) {
        physicIsActive=active;
    }

    public static boolean getPhysicIsActive() {
        return physicIsActive;
    }

    public static void explode(Point center, float power) {
        Explosion e=new Explosion(center, power/5);
        addEffect(e);
        SoundBase.playExplosion();

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
    private static int unsorted=0;
    public static void addModel(Model m) {
        addModelBuffer.add(m);
        if (unsorted==0){
            sortEffects();
            unsorted=100;
        }
        else unsorted--;
    }

    public static void addEffect(Effect p) {
        effects.add(p);
    }

    public static String getMessage() {
        return Camera.getMessage() + " Models: " + models.size()+" Effects: "+effects.size();
    }

    public static void draw() {
        Camera.setPosition(player1.getCenter());

        Background.draw();

        glBegin(GL_QUADS);
        for (Effect effect : effects) {
            effect.draw();
        }
        glEnd();

        TextureDrawer.startDrawTextures();
        for (Model model : models) {
            model.drawBackgroundLayer();
        }

        for (Model model : models) {
            model.drawTopLayer();
        }
        TextureDrawer.finishDraw();

        HUD.draw();

    }

    private static void updateKeyboardInput() {

        player1.updateKeyboardInput();

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
        updateKeyboardInput();
        if (physicIsActive)
        {
            updateModels(deltaTime);
            addAndRemoveModels();
            updateExplosions();
            updateEffects(deltaTime);
            PlotBase.reCheck();
        }
        HUD.update(deltaTime);
    }

    private static SpaceShip player1;
    private static ArrayList<SpaceShip> ships=new ArrayList<SpaceShip>();

    public static SpaceShip getPlayerShip() {
        return player1;
    }

    public static boolean getShipIsAlive(int number) {
          for (SpaceShip sh: ships)
              if (sh.getNumber()==number) return true;
        return false;
    }

    public static void init() {
        Plot.init();

        HUD.askQuestion(new String[]{"Ответ 1", "Ответ 2"}, new Runnable[] {

                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("ANSWER 1");
                        HUD.hideQuestion();
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("ANSWER 2");
                        HUD.hideQuestion();
                    }
                }
        });


        effects=new LinkedList<Effect>();
        ships = new ArrayList<SpaceShip>();

        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        explosionBuffer=new ArrayList<Point>();
        explosionPowerBuffer=new ArrayList<Float>();

        player1 = new SpaceShip(0,0);
        ships.add(player1);

        SpaceShip player2 = new SpaceShip(10000,10000);
        ships.add(player2);

        for (SpaceShip ship : ships) World.addModel(ship);
    }

}
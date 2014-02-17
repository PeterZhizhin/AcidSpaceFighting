package com.company;

import com.company.Audio.Sound;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Effects.Explosion;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Posteffect;
import com.company.Models.Base.BaseModel;
import com.company.Models.Dynamite.DynamiteModel;
import com.company.Models.Engine.EngineModel;
import com.company.Models.Gun.GunModel;
import com.company.Physic.ComplexPhysicModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.LinkedList;

public class World {

    //TODO: реализовать удаление моделей

    private static ArrayList<Model> models;
    private static ArrayList<Posteffect> effects;
    private static ArrayList<Integer> removeBuffer;
    private static ArrayList<Point> explosionBuffer;
    private static ArrayList<Float> explosionPowerBuffer;

    private static Sound music;

    public static void explode(Point center, float power) {
        Explosion e=new Explosion(center, power/8);
        addEffect(e);

        explosionBuffer.add(center);
        explosionPowerBuffer.add(power);
    }

    public static Model getModel(int num) {
        return models.get(num);
    }

    public static void removeModel(int num) {
        removeBuffer.add(num);
    }

    public static void addEffect(Posteffect p) {
        effects.add(p);
    }

    public static String getMessage() {
        return Camera.getMessage() + " Models: " + models.size();
    }

    public static void draw() {
        Camera.setPosition(totalModel.getCentre().getX(), totalModel.getCentre().getY());

        TextureDrawer.startDrawTextures();
        for (Model model : models) {
            model.drawBackgroundLayer();
        }

        for (Model model : models) {
            model.drawTopLayer();
        }

        for (Model model : models) {
            model.drawHealth();
        }

        for (Posteffect effect : effects) {
            effect.draw();
        }
        TextureDrawer.finishDraw();
    }

    public static void update(float deltaTime) {

        Camera.reScale(Mouse.getDWheel());

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            rocketPhys.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            rocketPhys2.doSpecialActionA();
            rocketPhys4.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rocketPhys3.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            rocketPhys5.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            boom.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            Window.pauseGame();

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

        if (addModelBuffer.size() > 0) {
            models.addAll(addModelBuffer);
            addModelBuffer.clear();
        }


        for (int i=0; i<effects.size(); i++) {
            Posteffect p =effects.get(i);
            p.update(deltaTime);
            if (p.noNeedMore())
            effects.remove(i);
        }

        if (removeBuffer.size()!=0) {
        for (int m: removeBuffer) {
            for (int i=0; i<models.size(); i++) {
                if (m==models.get(i).getNumber()) {
                    models.remove(i);
                    break;
                }
            }
        }
            removeBuffer.clear();
        }


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
        }
    }

    public static Point getNearestPhysicModel(Point p) {
        int maxNum = 0;
        double squadedMaxLength = p.getLengthSquared(models.get(0).getCenter());
        double tempMax;
        for (int i = 0; i < models.size(); i++) {
            if ((tempMax = models.get(i).getCenter().getLengthSquared(p)) < squadedMaxLength) {
                if (tempMax != 0) {
                    maxNum = i;
                    squadedMaxLength = tempMax;
                }
            }
        }
        return models.get(maxNum).getCenter();
    }

    public static void addModel(Model m) {
        addModelBuffer.add(m);
    }

    private static LinkedList<Model> addModelBuffer;
    private static Model rocketPhys;
    private static Model rocketPhys2;
    private static Model rocketPhys3;
    private static Model rocketPhys4;
    private static Model rocketPhys5;
    private static Model boom;
    private static ComplexPhysicModel totalModel;

    public static void init() {

        music = new Sound("ambience02.wav");
        music.setIsLooped(true);
        music.setVolume(0.3f);

        music.play();

        effects=new ArrayList<Posteffect>();

        removeBuffer=new ArrayList<Integer>();
        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        explosionBuffer=new ArrayList<Point>();
        explosionPowerBuffer=new ArrayList<Float>();

        Model m = new EngineModel(0, 0, 250f, 1.57f);
        rocketPhys = m;

        Model m2 = new EngineModel(0, 300, 250f);
        rocketPhys2 = m2;

        Model m6 = new EngineModel(0, 700, 250f);
        rocketPhys4 = m6;

        Model m5 = new EngineModel(0, 1000, 250f, -1.57f);
        rocketPhys3 = m5;

        Model m4 = new BaseModel(500, 0, 1250f);

        Model m7 = new DynamiteModel(1800, 0, 1250f);
        boom=m7;

        Model m8 = new GunModel(2700, -500, 1250f, (float) (Math.PI/2));
        rocketPhys5=m8;

        ComplexModel m3 = new ComplexModel(m4);
        m3.add(m, 0);
        m3.add(m2, 0);
        m3.add(m5, 0);
        m3.add(m6, 0);
        m3.add(m7, 0);
        m3.add(m8, 10);
        totalModel = m3.phyModel;

        World.addModel(m3);

    }


    public static void destroy()
    {
        music.dispose();
    }

}
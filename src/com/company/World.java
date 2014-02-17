package com.company;

import com.company.Audio.Sound;
import com.company.Audio.SoundBase;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.Effects.Explosion;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Posteffect;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Models.Base.BaseModel;
import com.company.Models.Connector.ConnectorModel;
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


    public static void explode(Point center, float power) {
        Explosion e=new Explosion(center, power/8);
        addEffect(e);

        SoundBase.playExplosion();

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
            for (Model m: rocketLeft) m.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            for (Model m: rocketEng) m.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            for (Model m: rocketRight) m.doSpecialActionA();
        }
/*

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            rocketPhys5.doSpecialActionA();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            boom.doSpecialActionA();
        }
*/

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
    private static ArrayList<Model> rocketEng=new ArrayList<Model>();
    private static ArrayList<Model> rocketLeft=new ArrayList<Model>();
    private static ArrayList<Model> rocketRight=new ArrayList<Model>();
    private static ComplexPhysicModel totalModel;

    public static void init() {

        SoundBase.playMusic();

        effects=new ArrayList<Posteffect>();

        removeBuffer=new ArrayList<Integer>();
        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        explosionBuffer=new ArrayList<Point>();
        explosionPowerBuffer=new ArrayList<Float>();

        float p2= -(float) (Math.PI/2);

        BaseModel base = new BaseModel(0, 0, 1000);
        EngineModel bl=new EngineModel(-1000, 0, 1000, p2);
        EngineModel br=new EngineModel(1000, 0, 1000, p2);
        EngineModel bll=new EngineModel(-2000, 0, 1000, p2);
        EngineModel brr=new EngineModel(2000, 0, 1000, p2);
        rocketLeft.add(bl); rocketLeft.add(bll);
        rocketRight.add(br); rocketRight.add(brr);
        ConnectorModel cb=new ConnectorModel(0, 1000, 1000);
        ConnectorModel cbb=new ConnectorModel(0, 2000, 1000);
        EngineModel cbbb=new EngineModel(0, 3000, 1000, p2);
        EngineModel cbbbl=new EngineModel(-1000, 3000, 1000, p2);
        EngineModel cbbbr=new EngineModel(1000, 3000, 1000, p2);
        EngineModel cbbbll=new EngineModel(-2000, 3000, 1000, p2);
        EngineModel cbbbrr=new EngineModel(2000, 3000, 1000, p2);
        rocketEng.add(cbbb);
        rocketEng.add(cbbbl);
        rocketEng.add(cbbbr);
        rocketEng.add(cbbbll);
        rocketEng.add(cbbbrr);

        ComplexModel m = new ComplexModel(base);
        m.add(bl, 0);
        m.add(br, 0);
        m.add(bll, 0);
        m.add(brr, 0);
        m.add(cb, 0);
        m.add(cbb, 0);
        m.add(cbbb, 0);
        m.add(cbbbl, 0);
        m.add(cbbbr, 0);
        m.add(cbbbll, 0);
        m.add(cbbbrr, 0);

        World.addModel(m);

        totalModel=m.phyModel;

        Model mm=new AsteroidModel(4500, 4500, 1000, 5000);
        World.addModel(mm);


    }

}
package com.company;

import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Models.Base.BaseModel;
import com.company.Models.Engine.EngineModel;
import com.company.Models.Gun.GunModel;
import com.company.Physic.ComplexPhysicModel;
import com.company.Physic.PhysicModel;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedList;

public class World {

    private static ArrayList<Model> models;

    public static Model getModel(int num) {
        return models.get(num);
    }

    public static String getMessage() {
        return Camera.getMessage() + " Models: " + models.size();
    }

    public static void draw() {
        Camera.setPosition(totalModel.getCentre().getX(), totalModel.getCentre().getY());
        for (Model model : models) {
            model.drawBackgroundLayer();
        }
        for (Model model : models) {
            model.drawTopLayer();
        }
        for (Model model : models) {
            model.drawHealthLine();
        }
    }

    public static void update(float deltaTime) {

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            rocketPhys.doSpecialActionA(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            rocketPhys2.doSpecialActionA(deltaTime);
            rocketPhys4.doSpecialActionA(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rocketPhys3.doSpecialActionA(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            rocketPhys5.doSpecialActionA(deltaTime);
        }

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
    private static PhysicModel rocketPhys;
    private static PhysicModel rocketPhys2;
    private static PhysicModel rocketPhys3;
    private static PhysicModel rocketPhys4;
    private static PhysicModel rocketPhys5;
    private static ComplexPhysicModel totalModel;

    public static void init() {
        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        Model m = new EngineModel(0, 0, 250f, 1.57f);
        rocketPhys = m.physic;

        Model m2 = new EngineModel(0, 300, 250f);
        rocketPhys2 = m2.physic;

        Model m6 = new EngineModel(0, 700, 250f);
        rocketPhys4 = m6.physic;

        Model m5 = new EngineModel(0, 1000, 250f, -1.57f);
        rocketPhys3 = m5.physic;

        Model m4 = new BaseModel(500, 0, 1250f);

        Model m7 = new BaseModel(2500, 0, 1250f);

        Model m8 = new GunModel(4000, 0, 1250f);
        rocketPhys5=m8.physic;

        ComplexModel m3 = new ComplexModel();
        m3.setBase(m4);
        m3.add(m, 0);
        m3.add(m2, 0);
        m3.add(m5, 0);
        m3.add(m6, 0);
        m3.add(m7, 0);
        m3.add(m8, 10);
        totalModel = m3.phyModel;

        World.addModel(m3);

        AsteroidModel asteroidModel = new AsteroidModel(-50000,-50000,10000f, 50000f);
        World.addModel(asteroidModel);
    }

}
package com.company;

import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Models.Engine.EngineModel;
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
        Camera.setPosition(rocketPhys.getCentre().getX(), rocketPhys.getCentre().getY());
        for (Model model : models) {
            model.drawBackgroundLayer();
        }
        for (Model model : models) {
            model.drawTopLayer();
        }
    }

    public static void update(float deltaTime) {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            rocketPhys.doSpecialActionA(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rocketPhys2.doSpecialActionA(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            rocketPhys.doSpecialActionB(deltaTime);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            rocketPhys2.doSpecialActionB(deltaTime);
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

    public static void init() {
        models = new ArrayList<Model>();
        addModelBuffer = new LinkedList<Model>();

        Model m = new EngineModel(0, 0, 250f);
        rocketPhys = m.physic;

        Model m2 = new EngineModel(0, 1000, 250f);
        rocketPhys2 = m2.physic;

        ComplexModel m3 = new ComplexModel();
        m3.setBase(m2);
        m3.add(m, 0);

        World.addModel(m3);
    }

}
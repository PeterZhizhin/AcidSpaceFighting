package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.ComplexGraphicModel;
import com.company.Graphic.GraphicModel;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Models.Engine.EngineModel;
import com.company.Models.Gun.GunModel;
import com.company.Physic.ComplexPhysicModel;
import com.company.Physic.PhysicModel;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class World {

    private static ArrayList<Model> models;

    public static Model getModel(int num) {
        return models.get(num);
    }

    public static String getMessage() {
        return Camera.getMessage()+" Models: "+models.size();
    }

    public static void draw() {
        //Camera.setPosition(rocketPhys.getCentre().getX(), rocketPhys.getCentre().getY());
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

        boolean  wasIntersection = true; byte n = 0;
        while (wasIntersection & n<=5)
        {
            n++;
            wasIntersection = false;
        for (int i=0; i<models.size()-1; i++)
            for (int j=i+1; j<models.size(); j++) {
                if (wasIntersection)
                    models.get(i).crossThem(models.get(j), deltaTime);
                else
                    wasIntersection = models.get(i).crossThem(models.get(j), deltaTime);
            }
        }
        for (int i=0; i<models.size()-1; i++)
            for (int j=i+1; j<models.size(); j++) {
                    models.get(i).updateStaticForces(models.get(j), deltaTime);
            }
        for (Model model : models) {
            model.updateMotion(deltaTime);
        }

        models.addAll(addModelBuffer);
        if (addModelBuffer.size()>0) {
            addModelBuffer.clear();
            for (int i=0; i<models.size(); i++)
            System.out.println(i+" : "+models.get(i).getCenter()+" "+models.get(i).physic.getCentre());
            System.out.println("FUCK");
        }
    }

    public static Point getNearestPhysicModel(Point  p) {
        int maxNum=0;
        double squadedMaxLength=p.getLengthSquared(models.get(0).getCenter());
        double tempMax;
        for (int i=0; i<models.size(); i++)   {
            if ((tempMax=models.get(i).getCenter().getLengthSquared(p))<squadedMaxLength) {
                if (tempMax!=0) {
                maxNum=i;
                squadedMaxLength=tempMax;  }
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
        models=new ArrayList<Model>();
        addModelBuffer=new LinkedList<Model>();

        Model m=new EngineModel(-10000, 10000, 250f);
        rocketPhys=m.physic;

        Model m2=new EngineModel(-9000, 10000, 250f);
        rocketPhys2=m.physic;

        ComplexModel m3 = new ComplexModel();
        m3.add(m);
        m3.add(m2);

        World.addModel(m3);
    }

}
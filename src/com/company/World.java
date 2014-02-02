package com.company;

import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Models.Asteroid.AsteroidModel;
import com.company.Models.Gun.GunModel;
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
        else
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            rocketPhys.doSpecialActionB(deltaTime);
        }
        else
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            rocketPhys.doSpecialActionC(deltaTime);
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

    public static Point getNearestPhysicModel(Point p) {
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

    public static void init() {
        models=new ArrayList<Model>();
        addModelBuffer=new LinkedList<Model>();

        Random rnd=new Random();
        float distance=4000f;
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++) {
                models.add(new AsteroidModel(i*distance, j*distance, rnd.nextInt(600)+600));
            }

        Model m=new GunModel(-10000, 10000, 250f);
        rocketPhys=m.physic;

        /*
        ArrayList<PhysicModel> bodies = new ArrayList<PhysicModel>();
        ArrayList<GraphicModel> graphicModels = new ArrayList<GraphicModel>();
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++) {
                float width=rnd.nextInt(600)+600;

                GeometricModel g2=new AsteroidGeometricModel(i*distance, j*distance, width);
                bodies.add(new PhysicModel(g2, width));
                graphicModels.add(new AsteroidGraphicModel(g2));
            }
        boolean[][] matrix = new boolean[2][2];
        matrix[0] = new boolean[]{true, true};
        matrix[1] = new boolean[]{true, true};

        ComplexModel m2 = new ComplexModel(new ComplexGraphicModel(graphicModels), new ComplexPhysicModel(bodies, matrix));
        World.addModel(m2);   */
    }

}
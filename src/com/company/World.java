package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.ComplexGraphicModel;
import com.company.Graphic.GraphicModel;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Models.GunEngine.GunGeometricModel;
import com.company.Models.GunEngine.GunGraphicModel;
import com.company.Models.GunEngine.GunPhysicModel;
import com.company.Models.RocketEngine.RocketEngineGeometricModel;
import com.company.Models.RocketEngine.RocketEngineGraphicModel;
import com.company.Models.RocketEngine.RocketEnginePhysicModel;
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
       // Camera.setPosition(rocket.getCentre().getX(), rocket.getCentre().getY());
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
        addModelBuffer.clear();
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
    private static GeometricModel rocket;
    private static PhysicModel rocketPhys;

    public static void init() {
        models=new ArrayList<Model>();
        addModelBuffer=new LinkedList<Model>();

        /*Random rnd=new Random();
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++) {
                float width=rnd.nextInt(600)+600;
                float distance=4000f;


                GeometricModel g=new AsteroidGeometricModel(i*distance, j*distance, width);
                PhysicModel p = new PhysicModel(g, width);
                Model m=new Model(new AsteroidGraphicModel(g), p);
                models.add(m);
            }

        GeometricModel g=new GunGeometricModel(-10000, 10000, 250f);
        rocket=g;
        PhysicModel e=new GunPhysicModel(g, 15f);
        rocketPhys=e;
        Model m=new Model(new GunGraphicModel(g), e);
        models.add(m);*/

        Random rnd=new Random();
        ArrayList<PhysicModel> bodies = new ArrayList<PhysicModel>();
        ArrayList<GraphicModel> graphicModels = new ArrayList<GraphicModel>();
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++) {
                float width=rnd.nextInt(600)+600;
                float distance=4000f;


                GeometricModel g=new AsteroidGeometricModel(i*distance, j*distance, width);
                bodies.add(new PhysicModel(g, width));
                graphicModels.add(new AsteroidGraphicModel(g));
            }
        boolean[][] matrix = new boolean[2][2];
        matrix[0] = new boolean[]{true, true};
        matrix[1] = new boolean[]{true, true};

        Model m = new Model(new ComplexGraphicModel(graphicModels), new ComplexPhysicModel(bodies, matrix));
    }

}
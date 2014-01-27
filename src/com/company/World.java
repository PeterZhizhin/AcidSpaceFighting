package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Models.RocketBase.RocketBaseGeometricModel;
import com.company.Models.RocketBase.RocketBaseGraphicModel;
import com.company.Physic.PhysicModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class World {

    private static ArrayList<Model> models;

    public static Model getModel(int num) {
        return models.get(num);
    }

    public static String getMessage() {
        return Camera.getMessage();
    }

    public static void draw() {
        Camera.setPosition(rocket.getCentre().getX(), rocket.getCentre().getY());
        for (Model model : models) {
            model.draw();
        }
    }

    public static void update(float deltaTime) {
        boolean wasIntersection = true; byte n = 0;
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
    }

    private static GeometricModel rocket;

    public static void init() {
        models=new ArrayList<Model>();

        Random rnd=new Random();
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++) {
                float width=rnd.nextInt(600)+600;
                float distance=4000f;


                GeometricModel g=new AsteroidGeometricModel(i*distance, j*distance, width);
                PhysicModel p = new PhysicModel(g, width);
                Model m=new Model(new AsteroidGraphicModel(g), p);
                models.add(m);
            }

        GeometricModel g=new RocketBaseGeometricModel(500, 500, 250f);
        rocket=g;
        PhysicModel e=new PhysicModel(g, 15f);
        Model m=new Model(new RocketBaseGraphicModel(g), e);
        models.add(m);
    }

}
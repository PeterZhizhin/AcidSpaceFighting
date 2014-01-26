package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;
import com.company.Models.Asteroid.AsteroidGeometricModel;
import com.company.Models.Asteroid.AsteroidGraphicModel;
import com.company.Physic.PhysicModel;

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
        for (Model model : models) {
            model.draw();
        }
    }

    public static void update(float deltaTime) {
         for (int i=0; i<models.size()-1; i++)
             for (int j=i+1; j<models.size(); j++) {
                 models.get(i).crossThem(models.get(j), deltaTime);
             }
        for (Model model : models) {
            model.updateMotion(deltaTime);
        }
    }

    public static void init() {
        models=new ArrayList<Model>();

        Random rnd=new Random();
        for (int i=0; i<12; i++)
            for (int j=0; j<12; j++) {
        float width=rnd.nextInt(200)+200;
        float distance=1200f;


        GeometricModel g=new AsteroidGeometricModel(i*distance, j*distance, width);
        PhysicModel p = new PhysicModel(g, width);
        Model m=new Model(new AsteroidGraphicModel(g), p);
        models.add(m);
            }
    }

}

package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.Camera;
import com.company.Graphic.GraphicModel;
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
        for (int i=0; i<25; i++)
            for (int j=0; j<25; j++) {
        float width=rnd.nextInt(20)+3;
        float distance=200f;

        GeometricModel g=new GeometricModel(new Point[]{
                new Point(i*distance, j*distance),
                new Point(i*distance+width, j*distance),
                new Point(i*distance+width, j*distance+width),
                new Point(i*distance, j*distance+width)});
                PhysicModel p = new PhysicModel(g);
        Model m=new Model(new GraphicModel(g, p), p);
        models.add(m);
            }
    }

}

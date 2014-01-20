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
        for (int i=0; i<20; i++)
            for (int j=0; j<20; j++) {
        float width=rnd.nextInt(20)+3;
        GeometricModel g=new GeometricModel(new Point[]{
                new Point(i*100, j*100),
                new Point(i*100+width, j*100),
                new Point(i*100+width, j*100+width),
                new Point(i*100, j*100+width)});
        Model m=new Model(new GraphicModel(g), new PhysicModel(g));
        models.add(m);
            }
    }

}

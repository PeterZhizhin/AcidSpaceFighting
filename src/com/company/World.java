package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.GraphicModel;
import com.company.Physic.PhysicModel;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private ArrayList<Model> models;

    public void draw() {
        for (Model model : models) {
            model.draw();
        }
    }

    public void update() {
         for (int i=0; i<models.size()-1; i++)
             for (int j=i+1; j<models.size(); j++) {
                 models.get(i).crossThem(models.get(j));
             }
        for (Model model : models) {
            model.updateMotion();
        }
    }

    public World() {
        models=new ArrayList<Model>();

        Random rnd=new Random();
        for (int i=4; i<7; i++)
            for (int j=4; j<7; j++) {
        float width=rnd.nextInt(20)+3;
        GeometricModel g=new GeometricModel(new Point[]{
                new Point(i*50, j*50),
                new Point(i*50+width, j*50),
                new Point(i*50+width, j*50+width),
                new Point(i*50, j*50+width)});
        Model m=new Model(new GraphicModel(g), new PhysicModel(g));
        models.add(m);
            }
    }

}

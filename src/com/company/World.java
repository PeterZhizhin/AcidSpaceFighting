package com.company;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Graphic.GraphicModel;
import com.company.Physic.PhysicModel;

import java.util.ArrayList;

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

        GeometricModel g=new GeometricModel(new Point[]{new Point(300f, 300f), new Point(300f, 350f), new Point(330f, 325f)});
        Model m=new Model(new GraphicModel(g), new PhysicModel(g));
        models.add(m);

        GeometricModel g2=new GeometricModel(new Point[]{new Point(500f, 300f), new Point(500f, 350f), new Point(530f, 325f)});
        Model m2=new Model(new GraphicModel(g2), new PhysicModel(g2));
        models.add(m2);

        GeometricModel g3=new GeometricModel(new Point[]{new Point(400f, 480f), new Point(400f, 530f), new Point(430f, 505f)});
        Model m3=new Model(new GraphicModel(g3), new PhysicModel(g3));
        models.add(m3);

    }

}

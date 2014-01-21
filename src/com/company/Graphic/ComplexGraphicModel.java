package com.company.Graphic;

import java.util.ArrayList;

public class ComplexGraphicModel extends GraphicModel{

    private ArrayList<GraphicModel> grModels;

    public void draw() {
        for (GraphicModel g: grModels)
            g.draw();
    }

    public void add(GraphicModel g) {
        grModels.add(g);
    }

    public void remove(int num) {
        grModels.remove(num);
    }

    public ComplexGraphicModel() {
        super(null, null);
        grModels=new ArrayList<GraphicModel>();
    }



}

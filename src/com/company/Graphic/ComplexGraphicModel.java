package com.company.Graphic;

import java.util.ArrayList;

public class ComplexGraphicModel extends GraphicModel{

    private ArrayList<GraphicModel> grModels;

    @Override
    public void  drawTopLayer() {
        for (GraphicModel g: grModels)
            g.drawTopLayer();
    }

    @Override
    public void drawBackgroundLayer()
    {
        for (GraphicModel g : grModels)
            g.drawBackgroundLayer();
    }

    public void add(GraphicModel g) {
        grModels.add(g);
    }

    public void remove(int num) {
        grModels.remove(num);
    }

    public ComplexGraphicModel(ArrayList<GraphicModel> g) {
        super(null, null);
        grModels=g;
    }



}

package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;


import java.util.ArrayList;

public class ComplexGraphicModel extends GraphicModel {

    public void destroy() {
       for (GraphicModel g: grModels) {
           g.destroy();
       }
    }

    private ArrayList<GraphicModel> grModels;
    public GraphicModel get(int index)
    {
        return grModels.get(index);
    }

    @Override
    public void drawTopLayer() {
        for (GraphicModel g : grModels)
            g.drawTopLayer();
    }

    @Override
    public void drawBackgroundLayer() {
        for (GraphicModel g : grModels)
            g.drawBackgroundLayer();

    }

    /*public void drawHealth() {
        for (GraphicModel g : grModels)
            g.drawHealth();
    }
    */

    public void setBase(GraphicModel g) {
        grModels.clear();
        grModels.add(g);
    }

    public void add(GraphicModel g) {
        grModels.add(g);
    }

    public void remove(int num) {
        grModels.remove(num);
    }

    public ComplexGraphicModel(ArrayList<GraphicModel> models)
    {
        super(null);
        grModels = models;
    }

    public ComplexGraphicModel() {
        super(null);
        grModels = new ArrayList<GraphicModel>();
    }


}

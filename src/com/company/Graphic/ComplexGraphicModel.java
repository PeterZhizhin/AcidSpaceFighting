package com.company.Graphic;


import com.company.Physic.ComplexPhysicModel;

import java.util.ArrayList;

public class ComplexGraphicModel extends GraphicModel {

    private ArrayList<GraphicModel> grModels;
    public GraphicModel get(int index)
    {
        return grModels.get(index);
    }
    private ComplexPhysicModel coPhy;

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

    public void drawHealth() {
        for (GraphicModel g : grModels)
            g.drawHealth();

        /*glEnable(GL_TEXTURE_2D);
        for (int i=0; i<coPhy.getConnectionPointsCount(); i++) {
            Point p=getTranslatedPoint(coPhy.getConnectionPoint(i));
            FontDrawer.drawString(p.x, p.y, i+" point", new Color(1f, 1f, 1f), false);
        }
        glDisable(GL_TEXTURE_2D); */
    }

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

    public ComplexGraphicModel(ComplexPhysicModel c, ArrayList<GraphicModel> models)
    {
        super(null);
        coPhy = c;
        grModels = models;
    }

    public ComplexGraphicModel(ComplexPhysicModel c) {
        super(null);
        coPhy=c;
        grModels = new ArrayList<GraphicModel>();
    }


}

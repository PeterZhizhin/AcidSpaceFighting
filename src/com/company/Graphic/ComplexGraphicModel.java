package com.company.Graphic;

import com.company.Geometry.Point;

import java.util.ArrayList;

import static com.company.Graphic.Camera.getTranslatedPoint;
import static org.lwjgl.opengl.GL11.*;

public class ComplexGraphicModel extends GraphicModel {

    private ArrayList<GraphicModel> grModels;

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

    public void drawHealthLine() {
        for (GraphicModel g : grModels)
            g.drawHealthLine();
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

    public ComplexGraphicModel() {
        super(null);
        grModels = new ArrayList<GraphicModel>();
    }


}

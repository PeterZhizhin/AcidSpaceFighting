package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

public class ComplexGraphicModel extends GraphicModel {

    private ComplexModel cm;

    public void destroy() {
       for (int i=0; i<cm.getSize(); i++) {
           cm.getModel(i).getGraphicModel().destroy();
       }
    }

    @Override
    public void drawTopLayer() {
        for (int i=0; i<cm.getSize(); i++) {
            cm.getModel(i).getGraphicModel().drawTopLayer();
        }
    }

    @Override
    public void drawBackgroundLayer() {
        for (int i=0; i<cm.getSize(); i++) {
            cm.getModel(i).getGraphicModel().drawBackgroundLayer();
        }
    }

    public ComplexGraphicModel(ComplexModel c)
    {
        super(null);
        cm=c;
    }


}

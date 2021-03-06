package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import org.lwjgl.opengl.GL11;

public class ComplexGraphicModel extends GraphicModel {

    private ComplexModel cm;

    public void destroy() {
       for (int i=0; i<cm.getSize(); i++) {
           cm.getModel(i).getGraphicModel().destroy();
       }
    }

    @Override
    public void drawTopLayer(boolean isSelected) {
        for (int i=0; i<cm.getSize(); i++) {
            cm.getModel(i).getGraphicModel().drawTopLayer(cm.getModel(i).getIsSelected());
        }
    }

    @Override
    public void drawBackgroundLayer() {
        GL11.glBegin(GL11.GL_QUADS);
        for (int i=0; i<cm.getSize()-1; i++) {
            for (int j=i+1; j<cm.getSize(); j++) {

               if (cm.checkDistanceIsToConnect(i, j)) {

                   TextureDrawer.drawConnection(cm.getModel(i).getCenter(), cm.getModel(j).getCenter(),
                           Math.max(cm.getModel(i).getMaxWidth(), cm.getModel(j).getMaxWidth())*0.1f
                           );
               }
            }
        }
        GL11.glEnd();
    }

    public ComplexGraphicModel(ComplexModel c)
    {
        super(null);
        cm=c;
    }


}

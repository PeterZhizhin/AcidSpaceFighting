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

        TextureDrawer.finishDraw();
        TextureDrawer.startDrawConnections();
        GL11.glBegin(GL11.GL_QUADS);
        for (int i=0; i<cm.getSize()-1; i++) {
            for (int j=i+1; j<cm.getSize(); j++) {
               if (cm.getModels().getConnection(i, j)) {
                   TextureDrawer.drawConnection(cm.getModel(i).getCenter(), cm.getModel(j).getCenter(),
                           Math.max(cm.getModel(i).getMaxWidth(), cm.getModel(j).getMaxWidth())*0.3f
                           );
               }
            }
        }
        GL11.glEnd();
        TextureDrawer.finishDraw();
        TextureDrawer.startDrawTextures();
    }

    public ComplexGraphicModel(ComplexModel c)
    {
        super(null);
        cm=c;
    }


}

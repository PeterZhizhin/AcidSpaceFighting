package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Background;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Explosion;
import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.SpaceShip;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.PlotBase;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;


public class World {

    private ArrayList<Model> models;
    private LinkedList<Effect> effects;

    /**
     * Получает модель по точке.
     * @param position Точка
     * @return null - если модели в этой точке нет. Ну или модель в этой точке
     */
    public Model getModel(Point position) {
        for (Model model : models)
            if (model.containsPoint(position))
                return model;
        return null;
    }

    public void explode(Point center, float power) {
        Explosion e=new Explosion(center, power/5);
        addEffect(e);
        explosionBuffer.add(center);
        explosionPowerBuffer.add(power);
    }

    private void sortEffects() {
        for (int i=0; i<effects.size()-1; i++) {

            int min=i;
            for (int j=i+1; j<effects.size(); j++)
                if (effects.get(j).getEfectType()<effects.get(min).getEfectType()) min=j;
            if (min!=i) {
                Effect e = effects.get(i);
                effects.set(i, effects.get(min));
                effects.set(min, e);
            }
        }
    }

    private LinkedList<Model> addModelBuffer;
    private int unsorted=0;
    public void addModel(Model m) {
        addModelBuffer.add(m);
        if (unsorted==0){
            sortEffects();
            unsorted=100;
        }
        else unsorted--;
    }

    public void addEffect(Effect p) {
        effects.add(p);
    }

    public void draw() {
        if (HUD.getIsRealTime())
        Camera.setPosition(player1.getCenter());
        else
            Camera.moveTo(player1.getCenter());


        Background.draw();

        TextureDrawer.startDrawConnections();
        for (Model model : models) {
            model.drawConnections();
        }
        TextureDrawer.finishDraw();

        glBegin(GL_QUADS);
        for (Effect effect : effects) {
            effect.draw();
        }
        glEnd();

        TextureDrawer.startDrawTextures();
        for (Model model : models) {
            model.drawTopLayer();
        }
        TextureDrawer.finishDraw();

        HUD.draw();

    }

    private void updateModels(float deltaTime) {
        boolean wasIntersection = true;
        for (int n=0; wasIntersection & n <= 5; n++) {
            n++;
            wasIntersection = false;
            for (int i = 0; i < models.size() - 1; i++)
                for (int j = i + 1; j < models.size(); j++) {
                    if (wasIntersection)
                        models.get(i).crossThem(models.get(j), deltaTime);
                    else
                        wasIntersection = models.get(i).crossThem(models.get(j), deltaTime);
                }
        }

        for (int i = 0; i < models.size() - 1; i++)
            for (int j = i + 1; j < models.size(); j++) {
                models.get(i).updateStaticForces(models.get(j), deltaTime);
            }

        for (Model model : models) {
            model.updateMotion(deltaTime);
            model.update(deltaTime);
        }
    }

    private void addAndRemoveModels() {

        for (int i=0; i<models.size(); i++) {
            if (models.get(i).getIsNoNeedMore())
            {
                if (!models.get(i).getIsComplex())
                    explode(models.get(i).getCenter(), models.get(i).getMaxWidth()*2);
                models.get(i).destroy();
                models.remove(i);
            }
        }

        if (addModelBuffer.size() > 0) {
            models.addAll(addModelBuffer);
            addModelBuffer.clear();
        }
    }

    private ArrayList<Point> explosionBuffer;
    private ArrayList<Float> explosionPowerBuffer;
    private void updateExplosions() {
        while (explosionBuffer.size() > 0) {
        for (Model m: models)
        {
            Point force=m.getCenter().negate().add(explosionBuffer.get(0)).negate();
            float l=force.length();
            l+=1;
            force=force.setLength(1);
            float power=explosionPowerBuffer.get(0);
            force=force.multiply(0.4f*power*power*power*power/l);
            m.useForce(m.getCenter(), force);
        }
        explosionBuffer.remove(0);
        explosionPowerBuffer.remove(0);
    }}

    private void updateEffects(float deltaTime) {
        for (int i=0; i<effects.size(); i++) {
            Effect p =effects.get(i);
            p.update(deltaTime);
            if (p.noNeedMore()) {
                effects.remove(i);
            }
        }
    }

    public void update(float deltaTime) {
        addAndRemoveModels();
        HUD.update(deltaTime);
        Camera.update(deltaTime);
    }

    public void updatePhysic(float deltaTime) {
        updateExplosions();
        updateEffects(deltaTime);
        updateModels(deltaTime);
        PlotBase.reCheck();
    }

    private SpaceShip player1;
    private ArrayList<SpaceShip> ships=new ArrayList<>();

    public SpaceShip getPlayerShip() {
        return player1;
    }

    public boolean getShipIsAlive(int number) {
          for (SpaceShip sh: ships)
              if (sh.getNumber()==number) return true;
        return false;
    }

    public void setPlayerModel(SpaceShip m) {
        player1=m;
    }

    public World() {

        effects=new LinkedList<>();
        ships = new ArrayList<>();

        models = new ArrayList<>();
        addModelBuffer = new LinkedList<>();

        explosionBuffer=new ArrayList<>();
        explosionPowerBuffer=new ArrayList<>();
    }

}
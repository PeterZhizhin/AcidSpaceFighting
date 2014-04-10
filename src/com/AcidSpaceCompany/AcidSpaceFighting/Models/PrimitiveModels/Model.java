package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Asteroid.AsteroidModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Base.BaseModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Bullet.BulletModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector.ConnectorModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine.EngineModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun.GunModel;

public class Model{

    private GraphicModel graphic;
    private PhysicModel physic;
    private static int lastNumber=0;
    private int number;
    private float linearSize;

    //ONLY FOR NETWORK-SYNCING
    public void setPositions(float x, float y, float w) {
        physic.setPositions(x, y, w);
    }

    //ONLY FOR NETWORK-SYNCING
    public void setSpeeds(float x, float y, float w) {
          physic.setSpeeds(x, y, w);
    }

    public String getSpeeds() {
        return physic.getSpeedX()+","+physic.getSpeedY()+","+physic.getSpeedW();
    }

    public String getPositions() {
        return physic.getCentre().x+","+physic.getCentre().y+","+physic.getAngle();
    }

    public int getType() {
        return -1;
    }

    public String toString() {
        return getType()+","+physic.getCentre().x+","+physic.getCentre().y+","+linearSize+","+
                physic.getAngle()+","+getSpeeds();
    }


    private boolean isSelected=false;

    public void select() {
        isSelected=true;
    }

    public void unselect() {
        isSelected=false;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public boolean getIsComplex()
    {
        return false;
    }

    public void setAngle(float f) {
        physic.setAngle(f);
    }

    public float getAngle() {
        return physic.getAngle();
    }

    /**
     * Получает модель по точке.
     * @param point Точка.
     * @return null - если в модели точка не содержится. Или модель, которой точка принадлежит
     */
    public Model getModelUnderPoint(Point point)
    {
       if (physic.containsPoint(point))
           return this;
        else
           return null;
    }

    public boolean containsPoint(Point point)
    {
        return physic.containsPoint(point);
    }

    public void moveGeometricModel(Point dS)
    {
        physic.moveGeometric(dS);
    }

    public float getMaxWidth() {
        return physic.getMaxWidth();
    }

    public boolean getIsNoNeedMore() {
        return physic.getIsNoNeedMore();
    }

    public void setIsNoNeedMore()
    {
        physic.setIsNoNeedMore();
    }

    public void destroy() {
        graphic.destroy();
        physic.destroy();
    }

    public GraphicModel getGraphicModel() {
        return graphic;
    }

    public PhysicModel getPhysicModel() {
        return physic;
    }

    public void useForce(Point where, Point toWhere) {
        physic.useForce(where, toWhere);
    }

    public void update(float time) {
        physic.update(time);
    }

    public void drawTopLayer() {
        graphic.drawTopLayer(isSelected);
    }

    public void drawConnections() {
        graphic.drawBackgroundLayer();
    }

    public boolean crossThem(Model p, float deltaTime) {
        return physic.crossThem(p.physic, deltaTime);
    }

    public void updateMotion(float deltaTime) {
        physic.updateMotion(deltaTime);
    }

    public void updateStaticForces(Model p, float deltaTime) {
        physic.applyStaticForces(p.physic, deltaTime);
    }

    public Point getCenter() {
        return physic.getCentre();
    }

    public void setPhysicModel(PhysicModel p) {
        p.setNumber(number);
        physic = p;
    }

    public void setGraphicModel(GraphicModel g) {
        graphic = g;
    }

    public void doSpecialAction() {
        physic.doSpecialAction();
    }

    public boolean getIsActive() {
        return physic.getActive();
    }

    public Model(GraphicModel g, PhysicModel p, float linearSize) {
        graphic = g;
        number=lastNumber;
        lastNumber++;
        if (p!=null)
            p.setNumber(number);
        physic = p;
        this.linearSize=linearSize;
    }

    public static Model getModel(float[] args) {
        int type= (int) args[0];

        switch (type) {
            case 0: return new AsteroidModel    (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 1: return new BulletModel      (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 2: return new BaseModel        (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 3: return new ConnectorModel   (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 4: return new GunModel         (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            case 5: return new EngineModel      (args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
        }
        return null;
    }
}

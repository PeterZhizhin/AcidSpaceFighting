package com.company;

import com.company.Models.Base.BaseModel;
import com.company.Models.Connector.ConnectorModel;
import com.company.Models.Engine.EngineModel;
import com.company.Physic.ComplexPhysicModel;

import javax.jws.WebParam;
import java.util.ArrayList;

/**
 * Created by peter on 17.02.14.
 */
public class SpaceShip {

    private ArrayList<Model> rocketEng=new ArrayList<Model>();
    private ArrayList<Model> rocketLeft=new ArrayList<Model>();
    private ArrayList<Model> rocketRight=new ArrayList<Model>();
    private ComplexModel totalModel;


    public float getX()
    {
        return totalModel.phyModel.getCentre().getX();
    }


    public float getY()
    {
        return totalModel.phyModel.getCentre().getY();
    }

    public void throttle()
    {
        for (Model m: rocketEng) m.doSpecialActionA();
    }

    public void turnLeft()
    {
        for (Model m: rocketLeft) m.doSpecialActionA();
    }
    public void turnRight()
    {
       for (Model m : rocketRight) m.doSpecialActionA();
    }

    public Model getBody()
    {
        return totalModel;
    }

    public SpaceShip(float x, float y)
    {
       float p2= -(float) (Math.PI/2);

        BaseModel base = new BaseModel(x, y, 1000);
        EngineModel bl=new EngineModel(-1000+x, y, 1000, p2);
        EngineModel br=new EngineModel(1000+x, y, 1000, p2);
        EngineModel bll=new EngineModel(-2000+x, y, 1000, p2);
        EngineModel brr=new EngineModel(2000+x, y, 1000, p2);
        rocketLeft.add(bl); rocketLeft.add(bll);
        rocketRight.add(br); rocketRight.add(brr);
        ConnectorModel cb=new ConnectorModel(x, 1000+y, 1000);
        ConnectorModel cbb=new ConnectorModel(x, 2000+y, 1000);
        EngineModel cbbb=new EngineModel(x, 3000+y, 1000, p2);
        EngineModel cbbbl=new EngineModel(-1000+x, 3000+y, 1000, p2);
        EngineModel cbbbr=new EngineModel(1000+x, 3000+y, 1000, p2);
        EngineModel cbbbll=new EngineModel(-2000+x, 3000+y, 1000, p2);
        EngineModel cbbbrr=new EngineModel(2000+x, 3000+y, 1000, p2);
        rocketEng.add(cbbb);
        rocketEng.add(cbbbl);
        rocketEng.add(cbbbr);
        rocketEng.add(cbbbll);
        rocketEng.add(cbbbrr);

        ComplexModel m = new ComplexModel(base);
        m.add(bl, 0);
        m.add(br, 0);
        m.add(bll, 0);
        m.add(brr, 0);
        m.add(cb, 0);
        m.add(cbb, 0);
        m.add(cbbb, 0);
        m.add(cbbbl, 0);
        m.add(cbbbr, 0);
        m.add(cbbbll, 0);
        m.add(cbbbrr, 0);

        totalModel = m;
    }

}

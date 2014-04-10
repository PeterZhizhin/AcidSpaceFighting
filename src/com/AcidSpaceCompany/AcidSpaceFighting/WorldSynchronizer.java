package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Network.Server;


public class WorldSynchronizer extends World{

    private Server s;

    public void addModel(Model m) {
        super.addModel(m);
        s.sendMessage("c"+m.toString());
    }

    public void addEffect(Effect p) {
        super.addEffect(p);
        s.sendMessage("e" + p);
    }

    protected void removeModel(int i) {
        super.removeModel(i);
        s.sendMessage("f" + i);
    }

    private String getSpeeds() {
        String s="";
        for (Model model : models) s += model.getSpeeds() + ",";
        return s;
    }

    private String getPositions() {
        String s="";
        for (Model model : models) s += model.getPositions() + ",";
        return s;
    }

    private float timer;
    public void update(float deltaTime) {
        super.update(deltaTime);
        s.sendMessage("a"+getSpeeds());

        if (timer<=0) {
            timer+=1f;
            s.sendMessage("b"+getPositions());
        }
        else timer-=deltaTime;
    }

    public WorldSynchronizer() {
        super();
        s=new Server(1234);
    }

}
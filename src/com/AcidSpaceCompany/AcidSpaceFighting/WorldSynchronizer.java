package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
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

        if (timer<=0) {
            timer+=0.05f;
            s.sendMessage("a"+getSpeeds());
            s.sendMessage("b"+getPositions());
            String activity=getActiveModels();
            if (activity.length()>0) {
                s.sendMessage("h" + activity);
            }
            else s.sendMessage("h-1");
        }
        else timer-=deltaTime;

        acceptActiveModels();
    }

    private void parseSyncMessage(String s) {
        if (s.length()>0) {
            char action = s.charAt(0);
            s = s.substring(1);
            String[] args = s.split(",");
            float[] argsFloat = new float[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    argsFloat[i] = Float.valueOf(args[i]);
                }
                catch (Exception e) {
                    //System.err.println("[WorldSynchronized] Error in parsing "+e);
                }
            }
            switch (action) {
                case 'a':
                    syncActivation(argsFloat);
            }
        }
    }

    private String getActiveModels() {
        String s = "";
        int modelNumber = 0;
        int num = 0;
        while (modelNumber < models.size()) {
            if (models.get(modelNumber).getIsComplex()) {
                ComplexModel c = (ComplexModel) models.get(modelNumber);
                for (int i = 0; i < c.getSize(); i++) {
                    if (c.getModel(i).getIsActive())
                        s += num + ",";
                    num++;
                }
                modelNumber++;
            } else {
                if (models.get(modelNumber).getIsActive())
                    s += num + ",";
                num++;
                modelNumber++;
            }
        }
        if (s.length() > 0)
            s = s.substring(0, s.length() - 1);
        return s;
    }

    public WorldSynchronizer() {
        super();
        s=new Server(1234);
        s.setOnInputEvent(() -> parseSyncMessage(s.getLastInputMessage()));
    }

}
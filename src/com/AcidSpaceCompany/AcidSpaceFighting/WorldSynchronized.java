package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Network.Client;

import java.util.Arrays;

public class WorldSynchronized extends World{

    Client s;

    private void syncSpeeds(float[] speeds) {
        int modelNumber=0;
        int speed=0;
        while (modelNumber<models.size()) {
            if (models.get(modelNumber).getIsComplex()) {
                ComplexModel c= (ComplexModel) models.get(modelNumber);
                for (int i=0; i<c.getSize(); i++) {
                    c.getModel(i).setSpeeds(speeds[speed], speeds[speed+1], speeds[speed+2]);
                    speed+=3;
                    modelNumber++;
                }
                c.recalculateModels();
            }
            else
            {
                models.get(modelNumber).setSpeeds(speeds[speed], speeds[speed+1], speeds[speed+2]);
                speed+=3;
                modelNumber++;
            }
        }
    }

    private void syncPositions(float[] speeds) {
        int modelNumber=0;
        int speed=0;
        while (modelNumber<models.size()) {
            if (models.get(modelNumber).getIsComplex()) {
                ComplexModel c= (ComplexModel) models.get(modelNumber);
                for (int i=0; i<c.getSize(); i++) {
                    c.getModel(i).setPositions(speeds[speed], speeds[speed+1], speeds[speed+2]);
                    speed+=3;
                    modelNumber++;
                }
                c.recalculateModels();
            }
            else
            {
                models.get(modelNumber).setPositions(speeds[speed], speeds[speed+1], speeds[speed+2]);
                speed+=3;
                modelNumber++;
            }
        }
    }

    public void parseSyncMessage(String s) {
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
                case 'a': syncSpeeds(argsFloat);
                    break;
                case 'b': syncPositions(argsFloat);
                    break;
                case 'c':
                    break;
                case 'd':
                    break;
                case 'e':
                    break;
                case 'f':
                    break;
                case 'g':
                    break;
                case 'z':
                    break;
            }

            //System.out.println("[WorldSynchronized] " + Arrays.toString(argsFloat) +"\n"+s);
        }
    }

    protected void updateModels(float deltaTime) {
        for (Model model : models) {
            model.updateMotion(deltaTime);
            model.update(deltaTime);
        }
    }

    public WorldSynchronized() {
        super();
        s=new Client("127.0.0.1", 1234);
        s.setOnInputEvent(() -> parseSyncMessage(s.getLastInput()));
    }

}
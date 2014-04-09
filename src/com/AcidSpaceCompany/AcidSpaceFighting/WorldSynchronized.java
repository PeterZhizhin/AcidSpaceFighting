package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Network.ClientConnection;

public class WorldSynchronized extends World{

    ClientConnection s;

    public void explode(Point center, float power) {
    }

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
                case 'b':
                    syncPositions(argsFloat);
                    break;
                case 'c':
                    addModelFromServer(Model.getModel(argsFloat));
                    break;
                case 'd':
                    break;
                case 'e':
                    break;
                case 'f': removeModelFromServer((int) argsFloat[0]);
                    break;
                case 'g':
                    break;
                case 'h':
                    break;
                case 'z':
                    break;
            }

            //System.out.println("[WorldSynchronized] " + Arrays.toString(argsFloat) +"\n"+s);
        }
    }


    private void removeModelFromServer(int num) {
        int modelNumber=0;
        while (modelNumber<models.size()) {
            if (models.get(modelNumber).getIsComplex()) {
                ComplexModel c= (ComplexModel) models.get(modelNumber);
                for (int i=0; i<c.getSize(); i++) {
                    if (modelNumber==num) {
                        c.getModel(i).setIsNoNeedMore();
                        return;
                    }
                    modelNumber++;
                }
                c.recalculateModels();
            }
            else
            {
                if (modelNumber==num) {
                    models.get(modelNumber).setIsNoNeedMore();
                    return;
                }
                modelNumber++;
            }
        }
    }

    public void addModelFromServer(Model m) {
        super.addModel(m);
    }

    public void addModel(Model m) {
    }

    protected void updateModels(float deltaTime) {
        for (Model model : models) {
            model.updateMotion(deltaTime);
            model.update(deltaTime);
        }
    }

    public WorldSynchronized() {
        super();
        s=new ClientConnection("127.0.0.1", 1234);
        s.setOnInputEvent(() -> parseSyncMessage(s.getLastInputMessage()));
    }

}
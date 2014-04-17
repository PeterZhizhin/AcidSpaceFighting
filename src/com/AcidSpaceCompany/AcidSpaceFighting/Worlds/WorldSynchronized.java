package com.AcidSpaceCompany.AcidSpaceFighting.Worlds;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Network.ClientConnection;


public class WorldSynchronized extends World {

    ClientConnection s;

    private void syncSpeeds(float[] speeds) {
        int speed = 0;
        for (Model model : models) {
            if (model.getIsComplex()) {
                ComplexModel c = (ComplexModel) model;
                for (int i = 0; i < c.getSize(); i++) {
                    c.getModel(i).setSpeeds(speeds[speed], speeds[speed + 1], speeds[speed + 2]);
                    speed += 3;
                }
                c.recalculateModels();
            } else {
                model.setSpeeds(speeds[speed], speeds[speed + 1], speeds[speed + 2]);
                speed += 3;
            }
        }
    }

    private void syncPositions(float[] speeds) {
        int speed = 0;
        for (Model model : models) {
            if (model.getIsComplex()) {
                ComplexModel c = (ComplexModel) model;
                for (int i = 0; i < c.getSize(); i++) {
                    c.getModel(i).setPositions(speeds[speed], speeds[speed + 1], speeds[speed + 2]);
                    speed += 3;
                }
                c.recalculateModels();
            } else {
                model.setPositions(speeds[speed], speeds[speed + 1], speeds[speed + 2]);
                speed += 3;
            }
        }
    }

    public void parseSyncMessage(String s) {
        if (s.length() > 0) {
            char action = s.charAt(0);
            s = s.substring(1);
            String[] args = s.split(",");
            float[] argsFloat = new float[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    argsFloat[i] = Float.valueOf(args[i]);
                } catch (Exception e) {
                    //System.err.println("[WorldSynchronized] Error in parsing "+e);
                }
            }
            switch (action) {
                case 'a':
                    syncSpeeds(argsFloat);
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
                case 'f':
                    removeModelFromServer((int) argsFloat[0]);
                    break;
                case 'g':
                    break;
                case 'h':
                    syncActivation(argsFloat);
                    break;
                case 'z':
                    break;
            }

            //System.out.println("[WorldSynchronized] " + Arrays.toString(argsFloat) +"\n"+s);
        }
    }

    private void removeModelFromServer(int num) {
        //todo: переделать, блджад, весь этот bullshit
        int modelNumber = 0;
        while (modelNumber < models.size()) {
            if (models.get(modelNumber).getIsComplex()) {
                ComplexModel c = (ComplexModel) models.get(modelNumber);
                for (int i = 0; i < c.getSize(); i++) {
                    if (modelNumber == num) {
                        c.getModel(i).setIsNoNeedMore();
                        return;
                    }
                    modelNumber++;
                }
                c.recalculateModels();
            } else {
                if (modelNumber == num) {
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

    private float timer;
    public void updateSyncros(float deltaTime) {
        if (timer<=0) {
            timer+=0.05f;
            String activity="";
            for (Integer m: activatedByUserModels) {
                activity+=m+",";
            }
            activatedByUserModels.clear();
            if (activity.length()>0) {
                activity=activity.substring(0, activity.length()-1);
                s.sendMessage("a" + activity);
            }
            else s.sendMessage("a-1");
        }
        else timer-=deltaTime;
    }

    public WorldSynchronized() {
        super();
        s = new ClientConnection("127.0.0.1", 1234);
        s.setOnInputEvent(() -> parseSyncMessage(s.getLastInputMessage()));
    }

    public void addActivatedByUserModel(int m) {
        activatedByUserModels.add(m);
    }

}
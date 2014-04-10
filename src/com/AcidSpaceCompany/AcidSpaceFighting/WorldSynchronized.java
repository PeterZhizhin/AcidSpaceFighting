package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Network.ClientConnection;

import java.util.LinkedList;


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

    private LinkedList<Model> activeModel = new LinkedList<>();

    private void syncActivation(float[] speeds) {

        activeModel.clear();
        System.out.println(activeModel.size());
        int speed = 0;
        int ls = 0;
        for (Model model : models) {
            if (model.getIsComplex()) {
                ComplexModel c = (ComplexModel) model;
                for (int i = 0; i < c.getSize(); i++) {
                    if (speed == (int) speeds[ls]) {
                        ls++;
                        activeModel.add(c.getModel(i));
                        if(ls>=speeds.length) break;
                    }
                    speed++;
                }
            } else {
                if (speed == (int) speeds[ls]) {
                    ls++;
                    activeModel.add(model);
                    if(ls>=speeds.length) break;
                }
                speed++;
            }

            if(ls>=speeds.length) break;
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

    synchronized private void updateActivations() {
        for (Model m: activeModel) {
            m.doSpecialAction();
        }
    }

    protected void updateModels(float deltaTime) {

        for (Model model : models) {
            model.updateMotion(deltaTime);
            model.update(deltaTime);
        }
        updateActivations();
    }

    public WorldSynchronized() {
        super();
        s = new ClientConnection("127.0.0.1", 1234);
        s.setOnInputEvent(() -> parseSyncMessage(s.getLastInputMessage()));
    }

}
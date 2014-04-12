package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.Worlds.OurWorld;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items.Bag;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items.Item;

public class DataWrapper {

    public static float getX() {
        return OurWorld.getPlayerShip().getCenter().getX();
    }

    public static float getY() {
        return OurWorld.getPlayerShip().getCenter().getY();
    }

    public static boolean getShipIsAlive(int shipNumber) {
        return OurWorld.getShipIsAlive(shipNumber);
    }

    public static Bag getBag() {
        return OurWorld.getPlayerShip().getBag();
    }

    public static boolean getConditionWasTrue(int conditionNumber) {
        return PlotBase.getConditionWasTrueInPast(conditionNumber);
    }

    public static void addItem(Item i) {
        OurWorld.getPlayerShip().getBag().add(i);
    }

    public static void spawnShip(float x, float y, float angle, int shipNumber) {

    }

    public static void spawnBlock(float x, float y, int type) {

    }

    public static void destroyShip(int nubmer) {

    }

    public static void addAchive(Achive a) {
        HUD.addAchive(a);
    }

    public static void changeStat(int type, float delta) {

    }

    public static void showMessage(Message d) {
        HUD.addAchive(d);
    }

    public static int showChoose(String[] variants) {
           return 0;
    }

    public static void enableEditor() {
        HUD.startEditor();
    }



}

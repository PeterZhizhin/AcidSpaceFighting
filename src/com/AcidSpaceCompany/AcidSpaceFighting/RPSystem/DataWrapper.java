package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items.Bag;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items.Item;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class DataWrapper {

    public static float getX() {
        return World.getPlayerShip().getCenter().getX();
    }

    public static float getY() {
        return World.getPlayerShip().getCenter().getY();
    }

    public static boolean getShipIsAlive(int shipNumber) {
        return World.getShipIsAlive(shipNumber);
    }

    public static Bag getBag() {
        return World.getPlayerShip().getBag();
    }

    public static boolean getConditionWasTrue(int conditionNumber) {
        return PlotBase.getConditionWasTrueInPast(conditionNumber);
    }

    public static void addItem(Item i) {
        World.getPlayerShip().getBag().add(i);
    }

    public static void spawnShip(float x, float y, float angle, int shipNumber) {

    }

    public static void spawnBlock(float x, float y, int type) {

    }

    public static void destroyShip(int nubmer) {

    }

    public static void addAchive(int number) {
        HUD.addAchive(null);
    }

    public static void changeStat(int type, float delta) {

    }

    public static void showMessage(Message d) {

    }

    public static int showChoose(String[] variants) {
           return 0;
    }




}

package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects.Effect;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.SpaceShip;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Plot;

public class OurWorld {

    private static World world;

    public static void explode(Point center, float power, float size) {
        world.explode(center, power, size);
    }

    public static void addEffect(Effect t) {
        world.addEffect(t);
    }

    public static void draw() {
        world.draw();
    }

    public static Model getModel(Point eventPosition) {
        return world.getModel(eventPosition);
    }

    public static void updatePhysic(float dt) {
        world.updatePhysic(dt);
    }

    public static void update(float dt) {
        world.update(dt);
    }

    public static void addModel(Model m) {
        world.addModel(m);
    }

    public static SpaceShip getPlayerShip() {
        return world.getPlayerShip();
    }

    public static boolean getShipIsAlive(int shipNumber) {
        return world.getShipIsAlive(shipNumber);
    }

    public static void initLocal() {
        Plot.init();
        world=new WorldLocal();

        SpaceShip player1 = new SpaceShip(0,0);
        world.addModel(player1);
        world.setPlayerModel(player1);


            SpaceShip player2 = new SpaceShip(2000, 0);
            world.addModel(player2);
    }

    public static void initServer() {
        Plot.init();
        world=new WorldSynchronizer();

        SpaceShip player1 = new SpaceShip(0,0);
        world.addModel(player1);
        world.setPlayerModel(player1);
    }

    public static void initClient() {
        Plot.init();

        WorldSynchronized w=new WorldSynchronized();
        world=w;

        SpaceShip player1 = new SpaceShip(0,0);
        w.addModelFromServer(player1);
        world.setPlayerModel(player1);
    }
}

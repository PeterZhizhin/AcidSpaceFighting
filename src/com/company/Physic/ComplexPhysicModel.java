package com.company.Physic;

import com.company.Geometry.Point;

import java.util.ArrayList;

public class ComplexPhysicModel extends PhysicModel{


    private ArrayList<PhysicModel> bodies;

    public void updateMotion(float deltaTime) {
        for (PhysicModel body: bodies)
            body.body.move(speedVector.multiply(deltaTime));
    }

    private void useForce(Point posOfForce, Point force) {
        speedVector.set(speedVector.getX()+force.getX(), speedVector.getX()+force.getY());
    }

    public void crossThem(ComplexPhysicModel m, int deltaTime) {
        for (PhysicModel body: bodies)
            crossWithGeometricModel(m, body.body, deltaTime);
    }

    public void add(PhysicModel g) {
        bodies.add(g);
    }

    public void remove(int num) {
        bodies.remove(num);
    }

    public ComplexPhysicModel() {
        super(null);
        bodies=new ArrayList<PhysicModel>();
        mass=1;
        speedVector=new Point(0, 0);
    }

}

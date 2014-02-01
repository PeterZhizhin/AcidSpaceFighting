package com.company.Physic;

import com.company.Geometry.Point;

import java.util.ArrayList;

public class ComplexPhysicModel extends PhysicModel{


    private ArrayList<PhysicModel> bodies;

    public void updateMotion(float deltaTime) {
        for (PhysicModel body: bodies)
            body.body.move(speedVector.multiply(deltaTime));
    }

    protected void useForce(Point posOfForce, Point force) {
        speedVector.move(force);
    }

    public void crossThem(ComplexPhysicModel m, float deltaTime) {
        for (PhysicModel body: bodies)
            crossWithGeometricModel(m, deltaTime);
    }

    public void add(PhysicModel g) {
        bodies.add(g);
    }

    public void remove(int num) {
        bodies.remove(num);
    }

    public ComplexPhysicModel() {
        super(null, 0);
        bodies=new ArrayList<PhysicModel>();
        mass=1;
        speedVector=new Point(0, 0);
    }

}

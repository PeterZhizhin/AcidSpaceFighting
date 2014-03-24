package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

public class StaticPhysicModel extends PhysicModel {

    private Trajectory trajectory;
    private float timeAccumulator;

    @Override
    public void useForce(Point force, Point position)
    {
        useDamage(force.length());
    }

    @Override
    public void update(float deltaTime)
    {
        timeAccumulator += deltaTime;
        Point newPosition = trajectory.getPoint(timeAccumulator);
        float newAngle = trajectory.getAngle(timeAccumulator);
        w = (newAngle - w) / deltaTime;
        speedVector = newPosition.add(speedVector.negate());
        speedVector.multiply(1.0f/deltaTime);
        moveTo(newPosition, newAngle);
    }

    private void moveTo(Point point, float angle) {
        body.moveTo(point);
        body.rotateTo(angle);
    }

    public StaticPhysicModel(GeometricModel geometricModel, float mass, Trajectory trajectory)
    {
        super(geometricModel, mass);
        moveTo(trajectory.getPoint(0), trajectory.getAngle(0));
        this.trajectory = trajectory;
        timeAccumulator = 0;
    }
}

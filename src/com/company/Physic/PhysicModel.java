package com.company.Physic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;

public class PhysicModel {

    private static final float G=1000f;//G is the gravitational constant

    protected GeometricModel body;
    protected float mass;
    protected Point speedVector;
    protected float damage; //if 1 - physicModel is noraml, if  0 - it is destroyed. Will used in model realisations.

    public void updateMotion(float deltaTime) {
        body.move(speedVector.multyply(deltaTime));
    }

    private void useForce(Point posOfForce, Point force, float deltaTime) {
        speedVector = speedVector.addVector(force.multyply(deltaTime));
    }

    protected void crossWithGeometricModel(PhysicModel m, GeometricModel body, float deltaTime) {
        {
            //gravitation
            double lengthBetweenCenters= body.getCenter().getDistanceToPoint(m.body.getCenter());
            double gravity=G*mass*m.mass/lengthBetweenCenters/lengthBetweenCenters;

            double dx=body.getCenter().getX()-m.body.getCenter().getX();
            double dy=body.getCenter().getY()-m.body.getCenter().getY();

            dx*=gravity;
            dy*=gravity;
            float x= (float) dx;
            float y= (float) dy;
            useForce(body.getCenter(), new Point(-x, -y), deltaTime);
            m.useForce(m.body.getCenter(), new Point(x, y), deltaTime);
        }


        Point intersection=body.getIntersection(m.body);
        if (intersection!=null) {

            //Поехавшая физика столкновений
            //Надо целиком перепилить
            //TODO: Перепилить физику столкновений
            double lengthBetweenCenters= body.getCenter().getDistanceToPoint(m.body.getCenter());
            double dx=body.getCenter().getX()-m.body.getCenter().getX();
            double dy=body.getCenter().getY()-m.body.getCenter().getY();
            dx/=lengthBetweenCenters;
            dy/=lengthBetweenCenters;

            double summarSpeed=Math.sqrt(speedVector.getX()*speedVector.getX()+speedVector.getY()*speedVector.getY())
                    +Math.sqrt(m.speedVector.getX()*m.speedVector.getX()+m.speedVector.getY()*m.speedVector.getY());
            summarSpeed/=4;

            dx*=summarSpeed;
            dy*=summarSpeed;
            float x= (float) dx;
            float y= (float) dy;
            useForce(body.getCenter(), new Point(x, y), deltaTime);
            m.useForce(m.body.getCenter(), new Point(-x, -y), deltaTime);
        }
    }

    public void crossThem(PhysicModel m, float deltaTime) {
        crossWithGeometricModel(m, body, deltaTime);
    }

    public PhysicModel(GeometricModel body) {
        this.body=body;
        mass=1;
        speedVector=new Point(0, 0);
    }

}

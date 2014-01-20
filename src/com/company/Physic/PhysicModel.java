package com.company.Physic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;

public class PhysicModel {

    private static final float G=1f;//G is the gravitational constant

    protected GeometricModel body;
    protected float mass;
    protected Point speedVector;
    protected float damage; //if 1 - physicModel is noraml, if  0 - it is destroyed. Will used in model realisations.

    public void updateMotion(float deltaTime) {
        body.move(speedVector.multiply(deltaTime));
    }

    private void useForce(Point posOfForce, Point force, float deltaTime) {
        speedVector.move(force.multiply(deltaTime));
    }

    protected void crossWithGeometricModel(PhysicModel m, GeometricModel body, float deltaTime) {
        {
            //gravitation
            double lengthBetweenCenters= body.getCentre().getDistanceToPoint(m.body.getCentre());
            double gravity=G*mass*m.mass/lengthBetweenCenters/lengthBetweenCenters;

            double dx=body.getCentre().getX()-m.body.getCentre().getX();
            double dy=body.getCentre().getY()-m.body.getCentre().getY();

            dx*=gravity;
            dy*=gravity;
            float x= (float) dx;
            float y= (float) dy;
            useForce(body.getCentre(), new Point(-x, -y), deltaTime);
            m.useForce(m.body.getCentre(), new Point(x, y), deltaTime);
        }


        Point intersection=body.getIntersection(m.body);
        if (intersection!=null) {

            //Поехавшая физика столкновений
            //Надо целиком перепилить
            //TODO: Перепилить физику столкновений
            double lengthBetweenCenters= body.getCentre().getDistanceToPoint(m.body.getCentre());
            double dx=body.getCentre().getX()-m.body.getCentre().getX();
            double dy=body.getCentre().getY()-m.body.getCentre().getY();
            dx/=lengthBetweenCenters;
            dy/=lengthBetweenCenters;

            double summarSpeed=Math.sqrt(speedVector.getX()*speedVector.getX()+speedVector.getY()*speedVector.getY())
                    +Math.sqrt(m.speedVector.getX()*m.speedVector.getX()+m.speedVector.getY()*m.speedVector.getY());
            summarSpeed/=4;

            dx*=summarSpeed;
            dy*=summarSpeed;
            float x= (float) dx;
            float y= (float) dy;
            useForce(body.getCentre(), new Point(x, y), deltaTime);
            m.useForce(m.body.getCentre(), new Point(-x, -y), deltaTime);
        }
    }

    public void crossThem(PhysicModel m, float deltaTime) {
        crossWithGeometricModel(m, body, deltaTime);
    }

    public PhysicModel(GeometricModel body) {
        this.body=body;
        mass=30;
        speedVector=new Point(0, 0);
    }

}

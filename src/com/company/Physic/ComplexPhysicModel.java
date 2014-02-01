package com.company.Physic;

import com.company.Geometry.Point;

import java.util.ArrayList;
import java.util.LinkedList;

public class ComplexPhysicModel extends PhysicModel{


    private ArrayList<PhysicModel> bodies;
    private boolean[][] adjacencyMatrix;

    private Point massCentre;
    //Интерфейс, вызываемый когда какое-то тело приложило силу
    private ForceInterface forceInterface = new ForceInterface() {
        @Override
        public void applyForce(Point posOfForce, Point force) {
            acceleration.move(force.multiply(1.0f/mass));
            //speedVector.move(force.multiply(deltaTime/mass));

            if (massCentre.getDistanceToPoint(posOfForce)>=Point.epsilon)
            {
                double deltaBeta = force.getLength()/J*Point.получитьРасстояниеОтТочкиДоПрямойБесплатноБезСМСБезРегистрации
                        (massCentre, posOfForce, Point.add(posOfForce, force));
                //Получаем знак
                //Если конец вектора лежит в правой полуплоскости относительно прямой, проходящей через центр масс и точку приложения силы
                //То вращается вправо (знак минус), иначе влево
                if (Point.getDirection(Point.add(posOfForce, force), massCentre, posOfForce)) {
                    deltaBeta = -deltaBeta;
                }
                beta += deltaBeta;
            }
        }
    };

    @Override
    public void applyStaticForces(PhysicModel m, float deltaTime)
    {

    }

    @Override
    public void updateMotion(float deltaTime)
    {
        Point dS = getMoveVector(deltaTime);
        float angle = getRotationAngle(deltaTime);
        for (PhysicModel body : bodies)
        {
            body.body.rotate(massCentre, angle);
            body.body.move(dS);
        }
        updateKinematic(deltaTime);
    }

    @Override
    protected void updateKinematic(float deltaTime)
    {
        super.updateKinematic(deltaTime);
        for (PhysicModel body : bodies)
        {
            body.speedVector = speedVector;
            body.w = this.w;
            body.acceleration = new Point(0,0);
            //body.centreOfRotation = new Point(massCentre);
            body.beta = 0.0f;
        }
    }

    //TODO: Припилить столкновение для комплексных физических моделей
    @Override
    public boolean crossThem(PhysicModel m, float deltaTime)
    {
        return false;
    }

    private LinkedList<PhysicModel> getComponents()
    {
        LinkedList<PhysicModel> result = new LinkedList<PhysicModel>();
        boolean[] visits = new boolean[adjacencyMatrix.length];
        for (int i=0; i<visits.length; i++)
            visits[i]=false;
        for (int i=0; i<visits.length; i++)
            if (!visits[i])
            {
                result.add(bodies.get(i));
                fillAllConnected(i, visits, adjacencyMatrix);
            }
        return result;
    }
    private void fillAllConnected(int index, boolean[] wasVisited, boolean[][] adjacencyMatrix)
    {
        wasVisited[index] = true;
        for (int i=0; i<wasVisited.length; i++)
            if (adjacencyMatrix[index][i])
                fillAllConnected(i, wasVisited, adjacencyMatrix);
    }


    public ComplexPhysicModel(ArrayList<PhysicModel> bodies, boolean[][] adjacencyMatrix) {
        super(null, 0);
        this.bodies = bodies;
        this.adjacencyMatrix = adjacencyMatrix;
        if (getComponents().size()!=1)
            throw new IllegalArgumentException("Adjacency matrix should have only one connected component");
        mass = 0;
        massCentre = new Point(0,0);
        speedVector = new Point(0,0);
        J = 0;
        for (PhysicModel body : this.bodies)
        {
            body.setForceInterface(forceInterface);
            mass+=body.mass;
            massCentre.move(body.getCenter().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f/mass);
        speedVector = speedVector.multiply(1.0f/mass);
        for (PhysicModel body : this.bodies)
        {
            J+=body.J; J+=body.mass*body.getCenter().getLengthSquared(this.massCentre);
        }
        acceleration = new Point(0,0);
    }

}
package com.company.Physic;

import com.company.Geometry.Point;
import com.company.Geometry.Segment;

import java.util.ArrayList;
import java.util.LinkedList;

public class ComplexPhysicModel extends PhysicModel{


    //Массив тел в модели
    private ArrayList<PhysicModel> bodies;
    //Матрица смежности тел, входящих в систему (жесткие связи между телами в система)
    private boolean[][] adjacencyMatrix;

    //Центр масс системы
    private Point massCentre;
    //Центр теперь определяется не центром геометрической модели, а центром масс системы
    //Кстати говоря: ускорения, скорости теперь тоже определяются центром масс системы
    @Override
    public Point getCentre()
    {
        return massCentre;
    }

    //Статические силы теперь тоже применяются ко всей системе
    @Override
    public void applyStaticForces(PhysicModel m, float deltaTime)
    {
        for (PhysicModel body : bodies)
            body.applyStaticForces(m, deltaTime);
    };

    //Здесь нужно передать изменения всей системе тел
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

    //А здесь сбросить угловые ускорения и скорости у тел
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

    //TODO: Припилить столкновение для: ComplexPhysicModel & ComplexPhysicModel; ComplexPhysicModel & PhysicModel
    /**
     * Здесь должна была быть обработка столкновений
     * @param m
     * @param deltaTime
     * @return
     */
    @Override
    public boolean crossThem(PhysicModel m, float deltaTime)
    {
        return false;
    }

    /**
     * Получем число компонент связностей в графе связности
     * Будет использоваться при выделении тел в две комплексные физические модели при отделении тел
     * @return Список тел из разных компонент связностей
     */
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

    /**
     * Рекурсивный обход в ширину для матрицы связности
     * @param index Индекс начала обхода
     * @param wasVisited  Массив посещенных вершин
     * @param adjacencyMatrix  Матрица связности
     */
    private void fillAllConnected(int index, boolean[] wasVisited, boolean[][] adjacencyMatrix)
    {
        wasVisited[index] = true;
        for (int i=0; i<wasVisited.length; i++)
            if (!adjacencyMatrix[index][i])
                fillAllConnected(i, wasVisited, adjacencyMatrix);
    }

    private ArrayList<Segment> forceBuffer;
    public void addForce(Segment s) {
        forceBuffer.add(s);
    }

    /**
     * Создание комплексной физической модели.
     * @param bodies  Тела, входящие в систему
     * @param adjacencyMatrix   Матрица смежности. Длина матрицы должна совпадать с количеством тел в системе! Компонента у графа должна быть одна!
     */
    public ComplexPhysicModel(ArrayList<PhysicModel> bodies, boolean[][] adjacencyMatrix) {
        super(null, 0);
        this.bodies = bodies;
        this.adjacencyMatrix = adjacencyMatrix;
        //if (bodies.size() != adjacencyMatrix.length | getComponents().size()!=1)
        //    throw new IllegalArgumentException("Adjacency matrix should have only one connected component. And number of bodies and points in graph should be the same");
        mass = 0;
        massCentre = new Point(0,0);
        speedVector = new Point(0,0);
        J = 0;
        for (PhysicModel body : this.bodies)
        {
            mass+=body.mass;
            massCentre.move(body.getCentre().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f/mass);
        speedVector = speedVector.multiply(1.0f/mass);
        for (PhysicModel body : this.bodies)
        {
            J+=body.J; J+=body.mass*body.getCentre().getLengthSquared(this.massCentre);
        }
        acceleration = new Point(0,0);
    }

}
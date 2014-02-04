package com.company.Physic;

import com.company.Geometry.Point;

import java.util.ArrayList;
import java.util.LinkedList;

public class ComplexPhysicModel extends PhysicModel {

    public boolean getIsComplex() {
        return true;
    }

    //Массив тел в модели
    private ArrayList<PhysicModel> bodies;
    //Матрица смежности тел, входящих в систему (жесткие связи между телами в система)
    private boolean[][] adjacencyMatrix;

    //Центр масс системы
    private Point massCentre;

    //Центр теперь определяется не центром геометрической модели, а центром масс системы
    //Кстати говоря: ускорения, скорости теперь тоже определяются центром масс системы
    @Override
    public Point getCentre() {
        return massCentre;
    }

    @Override
    public int getConnectionPointsCount() {
        int result = 0;
        for (PhysicModel body : bodies)
            result += body.getConnectionPointsCount();
        return result;
    }

    @Override
    public Point getConnectionPoint(int index) {
        int bodyNo = 0;
        while (index >= 0) {
            index -= bodies.get(bodyNo).getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        index += bodies.get(bodyNo).getConnectionPointsCount();
        return bodies.get(bodyNo).getConnectionPoint(index);
    }

    @Override
    public boolean getIsConnectionPointFree(int index) {
        int bodyNo = 0;
        while (index >= 0) {
            index -= bodies.get(bodyNo).getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        index += bodies.get(bodyNo).getConnectionPointsCount();
        return bodies.get(bodyNo).getIsConnectionPointFree(index);
    }

    private int getBodyByIndex(int index) {
        int bodyNo = 0;
        while (index >= 0) {
            index -= bodies.get(bodyNo).getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        return bodyNo;
    }

    //Статические силы теперь тоже применяются ко всей системе
    @Override
    public void applyStaticForces(PhysicModel m, float deltaTime) {
        for (PhysicModel body : bodies)
            body.applyStaticForces(m, deltaTime);
    }

    //Здесь нужно передать изменения всей системе тел
    @Override
    public void updateMotion(float deltaTime) {
        Point dS = getMoveVector(deltaTime);
        float angle = getRotationAngle(deltaTime);
        for (PhysicModel body : bodies) {
            body.body.rotate(massCentre, angle);
            body.body.move(dS);
        }
        updateKinematic(deltaTime);
    }

    //А здесь сбросить угловые ускорения и скорости у тел
    @Override
    protected void updateKinematic(float deltaTime) {
        super.updateKinematic(deltaTime);
        for (PhysicModel body : bodies) {
            body.speedVector = speedVector;
            body.w = this.w;
            body.acceleration = new Point(0, 0);
            body.centreOfRotation = new Point(massCentre);
            body.beta = 0.0f;
        }
    }

    //TODO: Припилить столкновение для: ComplexPhysicModel & ComplexPhysicModel; ComplexPhysicModel & PhysicModel

    /**
     * Здесь должна была быть обработка столкновений
     *
     * @param m
     * @param deltaTime
     * @return
     */
    @Override
    public boolean crossThem(PhysicModel m, float deltaTime) {
        return false;
    }

    public boolean crossThem(ComplexPhysicModel m, float deltaTime) {
        return false;
    }

    /**
     * Получем число компонент связностей в графе связности
     * Будет использоваться при выделении тел в две комплексные физические модели при отделении тел
     *
     * @return Список тел из разных компонент связностей
     */
    private LinkedList<PhysicModel> getComponents() {
        LinkedList<PhysicModel> result = new LinkedList<PhysicModel>();
        boolean[] visits = new boolean[adjacencyMatrix.length];
        for (int i = 0; i < visits.length; i++)
            visits[i] = false;
        for (int i = 0; i < visits.length; i++)
            if (!visits[i]) {
                result.add(bodies.get(i));
                fillAllConnected(i, visits, adjacencyMatrix);
            }
        return result;
    }

    /**
     * Рекурсивный обход в ширину для матрицы связности
     *
     * @param index           Индекс начала обхода
     * @param wasVisited      Массив посещенных вершин
     * @param adjacencyMatrix Матрица связности
     */
    private void fillAllConnected(int index, boolean[] wasVisited, boolean[][] adjacencyMatrix) {
        wasVisited[index] = true;
        for (int i = 0; i < wasVisited.length; i++)
            if (!adjacencyMatrix[index][i])
                fillAllConnected(i, wasVisited, adjacencyMatrix);
    }

    /*private ArrayList<Segment> forceBuffer;
    public void addForce(Segment s) {
        forceBuffer.add(s);
    }*/
    @Override
    public void useForce(Point posOfForce, Point force) {
        acceleration.move(force.multiply(1.0f / mass));
        //speedVector.move(force.multiply(deltaTime/mass));

        if (massCentre.getDistanceToPoint(posOfForce) >= Point.epsilon) {
            double deltaBeta = force.getLength() / J * Point.получитьРасстояниеОтТочкиДоПрямойБесплатноБезСМСБезРегистрации
                    (massCentre, posOfForce, posOfForce.add(force));
            //Получаем знак
            //Если конец вектора лежит в правой полуплоскости относительно прямой, проходящей через центр масс и точку приложения силы
            //То вращается вправо (знак минус), иначе влево
            if (Point.getDirection(posOfForce.add(force), massCentre, posOfForce)) {
                deltaBeta = -deltaBeta;
            }
            beta += deltaBeta;
        }
    }

    public void пересчитатьВсякиеТамЦентрыМассИПрочуюХрень () {
        massCentre = new Point(0, 0);
        speedVector = new Point(0, 0);
        mass = 0;
        J = 0;
        for (PhysicModel body : this.bodies) {
            mass += body.mass;
            massCentre.move(body.getCentre().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f / mass);
        speedVector = speedVector.multiply(1.0f / mass);
        for (PhysicModel body : this.bodies) {
            J += body.J;
            J += body.mass * body.getCentre().getLengthSquared(this.massCentre);
            body.setParent(this);
        }
        acceleration = new Point(0, 0);
    }

    public void setBase(PhysicModel p) {
        bodies.clear();
        bodies.add(p);
        adjacencyMatrix = new boolean[1][1];
        adjacencyMatrix[0][0] = true;

        пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
    }

    public void add(PhysicModel p, int addPointIndex) {
        int bodyIndex = getBodyByIndex(addPointIndex);
        bodies.add(p);
        boolean[][] tempAdjacency = new boolean[adjacencyMatrix.length][adjacencyMatrix[0].length];

        for (int i = 0; i < tempAdjacency.length; i++)
            for (int j = 0; j < tempAdjacency[i].length; j++)
                tempAdjacency[i][j] = adjacencyMatrix[i][j];
        adjacencyMatrix = new boolean[adjacencyMatrix.length + 1][adjacencyMatrix[1].length + 1];

        for (int i = 0; i < tempAdjacency.length; i++)
            for (int j = 0; j < tempAdjacency[i].length; j++)
                adjacencyMatrix[i][j] = tempAdjacency[i][j];

        int newBodyIndex = adjacencyMatrix.length - 1;
        adjacencyMatrix[newBodyIndex][newBodyIndex] = true;
        adjacencyMatrix[bodyIndex][newBodyIndex] = true;
        adjacencyMatrix[newBodyIndex][bodyIndex] = true;

        пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
    }

    public ComplexPhysicModel() {
        super(null, 0);
        bodies = new ArrayList<PhysicModel>();
        adjacencyMatrix = new boolean[0][0];
    }

}
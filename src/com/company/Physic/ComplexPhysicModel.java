package com.company.Physic;

import com.company.ComplexModel;
import com.company.Geometry.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ComplexPhysicModel extends PhysicModel {

    public boolean getIsComplex() {
        return true;
    }

    //Массив тел в модели
    private BodiesList bodies;
    //Максимальное их число
    private static final int maxBodiesSize = 10;

    //Центр масс системы
    private Point massCentre;

    private ComplexModel cm;

    //Центр теперь определяется не центром геометрической модели, а центром масс системы
    //Кстати говоря: ускорения, скорости теперь тоже определяются центром масс системы

    @Override
    public Point getCentre() {
        return massCentre;
    }

    @Override
    public int getConnectionPointsCount() {
        return bodies.getConnectionPointsCount();
    }

    @Override
    public Point getConnectionPoint(int index) {
        return bodies.getConnectionPoint(index);
    }

    @Override
    public boolean getIsConnectionPointFree(int index) {
        return bodies.getIsConnectionPointFree(index);
    }

    //Статические силы теперь тоже применяются ко всей системе
    @Override
    public void applyStaticForces(PhysicModel m, float deltaTime) {
        Iterator<PhysicModel> iterator = bodies.iterator();
        while (iterator.hasNext())
            iterator.next().applyStaticForces(m, deltaTime);
    }

    /**
     * Здесь мы будем удалять те тела, что с отрицательным здоровьем.
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        Iterator<PhysicModel> iterator = bodies.iterator();
        while (iterator.hasNext())
            iterator.next().update(deltaTime);

        boolean wasDeleted = false;
        iterator = bodies.iterator();
        while (iterator.hasNext())
        {
            PhysicModel body = iterator.next();
            if (body.getHealth()<0)
            {
                System.out.println("HEALTH: " + Float.toString(body.getHealth()));
                iterator.remove();
                wasDeleted = true;
            }
        }
        if (wasDeleted)
                пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
    }

    //Здесь нужно передать изменения всей системе тел
    @Override
    public void updateMotion(float deltaTime) {
        Point dS = getMoveVector(deltaTime);
        massCentre.move(dS);
        float angle = getRotationAngle(deltaTime);
        Iterator<PhysicModel> iterator = bodies.iterator();
        while (iterator.hasNext()) {
            PhysicModel body = iterator.next();
            body.rotate(massCentre, angle);
            body.move(dS);
        }
        updateKinematic(deltaTime);
    }

    //А здесь сбросить угловые ускорения и скорости у тел
    @Override
    protected void updateKinematic(float deltaTime) {
        super.updateKinematic(deltaTime);
        Iterator<PhysicModel> iterator = bodies.iterator();
        while (iterator.hasNext()) {
            PhysicModel body = iterator.next();
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


    @Override
    public void useForce(Point posOfForce, Point force) {
        acceleration.move(force.multiply(1.0f / mass));
        //speedVector.move(force.multiply(deltaTime/mass));

        if (massCentre.getDistanceToPoint(posOfForce) >= Point.epsilon) {
            double length = Point.getLengthToLine(massCentre, posOfForce, posOfForce.add(force));
            double deltaBeta = force.length() * length / J;
            //Получаем знак
            //Если конец вектора лежит в правой полуплоскости относительно прямой, проходящей через центр масс и точку приложения силы
            //То вращается вправо (знак минус), иначе влево

            //System.out.println(n.toString() + " " + new Float(deltaBeta).toString());
            beta += deltaBeta;
        }
    }

    public void пересчитатьВсякиеТамЦентрыМассИПрочуюХрень () {
        massCentre = new Point(0, 0);
        speedVector = new Point(0, 0);
        mass = 0;
        J = 0;
        Iterator<PhysicModel> iterator = bodies.iterator();
        while (iterator.hasNext()) {
            PhysicModel body = iterator.next();
            mass += body.mass;
            massCentre.move(body.getCentre().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f / mass);
        speedVector = speedVector.multiply(1.0f / mass);
        iterator = bodies.iterator();
        while (iterator.hasNext()) {
            PhysicModel body = iterator.next();
            J += body.J;
            J += body.mass * body.getCentre().getLengthSquared(this.massCentre);
            body.setParent(this);
        }
        acceleration = new Point(0, 0);
        this.beta = 0;
    }

    public void setBase(PhysicModel p) {
        bodies = new BodiesList(maxBodiesSize, p);
    }

    public void add(PhysicModel p, int addPointIndex) {
        bodies.add(p, addPointIndex);
        пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
    }

    public ComplexPhysicModel(ComplexModel c, PhysicModel firstModel) {
        super(null, 0);
        bodies = new BodiesList(maxBodiesSize, firstModel);
        cm=c;
    }

}
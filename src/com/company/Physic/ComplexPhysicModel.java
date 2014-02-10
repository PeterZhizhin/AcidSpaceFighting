package com.company.Physic;

import com.company.ComplexModel;
import com.company.Geometry.Point;
import com.company.World;

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
    //private static final int maxBodiesSize = 10;   //Для отладки
    private static final int maxBodiesSize = 1024;   //Реальное

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
        for(PhysicModel body : bodies)
            body.applyStaticForces(m,deltaTime);
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
        for (int i=0; i<bodies.getSize(); i++)
            if (bodies.get(i).getHealth()<0)
            {
                bodies.remove(i);
                cm.graModel.remove(i);
                wasDeleted = true;
            }
        if (wasDeleted)
        {
            boolean needRecalculation = bodies.recalculateMatrix()!=1;
            if (needRecalculation)
            {
                BodiesList[] bodiesLists = bodies.getComponents();
                this.bodies = bodiesLists[0];
            }
            пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
        }
    }

    //Здесь нужно передать изменения всей системе тел
    @Override
    public void updateMotion(float deltaTime) {
        Point dS = getMoveVector(deltaTime);
        massCentre.move(dS);
        float angle = getRotationAngle(deltaTime);
        for (int i = 0; i<bodies.getSize(); i++)
        {
            bodies.get(i).rotate(massCentre, angle);
            bodies.get(i).move(dS);
        }
        updateKinematic(deltaTime);
    }

    //А здесь сбросить угловые ускорения и скорости у тел
    @Override
    protected void updateKinematic(float deltaTime) {
        super.updateKinematic(deltaTime);
        for (PhysicModel body : bodies)
        {
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
        for (PhysicModel body : bodies)
        {
            mass += body.mass;
            massCentre.move(body.getCentre().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f / mass);
        speedVector = speedVector.multiply(1.0f / mass);
        for (PhysicModel body : bodies)
        {
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

    private ComplexPhysicModel(BodiesList list)
    {
        super(null, 0);
        bodies = list;
        пересчитатьВсякиеТамЦентрыМассИПрочуюХрень();
    }

    public ComplexPhysicModel(ComplexModel c, PhysicModel firstModel) {
        super(null, 0);
        bodies = new BodiesList(maxBodiesSize, firstModel);
        cm=c;
    }

}
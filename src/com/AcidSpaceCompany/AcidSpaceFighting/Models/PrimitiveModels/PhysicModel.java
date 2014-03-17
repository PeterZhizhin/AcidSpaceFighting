package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.BasicWindow;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Segment;
import org.lwjgl.util.vector.Vector3f;

import java.util.LinkedList;


public class PhysicModel {

    private static final double G = 10f;//G is the gravitational constant

    protected GeometricModel body;
    protected float activity = 0f;
    protected float mass;
    protected Point speedVector;
    protected Point acceleration;
    protected ComplexPhysicModel parent;
    protected int number;

    private Point[] connectionPoints;
    private boolean[] isConnectionFree;

    /**
     * Выясняет, содержит ли данная физическая модель данную точку
     * @param point Точка для проверки
     * @return true - содержит. false - нет.
     */
    public boolean containsPoint(Point point)
    {
       return body.containsPoint(point);
    }

    public void moveGeometric(Point dS)
    {
        body.move(dS);
    }

    public void destroy() {
        //if somebody need it
    }

    public float getDamage(float force) {
        return force/40000000000f;
    }

    public float getMaxWidth() {
        return body.getMaxLength();
    }
    public float getConnectionDistance()
    {
        return body.getConnectionDistance();
    }

    public boolean getIsNoNeedMore() {
        return health<=0;
    }

    public void setNumber(int num) {
        number=num;
    }

    public float getActivity() {
        return activity;
    }

    public boolean getIsComplex() {
        return false;
    }

    //Да, по-идее, все эти величины нужно делать трехмерными векторами.
    //Но у нас же плоскопараллельное движение вдоль плоскости xOy
    //Угловая скорость
    protected float w;
    //Угловое ускорение
    protected float beta;
    protected Point centreOfRotation;
    //Момент инерции
    protected float J;


    protected float health =1; //if 1 - physicModel is normal, if  0 - it is destroyed. Will used in model realisations.

    public float getHealth() {
        return health;
    }

    public float getSpeedX() {
        return speedVector.getX();
    }

    public float getSpeedY() {
        return speedVector.getY();
    }

    protected void addSpeed(Point dV)
    {
        speedVector.move(dV);
    }

    public void doSpecialActionA() {
    }

    protected void rotate(Point centre, float angle) {
        body.rotate(centre, angle);
        for (Point connectionPoint : connectionPoints)
            connectionPoint.rotate(angle, centre);
    }

    protected void move(Point dS) {
        body.move(dS);
        for (Point connectionPoint : connectionPoints)
            connectionPoint.move(dS);
    }

    public void rotate(float s) {
        body.rotate(s);
    }

    public void updateMotion(float deltaTime) {
        body.rotate(getRotationAngle(deltaTime));
        body.move(getMoveVector(deltaTime));
        centreOfRotation = getCentre();
        updateKinematic(deltaTime);
    }

    //for their own timers
    public void update(float deltaTime) {
        //НЕ НАДО СЮДА НИЧЕГО ДОБАВЛЯТЬ
        //МЕТОД ИСКЛЮЧИТЕЛЬНО ДЛЯ КОНКРЕТНЫХ РЕАЛИЗАЦИЙ МОДЕЛИ
    }

    /**
     * Считаем что ускорение на данном участке постоянно и считаем перемещение по формуле
     * S = V0*dT + A*dT^2/2
     *
     * @param deltaTime Время которое  перемещались
     * @return Вектор перемещения
     */
    public Point getMoveVector(float deltaTime) {
        return speedVector.multiply(deltaTime).add(acceleration.multiply(deltaTime * deltaTime / 2.0f));
    }

    /**
     * Получаем угол поворота по формуле для равноускоренного движения
     *
     * @param deltaTime Время которое перемещались
     * @return Угол поворота
     */
    public float getRotationAngle(float deltaTime) {
        return w * deltaTime + beta * deltaTime * deltaTime / 2.0f;
    }

    /**
     * Обновляем кинематические переменные (скорости и ускорения)
     * Скорости меняем, ускорения обнуляем
     *
     * @param deltaTime Время действия
     */
    protected void updateKinematic(float deltaTime) {
        speedVector.move(acceleration.multiply(deltaTime));
        acceleration = new Point(0, 0);
        w += beta * deltaTime;
        beta = 0;
    }

    /**
     * Применяем силу к телу для изменения ускорений (угловых и линейных)
     *
     * @param posOfForce Точка приложения силы TODO: переделать на Segment
     * @param force      Сама сила
     */
    public void useForce(Point posOfForce, Point force) {
        if (parent == null) {
            acceleration.move(force.multiply(1.0f / mass));
            //speedVector.move(force.multiply(deltaTime/mass));

            if (body.getCentre().getDistanceToPoint(posOfForce) >= Point.epsilon) {
                double deltaBeta = force.length() * Point.getLengthToLine
                        (body.getCentre(), posOfForce, posOfForce.add(force)) / J;
                beta += deltaBeta;
            }

        } else
            parent.useForce(posOfForce, force);
        health-=getDamage(force.length());
    }

    /**
     * Получение списка тел для обработки столкновений
     * @return Список, состоящий из всех тел системы
     */
    protected LinkedList<PhysicModel> getBodies()
    {
        LinkedList<PhysicModel> result = new LinkedList<PhysicModel>();
        result.add(this);
        return result;
    }

    protected float getForce(Point position, Point normal, PhysicModel anotherBody, float energyLess, float deltaTime)
    {
        Point r1 = new Point(position); r1.add(this.getCentre().negate());
        Point r2 = new Point(position); r2.add(anotherBody.getCentre().negate());
        Point vR = new Point(speedVector); vR.add(anotherBody.getCentre().negate());
        Vector3f cross1 = new Vector3f(r1.getRealVector3f());
        Vector3f.cross(cross1, normal.getRealVector3f(), cross1);
        cross1.scale(1.0f/J);
        Vector3f.cross(cross1, r1.getRealVector3f(), cross1);

        Vector3f cross2 = new Vector3f(r2.getRealVector3f());
        Vector3f.cross(cross2, normal.getRealVector3f(), cross2);
        cross2.scale(1.0f/anotherBody.J);
        Vector3f.cross(cross2, r2.getRealVector3f(), cross2);

        return (vR.length()*(energyLess+1))/(1.0f/mass + 1.0f/anotherBody.mass + cross1.length() + cross2.length())/deltaTime;
    }

    private boolean applyIntersection(PhysicModel m, float deltaTime) {
        boolean wasIntersection = false;
        GeometricModel g1 = new GeometricModel(body);
        GeometricModel g2 = new GeometricModel(m.body);

        g1.rotate(centreOfRotation, getRotationAngle(deltaTime));
        g2.rotate(centreOfRotation, m.getRotationAngle(deltaTime));
        g1.move(getMoveVector(deltaTime));
        g2.move(m.getMoveVector(deltaTime));

        Segment intersection = g1.getIntersection(g2);
        Segment tempIntersection = null;

        //Далее идет куча закомментированного кода, это все для отладочного вывода
        if (intersection != null) {
            //System.out.println("1");
            tempIntersection = body.getIntersection(m.body);
            //if (tempIntersection!=null)
            //    System.out.println("2");
        }

        if (intersection != null && tempIntersection == null) {
            wasIntersection = true;

            //Физика столкновений на основе законов сохранения импульсов и энергии

            //Продолжим. Формул нет нигде, вывел сам, хоть они и общеизвестные.
            //Пусть ось Ox - ось, вдоль которой действуют силы (нормаль к стороне в точке пересечения)
            //Oy - перпендикулярная ей ось.
            //Центр координат в точке пересечения

            //V10, V11 - скорости тела 1 до и после взаимодействия соответственно.
            //V20, V21 - аналогично второй

            //Вдоль оси Oy силы не действуют. Поэтому по закону сохранения импульса
            //V10y=V11y
            //V20y=V21y

            //Для осей Ox
            //V11x = ((m1-m2)V10x + 2*m2*V20x)/(m1+m2)
            //V21x = (2*m1*V10x + (m2-m1)V20x)/(m1+m2)

            //1: Ищем проекции скоростей на оси для этого поворачиваем вектора на -alpha
            //Оx совпадает с вектором нормали

            Point v1 = speedVector;
            Point v2 = m.speedVector;

            double angle = -Math.atan(intersection.getEnd().getY() / intersection.getEnd().getX());
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            //double modulus = Point.modulus(intersection.getEnd());
            // double cos = intersection.getEnd().getX()/modulus; double sin = intersection.getEnd().getY()/modulus;

            double v10x, v10y;
            v10x = v1.getX() * cos - v1.getY() * sin;
            v10y = v1.getX() * sin + v1.getY() * cos;
            double v20x;
            v20x = v2.getX() * cos - v2.getY() * sin;

            //Проекция на ось Oy не меняется, а Ox вычисляется по формуле выше
            float m1 = mass;
            float m2 = m.mass;

            double v11x = ((m1 - m2) * v10x + 2 * m2 * v20x) / (m1 + m2);
            //double v21x = (2*m1*v10x + (m2-m1)*v20x)/(m1+m2);

            //Возвращаемся назад

            //Получаем угол, под которым повернут вектор Ox
            //Т.к angle=-angle то cos=cos (четность) sin=-sin (нечетность)
            sin = -sin;

            v1 = new Point(v11x * cos - v10y * sin, v11x * sin + v10y * cos);
            //Конечные скорости получены.

            Point force = v1.add(speedVector.negate()).multiply(mass * 0.95f / deltaTime);
            Point rotationForces = new Point(force).multiply(0.05f);
            rotationForces = new Point(rotationForces.getY(), rotationForces.getX());

            useForce(body.getCentre(), force);
            m.useForce(m.body.getCentre(), force.negate());

            useForce(intersection.getStart(), rotationForces);
            m.useForce(intersection.getStart(), rotationForces.negate());
        }
        return wasIntersection;
    }

    protected boolean crossWithGeometricModel(PhysicModel m, float deltaTime) {

        //Проверяем, пересекаются ли тела
        return applyIntersection(m, deltaTime);

    }

    public void applyStaticForces(PhysicModel m, float deltaTime) {
        if (!m.getIsComplex()) {
            //gravitation
            double lengthBetweenCenters = getCentre().getLengthSquared(m.getCentre());
            double gravity = G * mass * m.mass / lengthBetweenCenters;

            Point force = new Point(getCentre()).add(m.getCentre()).multiply((float)gravity);
            GeometricModel g1 = new GeometricModel(body);
            GeometricModel g2 = new GeometricModel(m.body);
            g1.rotate(centreOfRotation, getRotationAngle(deltaTime));
            g2.rotate(centreOfRotation, m.getRotationAngle(deltaTime));
            g1.move(getMoveVector(deltaTime).add(force.multiply(deltaTime * deltaTime / mass / 2.0f)));
            g2.move(m.getMoveVector(deltaTime).add(force.multiply(-deltaTime * deltaTime / mass / 2.0f)));
            if (g1.getIntersection(g2) == null) {
                useForce(body.getCentre(), force);
                m.useForce(m.body.getCentre(), force.negate());
            }
        } else {
            m.applyStaticForces(this, deltaTime);
        }
    }

    public boolean crossThem(PhysicModel m, float deltaTime) {
        if (!m.getIsComplex())
            return crossWithGeometricModel(m, deltaTime);
        else
            return m.crossThem(this, deltaTime);
    }

    public Point getCentre() {
        return body.getCentre();
    }

    public void setParent(ComplexPhysicModel c) {
        parent = c;
    }

    protected PhysicModel(GeometricModel body, float mass) {
        this.body = body;
        this.mass = mass;
        speedVector = new Point(0, 0);
        acceleration = new Point(0, 0);
        w = 0;
        beta = 0;
        if (body != null) {
            centreOfRotation = body.getCentre();
            J = mass * this.body.calculateJ();
        } else
        if (!this.getIsComplex()) {
            System.err.println("[PhysicModel] Пустая тушка в конструкторе на входе");
            BasicWindow.exit();
        }
        this.parent = null;
    }

    protected PhysicModel(GeometricModel body, float mass, Point speedVector)
    {
        this(body,mass);
        this.speedVector = speedVector;
    }

    public PhysicModel(GeometricModel body, Point[] connectionPoints, float mass) {
        this(body, mass);
        this.connectionPoints = connectionPoints;
        isConnectionFree = new boolean[connectionPoints.length];
        for (int i = 0; i < isConnectionFree.length; i++)
            isConnectionFree[i] = true;
    }

    public PhysicModel(GeometricModel body, Point[] connectionPoints, float mass, Point speedVector)
    {
        this(body,connectionPoints, mass);
        this.speedVector = speedVector;
    }

}

package com.company.Physic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Geometry.Segment;

public class PhysicModel {

    private static final double G=10f;//G is the gravitational constant


    protected GeometricModel body;
    protected float mass;
    protected Point speedVector;
    protected Point acceleration;
    protected ComplexPhysicModel parent;


    //Да, по-идее, все эти величины нужно делать трехмерными векторами.
    //Но у нас же плоскопараллельное движение вдоль плоскости xOy
    //Угловая скорость
    protected float w;
    //Угловое ускорение
    protected float beta;
    protected Point centreOfRotation;
    //Момент инерции
    //TODO: Посчитать момент инерции треугольника. More: http://dxdy.ru/topic31945.html
    protected float J;


    protected float damage; //if 1 - physicModel is normal, if  0 - it is destroyed. Will used in model realisations.

    public float getSpeedX() {
        return speedVector.getX();
    }

    public float getSpeedY() {
        return speedVector.getY();
    }

    public void doSpecialActionA(float deltaTime) {
    }

    public void doSpecialActionB(float deltaTime) {
    }

    public void doSpecialActionC(float deltaTime) {
    }

    public void updateMotion(float deltaTime) {
        body.move(getMoveVector(deltaTime));
        body.rotate(getRotationAngle(deltaTime));
        updateKinematic(deltaTime);
    }

    /**
     * Считаем что ускорение на данном участке постоянно и считаем перемещение по формуле
     * S = V0*dT + A*dT^2/2
     * @param deltaTime Время которое  перемещались
     * @return  Вектор перемещения
     */
    public Point getMoveVector(float deltaTime)
    {
        return speedVector.multiply(deltaTime).add(acceleration.multiply(deltaTime * deltaTime / 2.0f));
    }

    /**
     * Получаем угол поворота по формуле для равноускоренного движения
     * @param deltaTime Время которое перемещались
     * @return Угол поворота
     */
    public float getRotationAngle(float deltaTime)
    {
        return w*deltaTime + beta * deltaTime * deltaTime / 2.0f;
    }

    /**
     * Обновляем кинематические переменные (скорости и ускорения)
     * Скорости меняем, ускорения обнуляем
     * @param deltaTime Время действия
     */
    protected void updateKinematic(float deltaTime)
    {
        speedVector.move(acceleration.multiply(deltaTime));
        acceleration = new Point(0,0);
        w += beta * deltaTime;
        beta = 0;
    }

    /**
     * Применяем силу к телу для изменения ускорений (угловых и линейных)
     * @param posOfForce  Точка приложения силы TODO: переделать на Segment
     * @param force    Сама сила
     */
    protected void useForce(Point posOfForce, Point force) {
        if (parent==null) {
        acceleration.move(force.multiply(1.0f/mass));
        //speedVector.move(force.multiply(deltaTime/mass));

        if (body.getCentre().getDistanceToPoint(posOfForce)>=Point.epsilon)
        {
        double deltaBeta = force.getLength()/J*Point.получитьРасстояниеОтТочкиДоПрямойБесплатноБезСМСБезРегистрации
                (body.getCentre(), posOfForce, posOfForce.add(force));
        //Получаем знак
        //Если конец вектора лежит в правой полуплоскости относительно прямой, проходящей через центр масс и точку приложения силы
        //То вращается вправо (знак минус), иначе влево
        if (Point.getDirection(posOfForce.add(force), body.getCentre(), posOfForce)) {
            deltaBeta = -deltaBeta;
        }
        beta += deltaBeta;
        }

        }
        else
            parent.useForce(posOfForce,force);
    }

    private boolean applyIntersection(PhysicModel m, float deltaTime)
    {
        boolean wasIntersection = false;
        GeometricModel g1=new GeometricModel(body);
        GeometricModel g2=new GeometricModel(m.body);

        g1.move(getMoveVector(deltaTime));
        g1.rotate(getRotationAngle(deltaTime));
        g2.move(m.getMoveVector(deltaTime));
        g2.rotate(m.getRotationAngle(deltaTime));

        Segment intersection=g1.getIntersection(g2);
        Segment tempIntersection = null;

        //Далее идет куча закомментированного кода, это все для отладочного вывода
        if (intersection!=null)
        {
            //System.out.println("1");
            tempIntersection = body.getIntersection(m.body);
            //if (tempIntersection!=null)
            //    System.out.println("2");
        }

        if (intersection!=null && tempIntersection == null) {
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
            double cos = Math.cos(angle); double sin = Math.sin(angle);
            //double modulus = Point.modulus(intersection.getEnd());
           // double cos = intersection.getEnd().getX()/modulus; double sin = intersection.getEnd().getY()/modulus;

            double v10x,v10y;
            v10x = v1.getX()*cos-v1.getY()*sin; v10y = v1.getX()*sin + v1.getY()*cos;
            double v20x;
            v20x = v2.getX()*cos-v2.getY()*sin;

            //Проекция на ось Oy не меняется, а Ox вычисляется по формуле выше
            float m1 = mass;
            float m2 = m.mass;

            double v11x = ((m1-m2)*v10x + 2*m2*v20x)/(m1+m2);
            //double v21x = (2*m1*v10x + (m2-m1)*v20x)/(m1+m2);

            //Возвращаемся назад

            //Получаем угол, под которым повернут вектор Ox
            //Т.к angle=-angle то cos=cos (четность) sin=-sin (нечетность)
            sin = -sin;

            v1 = new Point(v11x*cos-v10y*sin, v11x*sin + v10y*cos);
            //Конечные скорости получены.

            Point force = v1.add(speedVector.negate()).multiply(mass*0.95f/deltaTime);
            Point rotationForces = new Point(force).multiply(0.05f);
            rotationForces = new Point(rotationForces.getY(), rotationForces.getX());

            useForce(body.getCentre(), force);
            m.useForce(m.body.getCentre(), force.negate());

            useForce(intersection.getStart(), rotationForces);
            m.useForce(intersection.getStart(), rotationForces.negate());

            //speedVector = v1; m.speedVector = v2;

            GeometricModel g11=new GeometricModel(body);
            GeometricModel g21=new GeometricModel(m.body);

            g11.move(getMoveVector(deltaTime));
            g11.rotate(getRotationAngle(deltaTime));
            g21.move(m.getMoveVector(deltaTime));
            g21.rotate(m.getRotationAngle(deltaTime));


            //Segment intersection1=g11.getIntersection(g21);

            /*if (intersection1!=null)
            {
                System.out.println("FUCK! dV: " + Point.add(v1,speedVector.negate()));
                //Очень похоже, что нормаль направлена не в ту сторону.
                //И силы приложились не в том направлении. Значит нужно приложить силы в противоположном направлении.
                force = force.negate().multiply(2);
                useForce(body.getCentre(), force);
                m.useForce(m.body.getCentre(), force.negate());
            }
            else
            {
               // System.out.println("dV: " + Point.add(v1,Point.negate(speedVector)));
            }*/
        }
        return wasIntersection;
    }

    protected boolean crossWithGeometricModel(PhysicModel m, float deltaTime) {

        //Проверяем, пересекаются ли тела
        return applyIntersection(m, deltaTime);

    }

    public void applyStaticForces(PhysicModel m, float deltaTime)
    {
        //gravitation
        double lengthBetweenCenters = body.getCentre().getLengthSquared(m.body.getCentre());
        double gravity=G*mass*m.mass/lengthBetweenCenters;

        //lengthBetweenCenters = Math.sqrt(lengthBetweenCenters);
        double dx=(-body.getCentre().getX()+m.body.getCentre().getX())*gravity;
        double dy=(-body.getCentre().getY()+m.body.getCentre().getY())*gravity;

        Point force = new Point(dx,dy);
        GeometricModel g1 = new GeometricModel(body);
        GeometricModel g2 = new GeometricModel(m.body);

        g1.move(getMoveVector(deltaTime).add(force.multiply(deltaTime * deltaTime / mass / 2.0f)));
        g2.move(m.getMoveVector(deltaTime).add(force.multiply(-deltaTime * deltaTime / mass / 2.0f)));
        g1.rotate(getRotationAngle(deltaTime));
        g2.rotate(m.getRotationAngle(deltaTime));
        if (g1.getIntersection(g2)==null)
        {
            useForce(body.getCentre(), force);
            m.useForce(m.body.getCentre(), force.negate());
        }
    }

    public boolean crossThem(PhysicModel m, float deltaTime) {
        return crossWithGeometricModel(m, deltaTime);
    }

    public Point getCentre() {
        return body.getCentre();
    }

    public void setParent(ComplexPhysicModel c) {
        parent=c;
    }

    public PhysicModel(GeometricModel body, float mass) {
        this.body=body;
        this.mass=mass;
        //Пока не сделали нормально определение момента инерции - пользуемся формулой для шара с радиусом, численно равному массе
        this.J = mass * mass * mass / 2.0f;
        this.speedVector=new Point(0, 0);
        this.acceleration=new Point(0,0);
        this.w = 0;
        this.beta = 0;
    }

}

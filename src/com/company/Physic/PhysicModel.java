package com.company.Physic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Geometry.Segment;

public class PhysicModel {

    private static final double G=100f;//G is the gravitational constant

    private boolean wasIntersection = false;

    protected GeometricModel body;
    protected float mass;
    protected Point speedVector;

    //Угловая скорость
    protected float w;

    protected float damage; //if 1 - physicModel is noraml, if  0 - it is destroyed. Will used in model realisations.

    public float getSpeedX() {
        return speedVector.getX();
    }

    public float getSpeedY() {
        return speedVector.getY();
    }

    public void updateMotion(float deltaTime) {
        body.move(speedVector.multiply(deltaTime));
        body.rotate(w*deltaTime);
    }

    private void useForce(Point posOfForce, Point force, float deltaTime) {
        speedVector.move(force.multiply(deltaTime/mass));

        double deltaW = force.getDistanceToPoint(new Point(0,0))*deltaTime/mass*Point.получитьРасстояниеОтТочкиДоПрямойБесплатноБезСМСБезРегистрации
                (body.getCentre(), posOfForce, Point.add(posOfForce, force));
        //Получаем знак
        //Если конец вектора лежит в правой полуплоскости относительно прямой, проходящей через центр масс и точку приложения силы
        //То вращается вправо (знак минус), иначе влево
        if (Point.getDirection(Point.add(posOfForce, force), body.getCentre(), posOfForce))
                deltaW = -deltaW;
        w+=deltaW;
    }

    private void applyIntersection(PhysicModel m, float deltaTime)
    {
        wasIntersection = false;
        GeometricModel g1=new GeometricModel(body);
        GeometricModel g2=new GeometricModel(m.body);

        g1.move(speedVector.multiply(deltaTime));
        g2.move(m.speedVector.multiply(deltaTime));

        Segment intersection=g1.getIntersection(g2);
        Segment tempIntersection = null;
        if (intersection!=null)
        {
          //  System.out.println("1");
            tempIntersection = body.getIntersection(m.body);
           // if (tempIntersection!=null)
               // System.out.println("2");
        }

        if (intersection!=null && tempIntersection == null) {

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
            double v20x,v20y;
            v20x = v2.getX()*cos-v2.getY()*sin; v20y = v2.getX()*sin + v2.getY()*cos;

            //Проекция на ось Oy не меняется, а Ox вычисляется по формуле выше
            float m1 = mass;
            float m2 = m.mass;

            double v11x = ((m1-m2)*v10x + 2*m2*v20x)/(m1+m2);
            double v21x = (2*m1*v10x + (m2-m1)*v20x)/(m1+m2);

            //Возвращаемся назад

            //Получаем угол, под которым повернут вектор Ox
            //Т.к angle=-angle то cos=cos (четность) sin=-sin (нечетность)
            sin = -sin;

            v1 = new Point(v11x*cos-v10y*sin, v11x*sin + v10y*cos);
            v2 = new Point(v21x*cos-v20y*sin, v21x*sin + v20y*cos);

            //Конечные скорости получены.

            Point force = Point.add(v1, speedVector.negate()).multiply(mass/deltaTime);

            useForce(intersection.getStart(),force,deltaTime);
            m.useForce(intersection.getStart(),force.negate(),deltaTime);
            //speedVector = v1; m.speedVector = v2;

            GeometricModel g11=new GeometricModel(body);
            GeometricModel g21=new GeometricModel(m.body);

            g11.move(speedVector.multiply(deltaTime));
            g21.move(m.speedVector.multiply(deltaTime));

            Segment intersection1=g11.getIntersection(g21);

            if (intersection1!=null)
            {
                //System.out.println("FUCK! dV: " + Point.add(v1,Point.negate(speedVector)));
                //Очень похоже, что нормаль направлена не в ту сторону.
                //И силы приложились не в том направлении. Значит нужно приложить силы в противоположном направлении.
                force = force.negate().multiply(2);
                useForce(intersection.getStart(), force, deltaTime);
                m.useForce(intersection.getStart(), force.negate(), deltaTime);
            }
            else
            {
               // System.out.println("dV: " + Point.add(v1,Point.negate(speedVector)));
            }
        }
    }

    protected void crossWithGeometricModel(PhysicModel m, float deltaTime) {

        //Проверяем, пересекаются ли тела
        applyIntersection(m, deltaTime);

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

    public void crossThem(PhysicModel m, float deltaTime) {
        crossWithGeometricModel(m, deltaTime);
    }

    public PhysicModel(GeometricModel body, float mass) {
        this.body=body;
        this.mass=mass;
        this.speedVector=new Point(0, 0);
        this.w = 0;
    }

}

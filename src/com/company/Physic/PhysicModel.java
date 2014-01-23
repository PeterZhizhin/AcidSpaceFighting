package com.company.Physic;

import com.company.Geometry.GeometricModel;
import com.company.Geometry.Point;
import com.company.Geometry.Segment;

public class PhysicModel {

    private static final double G=10f;//G is the gravitational constant

    protected GeometricModel body;
    protected float mass;
    protected Point speedVector;
    protected float damage; //if 1 - physicModel is noraml, if  0 - it is destroyed. Will used in model realisations.

    public float getSpeedX() {
        return speedVector.getX();
    }

    public float getSpeedY() {
        return speedVector.getY();
    }

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


        Segment intersection=body.getIntersection(m.body);
        if (intersection!=null) {
            //Поехавшая физика столкновений
            //Надо целиком перепилить
            //TODO: Перепилить физику столкновений
            /*double lengthBetweenCenters= body.getCentre().getDistanceToPoint(m.body.getCentre());
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
            m.useForce(m.body.getCentre(), new Point(-x, -y), deltaTime);   **/

            float dx=body.getCentre().getX()-m.body.getCentre().getX();
            float dy=body.getCentre().getY()-m.body.getCentre().getY();

            intersection.setEnd(new Point(dx,dy));

            //Физика столкновений на основе законов сохранения импульсов и энергии

            //Продолжим. Формул нет нигде вывел сам.
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

            //1: Ищем проекции скоростей на оси
            //Оx совпадает с вектором нормали

            Point v1 = speedVector;
            Point v2 = m.speedVector;

            Point oX = intersection.getEnd();
            Point oY = new Point(oX.getY(), oX.getX());

            float angle = -(float)Math.atan(intersection.getEnd().getY() / intersection.getEnd().getX());
            float cos = (float)Math.cos(angle); float sin = (float)Math.sin(angle);

            float v10x,v10y;
            v10x = v1.getX()*cos-v1.getY()*sin; v10y = v1.getX()*sin + v1.getY()*cos;
            float v20x,v20y;
            v20x = v2.getX()*cos-v2.getY()*sin; v20y = v2.getX()*sin + v2.getY()*cos;

            //Проекция на ось Oy не меняется, а Ox вычисляется по формуле выше
            float m1 = mass;
            float m2 = m.mass;

            float v11x = ((m1-m2)*v10x + 2*m2*v20x)/(m1+m2);
            float v21x = (2*m1*v10x + (m2-m1)*v20x)/(m1+m2);

            //Получаем вектора в XoY

            v1 = new Point(v11x, v10y);
            v2 = new Point(v21x, v20y);

            //Возвращаемся назад

            //Получаем угол, под которым повернут вектор Ox
            sin = -sin;

            v1 = new Point(v1.getX()*cos-v1.getY()*sin, v1.getX()*sin + v1.getY()*cos);
            v2 = new Point(v2.getX()*cos-v2.getY()*sin, v2.getX()*sin + v2.getY()*cos);

            //Конечные скорости получены.

            /*Point f1 = Point.add(speedVector, Point.negate(v1)).multiply(mass/deltaTime);
            Point f2 = Point.add(m.speedVector, Point.negate(v2)).multiply(m.mass/deltaTime);

            useForce(intersection.getStart(),f1,deltaTime);
            useForce(intersection.getStart(),f2,deltaTime);*/
            speedVector = v1; m.speedVector = v2;

        }
    }

    public void crossThem(PhysicModel m, float deltaTime) {
        crossWithGeometricModel(m, body, deltaTime);
    }

    public PhysicModel(GeometricModel body, float mass, Point speedVector) {
        this.body=body;
        this.mass=mass;
        this.speedVector=speedVector;
    }

}

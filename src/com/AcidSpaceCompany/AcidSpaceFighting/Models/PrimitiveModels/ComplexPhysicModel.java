package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

import java.util.LinkedList;

public class ComplexPhysicModel extends PhysicModel {
    public boolean getIsComplex() {
        return true;
    }

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
    public boolean containsPoint(Point point)
    {
        return cm.getModelUnderPoint(point)!=null;
    }


    //Статические силы теперь тоже применяются ко всей системе
    @Override
    public void applyStaticForces(PhysicModel m, float deltaTime) {
        LinkedList<PhysicModel> another = m.getBodies();
        for(Model model : cm.getModels())
        {
            PhysicModel body = model.getPhysicModel();
            for (PhysicModel anotherBody : another)
                body.applyStaticForces(anotherBody,deltaTime);
        }
    }


    /**
     * Здесь мы будем удалять те тела, что с отрицательным здоровьем.
     * @param deltaTime изменение времени
     */
    @Override
    public void update(float deltaTime) {
        for (Model body : cm.getModels())
            body.update(deltaTime);
    }

    //Здесь нужно передать изменения всей системе тел
    @Override
    public void updateMotion(float deltaTime) {
        Point dS = getMoveVector(deltaTime);
        massCentre.move(dS);
        float angle = getRotationAngle(deltaTime);
        for (Model model : cm.getModels())
        {
            model.getPhysicModel().rotate(massCentre, angle);
            model.getPhysicModel().move(dS);
        }
        updateKinematic(deltaTime);
    }

    //А здесь сбросить угловые ускорения и скорости у тел
    @Override
    protected void updateKinematic(float deltaTime) {
        for (Model model : cm.getModels())
        {
            PhysicModel body = model.getPhysicModel();
            body.speedVector = new Point(speedVector);
            body.w = this.w;
            body.acceleration = new Point(0, 0);
            body.centreOfRotation = new Point(massCentre);
            body.beta = 0.0f;
        }
        super.updateKinematic(deltaTime);
    }

    @Override
    protected LinkedList<PhysicModel> getBodies()
    {
        LinkedList<PhysicModel> result = new LinkedList<PhysicModel>();
        for (Model model : cm.getModels())
            result.add(model.getPhysicModel());
        return result;
    }

    /**
     * Обработка столкновений. Просто проверяем столкновения отдельных частей.
     * @param m Модель для проверки столкновений
     * @param deltaTime Дельта время
     * @return Было столкновение (true) или нет (false)
     */
    @Override
    public boolean crossThem(PhysicModel m, float deltaTime) {
        boolean result = false;
        boolean needDestroy = m.getIsComplex();
        Point normal, point;
        normal = new Point(0,0);
        point = new Point(0,0);
        int intersectionNumber = 0;
        LinkedList<PhysicModel> anotherModelBodies = m.getBodies();
        for (Model model : cm.getModels())
        {
            PhysicModel body = model.getPhysicModel();
            for (PhysicModel anotherModelBody : anotherModelBodies)
            {
                /*Segment tempNormal = body.body.getIntersection(anotherModelBody.body);
                if (tempNormal!=null)
                {
                    result = true;
                    point.add(tempNormal.getStart());
                    normal.add(tempNormal.getEnd());
                    intersectionNumber++;
                } */
                boolean tempResult = body.crossThem(anotherModelBody, deltaTime);
                result = result | tempResult;
                //Если мы сталкиваемся с комплексной моделью - уничтожаем столкнувшиеся части
                //TODO: Не сталкиваемся, а пересекаемся. Если физон отработал нормально - всё ок. Если уже интерсект - взрываемся.
                if (needDestroy & tempResult)
                {
                    anotherModelBody.health = 0;
                    body.health = 0;
                }
            }
        }
       /* if (result)
        {
            normal.multiply(1.0f/intersectionNumber);
            point.multiply(1.0f/intersectionNumber);
            normal = normal.getNormal();

            float force = this.getForce(point, normal, m, 1, deltaTime);
            Point f1 = new Point(normal); f1.multiply(force);
            this.useForce(point, f1);
            m.useForce(point, f1.negate());
        }*/
        return result;
    }


    @Override
    public void useForce(Point posOfForce, Point force) {
        acceleration.move(force.multiply(1.0f / mass));
        //speedVector.move(force.multiply(deltaTime/mass));

        if (massCentre.getDistanceToPoint(posOfForce) >= Point.epsilon) {
            double length = Point.getLengthToLine(massCentre, posOfForce, posOfForce.add(force));
            double deltaBeta = force.length() * length / J;
            beta += deltaBeta;
        }
    }

    public void recomputeMassCenters() {
        massCentre = new Point(0, 0);
        speedVector = new Point(0, 0);
        mass = 0;
        J = 0;
        for (Model model : cm.getModels())
        {
            PhysicModel body = model.getPhysicModel();
            mass += body.mass;
            massCentre.move(body.getCentre().multiply(body.mass));
            speedVector.move(body.speedVector.multiply(body.mass));
        }
        massCentre = massCentre.multiply(1.0f / mass);
        speedVector = speedVector.multiply(1.0f / mass);
        w = 0;
        for (Model model : cm.getModels())
        {
            PhysicModel body = model.getPhysicModel();
            float localJ = body.J + body.mass * body.getCentre().getLengthSquared(this.massCentre);
            J += localJ;
            w+=body.w * localJ;
            body.setParent(this);
            body.centreOfRotation = massCentre;
        }
        acceleration = new Point(0, 0);
        w/=J;
        this.beta = 0;
    }


    public ComplexPhysicModel(ComplexModel complexModel)
    {
        super(null,0);
        cm = complexModel;
        recomputeMassCenters();
    }
}
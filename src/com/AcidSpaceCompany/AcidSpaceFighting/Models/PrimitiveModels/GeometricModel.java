package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Matrix3fGeometry;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Segment;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

//TODO: вычистить здесь всё

import static com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point.getTriangleSquare;

public class GeometricModel {
    private int numberOfCalculations = 0;
    private static final int maxNumberOfCalculations = 10000;

    private void incCalculations() {
        numberOfCalculations++;
        //if (numberOfCalculations > maxNumberOfCalculations)
        //    backupVertexes();
    }

    private Matrix3f rotationMatrix = new Matrix3f(); //Матрица поворота
    private Matrix3f scaleMatrix = new Matrix3f();    //Матрица масштаба
    private Matrix3f translateMatrix = new Matrix3f(); //Матрица переноса

    private Matrix3f resultMatrix = (Matrix3f) new Matrix3f().setIdentity(); //Результирующая матрица, умножив на неё радиус вектор точки - мы получим результирующую точку

    private Point[] rawVertexes; //"сырые" (не преобразованные точки)
    //Точки должны задаваться в системе координат, связанной с центром масс тела


    private static final float PI2 = (float) (2 * Math.PI);


    private Point centre;
    private Point[] vertexes;
    private float angle;

    //Максимальное расстояние между центрами масс (оптимизация пересечения)
    private float maxLength;
    //Расстояние для присоединения модели в комплексную модель
    private float connectionDistance;
    public float getConnectionDistance()
    {
        return connectionDistance;
    }

    /**
     * Считаем момент инерции многоугольника относительно центра масс
     * @return Геометрический момент инерции
     */
    public float calculateJ()
    {
        float J = 0;
        Vector3f[] vertexies = new Vector3f[vertexes.length];
        Point centreNegated = getCentre().negate();
        for (int i=0; i<vertexies.length; i++)
            vertexies[i] = vertexes[i].add(centreNegated).getRealVector3f();
        float chislitel = 0.0f; float znamenatel = 0.0f;
        Vector3f dest = new Vector3f();
        float tempCross;
        for (int i=0; i<vertexies.length-1; i++)
        {
            Vector3f.cross(vertexies[i+1], vertexies[i], dest);
            tempCross = dest.length();
            chislitel += tempCross * (vertexies[i+1].lengthSquared() +
                    Vector3f.dot(vertexies[i+1], vertexies[i]) + vertexies[i].lengthSquared());
            znamenatel += tempCross;
        }
        J = chislitel / znamenatel / 6.0f;
        return J;
    }


    public float getAngle() {
        return angle;
    }

    //Rotate model
    public void rotate(float angle) {
        /*this.angle += angle;

        //Совместимость
        for (Point vertex : vertexes) {
            vertex.rotate(angle, centre);
        }
        while (this.angle >= PI2) this.angle -= PI2;
        while (this.angle <= 0) this.angle += PI2;

        //Создаем матрицу для поворота на этот угол
        Matrix3fGeometry.createRotationMatrix(this.angle, rotationMatrix);
        incCalculations();*/
        rotate(centre, angle);
    }



    public double getVolumePerDeep() {
        double sumAggreagtor=0;

        for (int i=2; i<rawVertexes.length; i++) {
             sumAggreagtor+=getTriangleSquare(vertexes[0], vertexes[i-1], vertexes[i]);
        }

        return sumAggreagtor;
    }

    public void rotate(Point centreOfRotation, float angle) {
        for (Point vertex : vertexes) {
            vertex.rotate(angle, centreOfRotation);
        }

        this.angle += angle;
        while (this.angle >= PI2) this.angle -= PI2;
        while (this.angle <= 0) this.angle += PI2;

        centre.rotate(angle, centreOfRotation);
        incCalculations();
    }

    //Move todel on p.x, p.y
    public void move(float x, float y) {
        centre.move(x, y);

        //Совместимость
        for (Point vertex : vertexes) {
            vertex.move(x, y);
        }

        //Создаем матрицу для сдвига
        incCalculations();
    }

    //Move todel on p.x, p.y
    public void move(Point p) {
        move(p.x, p.y);
    }

    public void moveTo(float x, float y)
    {
        move(centre.negate());
        move(x, y);
    }

    public void moveTo(Point position)
    {
        moveTo(position.x, position.y);
    }

    public void rotateTo(float angle)
    {
        rotate(-this.angle);
        rotate(angle);
    }

    public float getMaxLength() {
        return maxLength;
    }


    //Get intersection between 2 models. If it is not exists - return null
    public Segment getIntersection(GeometricModel model) {

        /*if (centre.getBrutalLength(model.centre) > this.maxLength + model.maxLength)
            return null;*/

        float maxLength = this.maxLength + model.maxLength;
        maxLength *= maxLength;

        if (centre.getLengthSquared(model.centre) > maxLength)
            return null;


        for (int i = 0; i < model.getPointCount(); i++) {
            for (int j = 0; j < getPointCount(); j++) {
                Point p = Point.getIntersection(getPoint(j), getPoint(j + 1), model.getPoint(i), model.getPoint(i + 1));
                if (p != null) {

                    double d1 = p.getDistanceToPoint(model.getPoint(i)) + p.getDistanceToPoint(model.getPoint(i + 1));
                    double d2 = p.getDistanceToPoint(getPoint(j)) + p.getDistanceToPoint(getPoint(j + 1));
                    if (d1 < d2) {
                        return new Segment(p, Point.getNormal(model.getPoint(i), model.getPoint(i + 1)));
                    } else {
                        return new Segment(p, Point.getNormal(getPoint(j), getPoint(j + 1)));
                    }
                }

            }
        }

        /*
        for (int i=0; i<model.getPointCount(); i++)
            if (contains(model.getPoint(i))) {
                return model.getPoint(i);
            }

        for (int i=0; i<getPointCount(); i++)
            if (model.contains(getPoint(i))) {
                return getPoint(i);
            }
        */

        return null;
    }

    /**
     * Принадлежность точки многоугольнику
     * @param point
     * @return
     */
    public boolean contains(Point point)
    {
        double angle = 0;
        for (int i = 0; i < vertexes.length; i++) {
            angle += Point.getAngle(getPoint(i), point, getPoint(i + 1));
        }
        return Math.abs(angle - Math.PI * 2) <= Point.epsilon;
    }

    /**
     * Получаем результирующую матрицу (композицию всех преобразований)
     */
    private void createResultMatrix() {
        Matrix3f.setIdentity(resultMatrix);
        Matrix3f.mul(resultMatrix, translateMatrix, resultMatrix);
        Matrix3f.mul(resultMatrix, rotationMatrix, resultMatrix);
        Matrix3f.mul(resultMatrix, scaleMatrix, resultMatrix);
    }

    public void backupVertexes() {
        //Пусть тут пока не будет ничего.
        Matrix3fGeometry.createTranslateMatrix(centre, translateMatrix);
        Matrix3fGeometry.createRotationMatrix(this.angle, rotationMatrix);
        createResultMatrix();
        vertexes = new Point[rawVertexes.length];
        for (int i = 0; i < vertexes.length; i++) {
            Vector3f result = new Vector3f();
            Matrix3f.transform(resultMatrix, rawVertexes[i].getVector3f(), result);
            vertexes[i] = new Point(result.getX(), result.getY());
        }
    }

    //Return count of vertexes
    public int getPointCount() {
        return vertexes.length;
    }

    public Point getPoint(int pointNum) {
        if (pointNum >= vertexes.length) pointNum -= vertexes.length;
        return vertexes[pointNum];
    }

    public Point getCentre() {
        return centre;
    }

    private static Point[] clonePoints(Point[] p) {
        Point[] cl=new Point[p.length];
        for (int i=0; i<p.length; i++)
            cl[i]=new Point(p[i]);
        return cl;
    }

    public GeometricModel(GeometricModel g) {
        this(clonePoints(g.vertexes));
    }

    public float getConnectionDistanceCoef() {
        return 3f;
    }

    /**
     * Создание модели
     *
     * @param vertexes Точки тела
     */
    public GeometricModel(Point[] vertexes) {
        //Кусок для совместимости
        this.vertexes = vertexes;

        centre = new Point(0, 0);

        for (Point vertex : vertexes) {
            centre.move(vertex);
        }
        centre.set(centre.getX() / vertexes.length, centre.getY() / vertexes.length);

        //Получаем радиус окружности для отсечения
        maxLength = 0;
        double max = 0;
        for (Point vertex : vertexes) {
            max = Math.max(max, centre.getDistanceToPoint(vertex));
        }
        maxLength = (float) max;

        connectionDistance = getConnectionDistanceCoef()*maxLength;

        rawVertexes = new Point[vertexes.length];
        for (int i = 0; i < vertexes.length; i++) {
            rawVertexes[i] = new Point(vertexes[i]);
            rawVertexes[i].move(centre.negate());
        }


        //Создаем начальную матрицу переноса
        Matrix3fGeometry.createTranslateMatrix(centre, translateMatrix);

        //Создаем начальную матрицу поворота
        angle = 0.0f;
        Matrix3fGeometry.createRotationMatrix(0.0f, rotationMatrix);

        //Создаем начальную матрицу масштаба
        Matrix3fGeometry.createScaleMatrix(new Vector2f(1.0f, 1.0f), scaleMatrix);
    }


}

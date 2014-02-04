package com.company.Geometry;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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

    public void rotate(Point centreOfRotation, float angle) {
        for (Point vertex : vertexes) {
            vertex.rotate(angle, centreOfRotation);
        }

        this.angle += angle;
        while (this.angle >= PI2) this.angle -= PI2;
        while (this.angle <= 0) this.angle += PI2;

        centre.rotate(angle, centreOfRotation);

        Matrix3fGeometry.createRotationMatrix(this.angle, rotationMatrix);
        Matrix3fGeometry.createTranslateMatrix(centre.getVector2f(), translateMatrix);
        incCalculations();
    }

    //Move todel on p.x, p.y
    public void move(Point p) {
        centre.move(p);

        //Совместимость
        for (Point vertex : vertexes) {
            vertex.move(p);
        }

        //Создаем матрицу для сдвига
        Matrix3fGeometry.createTranslateMatrix(centre.getVector2f(), translateMatrix);
        incCalculations();
    }

    public float getMaxLength() {
        return maxLength;
    }

    private boolean contains(Point p) {
        double angle = 0;
        for (int i = 0; i < vertexes.length; i++) {
            angle += Point.getAngle(getPoint(i), p, getPoint(i + 1));
        }
        return Math.abs(angle - Math.PI * 2) <= Point.epsilon;
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

    public GeometricModel(GeometricModel g) {
        vertexes = new Point[g.getPointCount()];
        for (int i = 0; i < vertexes.length; i++)
            vertexes[i] = new Point(g.getPoint(i));
        centre = new Point(g.getCentre());
        maxLength = g.maxLength;

        rawVertexes = new Point[g.rawVertexes.length];
        for (int i = 0; i < rawVertexes.length; i++)
            rawVertexes[i] = new Point(g.rawVertexes[i]);

        Matrix3f.load(g.rotationMatrix, rotationMatrix);
        Matrix3f.load(g.translateMatrix, translateMatrix);
        Matrix3f.load(g.scaleMatrix, scaleMatrix);
        Matrix3f.load(g.resultMatrix, resultMatrix);
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

        rawVertexes = new Point[vertexes.length];
        for (int i = 0; i < vertexes.length; i++) {
            rawVertexes[i] = new Point(vertexes[i]);
            rawVertexes[i].move(centre.negate());
        }


        //Создаем начальную матрицу переноса
        Matrix3fGeometry.createTranslateMatrix(centre.getVector2f(), translateMatrix);

        //Создаем начальную матрицу поворота
        angle = 0.0f;
        Matrix3fGeometry.createRotationMatrix(0.0f, rotationMatrix);

        //Создаем начальную матрицу масштаба
        Matrix3fGeometry.createScaleMatrix(new Vector2f(1.0f, 1.0f), scaleMatrix);
    }


}

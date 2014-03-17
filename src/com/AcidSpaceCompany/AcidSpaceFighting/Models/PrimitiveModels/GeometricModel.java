package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Matrix3fGeometry;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Segment;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

//TODO: вычистить здесь всё

public class GeometricModel {

    private Matrix3f rotationMatrix = new Matrix3f(); //Матрица поворота
    private Matrix3f scaleMatrix = new Matrix3f();    //Матрица масштаба
    private Matrix3f translateMatrix = new Matrix3f(); //Матрица переноса

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
        float J;
        Vector3f[] vertexes = new Vector3f[this.vertexes.length];
        Point centreNegated = getCentre().negate();
        for (int i=0; i<vertexes.length; i++)
            vertexes[i] = this.vertexes[i].add(centreNegated).getRealVector3f();
        float chislitel = 0.0f; float znamenatel = 0.0f;
        Vector3f dest = new Vector3f();
        float tempCross;
        for (int i=0; i<vertexes.length-1; i++)
        {
            Vector3f.cross(vertexes[i+1], vertexes[i], dest);
            tempCross = dest.length();
            chislitel += tempCross * (vertexes[i+1].lengthSquared() +
                    Vector3f.dot(vertexes[i+1], vertexes[i]) + vertexes[i].lengthSquared());
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
    }

    //Move todel on p.x, p.y
    public void move(Point p) {
        centre.move(p);

        //Совместимость
        for (Point vertex : vertexes) {
            vertex.move(p);
        }
    }

    public float getMaxLength() {
        return maxLength;
    }

    public boolean contains(Point p) {
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

        return null;
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

        //TODO: выпилить
        connectionDistance = 2.0f*maxLength;

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

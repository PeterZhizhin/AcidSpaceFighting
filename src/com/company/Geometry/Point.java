package com.company.Geometry;

public class Point {

    private float x, y;
    public static final float epsilon = 0.0001f;

    public static Point add(Point v1, Point v2)
    {
        return new Point(v1.x + v2.x, v1.y + v2.y);
    }

    public Point negate()
    {
        return new Point(-x,-y);
    }

    public static Point getNormal(Point p1, Point p2) {
        float a1 = p2.getY() - p1.getY();
        float b1 = p1.getX() - p2.getX();
        return new Point(a1, b1);
    }

    //p2p1 and p2p3 - rays
    public static double getAngle(Point p1, Point p2, Point p3) {
        float x1 = p1.getX() - p2.getX();
        float x2 = p3.getX() - p2.getX();
        float y1 = p1.getY() - p2.getY();
        float y2 = p3.getY() - p2.getY();
        return (x1 * x2 + y1 * y2) / Math.sqrt((x1 * x1 + y1 * y1) * (x2 * x2 + y2 * y2));
    }

    /**
     * @param vector1 1 вектор
     * @param vector2 2 вектор
     * @return Скалярное произведение векторов
     */
    public static float getScalarMultiply(Point vector1, Point vector2)
    {
        return vector1.x * vector2.x + vector1.y * vector2.y;
    }

    /**
     * @param vector Вектор (или точка)
     * @return Длина вектора (или расстояние от центра координат)
     */
    public static float modulus(Point vector)
    {
        return (float)Math.sqrt(vector.x*vector.x + vector.y*vector.y);
    }

    /**
     * @param p1 Первая точка
     * @param p2 Вторая точка
     * @return Квадрат расстояния
     */
    public static float getLengthSquared(Point p1, Point p2)
    {
        //Вычисляем проекции расстояний
        float dx = p1.x - p2.x;
        float dy = p1.y - p2.y;
        return dx*dx + dy*dy;
    }

    /**
     * @param p1 Первая точка
     * @param p2 Вторая точка
     * @return Расстояние между точками
     */
    public static float getLength(Point p1, Point p2)
    {
        return (float)Math.sqrt(getLengthSquared(p1,p2));
    }

    //check lines p1p2 and line p3p4
    public static Point getIntersection(Point p1, Point p2, Point p3, Point p4) {
        float a1 = p2.getY() - p1.getY();
        float a2 = p4.getY() - p3.getY();
        float b1 = p1.getX() - p2.getX();
        float b2 = p3.getX() - p4.getX();
        float det = a1 * b2 - a2 * b1;
        if (Math.abs(det) <= epsilon) return null;
        float c1 = p1.getY() * b1 + p1.getX() * a1;
        float c2 = p3.getY() * b2 + p3.getX() * a2;
        float det1 = c1 * b2 - c2 * b1;
        float det2 = a1 * c2 - a2 * c1;
        float x = det1 / det;
        float y = det2 / det;
        boolean inX12 = Math.min(p1.getX(), p2.getX()) <= x && x <= Math.max(p1.getX(), p2.getX());
        boolean inX34 = Math.min(p3.getX(), p4.getX()) <= x && x <= Math.max(p3.getX(), p4.getX());
        boolean inY12 = Math.min(p1.getY(), p2.getY()) <= y && y <= Math.max(p1.getY(), p2.getY());
        boolean inY34 = Math.min(p3.getY(), p4.getY()) <= y && y <= Math.max(p3.getY(), p4.getY());
        if (inX12 && inX34 && inY12 && inY34) return new Point(x, y);
        return null;
    }

    public double getDistanceToPoint(Point p) {
        float dx=x-p.x;
        float dy=y-p.y;
        dx*=dx; dy*=dy;
        return Math.sqrt(dx+dy);
    }
    public void rotate(float angle) {
        double newX=x*Math.cos(angle)-y*Math.sin(angle);
        double newY=x*Math.sin(angle)+y*Math.cos(angle);
        x= (float) newX;
        y= (float) newY;
    }

    /**
     * Умножает вектор на скаляр
     * @param scalar Скаляр
     */
    public Point multiply(float scalar)
    {
        return new Point(x*scalar, y*scalar);
    }

    public void move(float dx, float dy) {
        x+=dx;
        y+=dy;
    }

    public void move(Point p) {
        x+=p.x;
        y+=p.y;
    }

    public void set(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public void set(Point p) {
        set(p.x, p.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point(double x, double y)
    {
        this.x = (float)x;
        this.y = (float)y;
    }

    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }


    public String toString() {
        return x+" "+y;
    }
}
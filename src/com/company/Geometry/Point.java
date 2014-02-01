package com.company.Geometry;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Point {

    private float x, y;
    public static final float epsilon = 0.0001f;

    public static double получитьРасстояниеОтТочкиДоПрямойБесплатноБезСМСБезРегистрации
            (Point точка, Point точкаПрямой1, Point точкаПрямой2) {
        float a1 = точкаПрямой2.getY() - точкаПрямой1.getY();
        float b1 = точкаПрямой1.getX() - точкаПрямой2.getX();
        float c1 = точкаПрямой1.getY() * b1 + точкаПрямой1.getX() * a1;
        double result = Math.abs(a1*точка.getX()+b1*точка.getY()+c1)/Math.sqrt(a1*a1+b1*b1);
        if (result<=Point.epsilon)
            return 0;
        else return result;
    }

    /**
     * Получаем полуплоскость, в которой лежит точка относительно прямой
     * @param point Исследуемая точка
     * @param p1  Первая точка
     * @param p2  Вторая точка
     * @return Какую-то из полуплоскостей (какую - фиг поймешь)
     */
    public static boolean getDirection(Point point, Point p1, Point p2)
    {
        float a1 = p2.getY() - p1.getY();
        float b1 = p1.getX() - p2.getX();
        float c1 = p1.getY() * b1 + p1.getX() * a1;

        return (a1*point.getX() + b1*point.getY() + c1)>0;
    }

    //p1p2p3-angle
    public static Point getBisection(Point p1, Point p2, Point p3) {
          return new Point((p3.x+p1.x)/2-p2.x, (p3.y+p1.y)/2-p2.y);
    }

    public static Point add(Point v1, Point v2)
    {
        return new Point(v1.x + v2.x, v1.y + v2.y);
    }

    public Point negate()
    {
        return new Point(-x,-y);
    }

    public void normalise() {
        float length= (float) Math.sqrt(x*x+y*y);
        x/=length;
        y/=length;
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

    public float getLengthSquared(Point p2)
    {
        float dx = x - p2.x;
        float dy = y - p2.y;
        return dx*dx + dy*dy;
    }

    public double getDistanceToPoint(Point p) {
        float dx=x-p.x;
        float dy=y-p.y;
        dx*=dx; dy*=dy;
        return Math.sqrt(dx+dy);
    }

    public double getLength()
    {
        return getDistanceToPoint(new Point(0, 0));
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

    /**
     * Получаем Vector2f из точки
     * @return Vector2f из точки
     */
    public Vector2f getVector2f()
    {
        return new Vector2f(x,y);
    }

    public Vector3f getVector3f()
    {
        return new Vector3f(x,y,1.0f);
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
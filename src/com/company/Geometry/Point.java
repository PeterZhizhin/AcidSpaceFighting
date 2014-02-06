package com.company.Geometry;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Point extends Vector2f {

    public static final float epsilon = 0.0001f;
    public static final Point empty=new Point(0, 0);

    public static Point mixPoints(Point p1, Point p2, float mix) {
        float mix2=1-mix;
        return new Point(p1.x*mix+p2.x*mix2, p1.y*mix+p2.y*mix2);
    }

    public static double getTriangleSquare(Point p1, Point p2, Point analRape) {

        double l1=p1.getDistanceToPoint(p2);
        double l2=p1.getDistanceToPoint(analRape);
        double l3=p2.getDistanceToPoint(analRape);

        double halfPerimeter=l1+l2+l3;
        halfPerimeter/=2;

        return Math.sqrt(halfPerimeter*(halfPerimeter-l1)*(halfPerimeter-l2)*(halfPerimeter-l3));
    }

    public static double getLengthToLine
            (Point from, Point ptOnLine1, Point ptOnLine2) {
        float a = ptOnLine1.y - ptOnLine2.y;
        float b = ptOnLine2.x - ptOnLine1.x;
        float c = ptOnLine1.x * ptOnLine2.y - ptOnLine2.x * ptOnLine1.y;
        double result = (a * from.getX() + b * from.getY() + c) / Math.sqrt(a * a + b * b);
        if (Math.abs(result) <= Point.epsilon)
            return 0;
        else return result;
    }

    //p1p2p3-angle
    public static Point getBisection(Point p1, Point p2, Point p3) {
        Point pl1=new Point(p2.x-p1.x, p2.y-p1.y);
        Point pl2=new Point(p2.x-p3.x, p2.y-p3.y);
        pl1.setLength(1);
        pl2.setLength(1);
        if (pl1.x+pl2.x+pl1.y+pl2.y <=epsilon*4) {
            return pl1.getNormal().setLength(1);
        }
        return new Point((pl1.x+pl2.x)/2, (pl1.y+pl2.y)/2).setLength(1);

    }

    public Point add(Point v2) {
        return new Point(x + v2.x, y + v2.y);
    }

    public Point negate() {
        return new Point(-x, -y);
    }

    public Point setLength(float length) {
        float l=super.length();
        if (l<=epsilon) return this;
        return this.multiply(length/l);
    }

    public Point getNormal() {
        float a1 = getY();
        float b1 = - getX();
        return new Point(a1, b1);
    }

    public static Point getNormal(Point p1, Point p2) {
        float a1 = p2.getY() - p1.getY();
        float b1 = p1.getX() - p2.getX();
        return new Point(a1, b1);
    }

    //p2p1 and p2p3 - rays
    public static double getAngle(Point p1, Point p2, Point p3) {
        /*
        float x1 = p1.getX() - p2.getX();
        float x2 = p3.getX() - p2.getX();
        float y1 = p1.getY() - p2.getY();
        float y2 = p3.getY() - p2.getY();
        return (x1 * x2 + y1 * y2) / Math.sqrt((x1 * x1 + y1 * y1) * (x2 * x2 + y2 * y2));
        */
        return Vector2f.angle(new Vector2f(p2.x-p1.x, p2.y-p1.y), new Vector2f(p2.x-p3.x, p2.y-p3.y));
    }

    public float getLengthSquared(Point p2) {
        float dx = x - p2.x;
        float dy = y - p2.y;
        return dx * dx + dy * dy;
    }

    public double getDistanceToPoint(Point p) {
        float dx = x - p.x;
        float dy = y - p.y;
        dx *= dx;
        dy *= dy;
        return Math.sqrt(dx + dy);
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

    public void rotate(float angle, Point center) {

        float ax = x - center.x;
        float ay = y - center.y;

        double newX = ax * Math.cos(angle) - ay * Math.sin(angle);
        double newY = ax * Math.sin(angle) + ay * Math.cos(angle);
        x = (float) newX + center.x;
        y = (float) newY + center.y;
    }

    /**
     * Умножает вектор на скаляр
     *
     * @param scalar Скаляр
     */
    public Point multiply(float scalar) {
        return new Point(x * scalar, y * scalar);
    }

    public void move(float dx, float dy) {
        super.translate(dx,dy);
    }

    public void move(Point p) {
        move(p.x, p.y);
    }

    public Point(double x, double y) {
        super((float)x, (float)y);
    }

    public Vector3f getVector3f() {
        return new Vector3f(x, y, 1.0f);
    }
    public Vector3f getRealVector3f() {
        return new Vector3f(x,y,0.0f);
    }


    public Point(float x, float y) {
        super(x, y);
    }

    public Point(Point p) {
        this(p.x, p.y);
    }


    public String toString() {
        return x + " " + y;
    }
}
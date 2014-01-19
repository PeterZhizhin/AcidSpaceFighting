package com.company.Geometry;

import static com.company.Geometry.Point.getAngle;

public class GeometricModel {
    private static final float PI2 = (float) (2 * Math.PI);


    private Point center;
    private Point[] vertexes;
    private float angle;

    //Rotate model
    public void rotate(float angle) {
        this.angle += angle;
        for (Point vertex : vertexes) {
            vertex.move(-center.getX(), -center.getY());
            vertex.rotate(angle);
            vertex.move(center);
        }
        if (this.angle >= PI2) this.angle -= PI2;
        else if (this.angle <= 0) this.angle += PI2;
    }

    //Move todel on p.x, p.y
    public void move(Point p) {
        center.move(p);
        for (Point vertex : vertexes) {
            vertex.move(p);
        }
    }

    private boolean contains(Point p) {
        double angle = 0;
        for (int i = 0; i < vertexes.length; i++) {
            angle += getAngle(getPoint(i), p, getPoint(i+1));
        }
        return Math.abs(angle - Math.PI * 2) <= Point.epsilon;
    }

    //Get intersection between 2 models. If it is not exists - return null
    public Point getIntersection(GeometricModel model) {

        for (int i=0; i<model.getPointCount(); i++)
        {
            for (int j=0; j<getPointCount(); j++)
            {
                Point p = Point.getIntersection(getPoint(j), getPoint(j + 1), model.getPoint(i), model.getPoint(i + 1));
                if (p!=null) {
                    return p;
                }
            }
        }

        for (int i=0; i<model.getPointCount(); i++)
            if (contains(model.getPoint(i))) {
                return model.getPoint(i);
            }

        return null;
    }

    //Return count of vertexes
    public int getPointCount() {
        return vertexes.length;
    }

    public Point getPoint(int pointNum) {
        if (pointNum>=vertexes.length) pointNum-=vertexes.length;
        return vertexes[pointNum];
    }

    public Point getCenter() {
        return center;
    }

    public GeometricModel(Point[] p) {
        vertexes = p;

        center = new Point(0, 0);
        for (Point p2 : p) {
            center.move(p2);
        }
        center.set(center.getX() / p.length, center.getY() / p.length);
    }


}

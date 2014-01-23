package com.company.Geometry;

import static com.company.Geometry.Point.getAngle;

public class GeometricModel {
    private static final float PI2 = (float) (2 * Math.PI);


    private Point centre;
    private Point[] vertexes;
    private float angle;

    //Максимальное расстояние между центрами масс (оптимизация пересечения)
    private float maxLength;

    //Rotate model
    public void rotate(float angle) {
        this.angle += angle;
        for (Point vertex : vertexes) {
            vertex.move(-centre.getX(), -centre.getY());
            vertex.rotate(angle);
            vertex.move(centre);
        }
        if (this.angle >= PI2) this.angle -= PI2;
        else if (this.angle <= 0) this.angle += PI2;
    }

    //Move todel on p.x, p.y
    public void move(Point p) {
        centre.move(p);
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
    public Segment getIntersection(GeometricModel model) {

        float maxLength = this.maxLength + model.maxLength;
        maxLength*=maxLength;
        if (Point.getLengthSquared(this.centre, model.centre) > maxLength)
            return null;

        for (int i=0; i<model.getPointCount(); i++)
        {
            for (int j=0; j<getPointCount(); j++)
            {
                Point p = Point.getIntersection(getPoint(j), getPoint(j + 1), model.getPoint(i), model.getPoint(i + 1));
                if (p!=null) {

                    double d1=p.getDistanceToPoint(model.getPoint(i))+ p.getDistanceToPoint(model.getPoint(i + 1));
                    double d2=p.getDistanceToPoint(getPoint(j))+ p.getDistanceToPoint(getPoint(j + 1));
                    if (d1<d2)
                    return new Segment(p, Point.getNormal(model.getPoint(i), model.getPoint(i + 1)));
                    else
                    return new Segment(p, Point.getNormal(getPoint(j), getPoint(j + 1)));
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

    //Return count of vertexes
    public int getPointCount() {
        return vertexes.length;
    }

    public Point getPoint(int pointNum) {
        if (pointNum>=vertexes.length) pointNum-=vertexes.length;
        return vertexes[pointNum];
    }

    public Point getCentre() {
        return centre;
    }

    public GeometricModel(Point[] p) {
        vertexes = p;

        centre = new Point(0, 0);
        for (Point p2 : p) {
            centre.move(p2);
        }
        centre.set(centre.getX() / p.length, centre.getY() / p.length);

        maxLength = 0;
        for (Point point : p)
        {
            maxLength = Math.max(maxLength, Point.getLength(centre, point));
        }
    }


}

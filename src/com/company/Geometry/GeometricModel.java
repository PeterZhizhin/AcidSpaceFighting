package com.company.Geometry;

public class GeometricModel {
    private static final float PI2= (float) (2*Math.PI);


    private Point center;
    private Point[] vertexes;
    private float angle;


    public void rotate(float angle) {
        this.angle+=angle;
        for (Point vertexe : vertexes) {
            vertexe.move(-center.getX(), -center.getY());
            vertexe.rotate(angle);
            vertexe.move(center);
        }
        if (this.angle>=PI2) this.angle-=PI2;
        else if (this.angle<=0) this.angle+=PI2;
    }

    public void move (Point p) {
       center.move(p);
        for (Point vertex : vertexes) {
            vertex.move(p);
        }
    }

    public boolean contains(Point p) {
        return false;
        //check
    }

    public boolean intersect(GeometricModel model) {
        return false;
        //check
    }

    public Point getPoint(int pointNum) {
        return vertexes[pointNum];
    }

    public Point getCenter() {
        return center;
    }

    public GeometricModel(Point[] p) {
        vertexes=p;

        center=new Point(0, 0);
        for (Point p2: p) {
            center.move(p2);
        }
        center.set(center.getX() / p.length, center.getY() / p.length);
    }




}

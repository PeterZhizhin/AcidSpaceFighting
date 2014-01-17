package com.company;

public class GeometricModel {

    private Point center;
    private Point[] vertexes;
    private Point[] sourceVertexes;
    private float angle;

    public void rotate(float angle) {
        this.angle+=angle;
        for (int i=0; i<vertexes.length; i++) {
            vertexes[i].set(sourceVertexes[i]);
            vertexes[i].rotate(angle);
            vertexes[i].move(center);
        }
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

    public GeometricModel() {

    }




}

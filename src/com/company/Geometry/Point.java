package com.company.Geometry;

public class Point {

    private float x, y;

    public void rotate(float angle) {
        double newX=x*Math.cos(angle)-y*Math.sin(angle);
        double newY=x*Math.sin(angle)+y*Math.cos(angle);
        x= (float) newX;
        y= (float) newY;
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

    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

}

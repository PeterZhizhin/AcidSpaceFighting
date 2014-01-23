package com.company.Geometry;

public class Segment {

    private Point start;
    //end - конец отрезка
    private Point end;

    public void setStart(Point p) {
        start=p;
    }

    public void setEnd(Point p) {
        end=p;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Segment(Point start, Point ends) {
        this.start=start;
        this.end=end;
    }

}

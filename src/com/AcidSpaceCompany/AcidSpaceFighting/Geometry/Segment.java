package com.AcidSpaceCompany.AcidSpaceFighting.Geometry;

public class Segment {

    private Point start;
    //end - конец отрезка
    private Point end;

    public void setStart(Point p) {
        start = p;
    }

    public void setEnd(Point p) {
        end = p;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Segment(float x1, float y1, float x2, float y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

}

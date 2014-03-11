package com.AcidSpaceCompany.AcidSpaceFighting.GUI.Controls;

public class Rectangle {

    protected float x, y, w, h, x2, y2;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public boolean contains(float xPos, float yPos) {
        return (xPos > x && xPos < x2 && yPos > y && yPos < y2);
    }

    public Rectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        x2 = x + w;
        y2 = y + h;
    }
}

package com.company.Graphic.Controls;

import static org.lwjgl.opengl.GL11.glColor4f;

public class Color {

    public float r, g, b, a;

    public void bind() {
        glColor4f(r, g, b, a);
    }

    public Color (Color a, Color b, float mix) {
        this(a.r*mix+b.r*(1-mix), a.g*mix+b.g*(1-mix), a.b*mix+b.b*(1-mix));
    }

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1f;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

}

package com.company.Graphic.Controls;

import static org.lwjgl.opengl.GL11.glColor4f;

public class Color {

    public float r, g, b, a;

    public void bind() {
        glColor4f(r, g, b, a);
    }

    public static final Color transparent=new Color(1f, 1f, 1f, 0f);

    public Color(float r, float g, float b) {
        this.r=r;
        this.g=g;
        this.b=b;
        this.a=1f;
    }

    public Color(float r, float g, float b, float a) {
        this.r=r;
        this.g=g;
        this.b=b;
        this.a=a;
    }

}

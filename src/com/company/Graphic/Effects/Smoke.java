package com.company.Graphic.Effects;

import com.company.Graphic.Camera;
import com.company.Graphic.Posteffect;

import static org.lwjgl.opengl.GL11.*;

public class Smoke implements Posteffect {

    private float radius;
    private float currentRadius;
    private float x, y;
    private int timer;

    public Smoke(float x, float y, float radius) {
        currentRadius=radius/1000f;
        this.radius=radius;
        this.x=x;
        this.y=y;
        timer=6;
    }

    @Override
    public void draw() {

        if (timer<=0) {
        glBegin(GL_TRIANGLE_FAN);
        float l=currentRadius*2/3;

        glColor4f(0, 0, 0, (1-currentRadius/radius));
        Camera.translatePoint(x, y);

        glColor4f(0f, 0f, 0f, 0f);
        Camera.translatePoint(x, y - currentRadius);
        Camera.translatePoint(x - l, y - l);
        Camera.translatePoint(x - currentRadius, y);
        Camera.translatePoint(x - l, y + l);
        Camera.translatePoint(x, y + currentRadius);
        Camera.translatePoint(x + l, y + l);
        Camera.translatePoint(x + currentRadius, y);
        Camera.translatePoint(x + l, y - l);
        Camera.translatePoint(x, y - currentRadius);

        glEnd();
        }

    }

    @Override
    public void update(float deltaTime) {
        if (timer<=0) {
        float delta=(currentRadius*4+radius)/5-currentRadius;
        currentRadius+=delta*10*deltaTime;
        }
        else timer--;
    }

    @Override
    public boolean noNeedMore() {
        return radius<=currentRadius || Math.abs(currentRadius-radius)< 1f;
    }
}

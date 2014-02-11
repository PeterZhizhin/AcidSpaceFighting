package com.company.Graphic.Effects;

import com.company.Geometry.Point;
import com.company.Graphic.TextureDrawer;
import com.company.Graphic.Posteffect;

import static org.lwjgl.opengl.GL11.*;

public class Smoke implements Posteffect {

    private float radius;
    private float currentRadius;
    private float x, y;

    public Smoke(float x, float y, float radius) {
        currentRadius=radius/1000f;
        this.radius=radius;
        this.x=x;
        this.y=y;
    }

    @Override
    public void draw() {
        glColor4f(1f, 1f, 1f, 1-currentRadius/radius);
        float l=currentRadius*2/3;
        TextureDrawer.drawQuad(new Point(x - l, y), new Point(x, y - l), new Point(x + l, y), new Point(x, y + l), 7);
        glColor3f(1f, 1f, 1f);
    }

    @Override
    public void update(float deltaTime) {
        float delta=(currentRadius*4+radius)/5-currentRadius;
        currentRadius+=delta*10*deltaTime;
    }

    @Override
    public boolean noNeedMore() {
        return radius<=currentRadius || Math.abs(currentRadius-radius)< 1f;
    }
}

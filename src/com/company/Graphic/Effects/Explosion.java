package com.company.Graphic.Effects;

import com.company.Geometry.Point;
import com.company.Geometry.Segment;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Posteffect;
import com.company.Graphic.Tale;

import java.util.Random;

public class Explosion implements Posteffect {

    private float radius;
    private float currentRadius;
    private Tale[] tales;
    private Segment [] centers;
    private int size=200;
    private static final Random rnd=new Random();

    public Explosion(float x, float y, float power) {
        tales=new Tale[size];
        centers=new Segment[size];
        for (int i=0; i<size; i++) {
            double angle=rnd.nextFloat()*Math.PI*2;
            float length=rnd.nextFloat()*rnd.nextFloat()*rnd.nextFloat();
            centers[i]=new Segment(x, y, (float)Math.cos(angle)*length*power, (float)Math.sin(angle)*length*power);
            tales[i]=new Tale(new Color(1f, 1f, 0f), new Color(1f, 0f, 0f), 10, 10, 5, 6, true);
        }
        currentRadius=power/1000f;
        radius=power;
    }

    public Explosion(Point p, float power) {
        this(p.x, p.y, power);
    }

    @Override
    public void draw() {
        for (Tale t: tales) t.draw();
    }

    @Override
    public void update(float deltaTime) {
        float width=(radius-currentRadius)*2;
        for (int i=0; i<size; i++) {
            centers[i].getStart().move(centers[i].getEnd());
            if (rnd.nextBoolean()) centers[i].getEnd().set(centers[i].getEnd().multiply(0.965f));
            tales[i].addPoint(centers[i].getStart(), width);
        }
        float delta=(currentRadius*3+radius)/4-currentRadius;
        currentRadius+=delta*10*deltaTime;

    }

    @Override
    public boolean noNeedMore() {
        return radius<=currentRadius || Math.abs(currentRadius-radius)< 1f;
    }
}

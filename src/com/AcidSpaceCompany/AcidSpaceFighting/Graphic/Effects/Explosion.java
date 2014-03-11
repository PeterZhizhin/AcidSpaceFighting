package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Segment;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

import java.util.Random;

public class Explosion implements Effect {

    private float radius;
    private float currentRadius;
    private Tale[] tales;
    private Segment [] centers;
    private int size=40;
    private static final Random rnd=new Random();

    public int getEfectType() {
        return 2;
    }

    public Explosion(float x, float y, float power) {
        tales=new Tale[size];
        centers=new Segment[size];
        for (int i=0; i<size; i++) {
            centers[i]=new Segment(x, y, rnd.nextFloat()*(rnd.nextFloat()-0.5f)*power, rnd.nextFloat()*(rnd.nextFloat()-0.5f)*power);
            tales[i]=new Tale(40, 20, 5, 15, true);
            World.addEffect(tales[i]);
        }
        currentRadius=power/1000f;
        radius=power;
    }

    public Explosion(Point p, float power) {
        this(p.x, p.y, power);
    }

    @Override
    public void draw() {
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
        if (noNeedMore()) destroy();
    }

    public void destroy() {
        for (Tale t: tales) {
            t.destroy();
        }
    }

    @Override
    public boolean noNeedMore() {
        return radius<=currentRadius || Math.abs(currentRadius-radius)< 1f;
    }
}

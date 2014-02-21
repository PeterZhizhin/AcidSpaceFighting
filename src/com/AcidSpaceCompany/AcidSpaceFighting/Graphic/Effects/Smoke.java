package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

import static org.lwjgl.opengl.GL11.*;

public class Smoke implements Effect {

    private float [] radius;
    private float [] currentRadius;
    private float [] x, y;
    private int size;
    private boolean noMoreNeeded;


    public Smoke(int size) {
        this.size=size;
        currentRadius=new float[size];
        radius=new float[size];
        this.x=new float[size];
        this.y=new float[size];
        for (int i=0; i<size; i++)  {
            //currentRadius[i]=radius/1000f;
            currentRadius[i]=0;
            radius[i]=-1;
            this.x[i]=0;
            this.y[i]=0;
        }
    }

    private int last;
    public void addPoint(float x, float y, float width) {
        radius[last]=width;
        currentRadius[last]=radius[last]/1000f;
        this.x[last]=x;
        this.y[last]=y;
        last++;
        if (last>=size) last=0;
    }

    @Override
    public void draw() {
        for (int i=0; i<size; i++) {
            if (currentRadius[i]>=0) {
            glColor4f(1f, 1f, 1f, 1-currentRadius[i]/radius[i]);
            float l=currentRadius[i]*2/3;
            TextureDrawer.drawQuad(new Point(x[i] - l, y[i]), new Point(x[i], y[i] - l),
                    new Point(x[i] + l, y[i]), new Point(x[i], y[i] + l), 7);
            glColor3f(1f, 1f, 1f);
            }
        }
    }

    private boolean getElementIsUsed(int i) {
        return (Math.abs(currentRadius[i]-radius[i])< 1f || radius[i]<=currentRadius[i]);
    }

    @Override
    public void update(float deltaTime) {
        for (int i=0; i<size; i++) {
            if (currentRadius[i]!=-1) {
            float delta=(currentRadius[i]*4+radius[i])/5-currentRadius[i];
            currentRadius[i]+=delta*10*deltaTime;
            if (getElementIsUsed(i))
                currentRadius[i]=-1;
            }
        }
    }

    public void destroy() {
        noMoreNeeded=true;
    }

    @Override
    public boolean noNeedMore() {
        if (!noMoreNeeded) return false;
        else {
            for (int i=0; i<size; i++) {
                if (currentRadius[i]>=0) return false;
            }
        }
        return true;
    }
}

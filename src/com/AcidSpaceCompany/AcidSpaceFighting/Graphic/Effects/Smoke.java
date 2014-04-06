package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.ShadersBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;

public class Smoke implements Effect {

    private float [] radius;
    private float [] currentRadius;
    private float [] x, y;
    private int size;
    private boolean noMoreNeeded;
    private float interval;

    public int getEfectType() {
        return 1;
    }

    public Smoke(int size, float interval) {
        this.interval=interval;
        this.size=size;
        currentRadius=new float[size];
        radius=new float[size];
        this.x=new float[size];
        this.y=new float[size];
        for (int i=0; i<size; i++)  {
            currentRadius[i]=0;
            radius[i]=-1;
            this.x[i]=0;
            this.y[i]=0;
        }
    }

    private int last;
    private float time=0f;

    public void addPoint(float x, float y, float width) {
        if (time>=interval) {
            time-=interval;
            radius[last] = width;
            currentRadius[last] = radius[last] / 1000f;
            this.x[last] = x;
            this.y[last] = y;
            last++;
            if (last >= size) last = 0;
        }
    }

    @Override
    public void draw() {
        TextureDrawer.startDrawSmoke();
        for (int i=0; i<size; i++) {
            if (currentRadius[i]>=0) {
                ShadersBase.setFloatValue(ShadersBase.smokeStateID, 1f-currentRadius[i]/radius[i]);
                TextureDrawer.drawQuadWIdthoutBeginAndEnd(x[i], y[i], currentRadius[i]);
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
        time+=deltaTime;
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

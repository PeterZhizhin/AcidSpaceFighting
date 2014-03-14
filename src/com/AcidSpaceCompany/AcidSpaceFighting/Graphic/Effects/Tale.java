package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.ShadersBase;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

import java.util.LinkedList;
import java.util.Random;

public class Tale implements Effect {

    private float deltaPosition;
    private Random rnd=new Random();
    private LinkedList<Point> coordinates;
    private Point lastCoord;
    private LinkedList<Float> widths;
    private LinkedList<Float> widths01;
    private int size;
    private float interval;
    private float timer;
    private boolean useSmoke;
    private Smoke smoke;
    private float smokeCoef;
    private boolean noNeedMore=false;

    public int getEfectType() {
        return 0;
    }

    private Point getPoint(int i) {
        return coordinates.get(Math.min(coordinates.size() - 1, i));
    }

    public void draw() {

        TextureDrawer.startDrawFire();
        Point normal = Point.empty;
        Point p1, p2, p3, p4;
        p1=getPoint(0).add(normal);
        p2=getPoint(0).add(normal.negate());
        for (int i = 0; i < size-1; i++)
            if (widths01.get(i)>=0) {

                float ww=widths.get(i)*widths01.get(i);

            normal = Point.getBisection(getPoint(i),getPoint(i + 1),
                    getPoint(i + 2)).multiply(ww);


            p3=getPoint(i + 1).add(normal.negate());
            p4=getPoint(i + 1).add(normal);
            ShadersBase.setFloatValue(ShadersBase.fireStateID, widths01.get(i));
            TextureDrawer.drawQuadWIdthoutBeginAndEnd(p2, p3, p4, p1);
            p1=p3;
            p2=p4;
        }
        TextureDrawer.drawQuadWIdthoutBeginAndEnd(p2, lastCoord, lastCoord, p1);
    }

    public void update(float deltaTime) {
        for (int i=0; i<widths.size(); i++)
        {
            widths01.set(i, widths01.get(i)-deltaTime);
        }
        timer+=deltaTime;
    }

    public boolean noNeedMore() {
        return noNeedMore;// && smoke.noNeedMore();
    }

    public void addPoint(Point coordinate, float width) {

        if (timer>=interval) {

        coordinates.add(coordinate.add(new Point((rnd.nextFloat() - 0.5f), (rnd.nextFloat() - 0.5f)).multiply(deltaPosition)));

            timer=0;
            if (useSmoke)
                smoke.addPoint(coordinate.x, coordinate.y, width * smokeCoef);

            widths.add(width);
            widths01.add(1f);
            if (coordinates.size()>size) {
                coordinates.remove(0);
                widths.remove(0);
                widths01.remove(0);
            }
        }
        lastCoord=coordinate;
    }

    public void destroy() {
        if (useSmoke) smoke.destroy();
        noNeedMore=true;
    }

    public Tale(float deltaPos, int size, float interv, int smokeCoef, boolean useSmoke) {
        interval=interv;
        this.size=size;
        coordinates=new LinkedList<Point>();
        widths=new LinkedList<Float>();
        widths01=new LinkedList<Float>();
        for (int i=0; i<size; i++) {
            coordinates.add(new Point(0, 0));
            widths.add(0f);
            widths01.add(0f);
        }
        lastCoord=new Point(0, 0);
        deltaPosition=deltaPos;
        this.useSmoke=useSmoke;
        if (useSmoke) {
            smoke =new Smoke(size);
            World.addEffect(smoke);
        }
        this.smokeCoef=smokeCoef;
    }



}

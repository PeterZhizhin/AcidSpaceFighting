package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Controls.Color;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.World;

import java.util.LinkedList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Tale implements Effect {

    private Color[] gradations;
    private float deltaPosition;
    private Random rnd=new Random();
    private LinkedList<Point> coordinates;
    private LinkedList<Float> widths;
    private int size;
    private int interval;
    private int timer;
    private boolean useSmoke;
    private Smoke smoke;
    private int smokeCoef;
    private boolean noNeedMore=false;

    public void draw() {

        Point normal = Point.empty;
        gradations[size-1].bind();

        for (int i = 0; i < size - 2; i++) {


            Point p1=coordinates.get(i).add(normal);
            Point p2=coordinates.get(i).add(normal.negate());

            normal = Point.getBisection(coordinates.get(i), coordinates.get(i+1), coordinates.get(i+2));
            normal = normal.multiply(widths.get(i)*i/size);
            gradations[i].bind();

            Point p3=coordinates.get(i+1).add(normal.negate());
            Point p4=coordinates.get(i+1).add(normal);

            TextureDrawer.drawQuad(p1, p2, p3, p4, 6);
             }
        glColor3f(1f, 1f, 1f);
    }

    public void update(float deltaTime) {
    }

    public boolean noNeedMore() {
        return noNeedMore;// && smoke.noNeedMore();
    }

    public void addPoint(Point coordinate, float width) {

        timer++;
        if (timer>=interval) {

        coordinates.add(coordinate.add(new Point((rnd.nextFloat()-0.5f), (rnd.nextFloat()-0.5f)).multiply(deltaPosition)));

            timer=0;
            if (useSmoke) {
                smoke.addPoint(coordinate.x, coordinate.y, width * smokeCoef);

                width*=4;
                widths.add(width);
                if (coordinates.size()>size) {
                    coordinates.remove(0);
                    widths.remove(0);
                }
            }
        }
        else {
            coordinates.set(size-1, coordinate.add(new Point((rnd.nextFloat()-0.5f), (rnd.nextFloat()-0.5f)).multiply(deltaPosition)));
            widths.set(size-1, width);
        }
    }

    public void destroy() {
        if (useSmoke) smoke.destroy();
        noNeedMore=true;
    }

    public Tale(Color start, Color end, float deltaPos, int size, int interv, int smokeCoef, boolean useSmoke) {
        interval=interv;
        this.size=size;
        coordinates=new LinkedList<Point>();
        widths=new LinkedList<Float>();
        gradations=new Color[size];
        for (int i=0; i<size; i++) {
            gradations[i]=new Color(start, end, 1f*i/size);
            coordinates.add(new Point(0, 0));
            widths.add(0f);
        }
        deltaPosition=deltaPos;
        this.useSmoke=useSmoke;
        if (useSmoke) {
            smoke =new Smoke(size);
            World.addEffect(smoke);
        }
        this.smokeCoef=smokeCoef;
    }



}

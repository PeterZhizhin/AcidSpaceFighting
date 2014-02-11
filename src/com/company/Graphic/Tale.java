package com.company.Graphic;

import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Effects.Smoke;
import com.company.World;

import java.util.LinkedList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Tale {

    private Color[] gradations;
    private float deltaPosition;
    private Random rnd=new Random();
    private LinkedList<Point> coordinates;
    private LinkedList<Float> widths;
    private int size;
    private int interval;
    private int timer;
    private boolean useSmoke;
    private int smokeCoef;

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

    public void addPoint(Point coordinate, float width) {

        timer++;
        if (timer>=interval) {

        coordinates.add(coordinate.add(new Point((rnd.nextFloat()-0.5f), (rnd.nextFloat()-0.5f)).multiply(deltaPosition)));

            timer=0;
            if (useSmoke) {
            Smoke s=new Smoke(coordinate.x, coordinate.y, width*smokeCoef);
            World.addEffect(s);

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
        this.smokeCoef=smokeCoef;
    }



}

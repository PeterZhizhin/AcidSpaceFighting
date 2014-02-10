package com.company.Graphic;

import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Effects.Smoke;
import com.company.World;

import java.util.LinkedList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

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

        glBegin(GL_QUADS);
        Point normal = Point.empty;
        gradations[size-1].bind();

        for (int i = 0; i < size - 2; i++) {



            Camera.translatePoint(coordinates.get(i).getX() + normal.getX(), coordinates.get(i).getY() + normal.getY());
            Camera.translatePoint(coordinates.get(i).getX() - normal.getX(), coordinates.get(i).getY() - normal.getY());

            normal = Point.getBisection(coordinates.get(i), coordinates.get(i+1), coordinates.get(i+2));
            normal = normal.multiply(widths.get(i)*i/size);
            gradations[i].bind();

            Camera.translatePoint(coordinates.get(i + 1).getX() - normal.getX(), coordinates.get(i + 1).getY() - normal.getY());
            Camera.translatePoint(coordinates.get(i + 1).getX() + normal.getX(), coordinates.get(i + 1).getY() + normal.getY());
        }

        glEnd();
    }

    public void addPoint(Point coordinate, float width) {
        timer++;
        if (timer>=interval) {

        coordinates.add(coordinate.add(new Point((rnd.nextFloat()-0.5f), (rnd.nextFloat()-0.5f)).multiply(deltaPosition)));
        widths.add(width);
        if (coordinates.size()>size) {
            coordinates.remove(0);
            widths.remove(0);
        }
            timer=0;
            if (useSmoke) {
            Smoke s=new Smoke(coordinate.x, coordinate.y, width*smokeCoef);
            World.addEffect(s);     }
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

package com.company.Graphic;

import com.company.Geometry.Point;
import com.company.Graphic.Controls.Color;
import com.company.Graphic.Controls.FontDrawer;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Background {

    public static void draw() {
        glEnable(GL_TEXTURE_2D);
        TextureDrawer.drawBackground();
        FontDrawer.drawString(10, 10,
                Camera.getSourcePoint(new Point(10, 10)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(Display.getWidth()-150, Display.getHeight()-50,
                Camera.getSourcePoint(new Point(Display.getWidth()-150, Display.getHeight()-50)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(10, Display.getHeight()-50,
                Camera.getSourcePoint(new Point(10, Display.getHeight()-50)).toString(),
                new Color(1f, 1f, 1f), false);

        FontDrawer.drawString(Display.getWidth()-150, 10,
                Camera.getSourcePoint(new Point(Display.getWidth()-150, 10)).toString(),
                new Color(1f, 1f, 1f), false);

        glDisable(GL_TEXTURE_2D);
    }

}

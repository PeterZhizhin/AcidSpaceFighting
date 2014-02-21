package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureDrawer {

    private static int textures;
    private static int noise;
    private static int background;

    public static void drawBackground() {
        glBindTexture(GL11.GL_TEXTURE_2D, background);
        glColor4f(1f, 1f, 1f, 1f);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1f, 0f);
        glVertex2f(Display.getWidth(), 0);
        glTexCoord2f(1f, 1f);
        glVertex2f(Display.getWidth(), Display.getHeight());
        glTexCoord2f(0, 1f);
        glVertex2f(0, Display.getHeight());
        glEnd();
    }

    public static void startDrawNoise() {
        glBindTexture(GL11.GL_TEXTURE_2D, noise);
        glColor4f(1f, 1f, 1f, 1f);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
    }

    public static void startDrawTextures() {
        glBindTexture(GL11.GL_TEXTURE_2D, textures);
        glColor4f(1f, 1f, 1f, 1f);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
    }

    public static void finishDraw() {
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    public static void drawQuad(Point p1, Point p2, Point p3, Point p4) {
        glTexCoord2f(0, 0);
        Camera.translatePoint(p1);
        glTexCoord2f(1f, 0);
        Camera.translatePoint(p2);
        glTexCoord2f(1f, 1f);
        Camera.translatePoint(p3);
        glTexCoord2f(0f, 1f);
        Camera.translatePoint(p4);
    }

    public static void drawQuad(Point p1, Point p2, Point p3, Point p4, int texture) {

        Point tp=new Point(0, 0);

        switch (texture) {
            case 0: {tp=new Point(0, 0); break;}
            case 1: {tp=new Point(0.25f, 0); break;}
            case 2: {tp=new Point(0, 0.25f); break;}
            case 3: {tp=new Point(0.25f, 0.25f); break;}
            case 4: {tp=new Point(0, 0.5f); break;}
            case 5: {tp=new Point(0.25f, 0.5f); break;}
            case 6: {tp=new Point(0, 0.75f); break;}
            case 7: {tp=new Point(0.25f, 0.75f); break;}

            case 8: {tp=new Point(0.5f, 0.75f); break;}
            case 9: {tp=new Point(0.5f, 0.5f); break;}
            case 10: {tp=new Point(0.5f, 0.25f); break;}
            case 11: {tp=new Point(0.5f, 0f); break;}
            case 12: {tp=new Point(0.75f, 0.75f); break;}
        }


        glTexCoord2f(tp.x, tp.y);
        Camera.translatePoint(p1);
        glTexCoord2f(tp.x+0.25f, tp.y);
        Camera.translatePoint(p2);
        glTexCoord2f(tp.x+0.25f, tp.y+0.25f);
        Camera.translatePoint(p3);
        glTexCoord2f(tp.x, tp.y+0.25f);
        Camera.translatePoint(p4);
    }

    private static int load (String title) {
        try {
            InputStream input = TextureDrawer.class.getResourceAsStream("Textures/"+title);
            PNGDecoder dec = new PNGDecoder(input);
            int width = dec.getWidth();
            int height = dec.getHeight();
            final int bpp = 4;
            ByteBuffer buf = BufferUtils.createByteBuffer(bpp * width * height);
            dec.decode(buf, width * bpp, PNGDecoder.Format.RGBA);
            buf.flip();
            int ret = glGenTextures();


            glBindTexture(GL_TEXTURE_2D, ret);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
            return ret;
        } catch (IOException e) {
            System.err.println("[TextureDrawer] Failed to init");
        }
        return -1;
    }

    public static void init() {
        textures=load("Textures.png");
        background=load("Background.png");
        noise=load("Noise.png");
    }

}

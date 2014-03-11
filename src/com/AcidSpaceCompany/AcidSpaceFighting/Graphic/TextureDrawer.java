package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureDrawer {

    private static int noise;
    private static int background;
    private static int white;
    private static int fire;
    private static int smoke;
    private static int[] blocks;
    private static int[] damages;

    public static void drawBackground() {
        ShadersBase.bindTexture(ShadersBase.backgroundTextureID, background);
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
        ShadersBase.bindTexture(ShadersBase.backgroundTextureID, noise);
    }

    public static void startDrawTextures() {
        ShadersBase.use(ShadersBase.block);
    }

    public static void startDrawSmoke() {
        if (ShadersBase.use(ShadersBase.smoke))      {
        ShadersBase.bindTexture(ShadersBase.smokeID, smoke) ;
            glBegin(GL_QUADS);
        }
    }

    public static void startDrawFire() {
        if (ShadersBase.use(ShadersBase.fire)) {
        ShadersBase.bindTexture(ShadersBase.fireID, fire);
            glBegin(GL_QUADS);
        }
    }

    public static void finishDraw() {
        ShadersBase.drop();
    }

    public static void drawBlock(Point p1, Point p2, Point p3, Point p4, int block, float health) {

        int damage=Math.round((1-health)*4);

        ShadersBase.bindTexture(ShadersBase.blockID, blocks[block]);

        if (damage>=5)
        ShadersBase.bindSecondTexture(ShadersBase.damageID, white);
        else
            ShadersBase.bindSecondTexture(ShadersBase.damageID, damages[damage]);

        drawQuad(p1, p2, p3, p4);
    }

    public static void drawQuadWIdthoutBeginAndEnd(float x, float y, float r) {
        glTexCoord2f(0, 0);
        Camera.translatePoint(x+r, y);
        glTexCoord2f(1, 0);
        Camera.translatePoint(x, y+r);
        glTexCoord2f(1, 1);
        Camera.translatePoint(x-r, y);
        glTexCoord2f(0, 1);
        Camera.translatePoint(x, y-r);
    }

    public static void drawQuadWIdthoutBeginAndEnd(Point p1, Point p2, Point p3, Point p4) {
        glTexCoord2f(0, 0);
        Camera.translatePoint(p1);
        glTexCoord2f(1, 0);
        Camera.translatePoint(p2);
        glTexCoord2f(1, 1);
        Camera.translatePoint(p3);
        glTexCoord2f(0, 1);
        Camera.translatePoint(p4);
    }

    public static void drawQuad(Point p1, Point p2, Point p3, Point p4) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        Camera.translatePoint(p1);
        glTexCoord2f(1, 0);
        Camera.translatePoint(p2);
        glTexCoord2f(1, 1);
        Camera.translatePoint(p3);
        glTexCoord2f(0, 1);
        Camera.translatePoint(p4);
        glEnd();
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
            System.out.println("[TextureDrawer]Texture " + title + " loaded");
            return ret;
        } catch (IOException e) {
            System.err.println("[TextureDrawer] Failed to load texture "+title);
        }
        return -1;
    }

    public static void init() {
        blocks=new int[6];
        for (int i=0; i<6; i++)
            blocks[i]=load("Block"+i+".png");

        damages=new int[5];
        for (int i=0; i<5; i++)
            damages[i]=load("Damage"+i+".png");

        background=load("Background.png");
        noise=load("Noise.png");
        fire=load("Fire.png");
        smoke=load("Smoke.png");
    }

}

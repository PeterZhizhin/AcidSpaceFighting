package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureDrawer {

    private static int noise;
    private static int background;
    private static int white;
    private static int achiveCorner;
    private static int achiveMain;
    private static int fire;
    private static int smoke;
    private static int font;
    private static int[] blocks;
    private static int[] damages;

    private static final boolean hardDebug=false;

    private static float value=0f;
    public static void drawBackground(boolean retro) {

        //if (retro) {
            ShadersBase.use(ShadersBase.blackAndWhite);
            if (retro) value=Mouse.getY()*2f/Display.getHeight()+2f-Mouse.getX()*2f/Display.getWidth();
            else if (value>0) value-=0.05f; //todo: time timer
            ShadersBase.setFloatValue(ShadersBase.stateForBAWID, value);

            ShadersBase.bindTexture(ShadersBase.textureForBAWID, background);
        //}
        //else {
        //    ShadersBase.use(ShadersBase.defaultShader);
        //    ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, background);
        //}

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
        //if (retro)
            finishDraw();
    }

    public static void startDrawNoise() {
        ShadersBase.use(ShadersBase.defaultShader);
        ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, noise);
    }

    public static void startDrawTextures() {
        ShadersBase.use(ShadersBase.block);
        glColor3f(1f, 1f, 1f);
    }

    public static void startDrawConnections() {
        ShadersBase.use(ShadersBase.defaultShader);
        ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, white);
        glColor3f(1f, 0.5f, 0f);
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

    public static void startDrawControls() {
        ShadersBase.use(ShadersBase.defaultShader);
        ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, white);
    }

    public static void startDrawText() {
        ShadersBase.use(ShadersBase.defaultShader);
            ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, font);
        }

    private static final float fontWidth=32f;

    public static void drawMessage(float dy, String title, String text) {
        ShadersBase.use(ShadersBase.defaultShader);
        ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, achiveCorner);

        float width=Font.getWidth(fontWidth, Math.max(title.length(), text.length()));

        float xMainEnd= Display.getWidth()-10;
        float xMainStart= xMainEnd-width;
        float xFigStart= xMainStart-(Display.getHeight()/7-10);
        float yMainStart= Display.getHeight()*6/7;
        float yMainEnd= Display.getHeight()-10;

        float yD= Display.getHeight()/7;

        yMainEnd+=dy*yD;
        yMainStart+=dy*yD;

        glBegin(GL_QUADS);
        drawUntranslatedQuad(xFigStart, yMainStart, xMainStart, yMainEnd);
        glEnd();

        ShadersBase.bindTexture(ShadersBase.textureForDefaultShaderID, achiveMain);
        glBegin(GL_QUADS);
        drawUntranslatedQuad(xMainStart, yMainStart, xMainEnd, yMainEnd);
        glEnd();

        startDrawText();
        glBegin(GL_QUADS);
        Font.drawString(xMainStart, yMainStart+12, fontWidth, title);
        Font.drawString(xMainStart, yMainStart+46, fontWidth, text);
        glEnd();
        finishDraw();

    }

    public static void drawUntranslatedQuad(float sx, float sy, float ex, float ey) {
        glTexCoord2f(0, 0);
        glVertex2f(sx, sy);
        glTexCoord2f(1, 0);
        glVertex2f(ex, sy);
        glTexCoord2f(1, 1);
        glVertex2f(ex, ey);
        glTexCoord2f(0, 1);
        glVertex2f(sx, ey);
    }

    public static void finishDraw() {
        ShadersBase.drop();
    }

    public static void drawConnection(Point pa, Point pb, float width) {

        Point normal=Point.getNormal(pa, pb);
        Point p1=pa.add(normal.setLength(width));
        Point p2=pa.add(normal.negate().setLength(width));
        Point p3=pb.add(normal.negate().setLength(width));
        Point p4=pb.add(normal.setLength(width));

        drawQuadWIdthoutBeginAndEnd(p1, p2, p3, p4);
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
            if (hardDebug) System.out.println("[TextureDrawer]Texture " + title + " loaded");
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
        achiveCorner=load("AchiveCorner.png");
        achiveMain=load("AchiveMain.png");
        font=load("Font.png");
    }

}

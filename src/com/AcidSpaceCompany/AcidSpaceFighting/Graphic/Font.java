package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Font {

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789.,<>/?;:'\"[]{}\\|`~!@#$%^&*()-_=+";


    public static void drawString(float x, float y, float size, String s) {
        float sx=x;
        float sizeW=size*3/4;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i)!=' ') {
            int num=alphabet.indexOf(s.charAt(i));
            float xx=(num%16)/16f;
            float yy=(num/16)/16f;
            drawGlyph(sx, y, size, xx, yy);
            }
            sx+=sizeW;
        }

    }

    private static final float onePer16= 0.0625f;
    public static void drawGlyph(float x, float y, float size, float gx, float gy) {
        glTexCoord2f(gx, gy);
        glVertex2f(x, y);
        glTexCoord2f(gx+onePer16, gy);
        glVertex2f(x+size, y);
        glTexCoord2f(gx+onePer16, gy+onePer16);
        glVertex2f(x+size, y+size);
        glTexCoord2f(gx, gy+onePer16);
        glVertex2f(x, y+size);
    }

}

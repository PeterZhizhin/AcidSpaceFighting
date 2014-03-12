package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttrib1f;

public class ShadersBase {

    private static Shader [] shaders;

    public static int fire=0;
    public static int block=1;
    public static int defaultShader =2;
    public static int smoke=3;
    public static int blackAndWhite=4;

    private static int current=-1;


    public static void setFloatValue(int num, float value) {
        glVertexAttrib1f(num, value);
    }

    public static void bindSecondTexture(int name, int num) {
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, num);
        ARBShaderObjects.glUniform1iARB(name, 1);
    }

    public static void bindTexture(int name, int num) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, num);
        ARBShaderObjects.glUniform1iARB(name, 0);
    }

    public static boolean use(int i) {
        if (i!=current) {
            glEnd();
            shaders[i].use();
            current=i;
            return true;
        }
        return false;
    }

    public static void drop() {
        Shader.use(0);
    }

    public static int blockID;
    public static int damageID;
    public static int textureForDefaultShaderID;
    public static int textureForBAWID;
    public static int stateForBAWID;
    public static int fireID;
    public static int fireStateID;
    public static int smokeID;
    public static int smokeStateID;

    public static void init() {
        shaders=new Shader[5];
        shaders[fire]=new Shader("fire");
        fireID = glGetUniformLocationARB(shaders[fire].getNumber(), "Fire");
        fireStateID = glGetAttribLocation(shaders[fire].getNumber(), "State");

        shaders[block]=new Shader("block");
        blockID= glGetUniformLocationARB(shaders[block].getNumber(), "Block");
        damageID= glGetUniformLocationARB(shaders[block].getNumber(), "Damage");

        shaders[defaultShader]=new Shader("default");
        textureForDefaultShaderID = glGetUniformLocationARB(shaders[defaultShader].getNumber(), "Texture");

        shaders[smoke]=new Shader("smoke");
        smokeID= glGetUniformLocationARB(shaders[smoke].getNumber(), "Smoke");
        smokeStateID = glGetAttribLocation(shaders[smoke].getNumber(), "State");

        shaders[blackAndWhite]=new Shader("blackAndWhite");
        textureForBAWID= glGetUniformLocationARB(shaders[blackAndWhite].getNumber(), "Texture");
        stateForBAWID= glGetAttribLocation(shaders[blackAndWhite].getNumber(), "State");
    }

}

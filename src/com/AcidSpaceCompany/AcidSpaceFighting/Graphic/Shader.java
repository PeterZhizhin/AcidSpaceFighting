package com.AcidSpaceCompany.AcidSpaceFighting.Graphic;

import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shader {

    private int shader, vertShader, fragShader;
    private static final boolean hardDebug=false;

    public int getNumber() {
        return shader;
    }

    public Shader (String s) {
        shader= ARBShaderObjects.glCreateProgramObjectARB();
        if(shader!=0){
            vertShader=createShader(loadCode("Shaders/"+s+".vert"), ARBVertexShader.GL_VERTEX_SHADER_ARB);
            if (hardDebug) System.out.println("[Shader] Vertex shader code loaded: "+s);
            fragShader=createShader(loadCode("Shaders/"+s+".frag"), ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
            if (hardDebug) System.out.println("[Shader] Fragment shader code loaded: "+s);
        }else  {
            System.err.println("[Shader] Failed to create shaders");
        }
        if(vertShader!=0 && fragShader!=0){
            ARBShaderObjects.glAttachObjectARB(shader, vertShader);
            if (hardDebug) System.out.println("[Shader] Vertex shader code attached: "+s);
            ARBShaderObjects.glAttachObjectARB(shader, fragShader);
            if (hardDebug) System.out.println("[Shader] Fragment shader code attached: "+s);
            ARBShaderObjects.glLinkProgramARB(shader);
            ARBShaderObjects.glValidateProgramARB(shader);


            if (ARBShaderObjects.glGetObjectParameteriARB(shader,
                    ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
                System.err.println("[Shader] "+getLogInfo(shader));
            } else
            if (hardDebug) System.out.println("[Shader] Shader ready to work: "+s);
        }else {
            System.err.println("[Shader] Failed to compile shader");
        }
    }

    public void use() {
        use(shader);
    }

    public static void use(int num) {
        ARBShaderObjects.glUseProgramObjectARB(num);
    }

    public void drop() {
        ARBShaderObjects.glUseProgramObjectARB(0);
    }

    private static String loadCode(String filename) {
        String code="";
        String line;

        InputStream in = Shader.class.getResourceAsStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((line = reader.readLine()) != null) {code+=line + "\n";
            }
        } catch (IOException e) {
            System.err.println("[Shader] Failed to load shader code: "+filename);
        }

        return code;
    }

    private int createShader(String code, int type){

        int shaderNum = ARBShaderObjects.glCreateShaderObjectARB(type) ;

        if(shaderNum==0){
            System.err.println("[Shader] Failed to get vertex shader number");
            return 0;
        }

        ARBShaderObjects.glShaderSourceARB(shaderNum, code);
        ARBShaderObjects.glCompileShaderARB(shaderNum);

        if (ARBShaderObjects.glGetObjectParameteriARB(shader,
                ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
            System.err.println("[Shader] "+getLogInfo(shader));

        return shaderNum;
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj,
                ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}

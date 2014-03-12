
        uniform sampler2D Texture;
        varying float StateVar;


                void main(){
                vec4 theColor=texture2D(Texture, (gl_TexCoord[0].st));
                float r=theColor.x*theColor.w;
                float g=theColor.y*theColor.w;
                float b=theColor.z*theColor.w;
                float a=theColor.w*theColor.w;

                float s=r+g+b;
                s*=StateVar;

                gl_FragColor = vec4(s+r, s+g, s+b, a);
                }
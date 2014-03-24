
        uniform sampler2D Texture;
        varying float StateVar;


                void main(){
                vec4 theColor=texture2D(Texture, (gl_TexCoord[0].st));

                float r=theColor.x;
                float g=theColor.y;
                float b=theColor.z;
                float a=theColor.w;

                float s=r+g+b;
                s*=StateVar;

                gl_FragColor = vec4(s+r, s+g, s+b, a);
                }
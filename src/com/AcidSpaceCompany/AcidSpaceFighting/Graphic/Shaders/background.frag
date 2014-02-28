
        uniform sampler2D Texture;

        void main(){
        vec2 pos=gl_TexCoord[0].st;

        //if (pos.x>1) pos.x=0.5;
        //if (pos.y>1) pos.y=0.5;

        vec4 theColor2=texture2D(Texture, (pos));
        float r=theColor2.x;
        float g=theColor2.y;
        float b=theColor2.z;
        float a=theColor2.w;

        gl_FragColor = vec4(r, g, b, a);
        }
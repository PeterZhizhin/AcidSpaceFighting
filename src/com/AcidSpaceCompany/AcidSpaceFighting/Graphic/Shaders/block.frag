

        uniform sampler2D Block;
        uniform sampler2D Damage;

        void main(){
        vec4 theColor2=texture2D(Block, (gl_TexCoord[0].st));
        vec4 theColor=texture2D(Damage, (gl_TexCoord[0].st));
        float r=theColor2.x-theColor.x*theColor.w;
        float g=theColor2.y-theColor.y*theColor.w;
        float b=theColor2.z-theColor.z*theColor.w;
        float a=theColor2.w-theColor.x*theColor.w;

        gl_FragColor = vec4(r, g, b, a);
        }
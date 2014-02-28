
        varying float StateVar;
        uniform sampler2D Smoke;

        void main(){
            vec4 theColor2=texture2D(Smoke, (gl_TexCoord[0].st));
            float a=theColor2.w*StateVar;
            gl_FragColor = vec4(0, 0, 0, a);
        }
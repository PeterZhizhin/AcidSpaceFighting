        uniform sampler2D Fire;
        varying float StateVar;

        void main(){
            vec4 theColor2=texture2D(Fire, (gl_TexCoord[0].st));

            float costst=theColor2.x*StateVar*StateVar;


            float a=costst*4f;

            float r=costst*4f;
            float g=(costst-0.25f)*4f;
            float b=(costst-0.4f)*4f;

            if (StateVar<0.5f) {
                float c=StateVar*2f;
                r*=c;
                g*=c;
                b*=c;
            }


            gl_FragColor = vec4(r, g, b, a);
        }
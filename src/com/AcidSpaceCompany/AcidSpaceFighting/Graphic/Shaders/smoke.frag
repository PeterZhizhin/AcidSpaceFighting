
        varying float StateVar;
        uniform sampler2D Smoke;

        void main(){
            gl_FragColor = vec4(0, 0, 0, texture2D(Smoke, (gl_TexCoord[0].st)).w*StateVar);
        }
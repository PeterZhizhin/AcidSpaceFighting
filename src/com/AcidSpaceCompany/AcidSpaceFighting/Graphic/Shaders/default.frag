
        uniform sampler2D Texture;
        varying vec4 color;

        void main(){
        vec4 color2 =texture2D(Texture, gl_TexCoord[0].st);
        gl_FragColor = color2*color;
        }
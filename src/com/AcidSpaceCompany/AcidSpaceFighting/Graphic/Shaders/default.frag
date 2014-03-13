
        uniform sampler2D Texture;
        varying vec4 color;

        void main(){
        vec4 color2 =texture2D(Texture, gl_TexCoord[0].st);
        //float r=color2.x*color.x;
        //float g=color2.y*color.y;
        //float b=color2.z*color.z;
        //float a=color2.w*color.w;
        gl_FragColor = color2*color;//vec4(r, g, b, a);
        }
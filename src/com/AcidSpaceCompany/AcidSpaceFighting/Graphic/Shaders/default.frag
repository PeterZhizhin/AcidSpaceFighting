
        uniform sampler2D Texture;

        void main(){
        vec4 color =texture2D(Texture, gl_TexCoord[0].st)*gl_Color;
        float r=color.x*gl_Color.x;
        float g=color.y*gl_Color.y;
        float b=color.z*gl_Color.z;
        float a=color.w*(1-gl_Color.w);
        gl_FragColor = vec4(r, g, b, a);
        }
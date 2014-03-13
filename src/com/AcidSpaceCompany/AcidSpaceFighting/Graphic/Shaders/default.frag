
        uniform sampler2D Texture;

        void main(){
        vec4 color =texture2D(Texture, gl_TexCoord[0].st);
        float r=color.x*(1f-gl_Color.x);
        float g=color.y*(1f-gl_Color.y);
        float b=color.z*(1f-gl_Color.z);
        float a=color.w;//*gl_Color.w;
        gl_FragColor = vec4(r, g, b, 1f);
        }
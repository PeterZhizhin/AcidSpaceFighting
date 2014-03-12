        attribute float State;
        varying float StateVar;

        void main(){
        StateVar=State;
        gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
        gl_TexCoord[0]=gl_MultiTexCoord0;
        }
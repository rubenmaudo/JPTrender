package materials;

import maths.ColorValue;

public class Texture extends ColorValue {
    String texture_path;

    //CONSTRUCTOR
    public Texture(String texture_path){
        super(0.5,0.5,0.5);
        this.texture_path=texture_path;
    }


    public ColorValue setColorValue(){
        return this;
    }

}

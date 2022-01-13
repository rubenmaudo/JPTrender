package materials;

import maths.ColorValue;
import maths.Vec3;

public class Texture extends ColorValue {
    String texture_path;

    //CONSTRUCTOR
    public Texture(String texture_path){
        super(1,0,0);
        this.texture_path=texture_path;
    }


    public ColorValue getColorValue(double u, double v, Vec3 p){
        return new ColorValue(1, 0, 0);
    }

}

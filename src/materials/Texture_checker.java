package materials;

import maths.ColorValue;
import maths.Vec3;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.sin;

public class Texture_checker extends ColorValue {
    ColorValue color1;
    ColorValue color2;
    double scale;

    //CONSTRUCTOR
    public Texture_checker(double scale,ColorValue color1, ColorValue color2){
        super(0,1,0);
        this.color1=color1;
        this.color2=color2;
        this.scale=scale;
    }


    public ColorValue getColorValue(double u, double v, @NotNull Vec3 p){

        double sines = sin(scale*p.x())*sin(scale*p.y())*sin(scale*p.z());
        if (sines < 0)
            return color1;
        else
            return color2;
    }

}

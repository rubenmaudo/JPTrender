package materials;

import geometry.Hit_record;
import maths.ColorValue;
import maths.Ray;
import maths.Vec3;

/**
 * @author : Ruben Maudo
 * @since : 25/06/2020
 **/

public class Diffuse_light extends Material{

    public Diffuse_light(ColorValue colorValue){
        this.emitted=colorValue;
    }


    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        return false;
    }

    public ColorValue emitted(double u, double v, double p){
        return new ColorValue(u,v,p);
    }

    public ColorValue getEmitted(){
        return emitted;
    }

}

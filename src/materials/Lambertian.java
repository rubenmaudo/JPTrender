/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.*;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Standard matte material that is defined just for the color
 */
public class Lambertian extends Material{

    //LAMBERTIAN FIELDS
    ColorValue albedo;

    //CONSTRUCTOR
    public Lambertian(ColorValue a){
        this.albedo=a;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {

        //OPTION 1-Calc with scatter in random unit sphere (create more shadows)
        Vec3 scatter_direction = rec.normal.add(Vec3.random_in_unit_sphere());

        //OPTION 2-Calc with scatter in random unit vector (Objects brighter and less shadow)
        //Vec3 scatter_direction = temp.normal.add(Vec3.random_unit_vector());

        this.scattered = new Ray(rec.p, scatter_direction);
        this.attenuation= albedo;

        return true;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.*;

/**
 *
 * @author RubenM
 */
public class Lambertian extends Material{
    
    ColorValue albedo;
    
    public Lambertian(ColorValue a){
        this.albedo=a;
    }
    
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {

        //Calc with scatter in random unit sphere (create more shadows)
        Vec3 scatter_direction = rec.normal.add(Vec3.random_in_unit_sphere());

        //Calc with scatter in random unit vector (Objects brighter and less shadow)
        //Vec3 scatter_direction = temp.normal.add(Vec3.random_unit_vector());

        this.scattered = new Ray(rec.p, scatter_direction);
        this.attenuation= albedo;

        return true;
    }
    
    
}

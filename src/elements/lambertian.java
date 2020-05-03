/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import math.Intersection;
import math.Primitive;
import math.Ray;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public class lambertian extends Material{
    
    Vec3 albedo;
    
    public lambertian (Vec3 a){
        this.albedo=a;
    };
    
    @Override
    public boolean scatter(Ray r_in, Intersection inters) {
        Primitive temp= inters.getPrim();
        Vec3 target = temp.p.add(
                temp.normal).add(random_in_unit_sphere());

        this.scattered = new Ray(temp.p, target.sub(temp.p));
        this.attenuation= albedo;  

        return true;
    }
    
    
}

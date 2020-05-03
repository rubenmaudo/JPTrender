/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import math.Intersection;
import math.Primitive;
import math.Ray;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public class metal extends Material{
    
    Vec3 albedo;
    
    public metal (Vec3 a){
        this.albedo=a;
    };
    
    @Override
    public boolean scatter(Ray r_in, Intersection inters) {
        Primitive temp= inters.getPrim();
        Vec3 reflected = reflect(r_in.direction().normalize(),temp.normal);

        this.scattered = new Ray(temp.p, reflected);
        this.attenuation= albedo;  

        return true;
    }
    
    Vec3 reflect(Vec3 v, Vec3 n){
        return v.sub(n.product(2).product(v.dotProduct(n)));
    }
    
    
}

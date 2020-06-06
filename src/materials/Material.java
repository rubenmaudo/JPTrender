/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import math.Intersection;
import math.Ray;
import math.Vec3;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author RubenM
 */
public abstract class Material {
    public Vec3 attenuation;
    public Ray scattered;
    
    public abstract boolean scatter(Ray r_in, Intersection inters);

    Vec3 reflect(Vec3 v, Vec3 n){

        return v.sub(n.product(2).product(v.dotProduct(n)));
    }

    public Vec3 refract(Vec3 uv, Vec3 n, double etai_over_etat){
        double cos_theta = uv.product(-1).dotProduct(n);
        Vec3 r_out_parallel = uv.add(n.product(cos_theta)).product(etai_over_etat);
        Vec3 r_out_perp= n.product(-sqrt(1-r_out_parallel.squared_length()));
        return r_out_parallel.add(r_out_perp);
    }

    double schlick(double cosine, double ref_idx){
        double r0 = (1-ref_idx) / (1+ref_idx);
        r0= r0 * r0;
        return r0 + (1-r0) * pow((1- cosine),5);
    }
}
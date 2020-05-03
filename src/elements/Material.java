/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import math.Intersection;
import math.Ray;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public abstract class Material {
    public Vec3 attenuation;
    public Ray scattered;
    
    public abstract boolean scatter(Ray r_in, Intersection inters);
    
    static Vec3 random_in_unit_sphere(){
        Vec3 p =new Vec3();
        do{
            p=new Vec3((float)Math.random(), (float)Math.random(), (float)Math.random()).product(2.0f)
                    .sub(new Vec3(1,1,1));
        }while (p.squared_length() >= 1);
        return p;
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

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

}
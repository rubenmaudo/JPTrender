/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import math.*;

/**
 *
 * @author RubenM
 */
public class Metal extends Material{
    
    ColorValue albedo;
    double fuzz;
    
    public Metal(ColorValue a, double fuzziness){
        this.albedo=a;
        if (fuzziness<1) fuzz=fuzziness;
        else fuzz=1;
        
    }
    
    @Override
    public boolean scatter(Ray r_in, Intersection inters) {
        Primitive temp= inters.getPrim();
        Vec3 reflected = reflect(r_in.direction().normalize(),temp.normal);

        this.scattered = new Ray(temp.p, reflected.add(Vec3.random_in_unit_sphere().product(fuzz)));
        this.attenuation= albedo;  

        return (scattered.direction().dotProduct(temp.normal)>0);
    }
}

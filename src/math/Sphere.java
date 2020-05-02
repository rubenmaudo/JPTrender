/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import static math.Vec3.dotProduct;
import static java.lang.Math.sqrt;


/**
 *
 * @author RubenM
 */
public class Sphere extends Primitive {
    
    Vec3 center; //Centre
    Float radius; //Radius
    
    public Sphere(Vec3 cen, float r){
        this.center = cen;
        this.radius = r;
    }
    
    @Override
    public boolean hit(Ray r, float t_min, float t_max){
        Vec3 oc= r.origin().sub(center);
        float a = dotProduct(r.direction(), r.direction());
        float b = dotProduct(oc, r.direction());
        float c = dotProduct(oc,oc) - radius*radius;
        float discriminant = b*b - a*c;
        if (discriminant > 0){
            float temp = (float) (( -b - sqrt(b*b-a*c))/a);
            
            if(temp < t_max && temp > t_min){
                this.t = temp;
                this.p = r.point_at_parameter(this.t);
                this.normal = (this.p.sub(center)).divide(radius);
                return true;
            }
            
            temp = (float) (( -b + sqrt(b*b-a*c))/a);
            
            if(temp < t_max && temp > t_min){
                this.t = temp;
                this.p = r.point_at_parameter(this.t);
                this.normal = (this.p.sub(center)).divide(radius);
                return true;
            }            
        }        
        return false;
    }
    
}

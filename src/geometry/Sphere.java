/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import math.Ray;
import math.Vec3;

import static math.Vec3.dotProduct;
import static java.lang.Math.sqrt;


/**
 *
 * @author RubenM
 */
public class Sphere extends Primitive {
    
    Vec3 center; //Centre
    double radius; //Radius
    
    public Sphere(Vec3 cen, double r, Material material){
        this.center = cen;
        this.radius = r;
        this.material= material;
    }
    
    @Override
    public boolean hit(Ray r, double t_min, double t_max){
        Vec3 oc= r.origin().sub(center);
        double a = dotProduct(r.direction(), r.direction());
        double half_b = dotProduct(oc, r.direction());
        double c = dotProduct(oc,oc) - radius*radius;
        double discriminant = half_b*half_b - a*c;

        if (discriminant > 0){
            double temp =  (( -half_b - sqrt(half_b*half_b-a*c))/a);
            
            if(temp < t_max && temp > t_min){
                this.t = temp;
                this.p = r.point_at_parameter(this.t);
                Vec3 outward_normal=(this.p.sub(center)).divide(radius);
                this.set_face_normal(r,outward_normal);
                return true;
            }
            
            temp = (( -half_b + sqrt(half_b*half_b-a*c))/a);
            
            if(temp < t_max && temp > t_min){
                this.t = temp;
                this.p = r.point_at_parameter(this.t);
                Vec3 outward_normal=(this.p.sub(center)).divide(radius);
                this.set_face_normal(r,outward_normal);
                return true;
            }            
        }        
        return false;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;

import static maths.Vec3.dotProduct;
import static java.lang.Math.sqrt;


/**
 *
 * @author RubenM
 */
public class Sphere extends Primitive{
    
    Vec3 center; //Centre
    double radius; //Radius
    
    public Sphere(Vec3 cen, double r, Material material){
        this.center = cen;
        this.radius = r;
        super.material= material;
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec){
        Vec3 oc= r.origin().sub(center);
        double a = dotProduct(r.direction(), r.direction());
        double half_b = dotProduct(oc, r.direction());
        double c = dotProduct(oc,oc) - radius*radius;
        double discriminant = half_b*half_b - a*c;

        if (discriminant > 0){
            final double sqrt = sqrt(half_b * half_b - a * c);
            double temp =  (( -half_b - sqrt)/a);
            
            if(temp < t_max && temp > t_min){
                rec.t = temp;
                rec.p = r.point_at_parameter(rec.t);

                rec.material=material;

                Vec3 outward_normal=(rec.p.sub(center)).divide(radius);
                rec.set_face_normal(r,outward_normal);
                return true;
            }
            
            temp = (( -half_b + sqrt)/a);
            
            if(temp < t_max && temp > t_min){
                rec.t = temp;
                rec.p = r.point_at_parameter(rec.t);

                rec.material=this.material;

                Vec3 outward_normal=(rec.p.sub(center)).divide(radius);
                rec.set_face_normal(r,outward_normal);
                return true;
            }            
        }        
        return false;
    }

    @Override
    public String getDescription() {
        return "The Spere centre is at xyz(" + center.x() + "," + center.y() + "," + center.z() + ") and the" +
                "radius is r";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static maths.Vec3.dotProduct;
import static java.lang.Math.sqrt;


/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * A class to identify and do calcs over a sphere
 */
public class Sphere extends Primitive{

    //SPHERE FIELDS
    Vec3 center; //Centre
    double radius; //Radius


    //CONSTRUCTOR

    /**
     * Constructor of Sphere based on radius and centre
     * @param cen
     * @param r
     * @param material
     */
    public Sphere(Vec3 cen, double r, Material material){
        this.center = cen;
        this.radius = r;
        super.material= material;
    }

    //METHODS
    /**
     * Method that calc if the Ray given impact in the sphere
     * @param r Ray
     * @param t_min minimum distance for hit search
     * @param t_max maximum distance for hit search
     * @param rec hit record
     * @return boolean confirming if it impact or not
     */
    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec){
        Vec3 oc= r.origin().sub(center);
        double a = dotProduct(r.direction(), r.direction());
        double half_b = dotProduct(oc, r.direction());
        double c = dotProduct(oc,oc) - radius*radius;
        double discriminant = half_b*half_b - a*c;

        //If bigger than 0 it means that the ray hit the sphere
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
        return false;//The ray doesn't hit the sphere
    }

    @Override
    public String getDescription() {
        return "The Sphere centre is at xyz(" + center.x() + "," + center.y() + "," + center.z() + ") and the" +
                "radius is r";
    }

    @Override
    public Node getGeomety(Document doc) {
        Element sphere=doc.createElement("Sphere");
        sphere.setAttribute("radius", String.valueOf(this.radius));
        sphere.setAttribute("CentreX", String.valueOf(this.center.getValue(0)));
        sphere.setAttribute("CentreY", String.valueOf(this.center.getValue(1)));
        sphere.setAttribute("CentreZ", String.valueOf(this.center.getValue(2)));

        sphere.appendChild(material.getMaterial(doc));

        return sphere;
    }

    public Vec3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Material getMaterial(){
        return material;
    }

    @Override
    public Primitive clone() {
        return new Sphere(Vec3.clone(this.getCenter()),this.getRadius(),this.getMaterial().clone());
    }

}

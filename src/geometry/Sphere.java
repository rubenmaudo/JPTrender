/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import maths.Onb;
import maths.Ray;
import maths.Utils;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static java.lang.Math.*;
import static maths.Vec3.dotProduct;
import static maths.Vec3.random_to_Sphere;


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
        create_bounding_box();
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

                get_spehere_uv(outward_normal,rec);

                return true;
            }

            temp = (( -half_b + sqrt)/a);

            if(temp < t_max && temp > t_min){
                rec.t = temp;
                rec.p = r.point_at_parameter(rec.t);

                rec.material=this.material;

                Vec3 outward_normal=(rec.p.sub(center)).divide(radius);
                rec.set_face_normal(r,outward_normal);

                get_spehere_uv(outward_normal,rec);

                return true;
            }
        }
        return false;//The ray doesn't hit the sphere
    }

    private void get_spehere_uv(Vec3 p, Hit_record rec){
        // p: a given point on the sphere of radius one, centered at the origin.
        // u: returned value [0,1] of angle around the Y axis from X=-1.
        // v: returned value [0,1] of angle from Y=-1 to Y=+1.
        //     <1 0 0> yields <0.50 0.50>       <-1  0  0> yields <0.00 0.50>
        //     <0 1 0> yields <0.50 1.00>       < 0 -1  0> yields <0.50 0.00>
        //     <0 0 1> yields <0.25 0.50>       < 0  0 -1> yields <0.75 0.50>

        /*
        double theta = acos(-p.y());
        double phi = atan2(-p.z(),p.x() + PI);
        rec.u= phi / (2*PI);
        rec.v= theta / PI;
         */

        double theta = asin(p.y());
        double phi = atan2(p.z(), p.x());

        rec.u = 1 - (phi + PI) / (2 * PI);
        rec.v = (theta + PI / 2) / PI;
        //System.out.println("u & v are=" + rec.u + ", " + rec.v);
    }

    @Override
    public double pdf_value(Vec3 o, Vec3 v) {
        Hit_record rec=new Hit_record();
        if(!this.hit(new Ray(o,v),0.001, Utils.INFINITY,rec))
            return 0;

        double cos_theta_max= Math.sqrt(1-radius*radius/(center.sub(o).squared_length()));
        double solid_angle= 2*Utils.PI*(1-cos_theta_max);

        return 1/solid_angle;
    }

    @Override
    public Vec3 random(Vec3 o) {
        Vec3 direction=center.sub(o);
        double distance_squared=direction.squared_length();
        Onb uvw = new Onb();
        uvw.build_from_w(direction);
        return uvw.local(random_to_Sphere(radius,distance_squared));
    }

    @Override
    public Node saveGeomety(Document doc) {
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
    public void create_bounding_box() {
        this.boundingBox= new AABB(center.sub(new Vec3(radius)),
                center.add(new Vec3(radius)));
    }

}

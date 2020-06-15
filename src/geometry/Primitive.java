/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import math.Ray;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public abstract class Primitive {
    public double t;//Distance to the hitting point
    public Vec3 p;//Point of hitting
    public Vec3 normal;//Normal
    public boolean front_face;//Determining if the ray is coming out or going in

    public Material material;//Material type


    public abstract boolean hit(Ray r, double t_min, double t_max);

    public void set_face_normal(Ray r, Vec3 outward_normal){
        front_face=r.direction().dotProduct(outward_normal)<0;
        if( front_face){
            normal=outward_normal;
        }else {normal = outward_normal.product(-1);
        }
    }
}

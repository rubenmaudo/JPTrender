/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Class used to storage all the information of the intersections when rendering
 */
public class Hit_record {
    //FIELDS
    public double t;//Distance to the hitting point
    public Vec3 p;//Point of hitting
    public Vec3 normal;//Normal
    public boolean front_face;//Determining if the ray is coming out or going in

    public Material material;//Material type for the primitive we hit

    public double u; //u coordinate for textures
    public double v; //v coordinate for textures



    //CONSTRUCTOR
    public Hit_record(){
        this.t=0;
        this.p=new Vec3();
        this.normal=new Vec3();
    }

    //METHODS
    /**
     * Method to check and set the face normal to the "world"
     * @param r ray
     * @param outward_normal outward normal
     */
    public void set_face_normal(Ray r, Vec3 outward_normal){
        front_face=r.direction().dotProduct(outward_normal)<0;
        if( front_face){
            normal=outward_normal;
        }else {normal = outward_normal.product(-1);
        }
    }
}

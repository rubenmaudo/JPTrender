/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import maths.Ray;
import maths.Utils;
import maths.Vec3;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class Camera {

    //CAMERA FIELDS
    private final Vec3 lower_left_corner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;
    private final Vec3 u,v,w; //Parameters to create an orthonormal basis to describe camera orientation
    private final double lens_radius;

    //CAMERA CONSTRUCTOR
    public Camera(Vec3 lookfrom, Vec3 lookat, Vec3 vup, double vfov, double aspect_ratio,
                  double aperture, double focus_dist){

        double theta = Utils.degrees_to_radians(vfov);//FieldOfView changed to radians
        double h = Math.tan(theta/2);
        double viewport_height = 2.0 * h;//Identify the viewport height
        double viewport_width =aspect_ratio * viewport_height;//Identify the viewport width

        this.w = lookfrom.sub(lookat).normalize();
        this.u = vup.cross(w).normalize();
        this.v = w.cross(u);

        this.origin = lookfrom; //Origin point for the camera
        this.horizontal = u.product(focus_dist*viewport_width);//Parameter to identify the width of the viewport to "take"
        this.vertical = v.product(focus_dist*viewport_height);//Parameter to identify the height of the viewport to "take"
        //Parameter to identify the left corner of the picture to "take" that will be used as a reference
        this.lower_left_corner = origin.sub(horizontal.divide(2)).sub(vertical.divide(2)).sub(w.product(focus_dist));
        this.lens_radius= aperture / 2;//Size of the lens to determine the aperture to be used
    }


    //CAMERA METHODS
    /**
     *Produce a ray based in the parameters given
     * @param s Also called u / parameter to identify the x position in the viewport
     * @param t Also called v / parameter to identify the y position in the viewport
     * @return a new ray that goes from the origing of the camera to the direction created based in s & t
     */
    public Ray get_ray(double s, double t){
        Vec3 rd= Vec3.random_in_unit_disk().product(lens_radius);//Obtain random point in the lens
        Vec3 offset = u.product(rd.x()).add(v.product(rd.y()));


        return new Ray(origin.add(offset),
                lower_left_corner.add(horizontal.product(s)).add(vertical.product(t)).sub(origin).sub(offset)
        );
    }
    
}

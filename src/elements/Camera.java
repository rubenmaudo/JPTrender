/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import math.Ray;
import math.Utils;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public class Camera {
    
    private final Vec3 lower_left_corner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;
    private final Vec3 u,v,w;
    private final double lens_radius;
    
    public Camera(Vec3 lookfrom, Vec3 lookat, Vec3 vup, double vfov, double aspect_ratio,
                  double aperture, double focus_dist){

        double theta = Utils.degrees_to_radians(vfov);
        double h = Math.tan(theta/2);
        double viewport_height = 2.0 * h;
        double viewport_width =aspect_ratio * viewport_height;

        this.w = lookfrom.sub(lookat).normalize();
        this.u = vup.cross(w).normalize();
        this.v = w.cross(u);

        this.origin = lookfrom;
        this.horizontal = u.product(focus_dist*viewport_width);
        this.vertical = v.product(focus_dist*viewport_height);
        this.lower_left_corner = origin.sub(horizontal.divide(2)).sub(vertical.divide(2)).sub(w.product(focus_dist));

        this.lens_radius= aperture / 2;

    }
    
    public Ray get_ray(double s, double t){
        Vec3 rd= Vec3.random_in_unit_disk().product(lens_radius);
        Vec3 offset = u.product(rd.x()).add(v.product(rd.y()));


        return new Ray(origin.add(offset),
                lower_left_corner.add(horizontal.product(s)).add(vertical.product(t)).sub(origin).sub(offset)
        );
    }
    
}

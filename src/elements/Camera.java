/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import math.Ray;
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
    
    public Camera(){

        final double aspect_ratio = 16.0 / 9.0 ;
        double viewport_height = 2.0;
        double viewport_width =aspect_ratio * viewport_height;
        double focal_length = 1.0;

        this.origin = new Vec3(0,0,0);
        this.horizontal = new Vec3(viewport_width,0,0);
        this.vertical = new Vec3(0, viewport_height, 0);
        this.lower_left_corner = origin.sub(horizontal.divide(2))
            .sub(vertical.divide(2))
                .sub(new Vec3(0,0,focal_length));
    }
    
    public Ray get_ray(double u, double v){
        return new Ray(origin,lower_left_corner.
                add(horizontal.product(u)).add(vertical.product(v)));
    }
    
}

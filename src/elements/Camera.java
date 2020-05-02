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
    
    Vec3 lower_left_corner;
    Vec3 horizontal;
    Vec3 vertical;
    Vec3 origin;
    
    public Camera(){
        this.lower_left_corner=new Vec3(-2.0f,-1.0f,-1.0f);
        this.horizontal=new Vec3(4.0f,0.0f,0.0f);
        this.vertical=new Vec3(0.0f,2.0f,0.0f);
        this.origin=new Vec3(0.0f,0.0f,0.0f);
    }
    
    public Ray get_ray(float u, float v){
        return new Ray(origin,lower_left_corner.
                add(horizontal.product(u)).add(vertical.product(v)));
    }
    
}

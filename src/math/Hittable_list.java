/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import geometry.Primitive;

import java.util.ArrayList;

/**
 *
 * @author RubenM
 */
public class Hittable_list {
    
    Primitive prim;
    Boolean hit_anything = false;
    double closest_so_far;
    
    public Hittable_list(){}
    
    public boolean hit(Ray r, double t_min, double t_max, ArrayList<Primitive> list){
        
        closest_so_far = t_max;

        for (Primitive primitive : list) {

            if (primitive.hit(r, t_min, closest_so_far)) {
                if (!hit_anything) {

                    prim = primitive;
                    closest_so_far = primitive.t;
                    hit_anything = true;

                } else if (primitive.t < closest_so_far) {
                    closest_so_far = primitive.t;
                    prim = primitive;
                }
            }
        }
        return hit_anything;        
    }
    
    public Primitive getPrim(){
        return prim;
    }
}

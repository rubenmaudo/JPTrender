/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author RubenM
 */
public class Intersection {
    
    Primitive prim;
    Boolean hit_anything = false;
    double closest_so_far;
    
    public Intersection(){}
    
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

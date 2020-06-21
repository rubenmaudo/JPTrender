/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

import geometry.Hit_record;
import geometry.Sphere;

import java.util.ArrayList;

/**
 *
 * @author RubenM
 */
public class Hittable {//ALSO CALLED INTERSECTION

    ArrayList<Sphere> list;

    Hit_record temp_rec;
    Boolean hit_anything = false;
    double closest_so_far;

    public Hittable(ArrayList<Sphere> list){
        this.list=list;
        this.temp_rec=new Hit_record();
    }
    
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec){

        closest_so_far = t_max;

        for (Sphere sphere : list) {

            if (sphere.hit(r, t_min, closest_so_far,rec)) {
                hit_anything = true;
                closest_so_far=rec.t;
                temp_rec=rec;

                /*
                if (!hit_anything) {

                    this.temp_rec = rec;
                    this.closest_so_far = temp_rec.t;
                    hit_anything = true;

                } else if (temp_rec.t < closest_so_far) {
                    closest_so_far = temp_rec.t;
                    rec = temp_rec;
                }*/
            }
        }
        return hit_anything;        
    }
    
    public Hit_record getTemp_rec(){
        return temp_rec;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.ColorValue;
import maths.Ray;
import maths.Vec3;

import java.io.Serializable;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Abstract class to be extended in all the materials to be used
 */
public abstract class Material implements Serializable {

    //MATERIAL FIELDS
    ColorValue attenuation;
    Ray scattered;

    //METHODS
    //Abstrad method to calc the scatter of the ray that hit the primitive
    public abstract boolean scatter(Ray r_in, Hit_record rec);

    //Calc the reflected ray
    Vec3 reflect(Vec3 v, Vec3 n){
        return v.sub(n.product(2).product(v.dotProduct(n)));
    }

    public ColorValue getAttenuation() {
        return attenuation;
    }

    public Ray getScattered() {
        return scattered;
    }
}
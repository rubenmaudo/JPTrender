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
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.Serializable;

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
    ColorValue emitted;
    Ray scattered;

    //TESTING
    double pdf;

    //METHODS
    /**
     * Abstrad method to calc the scatter of the ray that hit the primitive
     * @param r_in
     * @param rec
     * @return
     */
    public abstract boolean scatter(Ray r_in, Hit_record rec);

    public double scattering_pdf(Ray r_in, Hit_record rec, Ray scattered){
        return 0;
    }


    public ColorValue emitted(){
        if (emitted==null){
            return new ColorValue(0,0,0);
        }else{
            return emitted;
        }
    }

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

    public double getPdf() {return pdf;}


    public abstract Node getMaterial(Document doc);
}
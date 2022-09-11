/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Standard reflective material that is defined for the color and the fuzziness
 */
public class Metal2 extends Material{
    //METAL FIELDS
    Texture albedo;//Material "color"
    Texture reflectness;
    Texture roughness;
    double fuzz;//Amount of fuzziness to apply to the reflection

    //CONSTRUCTOR
    public Metal2(Texture colour, Texture reflectness, Texture roughness){
        this.albedo=colour;
        this.reflectness = reflectness;
        this.roughness = roughness;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {

        //Metal scatter
        Vec3 reflected = reflect(r_in.direction().normalize(), rec.normal);

        //Lambertian scatter
        Vec3 scatter_direction = rec.normal.add(Vec3.random_in_unit_sphere());
        this.attenuation= albedo.getColourValue(rec.u, rec.v, rec.p);


        if(ThreadLocalRandom.current().nextDouble()>= getScatterValue(rec)){
            //Lambertian part
            this.scattered = new Ray(rec.p, scatter_direction);
            return true;

        }else{
            //Metal part
            this.scattered = new Ray(rec.p, reflected.add(Vec3.random_in_unit_sphere().product(roughness.getColourValue(rec.u, rec.v, rec.p).vR()*2)));
            return (scattered.direction().dotProduct(rec.normal)>0);
        }
    }

    public double getScatterValue(Hit_record rec){
        double reflectnessTemp= reflectness.getColourValue(rec.u, rec.v, rec.p).vR();

        /*
        System.out.println(reflectnessTemp + ", " + reflectness.getColourValue(rec.u, rec.v, rec.p).vG() +
                ", " + reflectness.getColourValue(rec.u, rec.v, rec.p).vG());

         */

        return reflectnessTemp;
    }
    
    
    @Override
    public Node getMaterial(Document doc) {
        Element metal=doc.createElement("Material");
        metal.setAttribute("type", "metal");
        //metal.setAttribute("ColorR", String.valueOf(this.albedo.vR()));
        //metal.setAttribute("ColorG", String.valueOf(this.albedo.vG()));
        //metal.setAttribute("ColorB", String.valueOf(this.albedo.vB()));
        metal.setAttribute("fuzziness", String.valueOf(this.fuzz));

       
        return metal;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Standard reflective material that is defined for the color and the fuzziness
 */
public class Metal extends Material{
    //METAL FIELDS
    ColorValue albedo;//Material "color"
    double fuzz;//Amount of fuzziness to apply to the reflection

    //CONSTRUCTOR
    public Metal(ColorValue a, double fuzziness){
        this.albedo=a;
        if (fuzziness<1) fuzz=fuzziness;
        else fuzz=1;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        Vec3 reflected = reflect(r_in.direction().normalize(), rec.normal);

        this.scattered = new Ray(rec.p, reflected.add(Vec3.random_in_unit_sphere().product(fuzz)));
        this.attenuation= albedo;  

        return (scattered.direction().dotProduct(rec.normal)>0);
    }

    @Override
    public Node getMaterial(Document doc) {
        Element metal=doc.createElement("Material");
        metal.setAttribute("type", "metal");
        metal.setAttribute("ColorR", String.valueOf(this.albedo.vR()));
        metal.setAttribute("ColorG", String.valueOf(this.albedo.vG()));
        metal.setAttribute("ColorB", String.valueOf(this.albedo.vB()));
        metal.setAttribute("fuzziness", String.valueOf(this.fuzz));

        return metal;
    }




    public ColorValue getAlbedo() {
        return albedo;
    }

    public double getFuzz() {
        return fuzz;
    }

    @Override
    public Material clone(){
        return new Metal(ColorValue.clone(this.getAlbedo()),this.getFuzz());
    }
}

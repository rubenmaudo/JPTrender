/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.*;
import maths.Pdf.Primitive_pdf;
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
    Texture albedo;//Material "color"
    double fuzz;//Amount of fuzziness to apply to the reflection

    //CONSTRUCTOR
    public Metal(ColorValue a, double fuzziness){
        this.albedo=new Texture_solid_colour(a.vR(),a.vG(),a.vB());
        if (fuzziness<1) fuzz=fuzziness;
        else fuzz=1;
    }

    public Metal(ColorValue a){
        this.albedo=new Texture_solid_colour(a.vR(),a.vG(),a.vB());
        fuzz=0;
    }

    public Metal(Texture texture, double fuzziness){
        this.albedo=texture;
        if (fuzziness<1) fuzz=fuzziness;
        else fuzz=1;
    }

    public Metal(Texture texture){
        this.albedo=texture;
        fuzz=0;
    }

    public Metal(double R, double G, double B, double fuzziness){
        this.albedo=new Texture_solid_colour(R,G,B);
        if (fuzziness<1) fuzz=fuzziness;
        else fuzz=1;
    }

    public Metal(double R, double G, double B){
        this.albedo=new Texture_solid_colour(R,G,B);
        fuzz=0;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec, Scatter_record srec) {
        Vec3 reflected = reflect(r_in.direction().normalize(), rec.normal);

        /*
        this.scattered = new Ray(rec.p, reflected.add(Vec3.random_in_unit_sphere().product(fuzz)));
        this.attenuation= albedo.getColourValue(rec.u, rec.v, rec.p);
        return (scattered.direction().dotProduct(rec.normal)>0);
        */
        srec.setSpecular_ray(new Ray(rec.p,reflected.add(Vec3.random_in_unit_sphere().product(fuzz))));
        this.attenuation= albedo.getColourValue(rec.u, rec.v, rec.p);
        srec.setAttenuation(attenuation);
        srec.setPdf_ptr(null);
        srec.setIs_specular(true);
        return true;

    }

    @Override
    public Node getMaterial(Document doc) {
        Element metal=doc.createElement("Material");
        metal.setAttribute("type", "metal");
        //metal.setAttribute("ColorR", String.valueOf(this.albedo.vR()));
        //metal.setAttribute("ColorG", String.valueOf(this.albedo.vG()));
        //metal.setAttribute("ColorB", String.valueOf(this.albedo.vB()));
        metal.setAttribute("fuzziness", String.valueOf(this.fuzz));

        //TODO save scene need to be implemented
        return metal;
    }
}

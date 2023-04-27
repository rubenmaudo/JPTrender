/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package materials;

import geometry.Hit_record;
import maths.*;
import maths.Pdf.Cosine_pdf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Standard matte material that is defined just for the color
 */
public class Lambertian extends Material{

    //LAMBERTIAN FIELDS
    Texture albedo;

    ColorValue testColorValue;

    //CONSTRUCTOR
    public Lambertian(ColorValue a){
        this.albedo=new Texture_solid_colour(a.vR(),a.vG(),a.vB());
        this.testColorValue=a;
    }

    public Lambertian(double R, double G, double B){
        this.albedo=new Texture_solid_colour(R,G,B);
    }

    public Lambertian(Texture texture){
        this.albedo= texture;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec, Scatter_record srec) {

        /*MATERIAL AS IT WAS BEFORE CHAPTER 8-THE REST OF YOUR LIFE
        //OPTION 1-Calc with scatter in random unit sphere (create more shadows)
        //Vec3 scatter_direction = rec.normal.add(Vec3.random_in_unit_sphere());

        //OPTION 2-Calc with scatter in random unit vector (Objects brighter and less shadow)
        Vec3 scatter_direction = rec.normal.add(Vec3.random_unit_vector());
        //Vec3 scatter_direction = Vec3.random_in_hemisphere(rec.normal); ALTERNATIVE

        //catch degenerate scatter direction
        if (scatter_direction.near_zero()){
            scatter_direction=rec.normal;
        }

        this.scattered=new Ray(rec.p, scatter_direction.normalize());

        this.attenuation=albedo.getColourValue(rec.u, rec.v, rec.p);

        super.pdf=(rec.normal.dotProduct(scattered.direction()))/Utils.PI;
        //super.pdf=0.5/Utils.PI; ALTERNATIVE

        return true;
        */


        /*
        Onb uvw=new Onb();
        uvw.build_from_w(rec.normal);
        Vec3 direction=uvw.local(Vec3.random_cosine_direction());

        this.scattered=new Ray(rec.p, direction.normalize());
        this.attenuation=albedo.getColourValue(rec.u, rec.v, rec.p);

        super.pdf=(uvw.w().dotProduct(scattered.direction())) / Utils.PI;
        return true;
        */

        this.attenuation=albedo.getColourValue(rec.u, rec.v, rec.p);
        srec.setIs_specular(false);
        srec.setAttenuation(albedo.getColourValue(rec.u, rec.v, rec.p));
        srec.setPdf_ptr(new Cosine_pdf(rec.normal));
        return true;

    }

    @Override
    public double scattering_pdf(Ray r_in, Hit_record rec, Ray scattered) {
        double cosine= rec.normal.dotProduct(scattered.direction().normalize());
        return cosine < 0 ? 0 : cosine/Utils.PI;
    }


    @Override
    public Node getMaterial(Document doc) {
        Element lambertian=doc.createElement("Material");
        lambertian.setAttribute("type", "lambertian");
        lambertian.setAttribute("ColorR", String.valueOf(this.testColorValue.vR()));
        lambertian.setAttribute("ColorG", String.valueOf(this.testColorValue.vG()));
        lambertian.setAttribute("ColorB", String.valueOf(this.testColorValue.vB()));
        //TODO save scene need to be implemented
        return lambertian;
    }

}

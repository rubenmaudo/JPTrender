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

import java.awt.*;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Standard matte material that is defined just for the color
 */
public class Lambertian extends Material{

    //LAMBERTIAN FIELDS
    ColorValue albedo;

    //CONSTRUCTOR
    public Lambertian(ColorValue a){
        this.albedo=a;
    }

    public Lambertian(Texture texture){
        this.albedo=texture;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {

        //OPTION 1-Calc with scatter in random unit sphere (create more shadows)
        Vec3 scatter_direction = rec.normal.add(Vec3.random_in_unit_sphere());

        //OPTION 2-Calc with scatter in random unit vector (Objects brighter and less shadow)
        //Vec3 scatter_direction = temp.normal.add(Vec3.random_unit_vector());

        this.scattered = new Ray(rec.p, scatter_direction);
        //TESTING this.attenuation= albedo;
        this.attenuation=albedo.getColorValue(rec.u, rec.v, rec.p);

        return true;
    }

    @Override
    public Node getMaterial(Document doc) {
        Element lambertian=doc.createElement("Material");
        lambertian.setAttribute("type", "lambertian");
        lambertian.setAttribute("ColorR", String.valueOf(this.albedo.vR()));
        lambertian.setAttribute("ColorG", String.valueOf(this.albedo.vG()));
        lambertian.setAttribute("ColorB", String.valueOf(this.albedo.vB()));

        return lambertian;
    }

    @Override
    public Material clone() {
        return new Lambertian(ColorValue.clone(this.albedo));
    }


}

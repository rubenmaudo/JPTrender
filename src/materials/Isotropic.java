package materials;

import geometry.Hit_record;
import maths.ColorValue;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Isotropic extends Material{

    //LAMBERTIAN FIELDS
    Texture albedo;

    //CONSTRUCTOR
    public Isotropic (ColorValue a){
        this.albedo=new Texture_solid_colour(a.vR(),a.vG(),a.vB());
    }

    public Isotropic(double R, double G, double B){
        this.albedo=new Texture_solid_colour(R,G,B);
    }

    public Isotropic (Texture_solid_colour textureSolidcolour){
        this.albedo= textureSolidcolour;
    }


    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        this.scattered = new Ray(rec.p, Vec3.random_in_unit_sphere());
        this.attenuation=albedo.getColourValue(rec.u, rec.v, rec.p);
        return true;
    }

    @Override
    public Node getMaterial(Document doc) {
        return null;
    }
}

package materials;

import geometry.Hit_record;
import maths.ColorValue;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Isotropic extends Material{

    ColorValue albedo;

    //CONSTRUCTOR
    public Isotropic (ColorValue a){
        this.albedo=a;
    }

    public Isotropic (Texture texture){
        this.albedo=texture;
    }


    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        this.scattered = new Ray(rec.p, Vec3.random_in_unit_sphere());
        this.attenuation=albedo.getColorValue(rec.u, rec.v, rec.p);
        return true;
    }

    @Override
    public Node getMaterial(Document doc) {
        return null;
    }

    @Override
    public Material clone() {
        return null;
    }
}

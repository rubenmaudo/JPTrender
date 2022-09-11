package materials;

import geometry.Hit_record;
import maths.ColorValue;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author : Ruben Maudo
 * @since : 25/06/2020
 **/

public class Diffuse_light extends Material{

    public Diffuse_light(ColorValue colorValue){
        this.emitted=colorValue;
    }


    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        return false;
    }

    public ColorValue emitted(double u, double v, double p){
        return new ColorValue(u,v,p);
    }

    public ColorValue getEmitted(){
        return emitted;
    }

    @Override
    public Node getMaterial(Document doc) {
        Element light=doc.createElement("Material");
        light.setAttribute("type", "light");
        light.setAttribute("ColorR", String.valueOf(this.emitted.vR()));
        light.setAttribute("ColorG", String.valueOf(this.emitted.vG()));
        light.setAttribute("ColorB", String.valueOf(this.emitted.vB()));

        return light;
    }
}

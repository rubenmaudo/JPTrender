package geometry;

import materials.Material;
import maths.Ray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/*
 * Abstract class to be extended in all the geometry to be used
 */
public abstract class Primitive implements Serializable {

    //PRIMITIVE FIELDS
    Material material;//Material asigned to the primitive

    //PRIMITIVE METHODS
    //Method to be implemented in all the primitives types that look for impacts from the ray
    public abstract boolean hit(Ray r, double t_min, double t_max, Hit_record rec);

    //Create a description for the primitive and its parameters
    abstract String getDescription();

    public abstract Node getGeomety(Document doc);

}

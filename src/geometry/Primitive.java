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
    /**
     * Method to be implemented in all the primitives types that look for impacts from the ray
     * @param r Ray obtained
     * @param t_min Minimum distance to look at
     * @param t_max Maximum distance to look at
     * @param rec Array to store the details
     * @return
     */
    public abstract boolean hit(Ray r, double t_min, double t_max, Hit_record rec);

    /**
     * Create a description for the primitive and its parameters
     * @return
     */
    abstract String getDescription();

    /**
     * Method to parse onto xml from instances
     * @param doc Document DOM parser
     * @return
     */
    public abstract Node getGeomety(Document doc);

    /**
     * Clone primitive object
     *
     * @return
     */
    public abstract Primitive clone();

}

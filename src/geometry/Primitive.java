package geometry;

import materials.Material;
import maths.Ray;

import java.io.Serializable;

public abstract class Primitive implements Serializable {

    Material material;

    public abstract boolean hit(Ray r, double t_min, double t_max, Hit_record rec);

    abstract String getDescription();

}

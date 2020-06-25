package materials;

import math.Intersection;
import math.Ray;

/**
 * @author : Ruben Maudo
 * @since : 25/06/2020
 **/

public class Diffuse_light extends Material{


    @Override
    public boolean scatter(Ray r_in, Intersection inters) {
        return false;
    }
}

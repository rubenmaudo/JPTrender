package geometry;

import materials.Isotropic;
import materials.Lambertian;
import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.log;

public class Volume extends Primitive{

    //TODO implement boundary to work as a mesh
    Primitive boundary; //Boundary that define the volume using a primitive (only works with convex items, not mesh)
    double neg_inv_density;

    public Volume(Primitive boundary,double density) {
        this.boundary = boundary;
        this.material= boundary.material;
        this.neg_inv_density=(-1./density);
        create_bounding_box();
    }

    public Volume(Primitive boundary, double density, Isotropic isotropic) {
        this.boundary = boundary;
        this.material= isotropic;
        this.neg_inv_density=(-1./density);
        create_bounding_box();
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
        Hit_record rec1=new Hit_record();
        Hit_record rec2=new Hit_record();


        if(!boundary.hit(r,-Double.MAX_VALUE,Double.MAX_VALUE,rec1))return false;
        if(!boundary.hit(r,rec1.t+0.0001,Double.MAX_VALUE,rec2))return false;

        if (rec1.t < t_min) rec1.t = t_min;
        if (rec2.t > t_max) rec2.t = t_max;

        if (rec1.t >= rec2.t)
            return false;

        if (rec1.t < 0)
            rec1.t = 0;

        double ray_length = r.direction().length();
        double distance_inside_boundary = (rec2.t - rec1.t) * ray_length;
        double hit_distance = neg_inv_density * log(ThreadLocalRandom.current().nextDouble());

        if (hit_distance > distance_inside_boundary)
            return false;

        rec.t = rec1.t + hit_distance / ray_length;
        rec.p = r.point_at_parameter(rec.t);

        rec.normal = new Vec3(1,0,0);  // arbitrary
        rec.front_face = true;     // also arbitrary
        rec.material= material;

        return true;
    }

    @Override
    public Node getGeomety(Document doc) {
        return null;
    }

    @Override
    void create_bounding_box() {
        this.boundingBox= boundary.getAABB();
    }
}

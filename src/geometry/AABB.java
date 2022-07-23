package geometry;

import geometry.Hit_record;
import geometry.Primitive;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class AABB extends Primitive {

    Vec3 minimum;
    Vec3 maximum;

    public AABB(Vec3 minimum, Vec3 maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Vec3 min() {
        return minimum;
    }

    public Vec3 max() {
        return maximum;
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {


        /*OLD IMPLEMENTATION
        for(int a=0; a<3; a++){
            double t0= Math.min((minimum.getValue(a) - r.origin().getValue(a)) / r.direction().getValue(a),
                                (maximum.getValue(a) - r.origin().getValue(a)) / r.direction().getValue(a));

            double t1= Math.max((minimum.getValue(a) - r.origin().getValue(a)) / r.direction().getValue(a),
                    (maximum.getValue(a) - r.origin().getValue(a)) / r.direction().getValue(a));

            t_min=Math.max(t0,t_min);
            t_max=Math.min(t1,t_max);
            if (t_max<=t_min) return false;
        }
        return true;
        */

        for(int a=0; a<3; a++){
            double invD= 1.0/r.direction().getValue(a);
            double t0=(minimum.getValue(a)-r.origin().getValue(a))*invD;
            double t1=(maximum.getValue(a)-r.origin().getValue(a))*invD;
            if (invD<0.0){
                double t_temp=t1;
                t1=t0;
                t0=t_temp;
            }
            t_min=t0>t_min ? t0 : t_min;
            t_max=t1<t_max ? t1 : t_max;
            if (t_max<=t_min) return false;
        }
        return true;
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    public Node getGeomety(Document doc) {
        return null;
    }

    @Override
    public Primitive clone() {
        return null;
    }

    @Override
    void create_bounding_box() {
        this.boundingBox=null;
    }
}

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
    public Node saveGeomety(Document doc) {
        return null;
    }


    @Override
    public void create_bounding_box() {
        this.boundingBox=null;
    }
}

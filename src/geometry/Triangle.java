package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Triangle extends Primitive{

    //SPHERE FIELDS
    Vec3 v0,v1,v2; //Triangle vertex
    double kEpsilon = 0.00000001;

    //CONSTRUCTOR

    /**
     * Triangle constructor based on three vertex
     * @param v0
     * @param v1
     * @param v2
     */
    public Triangle(Vec3 v0, Vec3 v1, Vec3 v2, Material material) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        super.material= material;
    }


    //METHODS
    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        double u,v;

        Vec3 v0v1= v1.sub(v0);
        Vec3 v0v2= v2.sub(v0);
        Vec3 pvec = r.direction().cross(v0v2);
        double det = v0v1.dotProduct(pvec);

        // if the determinant is negative the triangle is backfacing
        // if the determinant is close to 0, the ray misses the triangle
        //if (det < kEpsilon & det>kEpsilon*-1) return false;

        double invDet = 1 / det;

        Vec3 tvec= r.origin().sub(v0);
        u =  (tvec.dotProduct(pvec)*invDet);
        if (u<0 || u>1) return false;

        Vec3 qvec = tvec.cross(v0v1);
        v=  (r.direction().dotProduct(qvec) * invDet);
        if (v<0 || u+v>1)return false;

        Double temp= v0v2.dotProduct(qvec)*invDet;

        if(temp < t_max && temp > t_min){
            rec.t = temp;
            rec.p = r.point_at_parameter(rec.t);

            rec.material=this.material;

            Vec3 outward_normal=v0v1.cross(v0v2).normalize();
            rec.set_face_normal(r,outward_normal);
            return true;
        }
        return false;
    }

    @Override
    String getDescription() {
        return "The triangle is formed by vertex v0("+ v0.x() + "," + v0.y() + "," + v0.z() + "), " +
                "v1(" + v1.x() + "," + v1.y() + "," + v1.z() + "), " +
                "v2(" + v2.x() + "," + v2.y() + "," + v2.z() + ")";
    }

    @Override
    public Node getGeomety(Document doc) {
        return null;
    }

    @Override
    public Primitive clone() {
        return new Triangle(
                new Vec3(v0.x(),v0.y(),v0.z()),
                new Vec3(v1.x(),v1.y(),v1.z()),
                new Vec3(v2.x(),v2.y(),v2.z()),
                super.material= material.clone());
    }
}

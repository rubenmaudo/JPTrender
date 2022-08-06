package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Triangle extends Primitive{

    //TRIANGLE FIELDS
    Vec3 v0,v1,v2; //Triangle vertex
    Vec3 vn0,vn1,vn2; //Triangle vertex
    boolean vertexNormalCalc;
    double kEpsilon = 0.00000001;

    //CONSTRUCTOR

    /**
     * Triangle constructor based on three vertex
     * @param v0 vertex normal
     * @param v1 vertex normal
     * @param v2 vertex normal
     */
    public Triangle(Vec3 v0, Vec3 v1, Vec3 v2, Material material) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        super.material= material;

        vertexNormalCalc=false;

        create_bounding_box();
    }

    /**
     *
     * @param v0 vertex
     * @param v1 vertex
     * @param v2 vertex
     * @param vn0 vertex normal
     * @param vn1 vertex normal
     * @param vn2 vertex normal
     * @param material
     */
    public Triangle(Vec3 v0, Vec3 v1, Vec3 v2, Vec3 vn0, Vec3 vn1, Vec3 vn2, Material material) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.vn0 = vn0;
        this.vn1 = vn1;
        this.vn2 = vn2;
        super.material= material;

        vertexNormalCalc=true;

        create_bounding_box();
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

            Vec3 outward_normal;
            if(vertexNormalCalc){
                //(1 - uv.x - uv.y) * n0 + uv.x * n1 + uv.y * n2;
                //Smoothing the shader using vertex normal
                outward_normal=(vn0.product(1-u-v).add(vn1.product(u))).add(vn2.product(v));
            }else{
                //Smoothing off
                outward_normal=v0v1.cross(v0v2).normalize();
            }
            rec.set_face_normal(r,outward_normal);
            return true;
        }
        return false;

    }

    @Override
    public Node getGeomety(Document doc) {
        return null;
    }

    @Override
    void create_bounding_box() {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        double tempMinX,tempMinY,tempMinZ,tempMaxX,tempMaxY,tempMaxZ;

        tempMinX=min(min(v0.x(),v1.x()),v2.x());
        tempMinY=min(min(v0.y(),v1.y()),v2.y());
        tempMinZ=min(min(v0.z(),v1.z()),v2.z());
        tempMaxX=max(max(v0.x(),v1.x()),v2.x());
        tempMaxY=max(max(v0.y(),v1.y()),v2.y());
        tempMaxZ=max(max(v0.z(),v1.z()),v2.z());

        this.boundingBox=new AABB(new Vec3(tempMinX,tempMinY,tempMinZ),new Vec3(tempMaxX,tempMaxY,tempMaxZ));
    }
}

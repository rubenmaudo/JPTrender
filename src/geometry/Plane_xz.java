package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author : Ruben Maudo
 * @since : 27/06/2020
 **/
public class Plane_xz extends Primitive{
    //PLANE FIELDS
    double x0,x1,z0,z1,k;

    double width;
    double depth;
    Vec3 centreBasePoint;
    boolean flipped;
    Material material;

    //CONSTRUCTOR
    public Plane_xz(double x0, double x1, double z0, double z1, double k, Material material){
        this.x0=x0;
        this.x1=x1;
        this.z0=z0;
        this.z1=z1;
        this.k=k;
        this.material=material;
        this.flipped=false;
    }

    public Plane_xz(double x0, double x1, double z0, double z1, double k,boolean flipped, Material material){
        this.x0=x0;
        this.x1=x1;
        this.z0=z0;
        this.z1=z1;
        this.k=k;
        this.material=material;
        this.flipped=flipped;
    }

    public Plane_xz(double width, double depth, Vec3 centreBasePoint, Material material){
        this.width=width;
        this.depth=depth;
        this.centreBasePoint=centreBasePoint;
        this.flipped=false;
        this.material=material;

        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.z0=centreBasePoint.z()-depth/2;
        this.z1=centreBasePoint.z()+depth/2;
        this.k=centreBasePoint.y();
        this.material=material;
    }

    public Plane_xz(double width, double depth, Vec3 centreBasePoint,boolean flipped, Material material){
        this.width=width;
        this.depth=depth;
        this.centreBasePoint=centreBasePoint;
        this.flipped=flipped;
        this.material=material;

        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.z0=centreBasePoint.z()-depth/2;
        this.z1=centreBasePoint.z()+depth/2;
        this.k=centreBasePoint.y();
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
        double t=(k-r.origin().y()) / r.direction().y();
        if(t<t_min || t>t_max){
            return false;
        }
        double x = r.origin().x() + t*r.direction().x();
        double z = r.origin().z() + t*r.direction().z();
        if(x < x0 || x > x1 || z<z0 || z>z1){
            return false;
        }
        //TO BE DEFINED IN THE FUTURE
        //rec.u= (x-x0)/(x1-x0);
        //rec.v= (y-y0)/(y1-y0);

        rec.t=t;
        Vec3 outward_normal;
        if(flipped){
            outward_normal=new Vec3(0,-1,0);
        }else{
            outward_normal=new Vec3(0,1,0);
        }
        rec.set_face_normal(r, outward_normal);
        rec.material=this.material;
        rec.p=r.point_at_parameter(t);
        return true;
    }
    //-----------------TO BE DEFINED IN FUTURE-----------------
    public boolean bounding_box(double t0, double t1){
        return false;
    }

    @Override
    String getDescription() {
        return "A plane";
    }

    @Override
    public Node getGeomety(Document doc) {
        Element planeXZ=doc.createElement("PlaneXZ");
        planeXZ.setAttribute("width", String.valueOf(this.width));
        planeXZ.setAttribute("depth", String.valueOf(this.depth));
        planeXZ.setAttribute("centreBasePointX", String.valueOf(this.centreBasePoint.getValue(0)));
        planeXZ.setAttribute("centreBasePointY", String.valueOf(this.centreBasePoint.getValue(1)));
        planeXZ.setAttribute("centreBasePointZ", String.valueOf(this.centreBasePoint.getValue(2)));
        planeXZ.setAttribute("flipped", String.valueOf(this.flipped));

        planeXZ.appendChild(material.getMaterial(doc));

        return planeXZ;
    }
}

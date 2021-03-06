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
public class Plane_yz extends Primitive{
    //PLANE FIELDS
    double y0,y1,z0,z1,k;

    double width;
    double height;
    Vec3 centreBasePoint;
    boolean flipped;
    Material material;

    //CONSTRUCTOR

    /**
     * Constructor that create a plane based in insertion points
     * @param y0
     * @param y1
     * @param z0
     * @param z1
     * @param k
     * @param material
     */
    public Plane_yz( double y0, double y1, double z0, double z1, double k, Material material){
        this.y0=y0;
        this.y1=y1;
        this.z0=z0;
        this.z1=z1;
        this.k=k;
        this.material=material;
        this.flipped=false;
    }

    /**
     * Constructor that create a plane based in insertion points
     * @param y0
     * @param y1
     * @param z0
     * @param z1
     * @param k
     * @param flipped
     * @param material
     */
    public Plane_yz( double y0, double y1, double z0, double z1, double k, boolean flipped, Material material){
        this.y0=y0;
        this.y1=y1;
        this.z0=z0;
        this.z1=z1;
        this.k=k;
        this.material=material;
        this.flipped=true;
    }

    /**
     * Constructor that create a plane based in parameters
     * @param width
     * @param height
     * @param centreBasePoint
     * @param material
     */
    public Plane_yz(double width, double height, Vec3 centreBasePoint, Material material){
        this.width=width;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        this.flipped=false;
        this.material=material;

        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.z0=centreBasePoint.z()-width/2;
        this.z1=centreBasePoint.z()+width/2;
        this.k=centreBasePoint.x();
    }

    /**
     * Constructor that create a plane based in parameters
     * @param width
     * @param height
     * @param centreBasePoint
     * @param flipped
     * @param material
     */
    public Plane_yz(double width, double height, Vec3 centreBasePoint,boolean flipped, Material material){
        this.width=width;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        this.flipped=flipped;
        this.material=material;

        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.z0=centreBasePoint.z()-width/2;
        this.z1=centreBasePoint.z()+width/2;
        this.k=centreBasePoint.x();
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
        double t=(k-r.origin().x()) / r.direction().x();
        if(t<t_min || t>t_max){
            return false;
        }
        double y = r.origin().y() + t*r.direction().y();
        double z = r.origin().z() + t*r.direction().z();
        if(y < y0 || y > y1 || z<z0 || z>z1){
            return false;
        }
        //TO BE DEFINED IN THE FUTURE
        //rec.u= (x-x0)/(x1-x0);
        //rec.v= (y-y0)/(y1-y0);

        rec.t=t;
        Vec3 outward_normal;
        if(flipped){
            outward_normal=new Vec3(-1,0,0);
        }else{
            outward_normal=new Vec3(1,0,0);
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
        Element planeYZ=doc.createElement("PlaneYZ");
        planeYZ.setAttribute("width", String.valueOf(this.width));
        planeYZ.setAttribute("height", String.valueOf(this.height));
        planeYZ.setAttribute("centreBasePointX", String.valueOf(this.centreBasePoint.getValue(0)));
        planeYZ.setAttribute("centreBasePointY", String.valueOf(this.centreBasePoint.getValue(1)));
        planeYZ.setAttribute("centreBasePointZ", String.valueOf(this.centreBasePoint.getValue(2)));
        planeYZ.setAttribute("flipped", String.valueOf(this.flipped));

        planeYZ.appendChild(material.getMaterial(doc));

        return planeYZ;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Vec3 getCentreBasePoint() {
        return centreBasePoint;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public Primitive clone() {
        return new Plane_yz(this.getWidth(), this.getHeight(), Vec3.clone(this.getCentreBasePoint()), this.isFlipped(), this.getMaterial().clone());
    }
}

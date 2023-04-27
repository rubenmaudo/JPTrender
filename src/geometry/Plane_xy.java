package geometry;

import materials.Material;
import maths.Ray;
import maths.Utils;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static java.lang.Math.*;

/**
 * @author : Ruben Maudo
 * @since : 27/06/2020
 **/
public class Plane_xy extends Primitive{
    //PLANE FIELDS
    double x0,x1,y0,y1,k;

    double width;
    double height;
    Vec3 centreBasePoint;
    boolean flipped;
    Material material;

    //CONSTRUCTOR

    /**
     * Constructor that create a plane based in insertion points
     * @param x0
     * @param x1
     * @param y0
     * @param y1
     * @param k
     * @param material
     */
    public Plane_xy(double x0, double x1, double y0, double y1, double k, Material material){
        this.x0=x0;
        this.x1=x1;
        this.y0=y0;
        this.y1=y1;
        this.k=k;
        this.material=material;
        this.flipped=false;
        create_bounding_box();
    }

    /**
     * Constructor that create a plane based in insertion points
     * @param x0
     * @param x1
     * @param y0
     * @param y1
     * @param k
     * @param flipped
     * @param material
     */
    public Plane_xy(double x0, double x1, double y0, double y1, double k,boolean flipped, Material material){
        this.x0=x0;
        this.x1=x1;
        this.y0=y0;
        this.y1=y1;
        this.k=k;
        this.material=material;
        this.flipped=flipped;
        create_bounding_box();
    }

    /**
     * Constructor that create a plane based in parameters
     * @param width
     * @param height
     * @param centreBasePoint Base of plane
     * @param material
     */
    public Plane_xy(double width, double height, Vec3 centreBasePoint, Material material){
        this.width=width;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        this.flipped=false;
        this.material=material;

        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.k=centreBasePoint.z();
        create_bounding_box();
    }

    /**
     * Constructor that create a plane based in parameters
     * @param width
     * @param height
     * @param centreBasePoint
     * @param flipped
     * @param material
     */
    public Plane_xy(double width, double height, Vec3 centreBasePoint,boolean flipped, Material material){
        this.width=width;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        this.flipped=flipped;
        this.material=material;


        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.k=centreBasePoint.z();

        create_bounding_box();

    }

    //METHODS
    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        double t=(k-r.origin().z()) / r.direction().z();
        if(t<t_min || t>t_max){
            return false;
        }
        double x = r.origin().x() + t*r.direction().x();
        double y = r.origin().y() + t*r.direction().y();
        if(x < x0 || x > x1 || y<y0 || y>y1){
            return false;
        }
        //TO BE DEFINED IN THE FUTURE
        //rec.u= (x-x0)/(x1-x0);
        //rec.v= (y-y0)/(y1-y0);

        rec.t=t;
        Vec3 outward_normal;
        if(flipped){
            outward_normal=new Vec3(0,0,-1);
        }else{
            outward_normal=new Vec3(0,0,1);
        }

        rec.set_face_normal(r, outward_normal);
        rec.material=this.material;
        rec.p=r.point_at_parameter(t);
        return true;

    }

    public double pdf_value(Vec3 o, Vec3 v) {
        Hit_record rec=new Hit_record();
        if(!this.hit(new Ray(o,v),0.001, Utils.INFINITY,rec))
            return 0;

        double area= (x1-x0)*(y1-y0);
        double distance_squared=rec.t* rec.t*v.squared_length();
        double cosine= abs(v.dotProduct(rec.normal)/v.length());

        return distance_squared / (cosine*area);
    }

    public Vec3 random(Vec3 origin) {
        Vec3 random_point= new Vec3(Vec3.random_double(x0,x1),Vec3.random_double(y0,y1),k);
        return random_point.sub(origin);
    }

    @Override
    public Node saveGeomety(Document doc) {
        Element planeXY=doc.createElement("PlaneXY");
        planeXY.setAttribute("width", String.valueOf(this.width));
        planeXY.setAttribute("height", String.valueOf(this.height));
        planeXY.setAttribute("centreBasePointX", String.valueOf(this.centreBasePoint.getValue(0)));
        planeXY.setAttribute("centreBasePointY", String.valueOf(this.centreBasePoint.getValue(1)));
        planeXY.setAttribute("centreBasePointZ", String.valueOf(this.centreBasePoint.getValue(2)));
        planeXY.setAttribute("flipped", String.valueOf(this.flipped));

        planeXY.appendChild(material.getMaterial(doc));

        return planeXY;
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
    void create_bounding_box() {
        this.boundingBox=new AABB(new Vec3(min(x0,x1),min(y0,y1),k),new Vec3(max(x0,x1),max(y0,y1),k+0.0000001));
    }
}

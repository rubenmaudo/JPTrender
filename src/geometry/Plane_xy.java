package geometry;

import materials.Material;
import maths.Ray;
import maths.Vec3;

/**
 * @author : Ruben Maudo
 * @since : 27/06/2020
 **/
public class Plane_xy extends Primitive{
    //PLANE FIELDS
    double x0,x1,y0,y1,k;
    boolean flipped;

    //CONSTRUCTOR
    public Plane_xy(double x0, double x1, double y0, double y1, double k, Material material){
        this.x0=x0;
        this.x1=x1;
        this.y0=y0;
        this.y1=y1;
        this.k=k;
        this.material=material;
        this.flipped=false;
    }

    public Plane_xy(double x0, double x1, double y0, double y1, double k,boolean flipped, Material material){
        this.x0=x0;
        this.x1=x1;
        this.y0=y0;
        this.y1=y1;
        this.k=k;
        this.material=material;
        this.flipped=flipped;
    }

    public Plane_xy(double width, double height, Vec3 centreBasePoint, Material material){
        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.k=centreBasePoint.z();
        this.material=material;
        this.flipped=false;
    }

    public Plane_xy(double width, double height, Vec3 centreBasePoint,boolean flipped, Material material){
        this.x0=centreBasePoint.x()-width/2;
        this.x1=centreBasePoint.x()+width/2;
        this.y0=centreBasePoint.y()-height/2;
        this.y1=centreBasePoint.y()+height/2;
        this.k=centreBasePoint.z();
        this.material=material;
        this.flipped=flipped;
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
    //-----------------TO BE DEFINED IN FUTURE-----------------
    public boolean bounding_box(double t0, double t1){
        return false;
    }

    @Override
    String getDescription() {
        return "A plane";
    }
}

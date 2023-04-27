package geometry;

import materials.Lambertian;
import materials.Material;
import maths.Hittable;
import maths.Pdf.Hittable_list_pdf;
import maths.Ray;
import maths.Utils;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * @author : Ruben Maudo
 * @since : 28/06/2020
 **/
public class Box extends Primitive{
    double width;
    double depth;
    double height;
    Vec3 centreBasePoint;
    double rotationAngle;
    double sin_theta;
    double cos_theta;

    ArrayList<Primitive> planesList;
    Hittable tempHittable;


    /**
     * Constructor that create a box object based on parameters passed
     * @param width
     * @param depth
     * @param height
     * @param centreBasePoint Base X,Y,Z
     * @param material
     */
    public Box(double width, double depth, double height, Vec3 centreBasePoint, Material material, double angle){
        this.width=width;
        this.depth=depth;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        super.material=material;

        this.rotationAngle=angle;
        double radians= Utils.degrees_to_radians(rotationAngle);

        this.sin_theta= Math.sin(radians);
        this.cos_theta= Math.cos(radians);


        //The box is created based on combination of flats
        planesList=new ArrayList<>();
        planesList.add(new Plane_xz(width, depth, new Vec3(centreBasePoint.x(),centreBasePoint.y()+height,centreBasePoint.z()),material));

        //TODO remove hard coded isSampled
        planesList.add(new Plane_xz(width, depth, centreBasePoint,true,false,material));


        planesList.add(new Plane_xy(width, height, new Vec3(centreBasePoint.x(),centreBasePoint.y()+(height/2),centreBasePoint.z()+(depth/2)), material));
        planesList.add(new Plane_xy(width, height, new Vec3(centreBasePoint.x(),centreBasePoint.y()+(height/2),centreBasePoint.z()-(depth/2)),true, material));

        planesList.add(new Plane_yz(depth, height, new Vec3(centreBasePoint.x()+(width/2),centreBasePoint.y()+(height/2),centreBasePoint.z()), material));
        planesList.add(new Plane_yz(depth, height, new Vec3(centreBasePoint.x()-(width/2),centreBasePoint.y()+(height/2),centreBasePoint.z()), true,material));

        tempHittable= new Hittable(planesList,new BVH_node(planesList));

        create_bounding_box();
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        if (rotationAngle!=0){

            Vec3 origin = new Vec3(r.origin().getValue(0),r.origin().getValue(1),r.origin().getValue(2));
            Vec3 direction = new Vec3(r.direction().getValue(0),r.direction().getValue(1),r.direction().getValue(2));

            origin.setValue(0, (cos_theta*r.origin().getValue(0)-sin_theta*r.origin().getValue(2)));
            origin.setValue(2, (sin_theta*r.origin().getValue(0)+cos_theta*r.origin().getValue(2)));

            direction.setValue(0, cos_theta*r.direction().getValue(0)-sin_theta*r.direction().getValue(2));
            direction.setValue(2, sin_theta*r.direction().getValue(0)+cos_theta*r.direction().getValue(2));

            Ray rotated_r=new Ray(origin, direction);


            if(!tempHittable.hit(rotated_r, t_min, t_max, rec)){
                return false;
            }else{

                if (rec.material==null){
                    return false;
                };

                Vec3 p = new Vec3(rec.p.getValue(0),rec.p.getValue(1),rec.p.getValue(2));
                Vec3 normal = new Vec3(rec.normal.getValue(0),rec.normal.getValue(1),rec.normal.getValue(2));

                p.setValue(0,cos_theta*rec.p.getValue(0) + sin_theta*rec.p.getValue(2));
                p.setValue(2,-sin_theta*rec.p.getValue(0) + cos_theta*rec.p.getValue(2));

                normal.setValue(0,cos_theta*rec.normal.getValue(0) + sin_theta*rec.normal.getValue(2));
                normal.setValue(2,-sin_theta*rec.normal.getValue(0) + cos_theta*rec.normal.getValue(2));

                rec.p = new Vec3(p.getValue(0),p.getValue(1),p.getValue(2));
                rec.set_face_normal(r, normal);

                return true;
            }

        }else{
            return tempHittable.hit(r, t_min, t_max, rec);
        }
    }

    public double pdf_value(Vec3 o, Vec3 v) {
        Hittable_list_pdf box_planes_list=new Hittable_list_pdf(planesList,o);
        return box_planes_list.value(v);
    }

    public Vec3 random(Vec3 origin) {
        Hittable_list_pdf box_planes_list=new Hittable_list_pdf(planesList,origin);
        return box_planes_list.generate();
    }


    @Override
    public Node saveGeomety(Document doc) {
        Element box=doc.createElement("Box");
        box.setAttribute("width", String.valueOf(this.width));
        box.setAttribute("depth", String.valueOf(this.depth));
        box.setAttribute("height", String.valueOf(this.height));
        box.setAttribute("centreBasePointX", String.valueOf(this.centreBasePoint.getValue(0)));
        box.setAttribute("centreBasePointY", String.valueOf(this.centreBasePoint.getValue(1)));
        box.setAttribute("centreBasePointZ", String.valueOf(this.centreBasePoint.getValue(2)));

        box.setAttribute("rotationAngle", String.valueOf(this.rotationAngle));

        box.appendChild(material.getMaterial(doc));



        return box;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }

    public double getHeight() {
        return height;
    }

    public Vec3 getCentreBasePoint() {
        return centreBasePoint;
    }

    public void setisSampled(boolean value){
        this.isSampled=value;
        for( Primitive p : planesList){
            p.setisSampled(value);
        }
    }

    public Material getMaterial() {
        return material;
    }


    @Override
    void create_bounding_box() {
        boundingBox=new AABB(
                new Vec3(centreBasePoint.x()-(width/2),centreBasePoint.y(),centreBasePoint.z()-depth/2),
                new Vec3(centreBasePoint.x()+(width/2),centreBasePoint.y()+height,centreBasePoint.z()+depth/2)
        );

        if(rotationAngle!=0){

            Vec3 min=new Vec3(Double.MAX_VALUE);
            Vec3 max=new Vec3(-Double.MAX_VALUE);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        double x = i*boundingBox.max().x() + (1-i)*boundingBox.min().x();
                        double y = j*boundingBox.max().y() + (1-j)*boundingBox.min().y();
                        double z = k*boundingBox.max().z() + (1-k)*boundingBox.min().z();

                        double newx =  cos_theta*x + sin_theta*z;
                        double newz = -sin_theta*x + cos_theta*z;

                        Vec3 tester=new Vec3(newx, y, newz);

                        for (int c = 0; c < 3; c++) {
                            min.setValue(c,Math.min(min.getValue(c), tester.getValue(c)));
                            max.setValue(c,Math.max(max.getValue(c), tester.getValue(c)));
                        }
                    }
                }
            }

            boundingBox=new AABB(min,max);

        }
    }
}

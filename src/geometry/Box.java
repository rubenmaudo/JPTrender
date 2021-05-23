package geometry;

import materials.Material;
import maths.Hittable;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 28/06/2020
 **/
public class Box extends Primitive{
    double width;
    double depth;
    double height;
    Vec3 centreBasePoint;
    Material material;

    ArrayList<Primitive> planesList;

    /**
     * Constructor that create a box object based on parameters passed
     * @param width
     * @param depth
     * @param height
     * @param centreBasePoint Base X,Y,Z
     * @param material
     */
    public Box(double width, double depth, double height, Vec3 centreBasePoint, Material material){
        this.width=width;
        this.depth=depth;
        this.height=height;
        this.centreBasePoint=centreBasePoint;
        this.material=material;

        //The box is created based on combination of flats
        planesList=new ArrayList<>();
        planesList.add(new Plane_xz(width, depth, new Vec3(centreBasePoint.x(),centreBasePoint.y()+height,centreBasePoint.z()),material));
        planesList.add(new Plane_xz(width, depth, centreBasePoint,true,material));


        planesList.add(new Plane_xy(width, height, new Vec3(centreBasePoint.x(),centreBasePoint.y()+(height/2),centreBasePoint.z()+(depth/2)), material));
        planesList.add(new Plane_xy(width, height, new Vec3(centreBasePoint.x(),centreBasePoint.y()+(height/2),centreBasePoint.z()-(depth/2)),true, material));

        planesList.add(new Plane_yz(depth, height, new Vec3(centreBasePoint.x()+(width/2),centreBasePoint.y()+(height/2),centreBasePoint.z()), material));
        planesList.add(new Plane_yz(depth, height, new Vec3(centreBasePoint.x()-(width/2),centreBasePoint.y()+(height/2),centreBasePoint.z()), true,material));
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
        Hittable tempHittable=new Hittable(planesList);

        return tempHittable.hit(r,t_min,t_max,rec);
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    public Node getGeomety(Document doc) {
        Element box=doc.createElement("Box");
        box.setAttribute("width", String.valueOf(this.width));
        box.setAttribute("depth", String.valueOf(this.depth));
        box.setAttribute("height", String.valueOf(this.height));
        box.setAttribute("centreBasePointX", String.valueOf(this.centreBasePoint.getValue(0)));
        box.setAttribute("centreBasePointY", String.valueOf(this.centreBasePoint.getValue(1)));
        box.setAttribute("centreBasePointZ", String.valueOf(this.centreBasePoint.getValue(2)));

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

    public Material getMaterial() {
        return material;
    }

    @Override
    public Primitive clone() {
        return new Box(this.getWidth(),this.getDepth(),this.getHeight(),Vec3.clone(this.getCentreBasePoint()),this.getMaterial().clone());
    }
}

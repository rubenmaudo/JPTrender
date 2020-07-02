package geometry;

import materials.Material;
import maths.Hittable;
import maths.Ray;
import maths.Vec3;

import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 28/06/2020
 **/
public class Box extends Primitive{
    ArrayList<Primitive> planesList;

    public Box(double width, double depth, double height, Vec3 centreBasePoint, Material material){
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
}

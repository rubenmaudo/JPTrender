package geometry;

import elements.Obj_read;
import materials.Material;
import maths.Hittable;
import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class Mesh extends Primitive{

    Vec3 centreBasePoint;

    ArrayList<Primitive> triangleslist;
    BVH_node bvh_node;

    public Mesh(String file_path, Vec3 base_point,Material material){
        this.centreBasePoint=base_point;
        super.material=material;

        Obj_read meshImport=new Obj_read(file_path,material);

        this.triangleslist=meshImport.getTriangleslist();

        this.bvh_node=new BVH_node(triangleslist);

        this.boundingBox= meshImport.getBoundingBox();

    }


    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        return new Hittable(triangleslist,bvh_node).hit(r, t_min, t_max, rec);
    }
    @Override
    public Node getGeomety(Document doc) {
        return null;
    }


    @Override
    void create_bounding_box() {

    }
}

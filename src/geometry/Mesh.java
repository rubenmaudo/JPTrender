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
    public ArrayList<Primitive> triangleslist;
    Vec3 centreBasePoint;
    Material material;

    Hittable triangleListHit;

    Box oldBoundingBox;

    BVH_node newBVH_Node;



    public Mesh(String file_path, Vec3 base_point,Material material){
        this.centreBasePoint=base_point;
        this.material=material;

        Obj_read meshImport=new Obj_read(file_path,material);

        this.triangleslist=meshImport.getTriangleslist();
        this.oldBoundingBox=meshImport.getOldBoundingBox();

        this.newBVH_Node= new BVH_node(triangleslist);

        this.triangleListHit=new Hittable(meshImport.getTriangleslist(),newBVH_Node);
        this.boundingBox= meshImport.getBoundingBox();


    }


    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        return triangleListHit.hit(r,t_min,t_max,rec);

        /*
        //ORIGINAL DESIGN WITH BOXES...
        ArrayList<Primitive> boundingBoxArray=new ArrayList<Primitive>();
        boundingBoxArray.add(oldBoundingBox);

        Hittable tempHittable1=new Hittable(boundingBoxArray);
        if(tempHittable1.hit(r,t_min,t_max,new Hit_record())){
            Hittable tempHittable2=new Hittable(triangleslist);
            return tempHittable2.hit(r,t_min,t_max,rec);
        }return false;

         */


    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    public Node getGeomety(Document doc) {
        return null;
    }

    @Override
    public Primitive clone() {
        return null;
    }

    @Override
    void create_bounding_box() {

    }
}

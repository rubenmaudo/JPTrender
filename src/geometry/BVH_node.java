package geometry;

import maths.Ray;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Math.*;

public class BVH_node extends Primitive{

    BVH_node leftNode;
    BVH_node rightNode;

    Primitive leftPrimitive;
    Primitive rightPrimitive;

    int axis; //Axis to order the nodes/primitives inside

    int nodeType;
    public BVH_node(ArrayList<Primitive> list) {

        if (list.size()>2){
            nodeType =3;
        }else nodeType =list.size();


        switch(nodeType) {
            case 3:
                //Choose a random axis to sort the primitives
                axis= java.util.concurrent.ThreadLocalRandom.current().nextInt(0,3);

                //Sort the list based on the axis
                Collections.sort(list, new Comparator<Primitive>() {
                    @Override
                    public int compare(Primitive p1, Primitive p2) {
                        if(p1.boundingBox.min().getValue(axis)<p2.boundingBox.min().getValue(axis)) return -1;
                        if(p1.boundingBox.min().getValue(axis)==p2.boundingBox.min().getValue(axis)) return 0;
                        return 1;
                    }
                });


                ArrayList<Primitive> left_temp=new ArrayList<>();
                ArrayList<Primitive> right_temp=new ArrayList<>();

                for (int i=0; i<(int) round(list.size()/2.);i++){
                    left_temp.add(list.get(i));
                }
                for (int i = (int) round(list.size()/2.); i<=list.size()-1; i++){
                    right_temp.add(list.get(i));
                }

                leftNode =new BVH_node(left_temp);
                rightNode=new BVH_node(right_temp);

                create_bounding_box();
                break;

            case 2:
                leftPrimitive =list.get(0);
                rightPrimitive=list.get(1);
                create_bounding_box();
                break;

            case 1:
                leftPrimitive =list.get(0);
                this.boundingBox= leftPrimitive.getAABB();
                break;
        }
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {

        double closest_so_far;

        closest_so_far= (rec.material!=null) ? min(t_max,rec.t) : t_max;

        if (nodeType >0 && boundingBox.hit(r, t_min, closest_so_far, rec)) {

            boolean hitAnything = false;

            switch(nodeType) {
                case 3:

                    if(leftNode.hit(r, t_min, closest_so_far, rec)) {
                        hitAnything = true;
                        closest_so_far=rec.t;
                    }

                    if(rightNode.hit(r, t_min, closest_so_far,rec)) hitAnything = true;

                    break;

                case 2:
                    if(leftPrimitive.getAABB().hit(r,t_min,closest_so_far, rec) &&
                            leftPrimitive.hit(r,t_min,closest_so_far, rec)){
                        closest_so_far=rec.t;
                        hitAnything=true;
                    }

                    if(rightPrimitive.getAABB().hit(r,t_min,closest_so_far, rec) &&
                            rightPrimitive.hit(r,t_min,closest_so_far, rec)){
                        hitAnything=true;
                    }
                    break;

                case 1:
                    if(leftPrimitive.hit(r, t_min, closest_so_far, rec)){
                        hitAnything=true;
                    }
                    break;

                case 0:
                    break;
            }
            return hitAnything;
        }
        return false;
    }


    @Override
    public Node saveGeomety(Document doc) {
        return null;
    }

    @Override
    void create_bounding_box() {

        Vec3 temp_min=new Vec3(0);
        Vec3 temp_max=new Vec3(0);

        if (nodeType ==2){
            for(int i=0; i<3; i++){
                temp_min.setValue(i,min(leftPrimitive.getAABB().min().getValue(i),rightPrimitive.getAABB().min().getValue(i)));
                temp_max.setValue(i,max(leftPrimitive.getAABB().max().getValue(i),rightPrimitive.getAABB().max().getValue(i)));
            }
        }else{
            for(int i=0; i<3; i++){
                temp_min.setValue(i,min(leftNode.getAABB().min().getValue(i),rightNode.getAABB().min().getValue(i)));
                temp_max.setValue(i,max(leftNode.getAABB().max().getValue(i),rightNode.getAABB().max().getValue(i)));
            }
        }
        this.boundingBox=new AABB(temp_min,temp_max);
    }
}

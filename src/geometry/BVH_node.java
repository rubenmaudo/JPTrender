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
    boolean singlefinalBranch=false;
    boolean doublefinalBranch=false;


    public BVH_node(ArrayList<Primitive> list) {

        ArrayList<Primitive> tempList=new ArrayList<>();
        tempList= (ArrayList<Primitive>) list.clone();

        if(tempList.size()==1){
            leftPrimitive =tempList.get(0);
            singlefinalBranch =true;
            this.boundingBox= leftPrimitive.getAABB();
        }else if(tempList.size()==2){
            leftPrimitive =tempList.get(0);
            rightPrimitive=tempList.get(1);
            doublefinalBranch =true;
            create_bounding_box();
        }else{
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
        }
    }

    @Override
    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
        return false;
    }

    public boolean hit(Ray r, double t_min, double t_max, Hit_record rec, ArrayList<Primitive> tempListHit) {
        if (boundingBox.hit(r, t_min, t_max, rec)) {

            /*
            if (singlefinalBranch ==true && leftPrimitive.getAABB().hit(r,t_min,t_max, rec)){

                tempListHit.add(leftPrimitive);
                return true;
            }

             */
            if (singlefinalBranch) {

                tempListHit.add(leftPrimitive);
                return true;
            }


                if (doublefinalBranch) {

                /*
                boolean hitAnything=false;
                if(leftPrimitive.getAABB().hit(r,t_min,t_max, rec)){
                    tempListHit.add(leftPrimitive);
                    hitAnything=true;
                }
                if(rightPrimitive.getAABB().hit(r,t_min,t_max, rec)){
                    tempListHit.add(rightPrimitive);
                    hitAnything=true;
                }
                if (hitAnything) return true;
                */

                    tempListHit.add(leftPrimitive);
                    tempListHit.add(rightPrimitive);

                    return true;

                } else {
                    boolean hitAnything = false;
                    if (leftNode.hit(r, t_min, t_max, rec, tempListHit)) hitAnything = true;
                    if (rightNode.hit(r, t_min, t_max, rec, tempListHit)) hitAnything = true;
                    return hitAnything;
                }
            }
            return false;
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

        Vec3 temp_min=new Vec3(0);
        Vec3 temp_max=new Vec3(0);

        if (doublefinalBranch){
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

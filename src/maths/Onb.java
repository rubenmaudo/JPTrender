package maths;

import static java.lang.Math.abs;

public class Onb {

    Vec3 axis[]=new Vec3[3];

    public Onb() {
    }

    public Vec3 u(){return axis[0];}
    public Vec3 v(){return axis[1];}
    public Vec3 w(){return axis[2];}

    public Vec3 local (double a, double b, double c){
        return u().product(a).add(v().product(b)).add(w().product(c));
    }

    public Vec3 local (Vec3 a){
        return u().product(a.x()).add(v().product(a.y())).add(w().product(a.z()));
    }

    public void build_from_w(Vec3 n){
        axis[2]=n.normalize();
        Vec3 a=(abs(w().x())>0.9) ? new Vec3(0,1,0) : new Vec3(1,0,0);
        axis[1]=w().cross(a).normalize();
        axis[0]=w().cross(v());
    }

}

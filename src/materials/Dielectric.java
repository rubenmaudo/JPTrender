package materials;

import math.*;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class Dielectric extends Material{
    double ref_idx; //Refraction index

    public Dielectric(double ri){
        this.ref_idx = ri;
    }

    @Override
    public boolean scatter(Ray r_in, Intersection inters) {
        this.attenuation= new ColorValue(1,1,1);
        double etai_over_etat;

        Primitive temp= inters.getPrim();
        if(temp.front_face){
            etai_over_etat = 1 / ref_idx;
        }else{
            etai_over_etat = ref_idx;
        }

        Vec3 unit_direction=r_in.direction().normalize();

        double cos_theta = min(unit_direction.product(-1).dotProduct(temp.normal),1);
        double sin_theta = sqrt(1 - cos_theta*cos_theta);
        if (etai_over_etat * sin_theta > 1){
            Vec3 reflected = reflect(unit_direction, temp.normal);
            this.scattered = new Ray(temp.p,reflected);
            return true;
        }

        double reflect_prob = schlick(cos_theta, etai_over_etat);
        if(Math.random()<reflect_prob){
            Vec3 reflected=reflect(unit_direction, temp.normal);
            this.scattered =new Ray(temp.p, reflected);
            return true;
        }

        Vec3 refracted = refract(unit_direction,temp.normal,etai_over_etat);
        this.scattered = new Ray(temp.p,refracted);
        return true;
    }
}

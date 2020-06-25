package materials;

import geometry.Hit_record;
import maths.*;

import static java.lang.Math.*;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/


/*
 * Standard dielectric (refractive) material that is defined for the color and the refraction index
 */
public class Dielectric extends Material{
    //DIELECTRIC FIELDS
    double ref_idx; //Refraction index
    double fuzz; //Refraction/reflection fuzziness

    //CONSTRUCTORS
    /**
     * Constructor to create a "clear" material with an indicated refraction index
     * @param ri refraction index
     */
    public Dielectric(double ri){
        this.ref_idx = ri;
        this.attenuation=new ColorValue(1,1,1);
    }

    /**
     * Constructor to create a "clear" material with an indicated refraction index and a level of fuzziness
     * @param ri refraction index
     * @param fuzz fuzziness
     */
    public Dielectric(double ri, double fuzz){
        this.ref_idx = ri;
        this.attenuation=new ColorValue(1,1,1);
        this.fuzz=fuzz;
    }

    /**
     * Constructor to create a "colored" glass material with an indicated refraction index
     * @param color Attenuation color (color)
     * @param ri refraction index
     */
    public Dielectric(ColorValue color, double ri){
        this.ref_idx = ri;
        this.attenuation=color;
        this.fuzz=0;
    }

    /**
     *
     * @param color Attenuation color (color)
     * @param ri refraction index
     * @param fuzz fuzziness
     */
    public Dielectric(ColorValue color, double ri, double fuzz){
        this.ref_idx = ri;
        this.attenuation=color;
        this.fuzz=fuzz;
    }

    //METHODS
    @Override
    public boolean scatter(Ray r_in, Hit_record rec) {
        double etai_over_etat;

        //Check if the ray is going into the material or going outside
        if(rec.front_face){
            etai_over_etat = 1 / ref_idx;
        }else{
            etai_over_etat = ref_idx;
        }

        Vec3 unit_direction=r_in.direction().normalize();

        double cos_theta = min(unit_direction.product(-1).dotProduct(rec.normal),1);
        double sin_theta = sqrt(1 - cos_theta*cos_theta);
        if (etai_over_etat * sin_theta > 1){
            Vec3 reflected = reflect(unit_direction, rec.normal);
            this.scattered = new Ray(rec.p,reflected.add(Vec3.random_in_unit_sphere().product(fuzz)));
            return true;
        }

        double reflect_prob = schlick(cos_theta, etai_over_etat);
        if(Math.random()<reflect_prob){
            Vec3 reflected=reflect(unit_direction, rec.normal);
            this.scattered =new Ray(rec.p, reflected.add(Vec3.random_in_unit_sphere().product(fuzz)));
            return true;
        }

        Vec3 refracted = refract(unit_direction, rec.normal,etai_over_etat);
        this.scattered = new Ray(rec.p,refracted.add(Vec3.random_in_unit_sphere().product(fuzz)));
        return true;
    }

    /**
     * Calc the refracted vector
     * @param uv vector uv
     * @param n vector n
     * @param etai_over_etat indicator of face
     * @return vector refract
     */
    public Vec3 refract(Vec3 uv, Vec3 n, double etai_over_etat){
        double cos_theta = uv.product(-1).dotProduct(n);
        Vec3 r_out_parallel = uv.add(n.product(cos_theta)).product(etai_over_etat);
        Vec3 r_out_perp= n.product(-sqrt(1-r_out_parallel.squared_length()));
        return r_out_parallel.add(r_out_perp);
    }

    /**
     * Calc the probability that the ray is reflected or refracted
     * @param cosine cosine
     * @param ref_idx reflexion index
     * @return double schlick
     */
    double schlick(double cosine, double ref_idx){
        double r0 = (1-ref_idx) / (1+ref_idx);
        r0= r0 * r0;
        return r0 + (1-r0) * pow((1- cosine),5);
    }

}

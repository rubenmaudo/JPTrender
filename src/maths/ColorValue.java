package maths;

import geometry.*;
import materials.Diffuse_light;
import materials.Lambertian;
import materials.Metal;
import maths.Pdf.Cosine_pdf;
import maths.Pdf.Hittable_list_pdf;
import maths.Pdf.Mixture_pdf;
import maths.Pdf.Primitive_pdf;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static maths.Utils.INFINITY;
import static maths.Vec3.random_double;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class ColorValue implements Serializable {

    //COLORVALUE FIELDS
    double vR,vG,vB;

    //CONSTRUCTOR
    public ColorValue(double vR, double vG, double vB) {
        this.vR = vR;
        this.vG = vG;
        this.vB = vB;
    }

    public ColorValue(double vR, double vG, double vB, double gammaValue) {
        this.vR = gammaCorrection(vR,gammaValue);
        this.vG = gammaCorrection(vG,gammaValue);
        this.vB = gammaCorrection(vB,gammaValue);
    }


    //TODO Refactor colorvalue for texture solid to be used

    //METHODS

    /**
     * Method that take a ray direction (from camera) and go through the array of elements in scene checking colisions
     * and obtaining the color of the hit object
     * @param r Ray to use
     * @param world list of items to hit with other information
     * @param depth how many bounces are allowed
     * @return a color value
     */
    public static ColorValue colorRay(Ray r, Hittable world, int depth, Background background) {

        Hit_record rec = world.getTemp_rec();

        //if depth is less or equal to 0 we return a black color
        if (depth <= 0)
            return new ColorValue(0, 0, 0);

        //EDITED FROM HERE

        //Background
        if (!world.hit(r, 0.001, INFINITY, rec)) {
            Vec3 unit_direction=(r.direction().normalize());
            double t= 0.5*(unit_direction.y() + 1);

            double angle=1.3;
            Vec3 sundir=new Vec3(0,cos(angle),sin(angle));

            if (background.getMixed()){
                //return background.getSecondaryColor().product(1-t).add(background.getMainColor().product(t));
                Atmosphere sky=new Atmosphere(sundir);
                Ray ray=r;
                ray.setPointOrigin(r.getPointOrigin().add(new Vec3(0,6360e3,0)));
                return sky.computeIncidentLight(ray);

            }else {
                return background.getMainColor();
            }
        }

        Scatter_record srec= new Scatter_record();

        ColorValue emitted = rec.material.emitted(r,rec,rec.u,rec.v,rec.p);

        if (!rec.material.scatter(r, rec, srec)) {
            return emitted;
        }

        if(srec.is_specular){
            return srec.attenuation.product(colorRay(srec.specular_ray, new Hittable(world.list,world.nodeList), depth - 1, background));
        }



        /*PRIOR TO CHAPTER 10.1
        Vec3 on_light= new Vec3(random_double(-65,65),554,random_double(-65,65));
        Vec3 to_light=on_light.sub(rec.p);
        double distance_squared=to_light.squared_length();
        to_light=to_light.normalize();

        if(to_light.dotProduct(rec.normal)<0) return rec.material.emitted();

        double light_area=(343-213)*(332-227);
        double light_cosine=abs(to_light.y());
        if(light_cosine<0.000001) return rec.material.emitted();

        double pdf = distance_squared / (light_cosine*light_area);
        Ray scattered=new Ray(rec.p,to_light);

         */


        /*PRIOR TO CHAPTER 10.2
        Cosine_pdf p=new Cosine_pdf(rec.normal);
        Ray scattered=new Ray(rec.p,p.generate());
        double pdf_val=p.value(scattered.direction());

         */

        //Box lightBox=new Box(30,30,30,new Vec3(600,520,2000),new Diffuse_light(new ColorValue(10000,10000,10000)),0);
        //Plane_xz light=new Plane_xz(10,10,new Vec3(0,554,0),new Diffuse_light(new ColorValue(10,10,10)));
        //Plane_xy light2=new Plane_xy(50,50,new Vec3(224,274,-274),new Diffuse_light(new ColorValue(100,100,100)));
        //Plane_yz light2=new Plane_yz(50,50,new Vec3(274,274,0),true,new Diffuse_light(new ColorValue(10,10,10)));
        //Sphere sphere=new Sphere(new Vec3(83,90,83),90,new Lambertian(new ColorValue(0.73,0.73,0.73)));
        //Sphere sphere2=new Sphere(new Vec3(-5000,10000,10000),10,new Diffuse_light(new ColorValue(10,10,10)));
        //ArrayList<Primitive> listSampler=new ArrayList<Primitive>();
        //listSampler.add(lightBox);
        //listSampler.add(light);
        //listSampler.add(light2);
        //listSampler.add(sphere);
        //listSampler.add(sphere2);

        /*
        Primitive_pdf light_ptr= new Primitive_pdf(lights,rec.p);
        Mixture_pdf p=new Mixture_pdf(light_ptr,srec.pdf_ptr);

        Ray scattered=new Ray(rec.p,p.generate());
        double pdf_val= p.value(scattered.direction());
         */

        world.getListSampler().add(new Box(165,165,330,new Vec3(-83,0.1,-83), new Metal(new ColorValue(0.73,0.73,0.73)),15));
        world.getListSampler().add(new Plane_xz(150,150,new Vec3(0,554,0),new Diffuse_light(new ColorValue(10,10,10))));


        Ray scattered;
        double pdf_val;
        if(world.getListSampler().size()>=1){
            Hittable_list_pdf list_ptr= new Hittable_list_pdf(world.getListSampler(),rec.p);
            Mixture_pdf p= new Mixture_pdf(list_ptr,srec.pdf_ptr);
            scattered=new Ray(rec.p,p.generate());
            pdf_val= p.value(scattered.direction());
        }else{
            Cosine_pdf p=new Cosine_pdf(rec.normal);
            scattered=new Ray(rec.p,p.generate());
            pdf_val=p.value(scattered.direction());
        }


        return emitted.add(
                srec.attenuation).product(rec.material.scattering_pdf(r,rec,scattered))
                        .product(colorRay(scattered, new Hittable(world.list,world.nodeList), depth - 1, background))
                        .divide(pdf_val);
    }

    /**
     * Method to obtain the gamma correction value
     * @param value initial value
     * @param correctionValue value for correction
     * @return double with the gamma correction value
     */
    public static double gammaCorrection(double value, double correctionValue){
        double gammaCorrection = 1 / correctionValue;
        return Math.pow(value,gammaCorrection);
    }

    /**
     * Method to clamp the image to max and min values to avoid burned areas
     * @param x value
     * @param min min
     * @param max max
     * @return return the clamped value (if needed)
     */
    public static double clamp(double x, double min, double max){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    /**
     * Transform the colorValues into RGB values
     * @return a integer with the code of the RBG color
     */
    public int toRGB(){
        int R= (int) (256 * clamp(vR,0, 0.999));
        int G= (int) (256 * clamp(vG,0, 0.999));
        int B= (int) (256 * clamp(vB,0, 0.999));
        return new Color(R,G,B).getRGB();
    }

    //TESTING TEXTURES

    public ColorValue getColorValue(double u, double v, Vec3 p){
        return this;
    }

    //TESTING TEXTURES


    public double vR(){
        return vR;
    }

    public double vG(){
        return vG;
    }

    public double vB(){
        return vB;
    }

    public ColorValue add(ColorValue c){
        return new ColorValue(vR+c.vR, vG+c.vG, vB+c.vB);
    }

    public ColorValue add(double d){
        return new ColorValue(vR+d, vG+d, vB+d);
    }

    public ColorValue product(double d){
        return new ColorValue(vR*d, vG*d, vB*d);
    }

    public ColorValue product(ColorValue c){
        return new ColorValue(vR*c.vR, vG*c.vG, vB*c.vB);
    }

    public ColorValue divide(double d){
        return new ColorValue(vR / d, vG / d, vB / d);
    }

    //Return a random ColourValue
    public static ColorValue randomColorValue(){
        return new ColorValue(ThreadLocalRandom.current().nextDouble(),ThreadLocalRandom.current().nextDouble(),ThreadLocalRandom.current().nextDouble());
    }

    //Return a random Colour within a minimum and a maximum
    public static ColorValue randomColorValue(double min, double max){
        return new ColorValue (random_double(min,max),random_double(min,max),random_double(min,max));
    }

    @Override
    public String toString (){
        return String.format("ColorValue[%.5f, %.5f, %.5f]", vR, vG, vB);
    }

    public static ColorValue clone(ColorValue colorValue){
        return new ColorValue(colorValue.vR(), colorValue.vG(), colorValue.vB());
    }

    public void setvR(){
        this.vR=1;
    }
    public void setvG(){
        this.vG=1;
    }
    public void setvB(){
        this.vB=1;
    }
}

package maths;

import geometry.Hit_record;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
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

            double angle=-1;
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

        if (!rec.material.scatter(r, rec)) {
            return rec.material.emitted();
        }

        return rec.material.getAttenuation().product(colorRay(rec.material.getScattered(),
                new Hittable(world.list,world.nodeList), depth - 1, background));
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
}

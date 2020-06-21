package maths;

import geometry.Hit_record;

import java.awt.*;
import java.util.Random;

import static maths.Utils.INFINITY;
import static maths.Vec3.random_double;

public class ColorValue {

    //PROPERTIES
    double vR,vG,vB;

    //CONSTRUCTOR
    public ColorValue(double vR, double vG, double vB) {
        this.vR = vR;
        this.vG = vG;
        this.vB = vB;
    }

    public ColorValue() {
        this.vR = 0;
        this.vG = 0;
        this.vB = 0;
    }

    public ColorValue(double x){
        this.vR=x;
        this.vG=x;
        this.vB=x;
    }

    public ColorValue(double vR, double vG, double vB, double gammaValue) {
        this.vR = gammaCorrection(vR,gammaValue);
        this.vG = gammaCorrection(vG,gammaValue);
        this.vB = gammaCorrection(vB,gammaValue);
    }


    //METHODS

    //Method that recive a ray direction (from camera) and go through the array of elements in scene checking colisions
    //and obtaining the color
    public static ColorValue colorRay(Ray r, Hittable world, int depth){

        //Hit_record rec=new Hit_record();

        Hit_record rec=world.getTemp_rec();

        if(depth<=0)
            return new ColorValue(0,0,0);

        if (world.hit(r, 0.001, INFINITY, rec)){

            if(rec.material.scatter(r, rec)){

                return rec.material.getAttenuation().product(colorRay(rec.material.getScattered(), new Hittable(world.list), depth-1));

            }else{
                return new ColorValue(0,0,0);
            }
        }

        //GENERATE BACKGROUND COLOR
        Vec3 unit_direction=(r.direction().normalize());
        double t= 0.5*(unit_direction.y() + 1);
        return new ColorValue(1,1,1).product(1-t).add(new ColorValue(0.5, 0.7, 1.0).product(t));

    }

    public static double gammaCorrection(double value, double correctionValue){
        double gammaCorrection = 1 / correctionValue;
        return Math.pow(value,gammaCorrection);
    }

    public static double clamp(double x, double min, double max){
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public int toRGB(){
        int R= (int) (256 * clamp(vR,0, 0.999));
        int G= (int) (256 * clamp(vG,0, 0.999));
        int B= (int) (256 * clamp(vB,0, 0.999));
        return new Color(R,G,B).getRGB();
    }


    public double get_vR(){
        return vR;
    }

    public double get_vG(){
        return vG;
    }

    public double get_vB(){
        return vB;
    }

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
        Random r = new Random();
        return new ColorValue(Math.random(),Math.random(),Math.random());
    }

    //Return a random Colour within a minimum and a maximum
    public static ColorValue randomColorValue(double min, double max){
        return new ColorValue (random_double(min,max),random_double(min,max),random_double(min,max));
    }

    @Override
    public String toString (){
        return String.format("ColorValue[%.5f, %.5f, %.5f]", vR, vG, vB);
    }




}

package math;

import java.util.Random;

import static math.Vec3.random_double;

public class ColorValue {

    //PROPERTIES
    private double vR,vG,vB;


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


    //METHODS
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

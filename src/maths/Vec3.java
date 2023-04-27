package maths;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

/**
 * A class to create & operate with points/vectors
 */
public class Vec3 implements Serializable {

    //PROPERTIES
    private double x,y,z;


    //CONSTRUCTORS
    public Vec3(){
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public Vec3(double x){
        this.x=x;
        this.y=x;
        this.z=x;
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    //METHODS 
    public Vec3 add(Vec3 v){
        return new Vec3(x+v.x, y+v.y, z+v.z);
    }

    public Vec3 add(double d){
        return new Vec3(x+d, y+d, z+d);
    }

    public Vec3 sub(Vec3 v){
        return new Vec3(x-v.x, y-v.y, z-v.z);
    }

    public Vec3 sub(double d){
        return new Vec3(x-d, y-d, z-d);
    }

    public Vec3 product(double d){
        return new Vec3(x*d, y*d, z*d);
    }

    public Vec3 product(Vec3 v){
        return new Vec3(x*v.x, y*v.y, z*v.z);
    }

    public Vec3 divide(Vec3 v)
    {
        return new Vec3(x / v.x, y / v.y, z / v.z);
    }

    public Vec3 divide(double v)
    {
        return new Vec3(x / v, y / v, z / v);
    }


    public double dotProduct(Vec3 v){
        return  x*v.x + y*v.y + z*v.z;
    }

    public static double dotProduct(Vec3 v1, Vec3 v2){
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    //Return perpendicular vector to the plane based in the two given vectors
    public Vec3 cross(Vec3 v){
        return new Vec3(
                y*v.z - z*v.y,
                z*v.x - x*v.z,
                x*v.y - y*v.x
        );
    }

    public double squared_length(){
        return x*x + y*y + z*z;
    }
    public double length(){
        return sqrt(squared_length());
    }


    public double getValue(int i){
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;
            default:
                return 0;
        }
    }

    public void setValue(int i, double value){
        switch (i) {
            case 0:
                this.x=value;
                break;
            case 1:
                this.y=value;
                break;
            case 2:
                this.z=value;
                break;
        }
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    //Every coordinate divided by the vector length
    //Also called unit_vector
    public Vec3 normalize(){
        double len=length();
        if(len>0){
            double invLen=1/len;
            x*=invLen;
            y*=invLen;
            z*=invLen;
        }
        return this;
    }

    public static double random_double(double min, double max){
        return min + (max - min) * ThreadLocalRandom.current().nextDouble();
    }

    public static int random_int(int min, int max){
        return ThreadLocalRandom.current().nextInt(min,max);
    }

    //Return a random vector
    public static Vec3 randomVec3(){
        Random r = new Random();
        return new Vec3(ThreadLocalRandom.current().nextDouble(),ThreadLocalRandom.current().nextDouble(),ThreadLocalRandom.current().nextDouble());
    }

    //Return a random vector within a minimum and a maximum
    public static Vec3 random(double min, double max){
        return new Vec3 (random_double(min,max),random_double(min,max),random_double(min,max));
    }

    public static Vec3 random_in_unit_sphere(){
        while(true){
            Vec3 p=random(-1,1);
            if(p.squared_length()>=1) continue;
            return p;
        }
    }

    //Not used
    public static Vec3 random_in_hemisphere(Vec3 normal){
        Vec3 in_unit_sphere =random_in_unit_sphere();
        if(in_unit_sphere.dotProduct(normal)>0) //In the same hemisphere as the normal
            return in_unit_sphere;
        else
            return in_unit_sphere.product(-1);
    }

    //It can be used in order to vary the result of the lambertian distribution
    public static Vec3 random_unit_vector(){
        double a = random_double(0, 2 * Utils.PI);
        double z = random_double(-1,1);
        double r = sqrt(1-z*z);
        return new Vec3 (r*cos(a),r*sin(a),z);
    }

    public static Vec3 random_in_unit_disk(){
        while(true){
            Vec3 p = new Vec3(random_double(-1,1), random_double(-1,1), 0);
            if (p.squared_length() >= 1) continue;
            return p;
        }
    }

    public static Vec3 random_cosine_direction(){
        double r1= ThreadLocalRandom.current().nextDouble();
        double r2= ThreadLocalRandom.current().nextDouble();
        double z=sqrt(1-r2);

        double phi=2*Utils.PI*r1;
        double x=cos(phi)*sqrt(r2);
        double y=sin(phi)*sqrt(r2);

        return new Vec3(x,y,z);
    }

    public static Vec3 random_to_Sphere(double radius, double distance_squared){
        double r1=random_double(0,1);
        double r2=random_double(0,1);
        double z=1+r2*(Math.sqrt(1-radius*radius/distance_squared)-1);

        double phi= 2*Utils.PI*Utils.PI;
        double x= cos(phi)*sqrt(1-z*z);
        double y= sin(phi)*sqrt(1-z*z);

        return new Vec3(x,y,z);

    }

    public boolean near_zero(){
        // Return true if the vector is close to zero in all dimensions.
        final double s = 1e-8;
        return (Math.abs(exp(0)) < s) && (Math.abs(exp(1))< s) && (Math.abs(exp(2)) < s);
    }

    public static Vec3 clone(Vec3 vec3){
        return(new Vec3(vec3.x(),vec3.y(),vec3.z()));
    }

    @Override
    public String toString (){
        return String.format("Vec3[%.5f, %.5f, %.5f]", x, y, z);
    }




}

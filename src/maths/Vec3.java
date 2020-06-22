package maths;

import java.io.Serializable;
import java.util.Random;

import static java.lang.Math.*;

/**
 *
 * @author RubenM
 */

//A class to create & operate with points/vectors & colours
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
    
    public double norm(){
        return x*x + y*y + z*z;
    }
    
    public double length(){        
        return sqrt(norm());
    }
    
    public double squared_length(){
        return length()*length();
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

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }
    
    
    
    //Every coordinate divided by the vector lenght
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
        return min + (max - min) * Math.random();
    }

    //Return a random vector
    public static Vec3 random(){
        Random r = new Random();
        return new Vec3(Math.random(),Math.random(),Math.random());
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


    @Override
    public String toString (){
        return String.format("Vec3[%.5f, %.5f, %.5f]", x, y, z);
    }

    
    
    
}

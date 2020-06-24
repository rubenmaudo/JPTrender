/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class Ray {

    //RAY FIELDS
    private final Vec3 pointOrigin;
    private final Vec3 vectorDirection;
    
    //CONSTRUCTOR
    public Ray(Vec3 pO, Vec3 vD){
        pointOrigin =pO;
        vectorDirection =vD;
    }
    
    //METHODS
    /**
     *
     * @return origin of ray
     */
    public Vec3 origin(){
        return pointOrigin;
    }

    /**
     *
     * @return Direction of Ray
     */
    public Vec3 direction(){
        return vectorDirection;
    }

    /**
     *
     * @param t ,Parameter to check position
     * @return the point at the parameter.
     */
    public Vec3 point_at_parameter(double t){
        return pointOrigin.add(vectorDirection.product(t));
    }
}

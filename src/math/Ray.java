/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author RubenM
 */
public class Ray {
    
    private Vec3f vA, vB;
    
    //CONSTRUCTOR
    public Ray(Vec3f va, Vec3f vb){
        vA=va;
        vB=vb;        
    }
    
    //METHODS
    public Vec3f origin(){
        return vA;
    }
    
    public Vec3f direction(){
        return vB;
    }
    
    public Vec3f point_at_parameter(float t){
        return vA.add(vB.product(t));
    }
}

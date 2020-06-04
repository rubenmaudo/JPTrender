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
    
    private Vec3 vA, vB;
    
    //CONSTRUCTOR
    public Ray(Vec3 va, Vec3 vb){
        vA=va;
        vB=vb;        
    }
    
    //METHODS
    public Vec3 origin(){
        return vA;
    }
    
    public Vec3 direction(){
        return vB;
    }
    
    public Vec3 point_at_parameter(double t){
        return vA.add(vB.product(t));
    }
}

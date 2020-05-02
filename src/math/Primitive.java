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
public abstract class Primitive {
    float t;
    Vec3 p;
    Vec3 normal;
    
    public abstract boolean hit(Ray r, float t_min, float t_max);
    

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }

    public Vec3 getP() {
        return p;
    }

    public void setP(Vec3 p) {
        this.p = p;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public void setNormal(Vec3 normal) {
        this.normal = normal;
    }
    
    
    
}
package math;

/**
 *
 * @author RubenM
 */

//A class to create & operate with points/vectors & colours
public class Vec3f {
    
    //PROPERTIES
    private float x,y,z;
    
    
    //CONSTRUCTOR
    public Vec3f(){
        this.x=0.0f;
        this.y=0.0f;
        this.z=0.0f;
    }
    
    
    public Vec3f(float x){
        this.x=x;
        this.y=x;
        this.z=x;
    }    
    
    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    //METHODS 
    public Vec3f add(Vec3f v){
        return new Vec3f(x+v.x, y+v.y, z+v.z);
    }
    
    public Vec3f add(float d){
        return new Vec3f(x+d, y+d, z+d);
    }
    
    public Vec3f sub(Vec3f v){
        return new Vec3f(x-v.x, y-v.y, z-v.z);
    }
    
    public Vec3f sub(float d){
        return new Vec3f(x-d, y-d, z-d);
    }
    
    public Vec3f product(float d){
        return new Vec3f(x*d, y*d, z*d);
    }
    
    public Vec3f dotProduct(Vec3f v){
        return  new Vec3f(x*v.x, y*v.y, z*v.z);
    }
    
    //Return perpendicular vector to the plane based in the two given vectors
    public Vec3f cross(Vec3f v){
        return new Vec3f(
                y*v.z - z*v.y,
                z*v.x - x*v.z,
                x*v.y - y*v.x
        );
    }
    
    public float norm(){
        return x*x + y*y + z*z;
    }
    
    public float length(){        
        return (float) Math.sqrt(norm());
    }
    
    public float getValue(int i){
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;            
            default:
                return 0.0f;
        }
    }
    
    //Every coordinate divided by the vector lenght
    public Vec3f normalize(){
        float len=length();
        if(len>0){
            float invLen=1/len;
            x*=invLen;
            y*=invLen;
            z*=invLen;
        }
        return this;
    }    
    
        
    @Override
    public String toString (){
        return String.format("Vec3f[%.5f, %.5f, %.5f]", x, y, z);
    }
    
    
}

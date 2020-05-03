package math;

/**
 *
 * @author RubenM
 */

//A class to create & operate with points/vectors & colours
public class Vec3 {
    
    //PROPERTIES
    private float x,y,z;
    
    
    //CONSTRUCTOR
    public Vec3(){
        this.x=0.0f;
        this.y=0.0f;
        this.z=0.0f;
    }
    
    
    public Vec3(float x){
        this.x=x;
        this.y=x;
        this.z=x;
    }    
    
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    //METHODS 
    public Vec3 add(Vec3 v){
        return new Vec3(x+v.x, y+v.y, z+v.z);
    }
    
    public Vec3 add(float d){
        return new Vec3(x+d, y+d, z+d);
    }
    
    public Vec3 sub(Vec3 v){
        return new Vec3(x-v.x, y-v.y, z-v.z);
    }
    
    public Vec3 sub(float d){
        return new Vec3(x-d, y-d, z-d);
    }
    
    public Vec3 product(float d){
        return new Vec3(x*d, y*d, z*d);
    }
    
    public Vec3 product(Vec3 v){
        return new Vec3(x*v.x, y*v.y, z*v.z);
    }
    
    public Vec3 divide(Vec3 v)
	{
		return new Vec3(x / v.x, y / v.y, z / v.z);
	}

	public Vec3 divide(float f)
	{
		return new Vec3(x / f, y / f, z / f);
	}
    
    
    public float dotProduct(Vec3 v){
        return  x*v.x + y*v.y + z*v.z;
    }
    
    public static float dotProduct(Vec3 v1, Vec3 v2){
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
    
    public float norm(){
        return x*x + y*y + z*z;
    }
    
    public float length(){        
        return (float) Math.sqrt(norm());
    }
    
    public float squared_length(){
        return length()*length();
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

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }
    
    
    
    //Every coordinate divided by the vector lenght
    public Vec3 normalize(){
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

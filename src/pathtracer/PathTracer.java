/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import math.Intersection;
import math.Primitive;
import math.Ray;
import math.Sphere;
import math.Vec3;

/**
 *
 * @author RubenM
 */
public class PathTracer {

    /*
    private static float hit_sphere(Vec3 center, float radius, Ray r){
        Vec3 oc= r.origin().sub(center);
        float a = dotProduct(r.direction(), r.direction());
        float b = 2.0f * dotProduct(oc, r.direction());
        float c = dotProduct(oc,oc) - radius*radius;
        float discriminant = b*b -4*a*c;
        if (discriminant < 0){
            return -1.0f;
        }else {
            return (float) ((-b-sqrt(discriminant)) / (2.0*a));
        }
    }
    */
    
    private static Vec3 color(Ray r, ArrayList<Primitive> list){
        Intersection inters = new Intersection();
            
        if (inters.hit(list, r, 0.0f, Float.MAX_VALUE)){
            return new Vec3(inters.getPrim().getNormal().x()+1,
                    inters.getPrim().getNormal().y()+1,
                    inters.getPrim().getNormal().z()+1
            ).product(0.5f);            
        }else{
            Vec3 unit_direction=(r.direction().normalize());
            float t= 0.5f*(unit_direction.y() + 1.0f);    
            return new Vec3(1.0f,1.0f,1.0f).product(1.0f-t)
                .add(new Vec3(0.5f, 0.7f, 1.0f).product(t));
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int pwidth = 800;
        int pheight = 400;
        int ns = 100;
        
        BufferedImage theImage = new BufferedImage(pwidth, pheight, 
                BufferedImage.TYPE_INT_RGB);
        
        Vec3 lower_left_corner=new Vec3(-2.0f,-1.0f,-1.0f);
        Vec3 horizontal=new Vec3(4.0f,0.0f,0.0f);
        Vec3 vertical=new Vec3(0.0f,2.0f,0.0f);
        Vec3 origin=new Vec3(0.0f,0.0f,0.0f);
        
        ArrayList<Primitive> primList= new ArrayList<>();
        primList.add(new Sphere(new Vec3(0,0,-1),0.5f));
        primList.add(new Sphere(new Vec3(0,-100.5f,-1),100f));
        
        
        Camera cam = new Camera();
        
        for (int j = pheight-1; j>=0; j--) {
            for (int i = 0; i < pwidth; i++) {
                
                Vec3 col=new Vec3(0,0,0);
                for(int s=0; s<ns; s++){
                    float u = (float)(i + Math.random())  / (float)pwidth;
                    float v = ((pheight-(float)j)+ (float)Math.random()) / (float)pheight;
                    Ray r =cam.get_ray(u, v);
                    col = color(r,primList).add(col);                  
                }
                col = col.divide(ns);
                                
                int ir= (int)(255.99*col.getValue(0));                
                int ig= (int)(255.99*col.getValue(1));
                int ib= (int)(255.99*col.getValue(2));                
                
                Color color=new Color(ir,ig,ib);   
                theImage.setRGB(i,j, color.getRGB());                
            }
        }

        File outputfile = new File("render.jpg");
        try {
            ImageIO.write(theImage, "jpg", outputfile);
        } catch (IOException e1) {

        }
    }
    
}

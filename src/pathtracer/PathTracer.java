/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import materials.Material;
import materials.lambertian;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import materials.metal;
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
    
    
    
    private static Vec3 color(Ray r, ArrayList<Primitive> list, int depth){
        Intersection inters = new Intersection();
        if (inters.hit(r, 0.001f, Float.MAX_VALUE, list)){
            Primitive temp= inters.getPrim();

            if(depth<50 && temp.material.scatter(r, inters)){
                return temp.material.attenuation.product(color(temp.material.scattered, list, depth+1));
            }else {
                return new Vec3(0,0,0);
            }
            
        }else{
                //BACKGROUND COLOR
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
        
        int pwidth = 1000;
        int pheight = 500;
        int ns = 200;
        
        BufferedImage theImage = new BufferedImage(pwidth, pheight, 
                BufferedImage.TYPE_INT_RGB);
                
        ArrayList<Primitive> primList= new ArrayList<>();
        primList.add(new Sphere(new Vec3(0,0,-1),0.5f,new lambertian(new Vec3(0.8f,0.3f,0.3f))));
        primList.add(new Sphere(new Vec3(0,-100.5f,-1),100f,new lambertian(new Vec3(0.8f,0.8f,0))));
        primList.add(new Sphere(new Vec3(1,0,-1),0.5f,new metal(new Vec3(0.8f,0.6f,0.2f))));
        primList.add(new Sphere(new Vec3(-1,0,-1),0.5f,new metal(new Vec3(0.8f,0.8f,0.8f))));
                
        Camera cam = new Camera();
        
        for (int j = pheight-1; j>=0; j--) {
            for (int i = 0; i < pwidth; i++) {
                
                Vec3 col=new Vec3(0,0,0);
                for(int s=0; s<ns; s++){
                    float u = (float)(i + Math.random())  / (float)pwidth;
                    float v = ((pheight-(float)j)+ (float)Math.random()) / (float)pheight;
                    Ray r =cam.get_ray(u, v);
                    col = color(r,primList,0).add(col);
                }
                col = col.divide(ns);
                col = new Vec3((float)sqrt(col.x()), (float)sqrt(col.y()), (float)sqrt(col.z()));
                                
                int ir= (int)(255.99*col.getValue(0));                
                int ig= (int)(255.99*col.getValue(1));
                int ib= (int)(255.99*col.getValue(2));                
                
                Color color=new Color(ir,ig,ib);   
                theImage.setRGB(i,j, color.getRGB());                
            }
        }

        File outputfile = new File("renders/render.jpg");
        try {
            ImageIO.write(theImage, "jpg", outputfile);
        } catch (IOException e1) {

        }
    }
    
}

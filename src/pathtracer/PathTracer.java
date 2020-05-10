/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import materials.lambertian;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javax.swing.JFrame;
import materials.metal;
import math.Intersection;
import math.Primitive;
import math.Ray;
import math.Sphere;
import math.Vec3;
import windowRender.MainFrame;

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
                return new Vec3(1f,1f,1f).product(1.0f-t)
                    .add(new Vec3(0.5f, 0.7f, 1.0f).product(t));
        }
    }
       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Flag to control the render time
        long startTime = System.currentTimeMillis();
        
        //Render specs
        int pwidth = 1000;
        int pheight = 500;
        boolean progressive = true; //Si esta activo de momento no guarda
        int ns = 800; //Number of samples
        int tempNs=0;
        
        BufferedImage theImage = new BufferedImage(pwidth, pheight, 
                BufferedImage.TYPE_INT_RGB);
        
        MainFrame ventana=new MainFrame(theImage);
        
                
        Vec3[][] imagePixels=new Vec3[pwidth][pheight];
        int[][] imagePixelsNs=new int[pwidth][pheight];
        Vec3[][] imagePixelsProcesed=new Vec3[pwidth][pheight];
            
        ArrayList<Primitive> primList= new ArrayList<>();
        primList.add(new Sphere(new Vec3(0,0,-1),0.5f,new lambertian(new Vec3(0.8f,0.3f,0.3f))));
        primList.add(new Sphere(new Vec3(0.45f,-0.4f,-0.7f),0.1f,new metal(new Vec3(0.8f,0.8f,0.8f),0f)));
        primList.add(new Sphere(new Vec3(0,-100.5f,-1),100f,new lambertian(new Vec3(0.8f,0.8f,0))));
        primList.add(new Sphere(new Vec3(1,0,-1),0.5f,new metal(new Vec3(0.8f,0.6f,0.2f),0.9f)));
        primList.add(new Sphere(new Vec3(-1,0,-1),0.5f,new metal(new Vec3(0.8f,0.8f,0.8f),0.2f)));
                
        Camera cam = new Camera();
        
        
        while(tempNs<ns || progressive){
            
            for (int j = pheight-1; j>=0; j--) {
                for (int i = 0; i < pwidth; i++) {

                    Vec3 col=new Vec3(0,0,0);
                    
                    float u = (float)(i + Math.random())  / (float)pwidth;
                    float v = ((pheight-(float)j)+ (float)Math.random()) / (float)pheight;
                    Ray r =cam.get_ray(u, v);
                    col = color(r,primList,0);
                    
                    if(imagePixels[i][j]==null){
                        imagePixels[i][j]=new Vec3(0,0,0);
                        imagePixelsNs[i][j]=0;
                    }
                    
                    imagePixels[i][j]=imagePixels[i][j].add(col);
                    imagePixelsNs[i][j]=imagePixelsNs[i][j]+1;
                    imagePixelsProcesed[i][j]=imagePixels[i][j].divide(imagePixelsNs[i][j]);
                    col= new Vec3((float)sqrt(imagePixelsProcesed[i][j].x()), 
                            (float)sqrt(imagePixelsProcesed[i][j].y()), 
                            (float)sqrt(imagePixelsProcesed[i][j].z()));
                    
                    int ir= (int)(255.99*col.getValue(0));                
                    int ig= (int)(255.99*col.getValue(1));
                    int ib= (int)(255.99*col.getValue(2));                

                    Color color=new Color(ir,ig,ib);   
                    theImage.setRGB(i,j, color.getRGB());
                                      
                }
            }
            //Control time
            long endTime = System.currentTimeMillis();
            long milliseconds = endTime - startTime;
            long minutes = (milliseconds / 1000) / 60;
            long seconds = (milliseconds / 1000) % 60;
            String text="Render Pass: " + tempNs + " / Render time: " + minutes + "m " + seconds + " s";
            
            //Print text on image
            Graphics graphics = theImage.getGraphics();
            graphics.setColor(Color.DARK_GRAY);
            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics.drawString(text, 3, 10);
            
            
            ventana.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.
                        
            tempNs++;
        }
    }    
}

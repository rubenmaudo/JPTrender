/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import elements.Scene;
import materials.Dielectric;
import materials.Lambertian;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.sqrt;
import static math.Utils.*;

import java.util.ArrayList;

import materials.Metal;
import math.*;
import windowRender.MainFrame;

/**
 *
 * @author RubenM
 */
public class PathTracer {
    
    private static Vec3 color(Ray r, ArrayList<Primitive> list, int depth){
        Intersection inters = new Intersection();

        if(depth<=0)
            return new Vec3(0,0,0);

        if (inters.hit(r, 0.001, INFINITY, list)){
            Primitive temp= inters.getPrim();

            if(temp.material.scatter(r, inters))
                return temp.material.attenuation.product(color(temp.material.scattered, list, depth-1));
                return new Vec3(0,0,0);
        }

        //BACKGROUND COLOR
        Vec3 unit_direction=(r.direction().normalize());
        double t= 0.5*(unit_direction.y() + 1);
        return new Vec3(1,1,1).product(1-t).add(new Vec3(0.5, 0.7, 1.0).product(t));
    }
       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Flag to control the render time
        long startTime = System.currentTimeMillis();

        //Render specs
        final double aspect_ratio = 16.0 / 9.0 ;
        int image_width = 1000;
        int image_height = (int) (image_width/aspect_ratio);

        boolean progressive = true; //Si esta activo de momento no guarda
        int ns = 15; //Number of samples
        int tempNs=1;

        int depth =50;//Maximum number of bounces we will allow
        
        BufferedImage theImage = new BufferedImage(image_width, image_height,
                BufferedImage.TYPE_INT_RGB);
        
        MainFrame ventana=new MainFrame(theImage);
        
                
        Vec3[][] imagePixels=new Vec3[image_width][image_height];
        int[][] imagePixelsNs=new int[image_width][image_height];
        Vec3[][] imagePixelsProcesed=new Vec3[image_width][image_height];

        //Create scene
        ArrayList<Primitive> primList= Scene.generateScene(5);

        //Create camera
        Camera cam = new Camera();
        
        
        while(tempNs<=ns || progressive){
            
            for (int j = 0; j<image_height; j++) {
                for (int i = 0; i < image_width; i++) {

                    Vec3 col;
                    
                    double u = (i + Math.random())  /  image_width;
                    double v = ((image_height-j) + Math.random()) / image_height;
                    Ray r =cam.get_ray(u, v);
                    col = color(r,primList,depth);
                    
                    if(imagePixels[i][j]==null){
                        imagePixels[i][j]=new Vec3(0,0,0);
                        imagePixelsNs[i][j]=0;
                    }
                    
                    imagePixels[i][j]=imagePixels[i][j].add(col);
                    imagePixelsNs[i][j]=imagePixelsNs[i][j]+1;
                    imagePixelsProcesed[i][j]=imagePixels[i][j].divide(imagePixelsNs[i][j]);

                    col= new Vec3(sqrt(imagePixelsProcesed[i][j].x()),
                            sqrt(imagePixelsProcesed[i][j].y()),
                            sqrt(imagePixelsProcesed[i][j].z()));
                    
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

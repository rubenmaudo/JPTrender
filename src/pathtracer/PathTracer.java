/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import math.Ray;
import math.Vec3f;

/**
 *
 * @author RubenM
 */
public class PathTracer {

    
    private Vec3f color(Ray r){
        Vec3f unit_direction=(r.direction().normalize());
        float t= 0.5f*(unit_direction.getValue(1) + 1.0f);    
        return new Vec3f(1.0f,1.0f,1.0f).product(1.0f-t)
                .add(new Vec3f(0.5f, 0.7f, 1.0f).product(t));
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int pwidth = 200;
        int pheight = 100;
        
        String test;
        
        BufferedImage theImage = new BufferedImage(pwidth, pheight, 
                BufferedImage.TYPE_INT_RGB);
        for (int j = pheight-1; j>=0; j--) {
            for (int i = 0; i < pwidth; i++) {
                
                //Vector to storage the RGB
                Vec3f v=new Vec3f((float)i / (float)pwidth,
                        (pheight-(float)j) / (float)pheight, 0.2f);
                /*float r = (float)i / (float)pwidth;
                float g = (pheight-(float)j) / (float)pheight ;
                float b = 0.2f;
                int ir= (int)(255.99*r);                
                int ig= (int)(255.99*g);
                int ib= (int)(255.99*b);  
                */
                int ir= (int)(255.99*v.getValue(0));                
                int ig= (int)(255.99*v.getValue(1));
                int ib= (int)(255.99*v.getValue(2)); 
                
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

package materials;

import maths.ColorValue;
import maths.Vec3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture_map extends Texture {
    String texture_path;
    BufferedImage image;
    Boolean image_ok=true;

    //CONSTRUCTOR
    public Texture_map(String texture_path){
        this.texture_path=texture_path;

        File img = new File(texture_path);
        this.image = null;
        try {
            this.image = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
            image_ok=false;
        }
    }


    /*
    public ColorValue getColorValue(double u, double v, Vec3 p){

        int pixelX= (int) (u*(image.getWidth()-1));
        int pixelY= image.getHeight()-(int) (v*(image.getHeight()-1));
        int clr = image.getRGB(pixelX, pixelY);

        double red = (((clr & 0x00ff0000) >> 16)/(double)255);
        double green = ((clr & 0x0000ff00) >> 8)/(double)255;
        double blue = (clr & 0x000000ff)/(double)255;

        return new ColorValue(red, green, blue);
    }

     */

    @Override
    public ColorValue getColourValue(double u, double v, Vec3 p) {

        if(image_ok){
            int pixelX, pixelY;

            int i= (int) (u * image.getWidth());
            int j= (int) (v * image.getHeight());

            if(i>= image.getWidth()){ i=i-1;}
            if(j>= image.getHeight()){ j=j-1;}

            pixelX=i;
            pixelY= (image.getHeight()-1)-j;

            int clr = image.getRGB(pixelX, pixelY);

            double red = (((clr & 0x00ff0000) >> 16)/(double)255);
            double green = ((clr & 0x0000ff00) >> 8)/(double)255;
            double blue = (clr & 0x000000ff)/(double)255;

            return new ColorValue(red, green, blue);

        }else{
            return new ColorValue(0,0,0);
        }

    }
}

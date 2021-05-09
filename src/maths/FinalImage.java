package maths;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/
public class FinalImage extends BufferedImage {
    long startTime;
    int renderPass;

    public FinalImage(int width, int height, int imageType) {
        super(width, height, imageType);

         startTime= System.currentTimeMillis();
    }

    public void setTimeOnImage(){
        long endTime = System.currentTimeMillis();
        long milliseconds = endTime - startTime;
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String text = "Render Pass: " + (renderPass) + " || Render time: " + minutes + "m " + seconds + " s";

        //Print text on image
        Graphics graphics = this.getGraphics();
        graphics.setColor(Color.DARK_GRAY);
        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
        graphics.drawString(text, 3, 10);
    }

    public void setRenderPass(int renderPass){
        this.renderPass = renderPass;
    }





}

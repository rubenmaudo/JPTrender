/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import elements.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;

import geometry.Primitive;
import maths.*;
import windowRender.MainFrame;

import static java.lang.Thread.sleep;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PathTracer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Flag to control the render time
        long startTime = System.currentTimeMillis();

        //Render specs
        final double aspect_ratio = 16.0 / 9;
        int image_width = 500;
        int image_height = (int) (image_width / aspect_ratio);

        boolean progressive = true; //Si esta activo de momento no guarda
        int ns = 16; //Number of samples
        int tempNs = 1;

        int depth = 50;//Maximum number of bounces we will allow

        double gammaValue = 2;

        BufferedImage theImage = new BufferedImage(image_width, image_height,
                BufferedImage.TYPE_INT_RGB);

        ColorValue[][] imagePixels = new ColorValue[image_width][image_height];

        MainFrame window = new MainFrame(theImage);


        //Create scene
        ArrayList<Primitive> primList = Scene.generateScene(8);
        //ArrayList<Primitive> primList = Scene.loadScene();

        Camera cam=Camera.generateCamera(aspect_ratio,6);


        //Generate a list of pixels
        ArrayList<int[]> pixelList = new ArrayList<>();
        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {
                int[] pixelLocation = {i, j, 0};//Last value is the reference for the Number of pass
                pixelList.add(pixelLocation);
            }
        }
        //Shuffle the values of the pixels and distribute them in different groups
        //The amount of groups will match with the amount of rows in the image
        Collections.shuffle(pixelList);


        //Create an ArrayList that contain a list of ArrayList with pixels
        ArrayList<ArrayList<int[]>> listOfPixelGroups = new ArrayList<>();
        for (int i = 0; i < image_height; i++) {
            listOfPixelGroups.add(new ArrayList<>());
        }

        //Distribute the pixel position along the different group of pixel positions
        int count = 0;
        for (int[] pixelLocation : pixelList) {
            listOfPixelGroups.get(count).add(pixelLocation);
            if (count + 1 < image_height) {
                count++;
            } else {
                count = 0;
            }
        }

        while (tempNs <= ns || progressive) {

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            int ID = 0;
            for (ArrayList<int[]> shufflePixelGroup : listOfPixelGroups) {
                ID++;

                executorService.execute(new PTcalcs_threads_runnable(primList, cam, depth, theImage,
                        shufflePixelGroup, imagePixels, gammaValue, ID, window));

            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(1000, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("------------------------------------IT HAS BEEN COMPLETED THE PASS NUMBER "
                    + tempNs + "------------------------------------");

            //Control time
            long endTime = System.currentTimeMillis();
            long milliseconds = endTime - startTime;
            long minutes = (milliseconds / 1000) / 60;
            long seconds = (milliseconds / 1000) % 60;
            String text = "Render Pass: " + (tempNs) + " / Render time: " + minutes + "m " + seconds + " s";

            //Print text on image
            Graphics graphics = theImage.getGraphics();
            graphics.setColor(Color.DARK_GRAY);
            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics.drawString(text, 3, 10);
            window.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.

            tempNs++;


    }




            window.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.
    }
}


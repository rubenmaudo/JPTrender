/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import elements.Camera;
import elements.Scene;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import geometry.Primitive;
import geometry.Sphere;
import maths.*;
import windowRender.MainFrame;

import static java.lang.Thread.sleep;

/**
 *
 * @author RubenM
 */
public class PathTracer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Flag to control the render time
        long startTime = System.currentTimeMillis();

        //Render specs
        final double aspect_ratio = 16.0 / 10.6666666667;
        int image_width = 1000;
        int image_height = (int) (image_width / aspect_ratio);

        boolean progressive = false; //Si esta activo de momento no guarda
        int ns = 16; //Number of samples
        int tempNs = 1;

        int depth = 50;//Maximum number of bounces we will allow

        double gammaValue = 2;

        BufferedImage theImage = new BufferedImage(image_width, image_height,
                BufferedImage.TYPE_INT_RGB);

        ColorValue[][] imagePixels = new ColorValue[image_width][image_height];

        MainFrame ventana = new MainFrame(theImage);




        //NOT USED ANYMORE
        //int[][] imagePixelsNs = new int[image_width][image_height];
        //ColorValue[][] imagePixelsProcesed = new ColorValue[image_width][image_height];


        //Create scene
        //ArrayList<Primitive> primList = Scene.generateScene(8);
        ArrayList<Primitive> primList = Scene.loadScene();

        //Create camera
        //Scene 8
        Vec3 lookfrom = new Vec3(13, 2, 3);
        Vec3 lookat = new Vec3(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        double dist_to_focus = 10; //lookfrom.sub(lookat).length(); This would be the change to auto focus to the point you are looking to
        double aperture = 0.1;

        Camera cam = new Camera(lookfrom, lookat, vup, 20
                , aspect_ratio, aperture, dist_to_focus);

        /*
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

         */



        //Generate a list of pixels
        List<int[]> pixelList = new ArrayList<>();
        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {
                int[] pixelLocation = {i, j, 0};//Last value is the reference for the Number of pass
                pixelList.add(pixelLocation);
            }
        }
        //Shuffle the values of the pixels and distribute them in different groups
        //The amount of groups will match with the amount of rows in the image
        //Collections.shuffle(pixelList);




        while(tempNs<=ns || progressive) {


            ImageProcess_Recursive imageProcess = new ImageProcess_Recursive(primList,
                    cam,
                    depth,
                    theImage,
                    pixelList,
                    imagePixels,
                    gammaValue);

            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(imageProcess);

            System.out.println("------------------------------------SE HA COMPLETADO EL PASE " + tempNs + "------------------------------------");

            ventana.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.

            tempNs++;
        }


            /*
            ForkJoinPool pool = new ForkJoinPool();
            //ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            int counting=0;
            for (ArrayList<int[]> shufflePixelGroup :  listOfPixelGroups){
                counting++;
                System.out.println("Se ha lanzado la tarea con ID" + counting + " con " + tempNs + " pases");


                //executorService.execute(new ImageProcess_threads_runnable(primList,cam,depth,theImage,
                //              shufflePixelGroup,imagePixels,gammaValue,counting));


                pool.execute(new ImageProcess_threads(primList,cam,depth,theImage,
                        shufflePixelGroup,imagePixels,gammaValue,counting));
            }
            //executorService.shutdown();
            ForkJoinTask.invokeAll();

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("------------------------------------SE HA COMPLETADO EL PASE " + tempNs + "------------------------------------");

            ventana.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.

            tempNs++;
        }

             */






        /*
        //Processing the scene
        while (tempNs <= ns || progressive) {

            for (int j = 0; j < image_height; j++) {
                for (int i = 0; i < image_width; i++) {

                    ColorValue col;

                    double u = (i + Math.random()) / image_width;
                    double v = ((image_height - j) + Math.random()) / image_height;
                    Ray r = cam.get_ray(u, v);
                    col = ColorValue.colorRay(r, new Hittable(primList), depth);

                    if (imagePixels[i][j] == null) {
                        imagePixels[i][j] = new ColorValue(0, 0, 0);
                        imagePixelsNs[i][j] = 0;
                    }

                    imagePixels[i][j] = imagePixels[i][j].add(col);
                    imagePixelsNs[i][j] = imagePixelsNs[i][j] + 1;
                    imagePixelsProcesed[i][j] = imagePixels[i][j].divide(imagePixelsNs[i][j]);

                    //Gamma correction
                    col = new ColorValue(imagePixelsProcesed[i][j].vR(),
                            imagePixelsProcesed[i][j].vG(),
                            imagePixelsProcesed[i][j].vB(),
                            gammaValue);

                    theImage.setRGB(i, j, col.toRGB());

                }
            }


            //Control time
            long endTime = System.currentTimeMillis();
            long milliseconds = endTime - startTime;
            long minutes = (milliseconds / 1000) / 60;
            long seconds = (milliseconds / 1000) % 60;
            String text = "Render Pass: " + tempNs + " / Render time: " + minutes + "m " + seconds + " s";

            //Print text on image
            Graphics graphics = theImage.getGraphics();
            graphics.setColor(Color.DARK_GRAY);
            graphics.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics.drawString(text, 3, 10);


            ventana.renderPanel.repaint();//ESto hay que cambiarlo (no acceder a las propiedades) mejor metodo.

            tempNs++;
        }

         */

    }
}


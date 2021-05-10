/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import GUIJPT.MainGUI;
import elements.Camera;
import elements.Scene;
import geometry.Primitive;
import maths.ColorValue;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PathTracer implements Runnable {

    private int image_width;
    private int image_height;
    double aspect_ratio;
    boolean progressive; //Path tracer set to progressive, it run unlimited
    int ns; //Number of samples
    int depth; //Maximum number of bounces we will allow
    double gammaValue;

    boolean activeThread;

    BufferedImage bi;
    ExecutorService executorService;

    MainGUI mainGUI;

    public PathTracer(int image_width, int image_height, boolean progressive, int ns,
                      int depth, double gammaValue, BufferedImage bi, boolean activeThread, MainGUI mainGUI) {
        this.image_width = image_width;
        this.image_height = image_height;
        this.aspect_ratio = (double)image_width/(double)image_height;
        this.progressive = progressive;
        this.ns = ns;
        this.depth = depth;
        this.gammaValue = gammaValue;
        this.bi = bi;
        this.activeThread=activeThread;
        this.mainGUI=mainGUI;
    }



    @Override
    public void run() {
        ColorValue[][] imagePixels = new ColorValue[image_width][image_height];

        //Create scene
        ArrayList<Primitive> primList = Scene.generateScene(13);
        //ArrayList<Primitive> primList = Scene.loadScene();

        Camera cam=Camera.generateCamera(aspect_ratio,8);


        int availableProcessors=Runtime.getRuntime().availableProcessors();


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
        for (int i = 0; i < availableProcessors; i++) {
            listOfPixelGroups.add(new ArrayList<>());
        }

        //Distribute the pixel position along the different group of pixel positions
        int count = 0;
        for (int[] pixelLocation : pixelList) {
            listOfPixelGroups.get(count).add(pixelLocation);
            if (count + 1 < availableProcessors) {
                count++;
            } else {
                count = 0;
            }
        }

        int tempNs = 1;
        while ((tempNs <= ns || progressive)&& activeThread) {

            executorService = Executors.newFixedThreadPool(availableProcessors);

            int ID = 0;
            for (ArrayList<int[]> shufflePixelGroup : listOfPixelGroups) {
                ID++;

                executorService.execute(new PTcalcs_threads_runnable(primList, cam, depth, bi,
                        shufflePixelGroup, imagePixels, gammaValue, ID));

            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(1000, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("------------------------------------PASS NUMBER "
                    + tempNs + " HAS BEEN COMPLETED------------------------------------");

            mainGUI.increasePassesCallBack();

            tempNs++;

            //INTRODUCIR AQUI LA LLAMADA A ACTUALIZAR LA IMAGEN
        }
    }

    public void setActiveThread(boolean activeThread) {
        this.activeThread = activeThread;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import GUIJPT.MainGUI;
import elements.Camera;
import elements.SceneLoader;
import geometry.Primitive;
import maths.Background;
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
    boolean progressive; //If path tracer set to progressive, it run unlimited passes until you stop it
    int np; //Number of passes
    int depth; //Maximum number of bounces we will allow
    double gammaValue;
    boolean activeThread;//Flag to control if the thread should be working or not
    BufferedImage bi;//Image to be rendered to
    ExecutorService executorService;//Thread pool

    MainGUI mainGUI;//Used to send the callback of passes increased
    Background background;
    SceneLoader sceneLoader;
    Camera camera;

    /**
     * Constructor to initialise the object using the parameters given
     * @param image_width
     * @param image_height
     * @param progressive Boolean to control if the thread pool should keep working
     * @param np Number of passes
     * @param depth Number of bounces allowed
     * @param gammaValue
     * @param bi Image to render to
     * @param mainGUI
     * @param background
     * @param sceneLoader
     */
    public PathTracer(int image_width, int image_height, boolean progressive, int np, int depth, double gammaValue,
                      BufferedImage bi, MainGUI mainGUI, Background background, SceneLoader sceneLoader, Camera camera) {
        this.image_width = image_width;
        this.image_height = image_height;
        this.aspect_ratio = (double)image_width/(double)image_height;
        this.progressive = progressive;
        this.np = np;
        this.depth = depth;
        this.gammaValue = gammaValue;
        this.bi = bi;
        this.mainGUI=mainGUI;
        this.background=background;
        this.sceneLoader=sceneLoader;
        this.camera=camera;
    }



    @Override
    public void run() {
        //Create an array of all the pixels on the image
        ColorValue[][] imagePixels = new ColorValue[image_width][image_height];

        //Create a new scene and camera from the xml loader
        ArrayList<Primitive> primList =sceneLoader.getGeometry();

        int availableProcessors=Runtime.getRuntime().availableProcessors()+1;//Check the proccessors available

        //Generate a list of pixels
        ArrayList<int[]> pixelList = new ArrayList<>();
        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {
                int[] pixelLocation = {i, j};//Last value is the reference for the Number of pass
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

        activeThread=true;//Initialise the thread

        int tempNs = 1;//Initialise the passes number
        while ((tempNs <= np || progressive)&& activeThread) {

            long startTime0=System.nanoTime();

            executorService = Executors.newFixedThreadPool(availableProcessors);//Thread pool

            int ID = 0;
            for (ArrayList<int[]> shufflePixelGroup : listOfPixelGroups) {
                ID++;

                executorService.execute(new PTcalcs_threads_runnable(primList, camera, depth, bi,
                        shufflePixelGroup,imagePixels,gammaValue,ID,background,activeThread,tempNs));

            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(1000, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long partialTime0 =  System.nanoTime();
            long millis0 = (partialTime0 - startTime0)/1000000;

            System.out.println("------------------------------------PASS NUMBER "
                    + tempNs + " HAS BEEN COMPLETED IN " + millis0 + "ms------------------------------------");

            if(mainGUI.controlKeys){
                imagePixels=new ColorValue[image_width][image_height];
                mainGUI.controlKeys=false;
                activeThread=true;
                tempNs=1;
            }else{
                mainGUI.updatePasses(tempNs);//Update the pass number
                tempNs++;
            }
        }
    }

    /**
     * Change the value of active thread to stop or start the render
     * @param activeThread
     */
    public void setActiveThread(boolean activeThread) {
        this.activeThread = activeThread;
    }
}


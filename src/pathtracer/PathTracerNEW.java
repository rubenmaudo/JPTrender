/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathtracer;

import GUIJPT.MainGUI;
import elements.Camera;
import elements.Scene;
import elements.SceneLoader;
import geometry.Primitive;
import maths.Background;
import maths.ColorValue;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PathTracerNEW implements Runnable {

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
    public PathTracerNEW(int image_width, int image_height, boolean progressive, int np, int depth, double gammaValue,
                         BufferedImage bi, MainGUI mainGUI, Background background, SceneLoader sceneLoader) {
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
        this.activeThread=true;
    }



    @Override
    public void run() {

        Camera cam=sceneLoader.getCamera(aspect_ratio);

        int availableProcessors=16;//Check the proccessors available

        ArrayList<BufferedImage> listImages= new ArrayList<BufferedImage>();//Portions of images to be created

        ArrayList<ArrayList<Primitive>> listSceneGeometries= new ArrayList<ArrayList<Primitive>>();//List of Geometries for each thread

        ArrayList<Camera> listCameras = new ArrayList<Camera>();//List of cameras for each thread

        ArrayList<Background> listBackgrounds = new ArrayList<Background>();

        ArrayList<Integer> listPasses = new ArrayList<Integer>();

        //Create the different arrays to be used by every thread
        int portionImageHeight=bi.getHeight()/availableProcessors;
        for(int i=0; i<availableProcessors; i++){

            //Array of images
            if(i==availableProcessors){
                BufferedImage bufIma=new BufferedImage(
                        bi.getWidth(),
                        bi.getHeight()-(portionImageHeight*i),
                        BufferedImage.TYPE_INT_ARGB);
                listImages.add(bufIma);
            }else{
                BufferedImage bufIma=new BufferedImage(bi.getWidth(), portionImageHeight, BufferedImage.TYPE_INT_ARGB);
                listImages.add(bufIma);
            }

            //Array of geometry
            ArrayList<Primitive> geometryScene= new ArrayList<Primitive>();
            for( Primitive prim : sceneLoader.getGeometry()){
                Primitive geometry=prim.clone();
                geometryScene.add(geometry);
            }
            listSceneGeometries.add(geometryScene);

            //Array of cameras
            listCameras.add(cam.clone());

            //Array of backgrounds
            listBackgrounds.add(new Background(background.getMainColor(),background.getSecondaryColor(),background.getMixed()));

            //Array of passes for each thread
            listPasses.add(np);
        }

        ExecutorService execServ= Executors.newFixedThreadPool(availableProcessors);
        ArrayList<Future<?>> threads =new ArrayList<>();

        //ArrayList<PTcalcs_threads_runnableNEW> threads= new ArrayList<>();

        for(int k=0; k<availableProcessors; k++){
            int depthThread= depth;
            double gammaValueThread = gammaValue;

            threads.add(execServ.submit(new PTcalcs_threads_runnableNEW(
                    listSceneGeometries.get(k),
                    listCameras.get(k),
                    depthThread,
                    listImages.get(k),
                    gammaValueThread,
                    k+1,
                    listBackgrounds.get(k),
                    listPasses.get(k),
                    activeThread
                    )));
        }

        while(true){
            Graphics g = bi.getGraphics();

            int tempRef=0;
            for(BufferedImage partialImage : listImages){
                g.drawImage(partialImage, 0, (partialImage.getHeight())*tempRef, null);

                tempRef++;

            }
            g.dispose();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



        /*
        activeThread=true;//Initialise the thread

        int tempNs = 1;//Initialise the passes number
        while ((tempNs <= np || progressive)&& activeThread) {

            executorService = Executors.newFixedThreadPool(4);//Thread pool

            int ID = 0;
            for (ArrayList<int[]> shufflePixelGroup : listOfPixelGroups) {
                ID++;

                executorService.execute(new PTcalcs_threads_runnable(primList, cam, depth, bi,
                        shufflePixelGroup, imagePixels, gammaValue, ID,background));

            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(1000, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("------------------------------------PASS NUMBER "
                    + tempNs + " HAS BEEN COMPLETED------------------------------------");

            mainGUI.updatePasses();//Update the pass number
            tempNs++;
        }

         */
    }

    /**
     * Change the value of active thread to stop or start the render
     * @param activeThread
     */
    public void setActiveThread(boolean activeThread) {
        this.activeThread = activeThread;
    }
}


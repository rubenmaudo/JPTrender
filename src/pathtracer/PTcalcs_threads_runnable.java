package pathtracer;

import elements.Camera;
import geometry.Primitive;
import maths.Background;
import maths.ColorValue;
import maths.Hittable;
import maths.Ray;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PTcalcs_threads_runnable implements Runnable {

    //THREAD PARAMETERS
    int ID;

    ArrayList<Primitive> scene;
    Camera camera;
    int depth;

    BufferedImage image;
    ArrayList<int[]> pixelList;
    ColorValue[][] imagePixels;
    double gammaValue;

    Background background;

    //CONSTRUCTOR
    public PTcalcs_threads_runnable(ArrayList<Primitive> scene,
                                    Camera camera,
                                    int depth,
                                    BufferedImage image,
                                    ArrayList<int[]> list,
                                    ColorValue[][] imagePixels,
                                    double gammaValue,
                                    int ID, Background background){


        //this.camera=camera;
        this.depth=depth;

        this.image=image;
        this.pixelList=list;
        this.imagePixels=imagePixels;
        this.gammaValue=gammaValue;

        this.ID=ID;
        this.background=Background.clone(background);

        //this.scene=scene;

        this.camera=camera.clone();

        this.scene=new ArrayList<Primitive>();

        for( Primitive prim : scene){
            Primitive geometry=prim.clone();
            this.scene.add(geometry);
        }
    }

    //METHODS
    @Override
    public void run() {
        long startTime0=System.nanoTime();

        for(int[] pxLoc : pixelList){
            long startTime1=0;
            long startTime2=0;
            long startTime3=0;
            long startTime4=0;
            long partialTime1=0;
            long partialTime2=0;
            long partialTime3=0;
            long partialTime4=0;

            //
            if(pxLoc[0]==300 && pxLoc[1]==300){
                startTime1= System.nanoTime();
            }

            ColorValue col;

            double u = (pxLoc[0] + ThreadLocalRandom.current().nextDouble())  /  image.getWidth();

            //
            if(pxLoc[0]==300 && pxLoc[1]==300){
                partialTime1 =System.nanoTime();
                startTime2=  System.nanoTime();
            }

            double v = ((image.getHeight()-pxLoc[1]) + ThreadLocalRandom.current().nextDouble()) / image.getHeight();

            //
            if(pxLoc[0]==300 && pxLoc[1]==300){
                partialTime2 =  System.nanoTime();
                startTime3=  System.nanoTime();
            }

            Ray r = camera.get_ray(u, v);

            //
            if(pxLoc[0]==300 && pxLoc[1]==300){
                partialTime3 =  System.nanoTime();
                startTime4=  System.nanoTime();
            }

            col = ColorValue.colorRay(r, new Hittable(scene),depth, background);


            //
            if(pxLoc[0]==300 && pxLoc[1]==300){
                partialTime4 =  System.nanoTime();

                long millis1 = (partialTime1 - startTime1);
                long millis2 = (partialTime2 - startTime2);
                long millis3 = (partialTime3 - startTime3);
                long millis4 = (partialTime4 - startTime4);

                System.out.println(millis1);
                System.out.println(millis2);
                System.out.println(millis3);
                System.out.println(millis4);
            }

            if (imagePixels[pxLoc[0]][pxLoc[1]]==null){
                imagePixels[pxLoc[0]][pxLoc[1]]=new ColorValue(0,0,0);
            }

            imagePixels[pxLoc[0]][pxLoc[1]]=imagePixels[pxLoc[0]][pxLoc[1]].add(col);
            pxLoc[2]=pxLoc[2]+1;



            //Gamma correction
            col= new ColorValue(imagePixels[pxLoc[0]][pxLoc[1]].divide(pxLoc[2]).vR(),
                    imagePixels[pxLoc[0]][pxLoc[1]].divide(pxLoc[2]).vG(),
                    imagePixels[pxLoc[0]][pxLoc[1]].divide(pxLoc[2]).vB(),
                    gammaValue);



            image.setRGB(pxLoc[0],pxLoc[1],col.toRGB());




        }
        long partialTime0 =  System.nanoTime();
        long millis0 = (partialTime0 - startTime0)/1000000;
        System.out.println("The thread "+ ID + " = "+millis0);
    }


}

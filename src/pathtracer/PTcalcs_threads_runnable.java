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


        this.scene=scene;
        this.camera =camera;
        this.depth=depth;

        this.image=image;
        this.pixelList=list;
        this.imagePixels=imagePixels;
        this.gammaValue=gammaValue;

        this.ID=ID;
        this.background=background;



    }

    //METHODS
    @Override
    public void run() {
        long startTime0=System.nanoTime();

        for(int[] pxLoc : pixelList){

            ColorValue col;

            double u = (pxLoc[0] + ThreadLocalRandom.current().nextDouble())  /  image.getWidth();

            double v = ((image.getHeight()-pxLoc[1]) + ThreadLocalRandom.current().nextDouble()) / image.getHeight();

            Ray r = camera.get_ray(u, v);

            col = ColorValue.colorRay(r, new Hittable(scene),depth, background);

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
        System.out.println("The thread "+ ID + " spent= "+millis0);
    }


}

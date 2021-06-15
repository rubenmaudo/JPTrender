package pathtracer;

import elements.Camera;
import geometry.Hit_record;
import geometry.Primitive;
import maths.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;
import static maths.Utils.INFINITY;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PTcalcs_threads_runnableNEW implements Runnable {

    //THREAD PARAMETERS
    int ID;

    ArrayList<Primitive> scene;
    Camera camera;
    int depth;

    BufferedImage partialImage;
    ColorValue[][] imagePixels;
    double gammaValue;

    Background background;

    int totalPasses;
    int passesCount;
    boolean activeThread;

    ThreadLocalRandom random=ThreadLocalRandom.current();

    //CONSTRUCTOR
    public PTcalcs_threads_runnableNEW(ArrayList<Primitive> scene,
                                       Camera camera,
                                       int depth,
                                       BufferedImage image,
                                       double gammaValue,
                                       int ID,
                                       Background background,
                                       int passes,
                                       boolean activeThread){


        this.scene=scene;
        this.camera=camera;
        this.depth=depth;
        this.partialImage =image;
        this.gammaValue=gammaValue;
        this.ID=ID;
        this.background=background;

        this.imagePixels = new ColorValue[image.getWidth()][image.getHeight()];//NEED TO BE CHANGED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        this.totalPasses=passes;
        passesCount=1;

        this.activeThread=activeThread;


    }

    //METHODS
    @Override
    public void run() {
        int finalHeight = 600; //NEED TO BE CHANGED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        while(passesCount<=totalPasses && activeThread){
            long startTime0=System.nanoTime();

            for (int y = 0; y< partialImage.getHeight(); y++){
                for (int x = 0; x< partialImage.getWidth(); x++){


                    ColorValue col=null;


                    double u = (x + random.nextDouble() )  /  partialImage.getWidth();


                    double v = ((finalHeight-(y+(partialImage.getHeight()*(ID-1)) + random.nextDouble()) )/ finalHeight);

                    Ray r = camera.get_ray(u, v);

                    col = ColorValue.colorRay(r, new Hittable(scene),depth, background);

                    //col=new ColorValue(0.2*ID,0,0);

                    if (imagePixels[x][y]==null){
                        imagePixels[x][y]=new ColorValue(0,0,0);
                    }


                    imagePixels[x][y]=imagePixels[x][y].add(col);


                    //Gamma correction
                    col= new ColorValue(imagePixels[x][y].divide(passesCount).vR(),
                            imagePixels[x][y].divide(passesCount).vG(),
                            imagePixels[x][y].divide(passesCount).vB(),
                            gammaValue);

                    partialImage.setRGB(x,y,col.toRGB());
                }
            }

            long partialTime0 =  System.nanoTime();
            long millis0 = (partialTime0 - startTime0)/1000000;


            System.out.println("Id:" + ID + ", Pass=" + passesCount + " time="+ millis0);
            passesCount++;
        }
    }
}

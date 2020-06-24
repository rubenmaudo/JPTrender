package pathtracer;

import elements.Camera;
import geometry.Primitive;
import geometry.Sphere;
import maths.ColorValue;
import maths.Hittable;
import maths.Ray;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class PTcalcs_threads_runnable implements Runnable {

    //THREAD PARAMETERS
    int ID;

    ArrayList<Primitive> scene;
    Camera cam;
    int depth;

    BufferedImage image;
    ArrayList<int[]> pixelList;
    ColorValue[][] imagePixels;
    double gammaValue;

    //CONSTRUCTOR
    public PTcalcs_threads_runnable(ArrayList<Primitive> scene,
                                    Camera cam,
                                    int depth,
                                    BufferedImage image,
                                    ArrayList<int[]> list,
                                    ColorValue[][] imagePixels,
                                    double gammaValue,
                                    int ID){

        this.scene=scene;
        this.cam=cam;
        this.depth=depth;

        this.image=image;
        this.pixelList=list;
        this.imagePixels=imagePixels;
        this.gammaValue=gammaValue;

        this.ID=ID;
    }

    //METHODS
    @Override
    public void run() {
        for(int[] pxLoc : pixelList){

            ColorValue col;

            double u = (pxLoc[0] + Math.random())  /  image.getWidth();
            double v = ((image.getHeight()-pxLoc[1]) + Math.random()) / image.getHeight();
            Ray r =cam.get_ray(u, v);
            col = ColorValue.colorRay(r, new Hittable(scene),depth);

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
    }
}

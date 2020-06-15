package math;

import elements.Camera;
import geometry.Primitive;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class ImageProcess_threads extends RecursiveAction{
    int ID;

    ArrayList<Primitive> primList;
    Camera cam;
    int depth;

    BufferedImage image;
    ArrayList<int[]> pixelList;
    ColorValue[][] imagePixels;
    double gammaValue;

    public ImageProcess_threads (ArrayList<Primitive> primList,
                                 Camera cam,
                                 int depth,
                                 BufferedImage image,
                                 ArrayList<int[]> list,
                                 ColorValue[][] imagePixels,
                                 double gammaValue,
                                 int ID
                                 ){
        this.primList=primList;
        this.cam=cam;
        this.depth=depth;

        this.image=image;
        this.pixelList=list;
        this.imagePixels=imagePixels;
        this.gammaValue=gammaValue;

        this.ID=ID;
        System.out.println("Se ha arrancado la tarea " + ID);
    }

    @Override
    protected void compute() {

        for(int[] pxLoc : pixelList){

            ColorValue col;

            double u = (pxLoc[0] + Math.random())  /  image.getWidth();
            double v = ((image.getHeight()-pxLoc[1]) + Math.random()) / image.getHeight();
            Ray r =cam.get_ray(u, v);
            col = ColorValue.colorRay(r,primList,depth);

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

        System.out.println("Se ha completado la tarea " + ID);
    }
}

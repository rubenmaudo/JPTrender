package maths;

import elements.Camera;
import geometry.Primitive;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ImageProcess_Recursive extends RecursiveAction {

    ArrayList<Primitive> scene;
    Camera cam;
    int depth;

    BufferedImage image;
    List<int[]> pixelList;
    ColorValue[][] imagePixels;
    double gammaValue;

    private final int threshold = 600;



    public ImageProcess_Recursive(ArrayList<Primitive> scene,
                                  Camera cam,
                                  int depth,
                                  BufferedImage image,
                                  List<int[]> list,
                                  ColorValue[][] imagePixels,
                                  double gammaValue){

        this.scene = scene;
        this.cam = cam;
        this.depth = depth;

        this.image = image;
        this.imagePixels = imagePixels;
        this.pixelList=list;
        this.gammaValue = gammaValue;
    }

    @Override
    protected void compute() {
        if(pixelList.size()<threshold){
            computeDirectly();
            return;
        }

        List<int[]> subList1=  pixelList.subList(0,(pixelList.size()+1)/2);
        List<int[]> subList2=  pixelList.subList((pixelList.size()+1)/2,pixelList.size());

        invokeAll(new ImageProcess_Recursive(scene, cam, depth, image, subList1, imagePixels, gammaValue),
                new ImageProcess_Recursive(scene, cam, depth, image, subList2, imagePixels, gammaValue)
                );
    }

    private void computeDirectly() {
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

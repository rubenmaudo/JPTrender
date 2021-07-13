/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import geometry.Primitive;
import geometry.Sphere;
import materials.Dielectric;
import materials.Lambertian;
import materials.Material;
import materials.Metal;
import maths.ColorValue;
import maths.Ray;
import maths.Utils;
import maths.Vec3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class Camera {

    //CONSTRUCTOR FIELDS
    private Vec3 lookfrom;
    private Vec3 lookat;
    private Vec3 vup;
    private double vfov;
    private double aperture;
    private double focus_dist;
    private int autofocus;
    private double aspect_ratio;

    //CAMERA FIELDS
    private Vec3 lower_left_corner;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Vec3 origin;
    private Vec3 u,v,w; //Parameters to create an orthonormal basis to describe camera orientation
    private double lens_radius;


    //CAMERA CONSTRUCTOR

    /**
     *
     * @param lookfrom Point from where the camera will look from
     * @param lookat Point where the camera will be aiming
     * @param vup Vertical vector to identify the vertical position of the camera
     * @param vfov Field of view (equivalent to camera lense mm)
     * @param aspect_ratio Aspect ratio of image
     * @param aperture
     * @param focus_dist Focus distance
     * @param autofocus Flag to activate or deactivate the camera
     */
    public Camera(Vec3 lookfrom, Vec3 lookat, Vec3 vup, double vfov, double aspect_ratio,
                  double aperture, double focus_dist, int autofocus){

        this.lookfrom=lookfrom;
        this.lookat=lookat;
        this.vup=vup;
        this.vfov=vfov;
        this.aperture=aperture;
        this.focus_dist=focus_dist;
        this.autofocus=autofocus;
        this.aspect_ratio=aspect_ratio;

        calcCamera();
    }



    //CAMERA METHODS

    /**
     * Make calcs to complete the camera parameters
     */
    public void calcCamera(){
        if(autofocus==1){
            focus_dist=lookfrom.sub(lookat).length();
        }

        double theta = Utils.degrees_to_radians(vfov);//FieldOfView changed to radians
        double h = Math.tan(theta/2);
        double viewport_height = 2.0 * h;//Identify the viewport height
        double viewport_width =aspect_ratio * viewport_height;//Identify the viewport width

        this.w = lookfrom.sub(lookat).normalize();
        this.u = vup.cross(w).normalize();
        this.v = w.cross(u);

        this.origin = lookfrom; //Origin point for the camera
        this.horizontal = u.product(focus_dist*viewport_width);//Parameter to identify the width of the viewport to "take"
        this.vertical = v.product(focus_dist*viewport_height);//Parameter to identify the height of the viewport to "take"
        //Parameter to identify the left corner of the picture to "take" that will be used as a reference
        this.lower_left_corner = origin.sub(horizontal.divide(2)).sub(vertical.divide(2)).sub(w.product(focus_dist));
        this.lens_radius= aperture / 2;//Size of the lens to determine the aperture to be used
    }


    /**
     *Produce a ray based in the parameters given
     * @param s Also called u / parameter to identify the x position in the viewport
     * @param t Also called v / parameter to identify the y position in the viewport
     * @return a new ray that goes from the origing of the camera to the direction created based in s & t
     */
    public Ray get_ray(double s, double t){
        Vec3 rd= Vec3.random_in_unit_disk().product(lens_radius);//Obtain random point in the lens
        Vec3 offset = u.product(rd.x()).add(v.product(rd.y()));


        return new Ray(origin.add(offset),
                lower_left_corner.add(horizontal.product(s)).add(vertical.product(t)).sub(origin).sub(offset)
        );
    }


    //PARSE FROM CAMERA TO XML TAGS
    /**
     * Method to obtain details from camera class and convert it to XML
     * @param doc
     * @return
     */
    public Node getCamera(Document doc){
        Element camera=doc.createElement("Camera");
        camera.setAttribute("vfov", String.valueOf(this.vfov));
        camera.setAttribute("aperture", String.valueOf(this.aperture));
        camera.setAttribute("focus_dist", String.valueOf(this.focus_dist));
        camera.setAttribute("autofocus", String.valueOf(this.autofocus));

        Element lookfrom=doc.createElement("lookfrom");
        camera.appendChild(lookfrom);
        lookfrom.setAttribute("X",String.valueOf(this.lookfrom.getValue(0)));
        lookfrom.setAttribute("Y",String.valueOf(this.lookfrom.getValue(1)));
        lookfrom.setAttribute("Z",String.valueOf(this.lookfrom.getValue(2)));

        Element lookat=doc.createElement("lookat");
        camera.appendChild(lookat);
        lookat.setAttribute("X",String.valueOf(this.lookat.getValue(0)));
        lookat.setAttribute("Y",String.valueOf(this.lookat.getValue(1)));
        lookat.setAttribute("Z",String.valueOf(this.lookat.getValue(2)));

        Element vup=doc.createElement("vup");
        camera.appendChild(vup);
        vup.setAttribute("X",String.valueOf(this.vup.getValue(0)));
        vup.setAttribute("Y",String.valueOf(this.vup.getValue(1)));
        vup.setAttribute("Z",String.valueOf(this.vup.getValue(2)));
        return camera;
    }


    public void updateAspectRatio(double aspectRatio){
        this.aspect_ratio=aspectRatio;
        calcCamera();
    }

    public void updateCentrePositionUp(){
        double value= lookfrom.y()*1.05-lookfrom.y();
        lookat=new Vec3(lookat.x(),lookat.y()+value, lookat.z());
        calcCamera();
    }
    public void updateCentrePositionDown(){
        double value=lookfrom.y()- lookfrom.y()*0.9523809;
        lookat=new Vec3(lookat.x(),lookat.y()-value, lookat.z());
        calcCamera();
    }

    public void updateCameraPositionUp(){
        double value= lookfrom.y()*1.05-lookfrom.y();
        lookfrom=new Vec3(lookfrom.x(),lookfrom.y()+value, lookfrom.z());
        lookat=new Vec3(lookat.x(),lookat.y()+value, lookat.z());
        calcCamera();
    }
    public void updateCameraPositionDown(){
        double value=lookfrom.y()- lookfrom.y()*0.9523809;
        lookfrom=new Vec3(lookfrom.x(),lookfrom.y()-value, lookfrom.z());
        lookat=new Vec3(lookat.x(),lookat.y()-value, lookat.z());
        calcCamera();
    }

    public void updateCameraPositionForward(){
        Vec3 latLfrm= lookfrom.sub(lookat);
        double t=latLfrm.length();
        Vec3 normLatLfrm=new Vec3(latLfrm.x(),latLfrm.y(),latLfrm.z());
        normLatLfrm.normalize();
        lookfrom=lookat.add(normLatLfrm.product(t*0.9803921));
        calcCamera();
    }

    public void updateCameraPositionBackward(){
        Vec3 latLfrm= lookfrom.sub(lookat);
        double t=latLfrm.length();
        Vec3 normLatLfrm=new Vec3(latLfrm.x(),latLfrm.y(),latLfrm.z());
        normLatLfrm.normalize();
        lookfrom=lookat.add(normLatLfrm.product(t*1.02));
        calcCamera();
    }

    public void updateCameraPositionRight(){
        double newX= (lookat.x() + ((lookfrom.x()-lookat.x())*Math.cos(0.02)) + ((lookfrom.z()- lookat.z())*Math.sin(0.02)));
        double newZ= (lookat.z() + ((lookfrom.x()-lookat.x())*-Math.sin(0.02)) + ((lookfrom.z()- lookat.z())*Math.cos(0.02)));
        lookfrom=new Vec3(newX, lookfrom.y(),newZ);
        calcCamera();
    }

    public void updateCameraPositionLeft(){
        double newX= (lookat.x() + ((lookfrom.x()-lookat.x())*Math.cos(-0.02)) + ((lookfrom.z()- lookat.z())*Math.sin(-0.02)));
        double newZ= (lookat.z() + ((lookfrom.x()-lookat.x())*-Math.sin(-0.02)) + ((lookfrom.z()- lookat.z())*Math.cos(-0.02)));
        lookfrom=new Vec3(newX, lookfrom.y(),newZ);
        calcCamera();
    }


    public void setOrigin() {
        this.origin = origin.add(new Vec3(0,0,10));

    }

    public Camera clone(){
        return new Camera(Vec3.clone(lookfrom),
                Vec3.clone(lookat),
                Vec3.clone(vup),
                vfov,
                aspect_ratio,
                aperture,
                focus_dist,
                autofocus);
    }

    public void printValues(){
        System.out.println(lookfrom);
        System.out.println(lookat);
    }

}

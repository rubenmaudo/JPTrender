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

    //CAMERA FINAL FIELDS
    private final Vec3 lower_left_corner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;
    private final Vec3 u,v,w; //Parameters to create an orthonormal basis to describe camera orientation
    private final double lens_radius;


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
        this.autofocus=autofocus;
    }


    //CAMERA METHODS
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


    /**
     * Create a camera with different settings, old method used to create cameras
     * @deprecated
     * @param aspect_ratio aspect ratio for the image
     * @param cameraID ID to identify the camera presettings
     * @return a Camera object
     */

    public static Camera generateCamera(double aspect_ratio, int cameraID){

        Vec3 lookfrom;
        Vec3 lookat;
        Vec3 vup;
        double dist_to_focus; //lookfrom.sub(lookat).length(); This would be the change to auto focus to the point you are looking to
        double aperture;

        Camera camera;

        switch(cameraID){
            case 1:
                //Standard camera
                lookfrom = new Vec3(0, 0, 0);
                lookat = new Vec3(0, 0, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0.0;

                camera = new Camera(lookfrom, lookat, vup, 90, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 2:
                //Top left view
                lookfrom = new Vec3(-2, 2, 1);
                lookat = new Vec3(0, 0, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0;

                camera = new Camera(lookfrom, lookat, vup, 90, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 3:
                //Top left view zoomed
                lookfrom = new Vec3(-2, 2, 1);
                lookat = new Vec3(0, 0, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0;

                camera = new Camera(lookfrom, lookat, vup, 20, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 4:
                //back camera
                lookfrom = new Vec3(0, 0, -3);
                lookat = new Vec3(0, 0, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0;

                camera = new Camera(lookfrom, lookat, vup, 70, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 5:
                //side camera
                lookfrom = new Vec3(-2.5, 0.3, 0.5);
                lookat = new Vec3(0, 0, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0.12;

                camera = new Camera(lookfrom, lookat, vup, 40, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 6:
                //Camera for scene 8
                lookfrom = new Vec3(13, 2, 3);
                lookat = new Vec3(0, 0, 0);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = 10; //lookfrom.sub(lookat).length(); This would be the change to auto focus to the point you are looking to
                aperture = 0.1;

                camera = new Camera(lookfrom, lookat, vup, 20, aspect_ratio, aperture, dist_to_focus,0);
                return camera;

            case 7:
                //Standard camera for scene 11 (caustic(
                lookfrom = new Vec3(0, 1.2, 2);
                lookat = new Vec3(0, 0.5, -1);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0.0;

                camera = new Camera(lookfrom, lookat, vup, 50, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            case 8:
                //Scene cornell box
                lookfrom = new Vec3(0, 278, 1078);
                lookat = new Vec3(0, 278, 0);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = 10;
                aperture = 0.0;

                camera = new Camera(lookfrom, lookat, vup, 40, aspect_ratio, aperture, dist_to_focus,1);
                return camera;

            default:
                //Camera for box scene
                lookfrom = new Vec3(3, 3, 2);
                lookat = new Vec3(0, 0.5, 0);
                vup = new Vec3(0, 1, 0);
                dist_to_focus = lookfrom.sub(lookat).length();
                aperture = 0;

                camera = new Camera(lookfrom, lookat, vup, 40, aspect_ratio, aperture, dist_to_focus,1);
                return camera;
        }

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

}

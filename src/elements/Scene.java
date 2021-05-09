package elements;

import geometry.*;
import materials.*;
import maths.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @author : Ruben Maudo
 * @since : 23/06/2020, Tue
 **/

public class Scene {

    /**
     *
     * @param i Identefy the scene we want to generate and render
     * @return Arraylist with all the geometry involved in the scene
     */
    public static ArrayList<Primitive> generateScene(int i){
        ArrayList<Primitive> geometry = new ArrayList<>();

        switch(i){
            case 1:
                ////Scene with the standard three balls
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new ColorValue(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.8,0.8),0.2)));

                return geometry;

            case 2:
                //Scene with the standard three balls, and two small, one mirrored and the other one metalic red
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new ColorValue(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(1,0.2,0.2),0.4)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.8,0.8),0.2)));

                return geometry;

            case 3:
                //Scene with the standard three balls one of the dielectric, and two small, one mirrored and one dielectric
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Dielectric(1.7)));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Dielectric(1.7)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.8,0.8),0.2)));

                return geometry;

            case 4:
                //Scene with blue ball and one dielectric
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new ColorValue(0.1,0.2,0.5))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Dielectric(1.5)));

                return geometry;

            case 5:
                //Scene with the standard three balls, and two small, one mirrored and one dielectric
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new ColorValue(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Dielectric(1.5)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.8,0.8),0.2)));

                return geometry;

            case 6:
                //Scene with buble
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new ColorValue(0.1,0.2,0.5))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Dielectric(1.5)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),-0.49,new Dielectric(1.5)));

                return geometry;

            case 7:
                //Red & Blue spheres for camera test
                double R =Math.cos(Utils.PI/4);
                geometry.add(new Sphere(new Vec3(-R,0,-1),R,new Lambertian(new ColorValue(0,0,1))));
                geometry.add(new Sphere(new Vec3(R,0,-1),R,new Lambertian(new ColorValue(1,0,0))));

                return geometry;

            case 8:
                //Create random scene
                Material ground_material = new Lambertian(new ColorValue(0.5,0.5,0.5));
                geometry.add(new Sphere(new Vec3(0,-1000,0),1000,ground_material));

                for(int a=-11; a<11; a++){
                    for(int b=-11; b<11; b++){
                        double choose_mat = Math.random();
                        Vec3 center = new Vec3(a + 0.8*Math.random(), 0.2 , b + 0.8*Math.random());

                        if(center.sub(new Vec3(4,0.2,0)).length()>0.9){
                            Material  sphere_material;

                            if(choose_mat < 0.8){
                                //diffuse
                                ColorValue albedo = ColorValue.randomColorValue().product(ColorValue.randomColorValue());
                                sphere_material = new Lambertian(albedo);
                                geometry.add(new Sphere(center,0.2,sphere_material));
                            } else if(choose_mat < 0.9){
                                //metal
                                ColorValue albedo = ColorValue.randomColorValue(0.5,1);
                                double fuzz =Vec3.random_double(0,0.5);
                                sphere_material = new Metal(albedo,fuzz);
                                geometry.add(new Sphere(center,0.2,sphere_material));
                            }else if(choose_mat < 0.95){
                                //lightDiffuse
                                sphere_material = new Diffuse_light(new ColorValue(3,3,3));
                                geometry.add(new Sphere(center,0.2,sphere_material));
                            }else{
                                //Dielectric
                                sphere_material = new Dielectric(1.5);
                                geometry.add(new Sphere(center,0.2,sphere_material));
                            }
                        }
                    }
                }

                Material material1 = new Dielectric(1.5);
                geometry.add(new Sphere(new Vec3(0,1,0),1,material1));

                Material material2 = new Lambertian(new ColorValue(0.4,0.2,0.1));
                geometry.add(new Sphere(new Vec3(-4,1,0),1,material2));

                Material material3 = new Metal(new ColorValue(0.7,0.6,0.5),0);
                geometry.add(new Sphere(new Vec3(4,1,0),1,material3));

                saveScene(geometry);

                return geometry;

            case 9:
                //Scene with two small dielectrics that have fuzziness
                geometry.add(new Sphere(new Vec3(0,0,-1.15),0.5,new Lambertian(new ColorValue(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Dielectric(new ColorValue(1,0.8,1),1.7,0.5)));
                geometry.add(new Sphere(new Vec3(0,-0.4,-0.7),0.1,new Dielectric(1.7,0.3)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(1,0.2,0.2),0.4)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));

                return geometry;

            case 10:
                //Scene with the standard three balls, and two small, one mirrored and one light
                geometry.add(new Sphere(new Vec3(0,0,-1.15),0.5,new Lambertian(new ColorValue(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new ColorValue(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Sphere(new Vec3(0,-0.4,-0.7),0.1,new Dielectric(1.7)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new ColorValue(1,0.2,0.2),0.4)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new ColorValue(0.8,0.6,0.2),0.9)));

                return geometry;

            case 11:
                //Scene to check caustic lights (red and blue balls)
                geometry.add(new Sphere(new Vec3(0,0.7,-1.3),0.3,new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Sphere(new Vec3(0.65,0,-1.15),0.5,new Dielectric(new ColorValue(1,0.1,0.1),1.4)));
                geometry.add(new Sphere(new Vec3(-0.65,0,-1.15),0.5,new Dielectric(new ColorValue(0.1,0.1,1),1.4)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0.8))));

                return geometry;

            case 12:
                //Scene to check shadows blue and red balls)

                geometry.add(new Sphere(new Vec3(0,0.7,-1.3),0.3,new Diffuse_light(new ColorValue(10,10,10))));
                geometry.add(new Sphere(new Vec3(0.65,0,-1.15),0.5,new Lambertian(new ColorValue(1,0.1,0.1))));
                geometry.add(new Sphere(new Vec3(-0.65,0,-1.15),0.5,new Lambertian(new ColorValue(0.1,0.1,1))));
                //geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new ColorValue(0.8,0.8,0.8))));
                geometry.add(new Plane_xz(-100,100,-100,100,-0.5,new Lambertian(new ColorValue(1,1,1))));

                return geometry;

            case 13:
                ////Cornell box test

                //LIGHT

                //Single square light at the top
                geometry.add(new Plane_xz(230,230,new Vec3(0,555,0), new Diffuse_light(new ColorValue(10,10,10))));



                //Striped lights white
                /*
                geometry.add(new Plane_xz(30,400,new Vec3(-195,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(-130,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(-65,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(0,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(65,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(130,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                geometry.add(new Plane_xz(30,400,new Vec3(195,555,0), new Diffuse_light(new ColorValue(5,5,5))));
                 */


                /*
                //Striped lights colours
                geometry.add(new Plane_xz(40,400,new Vec3(-195,555,0), new Diffuse_light(new ColorValue(0.7,70,0.7))));
                geometry.add(new Plane_xz(40,400,new Vec3(0,555,0), new Diffuse_light(new ColorValue(70,0.7,0.7))));
                geometry.add(new Plane_xz(40,400,new Vec3(195,555,0), new Diffuse_light(new ColorValue(0.7,0.7,70))));
                */

                /*
                //Vertical light right wall intens
                geometry.add(new Plane_yz(50,500,new Vec3(277.9,278,0), new Diffuse_light(new ColorValue(15,15,15))));
                 */

                //BOX
                geometry.add(new Plane_yz(556,556,new Vec3(-278,278,0),new Lambertian(new ColorValue(0.65,0.05,0.05))));
                geometry.add(new Plane_yz(556,556,new Vec3(278,278,0), new Lambertian(new ColorValue(0.12,0.45,0.15))));
                geometry.add(new Plane_xz(556,556,new Vec3(0,0,0), new Lambertian(new ColorValue(0.73,0.73,0.73))));
                geometry.add(new Plane_xz(556,556,new Vec3(0,556,0), new Lambertian(new ColorValue(0.73,0.73,0.73))));
                geometry.add(new Plane_xy(556,556,new Vec3(0,278,-278), new Lambertian(new ColorValue(0.73,0.73,0.73))));

                //CONTENT
                /*
                //Three spheres, metal mirror, lambertian blue, clear glass
                geometry.add(new Sphere(new Vec3(-100,70,128),70,new Metal(new ColorValue(1,1,1),0)));
                geometry.add(new Sphere(new Vec3(0,70,-22),70,new Lambertian(new ColorValue(0.05,0.05,0.65))));
                geometry.add(new Sphere(new Vec3(100,70,128),70,new Dielectric(1.65)));
                */


                //Three spheres, metal with fuzziness, lambertian blue, red dielectric with fuzziness
                geometry.add(new Sphere(new Vec3(-100,70,128),70,new Metal(new ColorValue(1,1,1),10)));
                geometry.add(new Sphere(new Vec3(0,70,-22),70,new Lambertian(new ColorValue(0.05,0.05,0.65))));
                geometry.add(new Sphere(new Vec3(100,70,128),70,new Dielectric(new ColorValue(1,0.2,0.2),1.5,0.25)));



                /*
                Staircase
                geometry.add(new Sphere(new Vec3(80,70,130),70,new Metal(new ColorValue(0.3,0.3,1),0.9)));
                geometry.add(new Sphere(new Vec3(200,110,-178),40,new Metal(new ColorValue(1,1,1),10)));
                geometry.add(new Sphere(new Vec3(-130,90,0),90,new Lambertian(new ColorValue(1,1,1))));
                //geometry.add(new Box(10,60,60, new Vec3(268,220,178),new Diffuse_light(new ColorValue(10,10,10))));
                geometry.add(new Box(60,200,10, new Vec3(200,60,-178),new Dielectric(1.65,0)));
                geometry.add(new Box(60,200,10, new Vec3(120,120,-178),new Dielectric(1.65,0.2)));
                geometry.add(new Box(60,200,10, new Vec3(60,180,-178),new Dielectric(1.65,0.4)));
                geometry.add(new Box(60,200,10, new Vec3(0,240,-178),new Dielectric(1.65,0.6)));
                geometry.add(new Box(60,200,10, new Vec3(-60,300,-178),new Dielectric(1.65,0.8)));
                geometry.add(new Box(60,200,10, new Vec3(-120,360,-178),new Dielectric(1.65,1)));
                geometry.add(new Box(60,200,10, new Vec3(-180,420,-178),new Dielectric(1.65,2)));
                geometry.add(new Box(60,200,10, new Vec3(-240,480,-178),new Dielectric(1.65,6)));
                */


                return geometry;

            default:
                ////Scene with flat plane and a box
                geometry.add(new Plane_xz(Double.MAX_VALUE,Double.MAX_VALUE,new Vec3(0,0,0),new Lambertian(new ColorValue(0.9,0.9,0))));
                geometry.add(new Box(1.2,1.2,1.5,new Vec3(0,0,0),new Lambertian(new ColorValue(0.9,0,0))));

                return geometry;
        }
    }

    /**
     * Serialize a scene to a file
     * @param scene ArrayList off primitives
     * @return boolean to check if it worked
     */
    public static boolean saveScene(ArrayList<Primitive> scene){
        try{
            FileOutputStream fos = new FileOutputStream("Scenes/scene.scn");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scene);
            oos.close();
            fos.close();
            return true;
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }


    /**
     * Load a previously serialized scene that is saved in the disc
     * @return a scene
     */
    public static ArrayList<Primitive> loadScene(){

        try{
            ArrayList<Primitive> scene;
            FileInputStream fis = new FileInputStream("Scenes/scene.scn");
            ObjectInputStream ois = new ObjectInputStream(fis);

            scene=(ArrayList<Primitive>)ois.readObject();

            ois.close();
            fis.close();
            return scene;
        }
        catch (IOException | ClassNotFoundException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

}

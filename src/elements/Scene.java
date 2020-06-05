package elements;

import materials.Dielectric;
import materials.Lambertian;
import materials.Metal;
import math.Primitive;
import math.Sphere;
import math.Vec3;

import java.util.ArrayList;

public class Scene {


    public static ArrayList<Primitive> generateScene(int i){
        ArrayList<Primitive> geometry = new ArrayList<>();

        switch(i){
            case 1:
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new Vec3(0.8,0.8,0.8),0.2)));

                return geometry;

            case 2:
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new Vec3(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Metal(new Vec3(1,0.2,0.2),0.4)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new Vec3(0.8,0.8,0.8),0.2)));

                return geometry;

            case 3:
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Dielectric(1.7)));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new Vec3(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Dielectric(1.7)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new Vec3(0.8,0.8,0.8),0.2)));

                return geometry;

            case 4:
                //Scene with blue ball and one dielectric
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.1,0.2,0.5))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Dielectric(1.5)));

                return geometry;

            case 5:
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.8,0.3,0.3))));
                geometry.add(new Sphere(new Vec3(0.45,-0.4,-0.7),0.1,new Metal(new Vec3(0.8,0.8,0.8),0)));
                geometry.add(new Sphere(new Vec3(-0.45,-0.4,-0.7),0.1,new Dielectric(1.5)));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0.9)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Metal(new Vec3(0.8,0.8,0.8),0.2)));

                return geometry;

            case 6:
                //Scene with buble
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.1,0.2,0.5))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.8,0.8,0))));
                geometry.add(new Sphere(new Vec3(1,0,-1),0.5,new Metal(new Vec3(0.8,0.6,0.2),0)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),0.5,new Dielectric(1.5)));
                geometry.add(new Sphere(new Vec3(-1,0,-1),-0.49,new Dielectric(1.5)));

                return geometry;

            default:
                geometry.add(new Sphere(new Vec3(0,0,-1),0.5,new Lambertian(new Vec3(0.6,0.6,0.6))));
                geometry.add(new Sphere(new Vec3(0,-100.5,-1),100,new Lambertian(new Vec3(0.6,0.6,0.6))));

                return geometry;
        }
    }
}

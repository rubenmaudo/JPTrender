package maths;

import geometry.Hit_record;
import geometry.Primitive;
import geometry.Sphere;
import materials.Lambertian;

import java.util.ArrayList;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Atmosphere {
    final Vec3 betaR = new Vec3(3.8e-6,15.5e-6,33.1e-6);
    final Vec3 betaM = new Vec3(21e-6);
    double Er = 6360e3;//in m
    double Ar = 6420e3;
    double Hr = 7994;
    double Hm = 1200;
    Vec3 sunDirection;
    ArrayList<Primitive> worldList=new ArrayList<>();
    ArrayList<Primitive> atmosphereList=new ArrayList<>();

    public Atmosphere(Vec3 sunDirection) {

        this.sunDirection=sunDirection;

        Sphere earth=new Sphere(new Vec3(0, 0, 0), 6360e3, new Lambertian(new ColorValue(0,0,0)));
        worldList.add(earth);

        Sphere atmosphere=new Sphere(new Vec3(0, 0, 0), 6420e3, new Lambertian(new ColorValue(0,0,0)));
        worldList.add(atmosphere);
        atmosphereList.add(atmosphere);
    }

    public ColorValue computeIncidentLight(Ray ray){

        //Ray newRay = new Ray(new Vec3(ray.origin().x(),ray.origin().y()+Er,ray.origin().z()),ray.direction());

        int numbSamples=16;
        int numbSamplesLight=8;
        double tCurrent= 0;
        Vec3 sumR=new Vec3(0,0,0); //Rayleigh contribution
        Vec3 sumM=new Vec3(0,0,0); //Mie contribution
        double opticalDepthR=0;
        double opticalDepthM=0;
        double phaseR;
        double phaseM;



        Hittable hitCheck=new Hittable(worldList);
        if(!hitCheck.hit(ray,0, Double.MAX_VALUE,new Hit_record())){
            return new ColorValue(1,1,1);
        }else{

            double segmentLength=hitCheck.temp_rec.t/(double)numbSamples;
            double mu=ray.direction().dotProduct(sunDirection);
            phaseR = 3./(16. * Utils.PI) * (1. + (mu * mu));
            double g = 0.76;
            phaseM = 3. / (8. * Utils.PI) * ((1. - g * g) * (1. + mu * mu)) / ((2. + g * g) * pow(1. + g * g - 2. * g * mu, 1.5));

            for(int i=0; i<numbSamples;++i){
                Vec3 samplePosition = ray.origin().add(ray.direction().product(tCurrent + segmentLength * 0.5));
                double height=samplePosition.length()- Er;

                //compute optical depth for light
                double hr = exp(-height / Hr) * segmentLength;
                double hm = exp(-height / Hm) * segmentLength;
                opticalDepthR += hr;
                opticalDepthM += hm;

                //light optical depth
                double t0Light=0;
                double t1Light=0;

                Hittable atmosphereHitCheck=new Hittable(atmosphereList);
                atmosphereHitCheck.hit(new Ray(samplePosition,sunDirection),0,Double.MAX_VALUE,new Hit_record());
                double segmentLengthLight=atmosphereHitCheck.temp_rec.t / (double)numbSamplesLight;
                double tCurrentLight = 0;

                double opticalDepthLightR = 0;
                double opticalDepthLightM = 0;

                int j;

                for(j=0;j<numbSamplesLight;++j){
                    Vec3 samplePositionLight = samplePosition.add(sunDirection.product(tCurrentLight + segmentLengthLight * 0.5));
                    double heightLight = samplePositionLight.squared_length() - Er;

                    if (heightLight < 0) return new ColorValue(1,0,0);

                    opticalDepthLightR += exp(-heightLight/Hr) * segmentLengthLight;
                    opticalDepthLightM += exp(-heightLight/Hm) * segmentLengthLight;
                    tCurrentLight += segmentLengthLight;
                }

                if (j==numbSamplesLight){
                    Vec3 tau = betaR.product(opticalDepthR+opticalDepthLightR).add(betaM.product(1.1*(opticalDepthM+opticalDepthLightM)));
                    Vec3 attenuation = new Vec3(exp(-tau.x()),exp(-tau.y()),exp(-tau.z()));

                    sumR = sumR.add(attenuation.product(hr));
                    sumM = sumM.add(attenuation.product(hr));
                }

                tCurrent += segmentLength;
            }
        }

        Vec3 finalVecR = sumR.product(betaR).product(phaseR);
        Vec3 finalVecM = sumM.product(betaM).product(phaseM);
        Vec3 finalVecColour = finalVecR.add(finalVecM).product(20);
        System.out.println(finalVecColour);

        return new ColorValue(finalVecColour.x(),finalVecColour.y(),finalVecColour.z());

    }
}

package maths;

public class Atmosphere {
    final Vec3 betaR = new Vec3(3.8e-6,15.5e-6,33.1e-6);
    final Vec3 betaM = new Vec3(21e-6);
    double er = 6360e3;
    double ar = 6420e3;
    double hr = 7994;
    double hm = 1200;

    public Atmosphere() {
    }

    public void computeIncidentLight(Vec3 origin, Vec3 direction, double tmin, double tmax){

    }
}

package maths.Pdf;

import maths.Vec3;

public class Mixture_pdf extends Pdf{

    Pdf p0;
    Pdf p1;

    public Mixture_pdf(Pdf p0, Pdf p1) {
        this.p0=p0;
        this.p1=p1;
    }

    @Override
    public double value(Vec3 direction) {
        return (0.5*p0.value(direction))+(0.5*p1.value(direction));
    }

    public Vec3 generate(){
        if(Vec3.random_double(0,1)<0.5){
            return p0.generate();
        }else{
            return p1.generate();
        }
    }

}

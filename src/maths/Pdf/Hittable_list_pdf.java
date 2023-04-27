package maths.Pdf;

import geometry.Primitive;
import maths.Vec3;

import java.util.ArrayList;

public class Hittable_list_pdf extends Pdf{

    ArrayList<Primitive> listSampler;
    Vec3 o;


    public Hittable_list_pdf(ArrayList<Primitive> listSampler,Vec3 origin) {
        this.listSampler=listSampler;
        this.o=origin;
    }

    @Override
    public double value(Vec3 direction) {
        double weight= 1.0 / listSampler.size();
        double sum=0.0;

        for(Primitive p : listSampler){
            sum+= weight*p.pdf_value(o,direction);
        }
        return sum;
    }

    @Override
    public Vec3 generate() {
        int int_size=listSampler.size();
        if(int_size>1){
            return listSampler.get(Vec3.random_int(0,int_size-1)).random(o);
        }
        if(int_size==1){
            return listSampler.get(0).random(o);
        }
        return null;
    }
}

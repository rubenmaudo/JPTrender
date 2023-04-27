package maths.Pdf;

import geometry.Primitive;
import maths.Vec3;

public class Primitive_pdf extends Pdf{
    Vec3 o;
    Primitive ptr;

    public Primitive_pdf(Primitive p, Vec3 origin) {
        this.ptr=p;
        this.o=origin;
    }

    public double value(Vec3 direction){
        return ptr.pdf_value(o, direction);
    }

    public Vec3 generate(){
        return ptr.random(o);
    }


}

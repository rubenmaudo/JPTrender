package maths;

public class Cosine_pdf extends Pdf{

    Onb uvw;

    public Cosine_pdf(Vec3 w) {
        uvw=new Onb();
        uvw.build_from_w(w);
    }

    public double value(Vec3 direction){
        double cosine = direction.normalize().dotProduct(uvw.w());
        return cosine <= 0 ? 0 : (cosine/Utils.PI);
    }

    public Vec3 generate(){
        return uvw.local(Vec3.random_cosine_direction());
    }
}

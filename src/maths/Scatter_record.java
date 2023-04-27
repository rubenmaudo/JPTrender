package maths;

import maths.Pdf.Pdf;
import maths.Pdf.Primitive_pdf;

public class Scatter_record {

    Ray specular_ray;
    boolean is_specular;
    ColorValue attenuation;
    Pdf pdf_ptr;


    public void setSpecular_ray(Ray specular_ray) {
        this.specular_ray = specular_ray;
    }

    public void setIs_specular(boolean is_specular) {
        this.is_specular = is_specular;
    }

    public void setAttenuation(ColorValue attenuation) {
        this.attenuation = attenuation;
    }

    public void setPdf_ptr(Pdf pdf_ptr) {
        this.pdf_ptr = pdf_ptr;
    }
}

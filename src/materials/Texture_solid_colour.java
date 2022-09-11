package materials;

import maths.ColorValue;
import maths.Vec3;

public class Texture_solid_colour extends Texture{

    ColorValue solidColour;

    public Texture_solid_colour(ColorValue solidColour) {
        this.solidColour = solidColour;
    }

    public Texture_solid_colour(double R, double G, double B) {
        this.solidColour = new ColorValue(R,G,B);
    }

    @Override
    public ColorValue getColourValue(double u, double v, Vec3 p) {
        return solidColour;
    }
}

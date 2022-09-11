package materials;

import maths.ColorValue;
import maths.Vec3;

/*
 * Abstract class to be extended in all the textures to be used
 */
public abstract class Texture{
    public abstract ColorValue getColourValue(double u, double v, Vec3 p);
}

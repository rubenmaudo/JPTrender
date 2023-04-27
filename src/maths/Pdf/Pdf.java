package maths.Pdf;

import maths.Vec3;

public abstract class Pdf {

    public abstract double value(Vec3 direction);

    public abstract Vec3 generate();
}

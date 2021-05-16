package maths;

import java.awt.*;

/**
 * @author : Ruben Maudo
 * @since : 10/05/2021
 **/
public class Background {
    private ColorValue mainColor;
    private ColorValue secondaryColor;
    private Boolean mixed;

    /**
     * Constructor to create a background
     */
    public Background() {
        mainColor=new ColorValue(0.6,0.745098039,1);
        secondaryColor=new ColorValue(1,0.78431372,0);
        mixed=false;
    }

    public ColorValue getMainColor() {
        return mainColor;
    }

    public void setMainColor(ColorValue mainColor) {
        this.mainColor = mainColor;
    }

    public ColorValue getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(ColorValue secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public Boolean getMixed() {
        return mixed;
    }

    public void setMixed(Boolean mixed) {
        this.mixed = mixed;
    }

    public static ColorValue fromColorToColorValue(Color color){
        return new ColorValue((double)color.getRed()/(double)255,
                (double)color.getGreen()/(double)255,(double)color.getBlue()/(double)255);
    }
}

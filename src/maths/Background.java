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
        this.mainColor=new ColorValue(0.6,0.745098039,1);
        this.secondaryColor=new ColorValue(1,0.78431372,0);
        this.mixed=false;
    }

    public Background(ColorValue mainColor, ColorValue secondaryColor, Boolean mixed) {
        this.mainColor=mainColor;
        this.secondaryColor=secondaryColor;
        this.mixed=mixed;
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



    public static Background clone(Background background){
        return new Background(background.getMainColor(),
                background.getSecondaryColor(),
                background.getMixed());
    }
}

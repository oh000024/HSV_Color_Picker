package model;

import java.util.Observable;

/**
 * The model holds the data.
 *
 * Model color.
 * Based on red, green, blue and alpha (transparency).
 *
 * RGB: integer values in the domain range of 0 to 255 (inclusive).
 * Alpha: integer value in the domain range of 0 to 255 (inclusive).
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 * @version 1.0
 */
public class HSVModel extends Observable {

    // CLASS VARIABLES
    public static final Integer MAX_ALPHA = 255;
    public static final Integer MAX_RGB   = 255;
    public static final Integer MIN_ALPHA = 0;
    public static final float MAX_HUE   = 359.0f;
    public static final float MIN_HSV   = 0.0f;
    public static final float MAX_SATURATION   = 100.0f;
    public static final float MAX_BRIGHTNESS   = 100.0f;

    // INSTANCE VARIABLES
//    private Integer alpha;
    private float hue;
    private float saturation;
    private float brightness;

    /**
     * No argument constructor.
     *
     * Instantiate a new instance of this class, and
     * initialize red, green, blue, and alpha to max values.
     */
    public HSVModel() {
        this( MIN_HSV, MIN_HSV, MIN_HSV );
    }

    /**
     * Convenience constructor.
     *
     * @param hue - starting hue value
     * @param saturation - starting saturation value
     * @param brightness - starting brightness value
     */
    public HSVModel(Float hue, Float saturation, Float brightness) {
        super();

//        this.alpha = alpha;
        this.hue  = hue;
        this.saturation = saturation;
        this.brightness   = brightness;
    }

    public void asBlack()   { this.setHSV( getHue(), getSaturation(), getBrightness() ); }
//    public void asBlue()    { this.setHSV( MIN_RGB, MIN_RGB, MAX_RGB ); }
//    public void asCyan()    { this.setHSV( MIN_RGB, MAX_RGB, MAX_RGB ); }
//    public void asGreen()   { this.setHSV( MIN_RGB, MAX_RGB, MIN_RGB ); }
//    public void asMagenta() { this.setHSV( MAX_RGB, MIN_RGB, MAX_RGB ); }
//    public void asRed()     { this.setHSV( MAX_RGB, MIN_RGB, MIN_RGB ); }
//    public void asWhite()   { this.setHSV( MAX_RGB, MAX_RGB, MAX_RGB ); }
//    public void asYellow()  { this.setHSV( MAX_RGB, MAX_RGB, MIN_RGB ); }

    // GETTERS
//    public Integer getAlpha() { return alpha; }
    public float getHue()  { return hue; }
//    public int     getColor() { return Color.argb( alpha, red, green, blue ); }
    public float getSaturation() { return saturation; }
    public float getBrightness()   { return brightness; }

    // SETTERS
//    public void setAlpha( Integer alpha ) {
//        this.alpha = alpha;
//
//        this.updateObservers();
//    }

    public void setHue(float hue) {
        this.hue = hue;

        this.updateObservers();
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;

        this.updateObservers();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        this.updateObservers();
    }

    // Convenient setter: set H, S, B
    public void setHSV( Float hue, Float saturation, Float brightness ) {
        this.hue   = hue;
        this.saturation = saturation;
        this.brightness  = brightness;

        this.updateObservers();
    }

    // the model has changed!
    // broadcast the update method to all registered observers
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public String toString() {
        return "HSV [r=" + hue + " g=" + saturation + " b=" + brightness + "]";
    }

    // Proof that my model is independent of any view.
    public static void main( String[] args ) {
        HSVModel model = new HSVModel( 127.0f, 127.0f, 127.0f );

        System.out.println( model );
    }
}
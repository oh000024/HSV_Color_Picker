package model;

import java.util.Observable;

/**
 * The model holds the data.
 *
 * Model color.
 * Based on Hue, Saturation, Brigntness.
 *
 * @author Jake Oh. oh000024@AlgonquinCollegelive.com
 * @version 1.0
 */
public class HSVModel extends Observable {

    // CLASS VARIABLES
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

        this.hue  = hue;
        this.saturation = saturation;
        this.brightness   = brightness;
    }

//    public void asBlack()   { this.setHSV( getHue(), getSaturation(), getBrightness() ); }

    // GETTERS
    public float getHue()  { return hue; }
    public float getSaturation() { return saturation; }
    public float getBrightness()   { return brightness; }

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
        return "HSV [h=" + hue + " s=" + saturation + " b=" + brightness + "]";
    }

    // Proof that my model is independent of any view.
    public static void main( String[] args ) {
        HSVModel model = new HSVModel( 127.0f, 127.0f, 127.0f );

        System.out.println( model );
    }
}
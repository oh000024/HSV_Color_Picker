package mad9132.hsvcolorpicker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import model.HSVModel;

/**
 * The Controller for HSV Color Picker .
 * <p>
 * As the Controller:
 * a) event handler for the View
 * b) observer of the Model (HSVModel)
 * <p>
 * Features the Update / React Strategy.
 *
 * @author Jake Oh. oh000024@AlgonquinCollegelive.com
 * @version 1.0
 */
public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {
    // CLASS VARIABLES
    private static final String ABOUT_DIALOG_TAG = "About";
    private static final String LOG_TAG = "RGBA";
    private static final String APPNAME = "HSV COLOR PICKER";
    private static final String HKEY = "H";
    private static final String SKEY = "S";
    private static final String BKEY = "B";

    private static int appState = 0;

    // INSTANCE VARIABLES
    // Pro-tip: different naming style; the 'm' means 'member'
    private AboutDialogFragment mAboutDialog;
    private TextView mColorSwatch;
    private HSVModel mModel;
    private SeekBar mHueSB;
    //
    private SeekBar mSaturationSB;
    private SeekBar mBrightnessSB;

//
    private TextView mHueTV;
    private TextView mSaturationTV;
    private TextView mBrightnessTV;
    private SharedPreferences userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences( getResources().getString(R.string.app_name), Context.MODE_PRIVATE );
        // Instantiate a new AboutDialogFragment()
        // but do not show it (yet)
        mAboutDialog = new AboutDialogFragment();

        // Instantiate a new HSV model
        // Initialize the model hue (0), saturation (0), value (0)
//        mModel = new HSVModel();
        mModel = new HSVModel( settings.getFloat("hue",   0.0f),
                settings.getFloat("saturation", 0.0f),
                settings.getFloat("bright",  0.0f) );
//
//        userDetails = getApplicationContext().getSharedPreferences(APPNAME, MODE_PRIVATE);
//        if (appState != 0) {
//            int rValue = userDetails.getInt("RED", HSVModel.MAX_RGB);
//            mModel.setRed(rValue);
//            int gValue = userDetails.getInt("GREEN", HSVModel.MIN_RGB);
//            mModel.setGreen(gValue);
//            int bValue = userDetails.getInt("BLUE", HSVModel.MIN_RGB);
//            mModel.setBlue(bValue);
//            int aValue = userDetails.getInt("ALPAH", HSVModel.MAX_ALPHA);
//            mModel.setAlpha(aValue);
//        } else {
//            SharedPreferences.Editor edit = userDetails.edit();
//            edit.clear();
//            appState = 1;
//            mModel.setHue(HSVModel.MIN_HSV);
//            mModel.setSaturation(HSVModel.MIN_HSV);
//            mModel.setBrightness(HSVModel.MIN_HSV);
//            mModel.setAlpha( HSVModel.MAX_ALPHA );
//        }
//
//        // The Model is observing this Controller (class MainActivity implements Observer)
        mModel.addObserver(this);
//
//        // reference each View
        mColorSwatch = (TextView) findViewById(R.id.colorSwatch);
        mHueSB = (SeekBar) findViewById(R.id.hueSB);
        mSaturationSB = (SeekBar) findViewById(R.id.saturationSB);
        mBrightnessSB = (SeekBar) findViewById(R.id.brightnessSB);
//        mAlphaSB = (SeekBar) findViewById( R.id.alphaSB );
//
        mHueTV = (TextView) findViewById(R.id.hue);
        mSaturationTV = (TextView) findViewById(R.id.saturation);
        mBrightnessTV = (TextView) findViewById(R.id.brightness);
//        mAlphaTV = (TextView) findViewById( R.id.alpha );
//
//        //TODO: setMax() for the remaining <SeekBar>s: green, blue and alpha(DONE)
//        // set the domain (i.e. max) for each component
        mHueSB.setMax((int)HSVModel.MAX_HUE);
        mSaturationSB.setMax((int)HSVModel.MAX_SATURATION);
        mBrightnessSB.setMax((int)HSVModel.MAX_BRIGHTNESS);
//        mAlphaSB.setMax( HSVModel.MAX_ALPHA );
//        // register the event handler for each <SeekBar>
        mHueSB.setOnSeekBarChangeListener(this);
//        //TODO: register the remaining <SeekBar>s: green, blue and alpha(DONE)
        mSaturationSB.setOnSeekBarChangeListener(this);
        mBrightnessSB.setOnSeekBarChangeListener(this);
//        mAlphaSB.setOnSeekBarChangeListener( this );
//
//
//        // initialize the View to the values of the Model
        this.updateView();

        mColorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String message = "H: " + mModel.getHue() + "°" + "\nS: " + mModel.getSaturation() + "%" + "\nV: " + mModel.getBrightness() + "%";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences( getResources().getString(R.string.app_name), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();

        editor.putFloat( "hue",   mModel.getHue() );
        editor.putFloat( "saturation",  mModel.getSaturation() );
        editor.putFloat( "brightness", mModel.getBrightness() );
//        editor.putInt( "alpha", mModel.getAlpha() );

        // Commit the edits!
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_about:
                mAboutDialog.show(getFragmentManager(), ABOUT_DIALOG_TAG);
                return true;
//
//            case R.id.action_red:
//                mModel.asRed();
//                return true;
//
//            //TODO: handle the remaining menu items(DONE)
//            case R.id.action_green:
//                mModel.asGreen();
//                return true;
//
//            case R.id.action_blue:
//                mModel.asBlue();
//                return true;
//            case R.id.action_black:
//                mModel.asBlack();
//                return true;
//            case R.id.action_cyan:
//                mModel.asCyan();
//                return true;
//            case R.id.action_magenta:
//                mModel.asMagenta();
//                return true;
//            case R.id.action_white:
//                mModel.asWhite();
//                return true;
//            case R.id.action_yellow:
//                mModel.asYellow();
//                return true;
//
//            default:
//                Toast.makeText(this, "MenuItem: " + item.getTitle(), Toast.LENGTH_LONG).show();
//                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Event handler for the <SeekBar>s: red, green, blue, and alpha.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if (fromUser == false) {
            return;
        }
//
        String keyString = "";
        Float rgbValue = 0.0f;
        // Determine which <SeekBark> caused the event (switch + case)
        // GET the SeekBar's progress, and SET the model to it's new value
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mModel.setHue((float)mHueSB.getProgress());
                mHueTV.setText(getResources().getString(R.string.hueProgress, progress).toUpperCase());
                keyString = HKEY;
                rgbValue = mModel.getHue();
                break;
//
//            //TODO: case R.id.greenSB(DONE)
            case R.id.saturationSB:
                mModel.setSaturation((float)mSaturationSB.getProgress());
                mSaturationTV.setText(getResources().getString(R.string.saturationProgress, progress).toUpperCase());
                keyString = SKEY;
                rgbValue = mModel.getSaturation();
                break;
//            //TODO: case R.id.blueSB(DONE)
            case R.id.brightnessSB:
                mModel.setBrightness((float) mBrightnessSB.getProgress());
                mBrightnessTV.setText(getResources().getString(R.string.brightnessProgress, progress).toUpperCase());
                keyString = BKEY;
                rgbValue = mModel.getBrightness();
                break;
//            //TODO: case R.id.alphaSB(DONE)
//            case R.id.alphaSB:
//                mModel.setAlpha( mAlphaSB.getProgress() );
//                mAlphaTV.setText( getResources().getString(R.string.alphaProgress, progress).toUpperCase() );
//                keyString = "ALPHA";
//                rgbValue = mModel.getAlpha();
//                break;
//
        }
//
//        userDetails = getApplicationContext().getSharedPreferences(APPNAME, MODE_PRIVATE);
//        SharedPreferences.Editor edit = userDetails.edit();
//        edit.putFloat(keyString, rgbValue);
//        edit.commit();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // No-Operation
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText(getResources().getString(R.string.hue));
                break;
            case R.id.saturationSB:
                mSaturationTV.setText(R.string.saturation);
                break;
            case R.id.brightnessSB:
                mBrightnessTV.setText(R.string.brightness);
                break;
//            case R.id.alphaSB:
//                mAlphaTV.setText(R.string.alpha );
//                break;
        }
    }

    // The Model has changed state!
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
//        Log.i( LOG_TAG, "The color (int) is: " + mModel.getColor() + "" );

        this.updateView();
    }

    private void updateColorSwatch() {
        //GET the model's r,g,b,a values, and SET the background colour of the swatch <TextView>
        mColorSwatch.setBackgroundColor(
                Color.HSVToColor(new float[]{
                        (mModel.getHue())
                        , mModel.getSaturation()
                        , (mModel.getBrightness())})
        );
    }

    private void updateBrightnessB() {
        //TODO: set the blueSB's progress to the model's blue value(DONE)
        mBrightnessSB.setProgress((int) mModel.getBrightness());
    }

    private void updateSaturationSB() {
        //TODO: set the saturationSB's progress to the model's green value(DONE)
        mSaturationSB.setProgress((int) mModel.getSaturation());
    }

    private void updateHueSB() {
        //GET the model's red value, and SET the red <SeekBar>
        mHueSB.setProgress((int) mModel.getHue());
    }

    private void updateAlphaSB() {
//        mAlphaSB.setProgress(mModel.getAlpha());
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateBrightnessB();
        this.updateAlphaSB();
    }

    public void onClick(View view) {

        Button but = (Button) view;
        float[] currentHSV = new float[3];
        ColorDrawable buttonColor = (ColorDrawable)but.getBackground();
        int colorId = buttonColor.getColor();


        Color.colorToHSV(colorId,currentHSV);

        mModel.setHue(currentHSV[0]);
        mModel.setSaturation(currentHSV[1]);
        mModel.setBrightness(currentHSV[2]);

        mModel.setHSV(currentHSV[0],currentHSV[1],currentHSV[2]);
        //updateColorSwatch();
        switch (but.getId()) {
            case R.id.blackButton:

                Toast.makeText(getApplicationContext(), "BLACK", Toast.LENGTH_SHORT).show();
                break;
            case R.id.redButton:
                Toast.makeText(getApplicationContext(), "RED", Toast.LENGTH_SHORT).show();
                break;
            case R.id.limeButton:
                Toast.makeText(getApplicationContext(), "LIME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.blueButton:
                Toast.makeText(getApplicationContext(), "BULE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yellowButton:
                Toast.makeText(getApplicationContext(), "YELLOW", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cyanButton:
                Toast.makeText(getApplicationContext(), "CYAN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.magentaButton:
                Toast.makeText(getApplicationContext(), "MAGENTA", Toast.LENGTH_SHORT).show();
                break;
            case R.id.silverButton:
                Toast.makeText(getApplicationContext(), "SILVER", Toast.LENGTH_SHORT).show();
                break;
            case R.id.grayButton:
                Toast.makeText(getApplicationContext(), "GRAY", Toast.LENGTH_SHORT).show();
                break;
            case R.id.maroonButton:
                Toast.makeText(getApplicationContext(), "MAROON", Toast.LENGTH_SHORT).show();
                break;
            case R.id.oliveButton:
                Toast.makeText(getApplicationContext(), "OLIVER", Toast.LENGTH_SHORT).show();
                break;
            case R.id.greenButton:
                Toast.makeText(getApplicationContext(), "GREEN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.purpleButton:
                Toast.makeText(getApplicationContext(), "PURPLE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tealButton:
                Toast.makeText(getApplicationContext(), "TEAL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navyButton:
                Toast.makeText(getApplicationContext(), "NANY", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}   // end of class

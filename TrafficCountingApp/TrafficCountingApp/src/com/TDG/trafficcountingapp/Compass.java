package com.TDG.trafficcountingapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Compass class was taken from http://www.javacodegeeks.com/2013/09/android-compass-code-example.html
 * Edited by Richard Fong
 * @Since 27.05.2015
 */

@SuppressLint("NewApi")
public class Compass extends Fragment implements SensorEventListener {
	
	// define the display assembly compass picture
    private ImageView image;
 
    // record the compass picture angle turned
    private float currentDegree = 0f;
 
    // device sensor manager
    private SensorManager mSensorManager;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_compass, container, false);
    	// our compass image 
    	image = (ImageView)view.findViewById(R.id.compass);
    	
    	// initialize your android device sensor capabilities
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
    	return view;
    }
 
    @Override
    public void onResume() {
        super.onResume();
         
        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
 
    @Override
    public void onPause() {
        super.onPause();
         
        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
 
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0])+90;
 
        //tvHeading.setText("Heading: " + Float.toString(degree%360) + " degrees");
 
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);
 
        // how long the animation will take place
        ra.setDuration(210);
 
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
 
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
 
    }
 
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}
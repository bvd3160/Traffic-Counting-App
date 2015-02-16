package com.TDG.trafficcountingapp;

import java.util.concurrent.TimeUnit;

import com.TDG.trafficcountingapp.CustomDialogs.Communicator;
import com.TDG.trafficcountingapp.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CountingScreen extends ActionBarActivity implements Communicator {

	/*
	 * An 'Count Object' in this project will be used as the term for the following: (Unless we can find a better name for them) 
	 * 
	 * Heavy -> Truck, Bus, Tractor?(Not sure if we count this) 
	 * Light Vehicle -> Car, Motorbike?(Not sure if we count this)
	 * Pedestrian -> Pedestrian(Normal), Bike(Only if it crosses using the crossing), Cane, Dog, Medical Aid, Scooter, Other(User needs to specify)
	 * 		Medical Aid -> Artificial Limb, Back Brace, Crutches, Leg Brace, Walking Frame, Wheel Chair,
	 * 			Back Brace -> Visible, Not Visible, 
	 * 			Leg Brace -> Visible, Not Visible,
	 * 			Wheel Chair -> Assisted, Manual, Powered,
	 */

	private static int bus, truck, car, motorBike, pedestrian, bike, cane, dog,
			mobilityScooter, artificialLimb, crutches, walkingFrame,
			backBrace_visible, backBrace_notVisible, legBrace_visible,
			legBrace_notVisible, wheelChair_assisted, wheelChair_manual,
			wheelChair_powered;

	TextView txt_totalCount;
	int totalCount;
	
	TextView txt_currentObject;
	TextView txt_timer;
	
	String comments;
	
	//Testing purposes
	Button btn_start, btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		comments = getIntent().getStringExtra("Comments");
		Toast.makeText(this, comments, Toast.LENGTH_SHORT).show();
		
		// Initialise the count as 0
		totalCount = 0;

		// Find the counter and set the text with the right count
		txt_totalCount = (TextView) findViewById(R.id.cs_txt_totalCounter);
		txt_totalCount.setText("Total Count: " + totalCount);

		// Initialises the current counting object to be a car
		txt_currentObject = (TextView) findViewById(R.id.cs_txt_currentlyselectedobject);
		txt_currentObject.setText("Car");
		
		// Initilises the Timer TextView to 15 minutes
		txt_timer = (TextView) findViewById(R.id.cs_txt_timer);
		txt_timer.setText("15:00");
		// Instantiates the CountDownTimer.
		final CountDownTimer countTimer = new CountDownTimer(900000, 1000);
		
		//Just for testing purposes we will have a start and stop button for the timer.
		btn_start = (Button) findViewById(R.id.cs_btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				countTimer.start();
			}
		});
		btn_stop = (Button) findViewById(R.id.cs_btn_stop);
		btn_stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				countTimer.cancel();
			}
		});
		showCountingPanel();
		populateButtons();
	}
	
	
	/*
	 * This method will determine what type of setup will be displayed in on the count panel
	 * based on the intersection type selected in the CountSetup.
	 */
	private void showCountingPanel() {
		String intersectionType = CountSetup.getTypeOfIntersection();
		ImageView countPanel = (ImageView) findViewById(R.id.countingPanel);
		if(intersectionType == "3 Way Intersection"){
			countPanel.setImageResource(drawable.intersection_3);
		}else if(intersectionType == "4 Way Intersection"){
			countPanel.setImageResource(drawable.intersection_4);
		}else if(intersectionType == "5 Way Intersection"){
			countPanel.setImageResource(drawable.intersection_5);
		}else if(intersectionType == "6 Way Intersection"){
			countPanel.setImageResource(drawable.intersection_6);
		}else{
			countPanel.setVisibility(8);
		}
	}

	/*
	 * This will update all the counters. 
	 * This includes the total count and the count of each count object
	 */
	private void updateCounter() {
		txt_totalCount.setText("Total Count: " + totalCount);
	}

	/*
	 * btn_increase is only here just to give it a way to increase the total count.
	 * (Will need to remove it and replace it with working directions later).
	 * 
	 * btn_undo currently only removes a value from the count. 
	 * (Will need to implement it correctly later when we get all the objects values working).
	 */
	private void populateButtons() {
		Button btn_increase = (Button) findViewById(R.id.cs_btn_increase);
		btn_increase.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				totalCount++;
				updateCounter();
			}
		});

		Button btn_undo = (Button) findViewById(R.id.cs_btn_undo);
		btn_undo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(totalCount >= 1){
					totalCount--;
				}
				updateCounter();
			}
		});
	}

	public static int getBus() {
		return bus;
	}

	public static void setBus(int bus) {
		CountingScreen.bus = bus;
	}

	public static int getTruck() {
		return truck;
	}

	public static void setTruck(int truck) {
		CountingScreen.truck = truck;
	}

	public static int getCar() {
		return car;
	}

	public static void setCar(int car) {
		CountingScreen.car = car;
	}

	public static int getMotorBike() {
		return motorBike;
	}

	public static void setMotorBike(int motorBike) {
		CountingScreen.motorBike = motorBike;
	}

	public static int getPedestrian() {
		return pedestrian;
	}

	public static void setPedestrian(int pedestrian) {
		CountingScreen.pedestrian = pedestrian;
	}

	public static int getBike() {
		return bike;
	}

	public static void setBike(int bike) {
		CountingScreen.bike = bike;
	}

	public static int getCane() {
		return cane;
	}

	public static void setCane(int cane) {
		CountingScreen.cane = cane;
	}

	public static int getDog() {
		return dog;
	}

	public static void setDog(int dog) {
		CountingScreen.dog = dog;
	}

	public static int getMobilityScooter() {
		return mobilityScooter;
	}

	public static void setMobilityScooter(int mobilityScooter) {
		CountingScreen.mobilityScooter = mobilityScooter;
	}

	public static int getArtificialLimb() {
		return artificialLimb;
	}

	public static void setArtificialLimb(int artificialLimb) {
		CountingScreen.artificialLimb = artificialLimb;
	}

	public static int getCrutches() {
		return crutches;
	}

	public static void setCrutches(int crutches) {
		CountingScreen.crutches = crutches;
	}

	public static int getWalkingFrame() {
		return walkingFrame;
	}

	public static void setWalkingFrame(int walkingFrame) {
		CountingScreen.walkingFrame = walkingFrame;
	}

	public static int getBackBrace_visible() {
		return backBrace_visible;
	}

	public static void setBackBrace_visible(int backBrace_visible) {
		CountingScreen.backBrace_visible = backBrace_visible;
	}

	public static int getBackBrace_notVisible() {
		return backBrace_notVisible;
	}

	public static void setBackBrace_notVisible(int backBrace_notVisible) {
		CountingScreen.backBrace_notVisible = backBrace_notVisible;
	}

	public static int getLegBrace_visible() {
		return legBrace_visible;
	}

	public static void setLegBrace_visible(int legBrace_visible) {
		CountingScreen.legBrace_visible = legBrace_visible;
	}

	public static int getLegBrace_notVisible() {
		return legBrace_notVisible;
	}

	public static void setLegBrace_notVisible(int legBrace_notVisible) {
		CountingScreen.legBrace_notVisible = legBrace_notVisible;
	}

	public static int getWheelChair_assisted() {
		return wheelChair_assisted;
	}

	public static void setWheelChair_assisted(int wheelChair_assisted) {
		CountingScreen.wheelChair_assisted = wheelChair_assisted;
	}

	public static int getWheelChair_manual() {
		return wheelChair_manual;
	}

	public static void setWheelChair_manual(int wheelChair_manual) {
		CountingScreen.wheelChair_manual = wheelChair_manual;
	}

	public static int getWheelChair_powered() {
		return wheelChair_powered;
	}

	public static void setWheelChair_powered(int wheelChair_powered) {
		CountingScreen.wheelChair_powered = wheelChair_powered;
	}

	/*
	 * Launches the CustomDialogs class and sends the reference "vehicleDialog"
	 * to that class to let that class know where it was called from. Then it
	 * displays the dialog which is handled in Custom_Dialogs.
	 */
	@SuppressLint("NewApi")
	public void showVehicleDialog(View view) {
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "vehicleDialog");
	}

	/*
	 * Launches the CustomDialogs class and sends the reference
	 * "pedestrianDialog" to that class to let that class know where it was
	 * called from. Then it displays the dialog which is handled in
	 * Custom_Dialogs.
	 */
	@SuppressLint("NewApi")
	public void showPedestrianDialog(View view) {
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "pedestrianDialog");
	}
	
	/*
	 * Launches the CustomDialogs class and sends the reference
	 * "commentsDialog" to that class to let that class know where it was
	 * called from. Then it displays the dialog which is handled in
	 * Custom_Dialogs.
	 */
	@SuppressLint("NewApi")
	public void showCommentsDialog(View view) {
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		Bundle args = new Bundle();
		args.putString("Comments", comments);
		dialog.setArguments(args);
		dialog.show(manager, "commentsDialog");
	}

	/*
	 * This method is from CustomDialogs' communicator interface. The message
	 * returned is the message which is sent from Custom_Dialogs. This allows us
	 * to know which button was clicked so we can make appropriate action in the
	 * CountingScreen class.
	 */
	@Override
	public void sendClickMessage(String key, String value) {

		// Checks which button was clicked by comparing the message sent from
		// Custom_Dialogs to this class.
		if (key.equals("Bus")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Truck")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Car")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Motor Bike")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Pedestrian")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Bike")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Cane")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Dog")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Mobility Scooter")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Artificial Limb")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Crutches")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Walking Frame")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Back Brace - Visible")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Back Brace - Not Visible")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Leg Brace - Visible")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Leg Brace - Not Visible")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Wheel Chair - Assisted")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Wheel Chair - Manual")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Wheel Chair - Powered")) {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		} else if (key.equals("Comment")){
			comments = value;
		} else {
			Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
		}

		// Setting the currently selected object to the object that the user chose.
		if(!key.equals("Comment")){
			txt_currentObject.setText(value);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent intent = new Intent();
	    	intent.putExtra("Comment", comments);
	    	setResult(RESULT_OK, intent);
	    	finish();
	    	return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	private class CountDownTimer extends android.os.CountDownTimer{

		public CountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			String ms = String.format("%02d:%02d", 
					TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
					TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
			txt_timer.setText(ms);
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			txt_timer.setText("Completed.");
		}
		
	}
}

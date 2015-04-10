package com.TDG.trafficcountingapp;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

import com.TDG.trafficcountingapp.CustomDialogs.Communicator;
import com.TDG.trafficcountingapp.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter.LengthFilter;
import android.text.format.DateFormat;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.ContactsContract.Directory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * @author Richard Fong 1248615
 * @version 1.0
 * @since 11 January, 2015
 */

public class CountingScreen extends ActionBarActivity implements Communicator, OnClickListener{

	/*
	 * An 'Count Object' in this project will be used as the term for the following: (Unless we can find a better name for them) 
	 * 
	 * Heavy -> Truck, Bus, Tractor?(Not sure if we count this) 
	 * Light Vehicle -> Car, Motorbike?(Not sure if we count this)
	 * Pedestrian -> Pedestrian(Normal), Bike(Only if it crosses using the crossing), Cane, Dog, Medical Aid, Scooter, Other(User needs to specify)
	 * Medical Aid -> Artificial Limb, Back Brace, Crutches, Leg Brace, Walking Frame, Wheel Chair,
	 * Back Brace -> Visible, Not Visible, 
	 * Leg Brace -> Visible, Not Visible,
	 * Wheel Chair -> Assisted, Manual, Powered,
	 */

	private static int bus, truck, car, motorBike, 
			pedestrian, crutches_1, crutches_2,
			cane, dog, mobilityScooter,
			wheelChair_assisted, wheelChair_manual, wheelChair_powered,
			pushChair, skateboard, manualScooter;

	static int northWest, north, northEast, east, west, southWest, south, southEast;
//	private String[] lastSelectedObjects = new String[3];
//	private Integer[] lastSelectedCounts = new Integer[3];
	
	TextView txt_totalCount;
	int totalCount;
	String directionFrom;
	String directionTo;
	
	TextView txt_currentObject;
	TextView txt_timer;
	TextView txt_currentObjectCount;
	
	String comments;
	String currentlySelectedObject;
	int currentlySelectedCount;
	String intersectionType;
	
	Button btn_direction_nw, btn_direction_n, btn_direction_ne, btn_direction_w,
	 btn_direction_e, btn_direction_sw, btn_direction_s, btn_direction_se;
	
	//Testing purposes
	Button btn_start, btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		comments = getIntent().getStringExtra("Comments");
		intersectionType = getIntent().getStringExtra("IntersectionType");
		if(intersectionType == null){
			intersectionType = "No Intersection";
		}
		
		// Initialises the current counting object to be a car
		updateCurrentObjectTo("Pedestrian (No Aid)");
				
		initialiseCountObjects();
		initialiseDirectionFromTo();
		populateTimer();
		populateButtons();
		showCountingPanelAndButtons();
		
	}
	
	private void initialiseCountObjects(){
		totalCount = 0;
		txt_totalCount = (TextView) findViewById(R.id.cs_txt_totalCounter);
		txt_totalCount.setText("Total Count: " + totalCount);
		
		bus = 0;
		truck = 0;
		car = 0;
		motorBike = 0;
		
		pedestrian = 0;
		crutches_1 = 0;
		crutches_2 = 0;
		
		cane = 0;
		dog = 0;
		mobilityScooter = 0;
		
		wheelChair_assisted = 0;
		wheelChair_manual = 0;
		wheelChair_powered = 0;
		
		pushChair = 0;
		skateboard = 0;
		manualScooter = 0;
		
		northWest = 0;
		north = 0;
		northEast = 0;
		west = 0;
		east = 0;
		southWest = 0;
		south = 0;
		southEast = 0;
		
//		lastSelectedCounts[1] = pedestrian;
//		lastSelectedObjects[1] = currentlySelectedObject;
//		lastSelectedCounts[2] = null;
//		lastSelectedObjects[2] = null;
//		lastSelectedCounts[3] = null;
//		lastSelectedObjects[3] = null;
		
		updateCurrentlySelectedObject(pedestrian);
	}
	
	private void initialiseDirectionFromTo(){
		directionTo = null;
		directionFrom = null;
	}
	
	private void defaultPedestrian(){
		updateCurrentObjectTo("Pedestrian (No Aid)");
		setCurrentlySelectedObject("Pedestrian (No Aid)");
		updateCurrentlySelectedObject(pedestrian);
	}
	
	private void updateCurrentObjectTo(String object){
		txt_currentObject = (TextView) findViewById(R.id.cs_txt_currentlyselectedobject);
		txt_currentObject.setText(object);
		
//		updateLastSelectedObjectCount();
		currentlySelectedObject = object;
	}
	
	/*
	 * This method updates the last 3 Count objects and stores them in an array.
	 * The newest count object is stored in the first slot of the array and the rest are shuffled along.
	 */
//	private void updateLastSelectedObjectCount(){
//		lastSelectedObjects[3] = lastSelectedObjects[2];
//		lastSelectedObjects[2] = lastSelectedObjects[1];
//		lastSelectedObjects[1] = currentlySelectedObject;
//		
//		lastSelectedCounts[3] = lastSelectedCounts[2];
//		lastSelectedCounts[2] = lastSelectedCounts[1];
//		lastSelectedCounts[1] = currentlySelectedCount;
//	}
	
	/*
	 * This method will determine what type of setup will be displayed in on the count panel
	 * based on the intersection type selected in the CountSetup.
	 * This method will also check which counting button needs to be visible depending on the type
	 * of intersection.
	 */
	private void showCountingPanelAndButtons() {
		ImageView countPanel = (ImageView) findViewById(R.id.countingPanel);
		switch (intersectionType) {
		case "3 Way Intersection":
			countPanel.setImageResource(drawable.intersection_3);			
			setVisibilityDirectionButtons(setVisibleButtons(3));
			
			break;
		case "4 Way Intersection":
			countPanel.setImageResource(drawable.intersection_4);
			setVisibilityDirectionButtons(setVisibleButtons(4));
			
			break;
		case "5 Way Intersection":
			countPanel.setImageResource(drawable.intersection_5);
			setVisibilityDirectionButtons(setVisibleButtons(5));
			
			break;
		case "6 Way Intersection":
			countPanel.setImageResource(drawable.intersection_6);
			setVisibilityDirectionButtons(setVisibleButtons(6));
			
			break;
	
		default:
			countPanel.setVisibility(8);
			setVisibilityDirectionButtons(setVisibleButtons(0));
			break;
		}
	}
	
	/*
	 * This method will do a check to see which button will need to be visible
	 * depending on which intersection was chosen.
	 * 
	 * The way this is set up is that it will follow this rule:
	 * 
	 *    0   1   2       NW   N   NE
	 *    3       4       W         E
	 *    5   6   7       SW   S   SE
	 *    
	 * The above is the number which corresponds to the layout of the button
	 * 
	 * @param intersectionWay is how many entrances/exits the intersection has.
	 */
	private boolean[] setVisibleButtons(int intersectionWay){
		boolean[] visibleButtons = new boolean [8];
		
		for (int i = 0; i < visibleButtons.length; i++) {
			visibleButtons[i] = false;
		}
		
		if(intersectionWay == 3){
			visibleButtons[3] = true;
			visibleButtons[4] = true;
			visibleButtons[6] = true;
		}else if(intersectionWay == 4){
			visibleButtons[1] = true;
			visibleButtons[3] = true;
			visibleButtons[4] = true;
			visibleButtons[6] = true;
		}else if(intersectionWay == 5){
			visibleButtons[1] = true;
			visibleButtons[3] = true;
			visibleButtons[4] = true;
			visibleButtons[6] = true;
			visibleButtons[7] = true;
		}else if(intersectionWay == 6){
			visibleButtons[0] = true;
			visibleButtons[1] = true;
			visibleButtons[2] = true;
			visibleButtons[5] = true;
			visibleButtons[6] = true;
			visibleButtons[7] = true;
		}
		
		return visibleButtons;
	}
	
	/*
	 * This method will set whether it will be visible or not.
	 * 
	 * @param visibleButtons has an array of booleans.
	 * If the value is true, the button will be visible. Otherwise, it will be not visible.
	 */
	private void setVisibilityDirectionButtons(boolean[] visibleButtons){
		if(!visibleButtons[0]){
			btn_direction_nw.setVisibility(4);
		}
		if(!visibleButtons[1]){
			btn_direction_n.setVisibility(4);
		}
		if(!visibleButtons[2]){
			btn_direction_ne.setVisibility(4);
		}
		if(!visibleButtons[3]){
			btn_direction_w.setVisibility(4);
		}
		if(!visibleButtons[4]){
			btn_direction_e.setVisibility(4);
		}
		if(!visibleButtons[5]){
			btn_direction_sw.setVisibility(4);
		}
		if(!visibleButtons[6]){
			btn_direction_s.setVisibility(4);
		}
		if(!visibleButtons[7]){
			btn_direction_se.setVisibility(4);
		}
	}

	/*
	 * This will update the Total count counter. 
	 */
	private void updateTotalCounter() {
		txt_totalCount.setText("Total Count: " + totalCount);
	}
	
	private void updateCurrentlySelectedObject(int currentObject){
		txt_currentObjectCount = (TextView) findViewById(R.id.cs_txt_currently_selected_count);
		txt_currentObjectCount.setText(currentlySelectedObject + ": " + currentObject);
	}

	/*
	 * This method will populate all the button methods
	 */
	private void populateButtons() {
		populateDirectionButtons();
		populateCounterButtons();
	}
	
	/*
	 * This method sets all the direction buttons to their correct id.
	 */
	private void populateDirectionButtons(){
		btn_direction_nw = (Button)findViewById(R.id.cs_btn_direction_nw);
		btn_direction_nw.setText("North-West (0)");
		btn_direction_nw.setOnClickListener(this);
		btn_direction_n = (Button)findViewById(R.id.cs_btn_direction_n);
		btn_direction_n.setText("North (0)");
		btn_direction_n.setOnClickListener(this);
		btn_direction_ne = (Button)findViewById(R.id.cs_btn_direction_ne);
		btn_direction_ne.setText("North-East (0)");
		btn_direction_ne.setOnClickListener(this);
		btn_direction_w = (Button)findViewById(R.id.cs_btn_direction_w);
		btn_direction_w.setText("West (0)");
		btn_direction_w.setOnClickListener(this);
		btn_direction_e = (Button)findViewById(R.id.cs_btn_direction_e);
		btn_direction_e.setText("East (0)");
		btn_direction_e.setOnClickListener(this);
		btn_direction_sw = (Button)findViewById(R.id.cs_btn_direction_sw);
		btn_direction_sw.setText("South-West (0)");
		btn_direction_sw.setOnClickListener(this);
		btn_direction_s = (Button)findViewById(R.id.cs_btn_direction_s);
		btn_direction_s.setText("South (0)");
		btn_direction_s.setOnClickListener(this);
		btn_direction_se = (Button)findViewById(R.id.cs_btn_direction_se);
		btn_direction_se.setText("South-East (0)");
		btn_direction_se.setOnClickListener(this);
	}
	
	private void populateTimer(){
		// Instantiates the CountDownTimer.
		
		/*
		 * We will count down in 0.5 seconds instead of 1 second because long is a
		 * "Rough" estimate so therefore we may skip some seconds. 
		 * eg. 00:05 in 1 seconds time maybe change to 00:03.
		 * By updating every 0.5 seconds will result in a more accurate measurement.
		 * Also, start with the time + 500 milliseconds so we can display the initial number
		 */
		
//15m	CountDownTimer countTimer = new CountDownTimer(900500, 500);
		final CountDownTimer countTimer = new CountDownTimer(5500, 500);
		
		//Just for testing purposes we will have a start and stop button for the timer.
				btn_start = (Button) findViewById(R.id.cs_btn_start);
				btn_start.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						countTimer.start();
					}
				});
				btn_stop = (Button) findViewById(R.id.cs_btn_stop);
				btn_stop.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						countTimer.cancel();
					}
				});
				
		// Initilises the Timer TextView to 15 minutes
		txt_timer = (TextView) findViewById(R.id.cs_txt_timer);
		txt_timer.setText(countTimer.toString());
	}
	
	/*
	 * btn_increase is only here just to give it a way to increase the total count.
	 * (Will need to remove it and replace it with working directions later).
	 * 
	 * btn_undo currently only removes a value from the count. 
	 * (Will need to implement it correctly later when we get all the objects values working).
	 */
	private void populateCounterButtons(){
		Button btn_undo = (Button) findViewById(R.id.cs_btn_undo);
		btn_undo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(totalCount >= 1){
					totalCount--;
					
				}
				updateTotalCounter();
			}
		});
	}
//================================================================================//
	
	public static int getBus() {
		return bus;
	}

	public String getCurrentlySelectedObject() {
		return currentlySelectedObject;
	}

	public void setCurrentlySelectedObject(String currentlySelectedObject) {
		this.currentlySelectedObject = currentlySelectedObject;
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

	public static int getCrutches_1() {
		return crutches_1;
	}

	public static void setCrutches_1(int crutches) {
		CountingScreen.crutches_1 = crutches;
	}
	
	public static int getCrutches_2() {
		return crutches_2;
	}

	public static void setCrutches_2(int crutches) {
		CountingScreen.crutches_2 = crutches;
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
	
	public static int getPushChair() {
		return pushChair;
	}

	public static void setPushChair(int pushChair) {
		CountingScreen.pushChair = pushChair;
	}

	public static int getSkateboard() {
		return skateboard;
	}

	public static void setSkateboard(int skateboard) {
		CountingScreen.skateboard = skateboard;
	}

	public static int getManualScooter() {
		return manualScooter;
	}

	public static void setManualScooter(int manualScooter) {
		CountingScreen.manualScooter = manualScooter;
	}
	
	public Integer getCurrentObjectCount(String object){
		Integer objectCount = null;
		switch (object) {
		case "Bus":
			objectCount = bus;
			break;
		case "Truck":
			objectCount = truck;
			break;
		case "Car":
			objectCount = car;
			break;
		case "Motor Bike":
			objectCount = motorBike;
			break;
		case "Pedestrian (No Aid)":
			objectCount = pedestrian;
			break;
		case "Cane (Poor Eyesight)":
			objectCount = cane;
			break;
		case "Guide Dog":
			objectCount = dog;
			break;
		case "Mobility Scooter":
			objectCount = mobilityScooter;
			break;
		case "Walking Stick / Crutch (1)":
			objectCount = crutches_1;
			break;
		case "Walking Sticks / Crutches (2)":
			objectCount = crutches_2;
			break;
		case "Wheel Chair (Assisted)":
			objectCount = wheelChair_assisted;
			break;
		case "Wheel Chair (Manual)":
			objectCount = wheelChair_manual;
			break;
		case "Wheel Chair (Powered)":
			objectCount = wheelChair_powered;
			break;
		case "Push Chair / Buggy":
			objectCount = pushChair;
			break;
		case "Skateboard":
			objectCount = skateboard;
			break;
		case "Manual Scooter":
			objectCount = manualScooter;
			break;
		default:
			break;
		}
		return objectCount;
	}
	
	//===========================================================================//

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
		if(!key.equals("Comment")){
			updateCurrentObjectTo(value);
			updateCurrentlySelectedObject(getCurrentObjectCount(value));
		}else{
			comments = value;
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
	
	@Override
	public void onClick(View v) {
		if(v.getId() == btn_direction_nw.getId()){
			checkDirectionFromAndTo(btn_direction_nw);
		}else if(v.getId() == btn_direction_n.getId()){
			checkDirectionFromAndTo(btn_direction_n);
		}else if(v.getId() == btn_direction_ne.getId()){
			checkDirectionFromAndTo(btn_direction_ne);
		}else if(v.getId() == btn_direction_w.getId()){
			checkDirectionFromAndTo(btn_direction_w);
		}else if(v.getId() == btn_direction_e.getId()){
			checkDirectionFromAndTo(btn_direction_e);
		}else if(v.getId() == btn_direction_sw.getId()){
			checkDirectionFromAndTo(btn_direction_sw);
		}else if(v.getId() == btn_direction_s.getId()){
			checkDirectionFromAndTo(btn_direction_s);
		}else if(v.getId() == btn_direction_se.getId()){
			checkDirectionFromAndTo(btn_direction_se);
		}
		
		Toast.makeText(this, "Direction From: " + directionFrom, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "Direction To: " + directionTo, Toast.LENGTH_SHORT).show();
	
		updateAllCounts();
	}
	
	/*
	 * This will check to see if the direction from and direction to is not null before continuing.
	 */
	private void checkDirectionFrom(Button button){
		if(directionTo == null && directionFrom == null){
			directionFrom = String.valueOf(button.getText());
		}
	}
	
	/*
	 * This will check if directionFrom is not null before continuing.
	 * 
	 */
	private void checkDirectionTo(Button button){
		if(directionFrom != null){
			if(!directionFrom.equals(String.valueOf(button.getText()))){
				directionTo = String.valueOf(button.getText());
			}
		}
	}
	
	/*
	 * This will check to see which method should be run.
	 * 
	 * This is done by checking if directionFrom is null.
	 */
	private void checkDirectionFromAndTo(Button button){
		if(directionFrom == null){
			checkDirectionFrom(button);
		}else{
			checkDirectionTo(button);
		}
	}
	
	/*
	 * This will increase the Total count by 1.
	 * Then run updateTotalCounter()
	 */
	private void increaseTotalCount(){
		totalCount++;
		updateTotalCounter();
	}
	
	/*
	 * Increases the individual count of the 'Count Object'.
	 * This gets the object which has been selected using the Pedestrian / Vehicle
	 * Dialog Fragment.
	 * 
	 * It compares the object name to see if they match.
	 * If they match, it will increment the individual value by 1.
	 * It will then change the text of the 'Count Object' located at the top
	 * of the counting screen with the correct text if a match was found.
	 * 
	 * Otherwise if there is no match, it will Toast "None of them match".
	 */
	private void increaseObjectCount(){
		boolean passed = false;
		switch (currentlySelectedObject) {
		case "Bus":
			bus++;
			currentlySelectedCount = bus;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + bus, Toast.LENGTH_SHORT).show();
			break;
		case "Truck":
			truck++;
			currentlySelectedCount = truck;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + truck, Toast.LENGTH_SHORT).show();
			
			break;
		case "Car":
			car++;
			currentlySelectedCount = car;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + car, Toast.LENGTH_SHORT).show();
			
			break;
		case "Motor Bike":
			motorBike++;
			currentlySelectedCount = motorBike;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + motorBike, Toast.LENGTH_SHORT).show();
			
			break;
		case "Pedestrian (No Aid)":
			pedestrian++;
			currentlySelectedCount = pedestrian;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + pedestrian, Toast.LENGTH_SHORT).show();
			
			break;
		case "Cane (Poor Eyesight)":
			cane++;
			currentlySelectedCount = cane;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + cane, Toast.LENGTH_SHORT).show();
			
			break;
		case "Guide Dog":
			dog++;
			currentlySelectedCount = dog;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + dog, Toast.LENGTH_SHORT).show();
			
			break;
		case "Mobility Scooter":
			mobilityScooter++;
			currentlySelectedCount = mobilityScooter;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + mobilityScooter, Toast.LENGTH_SHORT).show();
			
			break;
		case "Walking Stick / Crutch (1)":
			crutches_1++;
			currentlySelectedCount = crutches_1;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + crutches, Toast.LENGTH_SHORT).show();
			
			break;
		case "Walking Sticks / Crutches (2)":
			crutches_2++;
			currentlySelectedCount = crutches_2;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + crutches, Toast.LENGTH_SHORT).show();
			
			break;
		case "Wheel Chair (Assisted)":
			wheelChair_assisted++;
			currentlySelectedCount = wheelChair_assisted;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_assisted, Toast.LENGTH_SHORT).show();
			
			break;
		case "Wheel Chair (Manual)":
			wheelChair_manual++;
			currentlySelectedCount = wheelChair_manual;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_manual, Toast.LENGTH_SHORT).show();
			
			break;
		case "Wheel Chair (Powered)":
			wheelChair_powered++;
			currentlySelectedCount = wheelChair_powered;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_powered, Toast.LENGTH_SHORT).show();
			
			break;
		case "Push Chair / Buggy":
			pushChair++;
			currentlySelectedCount = pushChair;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_powered, Toast.LENGTH_SHORT).show();
			
			break;
		case "Skateboard":
			skateboard++;
			currentlySelectedCount = skateboard;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_powered, Toast.LENGTH_SHORT).show();
			
			break;
		case "Manual Scooter":
			manualScooter++;
			currentlySelectedCount = manualScooter;
			passed = true;
//			Toast.makeText(this, currentlySelectedObject + ": " + wheelChair_powered, Toast.LENGTH_SHORT).show();
			
			break;
		default:
			Toast.makeText(this, "None of them match", Toast.LENGTH_SHORT).show();
			break;
		}
		
		if(passed){
			Toast.makeText(this, currentlySelectedObject + ": " + currentlySelectedCount, Toast.LENGTH_SHORT).show();
			updateCurrentlySelectedObject(currentlySelectedCount);
		}
	}
	
	/*
	 * This method is used to increment the value of where the pedestrian/vehicle went.
	 */
	private void updateDirectionCount(){
		if(directionTo.contains("North-West")){
			northWest++;
			Toast.makeText(this, "North-West: " + northWest, Toast.LENGTH_SHORT).show();
			btn_direction_nw.setText("North-West (" + northWest + ")");
		}else if(directionTo.contains("North-East")){
			northEast++;
			Toast.makeText(this, "North-East: " + northEast, Toast.LENGTH_SHORT).show();
			btn_direction_ne.setText("North-East (" + northEast + ")");
		}else if(directionTo.contains("South-West")){
			southWest++;
			Toast.makeText(this, "South-West: " + southWest, Toast.LENGTH_SHORT).show();
			btn_direction_sw.setText("South-West (" + southWest + ")");
		}else if(directionTo.contains("South-East")){
			southEast++;
			Toast.makeText(this, "South-East: " + southEast, Toast.LENGTH_SHORT).show();
			btn_direction_se.setText("South-East (" + southEast + ")");
		}else if(directionTo.contains("North")){
			north++;
			Toast.makeText(this, "North: " + north, Toast.LENGTH_SHORT).show();
			btn_direction_n.setText("North (" + north + ")");
		}else if(directionTo.contains("East")){
			east++;
			Toast.makeText(this, "East: " + east, Toast.LENGTH_SHORT).show();
			btn_direction_e.setText("East (" + east + ")");
		}else if(directionTo.contains("South")){
			south++;
			Toast.makeText(this, "South: " + south, Toast.LENGTH_SHORT).show();
			btn_direction_s.setText("South (" + south + ")");
		}else if(directionTo.contains("West")){
			west++;
			Toast.makeText(this, "West: " + west, Toast.LENGTH_SHORT).show();
			btn_direction_w.setText("West (" + west + ")");
		}
	}
	
	/*
	 * This method checks to make sure that there is a value in direction from and direction to.
	 * If this is true. It will begin to update all the counts then default the current selected object back to pedestrian.
	 */
	private void updateAllCounts(){
		if(directionFrom != null && directionTo != null){
			increaseObjectCount();
			increaseTotalCount();
			updateDirectionCount();
			initialiseDirectionFromTo();
			defaultPedestrian();
		}
	}
	
	/*
	 * This class will handle the 15 minute countdown and subsequently save the data. 
	 * @author Richard and Jean-Yves
	 */
	
	private class CountDownTimer extends android.os.CountDownTimer{
		
		private String ms, minutes, seconds;
		
		public CountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			updateTimeRemaining(millisInFuture);
		}

		private void updateTimeRemaining(long millisUntilFinished){
			minutes = "" + (millisUntilFinished / 60000);
			if(minutes.length() < 2){
				minutes = "0" + minutes;
			}
			
			seconds = "" + (millisUntilFinished % 60000 / 1000);
			if(seconds.length() < 2){
				seconds = "0" + seconds;
			}
			
			ms = minutes + ":" + seconds;
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			updateTimeRemaining(millisUntilFinished);
			if(millisUntilFinished <= 1000){
				onFinish();
				/* The best place to put the saveData() method is here as putting it in 
				 * onFinish() saved the data twice every instance. -Jean-Yves */
				saveData();
			}else{
				txt_timer.setText(ms);
			}
		}

		@Override
		public void onFinish() {
			txt_timer.setText("Completed.");
		}
		
		
		
		/**
		 * This method will save data every 15 minutes
		 * @author Jean-Yves
		 * @since 30/03/2015
		 * 
		 * For Date before Filename: We decided YYYY.MM.DD for organization.
		 * if we go with date, month and then year we be mixed up when sorted by name.
		 */
		private void saveData(){
			String fileDate = CountSetup.updateDate();
			String currentDate = CountSetup.getCurrentDate();
			
			File folder = Environment.getExternalStoragePublicDirectory("TDG_DATA");
			String file = fileDate+"_Traffic_Count_Data.csv";
			File csvfile = new File(folder, file);
				try {
					if(!folder.exists()){
						//Make the directory
						folder.mkdir();
						//Create the file to be written to
						FileWriter writer = new FileWriter(csvfile, true);
						//Write the header for all data counts.
						writeDataHeader(writer, currentDate);
						flushAndCloseWriter(writer);
					}else if(folder.exists()){
						//Open the file to be written to. Re-initialization is necessary in this case.
						FileWriter writer = new FileWriter(csvfile, true);
						//Write data entry.
						if(intersectionType == "3 Way Intersection"){
							flushAndCloseWriter(writer);
							userMessage("Data save complete");
						}else if(intersectionType == "4 Way Intersection"){
							flushAndCloseWriter(writer);
							userMessage("Data save complete");
						}else if(intersectionType == "5 Way Intersection"){
							flushAndCloseWriter(writer);
							userMessage("Data save complete");
						}else if(intersectionType == "6 Way Intersection"){
							flushAndCloseWriter(writer);
							userMessage("Data save complete");
						}else{
							
						}
					}else{
						userMessage("Please mount your SD card");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		
		/*
		 * Header for every data count. This will always look the same.
		 * @author: Jean-Yves
		 * @since: 2 April 2015
		 */
		public void writeDataHeader(FileWriter fileWriter, String currentDate) throws IOException{
			//These should work, once we make sure the submit button sets the values from Textviews
			String streetNumandName = CountSetup.getStreetNumAndName();
			String suburbName = CountSetup.getSuburbName();
			String city = CountSetup.getCityName();
			String postCode = CountSetup.getAreaCode();
			String locDescription = CountSetup.getAreaDescript();
			//Make sure the above string don't return null
			
			//The header will look like below:
			/*
			 * Location: 17 Example street, Ohio Town, Las Noches, 0600
			 * Near the town Hall
			 * 
			 * Date: 17/12/2017
			 * TIME, 			CARS, 	BUSES, 	TRUCKS, 	MOTORCYCLES
			 * 8:00 to 8:15		25		2		13			7
			 */
			fileWriter.append("Location: " + streetNumandName + 
					", " + suburbName + ", "+ city +", " + postCode + ", " + "\n" + locDescription +
					"\n \n");
			fileWriter.append("Date: " + currentDate + "\n");
			fileWriter.append("Time, Cars, Buses, Trucks, Motorcycles");
			flushAndCloseWriter(fileWriter);
		}
		
		//To flush and subsequently close the opened file writer. -Jean-Yves
		public void flushAndCloseWriter(FileWriter writer) throws IOException{
			writer.flush();
			writer.close();
		}
		
		//Show the user a message. -Jean-Yves
		public void userMessage(String message){
			Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();
		}

		@Override
		public String toString() {
			return ms;
		}
	}
}

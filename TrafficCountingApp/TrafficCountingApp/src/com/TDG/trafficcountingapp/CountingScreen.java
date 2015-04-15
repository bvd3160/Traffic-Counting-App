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
import java.text.SimpleDateFormat;
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

	static int northWestTotal, northTotal, northEastTotal, eastTotal, westTotal, southWestTotal, southTotal, southEastTotal;
	static int carNorthWest, carNorth, carNorthEast, carEast, carWest, carSouthWest, carSouth, carSouthEast;
	static int busNorthWest, busNorth, busNorthEast, busEast, busWest, busSouthWest, busSouth, busSouthEast;
	static int truckNorthWest, truckNorth, truckNorthEast, truckEast, truckWest, truckSouthWest, truckSouth, truckSouthEast;
	static int motorbikeNorthWest, motorbikeNorth, motorbikeNorthEast, motorbikeEast, motorbikeWest, motorbikeSouthWest,
				motorbikeSouth, motorbikeSouthEast;
	static int pedestrianNorthWest, pedestrianNorth, pedestrianNorthEast, pedestrianEast, pedestrianWest, pedestrianSouthWest,
				pedestrianSouth, pedestrianSouthEast;
	static int crutches1NorthWest, crutches1North, crutches1NorthEast, crutches1East, crutches1West, crutches1SouthWest,
				crutches1South, crutches1SouthEast;
	static int crutches2NorthWest, crutches2North, crutches2NorthEast, crutches2East, crutches2West, crutches2SouthWest,
				crutches2South, crutches2SouthEast;
	static int caneNorthWest, caneNorth, caneNorthEast, caneEast, caneWest, caneSouthWest, caneSouth, caneSouthEast;
	static int dogNorthWest, dogNorth, dogNorthEast, dogEast, dogWest, dogSouthWest, dogSouth, dogSouthEast;
	static int mobilityscooterNorthWest, mobilityscooterNorth, mobilityscooterNorthEast, mobilityscooterEast,
				mobilityscooterWest, mobilityscooterSouthWest, mobilityscooterSouth, mobilityscooterSouthEast;
	static int wheelchair_assistedNorthWest, wheelchair_assistedNorth, wheelchair_assistedNorthEast, wheelchair_assistedEast,
				wheelchair_assistedWest, wheelchair_assistedSouthWest, wheelchair_assistedSouth, wheelchair_assistedSouthEast;
	static int wheelchair_manualNorthWest, wheelchair_manualNorth, wheelchair_manualNorthEast, wheelchair_manualEast,
				wheelchair_manualWest, wheelchair_manualSouthWest, wheelchair_manualSouth, wheelchair_manualSouthEast;
	static int wheelchair_poweredNorthWest, wheelchair_poweredNorth, wheelchair_poweredNorthEast, wheelchair_poweredEast,
				wheelchair_poweredWest, wheelchair_poweredSouthWest, wheelchair_poweredSouth, wheelchair_poweredSouthEast;
	static int pushchairNorthWest, pushchairNorth, pushchairNorthEast, pushchairEast, pushchairWest, pushchairSouthWest,
				pushchairSouth, pushchairSouthEast;
	static int skateboardNorthWest, skateboardNorth, skateboardNorthEast, skateboardEast, skateboardWest, skateboardSouthWest,
				skateboardSouth, skateboardSouthEast;
	static int manualscooterNorthWest, manualscooterNorth, manualscooterNorthEast, manualscooterEast, manualscooterWest, manualscooterSouthWest,
				manualscooterSouth, manualscooterSouthEast;
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
		
		initialiseBus();
		initialiseCar();
		initialiseTruck();
		initialiseMotorBike();
		
		initialisePedestrian();
		initialiseCrutches();
		initialiseCane();
		initialiseDog();
		initialisePushchair();
		initialiseScooter();
		initialiseWheelchair();
		initialiseSkateboard();
		initialiseDirectionTotal();
		
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
	
	private void initialiseBus(){
		bus = 0;
		busNorthWest = 0;
		busNorth = 0;
		busNorthEast = 0;
		busEast = 0;
		busWest = 0;
		busSouthWest = 0;
		busSouth = 0;
		busSouthEast = 0;
	}
	
	private void initialiseCar(){
		car = 0;
		carNorthWest = 0;
		carNorth = 0;
		carNorthEast = 0;
		carEast = 0;
		carWest = 0;
		carSouthWest = 0;
		carSouth = 0;
		carSouthEast = 0;
	}

	private void initialiseTruck(){
		truck = 0;
		truckNorthWest = 0;
		truckNorth = 0;
		truckNorthEast = 0;
		truckEast = 0;
		truckWest = 0;
		truckSouthWest = 0;
		truckSouth = 0;
		truckSouthEast = 0;
	}

	private void initialiseMotorBike(){
		motorBike = 0;
		motorbikeNorthWest = 0;
		motorbikeNorth = 0;
		motorbikeNorthEast = 0;
		motorbikeEast = 0;
		motorbikeWest = 0;
		motorbikeSouthWest = 0;
		motorbikeSouth = 0;
		motorbikeSouthEast = 0;
	}

	private void initialisePedestrian(){
		pedestrian = 0;
		pedestrianNorthWest = 0;
		pedestrianNorth = 0;
		pedestrianNorthEast = 0;
		pedestrianEast = 0;
		pedestrianWest = 0;
		pedestrianSouthWest = 0;
		pedestrianSouth = 0;
		pedestrianSouthEast = 0;
	}

	private void initialiseCrutches(){
		crutches_1 = 0;
		crutches1NorthWest = 0;
		crutches1North = 0;
		crutches1NorthEast = 0;
		crutches1East = 0;
		crutches1West = 0;
		crutches1SouthWest = 0;
		crutches1South = 0;
		crutches1SouthEast = 0;
		
		crutches_2 = 0;
		crutches2NorthWest = 0;
		crutches2North = 0;
		crutches2NorthEast = 0;
		crutches2East = 0;
		crutches2West = 0;
		crutches2SouthWest = 0;
		crutches2South = 0;
		crutches2SouthEast = 0;
	}

	private void initialiseCane(){
		cane = 0;
		caneNorthWest = 0;
		caneNorth = 0;
		caneNorthEast = 0;
		caneEast = 0;
		caneWest = 0;
		caneSouthWest = 0;
		caneSouth = 0;
		caneSouthEast = 0;
	}

	private void initialiseDog(){
		dog = 0;
		dogNorthWest = 0;
		dogNorth = 0;
		dogNorthEast = 0;
		dogEast = 0;
		dogWest = 0;
		dogSouthWest = 0;
		dogSouth = 0;
		dogSouthEast = 0;
	}
	
	private void initialiseScooter(){
		mobilityScooter= 0;
		mobilityscooterNorthWest = 0;
		mobilityscooterNorth = 0;
		mobilityscooterNorthEast = 0;
		mobilityscooterEast = 0;
		mobilityscooterWest = 0;
		mobilityscooterSouthWest = 0;
		mobilityscooterSouth = 0;
		mobilityscooterSouthEast = 0;
		
		manualScooter= 0;
		manualscooterNorthWest = 0;
		manualscooterNorth = 0;
		manualscooterNorthEast = 0;
		manualscooterEast = 0;
		manualscooterWest = 0;
		manualscooterSouthWest = 0;
		manualscooterSouth = 0;
		manualscooterSouthEast = 0;
	}

	private void initialiseWheelchair(){
		wheelChair_assisted = 0;
		wheelchair_assistedNorthWest = 0;
		wheelchair_assistedNorth = 0;
		wheelchair_assistedNorthEast = 0;
		wheelchair_assistedEast = 0;
		wheelchair_assistedWest = 0;
		wheelchair_assistedSouthWest = 0;
		wheelchair_assistedSouth = 0;
		wheelchair_assistedSouthEast = 0;
		
		wheelChair_manual = 0;
		wheelchair_manualNorthWest = 0;
		wheelchair_manualNorth = 0;
		wheelchair_manualNorthEast = 0;
		wheelchair_manualEast = 0;
		wheelchair_manualWest = 0;
		wheelchair_manualSouthWest = 0;
		wheelchair_manualSouth = 0;
		wheelchair_manualSouthEast = 0;
		
		wheelChair_powered = 0;
		wheelchair_poweredNorthWest = 0;
		wheelchair_poweredNorth = 0;
		wheelchair_poweredNorthEast = 0;
		wheelchair_poweredEast = 0;
		wheelchair_poweredWest = 0;
		wheelchair_poweredSouthWest = 0;
		wheelchair_poweredSouth = 0;
		wheelchair_poweredSouthEast = 0;
	}

	private void initialisePushchair(){
		pushChair = 0;
		pushchairNorthWest = 0;
		pushchairNorth = 0;
		pushchairNorthEast = 0;
		pushchairEast = 0;
		pushchairWest = 0;
		pushchairSouthWest = 0;
		pushchairSouth = 0;
		pushchairSouthEast = 0;
	}

	private void initialiseSkateboard(){
		skateboard = 0;
		skateboardNorthWest = 0;
		skateboardNorth = 0;
		skateboardNorthEast = 0;
		skateboardEast = 0;
		skateboardWest = 0;
		skateboardSouthWest = 0;
		skateboardSouth = 0;
		skateboardSouthEast = 0;
	}
	
	private void initialiseDirectionTotal(){
		northWestTotal = 0;
		northTotal = 0;
		northEastTotal = 0;
		westTotal = 0;
		eastTotal = 0;
		southWestTotal = 0;
		southTotal = 0;
		southEastTotal = 0;
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
	 * This method is used to increment the total and individual value of the pedestrian/vehicle counts that went a certain direction.
	 */
	private void updateDirectionCount(){
		switch (currentlySelectedObject) {
		case "Bus":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				busNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Bus: " + busNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				busNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Bus: " + busNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				busSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Bus: " + busSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				busSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Bus: " + busSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				busNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Bus: " + busNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				busEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Bus: " + busEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				busSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Bus: " + busSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				busWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Bus: " + busWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Truck":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				truckWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Truck: " + truckNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				truckNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Truck: " + truckNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				truckSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Truck: " + truckSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				truckSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Truck: " + truckSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				truckNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Truck: " + truckNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				truckEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Truck: " + truckEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				truckSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Truck: " + truckSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				truckWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Truck: " + truckWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Car":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				carNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Car: " + carNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				carNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Car: " + carNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				carSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Car: " + carSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				carSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Car: " + carSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				carNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Car: " + carNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				carEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Car: " + carEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				carSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Car: " + carSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				carWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Car: " + carWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Motor Bike":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				motorbikeNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-MotorBike: " + motorbikeNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				motorbikeNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-MotorBike: " + motorbikeNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				motorbikeSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-MotorBike: " + motorbikeSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				motorbikeSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-MotorBike: " + motorbikeSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				motorbikeNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-MotorBike: " + motorbikeNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				motorbikeEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-MotorBike: " + motorbikeEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				motorbikeSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-MotorBike: " + motorbikeSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				motorbikeWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-MotorBike: " + motorbikeWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Pedestrian (No Aid)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				pedestrianNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Pedestrian: " + pedestrianNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				pedestrianNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Pedestrian: " + pedestrianNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				pedestrianSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Pedestrian: " + pedestrianSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				pedestrianSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
				Toast.makeText(this, "South-East-Pedestrian: " + pedestrianSouthEast, Toast.LENGTH_SHORT).show();
			}else if(directionTo.contains("North")){
				northTotal++;
				pedestrianNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Pedestrian: " + pedestrianNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				pedestrianEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Pedestrian: " + pedestrianEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				pedestrianSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Pedestrian: " + pedestrianSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				pedestrianWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Pedestrian: " + pedestrianWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Cane (Poor Eyesight)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				caneNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Cane: " + caneNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				caneNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Cane: " + caneNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				caneSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Cane: " + caneSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				caneSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Cane: " + caneSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				caneNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Cane: " + caneNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				caneEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Cane: " + caneEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				caneSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Cane: " + caneSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				caneWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Cane: " + caneWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Guide Dog":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				dogNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Dog: " + dogNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				dogNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Dog: " + dogNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				dogSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Dog: " + dogSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				dogSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Dog: " + dogSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				dogNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Dog: " + dogNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				dogEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Dog: " + dogEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				dogSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Dog: " + dogSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				dogWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Dog: " + dogWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Mobility Scooter":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				mobilityscooterNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-MobilityScooter: " + mobilityscooterNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				mobilityscooterNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-MobilityScooter: " + mobilityscooterNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				mobilityscooterSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-MobilityScooter: " + mobilityscooterSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				mobilityscooterSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-MobilityScooter: " + mobilityscooterSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				mobilityscooterNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-MobilityScooter: " + mobilityscooterNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				mobilityscooterEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-MobilityScooter: " + mobilityscooterEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				mobilityscooterSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-MobilityScooter: " + mobilityscooterSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				mobilityscooterWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-MobilityScooter: " + mobilityscooterWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Walking Stick / Crutch (1)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				crutches1NorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Crutches1: " + crutches1NorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				crutches1NorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Crutches1: " + crutches1NorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				crutches1SouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Crutches1: " + crutches1SouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				crutches1SouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Crutches1: " + crutches1SouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				crutches1North++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Crutches1: " + crutches1North, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				crutches1East++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Crutches1: " + crutches1East, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				crutches1South++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Crutches1: " + crutches1South, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				crutches1West++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Crutches1: " + crutches1West, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Walking Sticks / Crutches (2)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				crutches2NorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Crutches2: " + crutches2NorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				crutches2NorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Crutches2: " + crutches2NorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				crutches2SouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Crutches2: " + crutches2SouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				crutches2SouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Crutches2: " + crutches2SouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				crutches2North++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Crutches2: " + crutches2North, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				crutches2East++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Crutches2: " + crutches2East, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				crutches2South++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Crutches2: " + crutches2South, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				crutches2West++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Crutches2: " + crutches2West, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Wheel Chair (Assisted)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				wheelchair_assistedNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-WheelChairAssisted: " + wheelchair_assistedNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				wheelchair_assistedNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-WheelChairAssisted: " + wheelchair_assistedNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				wheelchair_assistedSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-WheelChairAssisted: " + wheelchair_assistedSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				wheelchair_assistedSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-WheelChairAssisted: " + wheelchair_assistedSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				wheelchair_assistedNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-WheelChairAssisted: " + wheelchair_assistedNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				wheelchair_assistedEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-WheelChairAssisted: " + wheelchair_assistedEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				wheelchair_assistedSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-WheelChairAssisted: " + wheelchair_assistedSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				wheelchair_assistedWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-WheelChairAssisted: " + wheelchair_assistedWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Wheel Chair (Manual)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				wheelchair_manualNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-WheelChairManual: " + wheelchair_manualNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				wheelchair_manualNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-WheelChairManual: " + wheelchair_manualNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				wheelchair_manualSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-WheelChairManual: " + wheelchair_manualSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				wheelchair_manualSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-WheelChairManual: " + wheelchair_manualSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				wheelchair_manualNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-WheelChairManual: " + wheelchair_manualNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				wheelchair_manualEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-WheelChairManual: " + wheelchair_manualEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				wheelchair_manualSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-WheelChairManual: " + wheelchair_manualSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				wheelchair_manualWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-WheelChairManual: " + wheelchair_manualWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Wheel Chair (Powered)":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				wheelchair_poweredNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-WheelChairPowered: " + wheelchair_poweredNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				wheelchair_poweredNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-WheelChairPowered: " + wheelchair_poweredNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				wheelchair_poweredSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-WheelChairPowered: " + wheelchair_poweredSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				wheelchair_poweredSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-WheelChairPowered: " + wheelchair_poweredSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				wheelchair_poweredNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-WheelChairPowered: " + wheelchair_poweredNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				wheelchair_poweredEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-WheelChairPowered: " + wheelchair_poweredEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				wheelchair_poweredSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-WheelChairPowered: " + wheelchair_poweredSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				wheelchair_poweredWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-WheelChairPowered: " + wheelchair_poweredWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Push Chair / Buggy":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				pushchairNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-PushChair: " + pushchairNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				pushchairNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-PushChair: " + pushchairNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				pushchairSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-PushChair: " + pushchairSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				pushchairSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-PushChair: " + pushchairSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				pushchairNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-PushChair: " + pushchairNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				pushchairEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-PushChair: " + pushchairEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				pushchairSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-PushChair: " + pushchairSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				pushchairWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-PushChair: " + pushchairWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Skateboard":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				skateboardNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-Skateboard: " + skateboardNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				skateboardNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-Skateboard: " + skateboardNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				skateboardSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-Skateboard: " + skateboardSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				skateboardSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-Skateboard: " + skateboardSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				skateboardNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-Skateboard: " + skateboardNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				skateboardEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-Skateboard: " + skateboardEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				skateboardSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-Skateboard: " + skateboardSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				skateboardWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-Skateboard: " + skateboardWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		case "Manual Scooter":
			if(directionTo.contains("North-West")){
				northWestTotal++;
				manualscooterNorthWest++;
				Toast.makeText(this, "North-West: " + northWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-West-ManualScooter: " + manualscooterNorthWest, Toast.LENGTH_SHORT).show();
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}else if(directionTo.contains("North-East")){
				northEastTotal++;
				manualscooterNorthEast++;
				Toast.makeText(this, "North-East: " + northEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-East-ManualScooter: " + manualscooterNorthEast, Toast.LENGTH_SHORT).show();
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}else if(directionTo.contains("South-West")){
				southWestTotal++;
				manualscooterSouthWest++;
				Toast.makeText(this, "South-West: " + southWestTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-West-ManualScooter: " + manualscooterSouthWest, Toast.LENGTH_SHORT).show();
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}else if(directionTo.contains("South-East")){
				southEastTotal++;
				manualscooterSouthEast++;
				Toast.makeText(this, "South-East: " + southEastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-East-ManualScooter: " + manualscooterSouthEast, Toast.LENGTH_SHORT).show();
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}else if(directionTo.contains("North")){
				northTotal++;
				manualscooterNorth++;
				Toast.makeText(this, "North: " + northTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "North-ManualScooter: " + manualscooterNorth, Toast.LENGTH_SHORT).show();
				btn_direction_n.setText("North (" + northTotal + ")");
			}else if(directionTo.contains("East")){
				eastTotal++;
				manualscooterEast++;
				Toast.makeText(this, "East: " + eastTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "East-ManualScooter: " + manualscooterEast, Toast.LENGTH_SHORT).show();
				btn_direction_e.setText("East (" + eastTotal + ")");
			}else if(directionTo.contains("South")){
				southTotal++;
				manualscooterSouth++;
				Toast.makeText(this, "South: " + southTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "South-ManualScooter: " + manualscooterSouth, Toast.LENGTH_SHORT).show();
				btn_direction_s.setText("South (" + southTotal + ")");
			}else if(directionTo.contains("West")){
				westTotal++;
				manualscooterWest++;
				Toast.makeText(this, "West: " + westTotal, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "West-ManualScooter: " + manualscooterWest, Toast.LENGTH_SHORT).show();
				btn_direction_w.setText("West (" + westTotal + ")");
			}
			break;
		default:
			Toast.makeText(this, "None of them match", Toast.LENGTH_SHORT).show();
			break;
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
		
		//Jean-Yves' Initializations
		private SimpleDateFormat df = new SimpleDateFormat("h:mm a");
		
		private String sessionStartTime;
		private String currentTime;

		private Calendar cal = Calendar.getInstance();
		
		private int headerWriteCount = 0;
		
		//--------------------------------------------------------------
		
		public CountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			updateTimeRemaining(millisInFuture);
			
			currentTime = df.format(Calendar.getInstance().getTime());
			cal.setTime(cal.getTime());
			cal.add(Calendar.MINUTE, -15);
			Date fifteenMinBefore = cal.getTime();
		    sessionStartTime = df.format(fifteenMinBefore);
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
					}else if(folder.exists()){
						//Open the file to be written to. Re-initialization is necessary in this case.
						FileWriter writer = new FileWriter(csvfile, true);
						//Write the header for all data counts.
						if(headerWriteCount == 0){
							writeDATA_HEADER(writer, currentDate);
							headerWriteCount++;
						}
							saveDataBasedOnIntersectionType(writer);
							flushAndCloseWriter(writer);
						
						
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
		public void writeDATA_HEADER(FileWriter fileWriter, String currentDate) throws IOException{
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
			fileWriter.append("Time, Cars, Buses, Trucks, Motorcycles \n");
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
		
		//Save the data based on the intersectionType that was selected by the user. -Jean-Yves
		public void saveDataBasedOnIntersectionType(FileWriter writer) throws IOException {
			switch (intersectionType) {
			case "3 Way Intersection":
				writer.append(sessionStartTime + " TO " + currentTime + "," + "\n");
				userMessage("Data save complete");
				break;
			case "4 Way Intersection":
				writer.append(sessionStartTime + " TO " + currentTime + "," + "\n");
				userMessage("Data save complete");
				break;
			case "5 Way Intersection":
				writer.append(sessionStartTime + " TO " + currentTime + "," + "\n");
				userMessage("Data save complete");
				break;
			case "6 Way Intersection":
				writer.append(sessionStartTime + " TO " + currentTime + "," + "\n");
				userMessage("Data save complete");
				break;
		
			default:
				userMessage("You didn't choose an Intersection Type");
				userMessage("Data not saved!");
				break;
			}
		}

		@Override
		public String toString() {
			return ms;
		}
	}
}

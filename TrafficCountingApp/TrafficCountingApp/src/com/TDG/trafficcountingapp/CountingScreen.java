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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

	static boolean[] directionFrom;
	static boolean[] directionTo;
	static int northWestTotal, northTotal, northEastTotal, westTotal, eastTotal, southWestTotal,
				southTotal, southEastTotal;
	//North West
	static int northWestToNorthBus, northWestToNorthTruck, northWestToNorthCar,
				northWestToNorthMotorBike, northWestToNorthPedestrian, northWestToNorthCrutches1,
				northWestToNorthCrutches2, northWestToNorthCane, northWestToNorthDog, northWestToNorthMobilityScooter, 
				northWestToNorthWheelChairAssisted, northWestToNorthWheelChairManual, northWestToNorthWheelChairPowered,
				northWestToNorthPushChair, northWestToNorthSkateboard, northWestToNorthManualScooter;
	static int northWestToNorthEastBus, northWestToNorthEastTruck, northWestToNorthEastCar,
				northWestToNorthEastMotorBike, northWestToNorthEastPedestrian, northWestToNorthEastCrutches1,
				northWestToNorthEastCrutches2, northWestToNorthEastCane, northWestToNorthEastDog, northWestToNorthEastMobilityScooter, 
				northWestToNorthEastWheelChairAssisted, northWestToNorthEastWheelChairManual, northWestToNorthEastWheelChairPowered,
				northWestToNorthEastPushChair, northWestToNorthEastSkateboard, northWestToNorthEastManualScooter;
	static int northWestToWestBus, northWestToWestTruck, northWestToWestCar,
				northWestToWestMotorBike, northWestToWestPedestrian, northWestToWestCrutches1,
				northWestToWestCrutches2, northWestToWestCane, northWestToWestDog, northWestToWestMobilityScooter, 
				northWestToWestWheelChairAssisted, northWestToWestWheelChairManual, northWestToWestWheelChairPowered,
				northWestToWestPushChair, northWestToWestSkateboard, northWestToWestManualScooter;
	static int northWestToEastBus, northWestToEastTruck, northWestToEastCar,
				northWestToEastMotorBike, northWestToEastPedestrian, northWestToEastCrutches1,
				northWestToEastCrutches2, northWestToEastCane, northWestToEastDog, northWestToEastMobilityScooter, 
				northWestToEastWheelChairAssisted, northWestToEastWheelChairManual, northWestToEastWheelChairPowered,
				northWestToEastPushChair, northWestToEastSkateboard, northWestToEastManualScooter;
	static int northWestToSouthWestBus, northWestToSouthWestTruck, northWestToSouthWestCar,
				northWestToSouthWestMotorBike, northWestToSouthWestPedestrian, northWestToSouthWestCrutches1,
				northWestToSouthWestCrutches2, northWestToSouthWestCane, northWestToSouthWestDog, northWestToSouthWestMobilityScooter, 
				northWestToSouthWestWheelChairAssisted, northWestToSouthWestWheelChairManual, northWestToSouthWestWheelChairPowered,
				northWestToSouthWestPushChair, northWestToSouthWestSkateboard, northWestToSouthWestManualScooter;
	static int northWestToSouthBus, northWestToSouthTruck, northWestToSouthCar,
				northWestToSouthMotorBike, northWestToSouthPedestrian, northWestToSouthCrutches1,
				northWestToSouthCrutches2, northWestToSouthCane, northWestToSouthDog, northWestToSouthMobilityScooter, 
				northWestToSouthWheelChairAssisted, northWestToSouthWheelChairManual, northWestToSouthWheelChairPowered,
				northWestToSouthPushChair, northWestToSouthSkateboard, northWestToSouthManualScooter;
	static int northWestToSouthEastBus, northWestToSouthEastTruck, northWestToSouthEastCar,
				northWestToSouthEastMotorBike, northWestToSouthEastPedestrian, northWestToSouthEastCrutches1,
				northWestToSouthEastCrutches2, northWestToSouthEastCane, northWestToSouthEastDog, northWestToSouthEastMobilityScooter, 
				northWestToSouthEastWheelChairAssisted, northWestToSouthEastWheelChairManual, northWestToSouthEastWheelChairPowered,
				northWestToSouthEastPushChair, northWestToSouthEastSkateboard, northWestToSouthEastManualScooter;
	
	//North
	static int northToNorthWestBus, northToNorthWestTruck, northToNorthWestCar,
				northToNorthWestMotorBike, northToNorthWestPedestrian, northToNorthWestCrutches1,
				northToNorthWestCrutches2, northToNorthWestCane, northToNorthWestDog, northToNorthWestMobilityScooter, 
				northToNorthWestWheelChairAssisted, northToNorthWestWheelChairManual, northToNorthWestWheelChairPowered,
				northToNorthWestPushChair, northToNorthWestSkateboard, northToNorthWestManualScooter;
	static int northToNorthEastBus, northToNorthEastTruck, northToNorthEastCar,
				northToNorthEastMotorBike, northToNorthEastPedestrian, northToNorthEastCrutches1,
				northToNorthEastCrutches2, northToNorthEastCane, northToNorthEastDog, northToNorthEastMobilityScooter, 
				northToNorthEastWheelChairAssisted, northToNorthEastWheelChairManual, northToNorthEastWheelChairPowered,
				northToNorthEastPushChair, northToNorthEastSkateboard, northToNorthEastManualScooter;
	static int northToWestBus, northToWestTruck, northToWestCar,
				northToWestMotorBike, northToWestPedestrian, northToWestCrutches1,
				northToWestCrutches2, northToWestCane, northToWestDog, northToWestMobilityScooter, 
				northToWestWheelChairAssisted, northToWestWheelChairManual, northToWestWheelChairPowered,
				northToWestPushChair, northToWestSkateboard, northToWestManualScooter;
	static int northToEastBus, northToEastTruck, northToEastCar,
				northToEastMotorBike, northToEastPedestrian, northToEastCrutches1,
				northToEastCrutches2, northToEastCane, northToEastDog, northToEastMobilityScooter, 
				northToEastWheelChairAssisted, northToEastWheelChairManual, northToEastWheelChairPowered,
				northToEastPushChair, northToEastSkateboard, northToEastManualScooter;
	static int northToSouthWestBus, northToSouthWestTruck, northToSouthWestCar,
				northToSouthWestMotorBike, northToSouthWestPedestrian, northToSouthWestCrutches1,
				northToSouthWestCrutches2, northToSouthWestCane, northToSouthWestDog, northToSouthWestMobilityScooter, 
				northToSouthWestWheelChairAssisted, northToSouthWestWheelChairManual, northToSouthWestWheelChairPowered,
				northToSouthWestPushChair, northToSouthWestSkateboard, northToSouthWestManualScooter;
	static int northToSouthBus, northToSouthTruck, northToSouthCar,
				northToSouthMotorBike, northToSouthPedestrian, northToSouthCrutches1,
				northToSouthCrutches2, northToSouthCane, northToSouthDog, northToSouthMobilityScooter, 
				northToSouthWheelChairAssisted, northToSouthWheelChairManual, northToSouthWheelChairPowered,
				northToSouthPushChair, northToSouthSkateboard, northToSouthManualScooter;
	static int northToSouthEastBus, northToSouthEastTruck, northToSouthEastCar,
				northToSouthEastMotorBike, northToSouthEastPedestrian, northToSouthEastCrutches1,
				northToSouthEastCrutches2, northToSouthEastCane, northToSouthEastDog, northToSouthEastMobilityScooter, 
				northToSouthEastWheelChairAssisted, northToSouthEastWheelChairManual, northToSouthEastWheelChairPowered,
				northToSouthEastPushChair, northToSouthEastSkateboard, northToSouthEastManualScooter;
	
	//North East
	static int northEastToNorthWestBus, northEastToNorthWestTruck, northEastToNorthWestCar,
				northEastToNorthWestMotorBike, northEastToNorthWestPedestrian, northEastToNorthWestCrutches1,
				northEastToNorthWestCrutches2, northEastToNorthWestCane, northEastToNorthWestDog, northEastToNorthWestMobilityScooter, 
				northEastToNorthWestWheelChairAssisted, northEastToNorthWestWheelChairManual, northEastToNorthWestWheelChairPowered,
				northEastToNorthWestPushChair, northEastToNorthWestSkateboard, northEastToNorthWestManualScooter;
	static int northEastToNorthBus, northEastToNorthTruck, northEastToNorthCar,
				northEastToNorthMotorBike, northEastToNorthPedestrian, northEastToNorthCrutches1,
				northEastToNorthCrutches2, northEastToNorthCane, northEastToNorthDog, northEastToNorthMobilityScooter, 
				northEastToNorthWheelChairAssisted, northEastToNorthWheelChairManual, northEastToNorthWheelChairPowered,
				northEastToNorthPushChair, northEastToNorthSkateboard, northEastToNorthManualScooter;
	static int northEastToWestBus, northEastToWestTruck, northEastToWestCar,
				northEastToWestMotorBike, northEastToWestPedestrian, northEastToWestCrutches1,
				northEastToWestCrutches2, northEastToWestCane, northEastToWestDog, northEastToWestMobilityScooter, 
				northEastToWestWheelChairAssisted, northEastToWestWheelChairManual, northEastToWestWheelChairPowered,
				northEastToWestPushChair, northEastToWestSkateboard, northEastToWestManualScooter;
	static int northEastToEastBus, northEastToEastTruck, northEastToEastCar,
				northEastToEastMotorBike, northEastToEastPedestrian, northEastToEastCrutches1,
				northEastToEastCrutches2, northEastToEastCane, northEastToEastDog, northEastToEastMobilityScooter, 
				northEastToEastWheelChairAssisted, northEastToEastWheelChairManual, northEastToEastWheelChairPowered,
				northEastToEastPushChair, northEastToEastSkateboard, northEastToEastManualScooter;
	static int northEastToSouthWestBus, northEastToSouthWestTruck, northEastToSouthWestCar,
				northEastToSouthWestMotorBike, northEastToSouthWestPedestrian, northEastToSouthWestCrutches1,
				northEastToSouthWestCrutches2, northEastToSouthWestCane, northEastToSouthWestDog, northEastToSouthWestMobilityScooter, 
				northEastToSouthWestWheelChairAssisted, northEastToSouthWestWheelChairManual, northEastToSouthWestWheelChairPowered,
				northEastToSouthWestPushChair, northEastToSouthWestSkateboard, northEastToSouthWestManualScooter;
	static int northEastToSouthBus, northEastToSouthTruck, northEastToSouthCar,
				northEastToSouthMotorBike, northEastToSouthPedestrian, northEastToSouthCrutches1,
				northEastToSouthCrutches2, northEastToSouthCane, northEastToSouthDog, northEastToSouthMobilityScooter, 
				northEastToSouthWheelChairAssisted, northEastToSouthWheelChairManual, northEastToSouthWheelChairPowered,
				northEastToSouthPushChair, northEastToSouthSkateboard, northEastToSouthManualScooter;
	static int northEastToSouthEastBus, northEastToSouthEastTruck, northEastToSouthEastCar,
				northEastToSouthEastMotorBike, northEastToSouthEastPedestrian, northEastToSouthEastCrutches1,
				northEastToSouthEastCrutches2, northEastToSouthEastCane, northEastToSouthEastDog, northEastToSouthEastMobilityScooter, 
				northEastToSouthEastWheelChairAssisted, northEastToSouthEastWheelChairManual, northEastToSouthEastWheelChairPowered,
				northEastToSouthEastPushChair, northEastToSouthEastSkateboard, northEastToSouthEastManualScooter;

	//West
	static int westToNorthWestBus, westToNorthWestTruck, westToNorthWestCar,
				westToNorthWestMotorBike, westToNorthWestPedestrian, westToNorthWestCrutches1,
				westToNorthWestCrutches2, westToNorthWestCane, westToNorthWestDog, westToNorthWestMobilityScooter, 
				westToNorthWestWheelChairAssisted, westToNorthWestWheelChairManual, westToNorthWestWheelChairPowered,
				westToNorthWestPushChair, westToNorthWestSkateboard, westToNorthWestManualScooter;
	static int westToNorthBus, westToNorthTruck, westToNorthCar,
				westToNorthMotorBike, westToNorthPedestrian, westToNorthCrutches1,
				westToNorthCrutches2, westToNorthCane, westToNorthDog, westToNorthMobilityScooter, 
				westToNorthWheelChairAssisted, westToNorthWheelChairManual, westToNorthWheelChairPowered,
				westToNorthPushChair, westToNorthSkateboard, westToNorthManualScooter;
	static int westToNorthEastBus, westToNorthEastTruck, westToNorthEastCar,
				westToNorthEastMotorBike, westToNorthEastPedestrian, westToNorthEastCrutches1,
				westToNorthEastCrutches2, westToNorthEastCane, westToNorthEastDog, westToNorthEastMobilityScooter, 
				westToNorthEastWheelChairAssisted, westToNorthEastWheelChairManual, westToNorthEastWheelChairPowered,
				westToNorthEastPushChair, westToNorthEastSkateboard, westToNorthEastManualScooter;
	static int westToEastBus, westToEastTruck, westToEastCar,
				westToEastMotorBike, westToEastPedestrian, westToEastCrutches1,
				westToEastCrutches2, westToEastCane, westToEastDog, westToEastMobilityScooter, 
				westToEastWheelChairAssisted, westToEastWheelChairManual, westToEastWheelChairPowered,
				westToEastPushChair, westToEastSkateboard, westToEastManualScooter;
	static int westToSouthWestBus, westToSouthWestTruck, westToSouthWestCar,
				westToSouthWestMotorBike, westToSouthWestPedestrian, westToSouthWestCrutches1,
				westToSouthWestCrutches2, westToSouthWestCane, westToSouthWestDog, westToSouthWestMobilityScooter, 
				westToSouthWestWheelChairAssisted, westToSouthWestWheelChairManual, westToSouthWestWheelChairPowered,
				westToSouthWestPushChair, westToSouthWestSkateboard, westToSouthWestManualScooter;
	static int westToSouthBus, westToSouthTruck, westToSouthCar,
				westToSouthMotorBike, westToSouthPedestrian, westToSouthCrutches1,
				westToSouthCrutches2, westToSouthCane, westToSouthDog, westToSouthMobilityScooter, 
				westToSouthWheelChairAssisted, westToSouthWheelChairManual, westToSouthWheelChairPowered,
				westToSouthPushChair, westToSouthSkateboard, westToSouthManualScooter;
	static int westToSouthEastBus, westToSouthEastTruck, westToSouthEastCar,
				westToSouthEastMotorBike, westToSouthEastPedestrian, westToSouthEastCrutches1,
				westToSouthEastCrutches2, westToSouthEastCane, westToSouthEastDog, westToSouthEastMobilityScooter, 
				westToSouthEastWheelChairAssisted, westToSouthEastWheelChairManual, westToSouthEastWheelChairPowered,
				westToSouthEastPushChair, westToSouthEastSkateboard, westToSouthEastManualScooter;

	//East
	static int eastToNorthWestBus, eastToNorthWestTruck, eastToNorthWestCar,
				eastToNorthWestMotorBike, eastToNorthWestPedestrian, eastToNorthWestCrutches1,
				eastToNorthWestCrutches2, eastToNorthWestCane, eastToNorthWestDog, eastToNorthWestMobilityScooter, 
				eastToNorthWestWheelChairAssisted, eastToNorthWestWheelChairManual, eastToNorthWestWheelChairPowered,
				eastToNorthWestPushChair, eastToNorthWestSkateboard, eastToNorthWestManualScooter;
	static int eastToNorthBus, eastToNorthTruck, eastToNorthCar,
				eastToNorthMotorBike, eastToNorthPedestrian, eastToNorthCrutches1,
				eastToNorthCrutches2, eastToNorthCane, eastToNorthDog, eastToNorthMobilityScooter, 
				eastToNorthWheelChairAssisted, eastToNorthWheelChairManual, eastToNorthWheelChairPowered,
				eastToNorthPushChair, eastToNorthSkateboard, eastToNorthManualScooter;
	static int eastToNorthEastBus, eastToNorthEastTruck, eastToNorthEastCar,
				eastToNorthEastMotorBike, eastToNorthEastPedestrian, eastToNorthEastCrutches1,
				eastToNorthEastCrutches2, eastToNorthEastCane, eastToNorthEastDog, eastToNorthEastMobilityScooter, 
				eastToNorthEastWheelChairAssisted, eastToNorthEastWheelChairManual, eastToNorthEastWheelChairPowered,
				eastToNorthEastPushChair, eastToNorthEastSkateboard, eastToNorthEastManualScooter;
	static int eastToWestBus, eastToWestTruck, eastToWestCar,
				eastToWestMotorBike, eastToWestPedestrian, eastToWestCrutches1,
				eastToWestCrutches2, eastToWestCane, eastToWestDog, eastToWestMobilityScooter, 
				eastToWestWheelChairAssisted, eastToWestWheelChairManual, eastToWestWheelChairPowered,
				eastToWestPushChair, eastToWestSkateboard, eastToWestManualScooter;
	static int eastToSouthWestBus, eastToSouthWestTruck, eastToSouthWestCar,
				eastToSouthWestMotorBike, eastToSouthWestPedestrian, eastToSouthWestCrutches1,
				eastToSouthWestCrutches2, eastToSouthWestCane, eastToSouthWestDog, eastToSouthWestMobilityScooter, 
				eastToSouthWestWheelChairAssisted, eastToSouthWestWheelChairManual, eastToSouthWestWheelChairPowered,
				eastToSouthWestPushChair, eastToSouthWestSkateboard, eastToSouthWestManualScooter;
	static int eastToSouthBus, eastToSouthTruck, eastToSouthCar,
				eastToSouthMotorBike, eastToSouthPedestrian, eastToSouthCrutches1,
				eastToSouthCrutches2, eastToSouthCane, eastToSouthDog, eastToSouthMobilityScooter, 
				eastToSouthWheelChairAssisted, eastToSouthWheelChairManual, eastToSouthWheelChairPowered,
				eastToSouthPushChair, eastToSouthSkateboard, eastToSouthManualScooter;
	static int eastToSouthEastBus, eastToSouthEastTruck, eastToSouthEastCar,
				eastToSouthEastMotorBike, eastToSouthEastPedestrian, eastToSouthEastCrutches1,
				eastToSouthEastCrutches2, eastToSouthEastCane, eastToSouthEastDog, eastToSouthEastMobilityScooter, 
				eastToSouthEastWheelChairAssisted, eastToSouthEastWheelChairManual, eastToSouthEastWheelChairPowered,
				eastToSouthEastPushChair, eastToSouthEastSkateboard, eastToSouthEastManualScooter;
	
	//South West
	static int southWestToNorthWestBus, southWestToNorthWestTruck, southWestToNorthWestCar,
				southWestToNorthWestMotorBike, southWestToNorthWestPedestrian, southWestToNorthWestCrutches1,
				southWestToNorthWestCrutches2, southWestToNorthWestCane, southWestToNorthWestDog, southWestToNorthWestMobilityScooter, 
				southWestToNorthWestWheelChairAssisted, southWestToNorthWestWheelChairManual, southWestToNorthWestWheelChairPowered,
				southWestToNorthWestPushChair, southWestToNorthWestSkateboard, southWestToNorthWestManualScooter;
	static int southWestToNorthBus, southWestToNorthTruck, southWestToNorthCar,
				southWestToNorthMotorBike, southWestToNorthPedestrian, southWestToNorthCrutches1,
				southWestToNorthCrutches2, southWestToNorthCane, southWestToNorthDog, southWestToNorthMobilityScooter, 
				southWestToNorthWheelChairAssisted, southWestToNorthWheelChairManual, southWestToNorthWheelChairPowered,
				southWestToNorthPushChair, southWestToNorthSkateboard, southWestToNorthManualScooter;
	static int southWestToNorthEastBus, southWestToNorthEastTruck, southWestToNorthEastCar,
				southWestToNorthEastMotorBike, southWestToNorthEastPedestrian, southWestToNorthEastCrutches1,
				southWestToNorthEastCrutches2, southWestToNorthEastCane, southWestToNorthEastDog, southWestToNorthEastMobilityScooter, 
				southWestToNorthEastWheelChairAssisted, southWestToNorthEastWheelChairManual, southWestToNorthEastWheelChairPowered,
				southWestToNorthEastPushChair, southWestToNorthEastSkateboard, southWestToNorthEastManualScooter;
	static int southWestToWestBus, southWestToWestTruck, southWestToWestCar,
				southWestToWestMotorBike, southWestToWestPedestrian, southWestToWestCrutches1,
				southWestToWestCrutches2, southWestToWestCane, southWestToWestDog, southWestToWestMobilityScooter, 
				southWestToWestWheelChairAssisted, southWestToWestWheelChairManual, southWestToWestWheelChairPowered,
				southWestToWestPushChair, southWestToWestSkateboard, southWestToWestManualScooter;
	static int southWestToEastBus, southWestToEastTruck, southWestToEastCar,
				southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
				southWestToEastCrutches2, southWestToEastCane, southWestToEastDog, southWestToEastMobilityScooter, 
				southWestToEastWheelChairAssisted, southWestToEastWheelChairManual, southWestToEastWheelChairPowered,
				southWestToEastPushChair, southWestToEastSkateboard, southWestToEastManualScooter;
	static int southWestToSouthBus, southWestToSouthTruck, southWestToSouthCar,
				southWestToSouthMotorBike, southWestToSouthPedestrian, southWestToSouthCrutches1,
				southWestToSouthCrutches2, southWestToSouthCane, southWestToSouthDog, southWestToSouthMobilityScooter, 
				southWestToSouthWheelChairAssisted, southWestToSouthWheelChairManual, southWestToSouthWheelChairPowered,
				southWestToSouthPushChair, southWestToSouthSkateboard, southWestToSouthManualScooter;
	static int southWestToSouthEastBus, southWestToSouthEastTruck, southWestToSouthEastCar,
				southWestToSouthEastMotorBike, southWestToSouthEastPedestrian, southWestToSouthEastCrutches1,
				southWestToSouthEastCrutches2, southWestToSouthEastCane, southWestToSouthEastDog, southWestToSouthEastMobilityScooter, 
				southWestToSouthEastWheelChairAssisted, southWestToSouthEastWheelChairManual, southWestToSouthEastWheelChairPowered,
				southWestToSouthEastPushChair, southWestToSouthEastSkateboard, southWestToSouthEastManualScooter;

	// South
	static int southToNorthWestBus, southToNorthWestTruck, southToNorthWestCar,
				southToNorthWestMotorBike, southToNorthWestPedestrian, southToNorthWestCrutches1,
				southToNorthWestCrutches2, southToNorthWestCane, southToNorthWestDog, southToNorthWestMobilityScooter, 
				southToNorthWestWheelChairAssisted, southToNorthWestWheelChairManual, southToNorthWestWheelChairPowered,
				southToNorthWestPushChair, southToNorthWestSkateboard, southToNorthWestManualScooter;
	static int southToNorthBus, southToNorthTruck, southToNorthCar,
				southToNorthMotorBike, southToNorthPedestrian, southToNorthCrutches1,
				southToNorthCrutches2, southToNorthCane, southToNorthDog, southToNorthMobilityScooter, 
				southToNorthWheelChairAssisted, southToNorthWheelChairManual, southToNorthWheelChairPowered,
				southToNorthPushChair, southToNorthSkateboard, southToNorthManualScooter;
	static int southToNorthEastBus, southToNorthEastTruck, southToNorthEastCar,
				southToNorthEastMotorBike, southToNorthEastPedestrian, southToNorthEastCrutches1,
				southToNorthEastCrutches2, southToNorthEastCane, southToNorthEastDog, southToNorthEastMobilityScooter, 
				southToNorthEastWheelChairAssisted, southToNorthEastWheelChairManual, southToNorthEastWheelChairPowered,
				southToNorthEastPushChair, southToNorthEastSkateboard, southToNorthEastManualScooter;
	static int southToWestBus, southToWestTruck, southToWestCar,
				southToWestMotorBike, southToWestPedestrian, southToWestCrutches1,
				southToWestCrutches2, southToWestCane, southToWestDog, southToWestMobilityScooter, 
				southToWestWheelChairAssisted, southToWestWheelChairManual, southToWestWheelChairPowered,
				southToWestPushChair, southToWestSkateboard, southToWestManualScooter;
	static int southToEastBus, southToEastTruck, southToEastCar,
				southToEastMotorBike, southToEastPedestrian, southToEastCrutches1,
				southToEastCrutches2, southToEastCane, southToEastDog, southToEastMobilityScooter, 
				southToEastWheelChairAssisted, southToEastWheelChairManual, southToEastWheelChairPowered,
				southToEastPushChair, southToEastSkateboard, southToEastManualScooter;
	static int southToSouthWestBus, southToSouthWestTruck, southToSouthWestCar,
				southToSouthWestMotorBike, southToSouthWestPedestrian, southToSouthWestCrutches1,
				southToSouthWestCrutches2, southToSouthWestCane, southToSouthWestDog, southToSouthWestMobilityScooter, 
				southToSouthWestWheelChairAssisted, southToSouthWestWheelChairManual, southToSouthWestWheelChairPowered,
				southToSouthWestPushChair, southToSouthWestSkateboard, southToSouthWestManualScooter;
	static int southToSouthEastBus, southToSouthEastTruck, southToSouthEastCar,
				southToSouthEastMotorBike, southToSouthEastPedestrian, southToSouthEastCrutches1,
				southToSouthEastCrutches2, southToSouthEastCane, southToSouthEastDog, southToSouthEastMobilityScooter, 
				southToSouthEastWheelChairAssisted, southToSouthEastWheelChairManual, southToSouthEastWheelChairPowered,
				southToSouthEastPushChair, southToSouthEastSkateboard, southToSouthEastManualScooter;
	
	// South East
	static int southEastToNorthWestBus, southEastToNorthWestTruck, southEastToNorthWestCar,
				southEastToNorthWestMotorBike, southEastToNorthWestPedestrian, southEastToNorthWestCrutches1,
				southEastToNorthWestCrutches2, southEastToNorthWestCane, southEastToNorthWestDog, southEastToNorthWestMobilityScooter, 
				southEastToNorthWestWheelChairAssisted, southEastToNorthWestWheelChairManual, southEastToNorthWestWheelChairPowered,
				southEastToNorthWestPushChair, southEastToNorthWestSkateboard, southEastToNorthWestManualScooter;
	static int southEastToNorthBus, southEastToNorthTruck, southEastToNorthCar,
				southEastToNorthMotorBike, southEastToNorthPedestrian, southEastToNorthCrutches1,
				southEastToNorthCrutches2, southEastToNorthCane, southEastToNorthDog, southEastToNorthMobilityScooter, 
				southEastToNorthWheelChairAssisted, southEastToNorthWheelChairManual, southEastToNorthWheelChairPowered,
				southEastToNorthPushChair, southEastToNorthSkateboard, southEastToNorthManualScooter;
	static int southEastToNorthEastBus, southEastToNorthEastTruck, southEastToNorthEastCar,
				southEastToNorthEastMotorBike, southEastToNorthEastPedestrian, southEastToNorthEastCrutches1,
				southEastToNorthEastCrutches2, southEastToNorthEastCane, southEastToNorthEastDog, southEastToNorthEastMobilityScooter, 
				southEastToNorthEastWheelChairAssisted, southEastToNorthEastWheelChairManual, southEastToNorthEastWheelChairPowered,
				southEastToNorthEastPushChair, southEastToNorthEastSkateboard, southEastToNorthEastManualScooter;
	static int southEastToWestBus, southEastToWestTruck, southEastToWestCar,
				southEastToWestMotorBike, southEastToWestPedestrian, southEastToWestCrutches1,
				southEastToWestCrutches2, southEastToWestCane, southEastToWestDog, southEastToWestMobilityScooter, 
				southEastToWestWheelChairAssisted, southEastToWestWheelChairManual, southEastToWestWheelChairPowered,
				southEastToWestPushChair, southEastToWestSkateboard, southEastToWestManualScooter;
	static int southEastToEastBus, southEastToEastTruck, southEastToEastCar,
				southEastToEastMotorBike, southEastToEastPedestrian, southEastToEastCrutches1,
				southEastToEastCrutches2, southEastToEastCane, southEastToEastDog, southEastToEastMobilityScooter, 
				southEastToEastWheelChairAssisted, southEastToEastWheelChairManual, southEastToEastWheelChairPowered,
				southEastToEastPushChair, southEastToEastSkateboard, southEastToEastManualScooter;
	static int southEastToSouthWestBus, southEastToSouthWestTruck, southEastToSouthWestCar,
				southEastToSouthWestMotorBike, southEastToSouthWestPedestrian, southEastToSouthWestCrutches1,
				southEastToSouthWestCrutches2, southEastToSouthWestCane, southEastToSouthWestDog, southEastToSouthWestMobilityScooter, 
				southEastToSouthWestWheelChairAssisted, southEastToSouthWestWheelChairManual, southEastToSouthWestWheelChairPowered,
				southEastToSouthWestPushChair, southEastToSouthWestSkateboard, southEastToSouthWestManualScooter;
	static int southEastToSouthBus, southEastToSouthTruck, southEastToSouthCar,
				southEastToSouthMotorBike, southEastToSouthPedestrian, southEastToSouthCrutches1,
				southEastToSouthCrutches2, southEastToSouthCane, southEastToSouthDog, southEastToSouthMobilityScooter, 
				southEastToSouthWheelChairAssisted, southEastToSouthWheelChairManual, southEastToSouthWheelChairPowered,
				southEastToSouthPushChair, southEastToSouthSkateboard, southEastToSouthManualScooter;
	
	//	private String[] lastSelectedObjects = new String[3];
	//	private Integer[] lastSelectedCounts = new Integer[3];
	
	TextView txt_totalCount;
	int totalCount;
	
	TextView txt_currentObject;
	TextView txt_timer;
	TextView txt_currentObjectCount;
	TextView commentViewable;
	
	String comments;
	String currentlySelectedObject;
	int currentlySelectedCount;
	String intersectionType;
	boolean[] intersectionsPicked;
	
	Button btn_direction_nw, btn_direction_n, btn_direction_ne, btn_direction_w,
	 btn_direction_e, btn_direction_sw, btn_direction_s, btn_direction_se;
	
	boolean btn_direction_nw_clicked, btn_direction_n_clicked, btn_direction_ne_clicked,
			btn_direction_w_clicked, btn_direction_e_clicked, btn_direction_sw_clicked,
			btn_direction_s_clicked, btn_direction_se_clicked;
	
	//Testing purposes
	Button btn_start, btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		comments = getIntent().getStringExtra("Comments");
		
		commentViewable = (TextView) findViewById(R.id.tv_commentText);
		commentViewable.setText(comments);
		
		intersectionType = getIntent().getStringExtra("IntersectionType");
		if(intersectionType == null){
			intersectionType = "No Intersection";
		}
		
		intersectionsPicked = getIntent().getBooleanArrayExtra("IntersectionsPicked");
		
		// Initialises the current counting object to be a car
		updateCurrentObjectTo("Pedestrian (No Aid)");
				
		initialiseCountObjects();
		initialiseDirectionButtonClicked();
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
		directionFrom = new boolean[8];
		directionTo = new boolean[8];
		
		for (int x = 0; x < directionFrom.length; x++) {
			directionFrom[x] = false;
			directionTo[x]	= false;
		}
	}
	
	private void initialiseDirectionButtonClicked(){
		btn_direction_nw_clicked = false;
		btn_direction_n_clicked = false;
		btn_direction_ne_clicked = false;
		btn_direction_w_clicked = false;
		btn_direction_e_clicked = false;
		btn_direction_sw_clicked = false;
		btn_direction_s_clicked = false;
		btn_direction_se_clicked =false;
	}
	
	private void initialiseBus(){
		bus = 0;
		
		northWestToNorthBus = 0;
		northWestToNorthEastBus = 0;
		northWestToWestBus = 0;
		northWestToEastBus = 0;
		northWestToSouthWestBus = 0;
		northWestToSouthBus = 0;
		northWestToSouthEastBus = 0;
		
		northToNorthWestBus = 0;
		northToNorthEastBus = 0;
		northToWestBus = 0;
		northToEastBus = 0;
		northToSouthWestBus = 0;
		northToSouthBus = 0;
		northToSouthEastBus = 0;
		
		northEastToNorthWestBus = 0;
		northEastToNorthBus = 0;
		northEastToWestBus = 0;
		northEastToEastBus = 0;
		northEastToSouthWestBus = 0;
		northEastToSouthBus = 0;
		northEastToSouthEastBus = 0;
		
		westToNorthWestBus = 0;
		westToNorthBus = 0;
		westToNorthEastBus = 0;
		westToEastBus = 0;
		westToSouthEastBus = 0;
		westToSouthBus = 0;
		westToSouthEastBus = 0;
		
		eastToNorthWestBus = 0;
		eastToNorthBus = 0;
		eastToNorthEastBus = 0;
		eastToWestBus = 0;
		eastToSouthWestBus = 0;
		eastToSouthBus = 0;
		eastToSouthEastBus = 0;
		
		southWestToNorthWestBus = 0;
		southWestToNorthBus = 0;
		southWestToNorthEastBus = 0;
		southWestToWestBus = 0;
		southWestToEastBus = 0;
		southWestToSouthBus = 0;
		southWestToSouthEastBus = 0;
		
		southToNorthWestBus = 0;
		southToNorthBus = 0;
		southToNorthEastBus = 0;
		southToWestBus = 0;
		southToEastBus = 0;
		southToSouthWestBus = 0;
		southToSouthEastBus = 0;
		
		southEastToNorthWestBus = 0;
		southEastToNorthBus = 0;
		southEastToNorthEastBus = 0;
		southEastToWestBus = 0;
		southEastToEastBus = 0;
		southEastToSouthWestBus = 0;
		southEastToSouthBus = 0;
	}
	
	private void initialiseCar(){
		car = 0;

		northWestToNorthCar = 0;
		northWestToNorthEastCar = 0;
		northWestToWestCar = 0;
		northWestToEastCar = 0;
		northWestToSouthWestCar = 0;
		northWestToSouthCar = 0;
		northWestToSouthEastCar = 0;
		
		northToNorthWestCar = 0;
		northToNorthEastCar = 0;
		northToWestCar = 0;
		northToEastCar = 0;
		northToSouthWestCar = 0;
		northToSouthCar = 0;
		northToSouthEastCar = 0;
		
		northEastToNorthWestCar = 0;
		northEastToNorthCar = 0;
		northEastToWestCar = 0;
		northEastToEastCar = 0;
		northEastToSouthWestCar = 0;
		northEastToSouthCar = 0;
		northEastToSouthEastCar = 0;
		
		westToNorthWestCar = 0;
		westToNorthCar = 0;
		westToNorthEastCar = 0;
		westToEastCar = 0;
		westToSouthEastCar = 0;
		westToSouthCar = 0;
		westToSouthEastCar = 0;
		
		eastToNorthWestCar = 0;
		eastToNorthCar = 0;
		eastToNorthEastCar = 0;
		eastToWestCar = 0;
		eastToSouthWestCar = 0;
		eastToSouthCar = 0;
		eastToSouthEastCar = 0;
		
		southWestToNorthWestCar = 0;
		southWestToNorthCar = 0;
		southWestToNorthEastCar = 0;
		southWestToWestCar = 0;
		southWestToEastCar = 0;
		southWestToSouthCar = 0;
		southWestToSouthEastCar = 0;
		
		southToNorthWestCar = 0;
		southToNorthCar = 0;
		southToNorthEastCar = 0;
		southToWestCar = 0;
		southToEastCar = 0;
		southToSouthWestCar = 0;
		southToSouthEastCar = 0;
		
		southEastToNorthWestCar = 0;
		southEastToNorthCar = 0;
		southEastToNorthEastCar = 0;
		southEastToWestCar = 0;
		southEastToEastCar = 0;
		southEastToSouthWestCar = 0;
		southEastToSouthCar = 0;
	}

	private void initialiseTruck(){
		truck = 0;

		northWestToNorthTruck = 0;
		northWestToNorthEastTruck = 0;
		northWestToWestTruck = 0;
		northWestToEastTruck = 0;
		northWestToSouthWestTruck = 0;
		northWestToSouthTruck = 0;
		northWestToSouthEastTruck = 0;
		
		northToNorthWestTruck = 0;
		northToNorthEastTruck = 0;
		northToWestTruck = 0;
		northToEastTruck = 0;
		northToSouthWestTruck = 0;
		northToSouthTruck = 0;
		northToSouthEastTruck = 0;
		
		northEastToNorthWestTruck = 0;
		northEastToNorthTruck = 0;
		northEastToWestTruck = 0;
		northEastToEastTruck = 0;
		northEastToSouthWestTruck = 0;
		northEastToSouthTruck = 0;
		northEastToSouthEastTruck = 0;
		
		westToNorthWestTruck = 0;
		westToNorthTruck = 0;
		westToNorthEastTruck = 0;
		westToEastTruck = 0;
		westToSouthEastTruck = 0;
		westToSouthTruck = 0;
		westToSouthEastTruck = 0;
		
		eastToNorthWestTruck = 0;
		eastToNorthTruck = 0;
		eastToNorthEastTruck = 0;
		eastToWestTruck = 0;
		eastToSouthWestTruck = 0;
		eastToSouthTruck = 0;
		eastToSouthEastTruck = 0;
		
		southWestToNorthWestTruck = 0;
		southWestToNorthTruck = 0;
		southWestToNorthEastTruck = 0;
		southWestToWestTruck = 0;
		southWestToEastTruck = 0;
		southWestToSouthTruck = 0;
		southWestToSouthEastTruck = 0;
		
		southToNorthWestTruck = 0;
		southToNorthTruck = 0;
		southToNorthEastTruck = 0;
		southToWestTruck = 0;
		southToEastTruck = 0;
		southToSouthWestTruck = 0;
		southToSouthEastTruck = 0;
		
		southEastToNorthWestTruck = 0;
		southEastToNorthTruck = 0;
		southEastToNorthEastTruck = 0;
		southEastToWestTruck = 0;
		southEastToEastTruck = 0;
		southEastToSouthWestTruck = 0;
		southEastToSouthTruck = 0;
	}

	private void initialiseMotorBike(){
		motorBike = 0;

		northWestToNorthMotorBike = 0;
		northWestToNorthEastMotorBike = 0;
		northWestToWestMotorBike = 0;
		northWestToEastMotorBike = 0;
		northWestToSouthWestMotorBike = 0;
		northWestToSouthMotorBike = 0;
		northWestToSouthEastMotorBike = 0;
		
		northToNorthWestMotorBike = 0;
		northToNorthEastMotorBike = 0;
		northToWestMotorBike = 0;
		northToEastMotorBike = 0;
		northToSouthWestMotorBike = 0;
		northToSouthMotorBike = 0;
		northToSouthEastMotorBike = 0;
		
		northEastToNorthWestMotorBike = 0;
		northEastToNorthMotorBike = 0;
		northEastToWestMotorBike = 0;
		northEastToEastMotorBike = 0;
		northEastToSouthWestMotorBike = 0;
		northEastToSouthMotorBike = 0;
		northEastToSouthEastMotorBike = 0;
		
		westToNorthWestMotorBike = 0;
		westToNorthMotorBike = 0;
		westToNorthEastMotorBike = 0;
		westToEastMotorBike = 0;
		westToSouthEastMotorBike = 0;
		westToSouthMotorBike = 0;
		westToSouthEastMotorBike = 0;
		
		eastToNorthWestMotorBike = 0;
		eastToNorthMotorBike = 0;
		eastToNorthEastMotorBike = 0;
		eastToWestMotorBike = 0;
		eastToSouthWestMotorBike = 0;
		eastToSouthMotorBike = 0;
		eastToSouthEastMotorBike = 0;
		
		southWestToNorthWestMotorBike = 0;
		southWestToNorthMotorBike = 0;
		southWestToNorthEastMotorBike = 0;
		southWestToWestMotorBike = 0;
		southWestToEastMotorBike = 0;
		southWestToSouthMotorBike = 0;
		southWestToSouthEastMotorBike = 0;
		
		southToNorthWestMotorBike = 0;
		southToNorthMotorBike = 0;
		southToNorthEastMotorBike = 0;
		southToWestMotorBike = 0;
		southToEastMotorBike = 0;
		southToSouthWestMotorBike = 0;
		southToSouthEastMotorBike = 0;
		
		southEastToNorthWestMotorBike = 0;
		southEastToNorthMotorBike = 0;
		southEastToNorthEastMotorBike = 0;
		southEastToWestMotorBike = 0;
		southEastToEastMotorBike = 0;
		southEastToSouthWestMotorBike = 0;
		southEastToSouthMotorBike = 0;
	}

	private void initialisePedestrian(){
		pedestrian = 0;

		northWestToNorthPedestrian = 0;
		northWestToNorthEastPedestrian = 0;
		northWestToWestPedestrian = 0;
		northWestToEastPedestrian = 0;
		northWestToSouthWestPedestrian = 0;
		northWestToSouthPedestrian = 0;
		northWestToSouthEastPedestrian = 0;
		
		northToNorthWestPedestrian = 0;
		northToNorthEastPedestrian = 0;
		northToWestPedestrian = 0;
		northToEastPedestrian = 0;
		northToSouthWestPedestrian = 0;
		northToSouthPedestrian = 0;
		northToSouthEastPedestrian = 0;
		
		northEastToNorthWestPedestrian = 0;
		northEastToNorthPedestrian = 0;
		northEastToWestPedestrian = 0;
		northEastToEastPedestrian = 0;
		northEastToSouthWestPedestrian = 0;
		northEastToSouthPedestrian = 0;
		northEastToSouthEastPedestrian = 0;
		
		westToNorthWestPedestrian = 0;
		westToNorthPedestrian = 0;
		westToNorthEastPedestrian = 0;
		westToEastPedestrian = 0;
		westToSouthEastPedestrian = 0;
		westToSouthPedestrian = 0;
		westToSouthEastPedestrian = 0;
		
		eastToNorthWestPedestrian = 0;
		eastToNorthPedestrian = 0;
		eastToNorthEastPedestrian = 0;
		eastToWestPedestrian = 0;
		eastToSouthWestPedestrian = 0;
		eastToSouthPedestrian = 0;
		eastToSouthEastPedestrian = 0;
		
		southWestToNorthWestPedestrian = 0;
		southWestToNorthPedestrian = 0;
		southWestToNorthEastPedestrian = 0;
		southWestToWestPedestrian = 0;
		southWestToEastPedestrian = 0;
		southWestToSouthPedestrian = 0;
		southWestToSouthEastPedestrian = 0;
		
		southToNorthWestPedestrian = 0;
		southToNorthPedestrian = 0;
		southToNorthEastPedestrian = 0;
		southToWestPedestrian = 0;
		southToEastPedestrian = 0;
		southToSouthWestPedestrian = 0;
		southToSouthEastPedestrian = 0;
		
		southEastToNorthWestPedestrian = 0;
		southEastToNorthPedestrian = 0;
		southEastToNorthEastPedestrian = 0;
		southEastToWestPedestrian = 0;
		southEastToEastPedestrian = 0;
		southEastToSouthWestPedestrian = 0;
		southEastToSouthPedestrian = 0;
	}

	private void initialiseCrutches(){
		crutches_1 = 0;

		northWestToNorthCrutches1 = 0;
		northWestToNorthEastCrutches1 = 0;
		northWestToWestCrutches1 = 0;
		northWestToEastCrutches1 = 0;
		northWestToSouthWestCrutches1 = 0;
		northWestToSouthCrutches1 = 0;
		northWestToSouthEastCrutches1 = 0;
		
		northToNorthWestCrutches1 = 0;
		northToNorthEastCrutches1 = 0;
		northToWestCrutches1 = 0;
		northToEastCrutches1 = 0;
		northToSouthWestCrutches1 = 0;
		northToSouthCrutches1 = 0;
		northToSouthEastCrutches1 = 0;
		
		northEastToNorthWestCrutches1 = 0;
		northEastToNorthCrutches1 = 0;
		northEastToWestCrutches1 = 0;
		northEastToEastCrutches1 = 0;
		northEastToSouthWestCrutches1 = 0;
		northEastToSouthCrutches1 = 0;
		northEastToSouthEastCrutches1 = 0;
		
		westToNorthWestCrutches1 = 0;
		westToNorthCrutches1 = 0;
		westToNorthEastCrutches1 = 0;
		westToEastCrutches1 = 0;
		westToSouthEastCrutches1 = 0;
		westToSouthCrutches1 = 0;
		westToSouthEastCrutches1 = 0;
		
		eastToNorthWestCrutches1 = 0;
		eastToNorthCrutches1 = 0;
		eastToNorthEastCrutches1 = 0;
		eastToWestCrutches1 = 0;
		eastToSouthWestCrutches1 = 0;
		eastToSouthCrutches1 = 0;
		eastToSouthEastCrutches1 = 0;
		
		southWestToNorthWestCrutches1 = 0;
		southWestToNorthCrutches1 = 0;
		southWestToNorthEastCrutches1 = 0;
		southWestToWestCrutches1 = 0;
		southWestToEastCrutches1 = 0;
		southWestToSouthCrutches1 = 0;
		southWestToSouthEastCrutches1 = 0;
		
		southToNorthWestCrutches1 = 0;
		southToNorthCrutches1 = 0;
		southToNorthEastCrutches1 = 0;
		southToWestCrutches1 = 0;
		southToEastCrutches1 = 0;
		southToSouthWestCrutches1 = 0;
		southToSouthEastCrutches1 = 0;
		
		southEastToNorthWestCrutches1 = 0;
		southEastToNorthCrutches1 = 0;
		southEastToNorthEastCrutches1 = 0;
		southEastToWestCrutches1 = 0;
		southEastToEastCrutches1 = 0;
		southEastToSouthWestCrutches1 = 0;
		southEastToSouthCrutches1 = 0;
		
		crutches_2 = 0;

		northWestToNorthCrutches2 = 0;
		northWestToNorthEastCrutches2 = 0;
		northWestToWestCrutches2 = 0;
		northWestToEastCrutches2 = 0;
		northWestToSouthWestCrutches2 = 0;
		northWestToSouthCrutches2 = 0;
		northWestToSouthEastCrutches2 = 0;
		
		northToNorthWestCrutches2 = 0;
		northToNorthEastCrutches2 = 0;
		northToWestCrutches2 = 0;
		northToEastCrutches2 = 0;
		northToSouthWestCrutches2 = 0;
		northToSouthCrutches2 = 0;
		northToSouthEastCrutches2 = 0;
		
		northEastToNorthWestCrutches2 = 0;
		northEastToNorthCrutches2 = 0;
		northEastToWestCrutches2 = 0;
		northEastToEastCrutches2 = 0;
		northEastToSouthWestCrutches2 = 0;
		northEastToSouthCrutches2 = 0;
		northEastToSouthEastCrutches2 = 0;
		
		westToNorthWestCrutches2 = 0;
		westToNorthCrutches2 = 0;
		westToNorthEastCrutches2 = 0;
		westToEastCrutches2 = 0;
		westToSouthEastCrutches2 = 0;
		westToSouthCrutches2 = 0;
		westToSouthEastCrutches2 = 0;
		
		eastToNorthWestCrutches2 = 0;
		eastToNorthCrutches2 = 0;
		eastToNorthEastCrutches2 = 0;
		eastToWestCrutches2 = 0;
		eastToSouthWestCrutches2 = 0;
		eastToSouthCrutches2 = 0;
		eastToSouthEastCrutches2 = 0;
		
		southWestToNorthWestCrutches2 = 0;
		southWestToNorthCrutches2 = 0;
		southWestToNorthEastCrutches2 = 0;
		southWestToWestCrutches2 = 0;
		southWestToEastCrutches2 = 0;
		southWestToSouthCrutches2 = 0;
		southWestToSouthEastCrutches2 = 0;
		
		southToNorthWestCrutches2 = 0;
		southToNorthCrutches2 = 0;
		southToNorthEastCrutches2 = 0;
		southToWestCrutches2 = 0;
		southToEastCrutches2 = 0;
		southToSouthWestCrutches2 = 0;
		southToSouthEastCrutches2 = 0;
		
		southEastToNorthWestCrutches2 = 0;
		southEastToNorthCrutches2 = 0;
		southEastToNorthEastCrutches2 = 0;
		southEastToWestCrutches2 = 0;
		southEastToEastCrutches2 = 0;
		southEastToSouthWestCrutches2 = 0;
		southEastToSouthCrutches2 = 0;
	}

	private void initialiseCane(){
		cane = 0;

		northWestToNorthCane = 0;
		northWestToNorthEastCane = 0;
		northWestToWestCane = 0;
		northWestToEastCane = 0;
		northWestToSouthWestCane = 0;
		northWestToSouthCane = 0;
		northWestToSouthEastCane = 0;
		
		northToNorthWestCane = 0;
		northToNorthEastCane = 0;
		northToWestCane = 0;
		northToEastCane = 0;
		northToSouthWestCane = 0;
		northToSouthCane = 0;
		northToSouthEastCane = 0;
		
		northEastToNorthWestCane = 0;
		northEastToNorthCane = 0;
		northEastToWestCane = 0;
		northEastToEastCane = 0;
		northEastToSouthWestCane = 0;
		northEastToSouthCane = 0;
		northEastToSouthEastCane = 0;
		
		westToNorthWestCane = 0;
		westToNorthCane = 0;
		westToNorthEastCane = 0;
		westToEastCane = 0;
		westToSouthEastCane = 0;
		westToSouthCane = 0;
		westToSouthEastCane = 0;
		
		eastToNorthWestCane = 0;
		eastToNorthCane = 0;
		eastToNorthEastCane = 0;
		eastToWestCane = 0;
		eastToSouthWestCane = 0;
		eastToSouthCane = 0;
		eastToSouthEastCane = 0;
		
		southWestToNorthWestCane = 0;
		southWestToNorthCane = 0;
		southWestToNorthEastCane = 0;
		southWestToWestCane = 0;
		southWestToEastCane = 0;
		southWestToSouthCane = 0;
		southWestToSouthEastCane = 0;
		
		southToNorthWestCane = 0;
		southToNorthCane = 0;
		southToNorthEastCane = 0;
		southToWestCane = 0;
		southToEastCane = 0;
		southToSouthWestCane = 0;
		southToSouthEastCane = 0;
		
		southEastToNorthWestCane = 0;
		southEastToNorthCane = 0;
		southEastToNorthEastCane = 0;
		southEastToWestCane = 0;
		southEastToEastCane = 0;
		southEastToSouthWestCane = 0;
		southEastToSouthCane = 0;
	}

	private void initialiseDog(){
		dog = 0;

		northWestToNorthDog = 0;
		northWestToNorthEastDog = 0;
		northWestToWestDog = 0;
		northWestToEastDog = 0;
		northWestToSouthWestDog = 0;
		northWestToSouthDog = 0;
		northWestToSouthEastDog = 0;
		
		northToNorthWestDog = 0;
		northToNorthEastDog = 0;
		northToWestDog = 0;
		northToEastDog = 0;
		northToSouthWestDog = 0;
		northToSouthDog = 0;
		northToSouthEastDog = 0;
		
		northEastToNorthWestDog = 0;
		northEastToNorthDog = 0;
		northEastToWestDog = 0;
		northEastToEastDog = 0;
		northEastToSouthWestDog = 0;
		northEastToSouthDog = 0;
		northEastToSouthEastDog = 0;
		
		westToNorthWestDog = 0;
		westToNorthDog = 0;
		westToNorthEastDog = 0;
		westToEastDog = 0;
		westToSouthEastDog = 0;
		westToSouthDog = 0;
		westToSouthEastDog = 0;
		
		eastToNorthWestDog = 0;
		eastToNorthDog = 0;
		eastToNorthEastDog = 0;
		eastToWestDog = 0;
		eastToSouthWestDog = 0;
		eastToSouthDog = 0;
		eastToSouthEastDog = 0;
		
		southWestToNorthWestDog = 0;
		southWestToNorthDog = 0;
		southWestToNorthEastDog = 0;
		southWestToWestDog = 0;
		southWestToEastDog = 0;
		southWestToSouthDog = 0;
		southWestToSouthEastDog = 0;
		
		southToNorthWestDog = 0;
		southToNorthDog = 0;
		southToNorthEastDog = 0;
		southToWestDog = 0;
		southToEastDog = 0;
		southToSouthWestDog = 0;
		southToSouthEastDog = 0;
		
		southEastToNorthWestDog = 0;
		southEastToNorthDog = 0;
		southEastToNorthEastDog = 0;
		southEastToWestDog = 0;
		southEastToEastDog = 0;
		southEastToSouthWestDog = 0;
		southEastToSouthDog = 0;
	}
	
	private void initialiseScooter(){
		mobilityScooter= 0;

		northWestToNorthMobilityScooter = 0;
		northWestToNorthEastMobilityScooter = 0;
		northWestToWestMobilityScooter = 0;
		northWestToEastMobilityScooter = 0;
		northWestToSouthWestMobilityScooter = 0;
		northWestToSouthMobilityScooter = 0;
		northWestToSouthEastMobilityScooter = 0;
		
		northToNorthWestMobilityScooter = 0;
		northToNorthEastMobilityScooter = 0;
		northToWestMobilityScooter = 0;
		northToEastMobilityScooter = 0;
		northToSouthWestMobilityScooter = 0;
		northToSouthMobilityScooter = 0;
		northToSouthEastMobilityScooter = 0;
		
		northEastToNorthWestMobilityScooter = 0;
		northEastToNorthMobilityScooter = 0;
		northEastToWestMobilityScooter = 0;
		northEastToEastMobilityScooter = 0;
		northEastToSouthWestMobilityScooter = 0;
		northEastToSouthMobilityScooter = 0;
		northEastToSouthEastMobilityScooter = 0;
		
		westToNorthWestMobilityScooter = 0;
		westToNorthMobilityScooter = 0;
		westToNorthEastMobilityScooter = 0;
		westToEastMobilityScooter = 0;
		westToSouthEastMobilityScooter = 0;
		westToSouthMobilityScooter = 0;
		westToSouthEastMobilityScooter = 0;
		
		eastToNorthWestMobilityScooter = 0;
		eastToNorthMobilityScooter = 0;
		eastToNorthEastMobilityScooter = 0;
		eastToWestMobilityScooter = 0;
		eastToSouthWestMobilityScooter = 0;
		eastToSouthMobilityScooter = 0;
		eastToSouthEastMobilityScooter = 0;
		
		southWestToNorthWestMobilityScooter = 0;
		southWestToNorthMobilityScooter = 0;
		southWestToNorthEastMobilityScooter = 0;
		southWestToWestMobilityScooter = 0;
		southWestToEastMobilityScooter = 0;
		southWestToSouthMobilityScooter = 0;
		southWestToSouthEastMobilityScooter = 0;
		
		southToNorthWestMobilityScooter = 0;
		southToNorthMobilityScooter = 0;
		southToNorthEastMobilityScooter = 0;
		southToWestMobilityScooter = 0;
		southToEastMobilityScooter = 0;
		southToSouthWestMobilityScooter = 0;
		southToSouthEastMobilityScooter = 0;
		
		southEastToNorthWestMobilityScooter = 0;
		southEastToNorthMobilityScooter = 0;
		southEastToNorthEastMobilityScooter = 0;
		southEastToWestMobilityScooter = 0;
		southEastToEastMobilityScooter = 0;
		southEastToSouthWestMobilityScooter = 0;
		southEastToSouthMobilityScooter = 0;
		
		manualScooter= 0;

		northWestToNorthManualScooter = 0;
		northWestToNorthEastManualScooter = 0;
		northWestToWestManualScooter = 0;
		northWestToEastManualScooter = 0;
		northWestToSouthWestManualScooter = 0;
		northWestToSouthManualScooter = 0;
		northWestToSouthEastManualScooter = 0;
		
		northToNorthWestManualScooter = 0;
		northToNorthEastManualScooter = 0;
		northToWestManualScooter = 0;
		northToEastManualScooter = 0;
		northToSouthWestManualScooter = 0;
		northToSouthManualScooter = 0;
		northToSouthEastManualScooter = 0;
		
		northEastToNorthWestManualScooter = 0;
		northEastToNorthManualScooter = 0;
		northEastToWestManualScooter = 0;
		northEastToEastManualScooter = 0;
		northEastToSouthWestManualScooter = 0;
		northEastToSouthManualScooter = 0;
		northEastToSouthEastManualScooter = 0;
		
		westToNorthWestManualScooter = 0;
		westToNorthManualScooter = 0;
		westToNorthEastManualScooter = 0;
		westToEastManualScooter = 0;
		westToSouthEastManualScooter = 0;
		westToSouthManualScooter = 0;
		westToSouthEastManualScooter = 0;
		
		eastToNorthWestManualScooter = 0;
		eastToNorthManualScooter = 0;
		eastToNorthEastManualScooter = 0;
		eastToWestManualScooter = 0;
		eastToSouthWestManualScooter = 0;
		eastToSouthManualScooter = 0;
		eastToSouthEastManualScooter = 0;
		
		southWestToNorthWestManualScooter = 0;
		southWestToNorthManualScooter = 0;
		southWestToNorthEastManualScooter = 0;
		southWestToWestManualScooter = 0;
		southWestToEastManualScooter = 0;
		southWestToSouthManualScooter = 0;
		southWestToSouthEastManualScooter = 0;
		
		southToNorthWestManualScooter = 0;
		southToNorthManualScooter = 0;
		southToNorthEastManualScooter = 0;
		southToWestManualScooter = 0;
		southToEastManualScooter = 0;
		southToSouthWestManualScooter = 0;
		southToSouthEastManualScooter = 0;
		
		southEastToNorthWestManualScooter = 0;
		southEastToNorthManualScooter = 0;
		southEastToNorthEastManualScooter = 0;
		southEastToWestManualScooter = 0;
		southEastToEastManualScooter = 0;
		southEastToSouthWestManualScooter = 0;
		southEastToSouthManualScooter = 0;
	}

	private void initialiseWheelchair(){
		wheelChair_assisted = 0;

		northWestToNorthWheelChairAssisted = 0;
		northWestToNorthEastWheelChairAssisted = 0;
		northWestToWestWheelChairAssisted = 0;
		northWestToEastWheelChairAssisted = 0;
		northWestToSouthWestWheelChairAssisted = 0;
		northWestToSouthWheelChairAssisted = 0;
		northWestToSouthEastWheelChairAssisted = 0;
		
		northToNorthWestWheelChairAssisted = 0;
		northToNorthEastWheelChairAssisted = 0;
		northToWestWheelChairAssisted = 0;
		northToEastWheelChairAssisted = 0;
		northToSouthWestWheelChairAssisted = 0;
		northToSouthWheelChairAssisted = 0;
		northToSouthEastWheelChairAssisted = 0;
		
		northEastToNorthWestWheelChairAssisted = 0;
		northEastToNorthWheelChairAssisted = 0;
		northEastToWestWheelChairAssisted = 0;
		northEastToEastWheelChairAssisted = 0;
		northEastToSouthWestWheelChairAssisted = 0;
		northEastToSouthWheelChairAssisted = 0;
		northEastToSouthEastWheelChairAssisted = 0;
		
		westToNorthWestWheelChairAssisted = 0;
		westToNorthWheelChairAssisted = 0;
		westToNorthEastWheelChairAssisted = 0;
		westToEastWheelChairAssisted = 0;
		westToSouthEastWheelChairAssisted = 0;
		westToSouthWheelChairAssisted = 0;
		westToSouthEastWheelChairAssisted = 0;
		
		eastToNorthWestWheelChairAssisted = 0;
		eastToNorthWheelChairAssisted = 0;
		eastToNorthEastWheelChairAssisted = 0;
		eastToWestWheelChairAssisted = 0;
		eastToSouthWestWheelChairAssisted = 0;
		eastToSouthWheelChairAssisted = 0;
		eastToSouthEastWheelChairAssisted = 0;
		
		southWestToNorthWestWheelChairAssisted = 0;
		southWestToNorthWheelChairAssisted = 0;
		southWestToNorthEastWheelChairAssisted = 0;
		southWestToWestWheelChairAssisted = 0;
		southWestToEastWheelChairAssisted = 0;
		southWestToSouthWheelChairAssisted = 0;
		southWestToSouthEastWheelChairAssisted = 0;
		
		southToNorthWestWheelChairAssisted = 0;
		southToNorthWheelChairAssisted = 0;
		southToNorthEastWheelChairAssisted = 0;
		southToWestWheelChairAssisted = 0;
		southToEastWheelChairAssisted = 0;
		southToSouthWestWheelChairAssisted = 0;
		southToSouthEastWheelChairAssisted = 0;
		
		southEastToNorthWestWheelChairAssisted = 0;
		southEastToNorthWheelChairAssisted = 0;
		southEastToNorthEastWheelChairAssisted = 0;
		southEastToWestWheelChairAssisted = 0;
		southEastToEastWheelChairAssisted = 0;
		southEastToSouthWestWheelChairAssisted = 0;
		southEastToSouthWheelChairAssisted = 0;
		
		wheelChair_manual = 0;

		northWestToNorthWheelChairManual = 0;
		northWestToNorthEastWheelChairManual = 0;
		northWestToWestWheelChairManual = 0;
		northWestToEastWheelChairManual = 0;
		northWestToSouthWestWheelChairManual = 0;
		northWestToSouthWheelChairManual = 0;
		northWestToSouthEastWheelChairManual = 0;
		
		northToNorthWestWheelChairManual = 0;
		northToNorthEastWheelChairManual = 0;
		northToWestWheelChairManual = 0;
		northToEastWheelChairManual = 0;
		northToSouthWestWheelChairManual = 0;
		northToSouthWheelChairManual = 0;
		northToSouthEastWheelChairManual = 0;
		
		northEastToNorthWestWheelChairManual = 0;
		northEastToNorthWheelChairManual = 0;
		northEastToWestWheelChairManual = 0;
		northEastToEastWheelChairManual = 0;
		northEastToSouthWestWheelChairManual = 0;
		northEastToSouthWheelChairManual = 0;
		northEastToSouthEastWheelChairManual = 0;
		
		westToNorthWestWheelChairManual = 0;
		westToNorthWheelChairManual = 0;
		westToNorthEastWheelChairManual = 0;
		westToEastWheelChairManual = 0;
		westToSouthEastWheelChairManual = 0;
		westToSouthWheelChairManual = 0;
		westToSouthEastWheelChairManual = 0;
		
		eastToNorthWestWheelChairManual = 0;
		eastToNorthWheelChairManual = 0;
		eastToNorthEastWheelChairManual = 0;
		eastToWestWheelChairManual = 0;
		eastToSouthWestWheelChairManual = 0;
		eastToSouthWheelChairManual = 0;
		eastToSouthEastWheelChairManual = 0;
		
		southWestToNorthWestWheelChairManual = 0;
		southWestToNorthWheelChairManual = 0;
		southWestToNorthEastWheelChairManual = 0;
		southWestToWestWheelChairManual = 0;
		southWestToEastWheelChairManual = 0;
		southWestToSouthWheelChairManual = 0;
		southWestToSouthEastWheelChairManual = 0;
		
		southToNorthWestWheelChairManual = 0;
		southToNorthWheelChairManual = 0;
		southToNorthEastWheelChairManual = 0;
		southToWestWheelChairManual = 0;
		southToEastWheelChairManual = 0;
		southToSouthWestWheelChairManual = 0;
		southToSouthEastWheelChairManual = 0;
		
		southEastToNorthWestWheelChairManual = 0;
		southEastToNorthWheelChairManual = 0;
		southEastToNorthEastWheelChairManual = 0;
		southEastToWestWheelChairManual = 0;
		southEastToEastWheelChairManual = 0;
		southEastToSouthWestWheelChairManual = 0;
		southEastToSouthWheelChairManual = 0;
		
		wheelChair_powered = 0;

		northWestToNorthWheelChairPowered = 0;
		northWestToNorthEastWheelChairPowered = 0;
		northWestToWestWheelChairPowered = 0;
		northWestToEastWheelChairPowered = 0;
		northWestToSouthWestWheelChairPowered = 0;
		northWestToSouthWheelChairPowered = 0;
		northWestToSouthEastWheelChairPowered = 0;
		
		northToNorthWestWheelChairPowered = 0;
		northToNorthEastWheelChairPowered = 0;
		northToWestWheelChairPowered = 0;
		northToEastWheelChairPowered = 0;
		northToSouthWestWheelChairPowered = 0;
		northToSouthWheelChairPowered = 0;
		northToSouthEastWheelChairPowered = 0;
		
		northEastToNorthWestWheelChairPowered = 0;
		northEastToNorthWheelChairPowered = 0;
		northEastToWestWheelChairPowered = 0;
		northEastToEastWheelChairPowered = 0;
		northEastToSouthWestWheelChairPowered = 0;
		northEastToSouthWheelChairPowered = 0;
		northEastToSouthEastWheelChairPowered = 0;
		
		westToNorthWestWheelChairPowered = 0;
		westToNorthWheelChairPowered = 0;
		westToNorthEastWheelChairPowered = 0;
		westToEastWheelChairPowered = 0;
		westToSouthEastWheelChairPowered = 0;
		westToSouthWheelChairPowered = 0;
		westToSouthEastWheelChairPowered = 0;
		
		eastToNorthWestWheelChairPowered = 0;
		eastToNorthWheelChairPowered = 0;
		eastToNorthEastWheelChairPowered = 0;
		eastToWestWheelChairPowered = 0;
		eastToSouthWestWheelChairPowered = 0;
		eastToSouthWheelChairPowered = 0;
		eastToSouthEastWheelChairPowered = 0;
		
		southWestToNorthWestWheelChairPowered = 0;
		southWestToNorthWheelChairPowered = 0;
		southWestToNorthEastWheelChairPowered = 0;
		southWestToWestWheelChairPowered = 0;
		southWestToEastWheelChairPowered = 0;
		southWestToSouthWheelChairPowered = 0;
		southWestToSouthEastWheelChairPowered = 0;
		
		southToNorthWestWheelChairPowered = 0;
		southToNorthWheelChairPowered = 0;
		southToNorthEastWheelChairPowered = 0;
		southToWestWheelChairPowered = 0;
		southToEastWheelChairPowered = 0;
		southToSouthWestWheelChairPowered = 0;
		southToSouthEastWheelChairPowered = 0;
		
		southEastToNorthWestWheelChairPowered = 0;
		southEastToNorthWheelChairPowered = 0;
		southEastToNorthEastWheelChairPowered = 0;
		southEastToWestWheelChairPowered = 0;
		southEastToEastWheelChairPowered = 0;
		southEastToSouthWestWheelChairPowered = 0;
		southEastToSouthWheelChairPowered = 0;
	}

	private void initialisePushchair(){
		pushChair = 0;

		northWestToNorthPushChair = 0;
		northWestToNorthEastPushChair = 0;
		northWestToWestPushChair = 0;
		northWestToEastPushChair = 0;
		northWestToSouthWestPushChair = 0;
		northWestToSouthPushChair = 0;
		northWestToSouthEastPushChair = 0;
		
		northToNorthWestPushChair = 0;
		northToNorthEastPushChair = 0;
		northToWestPushChair = 0;
		northToEastPushChair = 0;
		northToSouthWestPushChair = 0;
		northToSouthPushChair = 0;
		northToSouthEastPushChair = 0;
		
		northEastToNorthWestPushChair = 0;
		northEastToNorthPushChair = 0;
		northEastToWestPushChair = 0;
		northEastToEastPushChair = 0;
		northEastToSouthWestPushChair = 0;
		northEastToSouthPushChair = 0;
		northEastToSouthEastPushChair = 0;
		
		westToNorthWestPushChair = 0;
		westToNorthPushChair = 0;
		westToNorthEastPushChair = 0;
		westToEastPushChair = 0;
		westToSouthEastPushChair = 0;
		westToSouthPushChair = 0;
		westToSouthEastPushChair = 0;
		
		eastToNorthWestPushChair = 0;
		eastToNorthPushChair = 0;
		eastToNorthEastPushChair = 0;
		eastToWestPushChair = 0;
		eastToSouthWestPushChair = 0;
		eastToSouthPushChair = 0;
		eastToSouthEastPushChair = 0;
		
		southWestToNorthWestPushChair = 0;
		southWestToNorthPushChair = 0;
		southWestToNorthEastPushChair = 0;
		southWestToWestPushChair = 0;
		southWestToEastPushChair = 0;
		southWestToSouthPushChair = 0;
		southWestToSouthEastPushChair = 0;
		
		southToNorthWestPushChair = 0;
		southToNorthPushChair = 0;
		southToNorthEastPushChair = 0;
		southToWestPushChair = 0;
		southToEastPushChair = 0;
		southToSouthWestPushChair = 0;
		southToSouthEastPushChair = 0;
		
		southEastToNorthWestPushChair = 0;
		southEastToNorthPushChair = 0;
		southEastToNorthEastPushChair = 0;
		southEastToWestPushChair = 0;
		southEastToEastPushChair = 0;
		southEastToSouthWestPushChair = 0;
		southEastToSouthPushChair = 0;
	}

	private void initialiseSkateboard(){
		skateboard = 0;

		northWestToNorthSkateboard = 0;
		northWestToNorthEastSkateboard = 0;
		northWestToWestSkateboard = 0;
		northWestToEastSkateboard = 0;
		northWestToSouthWestSkateboard = 0;
		northWestToSouthSkateboard = 0;
		northWestToSouthEastSkateboard = 0;
		
		northToNorthWestSkateboard = 0;
		northToNorthEastSkateboard = 0;
		northToWestSkateboard = 0;
		northToEastSkateboard = 0;
		northToSouthWestSkateboard = 0;
		northToSouthSkateboard = 0;
		northToSouthEastSkateboard = 0;
		
		northEastToNorthWestSkateboard = 0;
		northEastToNorthSkateboard = 0;
		northEastToWestSkateboard = 0;
		northEastToEastSkateboard = 0;
		northEastToSouthWestSkateboard = 0;
		northEastToSouthSkateboard = 0;
		northEastToSouthEastSkateboard = 0;
		
		westToNorthWestSkateboard = 0;
		westToNorthSkateboard = 0;
		westToNorthEastSkateboard = 0;
		westToEastSkateboard = 0;
		westToSouthEastSkateboard = 0;
		westToSouthSkateboard = 0;
		westToSouthEastSkateboard = 0;
		
		eastToNorthWestSkateboard = 0;
		eastToNorthSkateboard = 0;
		eastToNorthEastSkateboard = 0;
		eastToWestSkateboard = 0;
		eastToSouthWestSkateboard = 0;
		eastToSouthSkateboard = 0;
		eastToSouthEastSkateboard = 0;
		
		southWestToNorthWestSkateboard = 0;
		southWestToNorthSkateboard = 0;
		southWestToNorthEastSkateboard = 0;
		southWestToWestSkateboard = 0;
		southWestToEastSkateboard = 0;
		southWestToSouthSkateboard = 0;
		southWestToSouthEastSkateboard = 0;
		
		southToNorthWestSkateboard = 0;
		southToNorthSkateboard = 0;
		southToNorthEastSkateboard = 0;
		southToWestSkateboard = 0;
		southToEastSkateboard = 0;
		southToSouthWestSkateboard = 0;
		southToSouthEastSkateboard = 0;
		
		southEastToNorthWestSkateboard = 0;
		southEastToNorthSkateboard = 0;
		southEastToNorthEastSkateboard = 0;
		southEastToWestSkateboard = 0;
		southEastToEastSkateboard = 0;
		southEastToSouthWestSkateboard = 0;
		southEastToSouthSkateboard = 0;
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
			break;
		case "4 Way Intersection":
			countPanel.setImageResource(drawable.intersection_4);
			break;
		case "5 Way Intersection":
			countPanel.setImageResource(drawable.intersection_5);
			break;
		case "6 Way Intersection":
			countPanel.setImageResource(drawable.intersection_6);
			break;
		default:
			countPanel.setVisibility(8);
			break;
		}
		
		setVisibilityDirectionButtons();
	}
	
	/*
	 * This sets the visibility of the buttons for the directions
	 * The order of the intersectionPicked boolean array is set to:
	 * intersectionPicked[0] = North-West
	 * intersectionPicked[1] = North
	 * intersectionPicked[2] = North-East
	 * intersectionPicked[3] = West
	 * intersectionPicked[4] = East
	 * intersectionPicked[5] = South-West
	 * intersectionPicked[6] = South
	 * intersectionPicked[7] = South-East
	 */
	private void setVisibilityDirectionButtons(){
		if(!intersectionType.equals("No Intersection")){
			if(!intersectionsPicked[0]){
				btn_direction_nw.setVisibility(4);
			}
			if(!intersectionsPicked[1]){
				btn_direction_n.setVisibility(4);
			}
			if(!intersectionsPicked[2]){
				btn_direction_ne.setVisibility(4);
			}
			if(!intersectionsPicked[3]){
				btn_direction_w.setVisibility(4);
			}
			if(!intersectionsPicked[4]){
				btn_direction_e.setVisibility(4);
			}
			if(!intersectionsPicked[5]){
				btn_direction_sw.setVisibility(4);
			}
			if(!intersectionsPicked[6]){
				btn_direction_s.setVisibility(4);
			}
			if(!intersectionsPicked[7]){
				btn_direction_se.setVisibility(4);
			}
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
		btn_direction_nw.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_n = (Button)findViewById(R.id.cs_btn_direction_n);
		btn_direction_n.setText("North (0)");
		btn_direction_n.setOnClickListener(this);
		btn_direction_n.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_ne = (Button)findViewById(R.id.cs_btn_direction_ne);
		btn_direction_ne.setText("North-East (0)");
		btn_direction_ne.setOnClickListener(this);
		btn_direction_ne.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_w = (Button)findViewById(R.id.cs_btn_direction_w);
		btn_direction_w.setText("West (0)");
		btn_direction_w.setOnClickListener(this);
		btn_direction_w.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_e = (Button)findViewById(R.id.cs_btn_direction_e);
		btn_direction_e.setText("East (0)");
		btn_direction_e.setOnClickListener(this);
		btn_direction_e.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_sw = (Button)findViewById(R.id.cs_btn_direction_sw);
		btn_direction_sw.setText("South-West (0)");
		btn_direction_sw.setOnClickListener(this);
		btn_direction_sw.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_s = (Button)findViewById(R.id.cs_btn_direction_s);
		btn_direction_s.setText("South (0)");
		btn_direction_s.setOnClickListener(this);
		btn_direction_s.setBackgroundResource(R.drawable.small_grey_red);
		
		btn_direction_se = (Button)findViewById(R.id.cs_btn_direction_se);
		btn_direction_se.setText("South-East (0)");
		btn_direction_se.setOnClickListener(this);
		btn_direction_se.setBackgroundResource(R.drawable.small_grey_red);
	}
	
	private void resetDirectionButtonBackground(){
		btn_direction_nw.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_n.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_ne.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_w.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_e.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_sw.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_s.setBackgroundResource(R.drawable.small_grey_red);
		btn_direction_se.setBackgroundResource(R.drawable.small_grey_red);
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
	public void sendClickMessage(String key, String stringValue, boolean[] booleanValue) {
		if(!key.equals("Comment")){
			updateCurrentObjectTo(stringValue);
			updateCurrentlySelectedObject(getCurrentObjectCount(stringValue));
		}else{
			comments = stringValue;
			commentViewable.setText(comments);
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
		if(btn_direction_nw.isPressed()){
			btn_direction_nw_clicked = reverseButtonClicked(btn_direction_nw_clicked);
			if(btn_direction_nw_clicked){
				btn_direction_nw.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_nw.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(0);
		}else if(btn_direction_n.isPressed()){
			btn_direction_n_clicked = reverseButtonClicked(btn_direction_n_clicked);
			if(btn_direction_n_clicked){
				btn_direction_n.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_n.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(1);
		}else if(btn_direction_ne.isPressed()){
			btn_direction_ne_clicked = reverseButtonClicked(btn_direction_ne_clicked);
			if(btn_direction_ne_clicked){
				btn_direction_ne.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_ne.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(2);
		}else if(btn_direction_w.isPressed()){
			btn_direction_w_clicked = reverseButtonClicked(btn_direction_w_clicked);
			if(btn_direction_w_clicked){
				btn_direction_w.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_w.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(3);
		}else if(btn_direction_e.isPressed()){
			btn_direction_e_clicked = reverseButtonClicked(btn_direction_e_clicked);
			if(btn_direction_e_clicked){
				btn_direction_e.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_e.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(4);
		}else if(btn_direction_sw.isPressed()){
			btn_direction_sw_clicked = reverseButtonClicked(btn_direction_sw_clicked);
			if(btn_direction_sw_clicked){
				btn_direction_sw.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_sw.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(5);
		}else if(btn_direction_s.isPressed()){
			btn_direction_s_clicked = reverseButtonClicked(btn_direction_s_clicked);
			if(btn_direction_s_clicked){
				btn_direction_s.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_s.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(6);
		}else if(btn_direction_se.isPressed()){
			btn_direction_se_clicked = reverseButtonClicked(btn_direction_se_clicked);
			if(btn_direction_se_clicked){
				btn_direction_se.setBackgroundResource(R.drawable.small_grey);
			}else{
				btn_direction_se.setBackgroundResource(R.drawable.small_grey_red);
			}
			checkDirectionFromAndTo(7);
		}
		
		if(checkIfPass()){
			updateAllCounts();
		}
	}
	
	private boolean reverseButtonClicked(boolean buttonClicked){
		return !buttonClicked;
	}
	
	private void checkDirectionFromAndTo(int direction){
		boolean fromFound = false;
		boolean toFound = false;
		int fromDirection = -1;
		for (int x = 0; x < 8; x++) {
			if(directionFrom[x]){
				fromFound = true;
				fromDirection = x;
			}
			if(directionTo[x]){
				toFound = true;
			}
			if(fromFound && toFound){
				break;
			}
		}
		
		if(!fromFound && !toFound){
			directionFrom[direction] = true;
		}else if(fromFound && !toFound){
			if(direction == fromDirection){
				directionFrom[direction] = false;
			}else{
				directionTo[direction] = true;
			}
		}
	}
	
	private boolean checkIfPass(){
		boolean result = false;
		boolean directionFromFound = false;
		boolean directionToFound = false;
		for (int x = 0; x < 8; x++) {
			if(directionFrom[x]){
				directionFromFound = true;
			}
			if(directionTo[x]){
				directionToFound = true;
			}
			if(directionFromFound && directionToFound){
				break;
			}
		}
		
		if(directionFromFound && directionToFound){
			result = true;
		}
		
		Toast.makeText(this, "DirectionFromFound: " + directionFromFound + " || DirectionToFound: " + directionToFound, Toast.LENGTH_SHORT).show();
		
		
		return result;
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
			break;
		case "Truck":
			truck++;
			currentlySelectedCount = truck;
			passed = true;
			break;
		case "Car":
			car++;
			currentlySelectedCount = car;
			passed = true;
			break;
		case "Motor Bike":
			motorBike++;
			currentlySelectedCount = motorBike;
			passed = true;
			break;
		case "Pedestrian (No Aid)":
			pedestrian++;
			currentlySelectedCount = pedestrian;
			passed = true;
			break;
		case "Cane (Poor Eyesight)":
			cane++;
			currentlySelectedCount = cane;
			passed = true;
			break;
		case "Guide Dog":
			dog++;
			currentlySelectedCount = dog;
			passed = true;
			break;
		case "Mobility Scooter":
			mobilityScooter++;
			currentlySelectedCount = mobilityScooter;
			passed = true;
			break;
		case "Walking Stick / Crutch (1)":
			crutches_1++;
			currentlySelectedCount = crutches_1;
			passed = true;
			break;
		case "Walking Sticks / Crutches (2)":
			crutches_2++;
			currentlySelectedCount = crutches_2;
			passed = true;
			break;
		case "Wheel Chair (Assisted)":
			wheelChair_assisted++;
			currentlySelectedCount = wheelChair_assisted;
			passed = true;
			break;
		case "Wheel Chair (Manual)":
			wheelChair_manual++;
			currentlySelectedCount = wheelChair_manual;
			passed = true;
			break;
		case "Wheel Chair (Powered)":
			wheelChair_powered++;
			currentlySelectedCount = wheelChair_powered;
			passed = true;
			break;
		case "Push Chair / Buggy":
			pushChair++;
			currentlySelectedCount = pushChair;
			passed = true;
			break;
		case "Skateboard":
			skateboard++;
			currentlySelectedCount = skateboard;
			passed = true;
			break;
		case "Manual Scooter":
			manualScooter++;
			currentlySelectedCount = manualScooter;
			passed = true;
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
	 * This method checks to make sure that there is a value in direction from and direction to.
	 * If this is true. It will begin to update all the counts then default the current selected object back to pedestrian.
	 */
	private void updateAllCounts(){
		increaseObjectCount();
		increaseTotalCount();
		updateDirectionCount();
		initialiseDirectionFromTo();
		defaultPedestrian();
		resetDirectionButtonBackground();
		initialiseDirectionButtonClicked();
	}
	
	/*
	 * This method is used to increment the total and individual value of the pedestrian/vehicle counts that went a certain direction.
	 */
	private void updateDirectionCount(){
		int directionFromPosition = 0;
		int directionToPosition = 0;
		boolean directionFromFound = false;
		boolean directionToFound = false;
		for (int x = 0; x < directionFrom.length; x++) {
			if(directionFrom[x]){
				directionFromPosition = x;
				directionFromFound = true;
			}
			if(directionTo[x]){
				directionToPosition = x;
				directionToFound = true;
			}
			if(directionFromFound && directionToFound){
				break;
			}
		}
		
		switch (currentlySelectedObject) {
		case "Bus":
			checkBus(directionFromPosition, directionToPosition);
			break;
		case "Truck":
			checkTruck(directionFromPosition, directionToPosition);
			break;
		case "Car":
			checkCar(directionFromPosition, directionToPosition);
			break;
		case "Motor Bike":
			checkMotorbike(directionFromPosition, directionToPosition);
			break;
		case "Pedestrian (No Aid)":
			checkPedestrian(directionFromPosition, directionToPosition);
			break;
		case "Cane (Poor Eyesight)":
			checkCane(directionFromPosition, directionToPosition);
			break;
		case "Guide Dog":
			checkDog(directionFromPosition, directionToPosition);
			break;
		case "Mobility Scooter":
			checkMobilityScooter(directionFromPosition, directionToPosition);
			break;
		case "Walking Stick / Crutch (1)":
			checkCrutches1(directionFromPosition, directionToPosition);
			break;
		case "Walking Sticks / Crutches (2)":
			checkCrutches2(directionFromPosition, directionToPosition);
			break;
		case "Wheel Chair (Assisted)":
			checkWheelChairAssisted(directionFromPosition, directionToPosition);
			break;
		case "Wheel Chair (Manual)":
			checkWheelChairManual(directionFromPosition, directionToPosition);
			break;
		case "Wheel Chair (Powered)":
			checkWheelChairPowered(directionFromPosition, directionToPosition);
			break;
		case "Push Chair / Buggy":
			checkPushchair(directionFromPosition, directionToPosition);
			break;
		case "Skateboard":
			checkSkateboard(directionFromPosition, directionToPosition);
			break;
		case "Manual Scooter":
			checkManualScooter(directionFromPosition, directionToPosition);
			break;
		default:
			Toast.makeText(this, "None of them match", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	private void checkBus(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastBus++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastBus++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastBus++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastBus++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastBus++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastBus++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastBus++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestBus++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthBus++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastBus++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestBus++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastBus++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestBus++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthBus++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkCar(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCar++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCar++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCar++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCar++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCar++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCar++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCar++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCar++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCar++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCar++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCar++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCar++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCar++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCar++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkTruck(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastTruck++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastTruck++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastTruck++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastTruck++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastTruck++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastTruck++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastTruck++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestTruck++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthTruck++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastTruck++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestTruck++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastTruck++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestTruck++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthTruck++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkMotorbike(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMotorBike++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMotorBike++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMotorBike++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMotorBike++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMotorBike++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMotorBike++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMotorBike++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMotorBike++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMotorBike++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMotorBike++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMotorBike++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMotorBike++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMotorBike++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMotorBike++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkPedestrian(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPedestrian++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPedestrian++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPedestrian++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPedestrian++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPedestrian++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPedestrian++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPedestrian++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPedestrian++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPedestrian++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPedestrian++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPedestrian++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPedestrian++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPedestrian++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPedestrian++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkCrutches1(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches1++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches1++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches1++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches1++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches1++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches1++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches1++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches1++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches1++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches1++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches1++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches1++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches1++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches1++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkCrutches2(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches2++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches2++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches2++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches2++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches2++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches2++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches2++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches2++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches2++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches2++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches2++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches2++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches2++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches2++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkCane(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCane++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCane++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCane++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCane++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCane++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCane++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCane++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCane++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCane++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCane++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCane++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCane++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCane++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCane++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkDog(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastDog++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastDog++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastDog++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastDog++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastDog++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastDog++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastDog++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestDog++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthDog++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastDog++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestDog++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastDog++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestDog++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthDog++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkMobilityScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMobilityScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMobilityScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMobilityScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMobilityScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMobilityScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMobilityScooter++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMobilityScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMobilityScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMobilityScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMobilityScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMobilityScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMobilityScooter++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMobilityScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMobilityScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkWheelChairAssisted(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairAssisted++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairAssisted++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairAssisted++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairAssisted++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairAssisted++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairAssisted++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairAssisted++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairAssisted++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkWheelChairManual(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairManual++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairManual++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairManual++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairManual++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairManual++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairManual++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairManual++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairManual++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairManual++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairManual++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairManual++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairManual++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairManual++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairManual++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkWheelChairPowered(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairPowered++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairPowered++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairPowered++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairPowered++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairPowered++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairPowered++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairPowered++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairPowered++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkPushchair(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPushChair++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPushChair++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPushChair++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPushChair++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPushChair++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPushChair++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPushChair++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPushChair++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPushChair++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPushChair++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPushChair++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPushChair++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPushChair++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPushChair++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkSkateboard(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastSkateboard++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastSkateboard++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastSkateboard++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastSkateboard++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastSkateboard++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastSkateboard++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastSkateboard++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestSkateboard++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthSkateboard++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastSkateboard++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestSkateboard++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastSkateboard++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestSkateboard++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthSkateboard++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}
	}
	
	private void checkManualScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastManualScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West(" + northWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East(" + northEastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastManualScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastManualScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastManualScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastManualScooter++;
			eastTotal++;
			btn_direction_e.setText("East (" + eastTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastManualScooter++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastManualScooter++;
			southEastTotal++;
			btn_direction_se.setText("South-East (" + southEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestManualScooter++;
			northWestTotal++;
			btn_direction_nw.setText("North-West (" + northWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthManualScooter++;
			northTotal++;
			btn_direction_n.setText("North (" + northTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastManualScooter++;
			northEastTotal++;
			btn_direction_ne.setText("North-East (" + northEastTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestManualScooter++;
			westTotal++;
			btn_direction_w.setText("West (" + westTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastManualScooter++;
			eastTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestManualScooter++;
			southWestTotal++;
			btn_direction_sw.setText("South-West (" + southWestTotal + ")");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthManualScooter++;
			southTotal++;
			btn_direction_s.setText("South (" + southTotal + ")");
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
			 * 					From WEST to SOUTH							From WEST to EAST...
			 * 
			 * TIME 			CARS 	BUSES 	TRUCKS	 	MOTORCYCLES		CARS	BUSES...	
			 * 8:00 to 8:15		25		2		13			7				2		6
			 */
			fileWriter.append("Location: " + streetNumandName + 
					", " + suburbName + ", "+ city +", " + postCode + ", " + "\n" + locDescription +
					"\n \n");
			fileWriter.append("Date: " + currentDate + "\n");
			//Need to write directions From and To -Jean-Yves
			switch (intersectionType) {
			case "3 Way Intersection":
				fileWriter.append(" ,From WEST to EAST, , , , , From WEST to SOUTH, , , , , From SOUTH to WEST, , , , , From SOUTH to EAST, From EAST to SOUTH, , , , From EAST to SOUTH");
				fileWriter.append("\n \n");
				fileWriter.append("Time, Cars, Buses, Trucks, Motorcycles, , Cars, Buses, Trucks, Motorcycles, , Cars, Buses, Trucks, Motorcycles, , Cars, Buses, Trucks, Motorcycles, , Cars, Buses, Trucks, Motorcycles ");
				fileWriter.append("\n");
				break;
			case "4 Way Intersection":
				
				break;
			case "5 Way Intersection":
				
				break;
			case "6 Way Intersection":
				
				break;
		
			default:
				userMessage("Data not saved!");
				break;
			}
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
				writer.append(sessionStartTime + " TO " + currentTime + ",");
				writer.append(car+ "," + bus+ "," + truck + "," + motorBike + ",");
				writer.append("\n");
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

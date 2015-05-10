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
	 * Vehicles -> Bus, Car, Truck, Motorbike
	 * Pedestrian -> Pedestrian(No Aid), Crutches1, Crutches2, Cane, Guide Dog,
	 * 					Mobility Scooter, Wheelchair(Assisted, Manual, Powered),
	 * 					Push Chair, Skateboard, Manual Scooter
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
	String[] intersectionPickedNames;
	
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
		
		
		//Sets the Vehicle button to be invisible if no intersection was chosen (Pretty much Pedestrian Count)
		if(intersectionType.equals("No Intersection")){
			findViewById(R.id.cs_btn_vehicles).setVisibility(4);
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
		initialiseIntersectionName();
		
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
	
	private void initialiseIntersectionName(){
		intersectionPickedNames = new String[8];
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
	
	//Methods which initilise the count objects
	
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
	
	////////////////////////////////////////////////////////////////////////
	
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
	public void sendClickMessage(String key, String stringValue, boolean[] booleanValue, String[] stringArrayValue) {
		if(key.equals("IntersectionNamePicker")){
			intersectionPickedNames = stringArrayValue;
			updateAllDirectionalButtons();
		}else if(!key.equals("Comment")){
			updateCurrentObjectTo(stringValue);
			updateCurrentlySelectedObject(getCurrentObjectCount(stringValue));
		}else{
			comments = stringValue;
			commentViewable.setText(comments);
		}
	}
	
	@SuppressLint("NewApi")
	public void showIntersectionNamePicker(View view){
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		Bundle args = new Bundle();
		args.putBooleanArray("IntersectionsPicked", intersectionsPicked);
		dialog.setArguments(args);
		dialog.show(manager, "intersectionNamePicker");
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
		
//		Toast.makeText(this, "DirectionFromFound: " + directionFromFound + " || DirectionToFound: " + directionToFound, Toast.LENGTH_SHORT).show();
		
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
//			Toast.makeText(this, currentlySelectedObject + ": " + currentlySelectedCount, Toast.LENGTH_SHORT).show();
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
		updateAllDirectionalButtons();
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
	
	//Methods to check the directionTo and directionFrom of count objects
	
	private void checkBus(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastBus++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastBus++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastBus++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastBus++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastBus++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthBus++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastBus++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastBus++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestBus++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthBus++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastBus++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestBus++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastBus++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestBus++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthBus++;
			southTotal++;
		}
	}
	
	private void checkCar(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCar++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCar++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCar++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCar++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCar++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCar++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCar++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCar++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCar++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCar++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCar++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCar++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCar++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCar++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCar++;
			southTotal++;
		}
	}
	
	private void checkTruck(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastTruck++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastTruck++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastTruck++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastTruck++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastTruck++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthTruck++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastTruck++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastTruck++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestTruck++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthTruck++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastTruck++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestTruck++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastTruck++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestTruck++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthTruck++;
			southTotal++;
		}
	}
	
	private void checkMotorbike(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMotorBike++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMotorBike++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMotorBike++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMotorBike++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMotorBike++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMotorBike++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMotorBike++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMotorBike++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMotorBike++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMotorBike++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMotorBike++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMotorBike++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMotorBike++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMotorBike++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMotorBike++;
			southTotal++;
		}
	}
	
	private void checkPedestrian(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPedestrian++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPedestrian++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPedestrian++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPedestrian++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPedestrian++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPedestrian++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPedestrian++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPedestrian++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPedestrian++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPedestrian++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPedestrian++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPedestrian++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPedestrian++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPedestrian++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPedestrian++;
			southTotal++;
		}
	}
	
	private void checkCrutches1(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches1++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches1++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches1++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches1++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches1++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches1++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches1++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches1++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches1++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches1++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches1++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches1++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches1++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches1++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches1++;
			southTotal++;
		}
	}
	
	private void checkCrutches2(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches2++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches2++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches2++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches2++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches2++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches2++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches2++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches2++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches2++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches2++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches2++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches2++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches2++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches2++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches2++;
			southTotal++;
		}
	}
	
	private void checkCane(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCane++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCane++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCane++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCane++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCane++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCane++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCane++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCane++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCane++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCane++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCane++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCane++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCane++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCane++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCane++;
			southTotal++;
		}
	}
	
	private void checkDog(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastDog++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastDog++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastDog++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastDog++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastDog++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthDog++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastDog++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastDog++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestDog++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthDog++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastDog++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestDog++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastDog++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestDog++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthDog++;
			southTotal++;
		}
	}
	
	private void checkMobilityScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMobilityScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMobilityScooter++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMobilityScooter++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMobilityScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMobilityScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMobilityScooter++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMobilityScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMobilityScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMobilityScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMobilityScooter++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMobilityScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMobilityScooter++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMobilityScooter++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMobilityScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMobilityScooter++;
			southTotal++;
		}
	}
	
	private void checkWheelChairAssisted(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairAssisted++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairAssisted++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairAssisted++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairAssisted++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairAssisted++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairAssisted++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairAssisted++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairAssisted++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairAssisted++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairAssisted++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairAssisted++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairAssisted++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairAssisted++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairAssisted++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairAssisted++;
			southTotal++;
		}
	}
	
	private void checkWheelChairManual(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairManual++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairManual++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairManual++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairManual++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairManual++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairManual++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairManual++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairManual++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairManual++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairManual++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairManual++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairManual++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairManual++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairManual++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairManual++;
			southTotal++;
		}
	}
	
	private void checkWheelChairPowered(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelChairPowered++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelChairPowered++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelChairPowered++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelChairPowered++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelChairPowered++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelChairPowered++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelChairPowered++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelChairPowered++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelChairPowered++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelChairPowered++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelChairPowered++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelChairPowered++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelChairPowered++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelChairPowered++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelChairPowered++;
			southTotal++;
		}
	}
	
	private void checkPushchair(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPushChair++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPushChair++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPushChair++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPushChair++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPushChair++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPushChair++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPushChair++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPushChair++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPushChair++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPushChair++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPushChair++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPushChair++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPushChair++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPushChair++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPushChair++;
			southTotal++;
		}
	}
	
	private void checkSkateboard(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastSkateboard++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastSkateboard++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastSkateboard++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastSkateboard++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastSkateboard++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthSkateboard++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastSkateboard++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastSkateboard++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestSkateboard++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthSkateboard++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastSkateboard++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestSkateboard++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastSkateboard++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestSkateboard++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthSkateboard++;
			southTotal++;
		}
	}
	
	private void checkManualScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastManualScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastManualScooter++;
			southEastTotal++;
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastManualScooter++;
			southEastTotal++;
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastManualScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastManualScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthManualScooter++;
			southTotal++;
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastManualScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastManualScooter++;
			southEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestManualScooter++;
			northWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthManualScooter++;
			northTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastManualScooter++;
			northEastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestManualScooter++;
			westTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastManualScooter++;
			eastTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestManualScooter++;
			southWestTotal++;
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthManualScooter++;
			southTotal++;
		}
	}
	
	private void updateAllDirectionalButtons(){
		if(btn_direction_nw != null){
			if(intersectionPickedNames[0] != null){
				btn_direction_nw.setText(intersectionPickedNames[0] + " (" + northWestTotal + ")");
			}else{
				btn_direction_nw.setText("North-West (" + northWestTotal + ")");
			}
		}
		if(btn_direction_n != null){
			if(intersectionPickedNames[1] != null){
				btn_direction_n.setText(intersectionPickedNames[1] + " (" + northTotal + ")");
			}else{
				btn_direction_n.setText("North (" + northTotal + ")");
			}
		}
		if(btn_direction_ne != null){
			if(intersectionPickedNames[2] != null){
				btn_direction_ne.setText(intersectionPickedNames[2] + " (" + northEastTotal + ")");
			}else{
				btn_direction_ne.setText("North-East (" + northEastTotal + ")");
			}
		}
		if(btn_direction_w != null){
			if(intersectionPickedNames[3] != null){
				btn_direction_w.setText(intersectionPickedNames[3] + " (" + westTotal + ")");
			}else{
				btn_direction_w.setText("West (" + westTotal + ")");
			}
		}
		if(btn_direction_e != null){
			if(intersectionPickedNames[4] != null){
				btn_direction_e.setText(intersectionPickedNames[4] + " (" + eastTotal + ")");
			}else{
				btn_direction_e.setText("East (" + eastTotal + ")");
			}
		}
		if(btn_direction_sw != null){
			if(intersectionPickedNames[5] != null){
				btn_direction_sw.setText(intersectionPickedNames[5] + " (" + southWestTotal + ")");
			}else{
				btn_direction_sw.setText("South-West (" + southWestTotal + ")");
			}
		}
		if(btn_direction_s != null){
			if(intersectionPickedNames[6] != null){
				btn_direction_s.setText(intersectionPickedNames[6] + " (" + southTotal + ")");
			}else{
				btn_direction_s.setText("South (" + southTotal + ")");
			}
		}
		if(btn_direction_se != null){
			if(intersectionPickedNames[7] != null){
				btn_direction_se.setText(intersectionPickedNames[7] + " (" + southEastTotal + ")");
			}else{
				btn_direction_se.setText("South-East (" + southEastTotal + ")");
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * This class will handle the 15 minute countdown and subsequently save the data. 
	 * @author Richard and Jean-Yves
	 */
	
	private class CountDownTimer extends android.os.CountDownTimer{
		
		private String ms, minutes, seconds;
		
		//-------------Jean-Yves' Initializations-----------------------
		private SimpleDateFormat df = new SimpleDateFormat("h:mm a");
		
		private String sessionStartTime;
		private String currentTime;
		private boolean directionTitleAppended;

		private Calendar cal = Calendar.getInstance();
		
		//---------------------Richard Fong---------------------------------
		
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
		
		//------------------------Jean-Yves Kwibuka 1245654----------------------
		
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
						
			File folder = Environment.getExternalStoragePublicDirectory("TDG_DATA");
			String file = fileDate+"_Traffic_Count_Data.csv";
			File csvfile = new File(folder, file);
				try {
					if(!folder.exists()){
						//Make the directory
						folder.mkdir();						
					
					}if(folder.exists()){
						//Open the file to be written to. Re-initialization is necessary in this case.
						FileWriter writer = new FileWriter(csvfile, true);
						//WRITE DATA
						writeIntersectionCountData(writer);
						flushAndCloseWriter(writer);
					}else{
						userMessage("Please mount your SD card");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		
		/*
		 * Intersection Count Data is processed through this method.
		 * @author: Jean-Yves
		 * @since: 2 April 2015
		 */
		public void writeIntersectionCountData(FileWriter fileWriter) throws IOException{
			//These should work, once we make sure the submit button sets the values from Textviews
			String streetNumandName = CountSetup.getStreetNumAndName();
			String currentDate = CountSetup.getCurrentDate();
			String suburbName = CountSetup.getSuburbName();
			String city = CountSetup.getCityName();
			String postCode = CountSetup.getAreaCode();
			String locDescription = CountSetup.getAreaDescript();
			//Make sure the above string don't return null
			
			//The header will look like below: -Jean-Yves
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
				
				for(int x = 0; x < 8; x++){
					userMessage("Passed first FOR");
					if(intersectionsPicked[x]){
						directionTitleAppended = false;
						for(int y = 0; y < 8; y++){
							userMessage("Passed second FOR");
							if(intersectionsPicked[y] && y != x){
								userMessage("Made TO comparison with Array");
								//FROM NORTH-WEST TO ...
								if(x == 0 && y == 1){
									//North-west To North
									userMessage("Reached North-West to North IF");
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to North , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										//flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToNorthCar, northWestToNorthBus, northWestToNorthTruck,
													northWestToNorthMotorBike, northWestToNorthPedestrian, northWestToNorthCrutches1,
													northWestToNorthCrutches2, northWestToNorthCane, northWestToNorthDog, northWestToNorthMobilityScooter,
													northWestToNorthWheelChairAssisted, northWestToNorthWheelChairManual, northWestToNorthWheelChairPowered,
													northWestToNorthPushChair, northWestToNorthSkateboard, northWestToNorthManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 2){
									//North-west to North-East
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to North-East , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToNorthEastCar, northWestToNorthEastBus, northWestToNorthEastTruck,
											northWestToNorthEastMotorBike, northWestToNorthEastPedestrian, northWestToNorthEastCrutches1,
											northWestToNorthEastCrutches2, northWestToNorthEastCane, northWestToNorthEastDog, northWestToNorthEastMobilityScooter,
											northWestToNorthEastWheelChairAssisted, northWestToNorthEastWheelChairManual, northWestToNorthEastWheelChairPowered,
											northWestToNorthEastPushChair, northWestToNorthEastSkateboard, northWestToNorthEastManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 3){
									//North-west to West
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to West , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToWestCar, northWestToWestBus, northWestToWestTruck,
											northWestToWestMotorBike, northWestToWestPedestrian, northWestToWestCrutches1,
											northWestToWestCrutches2, northWestToWestCane, northWestToWestDog, northWestToWestMobilityScooter,
											northWestToWestWheelChairAssisted, northWestToWestWheelChairManual, northWestToWestWheelChairPowered,
											northWestToWestPushChair, northWestToWestSkateboard, northWestToWestManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 4){
									//North-west to East
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to East , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToEastCar, northWestToEastBus, northWestToEastTruck,
											northWestToEastMotorBike, northWestToEastPedestrian, northWestToEastCrutches1,
											northWestToEastCrutches2, northWestToEastCane, northWestToEastDog, northWestToEastMobilityScooter,
											northWestToEastWheelChairAssisted, northWestToEastWheelChairManual, northWestToEastWheelChairPowered,
											northWestToEastPushChair, northWestToEastSkateboard, northWestToEastManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 5){
									//North-west to South-West
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to SouthWest , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToSouthWestCar, northWestToSouthWestBus, northWestToSouthWestTruck,
											northWestToSouthWestMotorBike, northWestToSouthWestPedestrian, northWestToSouthWestCrutches1,
											northWestToSouthWestCrutches2, northWestToSouthWestCane, northWestToSouthWestDog, northWestToSouthWestMobilityScooter,
											northWestToSouthWestWheelChairAssisted, northWestToSouthWestWheelChairManual, northWestToSouthWestWheelChairPowered,
											northWestToSouthWestPushChair, northWestToSouthWestSkateboard, northWestToSouthWestManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 6){
									//North-west to South
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to South , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToSouthCar, northWestToSouthBus, northWestToSouthTruck,
											northWestToSouthMotorBike, northWestToSouthPedestrian, northWestToSouthCrutches1,
											northWestToSouthCrutches2, northWestToSouthCane, northWestToSouthDog, northWestToSouthMobilityScooter,
											northWestToSouthWheelChairAssisted, northWestToSouthWheelChairManual, northWestToSouthWheelChairPowered,
											northWestToSouthPushChair, northWestToSouthSkateboard, northWestToSouthManualScooter);
									userMessage("Data save complete");
								}else if(x == 0 && y == 7){
									//North-west to South-East
									if (! directionTitleAppended) {
										fileWriter.append(" ,From North-West to South-East , , , , , , , , , , , , , , , , , ,");
										fileWriter.append("\n \n");
										flushAndCloseWriter(fileWriter);
										appendIntersectionCountsHeader(fileWriter);
										directionTitleAppended = true;
									}
									appendCountables(fileWriter, northWestToSouthEastCar, northWestToSouthEastBus, northWestToSouthEastTruck,
											northWestToSouthEastMotorBike, northWestToSouthEastPedestrian, northWestToSouthEastCrutches1,
											northWestToSouthEastCrutches2, northWestToSouthEastCane, northWestToSouthEastDog, northWestToSouthEastMobilityScooter,
											northWestToSouthEastWheelChairAssisted, northWestToSouthEastWheelChairManual, northWestToSouthEastWheelChairPowered,
											northWestToSouthEastPushChair, northWestToSouthEastSkateboard, northWestToSouthEastManualScooter);
									userMessage("Data save complete");
								}//FROM NORTH TO ...
								else if(x == 1 && y == 0){
									//North to North-West
									appendCountables(fileWriter, northToNorthWestCar, northToNorthWestBus, northToNorthWestTruck,
											northToNorthWestMotorBike, northToNorthWestPedestrian, northToNorthWestCrutches1,
											northToNorthWestCrutches2, northToNorthWestCane, northToNorthWestDog, northToNorthWestMobilityScooter,
											northToNorthWestWheelChairAssisted, northToNorthWestWheelChairManual, northToNorthWestWheelChairPowered,
											northToNorthWestPushChair, northToNorthWestSkateboard, northToNorthWestManualScooter);
								}else if(x == 1 && y == 2){
									//North to North-East
									appendCountables(fileWriter, northToNorthEastCar, northToNorthEastBus, northToNorthEastTruck,
											northToNorthEastMotorBike, northToNorthEastPedestrian, northToNorthEastCrutches1,
											northToNorthEastCrutches2, northToNorthEastCane, northToNorthEastDog, northToNorthEastMobilityScooter,
											northToNorthEastWheelChairAssisted, northToNorthEastWheelChairManual, northToNorthEastWheelChairPowered,
											northToNorthEastPushChair, northToNorthEastSkateboard, northToNorthEastManualScooter);
								}else if(x == 1 && y == 3){
									//North to West
									appendCountables(fileWriter, northToWestCar, northToWestBus, northToWestTruck,
											northToWestMotorBike, northToWestPedestrian, northToWestCrutches1,
											northToWestCrutches2, northToWestCane, northToWestDog, northToWestMobilityScooter,
											northToWestWheelChairAssisted, northToWestWheelChairManual, northToWestWheelChairPowered,
											northToWestPushChair, northToWestSkateboard, northToWestManualScooter);
								}else if(x == 1 && y == 4){
									//North to East
									appendCountables(fileWriter, northToEastCar, northToEastBus, northToEastTruck,
											northToEastMotorBike, northToEastPedestrian, northToEastCrutches1,
											northToEastCrutches2, northToEastCane, northToEastDog, northToEastMobilityScooter,
											northToEastWheelChairAssisted, northToEastWheelChairManual, northToEastWheelChairPowered,
											northToEastPushChair, northToEastSkateboard, northToEastManualScooter);
								}else if(x == 1 && y == 5){
									//North to South-West
									appendCountables(fileWriter, northToSouthWestCar, northToSouthWestBus, northToSouthWestTruck,
											northToSouthWestMotorBike, northToSouthWestPedestrian, northToSouthWestCrutches1,
											northToSouthWestCrutches2, northToSouthWestCane, northToSouthWestDog, northToSouthWestMobilityScooter,
											northToSouthWestWheelChairAssisted, northToSouthWestWheelChairManual, northToSouthWestWheelChairPowered,
											northToSouthWestPushChair, northToSouthWestSkateboard, northToSouthWestManualScooter);
								}else if(x == 1 && y == 6){
									//North to South
									appendCountables(fileWriter, northToSouthCar, northToSouthBus, northToSouthTruck,
											northToSouthMotorBike, northToSouthPedestrian, northToSouthCrutches1,
											northToSouthCrutches2, northToSouthCane, northToSouthDog, northToSouthMobilityScooter,
											northToSouthWheelChairAssisted, northToSouthWheelChairManual, northToSouthWheelChairPowered,
											northToSouthPushChair, northToSouthSkateboard, northToSouthManualScooter);
								}else if(x == 1 && y == 7){
									//North to South-East
									appendCountables(fileWriter, northToSouthEastCar, northToSouthEastBus, northToSouthEastTruck,
											northToSouthEastMotorBike, northToSouthEastPedestrian, northToSouthEastCrutches1,
											northToSouthEastCrutches2, northToSouthEastCane, northToSouthEastDog, northToSouthEastMobilityScooter,
											northToSouthEastWheelChairAssisted, northToSouthEastWheelChairManual, northToSouthEastWheelChairPowered,
											northToSouthEastPushChair, northToSouthEastSkateboard, northToSouthEastManualScooter);
								}//FROM NORTH-EAST TO ...
								else if(x == 2 && y == 0){
									//North-East to North-West
									appendCountables(fileWriter, northEastToNorthWestCar, northEastToNorthWestBus, northEastToNorthWestTruck,
											northEastToNorthWestMotorBike, northEastToNorthWestPedestrian, northEastToNorthWestCrutches1,
											northEastToNorthWestCrutches2, northEastToNorthWestCane, northEastToNorthWestDog, northEastToNorthWestMobilityScooter,
											northEastToNorthWestWheelChairAssisted, northEastToNorthWestWheelChairManual, northEastToNorthWestWheelChairPowered,
											northEastToNorthWestPushChair, northEastToNorthWestSkateboard, northEastToNorthWestManualScooter);
								}else if(x == 2 && y == 1){
									//North-East to North
									appendCountables(fileWriter, northEastToNorthCar, northEastToNorthBus, northEastToNorthTruck,
											northEastToNorthMotorBike, northEastToNorthPedestrian, northEastToNorthCrutches1,
											northEastToNorthCrutches2, northEastToNorthCane, northEastToNorthDog, northEastToNorthMobilityScooter,
											northEastToNorthWheelChairAssisted, northEastToNorthWheelChairManual, northEastToNorthWheelChairPowered,
											northEastToNorthPushChair, northEastToNorthSkateboard, northEastToNorthManualScooter);
								}else if(x == 2 && y == 3){
									//North-East to West
									appendCountables(fileWriter, northEastToWestCar, northEastToWestBus, northEastToWestTruck,
											northEastToWestMotorBike, northEastToWestPedestrian, northEastToWestCrutches1,
											northEastToWestCrutches2, northEastToWestCane, northEastToWestDog, northEastToWestMobilityScooter,
											northEastToWestWheelChairAssisted, northEastToWestWheelChairManual, northEastToWestWheelChairPowered,
											northEastToWestPushChair, northEastToWestSkateboard, northEastToWestManualScooter);
								}else if(x == 2 && y == 4){
									//North-East to East
									appendCountables(fileWriter, northEastToEastCar, northEastToEastBus, northEastToEastTruck,
											northEastToEastMotorBike, northEastToEastPedestrian, northEastToEastCrutches1,
											northEastToEastCrutches2, northEastToEastCane, northEastToEastDog, northEastToEastMobilityScooter,
											northEastToEastWheelChairAssisted, northEastToEastWheelChairManual, northEastToEastWheelChairPowered,
											northEastToEastPushChair, northEastToEastSkateboard, northEastToEastManualScooter);
								}else if(x == 2 && y == 5){
									//North-East to South-West
									appendCountables(fileWriter, northEastToSouthWestCar, northEastToSouthWestBus, northEastToSouthWestTruck,
											northEastToSouthWestMotorBike, northEastToSouthWestPedestrian, northEastToSouthWestCrutches1,
											northEastToSouthWestCrutches2, northEastToSouthWestCane, northEastToSouthWestDog, northEastToSouthWestMobilityScooter,
											northEastToSouthWestWheelChairAssisted, northEastToSouthWestWheelChairManual, northEastToSouthWestWheelChairPowered,
											northEastToSouthWestPushChair, northEastToSouthWestSkateboard, northEastToSouthWestManualScooter);
								}else if(x == 2 && y == 6){
									//North-East to South
									appendCountables(fileWriter, northEastToSouthCar, northEastToSouthBus, northEastToSouthTruck,
											northEastToSouthMotorBike, northEastToSouthPedestrian, northEastToSouthCrutches1,
											northEastToSouthCrutches2, northEastToSouthCane, northEastToSouthDog, northEastToSouthMobilityScooter,
											northEastToSouthWheelChairAssisted, northEastToSouthWheelChairManual, northEastToSouthWheelChairPowered,
											northEastToSouthPushChair, northEastToSouthSkateboard, northEastToSouthManualScooter);
								}else if(x == 2 && y == 7){
									//North-East to South-East
									appendCountables(fileWriter, northEastToSouthEastCar, northEastToSouthEastBus, northEastToSouthEastTruck,
											northEastToSouthEastMotorBike, northEastToSouthEastPedestrian, northEastToSouthEastCrutches1,
											northEastToSouthEastCrutches2, northEastToSouthEastCane, northEastToSouthEastDog, northEastToSouthEastMobilityScooter,
											northEastToSouthEastWheelChairAssisted, northEastToSouthEastWheelChairManual, northEastToSouthEastWheelChairPowered,
											northEastToSouthEastPushChair, northEastToSouthEastSkateboard, northEastToSouthEastManualScooter);
								}//FROM West TO ...
								else if(x == 3 && y == 0){
									//West to North-West
									appendCountables(fileWriter, westToNorthWestCar, westToNorthWestBus, westToNorthWestTruck,
											westToNorthWestMotorBike, westToNorthWestPedestrian, westToNorthWestCrutches1,
											westToNorthWestCrutches2, westToNorthWestCane, westToNorthWestDog, westToNorthWestMobilityScooter,
											westToNorthWestWheelChairAssisted, westToNorthWestWheelChairManual, westToNorthWestWheelChairPowered,
											westToNorthWestPushChair, westToNorthWestSkateboard, westToNorthWestManualScooter);
								}else if(x == 3 && y == 1){
									//West to North
									appendCountables(fileWriter, westToNorthCar, westToNorthBus, westToNorthTruck,
											westToNorthMotorBike, westToNorthPedestrian, westToNorthCrutches1,
											westToNorthCrutches2, westToNorthCane, westToNorthDog, westToNorthMobilityScooter,
											westToNorthWheelChairAssisted, westToNorthWheelChairManual, westToNorthWheelChairPowered,
											westToNorthPushChair, westToNorthSkateboard, westToNorthManualScooter);
								}else if(x == 3 && y == 2){
									//West to North-East
									appendCountables(fileWriter, westToNorthEastCar, westToNorthEastBus, westToNorthEastTruck,
											westToNorthEastMotorBike, westToNorthEastPedestrian, westToNorthEastCrutches1,
											westToNorthEastCrutches2, westToNorthEastCane, westToNorthEastDog, westToNorthEastMobilityScooter,
											westToNorthEastWheelChairAssisted, westToNorthEastWheelChairManual, westToNorthEastWheelChairPowered,
											westToNorthEastPushChair, westToNorthEastSkateboard, westToNorthEastManualScooter);
								}else if(x == 3 && y == 4){
									//West to East
									appendCountables(fileWriter, westToEastCar, westToEastBus, westToEastTruck,
											westToEastMotorBike, westToEastPedestrian, westToEastCrutches1,
											westToEastCrutches2, westToEastCane, westToEastDog, westToEastMobilityScooter,
											westToEastWheelChairAssisted, westToEastWheelChairManual, westToEastWheelChairPowered,
											westToEastPushChair, westToEastSkateboard, westToEastManualScooter);
								}else if(x == 3 && y == 5){
									//West to South-West
									appendCountables(fileWriter, westToSouthWestCar, westToSouthWestBus, westToSouthWestTruck,
											westToSouthWestMotorBike, westToSouthWestPedestrian, westToSouthWestCrutches1,
											westToSouthWestCrutches2, westToSouthWestCane, westToSouthWestDog, westToSouthWestMobilityScooter,
											westToSouthWestWheelChairAssisted, westToSouthWestWheelChairManual, westToSouthWestWheelChairPowered,
											westToSouthWestPushChair, westToSouthWestSkateboard, westToSouthWestManualScooter);
								}else if(x == 3 && y == 6){
									//West to South
									appendCountables(fileWriter, westToSouthCar, westToSouthBus, westToSouthTruck,
											westToSouthMotorBike, westToSouthPedestrian, westToSouthCrutches1,
											westToSouthCrutches2, westToSouthCane, westToSouthDog, westToSouthMobilityScooter,
											westToSouthWheelChairAssisted, westToSouthWheelChairManual, westToSouthWheelChairPowered,
											westToSouthPushChair, westToSouthSkateboard, westToSouthManualScooter);
								}else if(x == 3 && y == 7){
									//West to South-East
									appendCountables(fileWriter, westToSouthEastCar, westToSouthEastBus, westToSouthEastTruck,
											westToSouthEastMotorBike, westToSouthEastPedestrian, westToSouthEastCrutches1,
											westToSouthEastCrutches2, westToSouthEastCane, westToSouthEastDog, westToSouthEastMobilityScooter,
											westToSouthEastWheelChairAssisted, westToSouthEastWheelChairManual, westToSouthEastWheelChairPowered,
											westToSouthEastPushChair, westToSouthEastSkateboard, westToSouthEastManualScooter);
								}//FROM East TO ...
								else if(x == 4 && y == 0){
									//East to North-West
									appendCountables(fileWriter, eastToNorthWestCar, eastToNorthWestBus, eastToNorthWestTruck,
											eastToNorthWestMotorBike, eastToNorthWestPedestrian, eastToNorthWestCrutches1,
											eastToNorthWestCrutches2, eastToNorthWestCane, eastToNorthWestDog, eastToNorthWestMobilityScooter,
											eastToNorthWestWheelChairAssisted, eastToNorthWestWheelChairManual, eastToNorthWestWheelChairPowered,
											eastToNorthWestPushChair, eastToNorthWestSkateboard, eastToNorthWestManualScooter);
								}else if(x == 4 && y == 1){
									//East to North
									appendCountables(fileWriter, eastToNorthCar, eastToNorthBus, eastToNorthTruck,
											eastToNorthMotorBike, eastToNorthPedestrian, eastToNorthCrutches1,
											eastToNorthCrutches2, eastToNorthCane, eastToNorthDog, eastToNorthMobilityScooter,
											eastToNorthWheelChairAssisted, eastToNorthWheelChairManual, eastToNorthWheelChairPowered,
											eastToNorthPushChair, eastToNorthSkateboard, eastToNorthManualScooter);
								}else if(x == 4 && y == 2){
									//East to North-East
									appendCountables(fileWriter, eastToNorthEastCar, eastToNorthEastBus, eastToNorthEastTruck,
											eastToNorthEastMotorBike, eastToNorthEastPedestrian, eastToNorthEastCrutches1,
											eastToNorthEastCrutches2, eastToNorthEastCane, eastToNorthEastDog, eastToNorthEastMobilityScooter,
											eastToNorthEastWheelChairAssisted, eastToNorthEastWheelChairManual, eastToNorthEastWheelChairPowered,
											eastToNorthEastPushChair, eastToNorthEastSkateboard, eastToNorthEastManualScooter);
								}else if(x == 4 && y == 3){
									//East to West
									appendCountables(fileWriter, eastToWestCar, eastToWestBus, eastToWestTruck,
											eastToWestMotorBike, eastToWestPedestrian, eastToWestCrutches1,
											eastToWestCrutches2, eastToWestCane, eastToWestDog, eastToWestMobilityScooter,
											eastToWestWheelChairAssisted, eastToWestWheelChairManual, eastToWestWheelChairPowered,
											eastToWestPushChair, eastToWestSkateboard, eastToWestManualScooter);
								}else if(x == 4 && y == 5){
									//East to South-West
									appendCountables(fileWriter, eastToSouthWestCar, eastToSouthWestBus, eastToSouthWestTruck,
											eastToSouthWestMotorBike, eastToSouthWestPedestrian, eastToSouthWestCrutches1,
											eastToSouthWestCrutches2, eastToSouthWestCane, eastToSouthWestDog, eastToSouthWestMobilityScooter,
											eastToSouthWestWheelChairAssisted, eastToSouthWestWheelChairManual, eastToSouthWestWheelChairPowered,
											eastToSouthWestPushChair, eastToSouthWestSkateboard, eastToSouthWestManualScooter);
								}else if(x == 4 && y == 6){
									//East to South
									appendCountables(fileWriter, eastToSouthCar, eastToSouthBus, eastToSouthTruck,
											eastToSouthMotorBike, eastToSouthPedestrian, eastToSouthCrutches1,
											eastToSouthCrutches2, eastToSouthCane, eastToSouthDog, eastToSouthMobilityScooter,
											eastToSouthWheelChairAssisted, eastToSouthWheelChairManual, eastToSouthWheelChairPowered,
											eastToSouthPushChair, eastToSouthSkateboard, eastToSouthManualScooter);
								}else if(x == 4 && y == 7){
									//East to South-East
									appendCountables(fileWriter, eastToSouthEastCar, eastToSouthEastBus, eastToSouthEastTruck,
											eastToSouthEastMotorBike, eastToSouthEastPedestrian, eastToSouthEastCrutches1,
											eastToSouthEastCrutches2, eastToSouthEastCane, eastToSouthEastDog, eastToSouthEastMobilityScooter,
											eastToSouthEastWheelChairAssisted, eastToSouthEastWheelChairManual, eastToSouthEastWheelChairPowered,
											eastToSouthEastPushChair, eastToSouthEastSkateboard, eastToSouthEastManualScooter);
								}//FROM South-West TO ...
								else if(x == 5 && y == 0){
									//South-West to North-West
									appendCountables(fileWriter, southWestToNorthWestCar, southWestToNorthWestBus, southWestToNorthWestTruck,
											southWestToNorthWestMotorBike, southWestToNorthWestPedestrian, southWestToNorthWestCrutches1,
											southWestToNorthWestCrutches2, southWestToNorthWestCane, southWestToNorthWestDog, southWestToNorthWestMobilityScooter,
											southWestToNorthWestWheelChairAssisted, southWestToNorthWestWheelChairManual, southWestToNorthWestWheelChairPowered,
											southWestToNorthWestPushChair, southWestToNorthWestSkateboard, southWestToNorthWestManualScooter);
								}else if(x == 5 && y == 1){
									//South-West to North
									appendCountables(fileWriter, southWestToNorthCar, southWestToNorthBus, southWestToNorthTruck,
											southWestToNorthMotorBike, southWestToNorthPedestrian, southWestToNorthCrutches1,
											southWestToNorthCrutches2, southWestToNorthCane, southWestToNorthDog, southWestToNorthMobilityScooter,
											southWestToNorthWheelChairAssisted, southWestToNorthWheelChairManual, southWestToNorthWheelChairPowered,
											southWestToNorthPushChair, southWestToNorthSkateboard, southWestToNorthManualScooter);
								}else if(x == 5 && y == 2){
									//South-West to North-East
									appendCountables(fileWriter, southWestToNorthEastCar, southWestToNorthEastBus, southWestToNorthEastTruck,
											southWestToNorthEastMotorBike, southWestToNorthEastPedestrian, southWestToNorthEastCrutches1,
											southWestToNorthEastCrutches2, southWestToNorthEastCane, southWestToNorthEastDog, southWestToNorthEastMobilityScooter,
											southWestToNorthEastWheelChairAssisted, southWestToNorthEastWheelChairManual, southWestToNorthEastWheelChairPowered,
											southWestToNorthEastPushChair, southWestToNorthEastSkateboard, southWestToNorthEastManualScooter);
								}else if(x == 5 && y == 3){
									//South-West to West
									appendCountables(fileWriter, southWestToWestCar, southWestToWestBus, southWestToWestTruck,
											southWestToWestMotorBike, southWestToWestPedestrian, southWestToWestCrutches1,
											southWestToWestCrutches2, southWestToWestCane, southWestToWestDog, southWestToWestMobilityScooter,
											southWestToWestWheelChairAssisted, southWestToWestWheelChairManual, southWestToWestWheelChairPowered,
											southWestToWestPushChair, southWestToWestSkateboard, southWestToWestManualScooter);
								}else if(x == 5 && y == 4){
									//South-West to East
									appendCountables(fileWriter, southWestToEastCar, southWestToEastBus, southWestToEastTruck,
											southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
											southWestToEastCrutches2, southWestToEastCane, southWestToEastDog, southWestToEastMobilityScooter,
											southWestToEastWheelChairAssisted, southWestToEastWheelChairManual, southWestToEastWheelChairPowered,
											southWestToEastPushChair, southWestToEastSkateboard, southWestToEastManualScooter);
								}else if(x == 5 && y == 6){
									//South-West to South
									appendCountables(fileWriter, southWestToSouthCar, southWestToSouthBus, southWestToSouthTruck,
											southWestToSouthMotorBike, southWestToSouthPedestrian, southWestToSouthCrutches1,
											southWestToSouthCrutches2, southWestToSouthCane, southWestToSouthDog, southWestToSouthMobilityScooter,
											southWestToSouthWheelChairAssisted, southWestToSouthWheelChairManual, southWestToSouthWheelChairPowered,
											southWestToSouthPushChair, southWestToSouthSkateboard, southWestToSouthManualScooter);
								}else if(x == 5 && y == 7){
									//South-West to South-East
									appendCountables(fileWriter, southWestToSouthEastCar, southWestToSouthEastBus, southWestToSouthEastTruck,
											southWestToSouthEastMotorBike, southWestToSouthEastPedestrian, southWestToSouthEastCrutches1,
											southWestToSouthEastCrutches2, southWestToSouthEastCane, southWestToSouthEastDog, southWestToSouthEastMobilityScooter,
											southWestToSouthEastWheelChairAssisted, southWestToSouthEastWheelChairManual, southWestToSouthEastWheelChairPowered,
											southWestToSouthEastPushChair, southWestToSouthEastSkateboard, southWestToSouthEastManualScooter);
								}//FROM South TO ...
								else if(x == 6 && y == 0){
									//South to North-West
									appendCountables(fileWriter, southToNorthWestCar, southToNorthWestBus, southToNorthWestTruck,
											southToNorthWestMotorBike, southToNorthWestPedestrian, southToNorthWestCrutches1,
											southToNorthWestCrutches2, southToNorthWestCane, southToNorthWestDog, southToNorthWestMobilityScooter,
											southToNorthWestWheelChairAssisted, southToNorthWestWheelChairManual, southToNorthWestWheelChairPowered,
											southToNorthWestPushChair, southToNorthWestSkateboard, southToNorthWestManualScooter);
								}else if(x == 6 && y == 1){
									//South to North
									appendCountables(fileWriter, southToNorthCar, southToNorthBus, southToNorthTruck,
											southToNorthMotorBike, southToNorthPedestrian, southToNorthCrutches1,
											southToNorthCrutches2, southToNorthCane, southToNorthDog, southToNorthMobilityScooter,
											southToNorthWheelChairAssisted, southToNorthWheelChairManual, southToNorthWheelChairPowered,
											southToNorthPushChair, southToNorthSkateboard, southToNorthManualScooter);
								}else if(x == 6 && y == 2){
									//South to North-East
									appendCountables(fileWriter, southToNorthEastCar, southToNorthEastBus, southToNorthEastTruck,
											southToNorthEastMotorBike, southToNorthEastPedestrian, southToNorthEastCrutches1,
											southToNorthEastCrutches2, southToNorthEastCane, southToNorthEastDog, southToNorthEastMobilityScooter,
											southToNorthEastWheelChairAssisted, southToNorthEastWheelChairManual, southToNorthEastWheelChairPowered,
											southToNorthEastPushChair, southToNorthEastSkateboard, southToNorthEastManualScooter);
								}else if(x == 6 && y == 3){
									//South to West
									appendCountables(fileWriter, southToWestCar, southToWestBus, southToWestTruck,
											southToWestMotorBike, southToWestPedestrian, southToWestCrutches1,
											southToWestCrutches2, southToWestCane, southToWestDog, southToWestMobilityScooter,
											southToWestWheelChairAssisted, southToWestWheelChairManual, southToWestWheelChairPowered,
											southToWestPushChair, southToWestSkateboard, southToWestManualScooter);
								}else if(x == 6 && y == 4){
									//South to East
									appendCountables(fileWriter, southToEastCar, southToEastBus, southToEastTruck,
											southToEastMotorBike, southToEastPedestrian, southToEastCrutches1,
											southToEastCrutches2, southToEastCane, southToEastDog, southToEastMobilityScooter,
											southToEastWheelChairAssisted, southToEastWheelChairManual, southToEastWheelChairPowered,
											southToEastPushChair, southToEastSkateboard, southToEastManualScooter);
								}else if(x == 6 && y == 5){
									//South to South-West
									appendCountables(fileWriter, southToSouthWestCar, southToSouthWestBus, southToSouthWestTruck,
											southToSouthWestMotorBike, southToSouthWestPedestrian, southToSouthWestCrutches1,
											southToSouthWestCrutches2, southToSouthWestCane, southToSouthWestDog, southToSouthWestMobilityScooter,
											southToSouthWestWheelChairAssisted, southToSouthWestWheelChairManual, southToSouthWestWheelChairPowered,
											southToSouthWestPushChair, southToSouthWestSkateboard, southToSouthWestManualScooter);
								}else if(x == 6 && y == 7){
									//South to South-East
									appendCountables(fileWriter, southToSouthEastCar, southToSouthEastBus, southToSouthEastTruck,
											southToSouthEastMotorBike, southToSouthEastPedestrian, southToSouthEastCrutches1,
											southToSouthEastCrutches2, southToSouthEastCane, southToSouthEastDog, southToSouthEastMobilityScooter,
											southToSouthEastWheelChairAssisted, southToSouthEastWheelChairManual, southToSouthEastWheelChairPowered,
											southToSouthEastPushChair, southToSouthEastSkateboard, southToSouthEastManualScooter);
								}//FROM South-East TO ...
								else if(x == 7 && y == 0){
									//South-East to North-West
									appendCountables(fileWriter, southEastToNorthWestCar, southEastToNorthWestBus, southEastToNorthWestTruck,
											southEastToNorthWestMotorBike, southEastToNorthWestPedestrian, southEastToNorthWestCrutches1,
											southEastToNorthWestCrutches2, southEastToNorthWestCane, southEastToNorthWestDog, southEastToNorthWestMobilityScooter,
											southEastToNorthWestWheelChairAssisted, southEastToNorthWestWheelChairManual, southEastToNorthWestWheelChairPowered,
											southEastToNorthWestPushChair, southEastToNorthWestSkateboard, southEastToNorthWestManualScooter);
								}else if(x == 7 && y == 1){
									//South-East to North
									appendCountables(fileWriter, southEastToNorthCar, southEastToNorthBus, southEastToNorthTruck,
											southEastToNorthMotorBike, southEastToNorthPedestrian, southEastToNorthCrutches1,
											southEastToNorthCrutches2, southEastToNorthCane, southEastToNorthDog, southEastToNorthMobilityScooter,
											southEastToNorthWheelChairAssisted, southEastToNorthWheelChairManual, southEastToNorthWheelChairPowered,
											southEastToNorthPushChair, southEastToNorthSkateboard, southEastToNorthManualScooter);
								}else if(x == 7 && y == 2){
									//South-East to North-East
									appendCountables(fileWriter, southEastToNorthEastCar, southEastToNorthEastBus, southEastToNorthEastTruck,
											southEastToNorthEastMotorBike, southEastToNorthEastPedestrian, southEastToNorthEastCrutches1,
											southEastToNorthEastCrutches2, southEastToNorthEastCane, southEastToNorthEastDog, southEastToNorthEastMobilityScooter,
											southEastToNorthEastWheelChairAssisted, southEastToNorthEastWheelChairManual, southEastToNorthEastWheelChairPowered,
											southEastToNorthEastPushChair, southEastToNorthEastSkateboard, southEastToNorthEastManualScooter);
								}else if(x == 7 && y == 3){
									//South-East to West
									appendCountables(fileWriter, southEastToWestCar, southEastToWestBus, southEastToWestTruck,
											southEastToWestMotorBike, southEastToWestPedestrian, southEastToWestCrutches1,
											southEastToWestCrutches2, southEastToWestCane, southEastToWestDog, southEastToWestMobilityScooter,
											southEastToWestWheelChairAssisted, southEastToWestWheelChairManual, southEastToWestWheelChairPowered,
											southEastToWestPushChair, southEastToWestSkateboard, southEastToWestManualScooter);
								}else if(x == 7 && y == 4){
									//South-East to East
									appendCountables(fileWriter, southEastToEastCar, southEastToEastBus, southEastToEastTruck,
											southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
											southEastToEastCrutches2, southEastToEastCane, southEastToEastDog, southEastToEastMobilityScooter,
											southEastToEastWheelChairAssisted, southEastToEastWheelChairManual, southEastToEastWheelChairPowered,
											southEastToEastPushChair, southEastToEastSkateboard, southEastToEastManualScooter);
								}else if(x == 7 && y == 5){
									//South-East to South-West
									appendCountables(fileWriter, southEastToSouthWestCar, southEastToSouthWestBus, southEastToSouthWestTruck,
											southEastToSouthWestMotorBike, southEastToSouthWestPedestrian, southEastToSouthWestCrutches1,
											southEastToSouthWestCrutches2, southEastToSouthWestCane, southEastToSouthWestDog, southEastToSouthWestMobilityScooter,
											southEastToSouthWestWheelChairAssisted, southEastToSouthWestWheelChairManual, southEastToSouthWestWheelChairPowered,
											southEastToSouthWestPushChair, southEastToSouthWestSkateboard, southEastToSouthWestManualScooter);
								}else if(x == 7 && y == 6){
									//South-East to South
									appendCountables(fileWriter, southEastToSouthCar, southEastToSouthBus, southEastToSouthTruck,
											southEastToSouthMotorBike, southEastToSouthPedestrian, southEastToSouthCrutches1,
											southEastToSouthCrutches2, southEastToSouthCane, southEastToSouthDog, southEastToSouthMobilityScooter,
											southEastToSouthWheelChairAssisted, southEastToSouthWheelChairManual, southEastToSouthWheelChairPowered,
											southEastToSouthPushChair, southEastToSouthSkateboard, southEastToSouthManualScooter);
								}else{
									userMessage("Nothing was saved!");
									break;
								}
							}
						}
					}
				}
				fileWriter.append("\n");
		}
		
		//Append the re-occurring header labels, cars, buses, trucks, etc- Jean-Yves
		private void appendIntersectionCountsHeader(FileWriter fWriter) throws IOException {
			fWriter.append(", Cars, Buses, Trucks, Motorbikes, Pedestrians(No Aid), Walking Sticks Crutch(1), Walking Sticks Crutch(2)," +
					"		Cane(poor eyesight), Guide Dog, Mobility Scooter, Wheelchair(assisted), Wheelchair(manual), Wheelchair(powered)," +
					"		Push Chair/Buggy, Skateboard, Manual Scooter, ,\n");
		}

		private void appendCountables(FileWriter writer, int cars, int buses, int trucks, int motorcycles, int pedestrian,
										int wsCrutchOne, int wsCrutchTwo, int cane, int guideDog, int mobilityScooter, int wheelchairA,
										int wheelchairM, int wheelchairP, int pcBuggy, int skateboard, int manualScooter) throws IOException{
			writer.append(sessionStartTime + " TO " + currentTime + ",");
			writer.append(cars+","+buses+","+trucks+","+motorcycles+","+pedestrian+","+wsCrutchOne+","+wsCrutchTwo+","+cane+","+guideDog+","+mobilityScooter+","+ 
							 wheelchairA+","+wheelchairM+","+wheelchairP+","+pcBuggy+","+skateboard+","+manualScooter+","+",");
			flushAndCloseWriter(writer);
		}
		
		//To flush and subsequently close the opened file writer. -Jean-Yves
		public void flushAndCloseWriter(FileWriter fWriter) throws IOException{
			fWriter.flush();
			fWriter.close();
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
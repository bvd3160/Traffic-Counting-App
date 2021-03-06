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
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.TDG.trafficcountingapp.CustomDialogs.Communicator;
import com.TDG.trafficcountingapp.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter.LengthFilter;
import android.text.format.DateFormat;
import android.transition.ChangeBounds;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
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

	private CountDownTimer countTimer;
	private int timerMinutes;
	Button btn_setTime;
	
	private static int bus, truck, car, motorBike, 
			pedestrian, crutches_1, crutches_2,
			cane, dog, mobilityScooter,
			wheelChair_assisted, wheelChair_manual, wheelChair_powered,
			pushChair, skateboard, manualScooter;

	String defaultObject;
	public boolean[] directionFrom;
	public boolean[] directionTo;
	static int northWestTotal, northTotal, northEastTotal, westTotal, eastTotal, southWestTotal,
				southTotal, southEastTotal;
	//North West
	static int northWestToNorthBus, northWestToNorthTruck, northWestToNorthCar,
				northWestToNorthMotorBike, northWestToNorthPedestrian, northWestToNorthCrutches1,
				northWestToNorthCrutches2, northWestToNorthCane, northWestToNorthDog, northWestToNorthMobilityScooter, 
				northWestToNorthWheelchairAssisted, northWestToNorthWheelchairManual, northWestToNorthWheelchairPowered,
				northWestToNorthPushChair, northWestToNorthSkateboard, northWestToNorthManualScooter;
	static int northWestToNorthEastBus, northWestToNorthEastTruck, northWestToNorthEastCar,
				northWestToNorthEastMotorBike, northWestToNorthEastPedestrian, northWestToNorthEastCrutches1,
				northWestToNorthEastCrutches2, northWestToNorthEastCane, northWestToNorthEastDog, northWestToNorthEastMobilityScooter, 
				northWestToNorthEastWheelchairAssisted, northWestToNorthEastWheelchairManual, northWestToNorthEastWheelchairPowered,
				northWestToNorthEastPushChair, northWestToNorthEastSkateboard, northWestToNorthEastManualScooter;
	static int northWestToWestBus, northWestToWestTruck, northWestToWestCar,
				northWestToWestMotorBike, northWestToWestPedestrian, northWestToWestCrutches1,
				northWestToWestCrutches2, northWestToWestCane, northWestToWestDog, northWestToWestMobilityScooter, 
				northWestToWestWheelchairAssisted, northWestToWestWheelchairManual, northWestToWestWheelchairPowered,
				northWestToWestPushChair, northWestToWestSkateboard, northWestToWestManualScooter;
	static int northWestToEastBus, northWestToEastTruck, northWestToEastCar,
				northWestToEastMotorBike, northWestToEastPedestrian, northWestToEastCrutches1,
				northWestToEastCrutches2, northWestToEastCane, northWestToEastDog, northWestToEastMobilityScooter, 
				northWestToEastWheelchairAssisted, northWestToEastWheelchairManual, northWestToEastWheelchairPowered,
				northWestToEastPushChair, northWestToEastSkateboard, northWestToEastManualScooter;
	static int northWestToSouthWestBus, northWestToSouthWestTruck, northWestToSouthWestCar,
				northWestToSouthWestMotorBike, northWestToSouthWestPedestrian, northWestToSouthWestCrutches1,
				northWestToSouthWestCrutches2, northWestToSouthWestCane, northWestToSouthWestDog, northWestToSouthWestMobilityScooter, 
				northWestToSouthWestWheelchairAssisted, northWestToSouthWestWheelchairManual, northWestToSouthWestWheelchairPowered,
				northWestToSouthWestPushChair, northWestToSouthWestSkateboard, northWestToSouthWestManualScooter;
	static int northWestToSouthBus, northWestToSouthTruck, northWestToSouthCar,
				northWestToSouthMotorBike, northWestToSouthPedestrian, northWestToSouthCrutches1,
				northWestToSouthCrutches2, northWestToSouthCane, northWestToSouthDog, northWestToSouthMobilityScooter, 
				northWestToSouthWheelchairAssisted, northWestToSouthWheelchairManual, northWestToSouthWheelchairPowered,
				northWestToSouthPushChair, northWestToSouthSkateboard, northWestToSouthManualScooter;
	static int northWestToSouthEastBus, northWestToSouthEastTruck, northWestToSouthEastCar,
				northWestToSouthEastMotorBike, northWestToSouthEastPedestrian, northWestToSouthEastCrutches1,
				northWestToSouthEastCrutches2, northWestToSouthEastCane, northWestToSouthEastDog, northWestToSouthEastMobilityScooter, 
				northWestToSouthEastWheelchairAssisted, northWestToSouthEastWheelchairManual, northWestToSouthEastWheelchairPowered,
				northWestToSouthEastPushChair, northWestToSouthEastSkateboard, northWestToSouthEastManualScooter;
	
	//North
	static int northToNorthWestBus, northToNorthWestTruck, northToNorthWestCar,
				northToNorthWestMotorBike, northToNorthWestPedestrian, northToNorthWestCrutches1,
				northToNorthWestCrutches2, northToNorthWestCane, northToNorthWestDog, northToNorthWestMobilityScooter, 
				northToNorthWestWheelchairAssisted, northToNorthWestWheelchairManual, northToNorthWestWheelchairPowered,
				northToNorthWestPushChair, northToNorthWestSkateboard, northToNorthWestManualScooter;
	static int northToNorthEastBus, northToNorthEastTruck, northToNorthEastCar,
				northToNorthEastMotorBike, northToNorthEastPedestrian, northToNorthEastCrutches1,
				northToNorthEastCrutches2, northToNorthEastCane, northToNorthEastDog, northToNorthEastMobilityScooter, 
				northToNorthEastWheelchairAssisted, northToNorthEastWheelchairManual, northToNorthEastWheelchairPowered,
				northToNorthEastPushChair, northToNorthEastSkateboard, northToNorthEastManualScooter;
	static int northToWestBus, northToWestTruck, northToWestCar,
				northToWestMotorBike, northToWestPedestrian, northToWestCrutches1,
				northToWestCrutches2, northToWestCane, northToWestDog, northToWestMobilityScooter, 
				northToWestWheelchairAssisted, northToWestWheelchairManual, northToWestWheelchairPowered,
				northToWestPushChair, northToWestSkateboard, northToWestManualScooter;
	static int northToEastBus, northToEastTruck, northToEastCar,
				northToEastMotorBike, northToEastPedestrian, northToEastCrutches1,
				northToEastCrutches2, northToEastCane, northToEastDog, northToEastMobilityScooter, 
				northToEastWheelchairAssisted, northToEastWheelchairManual, northToEastWheelchairPowered,
				northToEastPushChair, northToEastSkateboard, northToEastManualScooter;
	static int northToSouthWestBus, northToSouthWestTruck, northToSouthWestCar,
				northToSouthWestMotorBike, northToSouthWestPedestrian, northToSouthWestCrutches1,
				northToSouthWestCrutches2, northToSouthWestCane, northToSouthWestDog, northToSouthWestMobilityScooter, 
				northToSouthWestWheelchairAssisted, northToSouthWestWheelchairManual, northToSouthWestWheelchairPowered,
				northToSouthWestPushChair, northToSouthWestSkateboard, northToSouthWestManualScooter;
	static int northToSouthBus, northToSouthTruck, northToSouthCar,
				northToSouthMotorBike, northToSouthPedestrian, northToSouthCrutches1,
				northToSouthCrutches2, northToSouthCane, northToSouthDog, northToSouthMobilityScooter, 
				northToSouthWheelchairAssisted, northToSouthWheelchairManual, northToSouthWheelchairPowered,
				northToSouthPushChair, northToSouthSkateboard, northToSouthManualScooter;
	static int northToSouthEastBus, northToSouthEastTruck, northToSouthEastCar,
				northToSouthEastMotorBike, northToSouthEastPedestrian, northToSouthEastCrutches1,
				northToSouthEastCrutches2, northToSouthEastCane, northToSouthEastDog, northToSouthEastMobilityScooter, 
				northToSouthEastWheelchairAssisted, northToSouthEastWheelchairManual, northToSouthEastWheelchairPowered,
				northToSouthEastPushChair, northToSouthEastSkateboard, northToSouthEastManualScooter;
	
	//North East
	static int northEastToNorthWestBus, northEastToNorthWestTruck, northEastToNorthWestCar,
				northEastToNorthWestMotorBike, northEastToNorthWestPedestrian, northEastToNorthWestCrutches1,
				northEastToNorthWestCrutches2, northEastToNorthWestCane, northEastToNorthWestDog, northEastToNorthWestMobilityScooter, 
				northEastToNorthWestWheelchairAssisted, northEastToNorthWestWheelchairManual, northEastToNorthWestWheelchairPowered,
				northEastToNorthWestPushChair, northEastToNorthWestSkateboard, northEastToNorthWestManualScooter;
	static int northEastToNorthBus, northEastToNorthTruck, northEastToNorthCar,
				northEastToNorthMotorBike, northEastToNorthPedestrian, northEastToNorthCrutches1,
				northEastToNorthCrutches2, northEastToNorthCane, northEastToNorthDog, northEastToNorthMobilityScooter, 
				northEastToNorthWheelchairAssisted, northEastToNorthWheelchairManual, northEastToNorthWheelchairPowered,
				northEastToNorthPushChair, northEastToNorthSkateboard, northEastToNorthManualScooter;
	static int northEastToWestBus, northEastToWestTruck, northEastToWestCar,
				northEastToWestMotorBike, northEastToWestPedestrian, northEastToWestCrutches1,
				northEastToWestCrutches2, northEastToWestCane, northEastToWestDog, northEastToWestMobilityScooter, 
				northEastToWestWheelchairAssisted, northEastToWestWheelchairManual, northEastToWestWheelchairPowered,
				northEastToWestPushChair, northEastToWestSkateboard, northEastToWestManualScooter;
	static int northEastToEastBus, northEastToEastTruck, northEastToEastCar,
				northEastToEastMotorBike, northEastToEastPedestrian, northEastToEastCrutches1,
				northEastToEastCrutches2, northEastToEastCane, northEastToEastDog, northEastToEastMobilityScooter, 
				northEastToEastWheelchairAssisted, northEastToEastWheelchairManual, northEastToEastWheelchairPowered,
				northEastToEastPushChair, northEastToEastSkateboard, northEastToEastManualScooter;
	static int northEastToSouthWestBus, northEastToSouthWestTruck, northEastToSouthWestCar,
				northEastToSouthWestMotorBike, northEastToSouthWestPedestrian, northEastToSouthWestCrutches1,
				northEastToSouthWestCrutches2, northEastToSouthWestCane, northEastToSouthWestDog, northEastToSouthWestMobilityScooter, 
				northEastToSouthWestWheelchairAssisted, northEastToSouthWestWheelchairManual, northEastToSouthWestWheelchairPowered,
				northEastToSouthWestPushChair, northEastToSouthWestSkateboard, northEastToSouthWestManualScooter;
	static int northEastToSouthBus, northEastToSouthTruck, northEastToSouthCar,
				northEastToSouthMotorBike, northEastToSouthPedestrian, northEastToSouthCrutches1,
				northEastToSouthCrutches2, northEastToSouthCane, northEastToSouthDog, northEastToSouthMobilityScooter, 
				northEastToSouthWheelchairAssisted, northEastToSouthWheelchairManual, northEastToSouthWheelchairPowered,
				northEastToSouthPushChair, northEastToSouthSkateboard, northEastToSouthManualScooter;
	static int northEastToSouthEastBus, northEastToSouthEastTruck, northEastToSouthEastCar,
				northEastToSouthEastMotorBike, northEastToSouthEastPedestrian, northEastToSouthEastCrutches1,
				northEastToSouthEastCrutches2, northEastToSouthEastCane, northEastToSouthEastDog, northEastToSouthEastMobilityScooter, 
				northEastToSouthEastWheelchairAssisted, northEastToSouthEastWheelchairManual, northEastToSouthEastWheelchairPowered,
				northEastToSouthEastPushChair, northEastToSouthEastSkateboard, northEastToSouthEastManualScooter;

	//West
	static int westToNorthWestBus, westToNorthWestTruck, westToNorthWestCar,
				westToNorthWestMotorBike, westToNorthWestPedestrian, westToNorthWestCrutches1,
				westToNorthWestCrutches2, westToNorthWestCane, westToNorthWestDog, westToNorthWestMobilityScooter, 
				westToNorthWestWheelchairAssisted, westToNorthWestWheelchairManual, westToNorthWestWheelchairPowered,
				westToNorthWestPushChair, westToNorthWestSkateboard, westToNorthWestManualScooter;
	static int westToNorthBus, westToNorthTruck, westToNorthCar,
				westToNorthMotorBike, westToNorthPedestrian, westToNorthCrutches1,
				westToNorthCrutches2, westToNorthCane, westToNorthDog, westToNorthMobilityScooter, 
				westToNorthWheelchairAssisted, westToNorthWheelchairManual, westToNorthWheelchairPowered,
				westToNorthPushChair, westToNorthSkateboard, westToNorthManualScooter;
	static int westToNorthEastBus, westToNorthEastTruck, westToNorthEastCar,
				westToNorthEastMotorBike, westToNorthEastPedestrian, westToNorthEastCrutches1,
				westToNorthEastCrutches2, westToNorthEastCane, westToNorthEastDog, westToNorthEastMobilityScooter, 
				westToNorthEastWheelchairAssisted, westToNorthEastWheelchairManual, westToNorthEastWheelchairPowered,
				westToNorthEastPushChair, westToNorthEastSkateboard, westToNorthEastManualScooter;
	static int westToEastBus, westToEastTruck, westToEastCar,
				westToEastMotorBike, westToEastPedestrian, westToEastCrutches1,
				westToEastCrutches2, westToEastCane, westToEastDog, westToEastMobilityScooter, 
				westToEastWheelchairAssisted, westToEastWheelchairManual, westToEastWheelchairPowered,
				westToEastPushChair, westToEastSkateboard, westToEastManualScooter;
	static int westToSouthWestBus, westToSouthWestTruck, westToSouthWestCar,
				westToSouthWestMotorBike, westToSouthWestPedestrian, westToSouthWestCrutches1,
				westToSouthWestCrutches2, westToSouthWestCane, westToSouthWestDog, westToSouthWestMobilityScooter, 
				westToSouthWestWheelchairAssisted, westToSouthWestWheelchairManual, westToSouthWestWheelchairPowered,
				westToSouthWestPushChair, westToSouthWestSkateboard, westToSouthWestManualScooter;
	static int westToSouthBus, westToSouthTruck, westToSouthCar,
				westToSouthMotorBike, westToSouthPedestrian, westToSouthCrutches1,
				westToSouthCrutches2, westToSouthCane, westToSouthDog, westToSouthMobilityScooter, 
				westToSouthWheelchairAssisted, westToSouthWheelchairManual, westToSouthWheelchairPowered,
				westToSouthPushChair, westToSouthSkateboard, westToSouthManualScooter;
	static int westToSouthEastBus, westToSouthEastTruck, westToSouthEastCar,
				westToSouthEastMotorBike, westToSouthEastPedestrian, westToSouthEastCrutches1,
				westToSouthEastCrutches2, westToSouthEastCane, westToSouthEastDog, westToSouthEastMobilityScooter, 
				westToSouthEastWheelchairAssisted, westToSouthEastWheelchairManual, westToSouthEastWheelchairPowered,
				westToSouthEastPushChair, westToSouthEastSkateboard, westToSouthEastManualScooter;

	//East
	static int eastToNorthWestBus, eastToNorthWestTruck, eastToNorthWestCar,
				eastToNorthWestMotorBike, eastToNorthWestPedestrian, eastToNorthWestCrutches1,
				eastToNorthWestCrutches2, eastToNorthWestCane, eastToNorthWestDog, eastToNorthWestMobilityScooter, 
				eastToNorthWestWheelchairAssisted, eastToNorthWestWheelchairManual, eastToNorthWestWheelchairPowered,
				eastToNorthWestPushChair, eastToNorthWestSkateboard, eastToNorthWestManualScooter;
	static int eastToNorthBus, eastToNorthTruck, eastToNorthCar,
				eastToNorthMotorBike, eastToNorthPedestrian, eastToNorthCrutches1,
				eastToNorthCrutches2, eastToNorthCane, eastToNorthDog, eastToNorthMobilityScooter, 
				eastToNorthWheelchairAssisted, eastToNorthWheelchairManual, eastToNorthWheelchairPowered,
				eastToNorthPushChair, eastToNorthSkateboard, eastToNorthManualScooter;
	static int eastToNorthEastBus, eastToNorthEastTruck, eastToNorthEastCar,
				eastToNorthEastMotorBike, eastToNorthEastPedestrian, eastToNorthEastCrutches1,
				eastToNorthEastCrutches2, eastToNorthEastCane, eastToNorthEastDog, eastToNorthEastMobilityScooter, 
				eastToNorthEastWheelchairAssisted, eastToNorthEastWheelchairManual, eastToNorthEastWheelchairPowered,
				eastToNorthEastPushChair, eastToNorthEastSkateboard, eastToNorthEastManualScooter;
	static int eastToWestBus, eastToWestTruck, eastToWestCar,
				eastToWestMotorBike, eastToWestPedestrian, eastToWestCrutches1,
				eastToWestCrutches2, eastToWestCane, eastToWestDog, eastToWestMobilityScooter, 
				eastToWestWheelchairAssisted, eastToWestWheelchairManual, eastToWestWheelchairPowered,
				eastToWestPushChair, eastToWestSkateboard, eastToWestManualScooter;
	static int eastToSouthWestBus, eastToSouthWestTruck, eastToSouthWestCar,
				eastToSouthWestMotorBike, eastToSouthWestPedestrian, eastToSouthWestCrutches1,
				eastToSouthWestCrutches2, eastToSouthWestCane, eastToSouthWestDog, eastToSouthWestMobilityScooter, 
				eastToSouthWestWheelchairAssisted, eastToSouthWestWheelchairManual, eastToSouthWestWheelchairPowered,
				eastToSouthWestPushChair, eastToSouthWestSkateboard, eastToSouthWestManualScooter;
	static int eastToSouthBus, eastToSouthTruck, eastToSouthCar,
				eastToSouthMotorBike, eastToSouthPedestrian, eastToSouthCrutches1,
				eastToSouthCrutches2, eastToSouthCane, eastToSouthDog, eastToSouthMobilityScooter, 
				eastToSouthWheelchairAssisted, eastToSouthWheelchairManual, eastToSouthWheelchairPowered,
				eastToSouthPushChair, eastToSouthSkateboard, eastToSouthManualScooter;
	static int eastToSouthEastBus, eastToSouthEastTruck, eastToSouthEastCar,
				eastToSouthEastMotorBike, eastToSouthEastPedestrian, eastToSouthEastCrutches1,
				eastToSouthEastCrutches2, eastToSouthEastCane, eastToSouthEastDog, eastToSouthEastMobilityScooter, 
				eastToSouthEastWheelchairAssisted, eastToSouthEastWheelchairManual, eastToSouthEastWheelchairPowered,
				eastToSouthEastPushChair, eastToSouthEastSkateboard, eastToSouthEastManualScooter;
	
	//South West
	static int southWestToNorthWestBus, southWestToNorthWestTruck, southWestToNorthWestCar,
				southWestToNorthWestMotorBike, southWestToNorthWestPedestrian, southWestToNorthWestCrutches1,
				southWestToNorthWestCrutches2, southWestToNorthWestCane, southWestToNorthWestDog, southWestToNorthWestMobilityScooter, 
				southWestToNorthWestWheelchairAssisted, southWestToNorthWestWheelchairManual, southWestToNorthWestWheelchairPowered,
				southWestToNorthWestPushChair, southWestToNorthWestSkateboard, southWestToNorthWestManualScooter;
	static int southWestToNorthBus, southWestToNorthTruck, southWestToNorthCar,
				southWestToNorthMotorBike, southWestToNorthPedestrian, southWestToNorthCrutches1,
				southWestToNorthCrutches2, southWestToNorthCane, southWestToNorthDog, southWestToNorthMobilityScooter, 
				southWestToNorthWheelchairAssisted, southWestToNorthWheelchairManual, southWestToNorthWheelchairPowered,
				southWestToNorthPushChair, southWestToNorthSkateboard, southWestToNorthManualScooter;
	static int southWestToNorthEastBus, southWestToNorthEastTruck, southWestToNorthEastCar,
				southWestToNorthEastMotorBike, southWestToNorthEastPedestrian, southWestToNorthEastCrutches1,
				southWestToNorthEastCrutches2, southWestToNorthEastCane, southWestToNorthEastDog, southWestToNorthEastMobilityScooter, 
				southWestToNorthEastWheelchairAssisted, southWestToNorthEastWheelchairManual, southWestToNorthEastWheelchairPowered,
				southWestToNorthEastPushChair, southWestToNorthEastSkateboard, southWestToNorthEastManualScooter;
	static int southWestToWestBus, southWestToWestTruck, southWestToWestCar,
				southWestToWestMotorBike, southWestToWestPedestrian, southWestToWestCrutches1,
				southWestToWestCrutches2, southWestToWestCane, southWestToWestDog, southWestToWestMobilityScooter, 
				southWestToWestWheelchairAssisted, southWestToWestWheelchairManual, southWestToWestWheelchairPowered,
				southWestToWestPushChair, southWestToWestSkateboard, southWestToWestManualScooter;
	static int southWestToEastBus, southWestToEastTruck, southWestToEastCar,
				southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
				southWestToEastCrutches2, southWestToEastCane, southWestToEastDog, southWestToEastMobilityScooter, 
				southWestToEastWheelchairAssisted, southWestToEastWheelchairManual, southWestToEastWheelchairPowered,
				southWestToEastPushChair, southWestToEastSkateboard, southWestToEastManualScooter;
	static int southWestToSouthBus, southWestToSouthTruck, southWestToSouthCar,
				southWestToSouthMotorBike, southWestToSouthPedestrian, southWestToSouthCrutches1,
				southWestToSouthCrutches2, southWestToSouthCane, southWestToSouthDog, southWestToSouthMobilityScooter, 
				southWestToSouthWheelchairAssisted, southWestToSouthWheelchairManual, southWestToSouthWheelchairPowered,
				southWestToSouthPushChair, southWestToSouthSkateboard, southWestToSouthManualScooter;
	static int southWestToSouthEastBus, southWestToSouthEastTruck, southWestToSouthEastCar,
				southWestToSouthEastMotorBike, southWestToSouthEastPedestrian, southWestToSouthEastCrutches1,
				southWestToSouthEastCrutches2, southWestToSouthEastCane, southWestToSouthEastDog, southWestToSouthEastMobilityScooter, 
				southWestToSouthEastWheelchairAssisted, southWestToSouthEastWheelchairManual, southWestToSouthEastWheelchairPowered,
				southWestToSouthEastPushChair, southWestToSouthEastSkateboard, southWestToSouthEastManualScooter;

	// South
	static int southToNorthWestBus, southToNorthWestTruck, southToNorthWestCar,
				southToNorthWestMotorBike, southToNorthWestPedestrian, southToNorthWestCrutches1,
				southToNorthWestCrutches2, southToNorthWestCane, southToNorthWestDog, southToNorthWestMobilityScooter, 
				southToNorthWestWheelchairAssisted, southToNorthWestWheelchairManual, southToNorthWestWheelchairPowered,
				southToNorthWestPushChair, southToNorthWestSkateboard, southToNorthWestManualScooter;
	static int southToNorthBus, southToNorthTruck, southToNorthCar,
				southToNorthMotorBike, southToNorthPedestrian, southToNorthCrutches1,
				southToNorthCrutches2, southToNorthCane, southToNorthDog, southToNorthMobilityScooter, 
				southToNorthWheelchairAssisted, southToNorthWheelchairManual, southToNorthWheelchairPowered,
				southToNorthPushChair, southToNorthSkateboard, southToNorthManualScooter;
	static int southToNorthEastBus, southToNorthEastTruck, southToNorthEastCar,
				southToNorthEastMotorBike, southToNorthEastPedestrian, southToNorthEastCrutches1,
				southToNorthEastCrutches2, southToNorthEastCane, southToNorthEastDog, southToNorthEastMobilityScooter, 
				southToNorthEastWheelchairAssisted, southToNorthEastWheelchairManual, southToNorthEastWheelchairPowered,
				southToNorthEastPushChair, southToNorthEastSkateboard, southToNorthEastManualScooter;
	static int southToWestBus, southToWestTruck, southToWestCar,
				southToWestMotorBike, southToWestPedestrian, southToWestCrutches1,
				southToWestCrutches2, southToWestCane, southToWestDog, southToWestMobilityScooter, 
				southToWestWheelchairAssisted, southToWestWheelchairManual, southToWestWheelchairPowered,
				southToWestPushChair, southToWestSkateboard, southToWestManualScooter;
	static int southToEastBus, southToEastTruck, southToEastCar,
				southToEastMotorBike, southToEastPedestrian, southToEastCrutches1,
				southToEastCrutches2, southToEastCane, southToEastDog, southToEastMobilityScooter, 
				southToEastWheelchairAssisted, southToEastWheelchairManual, southToEastWheelchairPowered,
				southToEastPushChair, southToEastSkateboard, southToEastManualScooter;
	static int southToSouthWestBus, southToSouthWestTruck, southToSouthWestCar,
				southToSouthWestMotorBike, southToSouthWestPedestrian, southToSouthWestCrutches1,
				southToSouthWestCrutches2, southToSouthWestCane, southToSouthWestDog, southToSouthWestMobilityScooter, 
				southToSouthWestWheelchairAssisted, southToSouthWestWheelchairManual, southToSouthWestWheelchairPowered,
				southToSouthWestPushChair, southToSouthWestSkateboard, southToSouthWestManualScooter;
	static int southToSouthEastBus, southToSouthEastTruck, southToSouthEastCar,
				southToSouthEastMotorBike, southToSouthEastPedestrian, southToSouthEastCrutches1,
				southToSouthEastCrutches2, southToSouthEastCane, southToSouthEastDog, southToSouthEastMobilityScooter, 
				southToSouthEastWheelchairAssisted, southToSouthEastWheelchairManual, southToSouthEastWheelchairPowered,
				southToSouthEastPushChair, southToSouthEastSkateboard, southToSouthEastManualScooter;
	
	// South East
	static int southEastToNorthWestBus, southEastToNorthWestTruck, southEastToNorthWestCar,
				southEastToNorthWestMotorBike, southEastToNorthWestPedestrian, southEastToNorthWestCrutches1,
				southEastToNorthWestCrutches2, southEastToNorthWestCane, southEastToNorthWestDog, southEastToNorthWestMobilityScooter, 
				southEastToNorthWestWheelchairAssisted, southEastToNorthWestWheelchairManual, southEastToNorthWestWheelchairPowered,
				southEastToNorthWestPushChair, southEastToNorthWestSkateboard, southEastToNorthWestManualScooter;
	static int southEastToNorthBus, southEastToNorthTruck, southEastToNorthCar,
				southEastToNorthMotorBike, southEastToNorthPedestrian, southEastToNorthCrutches1,
				southEastToNorthCrutches2, southEastToNorthCane, southEastToNorthDog, southEastToNorthMobilityScooter, 
				southEastToNorthWheelchairAssisted, southEastToNorthWheelchairManual, southEastToNorthWheelchairPowered,
				southEastToNorthPushChair, southEastToNorthSkateboard, southEastToNorthManualScooter;
	static int southEastToNorthEastBus, southEastToNorthEastTruck, southEastToNorthEastCar,
				southEastToNorthEastMotorBike, southEastToNorthEastPedestrian, southEastToNorthEastCrutches1,
				southEastToNorthEastCrutches2, southEastToNorthEastCane, southEastToNorthEastDog, southEastToNorthEastMobilityScooter, 
				southEastToNorthEastWheelchairAssisted, southEastToNorthEastWheelchairManual, southEastToNorthEastWheelchairPowered,
				southEastToNorthEastPushChair, southEastToNorthEastSkateboard, southEastToNorthEastManualScooter;
	static int southEastToWestBus, southEastToWestTruck, southEastToWestCar,
				southEastToWestMotorBike, southEastToWestPedestrian, southEastToWestCrutches1,
				southEastToWestCrutches2, southEastToWestCane, southEastToWestDog, southEastToWestMobilityScooter, 
				southEastToWestWheelchairAssisted, southEastToWestWheelchairManual, southEastToWestWheelchairPowered,
				southEastToWestPushChair, southEastToWestSkateboard, southEastToWestManualScooter;
	static int southEastToEastBus, southEastToEastTruck, southEastToEastCar,
				southEastToEastMotorBike, southEastToEastPedestrian, southEastToEastCrutches1,
				southEastToEastCrutches2, southEastToEastCane, southEastToEastDog, southEastToEastMobilityScooter, 
				southEastToEastWheelchairAssisted, southEastToEastWheelchairManual, southEastToEastWheelchairPowered,
				southEastToEastPushChair, southEastToEastSkateboard, southEastToEastManualScooter;
	static int southEastToSouthWestBus, southEastToSouthWestTruck, southEastToSouthWestCar,
				southEastToSouthWestMotorBike, southEastToSouthWestPedestrian, southEastToSouthWestCrutches1,
				southEastToSouthWestCrutches2, southEastToSouthWestCane, southEastToSouthWestDog, southEastToSouthWestMobilityScooter, 
				southEastToSouthWestWheelchairAssisted, southEastToSouthWestWheelchairManual, southEastToSouthWestWheelchairPowered,
				southEastToSouthWestPushChair, southEastToSouthWestSkateboard, southEastToSouthWestManualScooter;
	static int southEastToSouthBus, southEastToSouthTruck, southEastToSouthCar,
				southEastToSouthMotorBike, southEastToSouthPedestrian, southEastToSouthCrutches1,
				southEastToSouthCrutches2, southEastToSouthCane, southEastToSouthDog, southEastToSouthMobilityScooter, 
				southEastToSouthWheelchairAssisted, southEastToSouthWheelchairManual, southEastToSouthWheelchairPowered,
				southEastToSouthPushChair, southEastToSouthSkateboard, southEastToSouthManualScooter;
	
	private Deque<String> lastSelectedObject;
	
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
	
	//Saving Related
	static boolean locationHeaderAppended;
	static boolean endOfWritingCoutablesRow;
 	static boolean endOfIntersectionCountsHeader;
	static boolean endOfAppendingCountables;
	static boolean endOfFromAndToPositionsHeader;
	
	Button btn_direction_nw, btn_direction_n, btn_direction_ne, btn_direction_w,
	 btn_direction_e, btn_direction_sw, btn_direction_s, btn_direction_se;
	
	boolean btn_direction_nw_clicked, btn_direction_n_clicked, btn_direction_ne_clicked,
			btn_direction_w_clicked, btn_direction_e_clicked, btn_direction_sw_clicked,
			btn_direction_s_clicked, btn_direction_se_clicked;
	
	Button btn_undo;
	
	//Testing purposes
	Button btn_start, btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		comments = getIntent().getStringExtra("Comments");
		defaultObject = "Pedestrian (No Aid)";
		
		commentViewable = (TextView) findViewById(R.id.tv_commentText);
		commentViewable.setText(comments);
		
		intersectionsPicked = getIntent().getBooleanArrayExtra("IntersectionsPicked");
		intersectionType = getIntent().getStringExtra("IntersectionType");
		
		if(intersectionType.isEmpty() || intersectionType == null || intersectionType == ""){
			intersectionType = "No Intersection";
			for (int x = 0; x < intersectionsPicked.length; x++) {
				intersectionsPicked[x] = true;
			}
		}
		
		//Sets the Vehicle button to be invisible if no intersection was chosen (Pretty much Pedestrian Count)
		if(intersectionType.equals("No Intersection")){
			findViewById(R.id.cs_btn_vehicles).setVisibility(4);
		}
		
		// Initialises the current counting object to be a car
		updateCurrentObjectTo("Pedestrian (No Aid)");
				
		timerMinutes = 15;
		btn_setTime = (Button)findViewById(R.id.cs_btn_changeTimer);
		
		initialiseCountObjects();
		initialiseDirectionButtonClicked();
		initialiseDirectionFromTo();
		populateTimer(timerMinutes);
		populateButtons();
		showCountingPanelAndButtons();
		
		locationHeaderAppended = false;
		endOfWritingCoutablesRow = false;
		endOfIntersectionCountsHeader = false;
		endOfAppendingCountables = false;
		endOfFromAndToPositionsHeader = false;
	}
	
	/*
	 * This method initialises all all counting objects such as 
	 * Cars, Bus, Trucks, Motorbikes, Pedestrians etc.
	 * Initialises the totalcount and stack for the undo button.
	 * Then it updates the current selected object to 'Pedestrian'.
	 * @author: Richard Fong
	 * @since:
	 */
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
		
		lastSelectedObject = new ArrayDeque<String>();
		
		updateCurrentlySelectedObject(pedestrian);
	}
	
	/*
	 * This method initialises the DirectionFrom and the DirectionTo.
	 * These arrays will be used to know where the count object came from and went.
	 * @author: Richard Fong
	 * @since:
	 */
	private void initialiseDirectionFromTo(){
		directionFrom = new boolean[8];
		directionTo = new boolean[8];
		
		for (int x = 0; x < directionFrom.length; x++) {
			directionFrom[x] = false;
			directionTo[x]	= false;
		}
	}
	
	/*
	 * This method initialises the array for the intersection names that the user
	 * may want to change.
	 * @author: Richard Fong
	 * @since: 
	 */
	private void initialiseIntersectionName(){
		intersectionPickedNames = new String[8];
	}
	
	/*
	 * This method initialises all the directional buttons to false which will
	 * be used later in choosing which directional button to enable using data
	 * from CountSetup.
	 * @author: Richard Fong
	 * @since:
	 */
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
	
	////////////////////////////////////////////////////
	//Methods which initilise the count objects
	////////////////////////////////////////////////////
	
	/*
	 * The methods below initialises all the count objects with their directionFrom
	 * and directionTo values to 0 as well as their total count.
	 * @author: Richard Fong
	 * @since: 
	 */
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

		northWestToNorthWheelchairAssisted = 0;
		northWestToNorthEastWheelchairAssisted = 0;
		northWestToWestWheelchairAssisted = 0;
		northWestToEastWheelchairAssisted = 0;
		northWestToSouthWestWheelchairAssisted = 0;
		northWestToSouthWheelchairAssisted = 0;
		northWestToSouthEastWheelchairAssisted = 0;
		
		northToNorthWestWheelchairAssisted = 0;
		northToNorthEastWheelchairAssisted = 0;
		northToWestWheelchairAssisted = 0;
		northToEastWheelchairAssisted = 0;
		northToSouthWestWheelchairAssisted = 0;
		northToSouthWheelchairAssisted = 0;
		northToSouthEastWheelchairAssisted = 0;
		
		northEastToNorthWestWheelchairAssisted = 0;
		northEastToNorthWheelchairAssisted = 0;
		northEastToWestWheelchairAssisted = 0;
		northEastToEastWheelchairAssisted = 0;
		northEastToSouthWestWheelchairAssisted = 0;
		northEastToSouthWheelchairAssisted = 0;
		northEastToSouthEastWheelchairAssisted = 0;
		
		westToNorthWestWheelchairAssisted = 0;
		westToNorthWheelchairAssisted = 0;
		westToNorthEastWheelchairAssisted = 0;
		westToEastWheelchairAssisted = 0;
		westToSouthEastWheelchairAssisted = 0;
		westToSouthWheelchairAssisted = 0;
		westToSouthEastWheelchairAssisted = 0;
		
		eastToNorthWestWheelchairAssisted = 0;
		eastToNorthWheelchairAssisted = 0;
		eastToNorthEastWheelchairAssisted = 0;
		eastToWestWheelchairAssisted = 0;
		eastToSouthWestWheelchairAssisted = 0;
		eastToSouthWheelchairAssisted = 0;
		eastToSouthEastWheelchairAssisted = 0;
		
		southWestToNorthWestWheelchairAssisted = 0;
		southWestToNorthWheelchairAssisted = 0;
		southWestToNorthEastWheelchairAssisted = 0;
		southWestToWestWheelchairAssisted = 0;
		southWestToEastWheelchairAssisted = 0;
		southWestToSouthWheelchairAssisted = 0;
		southWestToSouthEastWheelchairAssisted = 0;
		
		southToNorthWestWheelchairAssisted = 0;
		southToNorthWheelchairAssisted = 0;
		southToNorthEastWheelchairAssisted = 0;
		southToWestWheelchairAssisted = 0;
		southToEastWheelchairAssisted = 0;
		southToSouthWestWheelchairAssisted = 0;
		southToSouthEastWheelchairAssisted = 0;
		
		southEastToNorthWestWheelchairAssisted = 0;
		southEastToNorthWheelchairAssisted = 0;
		southEastToNorthEastWheelchairAssisted = 0;
		southEastToWestWheelchairAssisted = 0;
		southEastToEastWheelchairAssisted = 0;
		southEastToSouthWestWheelchairAssisted = 0;
		southEastToSouthWheelchairAssisted = 0;
		
		wheelChair_manual = 0;

		northWestToNorthWheelchairManual = 0;
		northWestToNorthEastWheelchairManual = 0;
		northWestToWestWheelchairManual = 0;
		northWestToEastWheelchairManual = 0;
		northWestToSouthWestWheelchairManual = 0;
		northWestToSouthWheelchairManual = 0;
		northWestToSouthEastWheelchairManual = 0;
		
		northToNorthWestWheelchairManual = 0;
		northToNorthEastWheelchairManual = 0;
		northToWestWheelchairManual = 0;
		northToEastWheelchairManual = 0;
		northToSouthWestWheelchairManual = 0;
		northToSouthWheelchairManual = 0;
		northToSouthEastWheelchairManual = 0;
		
		northEastToNorthWestWheelchairManual = 0;
		northEastToNorthWheelchairManual = 0;
		northEastToWestWheelchairManual = 0;
		northEastToEastWheelchairManual = 0;
		northEastToSouthWestWheelchairManual = 0;
		northEastToSouthWheelchairManual = 0;
		northEastToSouthEastWheelchairManual = 0;
		
		westToNorthWestWheelchairManual = 0;
		westToNorthWheelchairManual = 0;
		westToNorthEastWheelchairManual = 0;
		westToEastWheelchairManual = 0;
		westToSouthEastWheelchairManual = 0;
		westToSouthWheelchairManual = 0;
		westToSouthEastWheelchairManual = 0;
		
		eastToNorthWestWheelchairManual = 0;
		eastToNorthWheelchairManual = 0;
		eastToNorthEastWheelchairManual = 0;
		eastToWestWheelchairManual = 0;
		eastToSouthWestWheelchairManual = 0;
		eastToSouthWheelchairManual = 0;
		eastToSouthEastWheelchairManual = 0;
		
		southWestToNorthWestWheelchairManual = 0;
		southWestToNorthWheelchairManual = 0;
		southWestToNorthEastWheelchairManual = 0;
		southWestToWestWheelchairManual = 0;
		southWestToEastWheelchairManual = 0;
		southWestToSouthWheelchairManual = 0;
		southWestToSouthEastWheelchairManual = 0;
		
		southToNorthWestWheelchairManual = 0;
		southToNorthWheelchairManual = 0;
		southToNorthEastWheelchairManual = 0;
		southToWestWheelchairManual = 0;
		southToEastWheelchairManual = 0;
		southToSouthWestWheelchairManual = 0;
		southToSouthEastWheelchairManual = 0;
		
		southEastToNorthWestWheelchairManual = 0;
		southEastToNorthWheelchairManual = 0;
		southEastToNorthEastWheelchairManual = 0;
		southEastToWestWheelchairManual = 0;
		southEastToEastWheelchairManual = 0;
		southEastToSouthWestWheelchairManual = 0;
		southEastToSouthWheelchairManual = 0;
		
		wheelChair_powered = 0;

		northWestToNorthWheelchairPowered = 0;
		northWestToNorthEastWheelchairPowered = 0;
		northWestToWestWheelchairPowered = 0;
		northWestToEastWheelchairPowered = 0;
		northWestToSouthWestWheelchairPowered = 0;
		northWestToSouthWheelchairPowered = 0;
		northWestToSouthEastWheelchairPowered = 0;
		
		northToNorthWestWheelchairPowered = 0;
		northToNorthEastWheelchairPowered = 0;
		northToWestWheelchairPowered = 0;
		northToEastWheelchairPowered = 0;
		northToSouthWestWheelchairPowered = 0;
		northToSouthWheelchairPowered = 0;
		northToSouthEastWheelchairPowered = 0;
		
		northEastToNorthWestWheelchairPowered = 0;
		northEastToNorthWheelchairPowered = 0;
		northEastToWestWheelchairPowered = 0;
		northEastToEastWheelchairPowered = 0;
		northEastToSouthWestWheelchairPowered = 0;
		northEastToSouthWheelchairPowered = 0;
		northEastToSouthEastWheelchairPowered = 0;
		
		westToNorthWestWheelchairPowered = 0;
		westToNorthWheelchairPowered = 0;
		westToNorthEastWheelchairPowered = 0;
		westToEastWheelchairPowered = 0;
		westToSouthEastWheelchairPowered = 0;
		westToSouthWheelchairPowered = 0;
		westToSouthEastWheelchairPowered = 0;
		
		eastToNorthWestWheelchairPowered = 0;
		eastToNorthWheelchairPowered = 0;
		eastToNorthEastWheelchairPowered = 0;
		eastToWestWheelchairPowered = 0;
		eastToSouthWestWheelchairPowered = 0;
		eastToSouthWheelchairPowered = 0;
		eastToSouthEastWheelchairPowered = 0;
		
		southWestToNorthWestWheelchairPowered = 0;
		southWestToNorthWheelchairPowered = 0;
		southWestToNorthEastWheelchairPowered = 0;
		southWestToWestWheelchairPowered = 0;
		southWestToEastWheelchairPowered = 0;
		southWestToSouthWheelchairPowered = 0;
		southWestToSouthEastWheelchairPowered = 0;
		
		southToNorthWestWheelchairPowered = 0;
		southToNorthWheelchairPowered = 0;
		southToNorthEastWheelchairPowered = 0;
		southToWestWheelchairPowered = 0;
		southToEastWheelchairPowered = 0;
		southToSouthWestWheelchairPowered = 0;
		southToSouthEastWheelchairPowered = 0;
		
		southEastToNorthWestWheelchairPowered = 0;
		southEastToNorthWheelchairPowered = 0;
		southEastToNorthEastWheelchairPowered = 0;
		southEastToWestWheelchairPowered = 0;
		southEastToEastWheelchairPowered = 0;
		southEastToSouthWestWheelchairPowered = 0;
		southEastToSouthWheelchairPowered = 0;
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
	// End of initialising Count objects
	////////////////////////////////////////////////////////////////////////
	
	
	/*
	 * This method initialises the count for all directions.
	 * @author: Richard Fong
	 * @since:
	 */
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
	
	/*
	 * This method is used to set the currently selected object to the selected defaultObject
	 * as well as updating the TextView to show the defaultObject.
	 * @author: Richard Fong
	 * @since: 
	 */
	private void defaultTo(String object){
		switch (object) {
		case "Bus":
			updateCurrentObjectTo("Bus");
			setCurrentlySelectedObject("Bus");
			updateCurrentlySelectedObject(bus);
			break;
		
		case "Car":
			updateCurrentObjectTo("Car");
			setCurrentlySelectedObject("Car");
			updateCurrentlySelectedObject(car);
			break;
		
		case "Truck":
			updateCurrentObjectTo("Truck");
			setCurrentlySelectedObject("Truck");
			updateCurrentlySelectedObject(truck);
			break;
		
		case "Motor Bike":
			updateCurrentObjectTo("Motor Bike");
			setCurrentlySelectedObject("Motor Bike");
			updateCurrentlySelectedObject(motorBike);
			break;
		
		case "Pedestrian (No Aid)":
			updateCurrentObjectTo("Pedestrian (No Aid)");
			setCurrentlySelectedObject("Pedestrian (No Aid)");
			updateCurrentlySelectedObject(pedestrian);
			break;

		case "Walking Stick / Crutch (1)":
			updateCurrentObjectTo("Walking Stick / Crutch (1)");
			setCurrentlySelectedObject("Walking Stick / Crutch (1)");
			updateCurrentlySelectedObject(crutches_1);
			break;

		case "Walking Stick / Crutches (2)":
			updateCurrentObjectTo("Walking Stick / Crutches (2)");
			setCurrentlySelectedObject("Walking Stick / Crutches (2)");
			updateCurrentlySelectedObject(crutches_2);
			break;

		case "Cane (Poor Eyesight)":
			updateCurrentObjectTo("Cane (Poor Eyesight)");
			setCurrentlySelectedObject("Cane (Poor Eyesight)");
			updateCurrentlySelectedObject(cane);
			break;
			
		case "Guide Dog":
			updateCurrentObjectTo("Guide Dog");
			setCurrentlySelectedObject("Guide Dog");
			updateCurrentlySelectedObject(dog);
			break;
			
		case "Mobility Scooter":
			updateCurrentObjectTo("Mobility Scooter");
			setCurrentlySelectedObject("Mobility Scooter");
			updateCurrentlySelectedObject(mobilityScooter);
			break;

		case "Wheelchair (Assisted)":
			updateCurrentObjectTo("Wheelchair (Assisted)");
			setCurrentlySelectedObject("Wheelchair (Assisted)");
			updateCurrentlySelectedObject(wheelChair_assisted);
			break;

		case "Wheelchair (Manual)":
			updateCurrentObjectTo("Wheelchair (Manual)");
			setCurrentlySelectedObject("Wheelchair (Manual)");
			updateCurrentlySelectedObject(wheelChair_manual);
			break;

		case "Wheelchair (Powered)":
			updateCurrentObjectTo("Wheelchair (Powered)");
			setCurrentlySelectedObject("Wheelchair (Powered)");
			updateCurrentlySelectedObject(wheelChair_powered);
			break;

		case "Push Chair / Buggy":
			updateCurrentObjectTo("Push Chair / Buggy");
			setCurrentlySelectedObject("Push Chair / Buggy");
			updateCurrentlySelectedObject(pushChair);
			break;

		case "Skateboard":
			updateCurrentObjectTo("Skateboard");
			setCurrentlySelectedObject("Skateboard");
			updateCurrentlySelectedObject(skateboard);
			break;
			

		case "Manual Scooter":
			updateCurrentObjectTo("Manual Scooter");
			setCurrentlySelectedObject("Manual Scooter");
			updateCurrentlySelectedObject(manualScooter);
			break;
		default:
			break;
		}
	}
	
	private void updateCurrentObjectTo(String object){
		txt_currentObject = (TextView) findViewById(R.id.cs_txt_currentlyselectedobject);
		txt_currentObject.setText(object);
		
		currentlySelectedObject = object;
	}
	
	/*
	 * This method will determine what type of setup will be displayed in on the count panel
	 * based on the intersection type selected in the CountSetup.
	 * This method will also check which counting button needs to be visible depending on the type
	 * of intersection.
	 * @author: Richard Fong
	 * @since:
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
	 * @author: Richard Fong
	 * @since:
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
	 * @author: Richard Fong
	 * @since:
	 */
	private void updateTotalCounter() {
		txt_totalCount.setText("Total Count: " + totalCount);
	}
	
	/*
	 * This method will update the Textview at the top of the Counting Screen with
	 * the currently selected object.
	 * @author: Richard Fong
	 * @since:
	 */
	private void updateCurrentlySelectedObject(int currentObject){
		txt_currentObjectCount = (TextView) findViewById(R.id.cs_txt_currently_selected_count);
		txt_currentObjectCount.setText(currentlySelectedObject + ": " + currentObject);
	}

	/*
	 * This method will populate all the button methods
	 * @author: Richard Fong
	 * @since:
	 */
	private void populateButtons() {
		populateDirectionButtons();
		populateCounterButtons();
	}
	
	/*
	 * This method sets all the direction buttons to their correct id.
	 * @author: Richard Fong
	 * @since:
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
	
	/*
	 * This method sets all the directional buttons background to a red lined
	 * background to indicate that it's not selected.
	 * @author: Richard Fone
	 * @since:
	 */
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
	
	/*
	 * This method is used to initialise and populate the Timer so that it will be
	 * running with 15 minute intervals.
	 * @author: Richard Fong
	 * @since:
	 */
	private void populateTimer(int timeInMinutes){
		// Instantiates the CountDownTimer.
		
		/*
		 * We will count down in 0.5 seconds instead of 1 second because long is a
		 * "Rough" estimate so therefore we may skip some seconds. 
		 * eg. 00:05 in 1 seconds time maybe change to 00:03.
		 * By updating every 0.5 seconds will result in a more accurate measurement.
		 * Also, start with the time + 500 milliseconds so we can display the initial number
		 */
		
		int minutes = (timeInMinutes * 60000)+500;
		countTimer = new CountDownTimer(minutes, 500);
		btn_start = (Button) findViewById(R.id.cs_btn_start);
		btn_start.setOnClickListener(this);
		btn_stop = (Button) findViewById(R.id.cs_btn_stop);
		btn_stop.setOnClickListener(this);
		// Initilises the Timer TextView to show the correct minutes
		txt_timer = (TextView) findViewById(R.id.cs_txt_timer);
		txt_timer.setText(countTimer.toString());
	}
	
	/*
	 * This method initialises the undo button which will be used later.
	 * @author: Richard Fong
	 * @since:
	 */
	private void populateCounterButtons(){
		btn_undo = (Button) findViewById(R.id.cs_btn_undo);
		btn_undo.setOnClickListener(this);
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

	public static int getWheelchair_assisted() {
		return wheelChair_assisted;
	}

	public static void setWheelchair_assisted(int wheelChair_assisted) {
		CountingScreen.wheelChair_assisted = wheelChair_assisted;
	}

	public static int getWheelchair_manual() {
		return wheelChair_manual;
	}

	public static void setWheelchair_manual(int wheelChair_manual) {
		CountingScreen.wheelChair_manual = wheelChair_manual;
	}

	public static int getWheelchair_powered() {
		return wheelChair_powered;
	}

	public static void setWheelchair_powered(int wheelChair_powered) {
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
	
	public static int getWheelChair_assisted() {
		return wheelChair_assisted;
	}

	public static int getWheelChair_manual() {
		return wheelChair_manual;
	}

	public static int getWheelChair_powered() {
		return wheelChair_powered;
	}

	public static int getNorthWestTotal() {
		return northWestTotal;
	}

	public static int getNorthTotal() {
		return northTotal;
	}

	public static int getNorthEastTotal() {
		return northEastTotal;
	}

	public static int getWestTotal() {
		return westTotal;
	}

	public static int getEastTotal() {
		return eastTotal;
	}

	public static int getSouthWestTotal() {
		return southWestTotal;
	}

	public static int getSouthTotal() {
		return southTotal;
	}

	public static int getSouthEastTotal() {
		return southEastTotal;
	}

	public static int getNorthWestToNorthBus() {
		return northWestToNorthBus;
	}

	public static int getNorthWestToNorthTruck() {
		return northWestToNorthTruck;
	}

	public static int getNorthWestToNorthCar() {
		return northWestToNorthCar;
	}

	public static int getNorthWestToNorthMotorBike() {
		return northWestToNorthMotorBike;
	}

	public static int getNorthWestToNorthPedestrian() {
		return northWestToNorthPedestrian;
	}

	public static int getNorthWestToNorthCrutches1() {
		return northWestToNorthCrutches1;
	}

	public static int getNorthWestToNorthCrutches2() {
		return northWestToNorthCrutches2;
	}

	public static int getNorthWestToNorthCane() {
		return northWestToNorthCane;
	}

	public static int getNorthWestToNorthDog() {
		return northWestToNorthDog;
	}

	public static int getNorthWestToNorthMobilityScooter() {
		return northWestToNorthMobilityScooter;
	}

	public static int getNorthWestToNorthWheelchairAssisted() {
		return northWestToNorthWheelchairAssisted;
	}

	public static int getNorthWestToNorthWheelchairManual() {
		return northWestToNorthWheelchairManual;
	}

	public static int getNorthWestToNorthWheelchairPowered() {
		return northWestToNorthWheelchairPowered;
	}

	public static int getNorthWestToNorthPushChair() {
		return northWestToNorthPushChair;
	}

	public static int getNorthWestToNorthSkateboard() {
		return northWestToNorthSkateboard;
	}

	public static int getNorthWestToNorthManualScooter() {
		return northWestToNorthManualScooter;
	}

	public static int getNorthWestToNorthEastBus() {
		return northWestToNorthEastBus;
	}

	public static int getNorthWestToNorthEastTruck() {
		return northWestToNorthEastTruck;
	}

	public static int getNorthWestToNorthEastCar() {
		return northWestToNorthEastCar;
	}

	public static int getNorthWestToNorthEastMotorBike() {
		return northWestToNorthEastMotorBike;
	}

	public static int getNorthWestToNorthEastPedestrian() {
		return northWestToNorthEastPedestrian;
	}

	public static int getNorthWestToNorthEastCrutches1() {
		return northWestToNorthEastCrutches1;
	}

	public static int getNorthWestToNorthEastCrutches2() {
		return northWestToNorthEastCrutches2;
	}

	public static int getNorthWestToNorthEastCane() {
		return northWestToNorthEastCane;
	}

	public static int getNorthWestToNorthEastDog() {
		return northWestToNorthEastDog;
	}

	public static int getNorthWestToNorthEastMobilityScooter() {
		return northWestToNorthEastMobilityScooter;
	}

	public static int getNorthWestToNorthEastWheelchairAssisted() {
		return northWestToNorthEastWheelchairAssisted;
	}

	public static int getNorthWestToNorthEastWheelchairManual() {
		return northWestToNorthEastWheelchairManual;
	}

	public static int getNorthWestToNorthEastWheelchairPowered() {
		return northWestToNorthEastWheelchairPowered;
	}

	public static int getNorthWestToNorthEastPushChair() {
		return northWestToNorthEastPushChair;
	}

	public static int getNorthWestToNorthEastSkateboard() {
		return northWestToNorthEastSkateboard;
	}

	public static int getNorthWestToNorthEastManualScooter() {
		return northWestToNorthEastManualScooter;
	}

	public static int getNorthWestToWestBus() {
		return northWestToWestBus;
	}

	public static int getNorthWestToWestTruck() {
		return northWestToWestTruck;
	}

	public static int getNorthWestToWestCar() {
		return northWestToWestCar;
	}

	public static int getNorthWestToWestMotorBike() {
		return northWestToWestMotorBike;
	}

	public static int getNorthWestToWestPedestrian() {
		return northWestToWestPedestrian;
	}

	public static int getNorthWestToWestCrutches1() {
		return northWestToWestCrutches1;
	}

	public static int getNorthWestToWestCrutches2() {
		return northWestToWestCrutches2;
	}

	public static int getNorthWestToWestCane() {
		return northWestToWestCane;
	}

	public static int getNorthWestToWestDog() {
		return northWestToWestDog;
	}

	public static int getNorthWestToWestMobilityScooter() {
		return northWestToWestMobilityScooter;
	}

	public static int getNorthWestToWestWheelchairAssisted() {
		return northWestToWestWheelchairAssisted;
	}

	public static int getNorthWestToWestWheelchairManual() {
		return northWestToWestWheelchairManual;
	}

	public static int getNorthWestToWestWheelchairPowered() {
		return northWestToWestWheelchairPowered;
	}

	public static int getNorthWestToWestPushChair() {
		return northWestToWestPushChair;
	}

	public static int getNorthWestToWestSkateboard() {
		return northWestToWestSkateboard;
	}

	public static int getNorthWestToWestManualScooter() {
		return northWestToWestManualScooter;
	}

	public static int getNorthWestToEastBus() {
		return northWestToEastBus;
	}

	public static int getNorthWestToEastTruck() {
		return northWestToEastTruck;
	}

	public static int getNorthWestToEastCar() {
		return northWestToEastCar;
	}

	public static int getNorthWestToEastMotorBike() {
		return northWestToEastMotorBike;
	}

	public static int getNorthWestToEastPedestrian() {
		return northWestToEastPedestrian;
	}

	public static int getNorthWestToEastCrutches1() {
		return northWestToEastCrutches1;
	}

	public static int getNorthWestToEastCrutches2() {
		return northWestToEastCrutches2;
	}

	public static int getNorthWestToEastCane() {
		return northWestToEastCane;
	}

	public static int getNorthWestToEastDog() {
		return northWestToEastDog;
	}

	public static int getNorthWestToEastMobilityScooter() {
		return northWestToEastMobilityScooter;
	}

	public static int getNorthWestToEastWheelchairAssisted() {
		return northWestToEastWheelchairAssisted;
	}

	public static int getNorthWestToEastWheelchairManual() {
		return northWestToEastWheelchairManual;
	}

	public static int getNorthWestToEastWheelchairPowered() {
		return northWestToEastWheelchairPowered;
	}

	public static int getNorthWestToEastPushChair() {
		return northWestToEastPushChair;
	}

	public static int getNorthWestToEastSkateboard() {
		return northWestToEastSkateboard;
	}

	public static int getNorthWestToEastManualScooter() {
		return northWestToEastManualScooter;
	}

	public static int getNorthWestToSouthWestBus() {
		return northWestToSouthWestBus;
	}

	public static int getNorthWestToSouthWestTruck() {
		return northWestToSouthWestTruck;
	}

	public static int getNorthWestToSouthWestCar() {
		return northWestToSouthWestCar;
	}

	public static int getNorthWestToSouthWestMotorBike() {
		return northWestToSouthWestMotorBike;
	}

	public static int getNorthWestToSouthWestPedestrian() {
		return northWestToSouthWestPedestrian;
	}

	public static int getNorthWestToSouthWestCrutches1() {
		return northWestToSouthWestCrutches1;
	}

	public static int getNorthWestToSouthWestCrutches2() {
		return northWestToSouthWestCrutches2;
	}

	public static int getNorthWestToSouthWestCane() {
		return northWestToSouthWestCane;
	}

	public static int getNorthWestToSouthWestDog() {
		return northWestToSouthWestDog;
	}

	public static int getNorthWestToSouthWestMobilityScooter() {
		return northWestToSouthWestMobilityScooter;
	}

	public static int getNorthWestToSouthWestWheelchairAssisted() {
		return northWestToSouthWestWheelchairAssisted;
	}

	public static int getNorthWestToSouthWestWheelchairManual() {
		return northWestToSouthWestWheelchairManual;
	}

	public static int getNorthWestToSouthWestWheelchairPowered() {
		return northWestToSouthWestWheelchairPowered;
	}

	public static int getNorthWestToSouthWestPushChair() {
		return northWestToSouthWestPushChair;
	}

	public static int getNorthWestToSouthWestSkateboard() {
		return northWestToSouthWestSkateboard;
	}

	public static int getNorthWestToSouthWestManualScooter() {
		return northWestToSouthWestManualScooter;
	}

	public static int getNorthWestToSouthBus() {
		return northWestToSouthBus;
	}

	public static int getNorthWestToSouthTruck() {
		return northWestToSouthTruck;
	}

	public static int getNorthWestToSouthCar() {
		return northWestToSouthCar;
	}

	public static int getNorthWestToSouthMotorBike() {
		return northWestToSouthMotorBike;
	}

	public static int getNorthWestToSouthPedestrian() {
		return northWestToSouthPedestrian;
	}

	public static int getNorthWestToSouthCrutches1() {
		return northWestToSouthCrutches1;
	}

	public static int getNorthWestToSouthCrutches2() {
		return northWestToSouthCrutches2;
	}

	public static int getNorthWestToSouthCane() {
		return northWestToSouthCane;
	}

	public static int getNorthWestToSouthDog() {
		return northWestToSouthDog;
	}

	public static int getNorthWestToSouthMobilityScooter() {
		return northWestToSouthMobilityScooter;
	}

	public static int getNorthWestToSouthWheelchairAssisted() {
		return northWestToSouthWheelchairAssisted;
	}

	public static int getNorthWestToSouthWheelchairManual() {
		return northWestToSouthWheelchairManual;
	}

	public static int getNorthWestToSouthWheelchairPowered() {
		return northWestToSouthWheelchairPowered;
	}

	public static int getNorthWestToSouthPushChair() {
		return northWestToSouthPushChair;
	}

	public static int getNorthWestToSouthSkateboard() {
		return northWestToSouthSkateboard;
	}

	public static int getNorthWestToSouthManualScooter() {
		return northWestToSouthManualScooter;
	}

	public static int getNorthWestToSouthEastBus() {
		return northWestToSouthEastBus;
	}

	public static int getNorthWestToSouthEastTruck() {
		return northWestToSouthEastTruck;
	}

	public static int getNorthWestToSouthEastCar() {
		return northWestToSouthEastCar;
	}

	public static int getNorthWestToSouthEastMotorBike() {
		return northWestToSouthEastMotorBike;
	}

	public static int getNorthWestToSouthEastPedestrian() {
		return northWestToSouthEastPedestrian;
	}

	public static int getNorthWestToSouthEastCrutches1() {
		return northWestToSouthEastCrutches1;
	}

	public static int getNorthWestToSouthEastCrutches2() {
		return northWestToSouthEastCrutches2;
	}

	public static int getNorthWestToSouthEastCane() {
		return northWestToSouthEastCane;
	}

	public static int getNorthWestToSouthEastDog() {
		return northWestToSouthEastDog;
	}

	public static int getNorthWestToSouthEastMobilityScooter() {
		return northWestToSouthEastMobilityScooter;
	}

	public static int getNorthWestToSouthEastWheelchairAssisted() {
		return northWestToSouthEastWheelchairAssisted;
	}

	public static int getNorthWestToSouthEastWheelchairManual() {
		return northWestToSouthEastWheelchairManual;
	}

	public static int getNorthWestToSouthEastWheelchairPowered() {
		return northWestToSouthEastWheelchairPowered;
	}

	public static int getNorthWestToSouthEastPushChair() {
		return northWestToSouthEastPushChair;
	}

	public static int getNorthWestToSouthEastSkateboard() {
		return northWestToSouthEastSkateboard;
	}

	public static int getNorthWestToSouthEastManualScooter() {
		return northWestToSouthEastManualScooter;
	}

	public static int getNorthToNorthWestBus() {
		return northToNorthWestBus;
	}

	public static int getNorthToNorthWestTruck() {
		return northToNorthWestTruck;
	}

	public static int getNorthToNorthWestCar() {
		return northToNorthWestCar;
	}

	public static int getNorthToNorthWestMotorBike() {
		return northToNorthWestMotorBike;
	}

	public static int getNorthToNorthWestPedestrian() {
		return northToNorthWestPedestrian;
	}

	public static int getNorthToNorthWestCrutches1() {
		return northToNorthWestCrutches1;
	}

	public static int getNorthToNorthWestCrutches2() {
		return northToNorthWestCrutches2;
	}

	public static int getNorthToNorthWestCane() {
		return northToNorthWestCane;
	}

	public static int getNorthToNorthWestDog() {
		return northToNorthWestDog;
	}

	public static int getNorthToNorthWestMobilityScooter() {
		return northToNorthWestMobilityScooter;
	}

	public static int getNorthToNorthWestWheelchairAssisted() {
		return northToNorthWestWheelchairAssisted;
	}

	public static int getNorthToNorthWestWheelchairManual() {
		return northToNorthWestWheelchairManual;
	}

	public static int getNorthToNorthWestWheelchairPowered() {
		return northToNorthWestWheelchairPowered;
	}

	public static int getNorthToNorthWestPushChair() {
		return northToNorthWestPushChair;
	}

	public static int getNorthToNorthWestSkateboard() {
		return northToNorthWestSkateboard;
	}

	public static int getNorthToNorthWestManualScooter() {
		return northToNorthWestManualScooter;
	}

	public static int getNorthToNorthEastBus() {
		return northToNorthEastBus;
	}

	public static int getNorthToNorthEastTruck() {
		return northToNorthEastTruck;
	}

	public static int getNorthToNorthEastCar() {
		return northToNorthEastCar;
	}

	public static int getNorthToNorthEastMotorBike() {
		return northToNorthEastMotorBike;
	}

	public static int getNorthToNorthEastPedestrian() {
		return northToNorthEastPedestrian;
	}

	public static int getNorthToNorthEastCrutches1() {
		return northToNorthEastCrutches1;
	}

	public static int getNorthToNorthEastCrutches2() {
		return northToNorthEastCrutches2;
	}

	public static int getNorthToNorthEastCane() {
		return northToNorthEastCane;
	}

	public static int getNorthToNorthEastDog() {
		return northToNorthEastDog;
	}

	public static int getNorthToNorthEastMobilityScooter() {
		return northToNorthEastMobilityScooter;
	}

	public static int getNorthToNorthEastWheelchairAssisted() {
		return northToNorthEastWheelchairAssisted;
	}

	public static int getNorthToNorthEastWheelchairManual() {
		return northToNorthEastWheelchairManual;
	}

	public static int getNorthToNorthEastWheelchairPowered() {
		return northToNorthEastWheelchairPowered;
	}

	public static int getNorthToNorthEastPushChair() {
		return northToNorthEastPushChair;
	}

	public static int getNorthToNorthEastSkateboard() {
		return northToNorthEastSkateboard;
	}

	public static int getNorthToNorthEastManualScooter() {
		return northToNorthEastManualScooter;
	}

	public static int getNorthToWestBus() {
		return northToWestBus;
	}

	public static int getNorthToWestTruck() {
		return northToWestTruck;
	}

	public static int getNorthToWestCar() {
		return northToWestCar;
	}

	public static int getNorthToWestMotorBike() {
		return northToWestMotorBike;
	}

	public static int getNorthToWestPedestrian() {
		return northToWestPedestrian;
	}

	public static int getNorthToWestCrutches1() {
		return northToWestCrutches1;
	}

	public static int getNorthToWestCrutches2() {
		return northToWestCrutches2;
	}

	public static int getNorthToWestCane() {
		return northToWestCane;
	}

	public static int getNorthToWestDog() {
		return northToWestDog;
	}

	public static int getNorthToWestMobilityScooter() {
		return northToWestMobilityScooter;
	}

	public static int getNorthToWestWheelchairAssisted() {
		return northToWestWheelchairAssisted;
	}

	public static int getNorthToWestWheelchairManual() {
		return northToWestWheelchairManual;
	}

	public static int getNorthToWestWheelchairPowered() {
		return northToWestWheelchairPowered;
	}

	public static int getNorthToWestPushChair() {
		return northToWestPushChair;
	}

	public static int getNorthToWestSkateboard() {
		return northToWestSkateboard;
	}

	public static int getNorthToWestManualScooter() {
		return northToWestManualScooter;
	}

	public static int getNorthToEastBus() {
		return northToEastBus;
	}

	public static int getNorthToEastTruck() {
		return northToEastTruck;
	}

	public static int getNorthToEastCar() {
		return northToEastCar;
	}

	public static int getNorthToEastMotorBike() {
		return northToEastMotorBike;
	}

	public static int getNorthToEastPedestrian() {
		return northToEastPedestrian;
	}

	public static int getNorthToEastCrutches1() {
		return northToEastCrutches1;
	}

	public static int getNorthToEastCrutches2() {
		return northToEastCrutches2;
	}

	public static int getNorthToEastCane() {
		return northToEastCane;
	}

	public static int getNorthToEastDog() {
		return northToEastDog;
	}

	public static int getNorthToEastMobilityScooter() {
		return northToEastMobilityScooter;
	}

	public static int getNorthToEastWheelchairAssisted() {
		return northToEastWheelchairAssisted;
	}

	public static int getNorthToEastWheelchairManual() {
		return northToEastWheelchairManual;
	}

	public static int getNorthToEastWheelchairPowered() {
		return northToEastWheelchairPowered;
	}

	public static int getNorthToEastPushChair() {
		return northToEastPushChair;
	}

	public static int getNorthToEastSkateboard() {
		return northToEastSkateboard;
	}

	public static int getNorthToEastManualScooter() {
		return northToEastManualScooter;
	}

	public static int getNorthToSouthWestBus() {
		return northToSouthWestBus;
	}

	public static int getNorthToSouthWestTruck() {
		return northToSouthWestTruck;
	}

	public static int getNorthToSouthWestCar() {
		return northToSouthWestCar;
	}

	public static int getNorthToSouthWestMotorBike() {
		return northToSouthWestMotorBike;
	}

	public static int getNorthToSouthWestPedestrian() {
		return northToSouthWestPedestrian;
	}

	public static int getNorthToSouthWestCrutches1() {
		return northToSouthWestCrutches1;
	}

	public static int getNorthToSouthWestCrutches2() {
		return northToSouthWestCrutches2;
	}

	public static int getNorthToSouthWestCane() {
		return northToSouthWestCane;
	}

	public static int getNorthToSouthWestDog() {
		return northToSouthWestDog;
	}

	public static int getNorthToSouthWestMobilityScooter() {
		return northToSouthWestMobilityScooter;
	}

	public static int getNorthToSouthWestWheelchairAssisted() {
		return northToSouthWestWheelchairAssisted;
	}

	public static int getNorthToSouthWestWheelchairManual() {
		return northToSouthWestWheelchairManual;
	}

	public static int getNorthToSouthWestWheelchairPowered() {
		return northToSouthWestWheelchairPowered;
	}

	public static int getNorthToSouthWestPushChair() {
		return northToSouthWestPushChair;
	}

	public static int getNorthToSouthWestSkateboard() {
		return northToSouthWestSkateboard;
	}

	public static int getNorthToSouthWestManualScooter() {
		return northToSouthWestManualScooter;
	}

	public static int getNorthToSouthBus() {
		return northToSouthBus;
	}

	public static int getNorthToSouthTruck() {
		return northToSouthTruck;
	}

	public static int getNorthToSouthCar() {
		return northToSouthCar;
	}

	public static int getNorthToSouthMotorBike() {
		return northToSouthMotorBike;
	}

	public static int getNorthToSouthPedestrian() {
		return northToSouthPedestrian;
	}

	public static int getNorthToSouthCrutches1() {
		return northToSouthCrutches1;
	}

	public static int getNorthToSouthCrutches2() {
		return northToSouthCrutches2;
	}

	public static int getNorthToSouthCane() {
		return northToSouthCane;
	}

	public static int getNorthToSouthDog() {
		return northToSouthDog;
	}

	public static int getNorthToSouthMobilityScooter() {
		return northToSouthMobilityScooter;
	}

	public static int getNorthToSouthWheelchairAssisted() {
		return northToSouthWheelchairAssisted;
	}

	public static int getNorthToSouthWheelchairManual() {
		return northToSouthWheelchairManual;
	}

	public static int getNorthToSouthWheelchairPowered() {
		return northToSouthWheelchairPowered;
	}

	public static int getNorthToSouthPushChair() {
		return northToSouthPushChair;
	}

	public static int getNorthToSouthSkateboard() {
		return northToSouthSkateboard;
	}

	public static int getNorthToSouthManualScooter() {
		return northToSouthManualScooter;
	}

	public static int getNorthToSouthEastBus() {
		return northToSouthEastBus;
	}

	public static int getNorthToSouthEastTruck() {
		return northToSouthEastTruck;
	}

	public static int getNorthToSouthEastCar() {
		return northToSouthEastCar;
	}

	public static int getNorthToSouthEastMotorBike() {
		return northToSouthEastMotorBike;
	}

	public static int getNorthToSouthEastPedestrian() {
		return northToSouthEastPedestrian;
	}

	public static int getNorthToSouthEastCrutches1() {
		return northToSouthEastCrutches1;
	}

	public static int getNorthToSouthEastCrutches2() {
		return northToSouthEastCrutches2;
	}

	public static int getNorthToSouthEastCane() {
		return northToSouthEastCane;
	}

	public static int getNorthToSouthEastDog() {
		return northToSouthEastDog;
	}

	public static int getNorthToSouthEastMobilityScooter() {
		return northToSouthEastMobilityScooter;
	}

	public static int getNorthToSouthEastWheelchairAssisted() {
		return northToSouthEastWheelchairAssisted;
	}

	public static int getNorthToSouthEastWheelchairManual() {
		return northToSouthEastWheelchairManual;
	}

	public static int getNorthToSouthEastWheelchairPowered() {
		return northToSouthEastWheelchairPowered;
	}

	public static int getNorthToSouthEastPushChair() {
		return northToSouthEastPushChair;
	}

	public static int getNorthToSouthEastSkateboard() {
		return northToSouthEastSkateboard;
	}

	public static int getNorthToSouthEastManualScooter() {
		return northToSouthEastManualScooter;
	}

	public static int getNorthEastToNorthWestBus() {
		return northEastToNorthWestBus;
	}

	public static int getNorthEastToNorthWestTruck() {
		return northEastToNorthWestTruck;
	}

	public static int getNorthEastToNorthWestCar() {
		return northEastToNorthWestCar;
	}

	public static int getNorthEastToNorthWestMotorBike() {
		return northEastToNorthWestMotorBike;
	}

	public static int getNorthEastToNorthWestPedestrian() {
		return northEastToNorthWestPedestrian;
	}

	public static int getNorthEastToNorthWestCrutches1() {
		return northEastToNorthWestCrutches1;
	}

	public static int getNorthEastToNorthWestCrutches2() {
		return northEastToNorthWestCrutches2;
	}

	public static int getNorthEastToNorthWestCane() {
		return northEastToNorthWestCane;
	}

	public static int getNorthEastToNorthWestDog() {
		return northEastToNorthWestDog;
	}

	public static int getNorthEastToNorthWestMobilityScooter() {
		return northEastToNorthWestMobilityScooter;
	}

	public static int getNorthEastToNorthWestWheelchairAssisted() {
		return northEastToNorthWestWheelchairAssisted;
	}

	public static int getNorthEastToNorthWestWheelchairManual() {
		return northEastToNorthWestWheelchairManual;
	}

	public static int getNorthEastToNorthWestWheelchairPowered() {
		return northEastToNorthWestWheelchairPowered;
	}

	public static int getNorthEastToNorthWestPushChair() {
		return northEastToNorthWestPushChair;
	}

	public static int getNorthEastToNorthWestSkateboard() {
		return northEastToNorthWestSkateboard;
	}

	public static int getNorthEastToNorthWestManualScooter() {
		return northEastToNorthWestManualScooter;
	}

	public static int getNorthEastToNorthBus() {
		return northEastToNorthBus;
	}

	public static int getNorthEastToNorthTruck() {
		return northEastToNorthTruck;
	}

	public static int getNorthEastToNorthCar() {
		return northEastToNorthCar;
	}

	public static int getNorthEastToNorthMotorBike() {
		return northEastToNorthMotorBike;
	}

	public static int getNorthEastToNorthPedestrian() {
		return northEastToNorthPedestrian;
	}

	public static int getNorthEastToNorthCrutches1() {
		return northEastToNorthCrutches1;
	}

	public static int getNorthEastToNorthCrutches2() {
		return northEastToNorthCrutches2;
	}

	public static int getNorthEastToNorthCane() {
		return northEastToNorthCane;
	}

	public static int getNorthEastToNorthDog() {
		return northEastToNorthDog;
	}

	public static int getNorthEastToNorthMobilityScooter() {
		return northEastToNorthMobilityScooter;
	}

	public static int getNorthEastToNorthWheelchairAssisted() {
		return northEastToNorthWheelchairAssisted;
	}

	public static int getNorthEastToNorthWheelchairManual() {
		return northEastToNorthWheelchairManual;
	}

	public static int getNorthEastToNorthWheelchairPowered() {
		return northEastToNorthWheelchairPowered;
	}

	public static int getNorthEastToNorthPushChair() {
		return northEastToNorthPushChair;
	}

	public static int getNorthEastToNorthSkateboard() {
		return northEastToNorthSkateboard;
	}

	public static int getNorthEastToNorthManualScooter() {
		return northEastToNorthManualScooter;
	}

	public static int getNorthEastToWestBus() {
		return northEastToWestBus;
	}

	public static int getNorthEastToWestTruck() {
		return northEastToWestTruck;
	}

	public static int getNorthEastToWestCar() {
		return northEastToWestCar;
	}

	public static int getNorthEastToWestMotorBike() {
		return northEastToWestMotorBike;
	}

	public static int getNorthEastToWestPedestrian() {
		return northEastToWestPedestrian;
	}

	public static int getNorthEastToWestCrutches1() {
		return northEastToWestCrutches1;
	}

	public static int getNorthEastToWestCrutches2() {
		return northEastToWestCrutches2;
	}

	public static int getNorthEastToWestCane() {
		return northEastToWestCane;
	}

	public static int getNorthEastToWestDog() {
		return northEastToWestDog;
	}

	public static int getNorthEastToWestMobilityScooter() {
		return northEastToWestMobilityScooter;
	}

	public static int getNorthEastToWestWheelchairAssisted() {
		return northEastToWestWheelchairAssisted;
	}

	public static int getNorthEastToWestWheelchairManual() {
		return northEastToWestWheelchairManual;
	}

	public static int getNorthEastToWestWheelchairPowered() {
		return northEastToWestWheelchairPowered;
	}

	public static int getNorthEastToWestPushChair() {
		return northEastToWestPushChair;
	}

	public static int getNorthEastToWestSkateboard() {
		return northEastToWestSkateboard;
	}

	public static int getNorthEastToWestManualScooter() {
		return northEastToWestManualScooter;
	}

	public static int getNorthEastToEastBus() {
		return northEastToEastBus;
	}

	public static int getNorthEastToEastTruck() {
		return northEastToEastTruck;
	}

	public static int getNorthEastToEastCar() {
		return northEastToEastCar;
	}

	public static int getNorthEastToEastMotorBike() {
		return northEastToEastMotorBike;
	}

	public static int getNorthEastToEastPedestrian() {
		return northEastToEastPedestrian;
	}

	public static int getNorthEastToEastCrutches1() {
		return northEastToEastCrutches1;
	}

	public static int getNorthEastToEastCrutches2() {
		return northEastToEastCrutches2;
	}

	public static int getNorthEastToEastCane() {
		return northEastToEastCane;
	}

	public static int getNorthEastToEastDog() {
		return northEastToEastDog;
	}

	public static int getNorthEastToEastMobilityScooter() {
		return northEastToEastMobilityScooter;
	}

	public static int getNorthEastToEastWheelchairAssisted() {
		return northEastToEastWheelchairAssisted;
	}

	public static int getNorthEastToEastWheelchairManual() {
		return northEastToEastWheelchairManual;
	}

	public static int getNorthEastToEastWheelchairPowered() {
		return northEastToEastWheelchairPowered;
	}

	public static int getNorthEastToEastPushChair() {
		return northEastToEastPushChair;
	}

	public static int getNorthEastToEastSkateboard() {
		return northEastToEastSkateboard;
	}

	public static int getNorthEastToEastManualScooter() {
		return northEastToEastManualScooter;
	}

	public static int getNorthEastToSouthWestBus() {
		return northEastToSouthWestBus;
	}

	public static int getNorthEastToSouthWestTruck() {
		return northEastToSouthWestTruck;
	}

	public static int getNorthEastToSouthWestCar() {
		return northEastToSouthWestCar;
	}

	public static int getNorthEastToSouthWestMotorBike() {
		return northEastToSouthWestMotorBike;
	}

	public static int getNorthEastToSouthWestPedestrian() {
		return northEastToSouthWestPedestrian;
	}

	public static int getNorthEastToSouthWestCrutches1() {
		return northEastToSouthWestCrutches1;
	}

	public static int getNorthEastToSouthWestCrutches2() {
		return northEastToSouthWestCrutches2;
	}

	public static int getNorthEastToSouthWestCane() {
		return northEastToSouthWestCane;
	}

	public static int getNorthEastToSouthWestDog() {
		return northEastToSouthWestDog;
	}

	public static int getNorthEastToSouthWestMobilityScooter() {
		return northEastToSouthWestMobilityScooter;
	}

	public static int getNorthEastToSouthWestWheelchairAssisted() {
		return northEastToSouthWestWheelchairAssisted;
	}

	public static int getNorthEastToSouthWestWheelchairManual() {
		return northEastToSouthWestWheelchairManual;
	}

	public static int getNorthEastToSouthWestWheelchairPowered() {
		return northEastToSouthWestWheelchairPowered;
	}

	public static int getNorthEastToSouthWestPushChair() {
		return northEastToSouthWestPushChair;
	}

	public static int getNorthEastToSouthWestSkateboard() {
		return northEastToSouthWestSkateboard;
	}

	public static int getNorthEastToSouthWestManualScooter() {
		return northEastToSouthWestManualScooter;
	}

	public static int getNorthEastToSouthBus() {
		return northEastToSouthBus;
	}

	public static int getNorthEastToSouthTruck() {
		return northEastToSouthTruck;
	}

	public static int getNorthEastToSouthCar() {
		return northEastToSouthCar;
	}

	public static int getNorthEastToSouthMotorBike() {
		return northEastToSouthMotorBike;
	}

	public static int getNorthEastToSouthPedestrian() {
		return northEastToSouthPedestrian;
	}

	public static int getNorthEastToSouthCrutches1() {
		return northEastToSouthCrutches1;
	}

	public static int getNorthEastToSouthCrutches2() {
		return northEastToSouthCrutches2;
	}

	public static int getNorthEastToSouthCane() {
		return northEastToSouthCane;
	}

	public static int getNorthEastToSouthDog() {
		return northEastToSouthDog;
	}

	public static int getNorthEastToSouthMobilityScooter() {
		return northEastToSouthMobilityScooter;
	}

	public static int getNorthEastToSouthWheelchairAssisted() {
		return northEastToSouthWheelchairAssisted;
	}

	public static int getNorthEastToSouthWheelchairManual() {
		return northEastToSouthWheelchairManual;
	}

	public static int getNorthEastToSouthWheelchairPowered() {
		return northEastToSouthWheelchairPowered;
	}

	public static int getNorthEastToSouthPushChair() {
		return northEastToSouthPushChair;
	}

	public static int getNorthEastToSouthSkateboard() {
		return northEastToSouthSkateboard;
	}

	public static int getNorthEastToSouthManualScooter() {
		return northEastToSouthManualScooter;
	}

	public static int getNorthEastToSouthEastBus() {
		return northEastToSouthEastBus;
	}

	public static int getNorthEastToSouthEastTruck() {
		return northEastToSouthEastTruck;
	}

	public static int getNorthEastToSouthEastCar() {
		return northEastToSouthEastCar;
	}

	public static int getNorthEastToSouthEastMotorBike() {
		return northEastToSouthEastMotorBike;
	}

	public static int getNorthEastToSouthEastPedestrian() {
		return northEastToSouthEastPedestrian;
	}

	public static int getNorthEastToSouthEastCrutches1() {
		return northEastToSouthEastCrutches1;
	}

	public static int getNorthEastToSouthEastCrutches2() {
		return northEastToSouthEastCrutches2;
	}

	public static int getNorthEastToSouthEastCane() {
		return northEastToSouthEastCane;
	}

	public static int getNorthEastToSouthEastDog() {
		return northEastToSouthEastDog;
	}

	public static int getNorthEastToSouthEastMobilityScooter() {
		return northEastToSouthEastMobilityScooter;
	}

	public static int getNorthEastToSouthEastWheelchairAssisted() {
		return northEastToSouthEastWheelchairAssisted;
	}

	public static int getNorthEastToSouthEastWheelchairManual() {
		return northEastToSouthEastWheelchairManual;
	}

	public static int getNorthEastToSouthEastWheelchairPowered() {
		return northEastToSouthEastWheelchairPowered;
	}

	public static int getNorthEastToSouthEastPushChair() {
		return northEastToSouthEastPushChair;
	}

	public static int getNorthEastToSouthEastSkateboard() {
		return northEastToSouthEastSkateboard;
	}

	public static int getNorthEastToSouthEastManualScooter() {
		return northEastToSouthEastManualScooter;
	}

	public static int getWestToNorthWestBus() {
		return westToNorthWestBus;
	}

	public static int getWestToNorthWestTruck() {
		return westToNorthWestTruck;
	}

	public static int getWestToNorthWestCar() {
		return westToNorthWestCar;
	}

	public static int getWestToNorthWestMotorBike() {
		return westToNorthWestMotorBike;
	}

	public static int getWestToNorthWestPedestrian() {
		return westToNorthWestPedestrian;
	}

	public static int getWestToNorthWestCrutches1() {
		return westToNorthWestCrutches1;
	}

	public static int getWestToNorthWestCrutches2() {
		return westToNorthWestCrutches2;
	}

	public static int getWestToNorthWestCane() {
		return westToNorthWestCane;
	}

	public static int getWestToNorthWestDog() {
		return westToNorthWestDog;
	}

	public static int getWestToNorthWestMobilityScooter() {
		return westToNorthWestMobilityScooter;
	}

	public static int getWestToNorthWestWheelchairAssisted() {
		return westToNorthWestWheelchairAssisted;
	}

	public static int getWestToNorthWestWheelchairManual() {
		return westToNorthWestWheelchairManual;
	}

	public static int getWestToNorthWestWheelchairPowered() {
		return westToNorthWestWheelchairPowered;
	}

	public static int getWestToNorthWestPushChair() {
		return westToNorthWestPushChair;
	}

	public static int getWestToNorthWestSkateboard() {
		return westToNorthWestSkateboard;
	}

	public static int getWestToNorthWestManualScooter() {
		return westToNorthWestManualScooter;
	}

	public static int getWestToNorthBus() {
		return westToNorthBus;
	}

	public static int getWestToNorthTruck() {
		return westToNorthTruck;
	}

	public static int getWestToNorthCar() {
		return westToNorthCar;
	}

	public static int getWestToNorthMotorBike() {
		return westToNorthMotorBike;
	}

	public static int getWestToNorthPedestrian() {
		return westToNorthPedestrian;
	}

	public static int getWestToNorthCrutches1() {
		return westToNorthCrutches1;
	}

	public static int getWestToNorthCrutches2() {
		return westToNorthCrutches2;
	}

	public static int getWestToNorthCane() {
		return westToNorthCane;
	}

	public static int getWestToNorthDog() {
		return westToNorthDog;
	}

	public static int getWestToNorthMobilityScooter() {
		return westToNorthMobilityScooter;
	}

	public static int getWestToNorthWheelchairAssisted() {
		return westToNorthWheelchairAssisted;
	}

	public static int getWestToNorthWheelchairManual() {
		return westToNorthWheelchairManual;
	}

	public static int getWestToNorthWheelchairPowered() {
		return westToNorthWheelchairPowered;
	}

	public static int getWestToNorthPushChair() {
		return westToNorthPushChair;
	}

	public static int getWestToNorthSkateboard() {
		return westToNorthSkateboard;
	}

	public static int getWestToNorthManualScooter() {
		return westToNorthManualScooter;
	}

	public static int getWestToNorthEastBus() {
		return westToNorthEastBus;
	}

	public static int getWestToNorthEastTruck() {
		return westToNorthEastTruck;
	}

	public static int getWestToNorthEastCar() {
		return westToNorthEastCar;
	}

	public static int getWestToNorthEastMotorBike() {
		return westToNorthEastMotorBike;
	}

	public static int getWestToNorthEastPedestrian() {
		return westToNorthEastPedestrian;
	}

	public static int getWestToNorthEastCrutches1() {
		return westToNorthEastCrutches1;
	}

	public static int getWestToNorthEastCrutches2() {
		return westToNorthEastCrutches2;
	}

	public static int getWestToNorthEastCane() {
		return westToNorthEastCane;
	}

	public static int getWestToNorthEastDog() {
		return westToNorthEastDog;
	}

	public static int getWestToNorthEastMobilityScooter() {
		return westToNorthEastMobilityScooter;
	}

	public static int getWestToNorthEastWheelchairAssisted() {
		return westToNorthEastWheelchairAssisted;
	}

	public static int getWestToNorthEastWheelchairManual() {
		return westToNorthEastWheelchairManual;
	}

	public static int getWestToNorthEastWheelchairPowered() {
		return westToNorthEastWheelchairPowered;
	}

	public static int getWestToNorthEastPushChair() {
		return westToNorthEastPushChair;
	}

	public static int getWestToNorthEastSkateboard() {
		return westToNorthEastSkateboard;
	}

	public static int getWestToNorthEastManualScooter() {
		return westToNorthEastManualScooter;
	}

	public static int getWestToEastBus() {
		return westToEastBus;
	}

	public static int getWestToEastTruck() {
		return westToEastTruck;
	}

	public static int getWestToEastCar() {
		return westToEastCar;
	}

	public static int getWestToEastMotorBike() {
		return westToEastMotorBike;
	}

	public static int getWestToEastPedestrian() {
		return westToEastPedestrian;
	}

	public static int getWestToEastCrutches1() {
		return westToEastCrutches1;
	}

	public static int getWestToEastCrutches2() {
		return westToEastCrutches2;
	}

	public static int getWestToEastCane() {
		return westToEastCane;
	}

	public static int getWestToEastDog() {
		return westToEastDog;
	}

	public static int getWestToEastMobilityScooter() {
		return westToEastMobilityScooter;
	}

	public static int getWestToEastWheelchairAssisted() {
		return westToEastWheelchairAssisted;
	}

	public static int getWestToEastWheelchairManual() {
		return westToEastWheelchairManual;
	}

	public static int getWestToEastWheelchairPowered() {
		return westToEastWheelchairPowered;
	}

	public static int getWestToEastPushChair() {
		return westToEastPushChair;
	}

	public static int getWestToEastSkateboard() {
		return westToEastSkateboard;
	}

	public static int getWestToEastManualScooter() {
		return westToEastManualScooter;
	}

	public static int getWestToSouthWestBus() {
		return westToSouthWestBus;
	}

	public static int getWestToSouthWestTruck() {
		return westToSouthWestTruck;
	}

	public static int getWestToSouthWestCar() {
		return westToSouthWestCar;
	}

	public static int getWestToSouthWestMotorBike() {
		return westToSouthWestMotorBike;
	}

	public static int getWestToSouthWestPedestrian() {
		return westToSouthWestPedestrian;
	}

	public static int getWestToSouthWestCrutches1() {
		return westToSouthWestCrutches1;
	}

	public static int getWestToSouthWestCrutches2() {
		return westToSouthWestCrutches2;
	}

	public static int getWestToSouthWestCane() {
		return westToSouthWestCane;
	}

	public static int getWestToSouthWestDog() {
		return westToSouthWestDog;
	}

	public static int getWestToSouthWestMobilityScooter() {
		return westToSouthWestMobilityScooter;
	}

	public static int getWestToSouthWestWheelchairAssisted() {
		return westToSouthWestWheelchairAssisted;
	}

	public static int getWestToSouthWestWheelchairManual() {
		return westToSouthWestWheelchairManual;
	}

	public static int getWestToSouthWestWheelchairPowered() {
		return westToSouthWestWheelchairPowered;
	}

	public static int getWestToSouthWestPushChair() {
		return westToSouthWestPushChair;
	}

	public static int getWestToSouthWestSkateboard() {
		return westToSouthWestSkateboard;
	}

	public static int getWestToSouthWestManualScooter() {
		return westToSouthWestManualScooter;
	}

	public static int getWestToSouthBus() {
		return westToSouthBus;
	}

	public static int getWestToSouthTruck() {
		return westToSouthTruck;
	}

	public static int getWestToSouthCar() {
		return westToSouthCar;
	}

	public static int getWestToSouthMotorBike() {
		return westToSouthMotorBike;
	}

	public static int getWestToSouthPedestrian() {
		return westToSouthPedestrian;
	}

	public static int getWestToSouthCrutches1() {
		return westToSouthCrutches1;
	}

	public static int getWestToSouthCrutches2() {
		return westToSouthCrutches2;
	}

	public static int getWestToSouthCane() {
		return westToSouthCane;
	}

	public static int getWestToSouthDog() {
		return westToSouthDog;
	}

	public static int getWestToSouthMobilityScooter() {
		return westToSouthMobilityScooter;
	}

	public static int getWestToSouthWheelchairAssisted() {
		return westToSouthWheelchairAssisted;
	}

	public static int getWestToSouthWheelchairManual() {
		return westToSouthWheelchairManual;
	}

	public static int getWestToSouthWheelchairPowered() {
		return westToSouthWheelchairPowered;
	}

	public static int getWestToSouthPushChair() {
		return westToSouthPushChair;
	}

	public static int getWestToSouthSkateboard() {
		return westToSouthSkateboard;
	}

	public static int getWestToSouthManualScooter() {
		return westToSouthManualScooter;
	}

	public static int getWestToSouthEastBus() {
		return westToSouthEastBus;
	}

	public static int getWestToSouthEastTruck() {
		return westToSouthEastTruck;
	}

	public static int getWestToSouthEastCar() {
		return westToSouthEastCar;
	}

	public static int getWestToSouthEastMotorBike() {
		return westToSouthEastMotorBike;
	}

	public static int getWestToSouthEastPedestrian() {
		return westToSouthEastPedestrian;
	}

	public static int getWestToSouthEastCrutches1() {
		return westToSouthEastCrutches1;
	}

	public static int getWestToSouthEastCrutches2() {
		return westToSouthEastCrutches2;
	}

	public static int getWestToSouthEastCane() {
		return westToSouthEastCane;
	}

	public static int getWestToSouthEastDog() {
		return westToSouthEastDog;
	}

	public static int getWestToSouthEastMobilityScooter() {
		return westToSouthEastMobilityScooter;
	}

	public static int getWestToSouthEastWheelchairAssisted() {
		return westToSouthEastWheelchairAssisted;
	}

	public static int getWestToSouthEastWheelchairManual() {
		return westToSouthEastWheelchairManual;
	}

	public static int getWestToSouthEastWheelchairPowered() {
		return westToSouthEastWheelchairPowered;
	}

	public static int getWestToSouthEastPushChair() {
		return westToSouthEastPushChair;
	}

	public static int getWestToSouthEastSkateboard() {
		return westToSouthEastSkateboard;
	}

	public static int getWestToSouthEastManualScooter() {
		return westToSouthEastManualScooter;
	}

	public static int getEastToNorthWestBus() {
		return eastToNorthWestBus;
	}

	public static int getEastToNorthWestTruck() {
		return eastToNorthWestTruck;
	}

	public static int getEastToNorthWestCar() {
		return eastToNorthWestCar;
	}

	public static int getEastToNorthWestMotorBike() {
		return eastToNorthWestMotorBike;
	}

	public static int getEastToNorthWestPedestrian() {
		return eastToNorthWestPedestrian;
	}

	public static int getEastToNorthWestCrutches1() {
		return eastToNorthWestCrutches1;
	}

	public static int getEastToNorthWestCrutches2() {
		return eastToNorthWestCrutches2;
	}

	public static int getEastToNorthWestCane() {
		return eastToNorthWestCane;
	}

	public static int getEastToNorthWestDog() {
		return eastToNorthWestDog;
	}

	public static int getEastToNorthWestMobilityScooter() {
		return eastToNorthWestMobilityScooter;
	}

	public static int getEastToNorthWestWheelchairAssisted() {
		return eastToNorthWestWheelchairAssisted;
	}

	public static int getEastToNorthWestWheelchairManual() {
		return eastToNorthWestWheelchairManual;
	}

	public static int getEastToNorthWestWheelchairPowered() {
		return eastToNorthWestWheelchairPowered;
	}

	public static int getEastToNorthWestPushChair() {
		return eastToNorthWestPushChair;
	}

	public static int getEastToNorthWestSkateboard() {
		return eastToNorthWestSkateboard;
	}

	public static int getEastToNorthWestManualScooter() {
		return eastToNorthWestManualScooter;
	}

	public static int getEastToNorthBus() {
		return eastToNorthBus;
	}

	public static int getEastToNorthTruck() {
		return eastToNorthTruck;
	}

	public static int getEastToNorthCar() {
		return eastToNorthCar;
	}

	public static int getEastToNorthMotorBike() {
		return eastToNorthMotorBike;
	}

	public static int getEastToNorthPedestrian() {
		return eastToNorthPedestrian;
	}

	public static int getEastToNorthCrutches1() {
		return eastToNorthCrutches1;
	}

	public static int getEastToNorthCrutches2() {
		return eastToNorthCrutches2;
	}

	public static int getEastToNorthCane() {
		return eastToNorthCane;
	}

	public static int getEastToNorthDog() {
		return eastToNorthDog;
	}

	public static int getEastToNorthMobilityScooter() {
		return eastToNorthMobilityScooter;
	}

	public static int getEastToNorthWheelchairAssisted() {
		return eastToNorthWheelchairAssisted;
	}

	public static int getEastToNorthWheelchairManual() {
		return eastToNorthWheelchairManual;
	}

	public static int getEastToNorthWheelchairPowered() {
		return eastToNorthWheelchairPowered;
	}

	public static int getEastToNorthPushChair() {
		return eastToNorthPushChair;
	}

	public static int getEastToNorthSkateboard() {
		return eastToNorthSkateboard;
	}

	public static int getEastToNorthManualScooter() {
		return eastToNorthManualScooter;
	}

	public static int getEastToNorthEastBus() {
		return eastToNorthEastBus;
	}

	public static int getEastToNorthEastTruck() {
		return eastToNorthEastTruck;
	}

	public static int getEastToNorthEastCar() {
		return eastToNorthEastCar;
	}

	public static int getEastToNorthEastMotorBike() {
		return eastToNorthEastMotorBike;
	}

	public static int getEastToNorthEastPedestrian() {
		return eastToNorthEastPedestrian;
	}

	public static int getEastToNorthEastCrutches1() {
		return eastToNorthEastCrutches1;
	}

	public static int getEastToNorthEastCrutches2() {
		return eastToNorthEastCrutches2;
	}

	public static int getEastToNorthEastCane() {
		return eastToNorthEastCane;
	}

	public static int getEastToNorthEastDog() {
		return eastToNorthEastDog;
	}

	public static int getEastToNorthEastMobilityScooter() {
		return eastToNorthEastMobilityScooter;
	}

	public static int getEastToNorthEastWheelchairAssisted() {
		return eastToNorthEastWheelchairAssisted;
	}

	public static int getEastToNorthEastWheelchairManual() {
		return eastToNorthEastWheelchairManual;
	}

	public static int getEastToNorthEastWheelchairPowered() {
		return eastToNorthEastWheelchairPowered;
	}

	public static int getEastToNorthEastPushChair() {
		return eastToNorthEastPushChair;
	}

	public static int getEastToNorthEastSkateboard() {
		return eastToNorthEastSkateboard;
	}

	public static int getEastToNorthEastManualScooter() {
		return eastToNorthEastManualScooter;
	}

	public static int getEastToWestBus() {
		return eastToWestBus;
	}

	public static int getEastToWestTruck() {
		return eastToWestTruck;
	}

	public static int getEastToWestCar() {
		return eastToWestCar;
	}

	public static int getEastToWestMotorBike() {
		return eastToWestMotorBike;
	}

	public static int getEastToWestPedestrian() {
		return eastToWestPedestrian;
	}

	public static int getEastToWestCrutches1() {
		return eastToWestCrutches1;
	}

	public static int getEastToWestCrutches2() {
		return eastToWestCrutches2;
	}

	public static int getEastToWestCane() {
		return eastToWestCane;
	}

	public static int getEastToWestDog() {
		return eastToWestDog;
	}

	public static int getEastToWestMobilityScooter() {
		return eastToWestMobilityScooter;
	}

	public static int getEastToWestWheelchairAssisted() {
		return eastToWestWheelchairAssisted;
	}

	public static int getEastToWestWheelchairManual() {
		return eastToWestWheelchairManual;
	}

	public static int getEastToWestWheelchairPowered() {
		return eastToWestWheelchairPowered;
	}

	public static int getEastToWestPushChair() {
		return eastToWestPushChair;
	}

	public static int getEastToWestSkateboard() {
		return eastToWestSkateboard;
	}

	public static int getEastToWestManualScooter() {
		return eastToWestManualScooter;
	}

	public static int getEastToSouthWestBus() {
		return eastToSouthWestBus;
	}

	public static int getEastToSouthWestTruck() {
		return eastToSouthWestTruck;
	}

	public static int getEastToSouthWestCar() {
		return eastToSouthWestCar;
	}

	public static int getEastToSouthWestMotorBike() {
		return eastToSouthWestMotorBike;
	}

	public static int getEastToSouthWestPedestrian() {
		return eastToSouthWestPedestrian;
	}

	public static int getEastToSouthWestCrutches1() {
		return eastToSouthWestCrutches1;
	}

	public static int getEastToSouthWestCrutches2() {
		return eastToSouthWestCrutches2;
	}

	public static int getEastToSouthWestCane() {
		return eastToSouthWestCane;
	}

	public static int getEastToSouthWestDog() {
		return eastToSouthWestDog;
	}

	public static int getEastToSouthWestMobilityScooter() {
		return eastToSouthWestMobilityScooter;
	}

	public static int getEastToSouthWestWheelchairAssisted() {
		return eastToSouthWestWheelchairAssisted;
	}

	public static int getEastToSouthWestWheelchairManual() {
		return eastToSouthWestWheelchairManual;
	}

	public static int getEastToSouthWestWheelchairPowered() {
		return eastToSouthWestWheelchairPowered;
	}

	public static int getEastToSouthWestPushChair() {
		return eastToSouthWestPushChair;
	}

	public static int getEastToSouthWestSkateboard() {
		return eastToSouthWestSkateboard;
	}

	public static int getEastToSouthWestManualScooter() {
		return eastToSouthWestManualScooter;
	}

	public static int getEastToSouthBus() {
		return eastToSouthBus;
	}

	public static int getEastToSouthTruck() {
		return eastToSouthTruck;
	}

	public static int getEastToSouthCar() {
		return eastToSouthCar;
	}

	public static int getEastToSouthMotorBike() {
		return eastToSouthMotorBike;
	}

	public static int getEastToSouthPedestrian() {
		return eastToSouthPedestrian;
	}

	public static int getEastToSouthCrutches1() {
		return eastToSouthCrutches1;
	}

	public static int getEastToSouthCrutches2() {
		return eastToSouthCrutches2;
	}

	public static int getEastToSouthCane() {
		return eastToSouthCane;
	}

	public static int getEastToSouthDog() {
		return eastToSouthDog;
	}

	public static int getEastToSouthMobilityScooter() {
		return eastToSouthMobilityScooter;
	}

	public static int getEastToSouthWheelchairAssisted() {
		return eastToSouthWheelchairAssisted;
	}

	public static int getEastToSouthWheelchairManual() {
		return eastToSouthWheelchairManual;
	}

	public static int getEastToSouthWheelchairPowered() {
		return eastToSouthWheelchairPowered;
	}

	public static int getEastToSouthPushChair() {
		return eastToSouthPushChair;
	}

	public static int getEastToSouthSkateboard() {
		return eastToSouthSkateboard;
	}

	public static int getEastToSouthManualScooter() {
		return eastToSouthManualScooter;
	}

	public static int getEastToSouthEastBus() {
		return eastToSouthEastBus;
	}

	public static int getEastToSouthEastTruck() {
		return eastToSouthEastTruck;
	}

	public static int getEastToSouthEastCar() {
		return eastToSouthEastCar;
	}

	public static int getEastToSouthEastMotorBike() {
		return eastToSouthEastMotorBike;
	}

	public static int getEastToSouthEastPedestrian() {
		return eastToSouthEastPedestrian;
	}

	public static int getEastToSouthEastCrutches1() {
		return eastToSouthEastCrutches1;
	}

	public static int getEastToSouthEastCrutches2() {
		return eastToSouthEastCrutches2;
	}

	public static int getEastToSouthEastCane() {
		return eastToSouthEastCane;
	}

	public static int getEastToSouthEastDog() {
		return eastToSouthEastDog;
	}

	public static int getEastToSouthEastMobilityScooter() {
		return eastToSouthEastMobilityScooter;
	}

	public static int getEastToSouthEastWheelchairAssisted() {
		return eastToSouthEastWheelchairAssisted;
	}

	public static int getEastToSouthEastWheelchairManual() {
		return eastToSouthEastWheelchairManual;
	}

	public static int getEastToSouthEastWheelchairPowered() {
		return eastToSouthEastWheelchairPowered;
	}

	public static int getEastToSouthEastPushChair() {
		return eastToSouthEastPushChair;
	}

	public static int getEastToSouthEastSkateboard() {
		return eastToSouthEastSkateboard;
	}

	public static int getEastToSouthEastManualScooter() {
		return eastToSouthEastManualScooter;
	}

	public static int getSouthWestToNorthWestBus() {
		return southWestToNorthWestBus;
	}

	public static int getSouthWestToNorthWestTruck() {
		return southWestToNorthWestTruck;
	}

	public static int getSouthWestToNorthWestCar() {
		return southWestToNorthWestCar;
	}

	public static int getSouthWestToNorthWestMotorBike() {
		return southWestToNorthWestMotorBike;
	}

	public static int getSouthWestToNorthWestPedestrian() {
		return southWestToNorthWestPedestrian;
	}

	public static int getSouthWestToNorthWestCrutches1() {
		return southWestToNorthWestCrutches1;
	}

	public static int getSouthWestToNorthWestCrutches2() {
		return southWestToNorthWestCrutches2;
	}

	public static int getSouthWestToNorthWestCane() {
		return southWestToNorthWestCane;
	}

	public static int getSouthWestToNorthWestDog() {
		return southWestToNorthWestDog;
	}

	public static int getSouthWestToNorthWestMobilityScooter() {
		return southWestToNorthWestMobilityScooter;
	}

	public static int getSouthWestToNorthWestWheelchairAssisted() {
		return southWestToNorthWestWheelchairAssisted;
	}

	public static int getSouthWestToNorthWestWheelchairManual() {
		return southWestToNorthWestWheelchairManual;
	}

	public static int getSouthWestToNorthWestWheelchairPowered() {
		return southWestToNorthWestWheelchairPowered;
	}

	public static int getSouthWestToNorthWestPushChair() {
		return southWestToNorthWestPushChair;
	}

	public static int getSouthWestToNorthWestSkateboard() {
		return southWestToNorthWestSkateboard;
	}

	public static int getSouthWestToNorthWestManualScooter() {
		return southWestToNorthWestManualScooter;
	}

	public static int getSouthWestToNorthBus() {
		return southWestToNorthBus;
	}

	public static int getSouthWestToNorthTruck() {
		return southWestToNorthTruck;
	}

	public static int getSouthWestToNorthCar() {
		return southWestToNorthCar;
	}

	public static int getSouthWestToNorthMotorBike() {
		return southWestToNorthMotorBike;
	}

	public static int getSouthWestToNorthPedestrian() {
		return southWestToNorthPedestrian;
	}

	public static int getSouthWestToNorthCrutches1() {
		return southWestToNorthCrutches1;
	}

	public static int getSouthWestToNorthCrutches2() {
		return southWestToNorthCrutches2;
	}

	public static int getSouthWestToNorthCane() {
		return southWestToNorthCane;
	}

	public static int getSouthWestToNorthDog() {
		return southWestToNorthDog;
	}

	public static int getSouthWestToNorthMobilityScooter() {
		return southWestToNorthMobilityScooter;
	}

	public static int getSouthWestToNorthWheelchairAssisted() {
		return southWestToNorthWheelchairAssisted;
	}

	public static int getSouthWestToNorthWheelchairManual() {
		return southWestToNorthWheelchairManual;
	}

	public static int getSouthWestToNorthWheelchairPowered() {
		return southWestToNorthWheelchairPowered;
	}

	public static int getSouthWestToNorthPushChair() {
		return southWestToNorthPushChair;
	}

	public static int getSouthWestToNorthSkateboard() {
		return southWestToNorthSkateboard;
	}

	public static int getSouthWestToNorthManualScooter() {
		return southWestToNorthManualScooter;
	}

	public static int getSouthWestToNorthEastBus() {
		return southWestToNorthEastBus;
	}

	public static int getSouthWestToNorthEastTruck() {
		return southWestToNorthEastTruck;
	}

	public static int getSouthWestToNorthEastCar() {
		return southWestToNorthEastCar;
	}

	public static int getSouthWestToNorthEastMotorBike() {
		return southWestToNorthEastMotorBike;
	}

	public static int getSouthWestToNorthEastPedestrian() {
		return southWestToNorthEastPedestrian;
	}

	public static int getSouthWestToNorthEastCrutches1() {
		return southWestToNorthEastCrutches1;
	}

	public static int getSouthWestToNorthEastCrutches2() {
		return southWestToNorthEastCrutches2;
	}

	public static int getSouthWestToNorthEastCane() {
		return southWestToNorthEastCane;
	}

	public static int getSouthWestToNorthEastDog() {
		return southWestToNorthEastDog;
	}

	public static int getSouthWestToNorthEastMobilityScooter() {
		return southWestToNorthEastMobilityScooter;
	}

	public static int getSouthWestToNorthEastWheelchairAssisted() {
		return southWestToNorthEastWheelchairAssisted;
	}

	public static int getSouthWestToNorthEastWheelchairManual() {
		return southWestToNorthEastWheelchairManual;
	}

	public static int getSouthWestToNorthEastWheelchairPowered() {
		return southWestToNorthEastWheelchairPowered;
	}

	public static int getSouthWestToNorthEastPushChair() {
		return southWestToNorthEastPushChair;
	}

	public static int getSouthWestToNorthEastSkateboard() {
		return southWestToNorthEastSkateboard;
	}

	public static int getSouthWestToNorthEastManualScooter() {
		return southWestToNorthEastManualScooter;
	}

	public static int getSouthWestToWestBus() {
		return southWestToWestBus;
	}

	public static int getSouthWestToWestTruck() {
		return southWestToWestTruck;
	}

	public static int getSouthWestToWestCar() {
		return southWestToWestCar;
	}

	public static int getSouthWestToWestMotorBike() {
		return southWestToWestMotorBike;
	}

	public static int getSouthWestToWestPedestrian() {
		return southWestToWestPedestrian;
	}

	public static int getSouthWestToWestCrutches1() {
		return southWestToWestCrutches1;
	}

	public static int getSouthWestToWestCrutches2() {
		return southWestToWestCrutches2;
	}

	public static int getSouthWestToWestCane() {
		return southWestToWestCane;
	}

	public static int getSouthWestToWestDog() {
		return southWestToWestDog;
	}

	public static int getSouthWestToWestMobilityScooter() {
		return southWestToWestMobilityScooter;
	}

	public static int getSouthWestToWestWheelchairAssisted() {
		return southWestToWestWheelchairAssisted;
	}

	public static int getSouthWestToWestWheelchairManual() {
		return southWestToWestWheelchairManual;
	}

	public static int getSouthWestToWestWheelchairPowered() {
		return southWestToWestWheelchairPowered;
	}

	public static int getSouthWestToWestPushChair() {
		return southWestToWestPushChair;
	}

	public static int getSouthWestToWestSkateboard() {
		return southWestToWestSkateboard;
	}

	public static int getSouthWestToWestManualScooter() {
		return southWestToWestManualScooter;
	}

	public static int getSouthWestToEastBus() {
		return southWestToEastBus;
	}

	public static int getSouthWestToEastTruck() {
		return southWestToEastTruck;
	}

	public static int getSouthWestToEastCar() {
		return southWestToEastCar;
	}

	public static int getSouthWestToEastMotorBike() {
		return southWestToEastMotorBike;
	}

	public static int getSouthWestToEastPedestrian() {
		return southWestToEastPedestrian;
	}

	public static int getSouthWestToEastCrutches1() {
		return southWestToEastCrutches1;
	}

	public static int getSouthWestToEastCrutches2() {
		return southWestToEastCrutches2;
	}

	public static int getSouthWestToEastCane() {
		return southWestToEastCane;
	}

	public static int getSouthWestToEastDog() {
		return southWestToEastDog;
	}

	public static int getSouthWestToEastMobilityScooter() {
		return southWestToEastMobilityScooter;
	}

	public static int getSouthWestToEastWheelchairAssisted() {
		return southWestToEastWheelchairAssisted;
	}

	public static int getSouthWestToEastWheelchairManual() {
		return southWestToEastWheelchairManual;
	}

	public static int getSouthWestToEastWheelchairPowered() {
		return southWestToEastWheelchairPowered;
	}

	public static int getSouthWestToEastPushChair() {
		return southWestToEastPushChair;
	}

	public static int getSouthWestToEastSkateboard() {
		return southWestToEastSkateboard;
	}

	public static int getSouthWestToEastManualScooter() {
		return southWestToEastManualScooter;
	}

	public static int getSouthWestToSouthBus() {
		return southWestToSouthBus;
	}

	public static int getSouthWestToSouthTruck() {
		return southWestToSouthTruck;
	}

	public static int getSouthWestToSouthCar() {
		return southWestToSouthCar;
	}

	public static int getSouthWestToSouthMotorBike() {
		return southWestToSouthMotorBike;
	}

	public static int getSouthWestToSouthPedestrian() {
		return southWestToSouthPedestrian;
	}

	public static int getSouthWestToSouthCrutches1() {
		return southWestToSouthCrutches1;
	}

	public static int getSouthWestToSouthCrutches2() {
		return southWestToSouthCrutches2;
	}

	public static int getSouthWestToSouthCane() {
		return southWestToSouthCane;
	}

	public static int getSouthWestToSouthDog() {
		return southWestToSouthDog;
	}

	public static int getSouthWestToSouthMobilityScooter() {
		return southWestToSouthMobilityScooter;
	}

	public static int getSouthWestToSouthWheelchairAssisted() {
		return southWestToSouthWheelchairAssisted;
	}

	public static int getSouthWestToSouthWheelchairManual() {
		return southWestToSouthWheelchairManual;
	}

	public static int getSouthWestToSouthWheelchairPowered() {
		return southWestToSouthWheelchairPowered;
	}

	public static int getSouthWestToSouthPushChair() {
		return southWestToSouthPushChair;
	}

	public static int getSouthWestToSouthSkateboard() {
		return southWestToSouthSkateboard;
	}

	public static int getSouthWestToSouthManualScooter() {
		return southWestToSouthManualScooter;
	}

	public static int getSouthWestToSouthEastBus() {
		return southWestToSouthEastBus;
	}

	public static int getSouthWestToSouthEastTruck() {
		return southWestToSouthEastTruck;
	}

	public static int getSouthWestToSouthEastCar() {
		return southWestToSouthEastCar;
	}

	public static int getSouthWestToSouthEastMotorBike() {
		return southWestToSouthEastMotorBike;
	}

	public static int getSouthWestToSouthEastPedestrian() {
		return southWestToSouthEastPedestrian;
	}

	public static int getSouthWestToSouthEastCrutches1() {
		return southWestToSouthEastCrutches1;
	}

	public static int getSouthWestToSouthEastCrutches2() {
		return southWestToSouthEastCrutches2;
	}

	public static int getSouthWestToSouthEastCane() {
		return southWestToSouthEastCane;
	}

	public static int getSouthWestToSouthEastDog() {
		return southWestToSouthEastDog;
	}

	public static int getSouthWestToSouthEastMobilityScooter() {
		return southWestToSouthEastMobilityScooter;
	}

	public static int getSouthWestToSouthEastWheelchairAssisted() {
		return southWestToSouthEastWheelchairAssisted;
	}

	public static int getSouthWestToSouthEastWheelchairManual() {
		return southWestToSouthEastWheelchairManual;
	}

	public static int getSouthWestToSouthEastWheelchairPowered() {
		return southWestToSouthEastWheelchairPowered;
	}

	public static int getSouthWestToSouthEastPushChair() {
		return southWestToSouthEastPushChair;
	}

	public static int getSouthWestToSouthEastSkateboard() {
		return southWestToSouthEastSkateboard;
	}

	public static int getSouthWestToSouthEastManualScooter() {
		return southWestToSouthEastManualScooter;
	}

	public static int getSouthToNorthWestBus() {
		return southToNorthWestBus;
	}

	public static int getSouthToNorthWestTruck() {
		return southToNorthWestTruck;
	}

	public static int getSouthToNorthWestCar() {
		return southToNorthWestCar;
	}

	public static int getSouthToNorthWestMotorBike() {
		return southToNorthWestMotorBike;
	}

	public static int getSouthToNorthWestPedestrian() {
		return southToNorthWestPedestrian;
	}

	public static int getSouthToNorthWestCrutches1() {
		return southToNorthWestCrutches1;
	}

	public static int getSouthToNorthWestCrutches2() {
		return southToNorthWestCrutches2;
	}

	public static int getSouthToNorthWestCane() {
		return southToNorthWestCane;
	}

	public static int getSouthToNorthWestDog() {
		return southToNorthWestDog;
	}

	public static int getSouthToNorthWestMobilityScooter() {
		return southToNorthWestMobilityScooter;
	}

	public static int getSouthToNorthWestWheelchairAssisted() {
		return southToNorthWestWheelchairAssisted;
	}

	public static int getSouthToNorthWestWheelchairManual() {
		return southToNorthWestWheelchairManual;
	}

	public static int getSouthToNorthWestWheelchairPowered() {
		return southToNorthWestWheelchairPowered;
	}

	public static int getSouthToNorthWestPushChair() {
		return southToNorthWestPushChair;
	}

	public static int getSouthToNorthWestSkateboard() {
		return southToNorthWestSkateboard;
	}

	public static int getSouthToNorthWestManualScooter() {
		return southToNorthWestManualScooter;
	}

	public static int getSouthToNorthBus() {
		return southToNorthBus;
	}

	public static int getSouthToNorthTruck() {
		return southToNorthTruck;
	}

	public static int getSouthToNorthCar() {
		return southToNorthCar;
	}

	public static int getSouthToNorthMotorBike() {
		return southToNorthMotorBike;
	}

	public static int getSouthToNorthPedestrian() {
		return southToNorthPedestrian;
	}

	public static int getSouthToNorthCrutches1() {
		return southToNorthCrutches1;
	}

	public static int getSouthToNorthCrutches2() {
		return southToNorthCrutches2;
	}

	public static int getSouthToNorthCane() {
		return southToNorthCane;
	}

	public static int getSouthToNorthDog() {
		return southToNorthDog;
	}

	public static int getSouthToNorthMobilityScooter() {
		return southToNorthMobilityScooter;
	}

	public static int getSouthToNorthWheelchairAssisted() {
		return southToNorthWheelchairAssisted;
	}

	public static int getSouthToNorthWheelchairManual() {
		return southToNorthWheelchairManual;
	}

	public static int getSouthToNorthWheelchairPowered() {
		return southToNorthWheelchairPowered;
	}

	public static int getSouthToNorthPushChair() {
		return southToNorthPushChair;
	}

	public static int getSouthToNorthSkateboard() {
		return southToNorthSkateboard;
	}

	public static int getSouthToNorthManualScooter() {
		return southToNorthManualScooter;
	}

	public static int getSouthToNorthEastBus() {
		return southToNorthEastBus;
	}

	public static int getSouthToNorthEastTruck() {
		return southToNorthEastTruck;
	}

	public static int getSouthToNorthEastCar() {
		return southToNorthEastCar;
	}

	public static int getSouthToNorthEastMotorBike() {
		return southToNorthEastMotorBike;
	}

	public static int getSouthToNorthEastPedestrian() {
		return southToNorthEastPedestrian;
	}

	public static int getSouthToNorthEastCrutches1() {
		return southToNorthEastCrutches1;
	}

	public static int getSouthToNorthEastCrutches2() {
		return southToNorthEastCrutches2;
	}

	public static int getSouthToNorthEastCane() {
		return southToNorthEastCane;
	}

	public static int getSouthToNorthEastDog() {
		return southToNorthEastDog;
	}

	public static int getSouthToNorthEastMobilityScooter() {
		return southToNorthEastMobilityScooter;
	}

	public static int getSouthToNorthEastWheelchairAssisted() {
		return southToNorthEastWheelchairAssisted;
	}

	public static int getSouthToNorthEastWheelchairManual() {
		return southToNorthEastWheelchairManual;
	}

	public static int getSouthToNorthEastWheelchairPowered() {
		return southToNorthEastWheelchairPowered;
	}

	public static int getSouthToNorthEastPushChair() {
		return southToNorthEastPushChair;
	}

	public static int getSouthToNorthEastSkateboard() {
		return southToNorthEastSkateboard;
	}

	public static int getSouthToNorthEastManualScooter() {
		return southToNorthEastManualScooter;
	}

	public static int getSouthToWestBus() {
		return southToWestBus;
	}

	public static int getSouthToWestTruck() {
		return southToWestTruck;
	}

	public static int getSouthToWestCar() {
		return southToWestCar;
	}

	public static int getSouthToWestMotorBike() {
		return southToWestMotorBike;
	}

	public static int getSouthToWestPedestrian() {
		return southToWestPedestrian;
	}

	public static int getSouthToWestCrutches1() {
		return southToWestCrutches1;
	}

	public static int getSouthToWestCrutches2() {
		return southToWestCrutches2;
	}

	public static int getSouthToWestCane() {
		return southToWestCane;
	}

	public static int getSouthToWestDog() {
		return southToWestDog;
	}

	public static int getSouthToWestMobilityScooter() {
		return southToWestMobilityScooter;
	}

	public static int getSouthToWestWheelchairAssisted() {
		return southToWestWheelchairAssisted;
	}

	public static int getSouthToWestWheelchairManual() {
		return southToWestWheelchairManual;
	}

	public static int getSouthToWestWheelchairPowered() {
		return southToWestWheelchairPowered;
	}

	public static int getSouthToWestPushChair() {
		return southToWestPushChair;
	}

	public static int getSouthToWestSkateboard() {
		return southToWestSkateboard;
	}

	public static int getSouthToWestManualScooter() {
		return southToWestManualScooter;
	}

	public static int getSouthToEastBus() {
		return southToEastBus;
	}

	public static int getSouthToEastTruck() {
		return southToEastTruck;
	}

	public static int getSouthToEastCar() {
		return southToEastCar;
	}

	public static int getSouthToEastMotorBike() {
		return southToEastMotorBike;
	}

	public static int getSouthToEastPedestrian() {
		return southToEastPedestrian;
	}

	public static int getSouthToEastCrutches1() {
		return southToEastCrutches1;
	}

	public static int getSouthToEastCrutches2() {
		return southToEastCrutches2;
	}

	public static int getSouthToEastCane() {
		return southToEastCane;
	}

	public static int getSouthToEastDog() {
		return southToEastDog;
	}

	public static int getSouthToEastMobilityScooter() {
		return southToEastMobilityScooter;
	}

	public static int getSouthToEastWheelchairAssisted() {
		return southToEastWheelchairAssisted;
	}

	public static int getSouthToEastWheelchairManual() {
		return southToEastWheelchairManual;
	}

	public static int getSouthToEastWheelchairPowered() {
		return southToEastWheelchairPowered;
	}

	public static int getSouthToEastPushChair() {
		return southToEastPushChair;
	}

	public static int getSouthToEastSkateboard() {
		return southToEastSkateboard;
	}

	public static int getSouthToEastManualScooter() {
		return southToEastManualScooter;
	}

	public static int getSouthToSouthWestBus() {
		return southToSouthWestBus;
	}

	public static int getSouthToSouthWestTruck() {
		return southToSouthWestTruck;
	}

	public static int getSouthToSouthWestCar() {
		return southToSouthWestCar;
	}

	public static int getSouthToSouthWestMotorBike() {
		return southToSouthWestMotorBike;
	}

	public static int getSouthToSouthWestPedestrian() {
		return southToSouthWestPedestrian;
	}

	public static int getSouthToSouthWestCrutches1() {
		return southToSouthWestCrutches1;
	}

	public static int getSouthToSouthWestCrutches2() {
		return southToSouthWestCrutches2;
	}

	public static int getSouthToSouthWestCane() {
		return southToSouthWestCane;
	}

	public static int getSouthToSouthWestDog() {
		return southToSouthWestDog;
	}

	public static int getSouthToSouthWestMobilityScooter() {
		return southToSouthWestMobilityScooter;
	}

	public static int getSouthToSouthWestWheelchairAssisted() {
		return southToSouthWestWheelchairAssisted;
	}

	public static int getSouthToSouthWestWheelchairManual() {
		return southToSouthWestWheelchairManual;
	}

	public static int getSouthToSouthWestWheelchairPowered() {
		return southToSouthWestWheelchairPowered;
	}

	public static int getSouthToSouthWestPushChair() {
		return southToSouthWestPushChair;
	}

	public static int getSouthToSouthWestSkateboard() {
		return southToSouthWestSkateboard;
	}

	public static int getSouthToSouthWestManualScooter() {
		return southToSouthWestManualScooter;
	}

	public static int getSouthToSouthEastBus() {
		return southToSouthEastBus;
	}

	public static int getSouthToSouthEastTruck() {
		return southToSouthEastTruck;
	}

	public static int getSouthToSouthEastCar() {
		return southToSouthEastCar;
	}

	public static int getSouthToSouthEastMotorBike() {
		return southToSouthEastMotorBike;
	}

	public static int getSouthToSouthEastPedestrian() {
		return southToSouthEastPedestrian;
	}

	public static int getSouthToSouthEastCrutches1() {
		return southToSouthEastCrutches1;
	}

	public static int getSouthToSouthEastCrutches2() {
		return southToSouthEastCrutches2;
	}

	public static int getSouthToSouthEastCane() {
		return southToSouthEastCane;
	}

	public static int getSouthToSouthEastDog() {
		return southToSouthEastDog;
	}

	public static int getSouthToSouthEastMobilityScooter() {
		return southToSouthEastMobilityScooter;
	}

	public static int getSouthToSouthEastWheelchairAssisted() {
		return southToSouthEastWheelchairAssisted;
	}

	public static int getSouthToSouthEastWheelchairManual() {
		return southToSouthEastWheelchairManual;
	}

	public static int getSouthToSouthEastWheelchairPowered() {
		return southToSouthEastWheelchairPowered;
	}

	public static int getSouthToSouthEastPushChair() {
		return southToSouthEastPushChair;
	}

	public static int getSouthToSouthEastSkateboard() {
		return southToSouthEastSkateboard;
	}

	public static int getSouthToSouthEastManualScooter() {
		return southToSouthEastManualScooter;
	}

	public static int getSouthEastToNorthWestBus() {
		return southEastToNorthWestBus;
	}

	public static int getSouthEastToNorthWestTruck() {
		return southEastToNorthWestTruck;
	}

	public static int getSouthEastToNorthWestCar() {
		return southEastToNorthWestCar;
	}

	public static int getSouthEastToNorthWestMotorBike() {
		return southEastToNorthWestMotorBike;
	}

	public static int getSouthEastToNorthWestPedestrian() {
		return southEastToNorthWestPedestrian;
	}

	public static int getSouthEastToNorthWestCrutches1() {
		return southEastToNorthWestCrutches1;
	}

	public static int getSouthEastToNorthWestCrutches2() {
		return southEastToNorthWestCrutches2;
	}

	public static int getSouthEastToNorthWestCane() {
		return southEastToNorthWestCane;
	}

	public static int getSouthEastToNorthWestDog() {
		return southEastToNorthWestDog;
	}

	public static int getSouthEastToNorthWestMobilityScooter() {
		return southEastToNorthWestMobilityScooter;
	}

	public static int getSouthEastToNorthWestWheelchairAssisted() {
		return southEastToNorthWestWheelchairAssisted;
	}

	public static int getSouthEastToNorthWestWheelchairManual() {
		return southEastToNorthWestWheelchairManual;
	}

	public static int getSouthEastToNorthWestWheelchairPowered() {
		return southEastToNorthWestWheelchairPowered;
	}

	public static int getSouthEastToNorthWestPushChair() {
		return southEastToNorthWestPushChair;
	}

	public static int getSouthEastToNorthWestSkateboard() {
		return southEastToNorthWestSkateboard;
	}

	public static int getSouthEastToNorthWestManualScooter() {
		return southEastToNorthWestManualScooter;
	}

	public static int getSouthEastToNorthBus() {
		return southEastToNorthBus;
	}

	public static int getSouthEastToNorthTruck() {
		return southEastToNorthTruck;
	}

	public static int getSouthEastToNorthCar() {
		return southEastToNorthCar;
	}

	public static int getSouthEastToNorthMotorBike() {
		return southEastToNorthMotorBike;
	}

	public static int getSouthEastToNorthPedestrian() {
		return southEastToNorthPedestrian;
	}

	public static int getSouthEastToNorthCrutches1() {
		return southEastToNorthCrutches1;
	}

	public static int getSouthEastToNorthCrutches2() {
		return southEastToNorthCrutches2;
	}

	public static int getSouthEastToNorthCane() {
		return southEastToNorthCane;
	}

	public static int getSouthEastToNorthDog() {
		return southEastToNorthDog;
	}

	public static int getSouthEastToNorthMobilityScooter() {
		return southEastToNorthMobilityScooter;
	}

	public static int getSouthEastToNorthWheelchairAssisted() {
		return southEastToNorthWheelchairAssisted;
	}

	public static int getSouthEastToNorthWheelchairManual() {
		return southEastToNorthWheelchairManual;
	}

	public static int getSouthEastToNorthWheelchairPowered() {
		return southEastToNorthWheelchairPowered;
	}

	public static int getSouthEastToNorthPushChair() {
		return southEastToNorthPushChair;
	}

	public static int getSouthEastToNorthSkateboard() {
		return southEastToNorthSkateboard;
	}

	public static int getSouthEastToNorthManualScooter() {
		return southEastToNorthManualScooter;
	}

	public static int getSouthEastToNorthEastBus() {
		return southEastToNorthEastBus;
	}

	public static int getSouthEastToNorthEastTruck() {
		return southEastToNorthEastTruck;
	}

	public static int getSouthEastToNorthEastCar() {
		return southEastToNorthEastCar;
	}

	public static int getSouthEastToNorthEastMotorBike() {
		return southEastToNorthEastMotorBike;
	}

	public static int getSouthEastToNorthEastPedestrian() {
		return southEastToNorthEastPedestrian;
	}

	public static int getSouthEastToNorthEastCrutches1() {
		return southEastToNorthEastCrutches1;
	}

	public static int getSouthEastToNorthEastCrutches2() {
		return southEastToNorthEastCrutches2;
	}

	public static int getSouthEastToNorthEastCane() {
		return southEastToNorthEastCane;
	}

	public static int getSouthEastToNorthEastDog() {
		return southEastToNorthEastDog;
	}

	public static int getSouthEastToNorthEastMobilityScooter() {
		return southEastToNorthEastMobilityScooter;
	}

	public static int getSouthEastToNorthEastWheelchairAssisted() {
		return southEastToNorthEastWheelchairAssisted;
	}

	public static int getSouthEastToNorthEastWheelchairManual() {
		return southEastToNorthEastWheelchairManual;
	}

	public static int getSouthEastToNorthEastWheelchairPowered() {
		return southEastToNorthEastWheelchairPowered;
	}

	public static int getSouthEastToNorthEastPushChair() {
		return southEastToNorthEastPushChair;
	}

	public static int getSouthEastToNorthEastSkateboard() {
		return southEastToNorthEastSkateboard;
	}

	public static int getSouthEastToNorthEastManualScooter() {
		return southEastToNorthEastManualScooter;
	}

	public static int getSouthEastToWestBus() {
		return southEastToWestBus;
	}

	public static int getSouthEastToWestTruck() {
		return southEastToWestTruck;
	}

	public static int getSouthEastToWestCar() {
		return southEastToWestCar;
	}

	public static int getSouthEastToWestMotorBike() {
		return southEastToWestMotorBike;
	}

	public static int getSouthEastToWestPedestrian() {
		return southEastToWestPedestrian;
	}

	public static int getSouthEastToWestCrutches1() {
		return southEastToWestCrutches1;
	}

	public static int getSouthEastToWestCrutches2() {
		return southEastToWestCrutches2;
	}

	public static int getSouthEastToWestCane() {
		return southEastToWestCane;
	}

	public static int getSouthEastToWestDog() {
		return southEastToWestDog;
	}

	public static int getSouthEastToWestMobilityScooter() {
		return southEastToWestMobilityScooter;
	}

	public static int getSouthEastToWestWheelchairAssisted() {
		return southEastToWestWheelchairAssisted;
	}

	public static int getSouthEastToWestWheelchairManual() {
		return southEastToWestWheelchairManual;
	}

	public static int getSouthEastToWestWheelchairPowered() {
		return southEastToWestWheelchairPowered;
	}

	public static int getSouthEastToWestPushChair() {
		return southEastToWestPushChair;
	}

	public static int getSouthEastToWestSkateboard() {
		return southEastToWestSkateboard;
	}

	public static int getSouthEastToWestManualScooter() {
		return southEastToWestManualScooter;
	}

	public static int getSouthEastToEastBus() {
		return southEastToEastBus;
	}

	public static int getSouthEastToEastTruck() {
		return southEastToEastTruck;
	}

	public static int getSouthEastToEastCar() {
		return southEastToEastCar;
	}

	public static int getSouthEastToEastMotorBike() {
		return southEastToEastMotorBike;
	}

	public static int getSouthEastToEastPedestrian() {
		return southEastToEastPedestrian;
	}

	public static int getSouthEastToEastCrutches1() {
		return southEastToEastCrutches1;
	}

	public static int getSouthEastToEastCrutches2() {
		return southEastToEastCrutches2;
	}

	public static int getSouthEastToEastCane() {
		return southEastToEastCane;
	}

	public static int getSouthEastToEastDog() {
		return southEastToEastDog;
	}

	public static int getSouthEastToEastMobilityScooter() {
		return southEastToEastMobilityScooter;
	}

	public static int getSouthEastToEastWheelchairAssisted() {
		return southEastToEastWheelchairAssisted;
	}

	public static int getSouthEastToEastWheelchairManual() {
		return southEastToEastWheelchairManual;
	}

	public static int getSouthEastToEastWheelchairPowered() {
		return southEastToEastWheelchairPowered;
	}

	public static int getSouthEastToEastPushChair() {
		return southEastToEastPushChair;
	}

	public static int getSouthEastToEastSkateboard() {
		return southEastToEastSkateboard;
	}

	public static int getSouthEastToEastManualScooter() {
		return southEastToEastManualScooter;
	}

	public static int getSouthEastToSouthWestBus() {
		return southEastToSouthWestBus;
	}

	public static int getSouthEastToSouthWestTruck() {
		return southEastToSouthWestTruck;
	}

	public static int getSouthEastToSouthWestCar() {
		return southEastToSouthWestCar;
	}

	public static int getSouthEastToSouthWestMotorBike() {
		return southEastToSouthWestMotorBike;
	}

	public static int getSouthEastToSouthWestPedestrian() {
		return southEastToSouthWestPedestrian;
	}

	public static int getSouthEastToSouthWestCrutches1() {
		return southEastToSouthWestCrutches1;
	}

	public static int getSouthEastToSouthWestCrutches2() {
		return southEastToSouthWestCrutches2;
	}

	public static int getSouthEastToSouthWestCane() {
		return southEastToSouthWestCane;
	}

	public static int getSouthEastToSouthWestDog() {
		return southEastToSouthWestDog;
	}

	public static int getSouthEastToSouthWestMobilityScooter() {
		return southEastToSouthWestMobilityScooter;
	}

	public static int getSouthEastToSouthWestWheelchairAssisted() {
		return southEastToSouthWestWheelchairAssisted;
	}

	public static int getSouthEastToSouthWestWheelchairManual() {
		return southEastToSouthWestWheelchairManual;
	}

	public static int getSouthEastToSouthWestWheelchairPowered() {
		return southEastToSouthWestWheelchairPowered;
	}

	public static int getSouthEastToSouthWestPushChair() {
		return southEastToSouthWestPushChair;
	}

	public static int getSouthEastToSouthWestSkateboard() {
		return southEastToSouthWestSkateboard;
	}

	public static int getSouthEastToSouthWestManualScooter() {
		return southEastToSouthWestManualScooter;
	}

	public static int getSouthEastToSouthBus() {
		return southEastToSouthBus;
	}

	public static int getSouthEastToSouthTruck() {
		return southEastToSouthTruck;
	}

	public static int getSouthEastToSouthCar() {
		return southEastToSouthCar;
	}

	public static int getSouthEastToSouthMotorBike() {
		return southEastToSouthMotorBike;
	}

	public static int getSouthEastToSouthPedestrian() {
		return southEastToSouthPedestrian;
	}

	public static int getSouthEastToSouthCrutches1() {
		return southEastToSouthCrutches1;
	}

	public static int getSouthEastToSouthCrutches2() {
		return southEastToSouthCrutches2;
	}

	public static int getSouthEastToSouthCane() {
		return southEastToSouthCane;
	}

	public static int getSouthEastToSouthDog() {
		return southEastToSouthDog;
	}

	public static int getSouthEastToSouthMobilityScooter() {
		return southEastToSouthMobilityScooter;
	}

	public static int getSouthEastToSouthWheelchairAssisted() {
		return southEastToSouthWheelchairAssisted;
	}

	public static int getSouthEastToSouthWheelchairManual() {
		return southEastToSouthWheelchairManual;
	}

	public static int getSouthEastToSouthWheelchairPowered() {
		return southEastToSouthWheelchairPowered;
	}

	public static int getSouthEastToSouthPushChair() {
		return southEastToSouthPushChair;
	}

	public static int getSouthEastToSouthSkateboard() {
		return southEastToSouthSkateboard;
	}

	public static int getSouthEastToSouthManualScooter() {
		return southEastToSouthManualScooter;
	}

	public static boolean isLocationHeaderAppended() {
		return locationHeaderAppended;
	}

	public static boolean isEndOfWritingCoutablesRow() {
		return endOfWritingCoutablesRow;
	}

	public static boolean isEndOfIntersectionCountsHeader() {
		return endOfIntersectionCountsHeader;
	}

	public static boolean isEndOfAppendingCountables() {
		return endOfAppendingCountables;
	}

	public static boolean isEndOfFromAndToPositionsHeader() {
		return endOfFromAndToPositionsHeader;
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
		case "Wheelchair (Assisted)":
			objectCount = wheelChair_assisted;
			break;
		case "Wheelchair (Manual)":
			objectCount = wheelChair_manual;
			break;
		case "Wheelchair (Powered)":
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
	 * @author: Richard Fong
	 * @since:
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
	 * @author: Richard Fong
	 * @since:
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
	 * @author: Richard Fong
	 * @since:
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
	 * Launches the CustomDialog class and sends the reference 
	 * "changeDefault" to that class to let that class know where it was
	 * called from. Then it displays the dialog which is handled in
	 * Custom_Dialogs.
	 * @author: Richard Fong
	 * @since: 04.06.15
	 */
	@SuppressLint("NewApi")
	public void showChangeDefaultTo(View view) {
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		Bundle args = new Bundle();
		args.putString("IntersectionType", intersectionType);
		dialog.setArguments(args);
		dialog.show(manager, "changeDefault");
	}
	
	/*
	 * Launches the CustomDialog class and sends the reference 
	 * "changeTimer" to that class to let that class know where it was
	 * called from. Then it displays the dialog which is handled in
	 * Custom_Dialogs.
	 * @author: Richard Fong
	 * @since: 05.06.15
	 */
	@SuppressLint("NewApi")
	public void showChangeTimer(View view) {
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "changeTimer");
	}

	/*
	 * This method is from CustomDialogs' communicator interface. The message
	 * returned is the message which is sent from Custom_Dialogs. This allows us
	 * to know which button was clicked so we can make appropriate action in the
	 * CountingScreen class.
	 * @author: Richard Fong
	 * @since:
	 */
	@Override
	public void sendClickMessage(String key, String stringValue, boolean[] booleanValue, String[] stringArrayValue) {
		if(key.equals("IntersectionNamePicker")){
			intersectionPickedNames = stringArrayValue;
			updateAllDirectionalButtons();
		}else if(key.equals("changeDefault")){
			defaultTo(stringValue);
			defaultObject = stringValue;
			Toast.makeText(this, "defaultObject: " + defaultObject, Toast.LENGTH_SHORT).show();
		}else if(key.equals("setTimer")){
			timerMinutes = Integer.parseInt(stringValue);
			populateTimer(timerMinutes);
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
		if(btn_undo.isPressed()){
			if(totalCount >= 1 && !lastSelectedObject.isEmpty()){
				initialiseDirectionFromTo();
				decreaseUndoButton();
				updateTotalCounter();
				updateAllCounts(false);
			}
		}else if(btn_start.isPressed()){
			countTimer.startTime = countTimer.df.format(Calendar.getInstance().getTime());
			countTimer.start();
			countTimer.countTimerStarted = true;
			countTimer.countTimerSaved = false;
			btn_setTime.setEnabled(false);
		}else if(btn_stop.isPressed()){
			countTimer.onFinish();
			btn_setTime.setEnabled(true);
		}else if(btn_direction_nw.isPressed()){
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
				updateAllCounts(true);
		}
	}
	
	private void decreaseUndoButton(){
		if(lastSelectedObject.peek().contains("Bus")){
			decreaseBus();
		}else if(lastSelectedObject.peek().contains("Car")){
			decreaseCar();
		}else if(lastSelectedObject.peek().contains("Truck")){
			decreaseTruck();
		}else if(lastSelectedObject.peek().contains("MotorBike")){
			decreaseMotorBike();
		}else if(lastSelectedObject.peek().contains("Pedestrian")){
			decreasePedestrian();
		}else if(lastSelectedObject.peek().contains("Crutches1")){
			decreaseCrutches1();
		}else if(lastSelectedObject.peek().contains("Crutches2")){
			decreaseCrutches2();
		}else if(lastSelectedObject.peek().contains("Cane")){
			decreaseCane();
		}else if(lastSelectedObject.peek().contains("Dog")){
			decreaseDog();
		}else if(lastSelectedObject.peek().contains("MobilityScooter")){
			decreaseMobilityScooter();
		}else if(lastSelectedObject.peek().contains("WheelchairAssisted")){
			decreaseWheelchairAssisted();
		}else if(lastSelectedObject.peek().contains("WheelchairManual")){
			decreaseWheelchairManual();
		}else if(lastSelectedObject.peek().contains("WheelchairPowered")){
			decreaseWheelchairPowered();
		}else if(lastSelectedObject.peek().contains("PushChair")){
			decreasePushChair();
		}else if(lastSelectedObject.peek().contains("Skateboard")){
			decreaseSkateboard();
		}else if(lastSelectedObject.peek().contains("ManualScooter")){
			decreaseManualScooter();
		}
		
		
		//Removes the top object off the stack
		lastSelectedObject.pop();
		totalCount--;
	}
	
	private void decreaseBus(){
		if(lastSelectedObject.peek().equals("northWestToNorthBus")){
			northWestToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastBus")){
			northWestToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToWestBus")){
			northWestToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToEastBus")){
			northWestToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestBus")){
			northWestToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToSouthBus")){
			northWestToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastBus")){
			northWestToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestBus")){
			northToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("northToNorthEastBus")){
			northToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("northToWestBus")){
			northToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("northToEastBus")){
			northToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("northToSouthWestBus")){
			northToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("northToSouthBus")){
			northToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("northToSouthEastBus")){
			northToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestBus")){
			northEastToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToNorthBus")){
			northEastToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToWestBus")){
			northEastToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToEastBus")){
			northEastToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestBus")){
			northEastToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToSouthBus")){
			northEastToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastBus")){
			northEastToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestBus")){
			westToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("westToNorthBus")){
			westToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("westToNorthEastBus")){
			westToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("westToEastBus")){
			westToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("westToSouthWestBus")){
			westToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("westToSouthBus")){
			westToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("westToSouthEastBus")){
			westToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestBus")){
			eastToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("eastToNorthBus")){
			eastToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("eastToNorthEastBus")){
			eastToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("eastToWestBus")){
			eastToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("eastToSouthWestBus")){
			eastToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("eastToSouthBus")){
			eastToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("eastToSouthEastBus")){
			eastToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestBus")){
			southWestToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToNorthBus")){
			southWestToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastBus")){
			southWestToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToWestBus")){
			southWestToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToEastBus")){
			southWestToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToSouthBus")){
			southWestToSouthBus--;
			southTotal--;
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastBus")){
			southWestToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestBus")){
			southToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("southToNorthBus")){
			southToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("southToNorthEastBus")){
			southToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("southToWestBus")){
			southToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("southToEastBus")){
			southToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("southToSouthWestBus")){
			southToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("southToSouthEastBus")){
			southToSouthEastBus--;
			southEastTotal--;
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestBus")){
			southEastToNorthWestBus--;
			northWestTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToNorthBus")){
			southEastToNorthBus--;
			northTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastBus")){
			southEastToNorthEastBus--;
			northEastTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToWestBus")){
			southEastToWestBus--;
			westTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToEastBus")){
			southEastToEastBus--;
			eastTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestBus")){
			southEastToSouthWestBus--;
			southWestTotal--;
		}else if(lastSelectedObject.peek().equals("southEastToSouthBus")){
			southEastToSouthBus--;
			southTotal--;
			///////////////////////////////////////////////////////////////
		}
		bus--;
	}
	
	private void decreaseCar(){
		if(lastSelectedObject.peek().equals("northWestToNorthCar")){
			northWestToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastCar")){
			northWestToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestCar")){
			northWestToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastCar")){
			northWestToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestCar")){
			northWestToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthCar")){
			northWestToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastCar")){
			northWestToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestCar")){
			northToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastCar")){
			northToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestCar")){
			northToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastCar")){
			northToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestCar")){
			northToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthCar")){
			northToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastCar")){
			northToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestCar")){
			northEastToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthCar")){
			northEastToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestCar")){
			northEastToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastCar")){
			northEastToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestCar")){
			northEastToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthCar")){
			northEastToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastCar")){
			northEastToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestCar")){
			westToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthCar")){
			westToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastCar")){
			westToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastCar")){
			westToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestCar")){
			westToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthCar")){
			westToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastCar")){
			westToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestCar")){
			eastToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthCar")){
			eastToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastCar")){
			eastToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestCar")){
			eastToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestCar")){
			eastToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthCar")){
			eastToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastCar")){
			eastToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestCar")){
			southWestToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthCar")){
			southWestToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastCar")){
			southWestToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestCar")){
			southWestToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastCar")){
			southWestToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthCar")){
			southWestToSouthCar--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastCar")){
			southWestToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestCar")){
			southToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthCar")){
			southToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastCar")){
			southToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestCar")){
			southToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastCar")){
			southToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestCar")){
			southToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastCar")){
			southToSouthEastCar--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestCar")){
			southEastToNorthWestCar--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthCar")){
			southEastToNorthCar--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastCar")){
			southEastToNorthEastCar--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestCar")){
			southEastToWestCar--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastCar")){
			southEastToEastCar--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestCar")){
			southEastToSouthWestCar--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthCar")){
			southEastToSouthCar--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		car--;
	}
	
	private void decreaseTruck(){
		if(lastSelectedObject.peek().equals("northWestToNorthTruck")){
			northWestToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastTruck")){
			northWestToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestTruck")){
			northWestToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastTruck")){
			northWestToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestTruck")){
			northWestToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthTruck")){
			northWestToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastTruck")){
			northWestToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestTruck")){
			northToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastTruck")){
			northToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestTruck")){
			northToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastTruck")){
			northToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestTruck")){
			northToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthTruck")){
			northToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastTruck")){
			northToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestTruck")){
			northEastToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthTruck")){
			northEastToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestTruck")){
			northEastToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastTruck")){
			northEastToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestTruck")){
			northEastToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthTruck")){
			northEastToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastTruck")){
			northEastToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestTruck")){
			westToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthTruck")){
			westToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastTruck")){
			westToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastTruck")){
			westToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestTruck")){
			westToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthTruck")){
			westToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastTruck")){
			westToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestTruck")){
			eastToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthTruck")){
			eastToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastTruck")){
			eastToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestTruck")){
			eastToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestTruck")){
			eastToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthTruck")){
			eastToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastTruck")){
			eastToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestTruck")){
			southWestToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthTruck")){
			southWestToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastTruck")){
			southWestToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestTruck")){
			southWestToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastTruck")){
			southWestToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthTruck")){
			southWestToSouthTruck--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastTruck")){
			southWestToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestTruck")){
			southToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthTruck")){
			southToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastTruck")){
			southToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestTruck")){
			southToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastTruck")){
			southToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestTruck")){
			southToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastTruck")){
			southToSouthEastTruck--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestTruck")){
			southEastToNorthWestTruck--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthTruck")){
			southEastToNorthTruck--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastTruck")){
			southEastToNorthEastTruck--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestTruck")){
			southEastToWestTruck--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastTruck")){
			southEastToEastTruck--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestTruck")){
			southEastToSouthWestTruck--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthTruck")){
			southEastToSouthTruck--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		truck--;
	}
	
	private void decreaseMotorBike(){
		if(lastSelectedObject.peek().equals("northWestToNorthMotorBike")){
			northWestToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastMotorBike")){
			northWestToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestMotorBike")){
			northWestToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastMotorBike")){
			northWestToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestMotorBike")){
			northWestToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthMotorBike")){
			northWestToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastMotorBike")){
			northWestToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestMotorBike")){
			northToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastMotorBike")){
			northToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestMotorBike")){
			northToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastMotorBike")){
			northToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestMotorBike")){
			northToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthMotorBike")){
			northToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastMotorBike")){
			northToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestMotorBike")){
			northEastToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthMotorBike")){
			northEastToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestMotorBike")){
			northEastToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastMotorBike")){
			northEastToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestMotorBike")){
			northEastToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthMotorBike")){
			northEastToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastMotorBike")){
			northEastToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestMotorBike")){
			westToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthMotorBike")){
			westToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastMotorBike")){
			westToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastMotorBike")){
			westToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestMotorBike")){
			westToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthMotorBike")){
			westToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastMotorBike")){
			westToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestMotorBike")){
			eastToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthMotorBike")){
			eastToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastMotorBike")){
			eastToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestMotorBike")){
			eastToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestMotorBike")){
			eastToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthMotorBike")){
			eastToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastMotorBike")){
			eastToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestMotorBike")){
			southWestToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthMotorBike")){
			southWestToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastMotorBike")){
			southWestToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestMotorBike")){
			southWestToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastMotorBike")){
			southWestToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthMotorBike")){
			southWestToSouthMotorBike--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastMotorBike")){
			southWestToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestMotorBike")){
			southToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthMotorBike")){
			southToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastMotorBike")){
			southToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestMotorBike")){
			southToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastMotorBike")){
			southToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestMotorBike")){
			southToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastMotorBike")){
			southToSouthEastMotorBike--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestMotorBike")){
			southEastToNorthWestMotorBike--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthMotorBike")){
			southEastToNorthMotorBike--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastMotorBike")){
			southEastToNorthEastMotorBike--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestMotorBike")){
			southEastToWestMotorBike--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastMotorBike")){
			southEastToEastMotorBike--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestMotorBike")){
			southEastToSouthWestMotorBike--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthMotorBike")){
			southEastToSouthMotorBike--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		motorBike--;
	}
	
	private void decreasePedestrian(){
		if(lastSelectedObject.peek().equals("northWestToNorthPedestrian")){
			northWestToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastPedestrian")){
			northWestToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestPedestrian")){
			northWestToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastPedestrian")){
			northWestToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestPedestrian")){
			northWestToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthPedestrian")){
			northWestToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastPedestrian")){
			northWestToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestPedestrian")){
			northToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastPedestrian")){
			northToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestPedestrian")){
			northToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastPedestrian")){
			northToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestPedestrian")){
			northToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthPedestrian")){
			northToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastPedestrian")){
			northToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestPedestrian")){
			northEastToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthPedestrian")){
			northEastToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestPedestrian")){
			northEastToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastPedestrian")){
			northEastToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestPedestrian")){
			northEastToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthPedestrian")){
			northEastToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastPedestrian")){
			northEastToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestPedestrian")){
			westToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthPedestrian")){
			westToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastPedestrian")){
			westToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastPedestrian")){
			westToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestPedestrian")){
			westToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthPedestrian")){
			westToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastPedestrian")){
			westToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestPedestrian")){
			eastToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthPedestrian")){
			eastToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastPedestrian")){
			eastToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestPedestrian")){
			eastToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestPedestrian")){
			eastToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthPedestrian")){
			eastToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastPedestrian")){
			eastToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestPedestrian")){
			southWestToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthPedestrian")){
			southWestToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastPedestrian")){
			southWestToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestPedestrian")){
			southWestToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastPedestrian")){
			southWestToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthPedestrian")){
			southWestToSouthPedestrian--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastPedestrian")){
			southWestToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestPedestrian")){
			southToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthPedestrian")){
			southToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastPedestrian")){
			southToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestPedestrian")){
			southToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastPedestrian")){
			southToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestPedestrian")){
			southToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastPedestrian")){
			southToSouthEastPedestrian--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestPedestrian")){
			southEastToNorthWestPedestrian--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthPedestrian")){
			southEastToNorthPedestrian--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastPedestrian")){
			southEastToNorthEastPedestrian--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestPedestrian")){
			southEastToWestPedestrian--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastPedestrian")){
			southEastToEastPedestrian--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestPedestrian")){
			southEastToSouthWestPedestrian--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthPedestrian")){
			southEastToSouthPedestrian--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		pedestrian--;
	}
	
	private void decreaseCrutches1(){
		if(lastSelectedObject.peek().equals("northWestToNorthCrutches1")){
			northWestToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastCrutches1")){
			northWestToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestCrutches1")){
			northWestToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastCrutches1")){
			northWestToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestCrutches1")){
			northWestToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthCrutches1")){
			northWestToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastCrutches1")){
			northWestToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestCrutches1")){
			northToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastCrutches1")){
			northToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestCrutches1")){
			northToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastCrutches1")){
			northToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestCrutches1")){
			northToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthCrutches1")){
			northToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastCrutches1")){
			northToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestCrutches1")){
			northEastToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthCrutches1")){
			northEastToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestCrutches1")){
			northEastToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastCrutches1")){
			northEastToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestCrutches1")){
			northEastToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthCrutches1")){
			northEastToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastCrutches1")){
			northEastToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestCrutches1")){
			westToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthCrutches1")){
			westToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastCrutches1")){
			westToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastCrutches1")){
			westToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestCrutches1")){
			westToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthCrutches1")){
			westToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastCrutches1")){
			westToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestCrutches1")){
			eastToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthCrutches1")){
			eastToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastCrutches1")){
			eastToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestCrutches1")){
			eastToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestCrutches1")){
			eastToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthCrutches1")){
			eastToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastCrutches1")){
			eastToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestCrutches1")){
			southWestToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthCrutches1")){
			southWestToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastCrutches1")){
			southWestToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestCrutches1")){
			southWestToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastCrutches1")){
			southWestToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthCrutches1")){
			southWestToSouthCrutches1--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastCrutches1")){
			southWestToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestCrutches1")){
			southToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthCrutches1")){
			southToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastCrutches1")){
			southToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestCrutches1")){
			southToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastCrutches1")){
			southToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestCrutches1")){
			southToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastCrutches1")){
			southToSouthEastCrutches1--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestCrutches1")){
			southEastToNorthWestCrutches1--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthCrutches1")){
			southEastToNorthCrutches1--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastCrutches1")){
			southEastToNorthEastCrutches1--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestCrutches1")){
			southEastToWestCrutches1--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastCrutches1")){
			southEastToEastCrutches1--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestCrutches1")){
			southEastToSouthWestCrutches1--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthCrutches1")){
			southEastToSouthCrutches1--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		crutches_1--;
	}
	
	private void decreaseCrutches2(){
		if(lastSelectedObject.peek().equals("northWestToNorthCrutches2")){
			northWestToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastCrutches2")){
			northWestToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestCrutches2")){
			northWestToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastCrutches2")){
			northWestToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestCrutches2")){
			northWestToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthCrutches2")){
			northWestToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastCrutches2")){
			northWestToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestCrutches2")){
			northToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastCrutches2")){
			northToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestCrutches2")){
			northToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastCrutches2")){
			northToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestCrutches2")){
			northToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthCrutches2")){
			northToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastCrutches2")){
			northToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestCrutches2")){
			northEastToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthCrutches2")){
			northEastToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestCrutches2")){
			northEastToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastCrutches2")){
			northEastToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestCrutches2")){
			northEastToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthCrutches2")){
			northEastToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastCrutches2")){
			northEastToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestCrutches2")){
			westToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthCrutches2")){
			westToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastCrutches2")){
			westToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastCrutches2")){
			westToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestCrutches2")){
			westToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthCrutches2")){
			westToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastCrutches2")){
			westToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestCrutches2")){
			eastToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthCrutches2")){
			eastToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastCrutches2")){
			eastToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestCrutches2")){
			eastToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestCrutches2")){
			eastToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthCrutches2")){
			eastToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastCrutches2")){
			eastToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestCrutches2")){
			southWestToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthCrutches2")){
			southWestToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastCrutches2")){
			southWestToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestCrutches2")){
			southWestToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastCrutches2")){
			southWestToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthCrutches2")){
			southWestToSouthCrutches2--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastCrutches2")){
			southWestToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestCrutches2")){
			southToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthCrutches2")){
			southToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastCrutches2")){
			southToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestCrutches2")){
			southToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastCrutches2")){
			southToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestCrutches2")){
			southToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastCrutches2")){
			southToSouthEastCrutches2--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestCrutches2")){
			southEastToNorthWestCrutches2--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthCrutches2")){
			southEastToNorthCrutches2--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastCrutches2")){
			southEastToNorthEastCrutches2--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestCrutches2")){
			southEastToWestCrutches2--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastCrutches2")){
			southEastToEastCrutches2--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestCrutches2")){
			southEastToSouthWestCrutches2--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthCrutches2")){
			southEastToSouthCrutches2--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		crutches_2--;
	}
	
	private void decreaseCane(){
		if(lastSelectedObject.peek().equals("northWestToNorthCane")){
			northWestToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastCane")){
			northWestToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestCane")){
			northWestToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastCane")){
			northWestToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestCane")){
			northWestToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthCane")){
			northWestToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastCane")){
			northWestToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestCane")){
			northToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastCane")){
			northToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestCane")){
			northToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastCane")){
			northToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestCane")){
			northToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthCane")){
			northToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastCane")){
			northToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestCane")){
			northEastToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthCane")){
			northEastToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestCane")){
			northEastToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastCane")){
			northEastToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestCane")){
			northEastToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthCane")){
			northEastToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastCane")){
			northEastToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestCane")){
			westToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthCane")){
			westToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastCane")){
			westToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastCane")){
			westToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestCane")){
			westToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthCane")){
			westToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastCane")){
			westToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestCane")){
			eastToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthCane")){
			eastToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastCane")){
			eastToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestCane")){
			eastToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestCane")){
			eastToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthCane")){
			eastToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastCane")){
			eastToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestCane")){
			southWestToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthCane")){
			southWestToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastCane")){
			southWestToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestCane")){
			southWestToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastCane")){
			southWestToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthCane")){
			southWestToSouthCane--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastCane")){
			southWestToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestCane")){
			southToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthCane")){
			southToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastCane")){
			southToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestCane")){
			southToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastCane")){
			southToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestCane")){
			southToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastCane")){
			southToSouthEastCane--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestCane")){
			southEastToNorthWestCane--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthCane")){
			southEastToNorthCane--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastCane")){
			southEastToNorthEastCane--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestCane")){
			southEastToWestCane--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastCane")){
			southEastToEastCane--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestCane")){
			southEastToSouthWestCane--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthCane")){
			southEastToSouthCane--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		cane--;
	}
	
	private void decreaseDog(){
		if(lastSelectedObject.peek().equals("northWestToNorthDog")){
			northWestToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastDog")){
			northWestToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestDog")){
			northWestToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastDog")){
			northWestToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestDog")){
			northWestToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthDog")){
			northWestToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastDog")){
			northWestToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestDog")){
			northToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastDog")){
			northToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestDog")){
			northToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastDog")){
			northToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestDog")){
			northToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthDog")){
			northToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastDog")){
			northToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestDog")){
			northEastToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthDog")){
			northEastToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestDog")){
			northEastToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastDog")){
			northEastToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestDog")){
			northEastToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthDog")){
			northEastToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastDog")){
			northEastToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestDog")){
			westToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthDog")){
			westToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastDog")){
			westToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastDog")){
			westToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestDog")){
			westToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthDog")){
			westToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastDog")){
			westToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestDog")){
			eastToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthDog")){
			eastToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastDog")){
			eastToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestDog")){
			eastToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestDog")){
			eastToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthDog")){
			eastToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastDog")){
			eastToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestDog")){
			southWestToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthDog")){
			southWestToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastDog")){
			southWestToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestDog")){
			southWestToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastDog")){
			southWestToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthDog")){
			southWestToSouthDog--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastDog")){
			southWestToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestDog")){
			southToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthDog")){
			southToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastDog")){
			southToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestDog")){
			southToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastDog")){
			southToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestDog")){
			southToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastDog")){
			southToSouthEastDog--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestDog")){
			southEastToNorthWestDog--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthDog")){
			southEastToNorthDog--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastDog")){
			southEastToNorthEastDog--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestDog")){
			southEastToWestDog--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastDog")){
			southEastToEastDog--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestDog")){
			southEastToSouthWestDog--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthDog")){
			southEastToSouthDog--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		dog--;
	}
	
	private void decreaseMobilityScooter(){
		if(lastSelectedObject.peek().equals("northWestToNorthMobilityScooter")){
			northWestToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastMobilityScooter")){
			northWestToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestMobilityScooter")){
			northWestToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastMobilityScooter")){
			northWestToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestMobilityScooter")){
			northWestToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthMobilityScooter")){
			northWestToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastMobilityScooter")){
			northWestToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestMobilityScooter")){
			northToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastMobilityScooter")){
			northToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestMobilityScooter")){
			northToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastMobilityScooter")){
			northToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestMobilityScooter")){
			northToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthMobilityScooter")){
			northToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastMobilityScooter")){
			northToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestMobilityScooter")){
			northEastToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthMobilityScooter")){
			northEastToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestMobilityScooter")){
			northEastToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastMobilityScooter")){
			northEastToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestMobilityScooter")){
			northEastToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthMobilityScooter")){
			northEastToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastMobilityScooter")){
			northEastToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestMobilityScooter")){
			westToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthMobilityScooter")){
			westToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastMobilityScooter")){
			westToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastMobilityScooter")){
			westToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestMobilityScooter")){
			westToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthMobilityScooter")){
			westToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastMobilityScooter")){
			westToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestMobilityScooter")){
			eastToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthMobilityScooter")){
			eastToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastMobilityScooter")){
			eastToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestMobilityScooter")){
			eastToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestMobilityScooter")){
			eastToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthMobilityScooter")){
			eastToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastMobilityScooter")){
			eastToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestMobilityScooter")){
			southWestToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthMobilityScooter")){
			southWestToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastMobilityScooter")){
			southWestToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestMobilityScooter")){
			southWestToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastMobilityScooter")){
			southWestToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthMobilityScooter")){
			southWestToSouthMobilityScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastMobilityScooter")){
			southWestToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestMobilityScooter")){
			southToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthMobilityScooter")){
			southToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastMobilityScooter")){
			southToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestMobilityScooter")){
			southToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastMobilityScooter")){
			southToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestMobilityScooter")){
			southToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastMobilityScooter")){
			southToSouthEastMobilityScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestMobilityScooter")){
			southEastToNorthWestMobilityScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthMobilityScooter")){
			southEastToNorthMobilityScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastMobilityScooter")){
			southEastToNorthEastMobilityScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestMobilityScooter")){
			southEastToWestMobilityScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastMobilityScooter")){
			southEastToEastMobilityScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestMobilityScooter")){
			southEastToSouthWestMobilityScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthMobilityScooter")){
			southEastToSouthMobilityScooter--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		mobilityScooter--;
	}
	
	private void decreaseWheelchairAssisted(){
		if(lastSelectedObject.peek().equals("northWestToNorthWheelchairAssisted")){
			northWestToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastWheelchairAssisted")){
			northWestToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestWheelchairAssisted")){
			northWestToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastWheelchairAssisted")){
			northWestToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestWheelchairAssisted")){
			northWestToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWheelchairAssisted")){
			northWestToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastWheelchairAssisted")){
			northWestToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestWheelchairAssisted")){
			northToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastWheelchairAssisted")){
			northToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestWheelchairAssisted")){
			northToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastWheelchairAssisted")){
			northToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestWheelchairAssisted")){
			northToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWheelchairAssisted")){
			northToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastWheelchairAssisted")){
			northToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestWheelchairAssisted")){
			northEastToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthWheelchairAssisted")){
			northEastToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestWheelchairAssisted")){
			northEastToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastWheelchairAssisted")){
			northEastToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestWheelchairAssisted")){
			northEastToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWheelchairAssisted")){
			northEastToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastWheelchairAssisted")){
			northEastToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestWheelchairAssisted")){
			westToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthWheelchairAssisted")){
			westToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastWheelchairAssisted")){
			westToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastWheelchairAssisted")){
			westToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestWheelchairAssisted")){
			westToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWheelchairAssisted")){
			westToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastWheelchairAssisted")){
			westToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestWheelchairAssisted")){
			eastToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthWheelchairAssisted")){
			eastToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastWheelchairAssisted")){
			eastToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestWheelchairAssisted")){
			eastToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestWheelchairAssisted")){
			eastToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWheelchairAssisted")){
			eastToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastWheelchairAssisted")){
			eastToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestWheelchairAssisted")){
			southWestToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthWheelchairAssisted")){
			southWestToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastWheelchairAssisted")){
			southWestToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestWheelchairAssisted")){
			southWestToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastWheelchairAssisted")){
			southWestToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthWheelchairAssisted")){
			southWestToSouthWheelchairAssisted--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastWheelchairAssisted")){
			southWestToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestWheelchairAssisted")){
			southToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthWheelchairAssisted")){
			southToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastWheelchairAssisted")){
			southToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestWheelchairAssisted")){
			southToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastWheelchairAssisted")){
			southToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestWheelchairAssisted")){
			southToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastWheelchairAssisted")){
			southToSouthEastWheelchairAssisted--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestWheelchairAssisted")){
			southEastToNorthWestWheelchairAssisted--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthWheelchairAssisted")){
			southEastToNorthWheelchairAssisted--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastWheelchairAssisted")){
			southEastToNorthEastWheelchairAssisted--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestWheelchairAssisted")){
			southEastToWestWheelchairAssisted--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastWheelchairAssisted")){
			southEastToEastWheelchairAssisted--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestWheelchairAssisted")){
			southEastToSouthWestWheelchairAssisted--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWheelchairAssisted")){
			southEastToSouthWheelchairAssisted--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		wheelChair_assisted--;
	}
	
	private void decreaseWheelchairManual(){
		if(lastSelectedObject.peek().equals("northWestToNorthWheelchairManual")){
			northWestToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastWheelchairManual")){
			northWestToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestWheelchairManual")){
			northWestToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastWheelchairManual")){
			northWestToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestWheelchairManual")){
			northWestToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWheelchairManual")){
			northWestToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastWheelchairManual")){
			northWestToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestWheelchairManual")){
			northToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastWheelchairManual")){
			northToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestWheelchairManual")){
			northToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastWheelchairManual")){
			northToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestWheelchairManual")){
			northToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWheelchairManual")){
			northToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastWheelchairManual")){
			northToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestWheelchairManual")){
			northEastToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthWheelchairManual")){
			northEastToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestWheelchairManual")){
			northEastToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastWheelchairManual")){
			northEastToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestWheelchairManual")){
			northEastToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWheelchairManual")){
			northEastToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastWheelchairManual")){
			northEastToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestWheelchairManual")){
			westToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthWheelchairManual")){
			westToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastWheelchairManual")){
			westToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastWheelchairManual")){
			westToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestWheelchairManual")){
			westToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWheelchairManual")){
			westToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastWheelchairManual")){
			westToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestWheelchairManual")){
			eastToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthWheelchairManual")){
			eastToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastWheelchairManual")){
			eastToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestWheelchairManual")){
			eastToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestWheelchairManual")){
			eastToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWheelchairManual")){
			eastToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastWheelchairManual")){
			eastToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestWheelchairManual")){
			southWestToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthWheelchairManual")){
			southWestToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastWheelchairManual")){
			southWestToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestWheelchairManual")){
			southWestToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastWheelchairManual")){
			southWestToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthWheelchairManual")){
			southWestToSouthWheelchairManual--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastWheelchairManual")){
			southWestToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestWheelchairManual")){
			southToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthWheelchairManual")){
			southToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastWheelchairManual")){
			southToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestWheelchairManual")){
			southToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastWheelchairManual")){
			southToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestWheelchairManual")){
			southToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastWheelchairManual")){
			southToSouthEastWheelchairManual--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestWheelchairManual")){
			southEastToNorthWestWheelchairManual--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthWheelchairManual")){
			southEastToNorthWheelchairManual--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastWheelchairManual")){
			southEastToNorthEastWheelchairManual--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestWheelchairManual")){
			southEastToWestWheelchairManual--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastWheelchairManual")){
			southEastToEastWheelchairManual--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestWheelchairManual")){
			southEastToSouthWestWheelchairManual--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWheelchairManual")){
			southEastToSouthWheelchairManual--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		wheelChair_manual--;
	}
	
	private void decreaseWheelchairPowered(){
		if(lastSelectedObject.peek().equals("northWestToNorthWheelchairPowered")){
			northWestToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastWheelchairPowered")){
			northWestToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestWheelchairPowered")){
			northWestToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastWheelchairPowered")){
			northWestToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestWheelchairPowered")){
			northWestToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWheelchairPowered")){
			northWestToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastWheelchairPowered")){
			northWestToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestWheelchairPowered")){
			northToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastWheelchairPowered")){
			northToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestWheelchairPowered")){
			northToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastWheelchairPowered")){
			northToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestWheelchairPowered")){
			northToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWheelchairPowered")){
			northToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastWheelchairPowered")){
			northToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestWheelchairPowered")){
			northEastToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthWheelchairPowered")){
			northEastToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestWheelchairPowered")){
			northEastToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastWheelchairPowered")){
			northEastToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestWheelchairPowered")){
			northEastToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWheelchairPowered")){
			northEastToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastWheelchairPowered")){
			northEastToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestWheelchairPowered")){
			westToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthWheelchairPowered")){
			westToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastWheelchairPowered")){
			westToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastWheelchairPowered")){
			westToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestWheelchairPowered")){
			westToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWheelchairPowered")){
			westToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastWheelchairPowered")){
			westToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestWheelchairPowered")){
			eastToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthWheelchairPowered")){
			eastToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastWheelchairPowered")){
			eastToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestWheelchairPowered")){
			eastToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestWheelchairPowered")){
			eastToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWheelchairPowered")){
			eastToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastWheelchairPowered")){
			eastToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestWheelchairPowered")){
			southWestToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthWheelchairPowered")){
			southWestToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastWheelchairPowered")){
			southWestToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestWheelchairPowered")){
			southWestToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastWheelchairPowered")){
			southWestToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthWheelchairPowered")){
			southWestToSouthWheelchairPowered--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastWheelchairPowered")){
			southWestToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestWheelchairPowered")){
			southToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthWheelchairPowered")){
			southToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastWheelchairPowered")){
			southToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestWheelchairPowered")){
			southToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastWheelchairPowered")){
			southToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestWheelchairPowered")){
			southToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastWheelchairPowered")){
			southToSouthEastWheelchairPowered--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestWheelchairPowered")){
			southEastToNorthWestWheelchairPowered--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthWheelchairPowered")){
			southEastToNorthWheelchairPowered--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastWheelchairPowered")){
			southEastToNorthEastWheelchairPowered--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestWheelchairPowered")){
			southEastToWestWheelchairPowered--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastWheelchairPowered")){
			southEastToEastWheelchairPowered--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestWheelchairPowered")){
			southEastToSouthWestWheelchairPowered--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWheelchairPowered")){
			southEastToSouthWheelchairPowered--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		wheelChair_powered--;
	}
	
	private void decreasePushChair(){
		if(lastSelectedObject.peek().equals("northWestToNorthPushChair")){
			northWestToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastPushChair")){
			northWestToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestPushChair")){
			northWestToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastPushChair")){
			northWestToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestPushChair")){
			northWestToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthPushChair")){
			northWestToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastPushChair")){
			northWestToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestPushChair")){
			northToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastPushChair")){
			northToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestPushChair")){
			northToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastPushChair")){
			northToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestPushChair")){
			northToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthPushChair")){
			northToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastPushChair")){
			northToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestPushChair")){
			northEastToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthPushChair")){
			northEastToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestPushChair")){
			northEastToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastPushChair")){
			northEastToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestPushChair")){
			northEastToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthPushChair")){
			northEastToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastPushChair")){
			northEastToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestPushChair")){
			westToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthPushChair")){
			westToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastPushChair")){
			westToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastPushChair")){
			westToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestPushChair")){
			westToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthPushChair")){
			westToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastPushChair")){
			westToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestPushChair")){
			eastToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthPushChair")){
			eastToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastPushChair")){
			eastToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestPushChair")){
			eastToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestPushChair")){
			eastToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthPushChair")){
			eastToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastPushChair")){
			eastToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestPushChair")){
			southWestToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthPushChair")){
			southWestToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastPushChair")){
			southWestToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestPushChair")){
			southWestToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastPushChair")){
			southWestToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthPushChair")){
			southWestToSouthPushChair--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastPushChair")){
			southWestToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestPushChair")){
			southToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthPushChair")){
			southToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastPushChair")){
			southToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestPushChair")){
			southToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastPushChair")){
			southToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestPushChair")){
			southToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastPushChair")){
			southToSouthEastPushChair--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestPushChair")){
			southEastToNorthWestPushChair--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthPushChair")){
			southEastToNorthPushChair--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastPushChair")){
			southEastToNorthEastPushChair--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestPushChair")){
			southEastToWestPushChair--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastPushChair")){
			southEastToEastPushChair--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestPushChair")){
			southEastToSouthWestPushChair--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthPushChair")){
			southEastToSouthPushChair--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		pushChair--;
	}
	
	private void decreaseSkateboard(){
		if(lastSelectedObject.peek().equals("northWestToNorthSkateboard")){
			northWestToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastSkateboard")){
			northWestToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestSkateboard")){
			northWestToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastSkateboard")){
			northWestToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestSkateboard")){
			northWestToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthSkateboard")){
			northWestToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastSkateboard")){
			northWestToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestSkateboard")){
			northToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastSkateboard")){
			northToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestSkateboard")){
			northToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastSkateboard")){
			northToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestSkateboard")){
			northToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthSkateboard")){
			northToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastSkateboard")){
			northToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestSkateboard")){
			northEastToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthSkateboard")){
			northEastToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestSkateboard")){
			northEastToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastSkateboard")){
			northEastToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestSkateboard")){
			northEastToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthSkateboard")){
			northEastToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastSkateboard")){
			northEastToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestSkateboard")){
			westToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthSkateboard")){
			westToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastSkateboard")){
			westToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastSkateboard")){
			westToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestSkateboard")){
			westToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthSkateboard")){
			westToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastSkateboard")){
			westToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestSkateboard")){
			eastToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthSkateboard")){
			eastToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastSkateboard")){
			eastToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestSkateboard")){
			eastToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestSkateboard")){
			eastToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthSkateboard")){
			eastToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastSkateboard")){
			eastToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestSkateboard")){
			southWestToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthSkateboard")){
			southWestToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastSkateboard")){
			southWestToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestSkateboard")){
			southWestToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastSkateboard")){
			southWestToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthSkateboard")){
			southWestToSouthSkateboard--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastSkateboard")){
			southWestToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestSkateboard")){
			southToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthSkateboard")){
			southToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastSkateboard")){
			southToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestSkateboard")){
			southToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastSkateboard")){
			southToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestSkateboard")){
			southToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastSkateboard")){
			southToSouthEastSkateboard--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestSkateboard")){
			southEastToNorthWestSkateboard--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthSkateboard")){
			southEastToNorthSkateboard--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastSkateboard")){
			southEastToNorthEastSkateboard--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestSkateboard")){
			southEastToWestSkateboard--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastSkateboard")){
			southEastToEastSkateboard--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestSkateboard")){
			southEastToSouthWestSkateboard--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthSkateboard")){
			southEastToSouthSkateboard--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		skateboard--;
	}
	
	private void decreaseManualScooter(){
		if(lastSelectedObject.peek().equals("northWestToNorthManualScooter")){
			northWestToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToNorthEastManualScooter")){
			northWestToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToWestManualScooter")){
			northWestToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToEastManualScooter")){
			northWestToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthWestManualScooter")){
			northWestToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthManualScooter")){
			northWestToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northWestToSouthEastManualScooter")){
			northWestToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northToNorthWestManualScooter")){
			northToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToNorthEastManualScooter")){
			northToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToWestManualScooter")){
			northToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToEastManualScooter")){
			northToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthWestManualScooter")){
			northToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthManualScooter")){
			northToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northToSouthEastManualScooter")){
			northToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("northEastToNorthWestManualScooter")){
			northEastToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToNorthManualScooter")){
			northEastToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToWestManualScooter")){
			northEastToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToEastManualScooter")){
			northEastToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthWestManualScooter")){
			northEastToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthManualScooter")){
			northEastToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("northEastToSouthEastManualScooter")){
			northEastToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("westToNorthWestManualScooter")){
			westToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthManualScooter")){
			westToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToNorthEastManualScooter")){
			westToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToEastManualScooter")){
			westToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthWestManualScooter")){
			westToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthManualScooter")){
			westToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("westToSouthEastManualScooter")){
			westToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("eastToNorthWestManualScooter")){
			eastToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthManualScooter")){
			eastToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToNorthEastManualScooter")){
			eastToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToWestManualScooter")){
			eastToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthWestManualScooter")){
			eastToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthManualScooter")){
			eastToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("eastToSouthEastManualScooter")){
			eastToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southWestToNorthWestManualScooter")){
			southWestToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthManualScooter")){
			southWestToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToNorthEastManualScooter")){
			southWestToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToWestManualScooter")){
			southWestToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToEastManualScooter")){
			southWestToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthManualScooter")){
			southWestToSouthManualScooter--;
			southTotal--;
			
		}else if(lastSelectedObject.peek().equals("southWestToSouthEastManualScooter")){
			southWestToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southToNorthWestManualScooter")){
			southToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthManualScooter")){
			southToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToNorthEastManualScooter")){
			southToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToWestManualScooter")){
			southToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToEastManualScooter")){
			southToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthWestManualScooter")){
			southToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southToSouthEastManualScooter")){
			southToSouthEastManualScooter--;
			southEastTotal--;
			
			///////////////////////////////////////////////////////////////
		}else if(lastSelectedObject.peek().equals("southEastToNorthWestManualScooter")){
			southEastToNorthWestManualScooter--;
			northWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthManualScooter")){
			southEastToNorthManualScooter--;
			northTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToNorthEastManualScooter")){
			southEastToNorthEastManualScooter--;
			northEastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToWestManualScooter")){
			southEastToWestManualScooter--;
			westTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToEastManualScooter")){
			southEastToEastManualScooter--;
			eastTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthWestManualScooter")){
			southEastToSouthWestManualScooter--;
			southWestTotal--;
			
		}else if(lastSelectedObject.peek().equals("southEastToSouthManualScooter")){
			southEastToSouthManualScooter--;
			southTotal--;
			
			///////////////////////////////////////////////////////////////
		}
		manualScooter--;
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
		case "Wheelchair (Assisted)":
			wheelChair_assisted++;
			currentlySelectedCount = wheelChair_assisted;
			passed = true;
			break;
		case "Wheelchair (Manual)":
			wheelChair_manual++;
			currentlySelectedCount = wheelChair_manual;
			passed = true;
			break;
		case "Wheelchair (Powered)":
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
	private void updateAllCounts(boolean count){
		if(count){
			increaseObjectCount();
			increaseTotalCount();
		}
		updateDirectionCount();
		updateAllDirectionalButtons();
		initialiseDirectionFromTo();
		defaultTo(defaultObject);
		Toast.makeText(this, defaultObject, Toast.LENGTH_SHORT).show();
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
			checkMotorBike(directionFromPosition, directionToPosition);
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
			checkWheelchairAssisted(directionFromPosition, directionToPosition);
			break;
		case "Wheel Chair (Manual)":
			checkWheelchairManual(directionFromPosition, directionToPosition);
			break;
		case "Wheel Chair (Powered)":
			checkWheelchairPowered(directionFromPosition, directionToPosition);
			break;
		case "Push Chair / Buggy":
			checkPushChair(directionFromPosition, directionToPosition);
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
		if(directionFromPosition == 0){
			if(directionToPosition == 1){
				northWestToNorthBus++;
				northTotal++;
				lastSelectedObject.push("northWestToNorthBus");
			}else if(directionToPosition == 2){
				northWestToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("northWestToNorthEastBus");
			}else if(directionToPosition == 3){
				northWestToWestBus++;
				westTotal++;
				lastSelectedObject.push("northWestToWestBus");
			}else if(directionToPosition == 4){
				northWestToEastBus++;
				eastTotal++;
				lastSelectedObject.push("northWestToEastBus");
			}else if(directionToPosition == 5){
				northWestToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("northWestToSouthWestBus");
			}else if(directionToPosition == 6){
				northWestToSouthBus++;
				southTotal++;
				lastSelectedObject.push("northWestToSouthBus");
			}else if(directionToPosition == 7){
				northWestToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("northWestToSouthEastBus");
			}
		}else if(directionFromPosition == 1){
			if(directionToPosition == 0){
				northToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("northToNorthWestBus");
			}else if(directionToPosition == 2){
				northToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("northToNorthEastBus");
			}else if(directionToPosition == 3){
				northToWestBus++;
				westTotal++;
				lastSelectedObject.push("northToWestBus");
			}else if(directionToPosition == 4){
				northToEastBus++;
				eastTotal++;
				lastSelectedObject.push("northToEastBus");
			}else if(directionToPosition == 5){
				northToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("northToSouthWestBus");
			}else if(directionToPosition == 6){
				northToSouthBus++;
				southTotal++;
				lastSelectedObject.push("northToSouthBus");
			}else if(directionToPosition == 7){
				northToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("northToSouthEastBus");
			}
		} else if(directionFromPosition == 2){
			if(directionToPosition == 0){
				northEastToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("northEastToNorthWestBus");
			}else if(directionToPosition == 1){
				northEastToNorthBus++;
				northTotal++;
				lastSelectedObject.push("northEastToNorthBus");
			}else if(directionToPosition == 3){
				northEastToWestBus++;
				westTotal++;
				lastSelectedObject.push("northEastToWestBus");
			}else if(directionToPosition == 4){
				northEastToEastBus++;
				eastTotal++;
				lastSelectedObject.push("northEastToEastBus");
			}else if(directionToPosition == 5){
				northEastToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("northEastToSouthWestBus");
			}else if(directionToPosition == 6){
				northEastToSouthBus++;
				southTotal++;
				lastSelectedObject.push("northEastToSouthBus");
			}else if(directionToPosition == 7){
				northEastToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("northEastToSouthEastBus");
			}
		} else if(directionFromPosition == 3){
			if(directionToPosition == 0){
				westToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("westToNorthWestBus");
			}else if(directionToPosition == 1){
				westToNorthBus++;
				northTotal++;
				lastSelectedObject.push("westToNorthBus");
			}else if(directionToPosition == 2){
				westToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("westToNorthEastBus");
			}else if(directionToPosition == 4){
				westToEastBus++;
				eastTotal++;
				lastSelectedObject.push("westToEastBus");
			}else if(directionToPosition == 5){
				westToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("westToSouthWestBus");
			}else if(directionToPosition == 6){
				westToSouthBus++;
				southTotal++;
				lastSelectedObject.push("westToSouthBus");
			}else if(directionToPosition == 7){
				westToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("westToSouthWestBus");
			}
		}else if(directionFromPosition == 4){
			if(directionToPosition == 0){
				eastToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("eastToNorthWestBus");
			}else if(directionToPosition == 1){
				eastToNorthBus++;
				northTotal++;
				lastSelectedObject.push("eastToNorthBus");
			}else if(directionToPosition == 2){
				eastToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("eastToNorthEastBus");
			}else if(directionToPosition == 3){
				eastToWestBus++;
				westTotal++;
				lastSelectedObject.push("eastToWestBus");
			}else if(directionToPosition == 5){
				eastToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("eastToSouthWestBus");
			}else if(directionToPosition == 6){
				eastToSouthBus++;
				southTotal++;
				lastSelectedObject.push("eastToSouthBus");
			}else if(directionToPosition == 7){
				eastToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("eastToSouthEastBus");
			}
		}else if(directionFromPosition == 5){
			if(directionToPosition == 0){
				southWestToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("southWestToNorthWestBus");
			}else if(directionToPosition == 1){
				southWestToNorthBus++;
				northTotal++;
				lastSelectedObject.push("southWestToNorthBus");
			}else if(directionToPosition == 2){
				southWestToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("southWestToNorthEastBus");
			}else if(directionToPosition == 3){
				southWestToWestBus++;
				westTotal++;
				lastSelectedObject.push("southWestToWestBus");
			}else if(directionToPosition == 4){
				southWestToEastBus++;
				eastTotal++;
				lastSelectedObject.push("southWestToEastBus");
			}else if(directionToPosition == 6){
				southWestToSouthBus++;
				southTotal++;
				lastSelectedObject.push("southWestToSouthBus");
			}else if(directionToPosition == 7){
				southWestToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("southWestToSouthEastBus");
			}
		}else if(directionFromPosition == 6){
			if(directionToPosition == 0){
				southToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("southToNorthWestBus");
			}else if(directionToPosition == 1){
				southToNorthBus++;
				northTotal++;
				lastSelectedObject.push("southToNorthBus");
			}else if(directionToPosition == 2){
				southToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("southToNorthEastBus");
			}else if(directionToPosition == 3){
				southToWestBus++;
				westTotal++;
				lastSelectedObject.push("southToWestBus");
			}else if(directionToPosition == 4){
				southToEastBus++;
				eastTotal++;
				lastSelectedObject.push("southToEastBus");
			}else if(directionToPosition == 5){
				southToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("southToSouthWestBus");
			}else if(directionToPosition == 7){
				southToSouthEastBus++;
				southEastTotal++;
				lastSelectedObject.push("southToSouthEastBus");
			}
		}else if(directionFromPosition == 7){
			if(directionToPosition == 0){
				southEastToNorthWestBus++;
				northWestTotal++;
				lastSelectedObject.push("southEastToNorthWestBus");
			}else if(directionToPosition == 1){
				southEastToNorthBus++;
				northTotal++;
				lastSelectedObject.push("southEastToNorthBus");
			}else if(directionToPosition == 2){
				southEastToNorthEastBus++;
				northEastTotal++;
				lastSelectedObject.push("southEastToNorthEastBus");
			}else if(directionToPosition == 3){
				southEastToWestBus++;
				westTotal++;
				lastSelectedObject.push("southEastToWestBus");
			}else if(directionToPosition == 4){
				southEastToEastBus++;
				eastTotal++;
				lastSelectedObject.push("southEastToEastBus");
			}else if(directionToPosition == 5){
				southEastToSouthWestBus++;
				southWestTotal++;
				lastSelectedObject.push("southEastToSouthWestBus");
			}else if(directionToPosition == 6){
				southEastToSouthBus++;
				southTotal++;
				lastSelectedObject.push("southEastToSouthBus");
			}
		}
	}
	
	private void checkCar(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCar++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthCar");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastCar");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCar++;
			westTotal++;
			lastSelectedObject.push("northWestToWestCar");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCar++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastCar");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestCar");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCar++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthCar");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastCar");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestCar");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastCar");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCar++;
			westTotal++;
			lastSelectedObject.push("northToWestCar");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCar++;
			eastTotal++;
			lastSelectedObject.push("northToEastCar");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestCar");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCar++;
			southTotal++;
			lastSelectedObject.push("northToSouthCar");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastCar");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestCar");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCar++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthCar");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCar++;
			westTotal++;
			lastSelectedObject.push("northEastToWestCar");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCar++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastCar");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestCar");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCar++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthCar");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthWestCar");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestCar");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCar++;
			northTotal++;
			lastSelectedObject.push("westToNorthCar");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastCar");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCar++;
			eastTotal++;
			lastSelectedObject.push("westToEastCar");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestCar");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCar++;
			southTotal++;
			lastSelectedObject.push("westToSouthCar");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastCar");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestCar");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCar++;
			northTotal++;
			lastSelectedObject.push("eastToNorthCar");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastCar");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCar++;
			westTotal++;
			lastSelectedObject.push("eastToWestCar");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestCar");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCar++;
			southTotal++;
			lastSelectedObject.push("eastToSouthCar");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastCar");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestCar");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCar++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthCar");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastCar");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCar++;
			westTotal++;
			lastSelectedObject.push("southWestToWestCar");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCar++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastCar");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCar++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthCar");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastCar");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestCar");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCar++;
			northTotal++;
			lastSelectedObject.push("southToNorthCar");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastCar");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCar++;
			westTotal++;
			lastSelectedObject.push("southToWestCar");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCar++;
			eastTotal++;
			lastSelectedObject.push("southToEastCar");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestCar");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCar++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastCar");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCar++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestCar");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCar++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthCar");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCar++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastCar");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCar++;
			westTotal++;
			lastSelectedObject.push("southEastToWestCar");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCar++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastCar");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCar++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestCar");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCar++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthCar");
		}
	}
	
	private void checkTruck(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestTruck++;
			westTotal++;
			lastSelectedObject.push("northWestToEastTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("northWestToWestTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthTruck");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestTruck++;
			westTotal++;
			lastSelectedObject.push("northToWestTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("northToEastTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("northToSouthTruck");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastTruck");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestTruck++;
			westTotal++;
			lastSelectedObject.push("northEastToWestTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthTruck");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastTruck");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("westToNorthTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("westToEastTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("westToSouthTruck");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("eastToNorthTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestTruck++;
			westTotal++;
			lastSelectedObject.push("eastToWestTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("eastToSouthTruck");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthWestTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestTruck++;
			westTotal++;
			lastSelectedObject.push("southWestToWestTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthWestTruck");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("southToNorthTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestTruck++;
			westTotal++;
			lastSelectedObject.push("southToWestTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("southToEastTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestTruck");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastTruck++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestTruck++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthTruck++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastTruck++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestTruck++;
			westTotal++;
			lastSelectedObject.push("southEastToWestTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastTruck++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestTruck++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestTruck");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthTruck++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthTruck");
		}
	}
	
	private void checkMotorBike(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("northWestToWestMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthMotorBike");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("northToWestMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("northToEastMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("northToSouthMotorBike");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastMotorBike");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("northEastToWestMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthMotorBike");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastMotorBike");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("westToNorthMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("westToEastMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("westToSouthMotorBike");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("eastToNorthMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("eastToWestMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("eastToSouthMotorBike");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("southWestToWestMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthMotorBike");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("southToNorthMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("southToWestMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("southToEastMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestMotorBike");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMotorBike++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMotorBike++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMotorBike++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMotorBike++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMotorBike++;
			westTotal++;
			lastSelectedObject.push("southEastToWestMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMotorBike++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMotorBike++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestMotorBike");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMotorBike++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthMotorBike");
		}
	}
	
	private void checkPedestrian(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("northWestToWestPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthPedestrian");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("northToWestPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("northToEastPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("northToSouthPedestrian");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastPedestrian");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("northEastToWestPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthPedestrian");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastPedestrian");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("westToNorthPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("westToEastPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("westToSouthPedestrian");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("eastToNorthPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("eastToWestPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("eastToSouthPedestrian");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("southWestToWestPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthPedestrian");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("southToNorthPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("southToWestPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("southToEastPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestPedestrian");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPedestrian++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPedestrian++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPedestrian++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPedestrian++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPedestrian++;
			westTotal++;
			lastSelectedObject.push("southEastToWestPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPedestrian++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPedestrian++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestPedestrian");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPedestrian++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthPedestrian");
		}
	}
	
	private void checkCrutches1(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("northWestToWestCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthCrutches1");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("northToWestCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("northToEastCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("northToSouthCrutches1");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastCrutches1");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("northEastToWestCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthCrutches1");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastCrutches1");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("westToNorthCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("westToEastCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("westToSouthCrutches1");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("eastToNorthCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("eastToWestCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("eastToSouthCrutches1");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("southWestToWestCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthCrutches1");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("southToNorthCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("southToWestCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("southToEastCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWesCrutches1");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches1++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches1++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches1++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches1++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches1++;
			westTotal++;
			lastSelectedObject.push("southEastToWestCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches1++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches1++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestCrutches1");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches1++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthCrutches1");
		}
	}
	
	private void checkCrutches2(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("northWestToWestCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthCrutches2");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("northToWestCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("northToEastCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("northToSouthCrutches2");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastCrutches2");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthWestCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("northEastToWestCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthCrutches2");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastCrutches2");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("westToNorthCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("westToEastCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("westToSouthCrutches2");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("eastToNorthCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("eastToWestCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("eastToSouthCrutches2");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("southWestToWestCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthCrutches2");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("southToNorthCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("southToWestCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("southToEastCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestCrutches2");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCrutches2++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCrutches2++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCrutches2++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCrutches2++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCrutches2++;
			westTotal++;
			lastSelectedObject.push("southEastToWestCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCrutches2++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCrutches2++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestCrutches2");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCrutches2++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthCrutches2");
		}
	}
	
	private void checkCane(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthCane++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthCane");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastCane");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestCane++;
			westTotal++;
			lastSelectedObject.push("northWestToWestCane");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastCane++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastCane");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestCane");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthCane++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthCane");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastCane");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestCane");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastCane");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestCane++;
			westTotal++;
			lastSelectedObject.push("northToWestCane");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastCane++;
			eastTotal++;
			lastSelectedObject.push("northToEastCane");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestCane");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthCane++;
			southTotal++;
			lastSelectedObject.push("northToSouthCane");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastCane");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestCane");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthCane++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthCane");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestCane++;
			westTotal++;
			lastSelectedObject.push("northEastToWestCane");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastCane++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastCane");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestCane");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthCane++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthCane");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastCane");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestCane");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthCane++;
			northTotal++;
			lastSelectedObject.push("westToNorthCane");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastCane");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastCane++;
			eastTotal++;
			lastSelectedObject.push("westToEastCane");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestCane");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthCane++;
			southTotal++;
			lastSelectedObject.push("westToSouthCane");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastCane");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestCane");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthCane++;
			northTotal++;
			lastSelectedObject.push("eastToNorthCane");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastCane");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestCane++;
			westTotal++;
			lastSelectedObject.push("eastToWestCane");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestCane");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthCane++;
			southTotal++;
			lastSelectedObject.push("eastToSouthCane");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastCane");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestCane");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthCane++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthCane");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastCane");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestCane++;
			westTotal++;
			lastSelectedObject.push("southWestToWestCane");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastCane++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastCane");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthCane++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthCane");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEastCane");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestCane");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthCane++;
			northTotal++;
			lastSelectedObject.push("southToNorthCane");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastCane");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestCane++;
			westTotal++;
			lastSelectedObject.push("southToWestCane");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastCane++;
			eastTotal++;
			lastSelectedObject.push("southToEastCane");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestCane");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastCane++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastCane");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestCane++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestCane");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthCane++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthCane");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastCane++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastCane");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestCane++;
			westTotal++;
			lastSelectedObject.push("southEastToWestCane");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastCane++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastCane");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestCane++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestCane");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthCane++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthCane");
		}
	}
	
	private void checkDog(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthDog++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthDog");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastDog");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestDog++;
			westTotal++;
			lastSelectedObject.push("northWestToWestDog");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastDog++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastDog");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestDog");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthDog++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthDog");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastDog");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestDog");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastDog");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestDog++;
			westTotal++;
			lastSelectedObject.push("northToWestDog");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastDog++;
			eastTotal++;
			lastSelectedObject.push("northToEastDog");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestDog");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthDog++;
			southTotal++;
			lastSelectedObject.push("northToSouthDog");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastDog");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestDog");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthDog++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthDog");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestDog++;
			westTotal++;
			lastSelectedObject.push("northEastToWestDog");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastDog++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastDog");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestDog");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthDog++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthDog");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastDog");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestDog");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthDog++;
			northTotal++;
			lastSelectedObject.push("westToNorthDog");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastDog");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastDog++;
			eastTotal++;
			lastSelectedObject.push("westToEastDog");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestDog");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthDog++;
			southTotal++;
			lastSelectedObject.push("westToSouthDog");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastDog");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestDog");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthDog++;
			northTotal++;
			lastSelectedObject.push("eastToNorthDog");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastDog");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestDog++;
			westTotal++;
			lastSelectedObject.push("eastToWestDog");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestDog");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthDog++;
			southTotal++;
			lastSelectedObject.push("eastToSouthDog");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastDog");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestDog");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthDog++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthDog");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastDog");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestDog++;
			westTotal++;
			lastSelectedObject.push("southWestToWestDog");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastDog++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastDog");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthDog++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthDog");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasDog");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestDog");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthDog++;
			northTotal++;
			lastSelectedObject.push("southToNorthDog");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastDog");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestDog++;
			westTotal++;
			lastSelectedObject.push("southToWestDog");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastDog++;
			eastTotal++;
			lastSelectedObject.push("southToEastDog");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestDog");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastDog++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastDog");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestDog++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestDog");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthDog++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthDog");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastDog++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastDog");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestDog++;
			westTotal++;
			lastSelectedObject.push("southEastToWestDog");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastDog++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastDog");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestDog++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestDog");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthDog++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthDog");
		}
	}
	
	private void checkMobilityScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("northWestToWestMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthMobilityScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("northToWestMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("northToEastMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("northToSouthMobilityScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastMobilityScooter");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("northEastToWestMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthMobilityScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastMobilityScooter");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("westToNorthMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("westToEastMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("westToSouthMobilityScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("eastToNorthMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("eastToWestMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("eastToSouthMobilityScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("southWestToWestMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthMobilityScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("southToNorthMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("southToWestMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("southToEastMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestMobilityScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastMobilityScooter++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestMobilityScooter++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthMobilityScooter++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastMobilityScooter++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestMobilityScooter++;
			westTotal++;
			lastSelectedObject.push("southEastToWestMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastMobilityScooter++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestMobilityScooter++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestMobilityScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthMobilityScooter++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthMobilityScooter");
		}
	}
	
	private void checkWheelchairAssisted(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("northWestToWestWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthWheelchairAssisted");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("northToWestWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("northToEastWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("northToSouthWheelchairAssisted");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastWheelchairAssisted");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("northEastToWestWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthWheelchairAssisted");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastWheelchairAssisted");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("westToNorthWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("westToEastWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("westToSouthWheelchairAssisted");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("eastToNorthWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("eastToWestWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("eastToSouthWheelchairAssisted");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("southWestToWestWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthWheelchairAssisted");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("southToNorthWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("southToWestWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("southToEastWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelchairAssisted++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelchairAssisted++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelchairAssisted++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelchairAssisted++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelchairAssisted++;
			westTotal++;
			lastSelectedObject.push("southEastToWestWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelchairAssisted++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelchairAssisted++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestWheelchairAssisted");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelchairAssisted++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthWheelchairAssisted");
		}
	}

	private void checkWheelchairManual(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("northWestToWestWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthWheelchairManual");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("northToWestWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("northToEastWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("northToSouthWheelchairManual");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastWheelchairManual");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("northEastToWestWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthWheelchairManual");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastWheelchairManual");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("westToNorthWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("westToEastWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("westToSouthWheelchairManual");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("eastToNorthWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("eastToWestWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("eastToSouthWheelchairManual");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("southWestToWestWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthWheelchairManual");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("southToNorthWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("southToWestWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("southToEastWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestWheelchairManual");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelchairManual++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelchairManual++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelchairManual++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelchairManual++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelchairManual++;
			westTotal++;
			lastSelectedObject.push("southEastToWestWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelchairManual++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelchairManual++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestWheelchairManual");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelchairManual++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthWheelchairManual");
		}
	}
	
	private void checkWheelchairPowered(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("northWestToWestWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthWheelchairPowered");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("northToWestWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("northToEastWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("northToSouthWheelchairPowered");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastWheelchairPowered");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("northEastToWestWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthWheelchairPowered");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastWheelchairPowered");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("westToNorthWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("westToEastWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("westToSouthWheelchairPowered");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("eastToNorthWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("eastToWestWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("eastToSouthWheelchairPowered");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("southWestToWestWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthWheelchairPowered");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("southToNorthWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("southToWestWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("southToEastWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastWheelchairPowered++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestWheelchairPowered++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthWheelchairPowered++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastWheelchairPowered++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestWheelchairPowered++;
			westTotal++;
			lastSelectedObject.push("southEastToWestWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastWheelchairPowered++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestWheelchairPowered++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestWheelchairPowered");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthWheelchairPowered++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthWheelchairPowered");
		}
	}
	
	private void checkPushChair(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("northWestToWestPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthPushChair");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("northToWestPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("northToEastPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("northToSouthPushChair");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastPushChair");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("northEastToWestPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthPushChair");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastPushChair");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("westToNorthPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("westToEastPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("westToSouthPushChair");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("eastToNorthPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("eastToWestPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("eastToSouthPushChair");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("southWestToWestPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthPushChair");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("southToNorthPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("southToWestPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("southToEastPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestPushChair");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastPushChair++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestPushChair++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthPushChair++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastPushChair++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestPushChair++;
			westTotal++;
			lastSelectedObject.push("southEastToWestPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastPushChair++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestPushChair++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestPushChair");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthPushChair++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthPushChair");
		}
	}
	
	private void checkSkateboard(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("northWestToWestSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthSkateboard");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("northToWestSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("northToEastSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("northToSouthSkateboard");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastSkateboard");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("northEastToWestSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthSkateboard");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastSkateboard");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("westToNorthSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("westToEastSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("westToSouthSkateboard");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("eastToNorthSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("eastToWestSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("eastToSouthSkateboard");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("southWestToWestSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthSkateboard");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("southToNorthSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("southToWestSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("southToEastSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestSkateboard");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastSkateboard++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestSkateboard++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthSkateboard++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastSkateboard++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestSkateboard++;
			westTotal++;
			lastSelectedObject.push("southEastToWestSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastSkateboard++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestSkateboard++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestSkateboard");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthSkateboard++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthSkateboard");
		}
	}
	
	private void checkManualScooter(int directionFromPosition, int directionToPosition){
		if(directionFromPosition == 0 && directionToPosition == 1){
			northWestToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("northWestToNorthManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 2){
			northWestToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("northWestToNorthEastManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 3){
			northWestToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("northWestToWestManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 4){
			northWestToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("northWestToEastManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 5){
			northWestToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("northWestToSouthWestManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 6){
			northWestToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("northWestToSouthManualScooter");
		}else if(directionFromPosition == 0 && directionToPosition == 7){
			northWestToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("northWestToSouthEastManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 0){
			northToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("northToNorthWestManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 2){
			northToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("northToNorthEastManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 3){
			northToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("northToWestManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 4){
			northToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("northToEastManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 5){
			northToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("northToSouthWestManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 6){
			northToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("northToSouthManualScooter");
		}else if(directionFromPosition == 1 && directionToPosition == 7){
			northToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("northToSouthEastManualScooter");
		} else if(directionFromPosition == 2 && directionToPosition == 0){
			northEastToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("northEastToNorthWestManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 1){
			northEastToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("northEastToNorthManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 3){
			northEastToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("northEastToWestManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 4){
			northEastToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("northEastToEastManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 5){
			northEastToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("northEastToSouthWestManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 6){
			northEastToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("northEastToSouthManualScooter");
		}else if(directionFromPosition == 2 && directionToPosition == 7){
			northEastToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("northEastToSouthEastManualScooter");
		} else if(directionFromPosition == 3 && directionToPosition == 0){
			westToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("westToNorthWestManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 1){
			westToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("westToNorthManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 2){
			westToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("westToNorthEastManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 4){
			westToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("westToEastManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 5){
			westToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("westToSouthWestManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 6){
			westToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("westToSouthManualScooter");
		}else if(directionFromPosition == 3 && directionToPosition == 7){
			westToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("westToSouthEastManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 0){
			eastToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("eastToNorthWestManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 1){
			eastToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("eastToNorthManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 2){
			eastToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("eastToNorthEastManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 3){
			eastToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("eastToWestManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 5){
			eastToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("eastToSouthWestManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 6){
			eastToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("eastToSouthManualScooter");
		}else if(directionFromPosition == 4 && directionToPosition == 7){
			eastToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("eastToSouthEastManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 0){
			southWestToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("southWestToNorthWestManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 1){
			southWestToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("southWestToNorthManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 2){
			southWestToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("southWestToNorthEastManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 3){
			southWestToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("southWestToWestManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 4){
			southWestToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("southWestToEastManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 6){
			southWestToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("southWestToSouthManualScooter");
		}else if(directionFromPosition == 5 && directionToPosition == 7){
			southWestToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("southWestToSouthEasManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 0){
			southToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("southToNorthWestManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 1){
			southToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("southToNorthManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 2){
			southToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("southToNorthEastManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 3){
			southToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("southToWestManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 4){
			southToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("southToEastManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 5){
			southToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("southToSouthWestManualScooter");
		}else if(directionFromPosition == 6 && directionToPosition == 7){
			southToSouthEastManualScooter++;
			southEastTotal++;
			lastSelectedObject.push("southToSouthEastManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 0){
			southEastToNorthWestManualScooter++;
			northWestTotal++;
			lastSelectedObject.push("southEastToNorthWestManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 1){
			southEastToNorthManualScooter++;
			northTotal++;
			lastSelectedObject.push("southEastToNorthManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 2){
			southEastToNorthEastManualScooter++;
			northEastTotal++;
			lastSelectedObject.push("southEastToNorthEastManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 3){
			southEastToWestManualScooter++;
			westTotal++;
			lastSelectedObject.push("southEastToWestManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 4){
			southEastToEastManualScooter++;
			eastTotal++;
			lastSelectedObject.push("southEastToEastManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 5){
			southEastToSouthWestManualScooter++;
			southWestTotal++;
			lastSelectedObject.push("southEastToSouthWestManualScooter");
		}else if(directionFromPosition == 7 && directionToPosition == 6){
			southEastToSouthManualScooter++;
			southTotal++;
			lastSelectedObject.push("southEastToSouthManualScooter");
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
		private boolean countTimerSaved;
		private boolean countTimerStarted;
		
		//-------------Jean-Yves' Initializations-----------------------
		private String startTime;
		private SimpleDateFormat df; 
		
		private String generalComments;
		private int dataRowsWritten = 1;

		private Calendar cal = Calendar.getInstance();
		
		//---------------------Richard Fong---------------------------------
		
		public CountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			countTimerSaved = false;
			countTimerStarted = false;
			df = new SimpleDateFormat("h:mm a");
			startTime = df.format(Calendar.getInstance().getTime());
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
				//saveData();
			}else{
				txt_timer.setText(ms);
			}
		}

		@Override
		public void onFinish() {			
			if (!countTimerSaved && countTimerStarted){
				countTimer.cancel();
				countTimerSaved = true;
				countTimerStarted = false;
				txt_timer.setText("Completed.");
				btn_setTime.setEnabled(true);
				saveData();
			}
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
			
			generalComments = comments;
			userMessage(generalComments);
			
			appendLocationHeader(fileWriter);
			appendFromAndToPostionsHeader(fileWriter);
			appendIntersectionCountsHeader(fileWriter);
			
			for (int x = 0; x < 8; x++) {
				if(intersectionsPicked[x]){
					for (int y = 0; y < 8; y++) {
						if(intersectionsPicked[y] && y != x){
							//FROM NORTH-WEST TO ...
							if(x == 0 && y == 1){
								//DIRECTION: North-west To North
								appendCountables(fileWriter, northWestToNorthCar, northWestToNorthBus, northWestToNorthTruck,
												northWestToNorthMotorBike, northWestToNorthPedestrian, northWestToNorthCrutches1,
												northWestToNorthCrutches2, northWestToNorthCane, northWestToNorthDog, northWestToNorthMobilityScooter,
												northWestToNorthWheelchairAssisted, northWestToNorthWheelchairManual, northWestToNorthWheelchairPowered,
												northWestToNorthPushChair, northWestToNorthSkateboard, northWestToNorthManualScooter, generalComments);
							}else if(x == 0 && y == 2){
								//North-west to North-East
								appendCountables(fileWriter, northWestToNorthEastCar, northWestToNorthEastBus, northWestToNorthEastTruck,
										northWestToNorthEastMotorBike, northWestToNorthEastPedestrian, northWestToNorthEastCrutches1,
										northWestToNorthEastCrutches2, northWestToNorthEastCane, northWestToNorthEastDog, northWestToNorthEastMobilityScooter,
										northWestToNorthEastWheelchairAssisted, northWestToNorthEastWheelchairManual, northWestToNorthEastWheelchairPowered,
										northWestToNorthEastPushChair, northWestToNorthEastSkateboard, northWestToNorthEastManualScooter, generalComments);
							}else if(x == 0 && y == 3){
								//North-west to West
								appendCountables(fileWriter, northWestToWestCar, northWestToWestBus, northWestToWestTruck,
										northWestToWestMotorBike, northWestToWestPedestrian, northWestToWestCrutches1,
										northWestToWestCrutches2, northWestToWestCane, northWestToWestDog, northWestToWestMobilityScooter,
										northWestToWestWheelchairAssisted, northWestToWestWheelchairManual, northWestToWestWheelchairPowered,
										northWestToWestPushChair, northWestToWestSkateboard, northWestToWestManualScooter, generalComments);
							}else if(x == 0 && y == 4){
								//North-west to East
								appendCountables(fileWriter, northWestToEastCar, northWestToEastBus, northWestToEastTruck,
										northWestToEastMotorBike, northWestToEastPedestrian, northWestToEastCrutches1,
										northWestToEastCrutches2, northWestToEastCane, northWestToEastDog, northWestToEastMobilityScooter,
										northWestToEastWheelchairAssisted, northWestToEastWheelchairManual, northWestToEastWheelchairPowered,
										northWestToEastPushChair, northWestToEastSkateboard, northWestToEastManualScooter, generalComments);
							}else if(x == 0 && y == 5){
								//North-west to South-West
								appendCountables(fileWriter, northWestToSouthWestCar, northWestToSouthWestBus, northWestToSouthWestTruck,
										northWestToSouthWestMotorBike, northWestToSouthWestPedestrian, northWestToSouthWestCrutches1,
										northWestToSouthWestCrutches2, northWestToSouthWestCane, northWestToSouthWestDog, northWestToSouthWestMobilityScooter,
										northWestToSouthWestWheelchairAssisted, northWestToSouthWestWheelchairManual, northWestToSouthWestWheelchairPowered,
										northWestToSouthWestPushChair, northWestToSouthWestSkateboard, northWestToSouthWestManualScooter, generalComments);
							}else if(x == 0 && y == 6){
								//North-west to South
								appendCountables(fileWriter, northWestToSouthCar, northWestToSouthBus, northWestToSouthTruck,
										northWestToSouthMotorBike, northWestToSouthPedestrian, northWestToSouthCrutches1,
										northWestToSouthCrutches2, northWestToSouthCane, northWestToSouthDog, northWestToSouthMobilityScooter,
										northWestToSouthWheelchairAssisted, northWestToSouthWheelchairManual, northWestToSouthWheelchairPowered,
										northWestToSouthPushChair, northWestToSouthSkateboard, northWestToSouthManualScooter, generalComments);
							}else if(x == 0 && y == 7){
								//North-west to South-East
								appendCountables(fileWriter, northWestToSouthEastCar, northWestToSouthEastBus, northWestToSouthEastTruck,
										northWestToSouthEastMotorBike, northWestToSouthEastPedestrian, northWestToSouthEastCrutches1,
										northWestToSouthEastCrutches2, northWestToSouthEastCane, northWestToSouthEastDog, northWestToSouthEastMobilityScooter,
										northWestToSouthEastWheelchairAssisted, northWestToSouthEastWheelchairManual, northWestToSouthEastWheelchairPowered,
										northWestToSouthEastPushChair, northWestToSouthEastSkateboard, northWestToSouthEastManualScooter, generalComments);
							}
							
							//FROM NORTH TO ...
							else if(x == 1 && y == 0){
								//North to North-West
								appendCountables(fileWriter, northToNorthWestCar, northToNorthWestBus, northToNorthWestTruck,
										northToNorthWestMotorBike, northToNorthWestPedestrian, northToNorthWestCrutches1,
										northToNorthWestCrutches2, northToNorthWestCane, northToNorthWestDog, northToNorthWestMobilityScooter,
										northToNorthWestWheelchairAssisted, northToNorthWestWheelchairManual, northToNorthWestWheelchairPowered,
										northToNorthWestPushChair, northToNorthWestSkateboard, northToNorthWestManualScooter, generalComments);
							}else if(x == 1 && y == 2){
								//North to North-East
								appendCountables(fileWriter, northToNorthEastCar, northToNorthEastBus, northToNorthEastTruck,
										northToNorthEastMotorBike, northToNorthEastPedestrian, northToNorthEastCrutches1,
										northToNorthEastCrutches2, northToNorthEastCane, northToNorthEastDog, northToNorthEastMobilityScooter,
										northToNorthEastWheelchairAssisted, northToNorthEastWheelchairManual, northToNorthEastWheelchairPowered,
										northToNorthEastPushChair, northToNorthEastSkateboard, northToNorthEastManualScooter, generalComments);
							}else if(x == 1 && y == 3){
								//North to West
								appendCountables(fileWriter, northToWestCar, northToWestBus, northToWestTruck,
										northToWestMotorBike, northToWestPedestrian, northToWestCrutches1,
										northToWestCrutches2, northToWestCane, northToWestDog, northToWestMobilityScooter,
										northToWestWheelchairAssisted, northToWestWheelchairManual, northToWestWheelchairPowered,
										northToWestPushChair, northToWestSkateboard, northToWestManualScooter, generalComments);
							}else if(x == 1 && y == 4){
								//North to East
								appendCountables(fileWriter, northToEastCar, northToEastBus, northToEastTruck,
										northToEastMotorBike, northToEastPedestrian, northToEastCrutches1,
										northToEastCrutches2, northToEastCane, northToEastDog, northToEastMobilityScooter,
										northToEastWheelchairAssisted, northToEastWheelchairManual, northToEastWheelchairPowered,
										northToEastPushChair, northToEastSkateboard, northToEastManualScooter, generalComments);
								
							}else if(x == 1 && y == 5){
								//North to South-West
								appendCountables(fileWriter, northToSouthWestCar, northToSouthWestBus, northToSouthWestTruck,
										northToSouthWestMotorBike, northToSouthWestPedestrian, northToSouthWestCrutches1,
										northToSouthWestCrutches2, northToSouthWestCane, northToSouthWestDog, northToSouthWestMobilityScooter,
										northToSouthWestWheelchairAssisted, northToSouthWestWheelchairManual, northToSouthWestWheelchairPowered,
										northToSouthWestPushChair, northToSouthWestSkateboard, northToSouthWestManualScooter, generalComments);
								
							}else if(x == 1 && y == 6){
								//North to South
								appendCountables(fileWriter, northToSouthCar, northToSouthBus, northToSouthTruck,
										northToSouthMotorBike, northToSouthPedestrian, northToSouthCrutches1,
										northToSouthCrutches2, northToSouthCane, northToSouthDog, northToSouthMobilityScooter,
										northToSouthWheelchairAssisted, northToSouthWheelchairManual, northToSouthWheelchairPowered,
										northToSouthPushChair, northToSouthSkateboard, northToSouthManualScooter, generalComments);
								
							}else if(x == 1 && y == 7){
								//North to South-East
								appendCountables(fileWriter, northToSouthEastCar, northToSouthEastBus, northToSouthEastTruck,
										northToSouthEastMotorBike, northToSouthEastPedestrian, northToSouthEastCrutches1,
										northToSouthEastCrutches2, northToSouthEastCane, northToSouthEastDog, northToSouthEastMobilityScooter,
										northToSouthEastWheelchairAssisted, northToSouthEastWheelchairManual, northToSouthEastWheelchairPowered,
										northToSouthEastPushChair, northToSouthEastSkateboard, northToSouthEastManualScooter, generalComments);
								
							}
							
							//FROM NORTH-EAST TO ...
							else if(x == 2 && y == 0){
								//North-East to North-West
								appendCountables(fileWriter, northEastToNorthWestCar, northEastToNorthWestBus, northEastToNorthWestTruck,
										northEastToNorthWestMotorBike, northEastToNorthWestPedestrian, northEastToNorthWestCrutches1,
										northEastToNorthWestCrutches2, northEastToNorthWestCane, northEastToNorthWestDog, northEastToNorthWestMobilityScooter,
										northEastToNorthWestWheelchairAssisted, northEastToNorthWestWheelchairManual, northEastToNorthWestWheelchairPowered,
										northEastToNorthWestPushChair, northEastToNorthWestSkateboard, northEastToNorthWestManualScooter, generalComments);
								
							}else if(x == 2 && y == 1){
								//North-East to North
								appendCountables(fileWriter, northEastToNorthCar, northEastToNorthBus, northEastToNorthTruck,
										northEastToNorthMotorBike, northEastToNorthPedestrian, northEastToNorthCrutches1,
										northEastToNorthCrutches2, northEastToNorthCane, northEastToNorthDog, northEastToNorthMobilityScooter,
										northEastToNorthWheelchairAssisted, northEastToNorthWheelchairManual, northEastToNorthWheelchairPowered,
										northEastToNorthPushChair, northEastToNorthSkateboard, northEastToNorthManualScooter, generalComments);
								
							}else if(x == 2 && y == 3){
								//North-East to West
								appendCountables(fileWriter, northEastToWestCar, northEastToWestBus, northEastToWestTruck,
										northEastToWestMotorBike, northEastToWestPedestrian, northEastToWestCrutches1,
										northEastToWestCrutches2, northEastToWestCane, northEastToWestDog, northEastToWestMobilityScooter,
										northEastToWestWheelchairAssisted, northEastToWestWheelchairManual, northEastToWestWheelchairPowered,
										northEastToWestPushChair, northEastToWestSkateboard, northEastToWestManualScooter, generalComments);
								
							}else if(x == 2 && y == 4){
								//North-East to East
								appendCountables(fileWriter, northEastToEastCar, northEastToEastBus, northEastToEastTruck,
										northEastToEastMotorBike, northEastToEastPedestrian, northEastToEastCrutches1,
										northEastToEastCrutches2, northEastToEastCane, northEastToEastDog, northEastToEastMobilityScooter,
										northEastToEastWheelchairAssisted, northEastToEastWheelchairManual, northEastToEastWheelchairPowered,
										northEastToEastPushChair, northEastToEastSkateboard, northEastToEastManualScooter, generalComments);
								
							}else if(x == 2 && y == 5){
								//North-East to South-West
								appendCountables(fileWriter, northEastToSouthWestCar, northEastToSouthWestBus, northEastToSouthWestTruck,
										northEastToSouthWestMotorBike, northEastToSouthWestPedestrian, northEastToSouthWestCrutches1,
										northEastToSouthWestCrutches2, northEastToSouthWestCane, northEastToSouthWestDog, northEastToSouthWestMobilityScooter,
										northEastToSouthWestWheelchairAssisted, northEastToSouthWestWheelchairManual, northEastToSouthWestWheelchairPowered,
										northEastToSouthWestPushChair, northEastToSouthWestSkateboard, northEastToSouthWestManualScooter, generalComments);
								
							}else if(x == 2 && y == 6){
								//North-East to South
								appendCountables(fileWriter, northEastToSouthCar, northEastToSouthBus, northEastToSouthTruck,
										northEastToSouthMotorBike, northEastToSouthPedestrian, northEastToSouthCrutches1,
										northEastToSouthCrutches2, northEastToSouthCane, northEastToSouthDog, northEastToSouthMobilityScooter,
										northEastToSouthWheelchairAssisted, northEastToSouthWheelchairManual, northEastToSouthWheelchairPowered,
										northEastToSouthPushChair, northEastToSouthSkateboard, northEastToSouthManualScooter, generalComments);
								
							}else if(x == 2 && y == 7){
								//North-East to South-East
								appendCountables(fileWriter, northEastToSouthEastCar, northEastToSouthEastBus, northEastToSouthEastTruck,
										northEastToSouthEastMotorBike, northEastToSouthEastPedestrian, northEastToSouthEastCrutches1,
										northEastToSouthEastCrutches2, northEastToSouthEastCane, northEastToSouthEastDog, northEastToSouthEastMobilityScooter,
										northEastToSouthEastWheelchairAssisted, northEastToSouthEastWheelchairManual, northEastToSouthEastWheelchairPowered,
										northEastToSouthEastPushChair, northEastToSouthEastSkateboard, northEastToSouthEastManualScooter, generalComments);
								
							}
							
							//FROM West TO ...
							else if(x == 3 && y == 0){
								//West to North-West
								appendCountables(fileWriter, westToNorthWestCar, westToNorthWestBus, westToNorthWestTruck,
										westToNorthWestMotorBike, westToNorthWestPedestrian, westToNorthWestCrutches1,
										westToNorthWestCrutches2, westToNorthWestCane, westToNorthWestDog, westToNorthWestMobilityScooter,
										westToNorthWestWheelchairAssisted, westToNorthWestWheelchairManual, westToNorthWestWheelchairPowered,
										westToNorthWestPushChair, westToNorthWestSkateboard, westToNorthWestManualScooter, generalComments);
							
							}else if(x == 3 && y == 1){
								//West to North
								appendCountables(fileWriter, westToNorthCar, westToNorthBus, westToNorthTruck,
										westToNorthMotorBike, westToNorthPedestrian, westToNorthCrutches1,
										westToNorthCrutches2, westToNorthCane, westToNorthDog, westToNorthMobilityScooter,
										westToNorthWheelchairAssisted, westToNorthWheelchairManual, westToNorthWheelchairPowered,
										westToNorthPushChair, westToNorthSkateboard, westToNorthManualScooter, generalComments);
							
							}else if(x == 3 && y == 2){
								//West to North-East
								appendCountables(fileWriter, westToNorthEastCar, westToNorthEastBus, westToNorthEastTruck,
										westToNorthEastMotorBike, westToNorthEastPedestrian, westToNorthEastCrutches1,
										westToNorthEastCrutches2, westToNorthEastCane, westToNorthEastDog, westToNorthEastMobilityScooter,
										westToNorthEastWheelchairAssisted, westToNorthEastWheelchairManual, westToNorthEastWheelchairPowered,
										westToNorthEastPushChair, westToNorthEastSkateboard, westToNorthEastManualScooter, generalComments);
								
							}else if(x == 3 && y == 4){
								//West to East
								appendCountables(fileWriter, westToEastCar, westToEastBus, westToEastTruck,
										westToEastMotorBike, westToEastPedestrian, westToEastCrutches1,
										westToEastCrutches2, westToEastCane, westToEastDog, westToEastMobilityScooter,
										westToEastWheelchairAssisted, westToEastWheelchairManual, westToEastWheelchairPowered,
										westToEastPushChair, westToEastSkateboard, westToEastManualScooter, generalComments);
							
							}else if(x == 3 && y == 5){
								//West to South-West
								appendCountables(fileWriter, westToSouthWestCar, westToSouthWestBus, westToSouthWestTruck,
										westToSouthWestMotorBike, westToSouthWestPedestrian, westToSouthWestCrutches1,
										westToSouthWestCrutches2, westToSouthWestCane, westToSouthWestDog, westToSouthWestMobilityScooter,
										westToSouthWestWheelchairAssisted, westToSouthWestWheelchairManual, westToSouthWestWheelchairPowered,
										westToSouthWestPushChair, westToSouthWestSkateboard, westToSouthWestManualScooter, generalComments);
							
							}else if(x == 3 && y == 6){
								//West to South
								appendCountables(fileWriter, westToSouthCar, westToSouthBus, westToSouthTruck,
										westToSouthMotorBike, westToSouthPedestrian, westToSouthCrutches1,
										westToSouthCrutches2, westToSouthCane, westToSouthDog, westToSouthMobilityScooter,
										westToSouthWheelchairAssisted, westToSouthWheelchairManual, westToSouthWheelchairPowered,
										westToSouthPushChair, westToSouthSkateboard, westToSouthManualScooter, generalComments);
							
							}else if(x == 3 && y == 7){
								//West to South-East
								appendCountables(fileWriter, westToSouthEastCar, westToSouthEastBus, westToSouthEastTruck,
										westToSouthEastMotorBike, westToSouthEastPedestrian, westToSouthEastCrutches1,
										westToSouthEastCrutches2, westToSouthEastCane, westToSouthEastDog, westToSouthEastMobilityScooter,
										westToSouthEastWheelchairAssisted, westToSouthEastWheelchairManual, westToSouthEastWheelchairPowered,
										westToSouthEastPushChair, westToSouthEastSkateboard, westToSouthEastManualScooter, generalComments);
								
							}
							
							//FROM East TO ...
							else if(x == 4 && y == 0){
								//East to North-West
								appendCountables(fileWriter, eastToNorthWestCar, eastToNorthWestBus, eastToNorthWestTruck,
										eastToNorthWestMotorBike, eastToNorthWestPedestrian, eastToNorthWestCrutches1,
										eastToNorthWestCrutches2, eastToNorthWestCane, eastToNorthWestDog, eastToNorthWestMobilityScooter,
										eastToNorthWestWheelchairAssisted, eastToNorthWestWheelchairManual, eastToNorthWestWheelchairPowered,
										eastToNorthWestPushChair, eastToNorthWestSkateboard, eastToNorthWestManualScooter, generalComments);
								
							}else if(x == 4 && y == 1){
								//East to North
								appendCountables(fileWriter, eastToNorthCar, eastToNorthBus, eastToNorthTruck,
										eastToNorthMotorBike, eastToNorthPedestrian, eastToNorthCrutches1,
										eastToNorthCrutches2, eastToNorthCane, eastToNorthDog, eastToNorthMobilityScooter,
										eastToNorthWheelchairAssisted, eastToNorthWheelchairManual, eastToNorthWheelchairPowered,
										eastToNorthPushChair, eastToNorthSkateboard, eastToNorthManualScooter, generalComments);
								
							}else if(x == 4 && y == 2){
								//East to North-East
								appendCountables(fileWriter, eastToNorthEastCar, eastToNorthEastBus, eastToNorthEastTruck,
										eastToNorthEastMotorBike, eastToNorthEastPedestrian, eastToNorthEastCrutches1,
										eastToNorthEastCrutches2, eastToNorthEastCane, eastToNorthEastDog, eastToNorthEastMobilityScooter,
										eastToNorthEastWheelchairAssisted, eastToNorthEastWheelchairManual, eastToNorthEastWheelchairPowered,
										eastToNorthEastPushChair, eastToNorthEastSkateboard, eastToNorthEastManualScooter, generalComments);
								
							}else if(x == 4 && y == 3){
								//East to West
								appendCountables(fileWriter, eastToWestCar, eastToWestBus, eastToWestTruck,
										eastToWestMotorBike, eastToWestPedestrian, eastToWestCrutches1,
										eastToWestCrutches2, eastToWestCane, eastToWestDog, eastToWestMobilityScooter,
										eastToWestWheelchairAssisted, eastToWestWheelchairManual, eastToWestWheelchairPowered,
										eastToWestPushChair, eastToWestSkateboard, eastToWestManualScooter, generalComments);
								
							}else if(x == 4 && y == 5){
								//East to South-West
								appendCountables(fileWriter, eastToSouthWestCar, eastToSouthWestBus, eastToSouthWestTruck,
										eastToSouthWestMotorBike, eastToSouthWestPedestrian, eastToSouthWestCrutches1,
										eastToSouthWestCrutches2, eastToSouthWestCane, eastToSouthWestDog, eastToSouthWestMobilityScooter,
										eastToSouthWestWheelchairAssisted, eastToSouthWestWheelchairManual, eastToSouthWestWheelchairPowered,
										eastToSouthWestPushChair, eastToSouthWestSkateboard, eastToSouthWestManualScooter, generalComments);
								
							}else if(x == 4 && y == 6){
								//East to South
								appendCountables(fileWriter, eastToSouthCar, eastToSouthBus, eastToSouthTruck,
										eastToSouthMotorBike, eastToSouthPedestrian, eastToSouthCrutches1,
										eastToSouthCrutches2, eastToSouthCane, eastToSouthDog, eastToSouthMobilityScooter,
										eastToSouthWheelchairAssisted, eastToSouthWheelchairManual, eastToSouthWheelchairPowered,
										eastToSouthPushChair, eastToSouthSkateboard, eastToSouthManualScooter, generalComments);
								
							}else if(x == 4 && y == 7){
								//East to South-East
								appendCountables(fileWriter, eastToSouthEastCar, eastToSouthEastBus, eastToSouthEastTruck,
										eastToSouthEastMotorBike, eastToSouthEastPedestrian, eastToSouthEastCrutches1,
										eastToSouthEastCrutches2, eastToSouthEastCane, eastToSouthEastDog, eastToSouthEastMobilityScooter,
										eastToSouthEastWheelchairAssisted, eastToSouthEastWheelchairManual, eastToSouthEastWheelchairPowered,
										eastToSouthEastPushChair, eastToSouthEastSkateboard, eastToSouthEastManualScooter, generalComments);
							
							}
							
							//FROM South-West TO ...
							else if(x == 5 && y == 0){
								//South-West to North-West
								appendCountables(fileWriter, southWestToNorthWestCar, southWestToNorthWestBus, southWestToNorthWestTruck,
										southWestToNorthWestMotorBike, southWestToNorthWestPedestrian, southWestToNorthWestCrutches1,
										southWestToNorthWestCrutches2, southWestToNorthWestCane, southWestToNorthWestDog, southWestToNorthWestMobilityScooter,
										southWestToNorthWestWheelchairAssisted, southWestToNorthWestWheelchairManual, southWestToNorthWestWheelchairPowered,
										southWestToNorthWestPushChair, southWestToNorthWestSkateboard, southWestToNorthWestManualScooter, generalComments);
								
							}else if(x == 5 && y == 1){
								//South-West to North
								appendCountables(fileWriter, southWestToNorthCar, southWestToNorthBus, southWestToNorthTruck,
										southWestToNorthMotorBike, southWestToNorthPedestrian, southWestToNorthCrutches1,
										southWestToNorthCrutches2, southWestToNorthCane, southWestToNorthDog, southWestToNorthMobilityScooter,
										southWestToNorthWheelchairAssisted, southWestToNorthWheelchairManual, southWestToNorthWheelchairPowered,
										southWestToNorthPushChair, southWestToNorthSkateboard, southWestToNorthManualScooter, generalComments);
								
							}else if(x == 5 && y == 2){
								//South-West to North-East
								appendCountables(fileWriter, southWestToNorthEastCar, southWestToNorthEastBus, southWestToNorthEastTruck,
										southWestToNorthEastMotorBike, southWestToNorthEastPedestrian, southWestToNorthEastCrutches1,
										southWestToNorthEastCrutches2, southWestToNorthEastCane, southWestToNorthEastDog, southWestToNorthEastMobilityScooter,
										southWestToNorthEastWheelchairAssisted, southWestToNorthEastWheelchairManual, southWestToNorthEastWheelchairPowered,
										southWestToNorthEastPushChair, southWestToNorthEastSkateboard, southWestToNorthEastManualScooter, generalComments);
							
							}else if(x == 5 && y == 3){
								//South-West to West
								appendCountables(fileWriter, southWestToWestCar, southWestToWestBus, southWestToWestTruck,
										southWestToWestMotorBike, southWestToWestPedestrian, southWestToWestCrutches1,
										southWestToWestCrutches2, southWestToWestCane, southWestToWestDog, southWestToWestMobilityScooter,
										southWestToWestWheelchairAssisted, southWestToWestWheelchairManual, southWestToWestWheelchairPowered,
										southWestToWestPushChair, southWestToWestSkateboard, southWestToWestManualScooter, generalComments);
							
							}else if(x == 5 && y == 4){
								//South-West to East
								appendCountables(fileWriter, southWestToEastCar, southWestToEastBus, southWestToEastTruck,
										southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
										southWestToEastCrutches2, southWestToEastCane, southWestToEastDog, southWestToEastMobilityScooter,
										southWestToEastWheelchairAssisted, southWestToEastWheelchairManual, southWestToEastWheelchairPowered,
										southWestToEastPushChair, southWestToEastSkateboard, southWestToEastManualScooter, generalComments);
								
							}else if(x == 5 && y == 6){
								//South-West to South
								appendCountables(fileWriter, southWestToSouthCar, southWestToSouthBus, southWestToSouthTruck,
										southWestToSouthMotorBike, southWestToSouthPedestrian, southWestToSouthCrutches1,
										southWestToSouthCrutches2, southWestToSouthCane, southWestToSouthDog, southWestToSouthMobilityScooter,
										southWestToSouthWheelchairAssisted, southWestToSouthWheelchairManual, southWestToSouthWheelchairPowered,
										southWestToSouthPushChair, southWestToSouthSkateboard, southWestToSouthManualScooter, generalComments);
								
							}else if(x == 5 && y == 7){
								//South-West to South-East
								appendCountables(fileWriter, southWestToSouthEastCar, southWestToSouthEastBus, southWestToSouthEastTruck,
										southWestToSouthEastMotorBike, southWestToSouthEastPedestrian, southWestToSouthEastCrutches1,
										southWestToSouthEastCrutches2, southWestToSouthEastCane, southWestToSouthEastDog, southWestToSouthEastMobilityScooter,
										southWestToSouthEastWheelchairAssisted, southWestToSouthEastWheelchairManual, southWestToSouthEastWheelchairPowered,
										southWestToSouthEastPushChair, southWestToSouthEastSkateboard, southWestToSouthEastManualScooter, generalComments);
								
							}
							
							//FROM South TO ...
							else if(x == 6 && y == 0){
								//South to North-West
								appendCountables(fileWriter, southToNorthWestCar, southToNorthWestBus, southToNorthWestTruck,
										southToNorthWestMotorBike, southToNorthWestPedestrian, southToNorthWestCrutches1,
										southToNorthWestCrutches2, southToNorthWestCane, southToNorthWestDog, southToNorthWestMobilityScooter,
										southToNorthWestWheelchairAssisted, southToNorthWestWheelchairManual, southToNorthWestWheelchairPowered,
										southToNorthWestPushChair, southToNorthWestSkateboard, southToNorthWestManualScooter, generalComments);
								
							}else if(x == 6 && y == 1){
								//South to North
								appendCountables(fileWriter, southToNorthCar, southToNorthBus, southToNorthTruck,
										southToNorthMotorBike, southToNorthPedestrian, southToNorthCrutches1,
										southToNorthCrutches2, southToNorthCane, southToNorthDog, southToNorthMobilityScooter,
										southToNorthWheelchairAssisted, southToNorthWheelchairManual, southToNorthWheelchairPowered,
										southToNorthPushChair, southToNorthSkateboard, southToNorthManualScooter, generalComments);
								
							}else if(x == 6 && y == 2){
								//South to North-East
								appendCountables(fileWriter, southToNorthEastCar, southToNorthEastBus, southToNorthEastTruck,
										southToNorthEastMotorBike, southToNorthEastPedestrian, southToNorthEastCrutches1,
										southToNorthEastCrutches2, southToNorthEastCane, southToNorthEastDog, southToNorthEastMobilityScooter,
										southToNorthEastWheelchairAssisted, southToNorthEastWheelchairManual, southToNorthEastWheelchairPowered,
										southToNorthEastPushChair, southToNorthEastSkateboard, southToNorthEastManualScooter, generalComments);
								
							}else if(x == 6 && y == 3){
								//South to West
								appendCountables(fileWriter, southToWestCar, southToWestBus, southToWestTruck,
										southToWestMotorBike, southToWestPedestrian, southToWestCrutches1,
										southToWestCrutches2, southToWestCane, southToWestDog, southToWestMobilityScooter,
										southToWestWheelchairAssisted, southToWestWheelchairManual, southToWestWheelchairPowered,
										southToWestPushChair, southToWestSkateboard, southToWestManualScooter, generalComments);
								
							}else if(x == 6 && y == 4){
								//South to East
								appendCountables(fileWriter, southToEastCar, southToEastBus, southToEastTruck,
										southToEastMotorBike, southToEastPedestrian, southToEastCrutches1,
										southToEastCrutches2, southToEastCane, southToEastDog, southToEastMobilityScooter,
										southToEastWheelchairAssisted, southToEastWheelchairManual, southToEastWheelchairPowered,
										southToEastPushChair, southToEastSkateboard, southToEastManualScooter, generalComments);
								
							}else if(x == 6 && y == 5){
								//South to South-West
								appendCountables(fileWriter, southToSouthWestCar, southToSouthWestBus, southToSouthWestTruck,
										southToSouthWestMotorBike, southToSouthWestPedestrian, southToSouthWestCrutches1,
										southToSouthWestCrutches2, southToSouthWestCane, southToSouthWestDog, southToSouthWestMobilityScooter,
										southToSouthWestWheelchairAssisted, southToSouthWestWheelchairManual, southToSouthWestWheelchairPowered,
										southToSouthWestPushChair, southToSouthWestSkateboard, southToSouthWestManualScooter, generalComments);
								
							}else if(x == 6 && y == 7){
								//South to South-East
								appendCountables(fileWriter, southToSouthEastCar, southToSouthEastBus, southToSouthEastTruck,
										southToSouthEastMotorBike, southToSouthEastPedestrian, southToSouthEastCrutches1,
										southToSouthEastCrutches2, southToSouthEastCane, southToSouthEastDog, southToSouthEastMobilityScooter,
										southToSouthEastWheelchairAssisted, southToSouthEastWheelchairManual, southToSouthEastWheelchairPowered,
										southToSouthEastPushChair, southToSouthEastSkateboard, southToSouthEastManualScooter, generalComments);
								
							}
							
							//FROM South-East TO ...
							else if(x == 7 && y == 0){
								//South-East to North-West
								appendCountables(fileWriter, southEastToNorthWestCar, southEastToNorthWestBus, southEastToNorthWestTruck,
										southEastToNorthWestMotorBike, southEastToNorthWestPedestrian, southEastToNorthWestCrutches1,
										southEastToNorthWestCrutches2, southEastToNorthWestCane, southEastToNorthWestDog, southEastToNorthWestMobilityScooter,
										southEastToNorthWestWheelchairAssisted, southEastToNorthWestWheelchairManual, southEastToNorthWestWheelchairPowered,
										southEastToNorthWestPushChair, southEastToNorthWestSkateboard, southEastToNorthWestManualScooter, generalComments);
								
							}else if(x == 7 && y == 1){
								//South-East to North
								appendCountables(fileWriter, southEastToNorthCar, southEastToNorthBus, southEastToNorthTruck,
										southEastToNorthMotorBike, southEastToNorthPedestrian, southEastToNorthCrutches1,
										southEastToNorthCrutches2, southEastToNorthCane, southEastToNorthDog, southEastToNorthMobilityScooter,
										southEastToNorthWheelchairAssisted, southEastToNorthWheelchairManual, southEastToNorthWheelchairPowered,
										southEastToNorthPushChair, southEastToNorthSkateboard, southEastToNorthManualScooter, generalComments);
								
							}else if(x == 7 && y == 2){
								//South-East to North-East
								appendCountables(fileWriter, southEastToNorthEastCar, southEastToNorthEastBus, southEastToNorthEastTruck,
										southEastToNorthEastMotorBike, southEastToNorthEastPedestrian, southEastToNorthEastCrutches1,
										southEastToNorthEastCrutches2, southEastToNorthEastCane, southEastToNorthEastDog, southEastToNorthEastMobilityScooter,
										southEastToNorthEastWheelchairAssisted, southEastToNorthEastWheelchairManual, southEastToNorthEastWheelchairPowered,
										southEastToNorthEastPushChair, southEastToNorthEastSkateboard, southEastToNorthEastManualScooter, generalComments);
								
							}else if(x == 7 && y == 3){
								//South-East to West
								appendCountables(fileWriter, southEastToWestCar, southEastToWestBus, southEastToWestTruck,
										southEastToWestMotorBike, southEastToWestPedestrian, southEastToWestCrutches1,
										southEastToWestCrutches2, southEastToWestCane, southEastToWestDog, southEastToWestMobilityScooter,
										southEastToWestWheelchairAssisted, southEastToWestWheelchairManual, southEastToWestWheelchairPowered,
										southEastToWestPushChair, southEastToWestSkateboard, southEastToWestManualScooter, generalComments);
							
							}else if(x == 7 && y == 4){
								//South-East to East
								appendCountables(fileWriter, southEastToEastCar, southEastToEastBus, southEastToEastTruck,
										southWestToEastMotorBike, southWestToEastPedestrian, southWestToEastCrutches1,
										southEastToEastCrutches2, southEastToEastCane, southEastToEastDog, southEastToEastMobilityScooter,
										southEastToEastWheelchairAssisted, southEastToEastWheelchairManual, southEastToEastWheelchairPowered,
										southEastToEastPushChair, southEastToEastSkateboard, southEastToEastManualScooter, generalComments);
								
							}else if(x == 7 && y == 5){
								//South-East to South-West
								appendCountables(fileWriter, southEastToSouthWestCar, southEastToSouthWestBus, southEastToSouthWestTruck,
										southEastToSouthWestMotorBike, southEastToSouthWestPedestrian, southEastToSouthWestCrutches1,
										southEastToSouthWestCrutches2, southEastToSouthWestCane, southEastToSouthWestDog, southEastToSouthWestMobilityScooter,
										southEastToSouthWestWheelchairAssisted, southEastToSouthWestWheelchairManual, southEastToSouthWestWheelchairPowered,
										southEastToSouthWestPushChair, southEastToSouthWestSkateboard, southEastToSouthWestManualScooter, generalComments);
							
							}else if(x == 7 && y == 6){
								//South-East to South
								appendCountables(fileWriter, southEastToSouthCar, southEastToSouthBus, southEastToSouthTruck,
										southEastToSouthMotorBike, southEastToSouthPedestrian, southEastToSouthCrutches1,
										southEastToSouthCrutches2, southEastToSouthCane, southEastToSouthDog, southEastToSouthMobilityScooter,
										southEastToSouthWheelchairAssisted, southEastToSouthWheelchairManual, southEastToSouthWheelchairPowered,
										southEastToSouthPushChair, southEastToSouthSkateboard, southEastToSouthManualScooter, generalComments);
							
							}
						}
					}
				}
			}if(dataRowsWritten >= 1){
				fileWriter.append("\n");
				userMessage("Data saved successfully");
			}
		}
		
		/*
		 * Append details about the location the count is taking place, ONCE.
		 * @author: Jean-Yves
		 * @since: April 20 2014
		 */
		private void appendLocationHeader(FileWriter fileWriter) throws IOException {
			//These should work, once we make sure the submit button sets the values from Textviews
			String surveyorName = CountSetup.getsName();
			if(surveyorName == null){
				surveyorName = "Nil";
			}
			
			String streetNumAndName = CountSetup.getStreetNumAndName();
			if(streetNumAndName == null){
				streetNumAndName = "Nil";
			}
			
			String currentDate = CountSetup.getCurrentDate();
			if(currentDate == null){
				currentDate = "Nil";
			}
			
			String suburbName = CountSetup.getSuburbName();
			if(suburbName == null){
				suburbName = "Nil";
			}
			
			String city = CountSetup.getCityName();
			if(city == null){
				city = "Nil";
			}
			
			String postCode = CountSetup.getAreaCode();
			if(postCode == null){
				postCode = "Nil";
			}
			
			String locDescription = CountSetup.getAreaDescript();
			if(locDescription == null){
				locDescription = "Nil";
			}
			
			String weatherInfo = CountSetup.getWeatherCommentSection();
			if(weatherInfo == null){
				weatherInfo = "Nil";
			}
			
			if(!locationHeaderAppended){
				fileWriter.append("LOCATION:\n" +
						", Street number and/or Name: "+streetNumAndName + "\n" +
						", Suburb: " + suburbName + "\n" +
						", City: " + city + "\n" +
						", Post code: " + postCode + "\n" +
						", Description: " + locDescription +"\n \n");
				fileWriter.append("Intersection Type: " + intersectionType + "\n");				
				fileWriter.append("Weather Comment: " + weatherInfo + "\n\n");
				fileWriter.append("Surveyor's name: " + surveyorName + ", , Date: " + currentDate + "\n \n");
				locationHeaderAppended = true;
			}
		}
		
		/*
		 * Append the different To and From positions
		 * @author: Jean-Yves
		 * @since: May 2 2014
		 */
		private void appendFromAndToPostionsHeader(FileWriter fWriter) throws IOException {
			for (int f = 0; f < 8; f++) {
				if(intersectionsPicked[f]){
					for (int t = 0; t < 8; t++) {
						if(intersectionsPicked[t] && t != f){
							if(!endOfFromAndToPositionsHeader){
								//FROM NORTH-WEST TO ...
								if(f == 0 && t == 1){
									//DIRECTION: North-west To North
									fWriter.append("From North-West to North , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 0 && t == 2){
									//North-west to North-East
									fWriter.append("From North-West to North-East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 0 && t == 3){
									//North-west to West
									fWriter.append("From North-West to West , , , , , , , , , , , , , , , , , , ,");									
								}else if(f == 0 && t == 4){
									//North-west to East
									fWriter.append("From North-West to East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 0 && t == 5){
									//North-west to South-West
									fWriter.append("From North-West to South-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 0 && t == 6){
									//North-west to South
									fWriter.append("From North-West to South , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 0 && t == 7){
									//North-west to South-East
									fWriter.append("From North-West to South-East , , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM NORTH TO ...
								else if(f == 1 && t == 0){
									//North to North-West
									fWriter.append("From North to North-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 2){
									//North to North-East
									fWriter.append("From North to North-East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 3){
									//North to West
									fWriter.append("From North to West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 4){
									//North to East
									fWriter.append("From North to East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 5){
									//North to South-West
									fWriter.append("From North to South-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 6){
									//North to South
									fWriter.append("From North to South , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 1 && t == 7){
									//North to South-East
									fWriter.append("From North to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM NORTH-EAST TO ...
								else if(f == 2 && t == 0){
									//North-East to North-West
									fWriter.append("From North-East to North-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 1){
									//North-East to North
									fWriter.append("From North-East to North , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 3){
									//North-East to West
									fWriter.append("From North-East to West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 4){
									//North-East to East
									fWriter.append("From North-East to East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 5){
									//North-East to South-West
									fWriter.append("From North-East to South-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 6){
									//North-East to South
									fWriter.append("From North-East to South , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 2 && t == 7){
									//North-East to South-East
									fWriter.append("From North-East to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM West TO ...
								else if(f == 3 && t == 0){
									//West to North-West
									fWriter.append("From West to North-West , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 3 && t == 1){
									//West to North
									fWriter.append("From West to North , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 3 && t == 2){
									//West to North-East
									fWriter.append("From West to North-East , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 3 && t == 4){
									//West to East
									fWriter.append("From West to East , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 3 && t == 5){
									//West to South-West
									fWriter.append("From West to South-West , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 3 && t == 6){
									//West to South
									fWriter.append("From West to South , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 3 && t == 7){
									//West to South-East
									fWriter.append("From West to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM East TO ...
								else if(f == 4 && t == 0){
									//East to North-West
									fWriter.append("From East to North-West , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 4 && t == 1){
									//East to North
									fWriter.append("From East to North , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 4 && t == 2){
									//East to North-East
									fWriter.append("From East to North-East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 4 && t == 3){
									//East to West
									fWriter.append("From East to West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 4 && t == 5){
									//East to South-West
									fWriter.append("From East to South-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 4 && t == 6){
									//East to South
									fWriter.append("From East to South , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 4 && t == 7){
									//East to South-East
									fWriter.append("From East to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM South-West TO ...
								else if(f == 5 && t == 0){
									//South-West to North-West
									fWriter.append("From South-West to North-West , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 5 && t == 1){
									//South-West to North
									fWriter.append("From South-West to North , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 5 && t == 2){
									//South-West to North-East
									fWriter.append("From South-West to North-East , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 5 && t == 3){
									//South-West to West
									fWriter.append("From South-West to West , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 5 && t == 4){
									//South-West to East
									fWriter.append("From South-West to East , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 5 && t == 6){
									//South-West to South
									fWriter.append("From South-West to South , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 5 && t == 7){
									//South-West to South-East
									fWriter.append("From South-West to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM South TO ...
								else if(f == 6 && t == 0){
									//South to North-West
									fWriter.append("From South to North-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 6 && t == 1){
									//South to North
									fWriter.append("From South to North , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 6 && t == 2){
									//South to North-East
									fWriter.append("From South to North-East , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 6 && t == 3){
									//South to West
									fWriter.append("From South to South-West , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 6 && t == 4){
									//South to East
									fWriter.append("From South to East , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 6 && t == 5){
									//South to South-West
									fWriter.append("From South to South-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 6 && t == 7){
									//South to South-East
									fWriter.append("From South to South-East , , , , , , , , , , , , , , , , , , ,");
								}
								
								//FROM South-East TO ...
								else if(f == 7 && t == 0){
									//South-East to North-West
									fWriter.append("From South-East to North-West , , , , , , , , , , , , , , , , , , ,");
								}else if(f == 7 && t == 1){
									//South-East to North
									fWriter.append("From South-East to North , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 7 && t == 2){
									//South-East to North-East
									fWriter.append("From South-East to North-East , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 7 && t == 3){
									//South-East to West
									fWriter.append("From South-East to West , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 7 && t == 4){
									//South-East to East
									fWriter.append("From South-East to East , , , , , , , , , , , , , , , , , , ,");
									
								}else if(f == 7 && t == 5){
									//South-East to South-West
									fWriter.append("From South-East to South-West , , , , , , , , , , , , , , , , , , ,");
								
								}else if(f == 7 && t == 6){
									//South-East to South
									fWriter.append("From South-East to South , , , , , , , , , , , , , , , , , , , ,");
								}
							}							
						}
					}
				}
			}if(!endOfFromAndToPositionsHeader){
				fWriter.append("\n \n");
				endOfFromAndToPositionsHeader = true;
				//userMessage("End of Countables header has been reached!");
			}
		}
		

		/*
		 * Append the re-occurring header labels, cars, buses, trucks, etc
		 * @auhor: Jean-Yves
		 * @since: May 3 2015
		 */
		private void appendIntersectionCountsHeader(FileWriter fWriter) throws IOException {
			for (int f = 0; f < 8; f++) {
				if(intersectionsPicked[f]){
					for (int t = 0; t < 8; t++) {
						if(intersectionsPicked[t] && t != f){
							if(!endOfIntersectionCountsHeader){
								fWriter.append("TIME, CARS, BUSES, TRUCKS, MOTORBIKES, PEDESTRIANS(No Aid), WALKING STICKS- CRUTCH(1), WALKING STICKS- CRUTCH(2)," +
										"CANE(poor eyesight), GUIDE DOG, MOBILITY SCOOTER, WHEELCHAIR (assisted), WHEELCHAIR (manual), WHEELCHAIR (powered)," +
										"PUSH CHAIR/BUGGY, SKATEBOARD, MANUAL SCOOTER, COMMENTS, ,");
								//userMessage("Countables header successfully written");
							}
						}
					}
				}
			}if(!endOfIntersectionCountsHeader){
				fWriter.append("\n");
				endOfIntersectionCountsHeader = true;
				//userMessage("End of Countables header has been reached!");
			}
		}
		
		/*
		 * This method will append specific data about specific intersections as instructed. e.g:
		 * Data of Cars From South to North.
		 * @auhor: Jean-Yves
		 * @since: May 3 2015
		 */
		private void appendCountables(FileWriter writer, int cars, int buses, int trucks, int motorcycles, int pedestrian,
										int wsCrutchOne, int wsCrutchTwo, int cane, int guideDog, int mobilityScooter, int wheelchairA,
										int wheelchairM, int wheelchairP, int pcBuggy, int skateboard, int manualScooter, String comments) throws IOException{
			
			if(generalComments == null){
				comments = " ";
			}
			
			String endTime;
			Calendar cal = Calendar.getInstance();
			endTime = df.format(cal.getTime());
			
			writer.append(startTime + " TO " + endTime + ",");
			writer.append(cars+","+buses+","+trucks+","+motorcycles+","+pedestrian+","+wsCrutchOne+","+wsCrutchTwo+","+cane+","+guideDog+","+mobilityScooter+","+ 
					 wheelchairA+","+wheelchairM+","+wheelchairP+","+pcBuggy+","+skateboard+","+manualScooter+","+comments+", ,");
		}
		
		/* To flush and subsequently close the opened file writer. -Jean-Yves
		 *@auhor: Jean-Yves
		 *@since: April 3 2015
		 */
		
		public void flushAndCloseWriter(FileWriter fWriter) throws IOException{
			fWriter.flush();
			fWriter.close();
			startTime = df.format(Calendar.getInstance().getTime());
		}
		
		/* Display a message to the user.
		 *@auhor: Jean-Yves
		 *@since: April 6 2015
		 */
		public void userMessage(String message){
			Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();
		}

		@Override
		public String toString() {
			return ms;
		}
	}
}
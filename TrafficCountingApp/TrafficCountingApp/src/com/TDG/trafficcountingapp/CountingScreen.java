package com.TDG.trafficcountingapp;

import java.util.concurrent.TimeUnit;

import com.TDG.trafficcountingapp.Custom_Dialogs.Communicator;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	
	//Testing purposes
	Button btn_start, btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
		
		populateButtons();
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
				totalCount--;
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
	 * Launches the Custom_Dialogs class and sends the reference "vehicleDialog"
	 * to that class to let that class know where it was called from. Then it
	 * displays the dialog which is handled in Custom_Dialogs.
	 */
	@SuppressLint("NewApi")
	public void showVehicleDialog(View view) {
		FragmentManager manager = getFragmentManager();
		Custom_Dialogs dialog = new Custom_Dialogs();
		dialog.show(manager, "vehicleDialog");
	}

	/*
	 * Launches the Custom_Dialogs class and sends the reference
	 * "pedestrianDialog" to that class to let that class know where it was
	 * called from. Then it displays the dialog which is handled in
	 * Custom_Dialogs.
	 */
	@SuppressLint("NewApi")
	public void showPedestrianDialog(View view) {
		FragmentManager manager = getFragmentManager();
		Custom_Dialogs dialog = new Custom_Dialogs();
		dialog.show(manager, "pedestrianDialog");
	}

	/*
	 * This method is from Custom_Dialogs' communicator interface. The message
	 * returned is the message which is sent from Custom_Dialogs. This allows us
	 * to know which button was clicked so we can make appropriate action in the
	 * CountingScreen class.
	 */
	@Override
	public void sendClickMessage(String message) {

		// Checks which button was clicked by comparing the message sent from
		// Custom_Dialogs to this class.
		if (message.equals("Bus")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Truck")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Car")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Motor Bike")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Pedestrian")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Bike")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Cane")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Dog")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Mobility Scooter")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Artificial Limb")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Crutches")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Walking Frame")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Back Brace - Visible")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Back Brace - Not Visible")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Leg Brace - Visible")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Leg Brace - Not Visible")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Wheel Chair - Assisted")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Wheel Chair - Manual")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else if (message.equals("Wheel Chair - Powered")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}

		// Setting the currently selected object to the object that the user chose.
		txt_currentObject.setText(message);
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

	// /////////////////////////////
	// BELOW ARE UNUSED CODE //
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * Used to populate the ListView
	 * 
	 * private void populateListView(){ ArrayAdapter<CountingObjects> adapter =
	 * new MyListAdapter(); ListView list = (ListView)
	 * findViewById(R.id.cs_listView_CountingTypes); list.setAdapter(adapter); }
	 */

	/*
	 * Used with the ListView to add the values into the ListView list.
	 * 
	 * private void populateObjectList(){ list_countingObjects = new
	 * ArrayList<CountingObjects>(); list_countingObjects.add(new
	 * CountingObjects(R.drawable.heavy_icon, "Heavy Vehicles",
	 * R.drawable.arrow_icon)); list_countingObjects.add(new
	 * CountingObjects(R.drawable.light_icon, "Light Vehicles",
	 * R.drawable.transparent)); list_countingObjects.add(new
	 * CountingObjects(R.drawable.pedestrian_icon, "Pedestrians",
	 * R.drawable.arrow_icon));
	 * 
	 * list_countingObjects_heavy = new ArrayList<CountingObjects>();
	 * list_countingObjects_heavy.add(new CountingObjects(R.drawable.heavy_bus,
	 * "Bus", R.drawable.transparent)); list_countingObjects_heavy.add(new
	 * CountingObjects(R.drawable.heavy_truck, "Truck",
	 * R.drawable.transparent));
	 * 
	 * // list_countingObjects_light = new ArrayList<CountingObjects>(); //
	 * list_countingObjects_light.add(new CountingObjects(R.drawable.heavy_icon,
	 * "Heavy Vehicles", R.drawable.arrow_icon)); //
	 * list_countingObjects_light.add(new CountingObjects(R.drawable.light_icon,
	 * "Light Vehicles", R.drawable.arrow_icon)); //
	 * list_countingObjects_light.add(new
	 * CountingObjects(R.drawable.pedestrian_icon, "Pedestrians",
	 * R.drawable.arrow_icon)); // // list_countingObjects_pedestrian = new
	 * ArrayList<CountingObjects>(); // list_countingObjects_pedestrian.add(new
	 * CountingObjects(R.drawable.heavy_icon, "Heavy Vehicles",
	 * R.drawable.arrow_icon)); // list_countingObjects_pedestrian.add(new
	 * CountingObjects(R.drawable.light_icon, "Light Vehicles",
	 * R.drawable.arrow_icon)); // list_countingObjects_pedestrian.add(new
	 * CountingObjects(R.drawable.pedestrian_icon, "Pedestrians",
	 * R.drawable.arrow_icon)); }
	 */

	/*
	 * This is the custom ListView Class
	 * 
	 * private class MyListAdapter extends ArrayAdapter<CountingObjects>{ public
	 * MyListAdapter(){
	 * super(CountingScreen.this,R.layout.counting_objects_view,
	 * list_countingObjects); }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { View objectView = convertView; if(convertView == null){
	 * objectView = getLayoutInflater().inflate(R.layout.counting_objects_view,
	 * parent, false); }
	 * 
	 * CountingObjects currentObject = list_countingObjects.get(position);
	 * 
	 * ImageView imageView=
	 * (ImageView)objectView.findViewById(R.id.cov_image_objectIcon);
	 * imageView.setImageResource(currentObject.getIconID()); imageView=
	 * (ImageView)objectView.findViewById(R.id.cov_image_arrow);
	 * imageView.setImageResource(currentObject.getArrowIcon());
	 * 
	 * return objectView; } }
	 */

	// /**
	// * A placeholder fragment containing a simple view.
	// */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_counting_screen,
	// container, false);
	// return rootView;
	// }
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.counting_screen, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	// This goes under onCreate
	// if (savedInstanceState == null) {
	// getSupportFragmentManager().beginTransaction()
	// .add(R.id.container, new PlaceholderFragment()).commit();
	// }
	//
	// TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
	// tabHost.setup();
	//
	// TabSpec tabSpec = tabHost.newTabSpec("heavy vehicles");
	// tabSpec.setContent(R.id.tabHeavyVehicles);
	// tabSpec.setIndicator("Heavy Vehicles");
	// tabHost.addTab(tabSpec);
	//
	// tabSpec = tabHost.newTabSpec("light vehicles");
	// tabSpec.setContent(R.id.tabLightVehicles);
	// tabSpec.setIndicator("Light Vehicles");
	// tabHost.addTab(tabSpec);
	//
	// tabSpec = tabHost.newTabSpec("pedestrians");
	// tabSpec.setContent(R.id.tabPedestrians);
	// tabSpec.setIndicator("Pedestrians");
	// tabHost.addTab(tabSpec);

}

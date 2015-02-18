package com.TDG.trafficcountingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "InflateParams" })
public class CustomDialogs extends DialogFragment implements View.OnClickListener{
	
	Button btn_close;
	Button csc_btn_submit;
	Button csda_btn_submit;
	
	boolean csi_intersections = false;
	boolean csd_countObjects = false;
	boolean csd_countObjects_vehicles = false;
	boolean csda_date = false;
	boolean csc_comments = false;
	
	Button csi_btn_intersection3,csi_btn_intersection4,
			csi_btn_intersection5,csi_btn_intersection6;
	
	Button csd_btn_bus, csd_btn_truck, csd_btn_car, csd_btn_motorBike , csd_btn_pedestrian, csd_btn_bike,
			csd_btn_cane, csd_btn_dog, csd_btn_scooter, csd_btn_artificialLimb, 
			csd_btn_backBrace_visible, csd_btn_backBrace_notVisible,
			csd_btn_crutches, csd_btn_legBrace_visible, csd_btn_legBrace_notVisible,
			csd_btn_walkingFrame, csd_btn_wheelChair_assisted, csd_btn_wheelChair_manual,
			csd_btn_wheelChair_powered, csd_btn_other;
	
	TextView dialogComments;
	String comment;
	
	String date;
	DatePicker datePicker;
	
	Communicator communicator;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		communicator = (Communicator)activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//This creates the view and decides which fragment to show depending on the tags which were given to them in CountingScreen.java.
		View view = null;
		
		switch (getTag()) {
		case "vehicleDialog":
			view = populateVehicleDialog(inflater);
			break;
		case "pedestrianDialog":
			view = populatePedestrianDialog(inflater);
			break;
		case "intersectionDialog":
			view = populateIntersectionDialog(inflater);
			break;
		case "commentsDialog":
			view = populateCommentsDialog(inflater);
			break;
		case "dateDialog":
			view = populateDateDialog(inflater);
			break;
		default:
			break;
		}
		
//		if(getTag().equals("vehicleDialog")){
//			view = populateVehicleDialog(inflater);
//		}else if(getTag().equals("pedestrianDialog")){
//			view = populatePedestrianDialog(inflater);
//		}else if(getTag().equals("intersectionDialog")){
//			view = populateIntersectionDialog(inflater);
//		}else if(getTag().equals("commentsDialog")){
//			view = populateCommentsDialog(inflater);
//		}else if(getTag().equals("dateDialog")){
//			view = populateDateDialog(inflater);
//		}
		
		btn_close.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View view) {
		if(view.getId() == btn_close.getId()){
			dismiss();
		} else if(csi_intersections){
			onClickIntersection(view);
		} else if(csd_countObjects){
			onClickCountObjects(view);
		} else if(csda_date){
			onClickDate(view);
		} else if(csc_comments){
			onClickComments(view);
		} 
	}
	
	////////////////////////////////////////////////////////////
	// Start of Populate methods
	////////////////////////////////////////////////////////////
	
	private View populateVehicleDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_counting_screen_vehicle, null);
		getDialog().setTitle("Vehicles");
		btn_close = (Button)view.findViewById(R.id.csd_btn_close_vehicle);
		csd_countObjects = true;
		csd_countObjects_vehicles = true;
		
		csd_btn_bus = (Button)view.findViewById(R.id.csd_btn_bus);
		csd_btn_bus.setOnClickListener(this);
		csd_btn_truck = (Button)view.findViewById(R.id.csd_btn_truck);
		csd_btn_truck.setOnClickListener(this);
		csd_btn_car = (Button)view.findViewById(R.id.csd_btn_car);
		csd_btn_car.setOnClickListener(this);
		csd_btn_motorBike = (Button)view.findViewById(R.id.csd_btn_motorbike);
		csd_btn_motorBike.setOnClickListener(this);
		
		return view;
	}
	
	private View populatePedestrianDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_counting_screen_pedestrian, null);
		getDialog().setTitle("Pedestrians");
		btn_close = (Button)view.findViewById(R.id.csd_btn_close_pedestrian);
		csd_countObjects = true;
		
		csd_btn_pedestrian = (Button)view.findViewById(R.id.csd_btn_pedestrian);
		csd_btn_pedestrian.setOnClickListener(this);
		csd_btn_bike = (Button)view.findViewById(R.id.csd_btn_bike);
		csd_btn_bike.setOnClickListener(this);
		csd_btn_cane = (Button)view.findViewById(R.id.csd_btn_cane);
		csd_btn_cane.setOnClickListener(this);
		csd_btn_dog = (Button)view.findViewById(R.id.csd_btn_dog);
		csd_btn_dog.setOnClickListener(this);
		csd_btn_scooter = (Button)view.findViewById(R.id.csd_btn_scooter);
		csd_btn_scooter.setOnClickListener(this);
		csd_btn_artificialLimb = (Button)view.findViewById(R.id.csd_btn_artificial_limb); 
		csd_btn_artificialLimb.setOnClickListener(this);
		csd_btn_backBrace_visible = (Button)view.findViewById(R.id.csd_btn_back_visible);
		csd_btn_backBrace_visible.setOnClickListener(this);
		csd_btn_backBrace_notVisible = (Button)view.findViewById(R.id.csd_btn_back_not_visible);
		csd_btn_backBrace_notVisible.setOnClickListener(this);
		csd_btn_crutches = (Button)view.findViewById(R.id.csd_btn_crutches);
		csd_btn_crutches.setOnClickListener(this);
		csd_btn_legBrace_visible = (Button)view.findViewById(R.id.csd_btn_leg_visible);
		csd_btn_legBrace_visible.setOnClickListener(this);
		csd_btn_legBrace_notVisible = (Button)view.findViewById(R.id.csd_btn_leg_not_visible);
		csd_btn_legBrace_notVisible.setOnClickListener(this);
		csd_btn_walkingFrame = (Button)view.findViewById(R.id.csd_btn_walking_frame);
		csd_btn_walkingFrame.setOnClickListener(this);
		csd_btn_wheelChair_assisted = (Button)view.findViewById(R.id.csd_btn_wheel_chair_assisted);
		csd_btn_wheelChair_assisted.setOnClickListener(this);
		csd_btn_wheelChair_manual = (Button)view.findViewById(R.id.csd_btn_wheel_chair_manual);
		csd_btn_wheelChair_manual.setOnClickListener(this);
		csd_btn_wheelChair_powered = (Button)view.findViewById(R.id.csd_btn_wheel_chair_powered);
		csd_btn_wheelChair_powered.setOnClickListener(this);
		csd_btn_other = (Button)view.findViewById(R.id.csd_btn_other);
		csd_btn_other.setOnClickListener(this);
		
		return view;
	}
	
	private View populateIntersectionDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_count_setup_intersection, null);
		getDialog().setTitle("Intersections");
		btn_close = (Button)view.findViewById(R.id.csi_btn_close);
		csi_intersections = true;
		
		csi_btn_intersection3 = (Button)view.findViewById(R.id.csi_btn_3way);
		csi_btn_intersection3.setOnClickListener(this);
		csi_btn_intersection4 = (Button)view.findViewById(R.id.csi_btn_4way);
		csi_btn_intersection4.setOnClickListener(this);
		csi_btn_intersection5 = (Button)view.findViewById(R.id.csi_btn_5way);
		csi_btn_intersection5.setOnClickListener(this);
		csi_btn_intersection6 = (Button)view.findViewById(R.id.csi_btn_6way);
		csi_btn_intersection6.setOnClickListener(this);
		
		return view;
	}
	
	private View populateCommentsDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_counting_screen_comments, null);
		getDialog().setTitle("Comments");
		btn_close = (Button)view.findViewById(R.id.csc_btn_close);
		csc_comments = true;
		csc_btn_submit = (Button)view.findViewById(R.id.csc_btn_submit);
		csc_btn_submit.setOnClickListener(this);
		dialogComments = (TextView)view.findViewById(R.id.csc_txtfield_comments);
		comment = getArguments().getString("Comments");
		dialogComments.setText(comment);
		
		return view;
	}
	
	private View populateDateDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_count_setup_date, null);
		getDialog().setTitle("Date Picker");
		btn_close = (Button)view.findViewById(R.id.csda_btn_close);
		csda_date = true;
		csda_btn_submit = (Button)view.findViewById(R.id.csda_btn_submit);
		csda_btn_submit.setOnClickListener(this);
		datePicker = (DatePicker)view.findViewById(R.id.csda_date_datepicker);
		
		return view;
	}
	
	////////////////////////////////////////////////////////////
	// End of Populate methods
	////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////
	// Start of onClick methods
	////////////////////////////////////////////////////////////
	
	private void onClickIntersection(View view){
		if(view.getId() == csi_btn_intersection3.getId()){
			useCommunicator("IntersectionType", "3 Way Intersection");
		}else if(view.getId() == csi_btn_intersection4.getId()){
			useCommunicator("IntersectionType", "4 Way Intersection");
		}else if(view.getId() == csi_btn_intersection5.getId()){
			useCommunicator("IntersectionType", "5 Way Intersection");
		}else if(view.getId() == csi_btn_intersection6.getId()){
			useCommunicator("IntersectionType", "6 Way Intersection");
		}
	}
	
	private void onClickCountObjects(View view){
		if(csd_countObjects_vehicles){
			onClickCountObjectsVehicles(view);
		}else{
			onClickCountObjectsPedestrians(view);
		}
	}
	
	private void onClickCountObjectsVehicles(View view){
		if(view.getId() == csd_btn_bus.getId()){
			useCommunicator("Bus", "Bus");
		}else if(view.getId() == csd_btn_truck.getId()){
			useCommunicator("Truck", "Truck");
		}else if(view.getId() == csd_btn_car.getId()){
			useCommunicator("Car", "Car");
		}else if(view.getId() == csd_btn_motorBike.getId()){
			useCommunicator("Motor Bike", "Motor Bike");
		}
	}
	
	private void onClickCountObjectsPedestrians(View view){
		if(view.getId() == csd_btn_pedestrian.getId()){
			useCommunicator("Pedestrian", "Pedestrian");
		}else if(view.getId() == csd_btn_bike.getId()){
			useCommunicator("Bike", "Bike");
		}else if(view.getId() == csd_btn_cane.getId()){
			useCommunicator("Cane", "Cane");
		}else if(view.getId() == csd_btn_dog.getId()){
			useCommunicator("Dog", "Dog");
		}else if(view.getId() == csd_btn_scooter.getId()){
			useCommunicator("Mobility Scooter", "Mobility Scooter");
		}else if(view.getId() == csd_btn_artificialLimb.getId()){
			useCommunicator("Artificial Limb", "Artificial Limb");
		}else if(view.getId() == csd_btn_crutches.getId()){
			useCommunicator("Crutches", "Crutches");
		}else if(view.getId() == csd_btn_walkingFrame.getId()){
			useCommunicator("Walking Frame", "Walking Frame");
		}else if(view.getId() == csd_btn_backBrace_visible.getId()){
			useCommunicator("Back Brace - Visible", "Back Brace - Visible");
		}else if(view.getId() == csd_btn_backBrace_notVisible.getId()){
			useCommunicator("Back Brace - Not Visible", "Back Brace - Not Visible");
		}else if(view.getId() == csd_btn_legBrace_visible.getId()){
			useCommunicator("Leg Brace - Visible", "Leg Brace - Visible");
		}else if(view.getId() == csd_btn_legBrace_notVisible.getId()){
			useCommunicator("Leg Brace - Not Visible", "Leg Brace - Not Visible");
		}else if(view.getId() == csd_btn_wheelChair_assisted.getId()){
			useCommunicator("Wheel Chair - Assisted", "Wheel Chair - Assisted");
		}else if(view.getId() == csd_btn_wheelChair_manual.getId()){
			useCommunicator("Wheel Chair - Manual", "Wheel Chair - Manual");
		}else if(view.getId() == csd_btn_wheelChair_powered.getId()){
			useCommunicator("Wheel Chair - Powered", "Wheel Chair - Powered");
		}else {			
			//Need to fix this later
			String value = "Other";
			useCommunicator("Other", value);
		}
	}
	
	private void onClickDate(View view){
		String day = "" + datePicker.getDayOfMonth();
		if(day.length() < 2){
			day = "0" + datePicker.getDayOfMonth();
		}
		
		String month = "" + (datePicker.getMonth()+1);
		if(month.length() < 2){
			month = "0" + (datePicker.getMonth()+1);
		}
		
		date = day + "/" + month + "/" + datePicker.getYear();
		communicator.sendClickMessage("Date", date);
		dismiss();
	}
	
	private void onClickComments(View view){
		comment = dialogComments.getText().toString().trim();
		communicator.sendClickMessage("Comment", comment);
		dismiss();
	}
	
	////////////////////////////////////////////////////////////
	// End of onClick methods
	////////////////////////////////////////////////////////////
	
	interface Communicator{
		public void sendClickMessage(String key, String value);
	}
	
	private void useCommunicator(String key, String value){
		communicator.sendClickMessage(key, value);
		dismiss();
	}
}

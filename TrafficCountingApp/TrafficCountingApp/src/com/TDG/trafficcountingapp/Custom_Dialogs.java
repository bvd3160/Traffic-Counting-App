package com.TDG.trafficcountingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Custom_Dialogs extends DialogFragment implements View.OnClickListener{
	
	Button btn_close;
	
	boolean csi_intersections = false;
	Button csi_btn_intersection3,csi_btn_intersection4,
			csi_btn_intersection5,csi_btn_intersection6;
	
	boolean csd_countObjects = false;
	boolean csd_countObjects_vehicles = false;
	Button csd_btn_bus, csd_btn_truck, csd_btn_car, csd_btn_motorBike , csd_btn_pedestrian, csd_btn_bike,
			csd_btn_cane, csd_btn_dog, csd_btn_scooter, csd_btn_artificialLimb, 
			csd_btn_backBrace_visible, csd_btn_backBrace_notVisible,
			csd_btn_crutches, csd_btn_legBrace_visible, csd_btn_legBrace_notVisible,
			csd_btn_walkingFrame, csd_btn_wheelChair_assisted, csd_btn_wheelChair_manual,
			csd_btn_wheelChair_powered, csd_btn_other;
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
		
		if(getTag().equals("vehicleDialog")){
			view = inflater.inflate(R.layout.dialog_counting_screen_vehicle, null);
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
		}else if(getTag().equals("pedestrianDialog")){
			view = inflater.inflate(R.layout.dialog_counting_screen_pedestrian, null);
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
		}else if(getTag().equals("intersectionDialog")){
			view = inflater.inflate(R.layout.dialog_count_setup_intersection, null);
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
		}
		
		btn_close.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View view) {
		if(view.getId() == btn_close.getId()){
			dismiss();
		}else if(csi_intersections){
			if(view.getId() == csi_btn_intersection3.getId()){
				communicator.sendClickMessage("3 Way Intersection");
				dismiss();
			}else if(view.getId() == csi_btn_intersection4.getId()){
				communicator.sendClickMessage("4 Way Intersection");
				dismiss();
			}else if(view.getId() == csi_btn_intersection5.getId()){
				communicator.sendClickMessage("5 Way Intersection");
				dismiss();
			}else if(view.getId() == csi_btn_intersection6.getId()){
				communicator.sendClickMessage("6 Way Intersection");
				dismiss();
			}
		}else if(csd_countObjects){
			if(csd_countObjects_vehicles){
				if(view.getId() == csd_btn_bus.getId()){
					communicator.sendClickMessage("Bus");
					dismiss();
				}else if(view.getId() == csd_btn_truck.getId()){
					communicator.sendClickMessage("Truck");
					dismiss();
				}else if(view.getId() == csd_btn_car.getId()){
					communicator.sendClickMessage("Car");
					dismiss();
				}else if(view.getId() == csd_btn_motorBike.getId()){
					communicator.sendClickMessage("Motor Bike");
					dismiss();
				}
			}else{
				if(view.getId() == csd_btn_pedestrian.getId()){
					communicator.sendClickMessage("Pedestrian");
					dismiss();
				}else if(view.getId() == csd_btn_bike.getId()){
					communicator.sendClickMessage("Bike");
					dismiss();
				}else if(view.getId() == csd_btn_cane.getId()){
					communicator.sendClickMessage("Cane");
					dismiss();
				}else if(view.getId() == csd_btn_dog.getId()){
					communicator.sendClickMessage("Dog");
					dismiss();
				}else if(view.getId() == csd_btn_scooter.getId()){
					communicator.sendClickMessage("Mobility Scooter");
					dismiss();
				}else if(view.getId() == csd_btn_artificialLimb.getId()){
					communicator.sendClickMessage("Artificial Limb");
					dismiss();
				}else if(view.getId() == csd_btn_crutches.getId()){
					communicator.sendClickMessage("Crutches");
					dismiss();
				}else if(view.getId() == csd_btn_walkingFrame.getId()){
					communicator.sendClickMessage("Walking Frame");
					dismiss();
				}else if(view.getId() == csd_btn_backBrace_visible.getId()){
					communicator.sendClickMessage("Back Brace - Visible");
					dismiss();
				}else if(view.getId() == csd_btn_backBrace_notVisible.getId()){
					communicator.sendClickMessage("Back Brace - Not Visible");
					dismiss();
				}else if(view.getId() == csd_btn_legBrace_visible.getId()){
					communicator.sendClickMessage("Leg Brace - Visible");
					dismiss();
				}else if(view.getId() == csd_btn_legBrace_notVisible.getId()){
					communicator.sendClickMessage("Leg Brace - Not Visible");
					dismiss();
				}else if(view.getId() == csd_btn_wheelChair_assisted.getId()){
					communicator.sendClickMessage("Wheel Chair - Assisted");
					dismiss();
				}else if(view.getId() == csd_btn_wheelChair_manual.getId()){
					communicator.sendClickMessage("Wheel Chair - Manual");
					dismiss();
				}else if(view.getId() == csd_btn_wheelChair_powered.getId()){
					communicator.sendClickMessage("Wheel Chair - Powered");
					dismiss();
				}else if(view.getId() == csd_btn_other.getId()){
					//Need to fix this later
					communicator.sendClickMessage("Other");
					dismiss();
				}
			}
		}
	}
	
	interface Communicator{
		public void sendClickMessage(String message);
	}
}

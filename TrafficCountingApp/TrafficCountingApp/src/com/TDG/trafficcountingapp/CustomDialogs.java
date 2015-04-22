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

/*
 * @author Richard Fong 1248615
 * @version 1.0
 * @since 13 January, 2015
 */

@SuppressLint({ "NewApi", "InflateParams" })
public class CustomDialogs extends DialogFragment implements View.OnClickListener{
	
	Button btn_close;
	Button csc_btn_submit;
	Button csda_btn_submit;
	Button csis_btn_submit;
	
	boolean csi_intersections = false;
	boolean csd_countObjects = false;
	boolean csd_countObjects_vehicles = false;
	boolean csda_date = false;
	boolean csc_comments = false;
	boolean csis_intersection_setup = false;
	
	Button csi_btn_intersection3,csi_btn_intersection4,
			csi_btn_intersection5,csi_btn_intersection6;
	
	Button csd_btn_bus, csd_btn_truck, csd_btn_car, csd_btn_motorBike ,
	
			csd_btn_pedestrian, csd_btn_crutches_1, csd_btn_crutches_2, 
			csd_btn_cane, csd_btn_dog, csd_btn_mobilityScooter, 
			csd_btn_wheelChair_assisted, csd_btn_wheelChair_manual, csd_btn_wheelChair_powered, 
			csd_btn_pushchair, csd_btn_skateboard, csd_btn_manualScooter;
	
	Button csis_btn_nw, csis_btn_n, csis_btn_ne, csis_btn_w, csis_btn_e, csis_btn_sw,
			csis_btn_s, csis_btn_se;
	boolean csis_btn_nw_clicked, csis_btn_n_clicked, csis_btn_ne_clicked, csis_btn_w_clicked,
			csis_btn_e_clicked, csis_btn_sw_clicked, csis_btn_s_clicked, csis_btn_se_clicked;
	TextView csis_txt_approachleft;
	String intersectionType;
	int intersectionCount;
	
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
		case "intersectionSetupDialog":
			view = populateIntersectionSetupDialog(inflater);
		default:
			break;
		}
		
		if(btn_close != null){
			btn_close.setOnClickListener(this);
		}
		
		return view;
	}
	
	@Override
	public void onClick(View view) {
		if(btn_close != null){
			if(view.getId() == btn_close.getId()){
				dismiss();
			}else if(csi_intersections){
				onClickIntersection(view);
			} else if(csd_countObjects){
				onClickCountObjects(view);
			} else if(csda_date){
				onClickDate(view);
			} else if(csc_comments){
				onClickComments(view);
			}
		}else{
			if(csis_intersection_setup){
				onClickIntersectionSetup(view);
			}
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
		csd_btn_crutches_1 = (Button)view.findViewById(R.id.csd_btn_crutches_1);
		csd_btn_crutches_1.setOnClickListener(this);
		csd_btn_crutches_2 = (Button)view.findViewById(R.id.csd_btn_crutches_2);
		csd_btn_crutches_2.setOnClickListener(this);
		
		csd_btn_cane = (Button)view.findViewById(R.id.csd_btn_cane);
		csd_btn_cane.setOnClickListener(this);
		csd_btn_dog = (Button)view.findViewById(R.id.csd_btn_dog);
		csd_btn_dog.setOnClickListener(this);
		csd_btn_mobilityScooter = (Button)view.findViewById(R.id.csd_btn_mobility_scooter);
		csd_btn_mobilityScooter.setOnClickListener(this);
		
		csd_btn_wheelChair_assisted = (Button)view.findViewById(R.id.csd_btn_wheelchair_assisted);
		csd_btn_wheelChair_assisted.setOnClickListener(this);
		csd_btn_wheelChair_manual = (Button)view.findViewById(R.id.csd_btn_wheelchair_manual);
		csd_btn_wheelChair_manual.setOnClickListener(this);
		csd_btn_wheelChair_powered = (Button)view.findViewById(R.id.csd_btn_wheelchair_powered);
		csd_btn_wheelChair_powered.setOnClickListener(this);
		
		csd_btn_pushchair = (Button)view.findViewById(R.id.csd_btn_pushchair);
		csd_btn_pushchair.setOnClickListener(this);
		csd_btn_skateboard = (Button)view.findViewById(R.id.csd_btn_skateboard);
		csd_btn_skateboard.setOnClickListener(this);
		csd_btn_manualScooter = (Button)view.findViewById(R.id.csd_btn_manual_scooter);
		csd_btn_manualScooter.setOnClickListener(this);
		
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
	
	private View populateIntersectionSetupDialog(LayoutInflater inflater){
		View view = inflater.inflate(R.layout.dialog_count_setup_intersection_setup, null);
		getDialog().setTitle("Intersection Setup");
		setCancelable(false);
		csis_intersection_setup = true;
		csis_btn_submit = (Button)view.findViewById(R.id.csis_btn_submit);
		csis_btn_submit.setOnClickListener(this);
		
		csis_btn_nw = (Button)view.findViewById(R.id.csis_btn_nw);
		csis_btn_nw.setOnClickListener(this);
		csis_btn_nw_clicked = false;
		
		csis_btn_n = (Button)view.findViewById(R.id.csis_btn_n);
		csis_btn_n.setOnClickListener(this);
		csis_btn_n_clicked = false;
		
		csis_btn_ne = (Button)view.findViewById(R.id.csis_btn_ne);
		csis_btn_ne.setOnClickListener(this);
		csis_btn_ne_clicked = false;
		
		csis_btn_w = (Button)view.findViewById(R.id.csis_btn_w);
		csis_btn_w.setOnClickListener(this);
		csis_btn_w_clicked = false;
		
		csis_btn_e = (Button)view.findViewById(R.id.csis_btn_e);
		csis_btn_e.setOnClickListener(this);
		csis_btn_e_clicked = false;
		
		csis_btn_sw = (Button)view.findViewById(R.id.csis_btn_sw);
		csis_btn_sw.setOnClickListener(this);
		csis_btn_sw_clicked = false;
		
		csis_btn_s = (Button)view.findViewById(R.id.csis_btn_s);
		csis_btn_s.setOnClickListener(this);
		csis_btn_s_clicked = false;
		
		csis_btn_se = (Button)view.findViewById(R.id.csis_btn_se);
		csis_btn_se.setOnClickListener(this);
		csis_btn_se_clicked = false;
		
		csis_txt_approachleft = (TextView)view.findViewById(R.id.csis_txt_approachleft);
		intersectionType = getArguments().getString("intersectionType");
		intersectionCount = 0;
		
		setIntersectionLeftText();
		
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
			useCommunicator("IntersectionType", "3 Way Intersection", null);
		}else if(view.getId() == csi_btn_intersection4.getId()){
			useCommunicator("IntersectionType", "4 Way Intersection", null);
		}else if(view.getId() == csi_btn_intersection5.getId()){
			useCommunicator("IntersectionType", "5 Way Intersection", null);
		}else if(view.getId() == csi_btn_intersection6.getId()){
			useCommunicator("IntersectionType", "6 Way Intersection", null);
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
			useCommunicator("Bus", "Bus", null);
		}else if(view.getId() == csd_btn_truck.getId()){
			useCommunicator("Truck", "Truck", null);
		}else if(view.getId() == csd_btn_car.getId()){
			useCommunicator("Car", "Car", null);
		}else if(view.getId() == csd_btn_motorBike.getId()){
			useCommunicator("Motor Bike", "Motor Bike", null);
		}
	}
	
	private void onClickCountObjectsPedestrians(View view){
		if(view.getId() == csd_btn_pedestrian.getId()){
			useCommunicator("Pedestrian", "Pedestrian (No Aid)", null);
		}else if(view.getId() == csd_btn_cane.getId()){
			useCommunicator("Cane", "Cane (Poor Eyesight)", null);
		}else if(view.getId() == csd_btn_dog.getId()){
			useCommunicator("Dog", "Guide Dog", null);
		}else if(view.getId() == csd_btn_mobilityScooter.getId()){
			useCommunicator("Mobility Scooter", "Mobility Scooter", null);
		}else if(view.getId() == csd_btn_crutches_1.getId()){
			useCommunicator("Crutches_1", "Walking Stick / Crutch (1)", null);
		}else if(view.getId() == csd_btn_crutches_2.getId()){
			useCommunicator("Crutches_2", "Walking Sticks / Crutches (2)", null);
		}else if(view.getId() == csd_btn_wheelChair_assisted.getId()){
			useCommunicator("Wheel Chair - Assisted", "Wheel Chair (Assisted)", null);
		}else if(view.getId() == csd_btn_wheelChair_manual.getId()){
			useCommunicator("Wheel Chair - Manual", "Wheel Chair (Manual)", null);
		}else if(view.getId() == csd_btn_wheelChair_powered.getId()){
			useCommunicator("Wheel Chair - Powered", "Wheel Chair (Powered)", null);
		}else if(view.getId() == csd_btn_pushchair.getId()){
			useCommunicator("Push Chair", "Push Chair / Buggy", null);
		}else if(view.getId() == csd_btn_skateboard.getId()){
			useCommunicator("Skateboard", "Skateboard", null);
		}else if(view.getId() == csd_btn_manualScooter.getId()){
			useCommunicator("Manual Scooter", "Manual Scooter", null);
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
		communicator.sendClickMessage("Date", date, null);
		dismiss();
	}
	
	private void onClickComments(View view){
		comment = dialogComments.getText().toString().trim();
		communicator.sendClickMessage("Comment", comment, null);
		dismiss();
	}
	
	
	////////////////////////////////////////////////////////////
	// Start of OnClickIntersectionSetup
	////////////////////////////////////////////////////////////
	
	private void onClickIntersectionSetup(View view){
		if(intersectionType.equals("3 Way Intersection")){
			checkIntersection(3);
		}else if (intersectionType.equals("4 Way Intersection")){
			checkIntersection(4);
		}else if (intersectionType.equals("5 Way Intersection")){
			checkIntersection(5);
		}else if (intersectionType.equals("6 Way Intersection")){
			checkIntersection(6);
		}
		setIntersectionLeftText();
	}
	
	private void setIntersectionLeftText(){
		int approachLeft = ((intersectionType.charAt(0)-48) - intersectionCount);
		csis_txt_approachleft.setText("Approach Left: " + approachLeft);
	}
	
	private void checkIntersection(int approachRequired){
		if(csis_btn_nw.isPressed()){
			csis_btn_nw_clicked = checkIntersectionClickedButton(csis_btn_nw_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - nw", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "NW Enabled - " + csis_btn_nw_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_n.isPressed()){
			csis_btn_n_clicked = checkIntersectionClickedButton(csis_btn_n_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - n", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "N Enabled - " + csis_btn_n_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_ne.isPressed()){
			csis_btn_ne_clicked = checkIntersectionClickedButton(csis_btn_ne_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - ne", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "NE Enabled - " + csis_btn_ne_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_w.isPressed()){
			csis_btn_w_clicked = checkIntersectionClickedButton(csis_btn_w_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - w", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "W Enabled - " + csis_btn_w_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_e.isPressed()){
			csis_btn_e_clicked = checkIntersectionClickedButton(csis_btn_e_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - e", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "E Enabled - " + csis_btn_e_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_sw.isPressed()){
			csis_btn_sw_clicked = checkIntersectionClickedButton(csis_btn_sw_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - sw", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "SW Enabled - " + csis_btn_sw_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_s.isPressed()){
			csis_btn_s_clicked = checkIntersectionClickedButton(csis_btn_s_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - s", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "S Enabled - " + csis_btn_s_clicked, Toast.LENGTH_SHORT).show();
		}else if(csis_btn_se.isPressed()){
			csis_btn_se_clicked = checkIntersectionClickedButton(csis_btn_se_clicked, approachRequired);
			Toast.makeText(getActivity(), approachRequired + " - se", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "SE Enabled - " + csis_btn_se_clicked, Toast.LENGTH_SHORT).show();
		}
		
		if(approachRequired == intersectionCount && csis_btn_submit.isPressed()){
			boolean[] result = new boolean[8];
			result[0] = csis_btn_nw_clicked;
			result[1] = csis_btn_n_clicked;
			result[2] = csis_btn_ne_clicked;
			result[3] = csis_btn_w_clicked;
			result[4] = csis_btn_e_clicked;
			result[5] = csis_btn_sw_clicked;
			result[6] = csis_btn_s_clicked;
			result[7] = csis_btn_se_clicked;
			useCommunicator("Intersection Setup", null, result);
		}
	}
	
	private boolean checkIntersectionClickedButton(boolean buttonClicked, int approachRequired){
		if(!buttonClicked && intersectionCount < approachRequired){
			buttonClicked = true;
			intersectionCount++;
		}else if(buttonClicked){
			buttonClicked = false;
			intersectionCount--;
		}
		
		return buttonClicked;
	}
	
	////////////////////////////////////////////////////////////
	// End of OnClickIntersectionSetup
	////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////
	// End of onClick methods
	////////////////////////////////////////////////////////////
	
	interface Communicator{
		public void sendClickMessage(String key, String stringValue, boolean[] booleanValue);
	}
	
	private void useCommunicator(String key, String stringValue, boolean[] booleanValue){
		communicator.sendClickMessage(key, stringValue, booleanValue);
		dismiss();
	}
}

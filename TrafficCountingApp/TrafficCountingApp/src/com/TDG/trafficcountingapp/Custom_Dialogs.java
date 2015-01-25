package com.TDG.trafficcountingapp;

import android.annotation.SuppressLint;
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
	boolean intersections = false;
	Button csi_btn_intersection3,csi_btn_intersection4,
			csi_btn_intersection5,csi_btn_intersection6;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//This creates the view and decides which fragment to show depending on the tags which were given to them in CountingScreen.java.
		View view = null;
		
		if(getTag().equals("vehicleDialog")){
			view = inflater.inflate(R.layout.dialog_counting_screen_vehicle, null);
			getDialog().setTitle("Vehicles");
			btn_close = (Button)view.findViewById(R.id.csd_btn_close_vehicle);
		}else if(getTag().equals("pedestrianDialog")){
			view = inflater.inflate(R.layout.dialog_counting_screen_pedestrian, null);
			getDialog().setTitle("Pedestrians");
			btn_close = (Button)view.findViewById(R.id.csd_btn_close_pedestrian);
		}else if(getTag().equals("intersectionDialog")){
			view = inflater.inflate(R.layout.dialog_count_setup_intersection, null);
			getDialog().setTitle("Intersections");
			btn_close = (Button)view.findViewById(R.id.csi_btn_close);
			intersections = true;
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
		}else if(intersections){
			if(view.getId() == csi_btn_intersection3.getId()){
				Toast.makeText(getActivity(), "Clicked 3 intersection", Toast.LENGTH_SHORT).show();
			}else if(view.getId() == csi_btn_intersection4.getId()){
				Toast.makeText(getActivity(), "Clicked 4 intersection", Toast.LENGTH_SHORT).show();
			}else if(view.getId() == csi_btn_intersection5.getId()){
				Toast.makeText(getActivity(), "Clicked 5 intersection", Toast.LENGTH_SHORT).show();
			}else if(view.getId() == csi_btn_intersection6.getId()){
				Toast.makeText(getActivity(), "Clicked 6 intersection", Toast.LENGTH_SHORT).show();
			}
		}
	}
}

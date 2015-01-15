package com.TDG.trafficcountingapp;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class CountingScreen_Dialog extends DialogFragment implements View.OnClickListener{
	
	Button btn_cancel;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//This creates the view and decides which fragment to show depending on the tags which were given to them in CountingScreen.java.
		View view = null;
		if(getTag().equals("vehicleDialog")){
			view = inflater.inflate(R.layout.fragment_counting_screen_vehicle, null);
			getDialog().setTitle("Vehicles");
			btn_cancel = (Button)view.findViewById(R.id.csd_btn_cancel_vehicle);
		}else if(getTag().equals("pedestrianDialog")){
			view = inflater.inflate(R.layout.fragment_counting_screen_pedestrian, null);
			getDialog().setTitle("Pedestrians");
			btn_cancel = (Button)view.findViewById(R.id.csd_btn_cancel_pedestrian);
		}
		
		setCancelable(false);
		btn_cancel.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == btn_cancel.getId()){
			dismiss();
		}
	}
	
	
	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting_screen__dialog);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counting_screen__dialog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 *
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater
					.inflate(R.layout.fragment_counting_screen__vehicle,
							container, false);
			return rootView;
		}
	}
	*/

}

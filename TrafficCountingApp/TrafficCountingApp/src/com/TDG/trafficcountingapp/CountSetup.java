package com.TDG.trafficcountingapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class CountSetup extends ActionBarActivity {
	
	TextView surveyorName, currentDate, numAndNameStreet, 
			suburb, city, postCode, areaDescription, intersectionType, comments;

	private static String sName;
	
	SimpleDateFormat dateForm;
	Calendar calendar = Calendar.getInstance();
	Button setDate,submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_setup);
		
		surveyorName = (TextView) findViewById(R.id.surveyorName);
		currentDate = (TextView) findViewById(R.id.dateSetup);
		numAndNameStreet = (TextView) findViewById(R.id.streetNumAndNAme);
		suburb = (TextView) findViewById(R.id.suburb);
		city = (TextView) findViewById(R.id.city);
		postCode = (TextView) findViewById(R.id.postcode);
		areaDescription = (TextView) findViewById(R.id.areaDescription);
		intersectionType = (TextView) findViewById(R.id.intersectionType);
		comments = (TextView) findViewById(R.id.comments);
		
		//Current Date setup
		setDate = (Button) findViewById(R.id.setDateBT);
		setDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "As of now, Button only updates to current Date " +
						"GO BACK AND FIX IT so it shows DateSettingDialog", Toast.LENGTH_LONG).show();
				updateDate();
				//setDate(); <<< That's what we need to get working!
			}
		});
		
		//Date format
		dateForm = new SimpleDateFormat("dd/MM/yyyy");
		
		//Submit Button setup
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = surveyorName.getText().toString().trim();
				
				if(!name.isEmpty()){
					CountSetup.setsName(name);
					Toast.makeText(CountSetup.this, "Your full name is: "+name, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(CountSetup.this, "Please State your name",Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
		updateDate();
	}
	
	/*
	 * The actual setting of new date on a DatePicker dialog
	 */	
	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		}
	};
	
	/*
	 * SetDate dialog box already set with current date.
	*/
	public void setDate(){
		new DatePickerDialog(getApplicationContext(), date, calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/*
	 *  Setting the currentDate TextView with the date user just selected
	 */
	public void updateDate(){
		currentDate.setText(dateForm.format(calendar.getTime()));
	}

	
	
	public static String getsName() {
		return sName;
	}

	public static void setsName(String sName) {
		CountSetup.sName = sName;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.count_setup, menu);
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
}

package com.TDG.trafficcountingapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

	private static String sName, theDate;
	
	SimpleDateFormat dateForm;
	Calendar calendar = Calendar.getInstance();
	Button setDate, selectIntersectionType, submit;

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
		selectIntersectionType = (Button) findViewById(R.id.intersectionButton);
		comments = (TextView) findViewById(R.id.comments);
		submit = (Button) findViewById(R.id.submit);
		
		//Current Date setup
		dateForm = new SimpleDateFormat("dd/MM/yyyy");
		setDate = (Button) findViewById(R.id.setDateBT);
		setDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateDate();
				//setDate(); <<< That's what we need to get working!
			}
		});
		
		updateDate();	
		preSubmissionChecks();
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

	
	/*
	 * Checks and test before accepting data
	 */
	private void preSubmissionChecks() {
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = surveyorName.getText().toString().trim();
				String date = currentDate.getText().toString().trim();
				
				//====CHECKS and SETTING values to variable for later referencing later====
				
				//SURVEYOR NAME
				if(!name.isEmpty()){
					CountSetup.setsName(name);
					System.out.println("1-Surveyor Name: "+getsName());
				}else{
					Toast.makeText(CountSetup.this, "Please State your name",Toast.LENGTH_LONG).show();
				}
				//THE DATE
				if(!date.isEmpty()){
					CountSetup.setTheDate(date);
					System.out.println("2-Date: "+getTheDate());
				}else{
					Toast.makeText(CountSetup.this, "Please click the 'Set Date' button", Toast.LENGTH_LONG).show();
				}
				//LOCATION
				
				
			}
		});
	}

	public static String getsName() {
		return sName;
	}

	public static void setsName(String sName) {
		CountSetup.sName = sName;
	}

	public static String getTheDate() {
		return theDate;
	}

	public static void setTheDate(String theDate) {
		CountSetup.theDate = theDate;
	}
	
	
	public void selectIntersectionType(View view){
		Intent intent = new Intent(this, IntersectionType.class);
		startActivity(intent);
	}

//===============================================================================	
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

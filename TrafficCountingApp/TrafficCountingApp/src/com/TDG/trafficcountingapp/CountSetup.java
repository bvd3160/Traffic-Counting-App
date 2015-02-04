package com.TDG.trafficcountingapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.TDG.trafficcountingapp.CustomDialogs.Communicator;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class CountSetup extends ActionBarActivity implements Communicator{
	
	TextView surveyorName, currentDate, numAndNameStreet, 
			suburb, city, postCode, areaDescription, intersectionType, comments;

	private static String sName, theDate, streetNumAndName, suburbName, cityName, areaCode, areaDescript,
						typeOfIntersection, commentSection;
	
	SimpleDateFormat dateForm;
	Calendar calendar = Calendar.getInstance();
	Button setDate, selectIntersectionType, submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_setup);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		
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
				
				/*
				 * For now
				 */
				boolean completed = true;
				if(completed){
					submitCountScreen(v);
				}else{
				
				String name = surveyorName.getText().toString().trim();
				String date = currentDate.getText().toString().trim();
				String streeNumAndName = numAndNameStreet.getText().toString().trim();
				String suburbName = suburb.getText().toString().trim();
				String cityName = city.getText().toString().trim();
				String areaCode = postCode.getText().toString().trim();
				String areaDescript = areaDescription.getText().toString().trim();
				String intersectionKind = intersectionType.getText().toString().trim();
				String commentArea = comments.getText().toString().trim();
				//boolean completed = true;
				
				//====CHECKS and SETTING values to variable for later referencing later====
				
				//SURVEYOR NAME
				if(!name.isEmpty()){
					CountSetup.setsName(name);
					System.out.println("1-Surveyor Name: "+getsName());
				}else{
					Toast.makeText(CountSetup.this, "Please State your name", Toast.LENGTH_LONG).show();
					completed = false;
				}
				//THE DATE
				if(!date.isEmpty()){
					CountSetup.setTheDate(date);
					System.out.println("2-Date: "+getTheDate());
				}else{
					Toast.makeText(CountSetup.this, "Please click the 'Set Date' button", Toast.LENGTH_LONG).show();
					completed = false;
				}
				//LOCATION
				if(!streeNumAndName.isEmpty()){
					CountSetup.setStreetNumAndName(streeNumAndName);
					System.out.println("3.1-Street: "+getStreetNumAndName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide street number and/or name", Toast.LENGTH_LONG).show();
					completed = false;
				}
				if(!suburbName.isEmpty()){
					CountSetup.setSuburbName(suburbName);
					System.out.println("3.2-Suburb: "+getSuburbName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the Suburb name", Toast.LENGTH_LONG).show();
					completed = false;
				}
				if(!cityName.isEmpty()){
					CountSetup.setCityName(cityName);
					System.out.println("3.3-City: "+getCityName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the City name", Toast.LENGTH_LONG).show();
					completed = false;
				}
				if(!areaCode.isEmpty()){
					CountSetup.setAreaCode(areaCode);
					System.out.println("3.4-Area Code: "+getAreaCode());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the Post code", Toast.LENGTH_LONG).show();
					completed = false;
				}
				if(!areaDescript.isEmpty()){
					CountSetup.setAreaDescript(areaDescript);
					System.out.println("3.4-Area Description: "+getAreaDescript());
				}else{
					Toast.makeText(CountSetup.this, "Please provide a Description of the area", Toast.LENGTH_LONG).show();
					completed = false;
				}
				
				if(!commentArea.isEmpty()){
					CountSetup.setCommentSection(commentArea);
					System.out.println("3.6-Area Description: "+getCommentSection());
				}else{
					Toast.makeText(CountSetup.this, "Would you care to comment on what you see?", Toast.LENGTH_LONG).show();
					completed = false;
				}
				
				if(completed){
					submitCountScreen(v);
				}
				}
			}
		});
	}
	
	/* Jeans unedited version(I needed to edit the above to allow the intent to work after checking.
	 * Done by adding a boolean value which will check if the required information has been completed)
	 * 
	 * private void preSubmissionChecks() {
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = surveyorName.getText().toString().trim();
				String date = currentDate.getText().toString().trim();
				String streeNumAndName = numAndNameStreet.getText().toString().trim();
				String suburbName = suburb.getText().toString().trim();
				String cityName = city.getText().toString().trim();
				String areaCode = postCode.getText().toString().trim();
				String areaDescript = areaDescription.getText().toString().trim();
				String intersectionKind = intersectionType.getText().toString().trim();
				String commentArea = comments.getText().toString().trim();
				
				//====CHECKS and SETTING values to variable for later referencing later====
				
				//SURVEYOR NAME
				if(!name.isEmpty()){
					CountSetup.setsName(name);
					System.out.println("1-Surveyor Name: "+getsName());
				}else{
					Toast.makeText(CountSetup.this, "Please State your name", Toast.LENGTH_LONG).show();
				}
				//THE DATE
				if(!date.isEmpty()){
					CountSetup.setTheDate(date);
					System.out.println("2-Date: "+getTheDate());
				}else{
					Toast.makeText(CountSetup.this, "Please click the 'Set Date' button", Toast.LENGTH_LONG).show();
				}
				//LOCATION
				if(!streeNumAndName.isEmpty()){
					CountSetup.setStreetNumAndName(streeNumAndName);
					System.out.println("3.1-Street: "+getStreetNumAndName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide street number and/or name", Toast.LENGTH_LONG).show();
				}
				if(!suburbName.isEmpty()){
					CountSetup.setSuburbName(suburbName);
					System.out.println("3.2-Suburb: "+getSuburbName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the Suburb name", Toast.LENGTH_LONG).show();
				}
				if(!cityName.isEmpty()){
					CountSetup.setCityName(cityName);
					System.out.println("3.3-City: "+getCityName());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the City name", Toast.LENGTH_LONG).show();
				}
				if(!areaCode.isEmpty()){
					CountSetup.setAreaCode(areaCode);
					System.out.println("3.4-Area Code: "+getAreaCode());
				}else{
					Toast.makeText(CountSetup.this, "Please provide the Post code", Toast.LENGTH_LONG).show();
				}
				if(!areaDescript.isEmpty()){
					CountSetup.setAreaDescript(areaDescript);
					System.out.println("3.4-Area Description: "+getAreaDescript());
				}else{
					Toast.makeText(CountSetup.this, "Please provide a Description of the area", Toast.LENGTH_LONG).show();
				}
				
				if(!commentArea.isEmpty()){
					CountSetup.setCommentSection(commentArea);
					System.out.println("3.6-Area Description: "+getCommentSection());
				}else{
					Toast.makeText(CountSetup.this, "Would you care to comment on what you see?", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	 */

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
	
	public static String getStreetNumAndName() {
		return streetNumAndName;
	}

	public static void setStreetNumAndName(String streetNumAndName) {
		CountSetup.streetNumAndName = streetNumAndName;
	}

	public static String getSuburbName() {
		return suburbName;
	}

	public static void setSuburbName(String suburbName) {
		CountSetup.suburbName = suburbName;
	}

	public static String getCityName() {
		return cityName;
	}

	public static void setCityName(String cityName) {
		CountSetup.cityName = cityName;
	}

	public static String getAreaCode() {
		return areaCode;
	}

	public static void setAreaCode(String areaCode) {
		CountSetup.areaCode = areaCode;
	}

	public static String getAreaDescript() {
		return areaDescript;
	}

	public static void setAreaDescript(String areaDescript) {
		CountSetup.areaDescript = areaDescript;
	}

	public static String getTypeOfIntersection() {
		return typeOfIntersection;
	}

	public static void setTypeOfIntersection(String typeOfIntersection) {
		CountSetup.typeOfIntersection = typeOfIntersection;
	}

	public static String getCommentSection() {
		return commentSection;
	}

	public static void setCommentSection(String commentSection) {
		CountSetup.commentSection = commentSection;
	}
	
	public void submitCountScreen(View view){
		Intent intent = new Intent(this, CountingScreen.class);
		/*
		 * Need to send data from here
		 */
		startActivity(intent);
	}
	
	@SuppressLint("NewApi")
	public void showIntersectionDialog(View view){
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "intersectionDialog");		
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

	@Override
	public void sendClickMessage(String message) {
		CountSetup.setTypeOfIntersection(message);
		intersectionType.setText(message);
		Toast.makeText(this, CountSetup.getTypeOfIntersection(), Toast.LENGTH_SHORT).show();
	}
}

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

/*
 * @author Jean-Yves Kwibuka 1245654
 * @version 1.0
 * @since December, 2014
 */

public class CountSetup extends ActionBarActivity implements Communicator{
	
	boolean SKIPCHECKS = false;
	
	TextView surveyorName;

	static TextView currentDate;

	TextView numAndNameStreet;

	TextView suburb;

	TextView city;

	TextView postCode;

	TextView areaDescription;

	TextView intersectionType;

	TextView comments;
	
	TextView weatherComment_et;

	private static String sName, theDate, streetNumAndName, suburbName, cityName, areaCode, areaDescript
	, weatherCommentSection, commentSection;
	
	private String typeOfIntersection;
	
	static SimpleDateFormat dateForm;
	static SimpleDateFormat datFileDateForm;
	
	static Calendar calendar = Calendar.getInstance();
	Button setDate, selectIntersectionType, submit;
	
	boolean[] intersectionPicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_setup);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		surveyorName = (TextView) findViewById(R.id.surveyorName);
		currentDate = (TextView) findViewById(R.id.dateSetup);
		currentDate.setClickable(false);
		numAndNameStreet = (TextView) findViewById(R.id.streetNumAndNAme);
		suburb = (TextView) findViewById(R.id.suburb);
		city = (TextView) findViewById(R.id.city);
		postCode = (TextView) findViewById(R.id.postcode);
		areaDescription = (TextView) findViewById(R.id.areaDescription);
		intersectionType = (TextView) findViewById(R.id.intersectionType);
		intersectionType.setClickable(false);
		selectIntersectionType = (Button) findViewById(R.id.selectIntersection);
		weatherComment_et = (TextView) findViewById(R.id.weatherComment_et);
		comments = (TextView) findViewById(R.id.comments);
		submit = (Button) findViewById(R.id.submit);
		intersectionPicked = new boolean[8];
		for(int x = 0; x < 8; x++){
			intersectionPicked[x] = false;
		}
		
		/*
		 * This block of code will catch the extra sent from MainScreen
		 * From here I can disable buttons of whatever else.
		 * @author: Jean-Yves
		 * @since: November 2014
		 */
		Intent intent = getIntent();
		String clicked = intent.getStringExtra("nonIntBtn");
		if(clicked != null){
			//Line below is a test
			//Toast.makeText(CountSetup.this, clicked, Toast.LENGTH_LONG).show();
			selectIntersectionType.setVisibility(4);
			intersectionType.setVisibility(4);
			findViewById(R.id.csn_txt_w).setVisibility(4);
		}
		
		/*
		 * Current Date setup
		 * @author: Jean-Yves
		 * @since: November 2014
		 */
		dateForm = new SimpleDateFormat("dd/MM/yyyy");
		datFileDateForm = new SimpleDateFormat("yyyy.MM.dd");
		
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
	public static String updateDate(){
		currentDate.setText(dateForm.format(calendar.getTime()));
		return (datFileDateForm.format(calendar.getTime()));
	}
	
	public static String getCurrentDate(){
		return (dateForm.format(calendar.getTime()));
	}

	
	/*
	 * Checks and test before accepting data
	 */
	private void preSubmissionChecks() {
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(SKIPCHECKS){
					String commentArea = comments.getText().toString().trim();
					CountSetup.setCommentSection(commentArea);
					
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
				String weatherComment = weatherComment_et.getText().toString().trim();
				String commentArea = comments.getText().toString().trim();
				
				boolean completed = true;
				
				//====CHECKS and SETTING values to variable for later referencing later====
				
				//SURVEYOR NAME
				/*if(!name.isEmpty()){
					CountSetup.setsName(name);
					System.out.println("1-Surveyor Name: "+getsName());
				}else{
					Toast.makeText(CountSetup.this, "Please State your name", Toast.LENGTH_LONG).show();
					completed = false;
				}*/
				//THE DATE
				if(!date.isEmpty()){
					CountSetup.setTheDate(date);
					//System.out.println("2-Date: "+getTheDate());
				}else{
					Toast.makeText(CountSetup.this, "Please set today's date", Toast.LENGTH_LONG).show();
					completed = false;
				}
				//LOCATION
				if(!streeNumAndName.isEmpty() || !suburbName.isEmpty() || !cityName.isEmpty() || !areaCode.isEmpty() || !areaDescript.isEmpty()){
					CountSetup.setStreetNumAndName(streeNumAndName);
					//System.out.println("3.1-Street: "+getStreetNumAndName());
				}else{
					Toast.makeText(CountSetup.this, "Please tell me where you are", Toast.LENGTH_LONG).show();
					completed = false;
				}
				/*
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
				if(!weatherComment.isEmpty()){
					CountSetup.setWeatherCommentSection(weatherComment);
					System.out.println("3.5-Weahter Commnent: "+getWeatherCommentSection());
				}else{
					Toast.makeText(CountSetup.this, "Please provide a description of current weather conditions", Toast.LENGTH_LONG).show();
					completed = false;
				}
				if(!commentArea.isEmpty()){
					CountSetup.setCommentSection(commentArea);
					System.out.println("3.6-Area Description: "+getCommentSection());
				}else{
					Toast.makeText(CountSetup.this, "Would you care to comment on what you see?", Toast.LENGTH_LONG).show();
					completed = false;
				}
				*/
				
				if(completed){
					submitCountScreen(v);
				}
				}
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

	public String getTypeOfIntersection() {
		return typeOfIntersection;
	}

	public void setTypeOfIntersection(String typeOfIntersection) {
		this.typeOfIntersection = typeOfIntersection;
	}

	public static String getWeatherCommentSection() {
		return weatherCommentSection;
	}

	public static void setWeatherCommentSection(String weatherCommentSection) {
		CountSetup.weatherCommentSection = weatherCommentSection;
	}

	public static String getCommentSection() {
		return commentSection;
	}

	public static void setCommentSection(String commentSection) {
		CountSetup.commentSection = commentSection;
	}
	
	/*
	 * This method just adds the extra data that will be used in CountingScreen.java
	 * @author: Richard Fong
	 * @since: 
	 */
	public void submitCountScreen(View view){
		Intent intent = new Intent(this, CountingScreen.class);
		/*
		 * Need to send data from here
		 */
		intent.putExtra("Comments", getCommentSection());
		intent.putExtra("IntersectionType", getTypeOfIntersection());
		intent.putExtra("IntersectionsPicked", intersectionPicked);
		startActivityForResult(intent, 1);
	}
	
	/*
	 * These methods allow the CustomDialog know which pop-up screen to display.
	 * @author: Richad Fong
	 * @since: 
	 */
	@SuppressLint("NewApi")
	public void showIntersectionDialog(View view){
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "intersectionDialog");		
	}
	
	@SuppressLint("NewApi")
	public void showDateDialog(View view){
		FragmentManager manager = getFragmentManager();
		CustomDialogs dialog = new CustomDialogs();
		dialog.show(manager, "dateDialog");
	}
	
	/*
	 * This method is used when the user goes to the CountSetup screen from the CountingScreen.
	 * It updates the comment section in the CountSetup screen.
	 * @author: Richard Fong
	 * @since: 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if (requestCode == 1) {
		         if(resultCode == RESULT_OK){
		          setCommentSection(data.getStringExtra("Comment"));
		          comments.setText(getCommentSection());
		    }
		} 
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

	/*
	 * This method is from the Communications class in CustomDialogs.
	 * It allows CountSetup to get the data which was gathered in CustomDialogs
	 * and updates the required information in CountSetup.
	 * @author: Richard Fong
	 * @since:
	 */
	@SuppressLint("NewApi")
	@Override
	public void sendClickMessage(String key, String stringValue, boolean[] booleanValue, String[] stringArrayValue) {
		if(key.equals("IntersectionType")){
			this.setTypeOfIntersection(stringValue);
			intersectionType.setText(stringValue);
			FragmentManager manager = getFragmentManager();
			CustomDialogs dialog = new CustomDialogs();
			Bundle args = new Bundle();
			args.putString("intersectionType", getTypeOfIntersection());
			dialog.setArguments(args);
			dialog.show(manager, "intersectionSetupDialog");
		} else if(key.equals("Date")){
			CountSetup.setTheDate(stringValue);
			currentDate.setText(stringValue);
		} else if(key.equals("Intersection Setup")){
			intersectionPicked = booleanValue;
		}
	}
}

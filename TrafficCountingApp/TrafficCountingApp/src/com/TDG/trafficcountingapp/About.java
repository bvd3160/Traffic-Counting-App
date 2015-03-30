package com.TDG.trafficcountingapp;

import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/*
 * @author Jean-Yves Kwibuka 1245654
 * @version 1.0
 * @since December, 2014
 */

public class About extends ActionBarActivity {
	
	TextView aboutTDG, aboutApp, aboutTeam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		aboutTDG = (TextView) findViewById(R.id.about_tdg);
			aboutTDG.setMovementMethod(new ScrollingMovementMethod());
		aboutApp = (TextView) findViewById(R.id.about_app);
			aboutApp.setMovementMethod(new ScrollingMovementMethod());
		aboutTeam = (TextView) findViewById(R.id.about_team);
			aboutTeam.setMovementMethod(new ScrollingMovementMethod());
		
		String aboutTDGText = "TDG is New Zealand’s largest specialist transportation " +
				"engineering consultancy offering a full range of professional services in " +
				"transportation: engineering, planning, modelling, analysis and design. With " +
				"offices throughout the country, our work spans large and small developments, in " +
				"both the private and public sectors. Wherever people and transportation meet, " +
				"that's where we are. Making that meeting point better, is what we do. \n\n" +
				"" +
				"We believe that traffic engineering and transportation planning are, at core, " +
				"about people. In today’s world, wherever people are, whatever they are doing – living, " +
				"working, enjoying their leisure – their lives are in some way affected by transportation. " +
				"At the foundation of our business is a simple equation:\n       Infrastructure + Activity = " +
				"Transportation\nOur job since 1976 has been to make the interplay of these elements positive, " +
				"effective and efficient. Our tools are our people.  We bring our knowledge, skills and " +
				"expertise together to offer intelligent, innovative and practical solutions.  Solutions that " +
				"help address the transportation needs of today and tomorrow.\n\n" +
				"" +
				"Good transportation planning and traffic engineering design will seamlessly support and sustain " +
				"development and activity. We deliver high levels of service resulting in mutually beneficial outcomes " +
				"and experiences for all parties involved, and achieve the best return on investment for our " +
				"clients - both in the short and long term.\n\nWe think strategically, because transport is about " +
				"people - what they do and how they do it.  For us, transport is about journeys.  And - whether strategy" +
				" or policy development, motorway improvement, commercial activity, alternative or mixed mode travel, - " +
				"always, our focus is on making each and every journey remarkable. A vital component of our delivery " +
				"depends on state-of-the-art technology. We have leading edge software from around the world, and " +
				"are the New Zealand distribution agents for software produced by many international developers.";
				
				aboutTDG.setText(aboutTDGText);
				
		String aboutAppText = "Traffic Design Group Limited are the largest specialist transportation engineering " +
				"consultancy in New Zealand, they have a very large responsibility to accurately provide information " +
				"and data about the roads, traffic and other various pathways in this country. They currently collect " +
				"data on traffic in many various ways, the most widely used is manual counting using a pen and paper, " +
				"this is where they send off a few employees (Who vary in age and knowledge about traffic, sometimes " +
				"they can hire people with disabilities) to a cordoned intersection or public location (e.g: park, " +
				"mall, Aotea Square) and count the number of specified vehicles or pedestrians, the approach, any " +
				"mobility aids the pedestrian, the current weather, time, and other comments that need to be noted, " +
				"though not all of this information is required at every session of counting.\n\n" +
				"" +
				"Curretly all of this data is recorded on the worksheets manually with a pen and paper by assigned " +
				"employee(s) at an intersection, the employee(s) has to keep all the worksheets and then submit the " +
				"completed worksheets to the main TDG office where someone else will process all of this data onto a " +
				".XLS or .CSV spreadsheet to have the data modelled and be readily available for anyone to use or refer " +
				"to, this can be used by TDG, the NZ Government, NZTA, or any other company.\n\n" +
				"" +
				"The current worksheets can run into several problems with lost worksheets, low accuracy of correct " +
				"data, some employees (Particularly people with disabilities) can find it hard to count or use and " +
				"someone has to go through each worksheet again and manually put together all the data and information " +
				"on a spreadsheet. This project is being created to make the ‘pen and paper’ method redundant and have " +
				"an electronic version to replace it. This will mean that with the traffic counting app, the employees " +
				"who count the traffic will be able to count more efficiently and it will be easier to count the traffic.";
		
				aboutApp.setText(aboutAppText);
				
		String aboutTeamText = 
				"Name:		Patrick Dubek\n" +
				"Major(s): 	Software Development\n" +
				"Phone Number:		021 265 2316\n" +
				"E-mail:		dodge_man006@hotmail.com\n\n" +
				"" +
				"Name:		Richard Fong\n" +
				"Major(s):		Software Development\n" +
				"Phone Number:		021 081 83254\n" +
				"E-mail:		xelyfer@gmail.com\n\n" +
				"" +
				"Name:		Jean-Yves Kwibuka\n" +
				"Major(s): 	Software Development & Computational Intelligence\n" +
				"Phone Number:		021 150 0716\n" +
				"E-mail:		jkwibuka@yahoo.co.nz\n\n" +
				"" +
				"Name:		YiXiao (Nigel) Chen\n" +
				"Major(s):		Computer Science\n" +
				"Phone Number:		021 081 61982\n" +
				"E-mail:		nigelc223@gmail.com";
				
				aboutTeam.setText(aboutTeamText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
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

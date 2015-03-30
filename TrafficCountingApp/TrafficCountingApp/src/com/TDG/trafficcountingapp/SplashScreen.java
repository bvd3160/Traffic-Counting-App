package com.TDG.trafficcountingapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

/*
 * @author Jean-Yves Kwibuka 1245654
 * @version 1.0
 * @since November, 2014
 */

public class SplashScreen extends ActionBarActivity {
	
	private ProgressBar loadSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		loadSpinner = (ProgressBar) findViewById(R.id.loadSpinner);
		loadSpinner.setVisibility(View.VISIBLE);
		
		//Thread to time how long the loading screen is displayed
		Thread loadTimer = new Thread(){
			@Override
			public void run(){
				try {
					sleep(6000);
					startActivity(new Intent(getApplicationContext(), MainScreen.class));
					finish();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		};
		loadTimer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
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

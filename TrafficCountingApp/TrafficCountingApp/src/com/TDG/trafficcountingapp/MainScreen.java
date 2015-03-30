package com.TDG.trafficcountingapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
 * @author Jean-Yves Kwibuka 1245654
 * @version 1.0
 * @since December, 2014
 */

public class MainScreen extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
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
	
	public void nonIntersectionCountSetup(View view){
		Intent intent = new Intent(this, CountSetup.class);
		intent.putExtra("nonIntBtn", "nonIntBtn was clicked");
		startActivity(intent);
	}
	public void intersectionCountSetup(View view){
		Intent intent = new Intent(this, CountSetup.class);
		intent.putExtra("intBtn", "intBtn was clicked");
		startActivity(intent);
	}
	
	public void about(View view){
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}
	public void help(View view){
		Intent intent = new Intent(this, Help.class);
		startActivity(intent);
	}
}

package com.TDG.trafficcountingapp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.TDG.trafficcountingapp.CountingScreen;
import com.TDG.trafficcountingapp.R.id;


public class CountingScreenTest extends ActivityInstrumentationTestCase2<CountingScreen> {
	
	private CountingScreen countingScreen;
	private Button btn_direction_nw, btn_direction_n, btn_direction_ne, btn_direction_w,
	 		btn_direction_e, btn_direction_sw, btn_direction_s, btn_direction_se;
	
	public CountingScreenTest() {
		super(CountingScreen.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		countingScreen = getActivity();
		btn_direction_nw = (Button)countingScreen.findViewById(id.cs_btn_direction_nw);
		btn_direction_n = (Button)countingScreen.findViewById(id.cs_btn_direction_n);
	}

	public void testCarNorthWestToNorth(){
		btn_direction_nw.performClick();
		btn_direction_n.performClick();
		
		assertEquals(1, countingScreen.getNorthWestToNorthCar());
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		
		countingScreen = null;
	}
}

package com.victor_fun.android_app_utils.test;

import android.app.Activity;
import android.os.Bundle;

import com.victor_fun.android_app_utils.R;
import com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.countdown.CountDownLatchDemon;
import com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.cyclicbarrier.HorseRace;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.only_text_view);
		
		test();
	}

	private void test() {
		Thread m = new Thread(){

			@Override
			public void run() {
//				testCountDownLatch();
				testCyclicBarrier();
			}
			
		};
		m.start();
	}

	protected void testCyclicBarrier() {
		HorseRace h = new HorseRace();
	}

	protected void testCountDownLatch() {
		CountDownLatchDemon demon = new CountDownLatchDemon();
		try {
			demon.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

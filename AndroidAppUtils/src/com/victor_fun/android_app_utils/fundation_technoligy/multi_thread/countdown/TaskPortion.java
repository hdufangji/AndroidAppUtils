package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;
	
	public TaskPortion(CountDownLatch latch){
		this.latch = latch;
	}

	@Override
	public void run() {
		try{
			doWork();
			latch.countDown();
		} catch (InterruptedException ex) {
			//exit
		}
	}

	private void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		Log.d("", this + "completed");
	}

	@Override
	public String toString() {
		return "TaskPortion [id=" + id + "]";
	}

}

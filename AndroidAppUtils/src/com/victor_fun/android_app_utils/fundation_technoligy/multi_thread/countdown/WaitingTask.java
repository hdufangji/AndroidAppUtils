package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.countdown;

import java.util.concurrent.CountDownLatch;

import android.util.Log;

public class WaitingTask implements Runnable{
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;
	
	public WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			latch.await();
			Log.d("", "Latch barrier passed for " + this);
		} catch (InterruptedException ex) {
			Log.e("", this + "interrupted");
		}
	}

	@Override
	public String toString() {
		return "WaitingTask [id=" + id + "]";
	}
	
}

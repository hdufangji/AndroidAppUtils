package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

public class CountDownLatchDemon {
	static final int SIZE = 100;
	
	public void test() throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		CountDownLatch latch = new CountDownLatch(SIZE);
		
		for (int i = 0; i < 10; i++) {
			exec.execute(new WaitingTask(latch));
		}
		
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new TaskPortion(latch));
		}
		
		Log.d("", "Launched all tasks");
		exec.shutdown();
	}
}

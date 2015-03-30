package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class HorseRace {
	static final String LOG_TAG = HorseRace.class.getSimpleName();
	
	static final int FINISH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();
	private ExecutorService exec = Executors.newCachedThreadPool();
	
	private CyclicBarrier barrier;
	
	public HorseRace(){
		this(7, 200);
	}

	public HorseRace(int nHorses, final int pause) {
		barrier = new CyclicBarrier(nHorses, new Runnable(){

			@Override
			public void run() {
				StringBuilder s = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++) {
					s.append("=");
				}
				Log.d(LOG_TAG, s.toString());
				
				for (Horse horse : horses) {
					Log.d(LOG_TAG, horse.getStrides() + "");
				}
				
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						Log.d(LOG_TAG, horse + "won!");
						exec.shutdown();
						return;
					}
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					Log.d(LOG_TAG, "barrier-action sleep interrupted");
				}
			}
			
		});
		
		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			exec.execute(horse);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nHorses = 7;
		int pause = 200;
		
		new HorseRace(nHorses, pause);

	}
	
	public void test() {
		int nHorses = 7;
		int pause = 200;
		
		new HorseRace(nHorses, pause);

	}

}

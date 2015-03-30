package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
	static int counter = 0;
	final int id = counter++;
	int strides = 0;
	static Random rand = new Random(47);
	static CyclicBarrier barrier;
	

	public Horse(CyclicBarrier b) {
		this.barrier = b;
	}
	
	public synchronized int getStrides(){
		return strides;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += rand.nextInt(3);
				}
				barrier.await();
			}
		} catch (InterruptedException e) {
			// A legitimate way to exit
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
	}
	
	public String tracks() {
		StringBuilder s  = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			s.append("*");
		}
		s.append(id);
		return s.toString();
	}

	@Override
	public String toString() {
		return "Horse [id=" + id + ", strides=" + strides + "]";
	}

}

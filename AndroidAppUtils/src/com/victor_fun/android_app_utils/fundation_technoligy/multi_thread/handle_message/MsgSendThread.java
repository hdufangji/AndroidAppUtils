package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.handle_message;

import android.os.Handler;

public class MsgSendThread extends Thread {
	static final String LOG_TAG = MsgSendThread.class.getSimpleName();
	static final int TEST_DATA = 10000;
	private Handler mHandler;
	
	public MsgSendThread(Handler handler){
		this.mHandler = handler;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (i < TEST_DATA) {
			mHandler.sendMessage(mHandler.obtainMessage(i, i, i, "send massage " + i));
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				i++;
			}
		}
	}
	
}

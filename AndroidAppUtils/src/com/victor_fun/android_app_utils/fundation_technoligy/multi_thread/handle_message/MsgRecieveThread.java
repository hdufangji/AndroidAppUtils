package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.handle_message;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MsgRecieveThread extends Thread {
	static final String LOG_TAG = MsgRecieveThread.class.getSimpleName();
	private Handler mHandler;
	private Handler mMainHandler;

	public MsgRecieveThread(Handler mainHandler){
		this.mMainHandler = mainHandler;
	}
	
	@Override
	public void run() {
		
		mHandler = new MyHandler(mMainHandler);
		
		Log.i(LOG_TAG, "MsgRecieveThread start!");
	}

	public Handler getHandler(){
		return mHandler;
	}
	
	static class MyHandler extends Handler {
		Handler mainHandler;
		MyHandler(Handler m){
			mainHandler = m;
		}
		
		@Override
		public void handleMessage(Message msg) {
			Log.i(LOG_TAG, "handleMessage -> " + msg.toString());
			Message m = mainHandler.obtainMessage();
			m.copyFrom(msg);
			mainHandler.sendMessage(m);
			super.handleMessage(msg);
		}
	}
}

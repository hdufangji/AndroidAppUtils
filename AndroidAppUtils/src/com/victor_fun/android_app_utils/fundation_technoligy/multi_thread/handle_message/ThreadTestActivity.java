package com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.handle_message;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.victor_fun.android_app_utils.R;

public class ThreadTestActivity extends Activity {
	static TextView mTv;
	MsgRecieveThread mMrt;
	MsgSendThread mMst;

//	private Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			mTv.setText(msg.toString());
//		}
//
//	};
	private final Handler mHandler = new MyHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.only_text_view);
		mTv = (TextView) findViewById(R.id.tv);

		mMrt = new MsgRecieveThread(mHandler);
		mMrt.start();
		mMst = new MsgSendThread(mMrt.getHandler());
		mMst.start();
	}

	static class MyHandler extends Handler{
		private WeakReference<Activity> activity;
		
		public MyHandler(WeakReference<Activity> activity){
			this.activity = activity;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if (activity.get() != null) {
				mTv.setText(msg.toString());				
			}
		}
	}
}

package com.victor_fun.android_app_utils.test;

import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor_fun.android_app_utils.R;
import com.victor_fun.android_app_utils.ThreadUtil;
import com.victor_fun.android_app_utils.fundation_technoligy.locale.LocaleDemon;
import com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.countdown.CountDownLatchDemon;
import com.victor_fun.android_app_utils.fundation_technoligy.multi_thread.cyclicbarrier.HorseRace;
import com.victor_fun.android_app_utils.fundation_technoligy.net.InternetAddressDemon;
import com.victor_fun.android_app_utils.media.MediaMetadataRetreiverDemon;

public class TestActivity extends Activity {
	static final String LOG_TAG = TestActivity.class.getSimpleName();
	
	TextView mTv;
	ImageView mIv;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			byte[] data = (byte[]) msg.obj;
			mIv.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.only_text_view);
		mTv = (TextView)findViewById(R.id.tv);
		mIv = (ImageView)findViewById(R.id.iv);
//		testLock();
//		testInternetAddress();
//		testLocale();
//		testMediaMetadataRetreiver();
		testThreadUtil();
	}

	private void testThreadUtil() {
		Thread t = new Thread(){
			public void run(){
				byte[] data = ThreadUtil.downloadImage("http://h.hiphotos.baidu.com/image/pic/item/1b4c510fd9f9d72ad752b311d62a2834349bbb8c.jpg");
				
				Log.d(LOG_TAG, "data.length = " + data.length);
				Message msg = Message.obtain();
				msg.obj = data;
				mHandler.sendMessage(msg);
			}
		};
		t.start();
		
	}

	private void testMediaMetadataRetreiver() {
		MediaMetadataRetreiverDemon demon = new MediaMetadataRetreiverDemon();
		//testcase: /storage/emulated/0/Music/Nero MediaHome/Maid with the Flaxen Hair.mp3
		//testcase: /storage/emulated/0/DCIM/100MEDIA/VIDEO0001.mp4
		demon.getMetadata("/storage/emulated/0/Music/Nero MediaHome/Maid with the Flaxen Hair.mp3");
	}

	private void testLocale() {
		LocaleDemon demon = new LocaleDemon();
		Log.d(LOG_TAG, demon.getLocale());
		mTv.setText(demon.getLocale());
	}

	private void testInternetAddress() {
		InternetAddressDemon demon = new InternetAddressDemon();
		try {
			demon.getHostAddressByName("www.sina.com.cn");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void testLock() {
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

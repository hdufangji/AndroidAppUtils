package com.victorFun.androidapputils.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.victorFun.androidapputils.R;
import com.victorFun.androidapputils.file.SimpleFileObserver;
import com.victorFun.androidapputils.file.SimpleFileObserver.onFileChangedListener;
import com.victorFun.androidapputils.fundation_technoligy.android.pair.PairDemonActivity;

public class FileTestActivity extends Activity implements onFileChangedListener {
	static final String LOG_TAG = FileTestActivity.class.getSimpleName();
	static SimpleFileObserver fileObserver = null;
	TextView tv;
	StringBuffer sb = new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.only_text_view);
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		}
		
		tv = (TextView)findViewById(R.id.tv);
		tv.setText(Environment.getExternalStorageDirectory().toString());
		
		if(fileObserver == null){
			fileObserver = new SimpleFileObserver(Environment.getExternalStorageDirectory().toString()+"/");			
		}
		fileObserver.setOnFileChangedListener(this);
		fileObserver.startWatching();
	}
	
	@Override
	public void onFileChanged(int event, String path) {
		Log.e(LOG_TAG, path + " = " + event);
//		tv.setText(path + " = " + event);
		sb.append(path + "\n");
	}

	@Override
	protected void onResume() {
		if(!sb.toString().equals(""))
			tv.setText(sb.toString());
		super.onResume();
	}

	@Override
	protected void onPause() {
		
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		fileObserver.stopWatching();
		super.onDestroy();
	}
	
	// Options menu
	private static final int MENU_PREFERENCES    = Menu.FIRST + 0;
	private static final int MENU_RECONNECT_WIFI = Menu.FIRST + 1;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_PREFERENCES,    0, "123")
				.setIcon(android.R.drawable.ic_menu_preferences);
//		menu.add(0, MENU_RECONNECT_WIFI, 0, R.string.menu_reconnect_wifi)
//				.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_PREFERENCES:
			{
				Intent intent = new Intent(
						this, 
						PairDemonActivity.class
						);
				startActivity(intent);
			}
			break;

//		case MENU_RECONNECT_WIFI:
//			if (mServiceStatus.isServerRunning(mService)) {
//				try {
//					mService.reconnectWiFi();
//				} catch (RemoteException e) {
//					Log.e(LOG_TAG, e.toString());
//				}
//			}
//			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}

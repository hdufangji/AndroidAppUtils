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
import com.victorFun.androidapputils.file.service.FileModificationService;
import com.victorFun.androidapputils.fundation_technoligy.android.pair.PairDemonActivity;

public class FileTestActivity extends Activity implements onFileChangedListener {
	static final String LOG_TAG = FileTestActivity.class.getSimpleName();

	TextView tv;
	StringBuffer sb = new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_text_view);
		
		tv = (TextView)findViewById(R.id.tv);
		tv.setText(Environment.getExternalStorageDirectory().toString());
		
		startService(new Intent(this, FileModificationService.class));
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
		stopService(new Intent(this, FileModificationService.class));
		super.onDestroy();
	}
}

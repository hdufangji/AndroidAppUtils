package com.victorFun.androidapputils.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.victorFun.androidapputils.R;
import com.victorFun.androidapputils.file.RecursiveFileObserver;
import com.victorFun.androidapputils.file.RecursiveFileObserver.onFileChangedListener;

public class FileTestActivity extends Activity implements onFileChangedListener {
	static final String LOG_TAG = FileTestActivity.class.getSimpleName();
	RecursiveFileObserver fileObserver;
	TextView tv;
	StringBuffer sb = new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_text_view);
		
		tv = (TextView)findViewById(R.id.tv);
		tv.setText(Environment.getExternalStorageDirectory().toString());
		
		fileObserver = new RecursiveFileObserver(Environment.getExternalStorageDirectory().toString());
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
}

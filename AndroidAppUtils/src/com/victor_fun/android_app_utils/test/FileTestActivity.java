package com.victor_fun.android_app_utils.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.victor_fun.android_app_utils.R;
import com.victor_fun.android_app_utils.file.SimpleFileObserver.onFileChangedListener;
import com.victor_fun.android_app_utils.file.service.FileModificationService;

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
		
		startService(new Intent(this.getApplicationContext(), FileModificationService.class));
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
		stopService(new Intent(this.getApplicationContext(), FileModificationService.class));
		super.onDestroy();
	}
}

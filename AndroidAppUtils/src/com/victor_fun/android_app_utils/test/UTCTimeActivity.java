package com.victor_fun.android_app_utils.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.victor_fun.android_app_utils.R;
import com.victor_fun.android_app_utils.media.image.PhotoDetailInfo;
import com.victor_fun.android_app_utils.media.image.PhotoServer;

public class UTCTimeActivity extends Activity{
	static final String LOG_TAG = FileTestActivity.class.getSimpleName();

	TextView tv;
	StringBuffer sb = new StringBuffer();
	
	private PhotoServer mPhotoServer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_text_view);
		
		tv = (TextView)findViewById(R.id.tv);
		tv.setText(Environment.getExternalStorageDirectory().toString());
		
		mPhotoServer = new PhotoServer();
		PhotoDetailInfo info = mPhotoServer.getPhotoDetailInfoByPath(this, "/storage/emulated/0/DSC_0019.jpg");
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
		super.onDestroy();
	}
}

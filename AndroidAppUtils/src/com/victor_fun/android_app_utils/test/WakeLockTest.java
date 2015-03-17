package com.victor_fun.android_app_utils.test;

import android.app.Activity;
import android.os.Bundle;

import com.victor_fun.android_app_utils.uikit.WakeLockUtil;

public class WakeLockTest extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WakeLockUtil util = new WakeLockUtil(this);
	}

}

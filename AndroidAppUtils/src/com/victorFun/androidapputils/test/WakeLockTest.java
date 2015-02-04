package com.victorFun.androidapputils.test;

import android.app.Activity;
import android.os.Bundle;

import com.victorFun.androidapputils.uikit.WakeLockUtil;

public class WakeLockTest extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WakeLockUtil util = new WakeLockUtil(this);
	}

}

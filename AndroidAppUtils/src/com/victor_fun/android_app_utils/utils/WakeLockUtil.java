package com.victor_fun.android_app_utils.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class WakeLockUtil {
	static final String LOG_TAG = WakeLockUtil.class.getSimpleName();
	
	
	private PowerManager mPowerManager;
	private WakeLock mWakeLock;
	
	public WakeLockUtil(Context ctx){
		mPowerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, ctx.getPackageName());
	}
	
	private void releaseWakeLock() {
		if (mWakeLock.isHeld()) {
			mWakeLock.release();
			if ((mWakeLock.isHeld())) {
				Log.w(LOG_TAG, "Failed to release lock.");
			}
		}
	}

	private void acquireWakeLock() {
		if (mWakeLock.isHeld() == false) {
			mWakeLock.acquire();
			if ((mWakeLock.isHeld())) {
				Log.w(LOG_TAG, "success acquire wake lock.");
			}
		}
	}
}

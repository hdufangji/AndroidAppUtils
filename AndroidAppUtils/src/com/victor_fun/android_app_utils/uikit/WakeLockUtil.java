package com.victor_fun.android_app_utils.uikit;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Build;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;

/**
 * WakeLockUtil is a util for wifi lock and power lock(screen lock).<br>
 * wifi lock: wifiLock() and wifiUnlock()<br>
 * power lock has two ways powerLock() and powerUnlock(). When we use this function, do not forget to add permission: android.permission.WAKE_LOCK<br>
 * we can also use setKeepScreenOn(Activity activity, boolean flag)
 * 
 * @author VFang
 */
public class WakeLockUtil {
	Context mContext = null;
	WifiLock wifiLock = null;
	WakeLock wakeLock = null;

	public WakeLockUtil(Context context) {
		mContext = context;
	}

	public void wifiLock() {
		if(mContext  == null)
			return;
		try {
			wifiLock = ((WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE)).createWifiLock(  Build.VERSION.SDK_INT < 12 ? WifiManager.WIFI_MODE_FULL : WifiManager.WIFI_MODE_FULL_HIGH_PERF , "BIUWifiLock");
			wifiLock.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void powerLock() {
		if(mContext  == null)
			return;
		try {
			wakeLock = ((PowerManager) mContext
					.getSystemService(Context.POWER_SERVICE)).newWakeLock(
					PowerManager.SCREEN_DIM_WAKE_LOCK, "BIUWakeLock");
//			if (Build.VERSION.SDK_INT >= 17)
//				((PowerManager) mContext
//						.getSystemService(Context.POWER_SERVICE))
//						.wakeUp(SystemClock.uptimeMillis());
			wakeLock.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void wifiUnlock() {
		if (wifiLock != null && wifiLock.isHeld()) {
			wifiLock.release();
			wifiLock = null;
		}
	}

	public void powerUnlock() {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
			wakeLock = null;
		}
	}
	
	public void setKeepScreenOn(Activity activity, boolean flag){
		if(activity != null){
			if(flag){
				activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);				
			}else{
				activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}
}
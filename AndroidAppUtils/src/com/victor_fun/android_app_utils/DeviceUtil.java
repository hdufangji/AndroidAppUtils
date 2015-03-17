package com.victor_fun.android_app_utils;

import org.json.JSONException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.StatFs;
import android.util.Pair;

public class DeviceUtil {
	public static Pair<String, String> getDeviceInfo(Context ctx){
		String deviceName = android.os.Build.MODEL;
		String deviceDescription = android.os.Build.DEVICE + android.os.Build.PRODUCT;
		PackageInfo pinfo = null;
		int versionNumber = 0;
		try {
			pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			versionNumber = pinfo.versionCode;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return new Pair<String, String>(deviceName, deviceDescription);
	}

	/**
	 * get device capacity
	 * @param ctx
	 * @param statFs
	 * @return Pair<Long, Long>: first Long is total, second Long is free
	 * @throws JSONException
	 */
	public static Pair<Long, Long> getDeviceCapacity(Context ctx, StatFs statFs){
		long total, free;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			total = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
			free = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
		}
		else {
			total = (long) statFs.getBlockCount() * (long) statFs.getBlockSize();
			free = (long) statFs.getAvailableBlocks() * (long) statFs.getBlockSize();
		}

		return new Pair<Long, Long>(total, free);
	}
}

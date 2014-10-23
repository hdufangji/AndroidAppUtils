package com.victorFun.androidapputils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UUIDUtil {
	public static String generateUUID(Context context) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context); 
	    String identity = preference.getString("deviceId", null);
	    if (identity == null) {
	        identity = java.util.UUID.randomUUID().toString();
	        preference.edit().putString("deviceId", identity);
	    }
	    return identity;
	}
}

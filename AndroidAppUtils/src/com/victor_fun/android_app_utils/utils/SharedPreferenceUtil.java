package com.victor_fun.android_app_utils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	public static void setSharedPreferenceString(Context ctx, String preferenceName, String preferenceKey, String preferenceValue){
		Editor editor = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit();
		editor.putString(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	public static String getSharedPreferenceString(Context ctx, String preferenceName, String preferenceKey){
		SharedPreferences sharedPreference = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		return sharedPreference.getString(preferenceKey, "");
	}
	
	public static void setSharedPreferenceBoolean(Context ctx, String preferenceName, String preferenceKey, boolean preferenceValue){
		Editor editor = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit();
		editor.putBoolean(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	public static boolean getSharedPreferenceBoolean(Context ctx, String preferenceName, String preferenceKey){
		SharedPreferences sharedPreference = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		return sharedPreference.getBoolean(preferenceKey, false);
	}
}

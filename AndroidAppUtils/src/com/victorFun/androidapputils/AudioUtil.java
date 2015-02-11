package com.victorFun.androidapputils;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

public class AudioUtil {
	public static Pair<Integer, Long> getDeviceAudioNumberAndSize(Context ctx, Uri uri){
		String[] projection = MediaSQLiteUtil.ItemAudioProjection;
		
		return MediaSQLiteUtil.getDeviceMediaNumberAndSize(ctx, uri, projection);
	}
}

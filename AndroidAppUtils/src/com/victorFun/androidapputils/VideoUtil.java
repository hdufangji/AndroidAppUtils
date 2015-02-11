package com.victorFun.androidapputils;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

public class VideoUtil {
	
	public static Pair<Integer, Long> getDeviceVideoNumberAndSize(Context ctx, Uri uri){
		String[] projection = MediaSQLiteUtil.ItemVideoProjection;
		
		return MediaSQLiteUtil.getDeviceMediaNumberAndSize(ctx, uri, projection);
	}
}

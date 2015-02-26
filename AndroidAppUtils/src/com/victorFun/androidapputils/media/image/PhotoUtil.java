package com.victorFun.androidapputils.media.image;

import org.json.JSONException;

import com.victorFun.androidapputils.media.MediaSQLiteUtil;


import android.content.Context;
import android.net.Uri;
import android.util.Pair;

public class PhotoUtil {

	public static Pair<Integer, Long> getDevicePhotoNumberAndSize(Context ctx, Uri uri){
		String[] projection = MediaSQLiteUtil.ItemImageProjection;
		
		return MediaSQLiteUtil.getDeviceMediaNumberAndSize(ctx, uri, projection);
	}
	
	public static String getDeviceAllPhotoInfo(Context ctx) throws JSONException{
		
		
		return null;
	}
}

package com.victor_fun.android_app_utils.utils;

import java.util.Locale;

import android.webkit.MimeTypeMap;

public class MimeTypeUtil {
	public static String getMimeType(String url)
	{
	    String type = null;
	    String extension = PathUtil.getFileExtensionFromPath(url).toLowerCase(Locale.getDefault());
	    if (extension != null) {
	        MimeTypeMap mime = MimeTypeMap.getSingleton();
	        type = mime.getMimeTypeFromExtension(extension);
	    }
	    return type;
	}
	
	public static String getBigType(String mimeType){
		if (mimeType != null) {
			return mimeType.substring(0, mimeType.indexOf("/"));			
		} else
			return null;
	}
	
	public static String getSubType(String mimeType){
		return mimeType.substring(mimeType.indexOf("/"), mimeType.length());
	}
}

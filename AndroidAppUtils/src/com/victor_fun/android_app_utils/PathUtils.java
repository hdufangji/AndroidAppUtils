package com.victor_fun.android_app_utils;

public class PathUtils {
	public final static String	ROOT				= "/";

	public static String pathAppend(String base, String path) {
		if (path == null || path.length() == 0)
			return base;
		if (base.endsWith("/"))
			base = base.substring(0, base.length() - 1);
		if (path.startsWith("/"))
			path = path.substring(1);
		return base + "/" + path;
	}

	public static String getParent(String path) {
		int index = path.lastIndexOf('/');
		if (index <= 0) {
			return PathUtils.ROOT;
		}
		return path.substring(0, index);
	}

	public static String getName(String path) {
		if (path.endsWith("/"))
			return "";
		int index = path.lastIndexOf('/');
		if (index >= 0)
			return path.substring(index + 1);
		return "";
	}

	public static String getRoot(String path) {
		if (path.equals(PathUtils.ROOT))
			return path;
		if (path.startsWith("/"))
			path = path.substring(1);
		int index = path.indexOf('/');
		if (index < 0)
			return path;
		return path.substring(0, index);
	}

	public static boolean isRoot(String path) {
		return ROOT.equals(path);
	}
	
	public static void changeOnedrivePath(String path){
		if(path.contains("/.meta")){
			path.replaceAll("/.meta", "/_meta");
		}
		
		if(path.contains("/.data")){
			path.replaceAll("/.data", "/_data");
		}
		
		if(path.contains("/.cache")){
			path.replaceAll("/.cache", "/_cache");
		}
	}
}

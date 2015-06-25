package com.victor_fun.android_app_utils.utils;

import java.io.File;

import android.text.TextUtils;

public class PathUtil {
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
			return PathUtil.ROOT;
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
		if (path.equals(PathUtil.ROOT))
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
	
	/**
     * Returns the file extension or an empty string iff there is no
     * extension. This method is a convenience method for obtaining the
     * extension of a url and has undefined results for other Strings.
     * @param path
     * @return The file extension of the given url.
     */
    public static String getFileExtensionFromPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            int fragment = path.lastIndexOf('#');
            if (fragment > 0) {
                path = path.substring(0, fragment);
            }

            int query = path.lastIndexOf('?');
            if (query > 0) {
                path = path.substring(0, query);
            }

            int filenamePos = path.lastIndexOf('/');
            String filename =
                0 <= filenamePos ? path.substring(filenamePos + 1) : path;

            // if the filename contains special characters, we don't
            // consider it valid for our matching purposes:
            if (!filename.isEmpty()) {
//                Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", filename)
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return "";
    }
    
    public static boolean isFolder(String path) {
    	File f = new File(path);
    	return f.isDirectory();
    }

	public static boolean isFileExist(String path) {
		File f = new File(path);
		return f.exists();
	}
}

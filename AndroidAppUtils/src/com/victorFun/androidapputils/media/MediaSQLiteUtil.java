package com.victorFun.androidapputils.media;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;

public class MediaSQLiteUtil {
	public static final String[] ItemImageProjection = new String[] {
		MediaStore.Images.ImageColumns.DISPLAY_NAME,
		MediaStore.Images.ImageColumns.DATA,
		MediaStore.Images.ImageColumns.SIZE,
		MediaStore.Images.ImageColumns._ID
	};
	
	public static final String[] ItemVideoProjection = new String[] {
		MediaStore.Video.VideoColumns.DISPLAY_NAME,
		MediaStore.Video.VideoColumns.DATA,
		MediaStore.Video.VideoColumns.SIZE,
		MediaStore.Video.VideoColumns._ID
	};
	
	public static final String[] ItemAudioProjection = new String[] {
		MediaStore.Audio.AudioColumns.DISPLAY_NAME,
		MediaStore.Audio.AudioColumns.DATA,
		MediaStore.Audio.AudioColumns.SIZE,
		MediaStore.Audio.AudioColumns._ID
	};
	
	/**
	 * 
	 * @param ctx
	 * @param uri The URI, using the content:// scheme, for the content to retrieve.
	 * @param projection
	 * @return Pair<Integer, Long> Integer is number, Long is size(bytes)
	 */
	public static Pair<Integer, Long> getDeviceMediaNumberAndSize(Context ctx, Uri uri, String[] projection){
		int photoNum = 0;
		long photoSize = 0;
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			cursor = mContentResolver.query(uri, projection, null, null,
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					File file = null;
					String data = cursor.getString(1);
					if (data == null)
						continue;

					file = new File(data);

					if (file == null || file.exists() == false
							|| file.isHidden() || file.isDirectory())
						continue;

					photoNum++;
					photoSize += cursor.getLong(2);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return new Pair<Integer, Long>(photoNum, photoSize);
	}
	
	public static ArrayList<String> getDeviceMediaFilePath(Context ctx, String[][] projections){
		ArrayList<String> paths = new ArrayList<String>();
		
		for(int i = projections.length; i < projections.length; i++){
			String[] projection = projections[i];
			paths.addAll(getDeviceMediaFilePath(ctx, projection));
		}
		
		return paths;
	}
	
	public static ArrayList<String> getDeviceMediaFilePath(Context ctx, String[] projection){
		ArrayList<String> paths = new ArrayList<String>();
		
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			cursor = mContentResolver.query(null, projection, null, null,
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					File file = null;
					String data = cursor.getString(1);
					if (data == null)
						continue;

					file = new File(data);

					if (file == null || file.exists() == false
							|| file.isHidden() || file.isDirectory())
						continue;

					paths.add(cursor.getString(1));
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return paths;
	}
	
}

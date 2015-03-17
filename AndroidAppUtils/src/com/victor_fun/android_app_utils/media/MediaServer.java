package com.victor_fun.android_app_utils.media;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

public abstract class MediaServer {
	
	public static final String[] ItemImageProjection = new String[] {
		MediaStore.Images.ImageColumns.DISPLAY_NAME,
		MediaStore.Images.ImageColumns.DATA,
		MediaStore.Images.ImageColumns.SIZE,
		MediaStore.Images.ImageColumns._ID,
		MediaStore.Images.ImageColumns.MIME_TYPE
	};
	
	public static final String[] ItemVideoProjection = new String[] {
		MediaStore.Video.VideoColumns.DISPLAY_NAME,
		MediaStore.Video.VideoColumns.DATA,
		MediaStore.Video.VideoColumns.SIZE,
		MediaStore.Video.VideoColumns._ID,
		MediaStore.Video.VideoColumns.MIME_TYPE
	};
	
	public static final String[] ItemAudioProjection = new String[] {
		MediaStore.Audio.AudioColumns.DISPLAY_NAME,
		MediaStore.Audio.AudioColumns.DATA,
		MediaStore.Audio.AudioColumns.SIZE,
		MediaStore.Audio.AudioColumns._ID,
		MediaStore.Audio.AudioColumns.MIME_TYPE
	};
	
	public abstract Pair<Integer, Long> getNumberAndSize(Context ctx, Uri uri);
	
	public abstract long getContentIdByFilePath(Context ctx, String filePath);
	
	public abstract Bitmap getBitmapByContentid(Context ctx, long contentId);
	
	public abstract ArrayList<FileInfo> getDeviceMediaFileInfo(Context ctx, Uri uri);
	
	public abstract MediaDetailInfo getMediaDetailInfoById(Context ctx, long id);
	
	public abstract FileInfo getFileInfoByPath(Context ctx, String path);
	
	public byte[] getThumbnailByFilePath(Context ctx, String filePath) {
		File f = new File(filePath);
		if(!f.exists()){
			return null;
		}
			
		long id = getContentIdByFilePath(ctx, filePath);
		if (id == -1) {
			return null;
		}

		Bitmap bitmap = getBitmapByContentid(ctx, id);
		
		if (bitmap == null)
			return null;
		
		return converteBitmap2ByteArray(bitmap);
	}
	
	/**
	 * //TODO this function is waste allactions, need to optimize
	 * @param bitmap
	 * @return
	 */
	public byte[] converteBitmap2ByteArray(Bitmap bitmap) {
		if(bitmap == null)
			return null;
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param uri The URI, using the content:// scheme, for the content to retrieve.
	 * @param projection
	 * @return Pair<Integer, Long> Integer is number, Long is size(bytes)
	 */
	public Pair<Integer, Long> getDeviceMediaNumberAndSize(Context ctx, Uri uri, String[] projection){
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
	
	public ArrayList<FileInfo> getDeviceMediaFileInfo(Context ctx, Uri uri, String[] projection){
		ArrayList<FileInfo> paths = new ArrayList<FileInfo>();
		
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

					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileName(cursor.getString(0));
					fileInfo.setFilePath(cursor.getString(1));
					fileInfo.setSize(cursor.getLong(2));
					fileInfo.setId(cursor.getLong(3));
					fileInfo.setMimeType(cursor.getString(4));
					paths.add(fileInfo);
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return paths;
	}
	
	public FileInfo getDeviceMediaFileInfoByPath(Context ctx, Uri uri, String[] projection, String selection){
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			cursor = mContentResolver.query(uri, projection, selection, null,
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();

				File file = null;
				String data = cursor.getString(1);
				if (data == null)
					return null;

				file = new File(data);

				if (file == null || file.exists() == false || file.isHidden()
						|| file.isDirectory())
					return null;

				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileName(cursor.getString(0));
				fileInfo.setFilePath(cursor.getString(1));
				fileInfo.setSize(cursor.getLong(2));
				fileInfo.setId(cursor.getLong(3));
				fileInfo.setMimeType(cursor.getString(4));
				return fileInfo;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return null;
	}

	//TODO this function need to be verified
	public String getMediaLocationInfo(Context ctx, long latitude, long longitude) {
		Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(" ");
                }
                sb.append(address.getCountryName()).append(" ");
                sb.append(address.getLocality()).append(" ");
                sb.append(address.getSubLocality());
                Log.d("test", "address:" + sb.toString());
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return null;
	}
}

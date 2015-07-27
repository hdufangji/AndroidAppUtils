package com.victor_fun.android_app_utils.media.image;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

import com.victor_fun.android_app_utils.media.FileInfo;
import com.victor_fun.android_app_utils.media.MediaDetailInfo;
import com.victor_fun.android_app_utils.media.MediaServer;
import com.victor_fun.android_app_utils.media.MediaType;
import com.victor_fun.android_app_utils.utils.DateUtil;

public class PhotoServer extends MediaServer {
	static final String LOG_TAG = PhotoServer.class.getSimpleName();
	
	public static final String[] ItemImageDetailProjection = new String[] {
		MediaStore.Images.ImageColumns.DISPLAY_NAME,
		MediaStore.Images.ImageColumns.DATA,
		MediaStore.Images.ImageColumns.SIZE,
		MediaStore.Images.ImageColumns._ID,
		MediaStore.Images.ImageColumns.DATE_MODIFIED,
		MediaStore.Images.ImageColumns.WIDTH,
		MediaStore.Images.ImageColumns.HEIGHT,
		MediaStore.Images.ImageColumns.LATITUDE,
		MediaStore.Images.ImageColumns.LONGITUDE,
		MediaStore.Images.ImageColumns.DATE_TAKEN,
		MediaStore.Images.ImageColumns.DATE_ADDED
	};

	public String getDeviceAllPhotoInfo(Context ctx) throws JSONException{
		
		
		return null;
	}

	public ArrayList<PhotoDetailInfo> getPhotoDetailInfoByPath(Context ctx, JSONArray pathList) throws JSONException {
		ArrayList<PhotoDetailInfo> photoDetailInfos = new ArrayList<PhotoDetailInfo>();
		for(int i = 0; i < pathList.length(); i++){
			String path = pathList.getString(i);
			
			File f = new File(path);
			if(!f.exists()){
				continue;
			}
			
			PhotoDetailInfo info = getPhotoDetailInfoByPath(ctx, path);
			if(info != null){
				photoDetailInfos.add(info);				
			}else{
				photoDetailInfos.add(new PhotoDetailInfo());
			}
				
		}
		return photoDetailInfos;
	}

	public PhotoDetailInfo getPhotoDetailInfoByPath(Context ctx, String path) {
		long id = getContentIdByFilePath(ctx, path);
		if (id == -1){
			return null;
		}
		
		return (PhotoDetailInfo) getMediaDetailInfoById(ctx, id);
	}

	@Override
	public Pair<Integer, Long> getNumberAndSize(Context ctx, Uri uri){
		String[] projection = ItemImageProjection;
		
		return getDeviceMediaNumberAndSize(ctx, uri, projection);
	}
	
	@Override
	public long getContentIdByFilePath(Context ctx, String filePath) {
		long id = -1;
		Cursor cursor = null;
		
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		
		try{
			cursor = ctx.getContentResolver().query(uri,
					new String[] { MediaStore.Images.Media._ID },
					MediaStore.Images.Media.DATA + "=? ",
					new String[] { filePath }, null);

			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();

				id = cursor.getLong(0);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return id;
	}

	@Override
	public Bitmap getBitmapByContentid(Context ctx, long contentId){
		return MediaStore.Images.Thumbnails.getThumbnail(ctx.getContentResolver(),
				contentId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
	}

	@Override
	public ArrayList<FileInfo> getDeviceMediaFileInfo(Context ctx, Uri uri) {
		ArrayList<FileInfo> paths = new ArrayList<FileInfo>();
		
		paths.addAll(getDeviceMediaFileInfo(ctx, uri, ItemImageProjection));
		
		return paths;
	}

	@Override
	public MediaDetailInfo getMediaDetailInfoById(Context ctx, long id) {
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			try{
				cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ItemImageDetailProjection, 
						MediaStore.Images.Media._ID + "=? ", 
						new String [] {id + ""},
						null);	
			} catch (Exception e) {
				return null;
			}
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();

				File file = null;
				String data = cursor.getString(1);
				if (data == null)
					return null;

				file = new File(data);

				if (file == null || file.exists() == false || file.isHidden() || file.isDirectory())
					return null;

				PhotoDetailInfo tempInfo = new PhotoDetailInfo();
				tempInfo.setName(cursor.getString(0));
				tempInfo.setPath(cursor.getString(1));
				tempInfo.setSize(cursor.getLong(2));
				tempInfo.setId(cursor.getLong(3));
				tempInfo.setModifiedDate(cursor.getLong(4));
				tempInfo.setWidth(cursor.getInt(5));
				tempInfo.setHeight(cursor.getInt(6));
				long la = cursor.getLong(7);
				long lo = cursor.getLong(8);
				tempInfo.setLatitude(la);
				tempInfo.setLongitude(lo);
				
				Log.d(LOG_TAG, "DATE_TAKEN" + cursor.getLong(9) + DateUtil.parseLong2Date(cursor.getLong(9)));
				Log.d(LOG_TAG, "DATE_ADDED" + cursor.getLong(10) + DateUtil.parseLong2Date(cursor.getLong(10) * 1000));
				// TODO
//				String locationName = MediaSQLiteUtil.getMediaLocationInfo(ctx, la, lo);
//				tempInfo.setLocation(locationName);
				tempInfo.setLocation("");
				// TODO
				tempInfo.setCameraInfo("");
				return tempInfo;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return null;
	}
	
	@Override
	public FileInfo getFileInfoByPath(Context ctx, String path){
		String selection = MediaStore.Images.Media.DATA + " = '" + path + "'";
		
		FileInfo info = getDeviceMediaFileInfoByPath(ctx, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ItemImageProjection, selection);
		info.setType(MediaType.PHOTO.toString());
		
		return info;
	}
}

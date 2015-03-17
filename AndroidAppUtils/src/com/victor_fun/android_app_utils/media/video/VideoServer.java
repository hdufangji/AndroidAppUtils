package com.victor_fun.android_app_utils.media.video;

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
import android.util.Pair;

import com.victor_fun.android_app_utils.media.FileInfo;
import com.victor_fun.android_app_utils.media.MediaDetailInfo;
import com.victor_fun.android_app_utils.media.MediaServer;
import com.victor_fun.android_app_utils.media.MediaType;

public class VideoServer extends MediaServer {
	public static final String[] ItemVideoDetailProjection = new String[] {
		MediaStore.Video.VideoColumns.DISPLAY_NAME,
		MediaStore.Video.VideoColumns.DATA,
		MediaStore.Video.VideoColumns.SIZE,
		MediaStore.Video.VideoColumns._ID,
		MediaStore.Video.VideoColumns.DATE_MODIFIED,
		MediaStore.Video.VideoColumns.WIDTH,
		MediaStore.Video.VideoColumns.HEIGHT,
		MediaStore.Video.VideoColumns.LATITUDE,
		MediaStore.Video.VideoColumns.LONGITUDE,
		MediaStore.Video.VideoColumns.DURATION,
		MediaStore.Video.VideoColumns.DESCRIPTION
	};

	public ArrayList<VideoDetailInfo> getVideoDetailInfoByPath(Context ctx, JSONArray pathList) throws JSONException {
		ArrayList<VideoDetailInfo> videoDetailInfos = new ArrayList<VideoDetailInfo>();
		for(int i = 0; i < pathList.length(); i++){
			String path = pathList.getString(i);
			
			File f = new File(path);
			if(!f.exists()){
				continue;
			}
			
			VideoDetailInfo info = getVideoDetailInfoByPath(ctx, path);
			if(info != null){
				videoDetailInfos.add(info);				
			}else{
				videoDetailInfos.add(new VideoDetailInfo());
			}
		}
		
		return videoDetailInfos;
	}

	public VideoDetailInfo getVideoDetailInfoByPath(Context ctx, String path) {
		long id = getContentIdByFilePath(ctx, path);
		if (id == -1){
			return null;
		}
		
		return (VideoDetailInfo) getMediaDetailInfoById(ctx, id);
	}
	
	@Override
	public Pair<Integer, Long> getNumberAndSize(Context ctx, Uri uri) {
		String[] projection = ItemVideoProjection;
		
		return getDeviceMediaNumberAndSize(ctx, uri, projection);
	}

	@Override
	public long getContentIdByFilePath(Context ctx, String filePath) {
		long id = -1;
		Cursor cursor = null;
		
		Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		
		try{
			cursor = ctx.getContentResolver().query(uri,
					new String[] { MediaStore.Video.Media._ID },
					MediaStore.Video.Media.DATA + "=? ",
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
	public Bitmap getBitmapByContentid(Context ctx, long contentId) {
		return MediaStore.Video.Thumbnails.getThumbnail(ctx.getContentResolver(),
				contentId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
	}

	@Override
	public ArrayList<FileInfo> getDeviceMediaFileInfo(Context ctx, Uri uri) {
		ArrayList<FileInfo> paths = new ArrayList<FileInfo>();
		
		paths.addAll(getDeviceMediaFileInfo(ctx, uri, ItemVideoProjection));		
		
		return paths;
	}

	@Override
	public MediaDetailInfo getMediaDetailInfoById(Context ctx, long id) {
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			try{
				cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, ItemVideoDetailProjection, 
						MediaStore.Video.Media._ID + "=? ", 
						new String [] {id + ""},
						null);				
			} catch (Exception e){
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

				VideoDetailInfo tempInfo = new VideoDetailInfo();
				tempInfo.setName(cursor.getString(0));
				tempInfo.setPath(cursor.getString(1));
				tempInfo.setSize(cursor.getLong(2));
				tempInfo.setId(cursor.getLong(3));
				tempInfo.setModifiedDate(cursor.getLong(4));
				tempInfo.setWidth(cursor.getInt(5));
				tempInfo.setHeight(cursor.getInt(6));
				tempInfo.setVideoDetail(cursor.getString(10));
				long la = cursor.getLong(7);
				long lo = cursor.getLong(8);
				// TODO
				String locationName = getMediaLocationInfo(ctx, la, lo);
//				tempInfo.setLocation(locationName);
				tempInfo.setLocation("");
				// TODO
				tempInfo.setCameraInfo("");
				tempInfo.setAudioBitrate("");
				tempInfo.setAudioChannels(0);
				
				
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
	public FileInfo getFileInfoByPath(Context ctx, String path) {
		String selection = MediaStore.Video.Media.DATA + " = '" + path + "'";
		
		FileInfo info = getDeviceMediaFileInfoByPath(ctx, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, ItemVideoProjection, selection);
		info.setType(MediaType.VIDEO.toString());
		
		return info;
	}
}

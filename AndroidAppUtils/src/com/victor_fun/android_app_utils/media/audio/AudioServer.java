package com.victor_fun.android_app_utils.media.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.util.Pair;

import com.victor_fun.android_app_utils.media.FileInfo;
import com.victor_fun.android_app_utils.media.MediaDetailInfo;
import com.victor_fun.android_app_utils.media.MediaServer;
import com.victor_fun.android_app_utils.media.MediaType;

public class AudioServer extends MediaServer {
	static String LOG_TAG = AudioServer.class.getSimpleName();
	
	public static final String[] ItemAudioDetailProjection = new String[] {
		MediaStore.Audio.AudioColumns.DISPLAY_NAME,
		MediaStore.Audio.AudioColumns.DATA,
		MediaStore.Audio.AudioColumns.SIZE,
		MediaStore.Audio.AudioColumns._ID,
		MediaStore.Audio.AudioColumns.DATE_MODIFIED,
		MediaStore.Audio.AudioColumns.TRACK,
		MediaStore.Audio.AudioColumns.ALBUM,
		MediaStore.Audio.AudioColumns.DURATION,
		MediaStore.Audio.AudioColumns.ARTIST,
		MediaStore.Audio.AudioColumns.YEAR
	};
//	private String genre;
//	private int bitrate;
//	private int frequency;
//	private int channels;

	public ArrayList<AudioDetailInfo> getAudioDetailInfoByPath(Context ctx, JSONArray pathList) throws JSONException {
		ArrayList<AudioDetailInfo> audioDetailInfos = new ArrayList<AudioDetailInfo>();
		for(int i = 0; i < pathList.length(); i++){
			String path = pathList.getString(i);
			
			File f = new File(path);
			if(!f.exists()){
				continue;
			}
			
			AudioDetailInfo info = getAudioDetailInfoByPath(ctx, path);
			if(info != null){
				audioDetailInfos.add(info);				
			}else{
				audioDetailInfos.add(new AudioDetailInfo());
			}
		}
		return audioDetailInfos;
	}

	public AudioDetailInfo getAudioDetailInfoByPath(Context ctx, String path) {
		long id = getContentIdByFilePath(ctx, path);
		if (id == -1){
			return null;
		}
		
		return (AudioDetailInfo) getMediaDetailInfoById(ctx, id);
	}
	
	/**
	 * TODO
	 * @param ctx
	 * @param genre_id
	 * @return
	 */
	public String getGenreName(Context ctx, long genre_id) {
		String where = Audio.Genres._ID + "=" + genre_id;
		String[] cols = new String[] { Audio.Genres.NAME };
		Uri uri = Audio.Genres.EXTERNAL_CONTENT_URI;
		Cursor cursor = null;
		
		try{
			cursor = ctx.getContentResolver().query(uri, cols, where, null, null);
		} catch (Exception e) {
			return "";
		}
		
		if (cursor.getCount() <= 0) {
			return MediaStore.UNKNOWN_STRING;
		} else {
			cursor.moveToFirst();
			String name = cursor.getString(0);
			cursor.close();
			if (name == null || MediaStore.UNKNOWN_STRING.equals(name)) {
				return MediaStore.UNKNOWN_STRING;
			}
			return name;
		}
	}

//	public static Bitmap getAudioThumbnailById(Context ctx, long id) {
//		InputStream is = queryAudioThumnail(ctx.getContentResolver(), id, false);
//		Bitmap bitmap = BitmapFactory.decodeStream(is);
//		return bitmap;
//	}
	

	
	private InputStream queryAudioThumnail(ContentResolver cr, long id) {
		Cursor c = null;
		long albumID = -1;
		try {
			Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
			// String[] projection = { MediaStore.Audio.AudioColumns.ALBUM_ID };
			c = cr.query(uri, null, null, null, null);
			if (c != null) {
				if (c.moveToFirst()) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						try {
							Log.v(LOG_TAG,
									"audio thumbnail - " + c.getColumnName(i)
											+ "=[" + c.getString(i) + "]");
						} catch (Exception e) {
							Log.v(LOG_TAG,
									"audio thumbnail - " + c.getColumnName(i)
											+ "=???");
						}
					}
					;
					Log.v(LOG_TAG,
							"audio thumbnail -----------------------------");
					albumID = c
							.getLong(c
									.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Failed to read image thumnail for " + id, e);
			return null;
		} finally {
			if (c != null)
				c.close();
		}
		if (albumID == -1) {
			return null;
		}
		String albumArt = null;
		// Uri albumUri = null;
		try {
			Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
			uri = ContentUris.withAppendedId(uri, albumID);
			// String[] projection = { MediaStore.Audio.AudioColumns.ALBUM_ART
			// };
			c = cr.query(uri, null, null, null, null);
			if (c != null) {
				if (c.moveToFirst()) {
					// do {
					HashMap<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < c.getColumnCount(); i++) {
						try {
							map.put(c.getColumnName(i), c.getString(i));
						} catch (Exception e) {
							map.put(c.getColumnName(i), "???");
						}
					}
					;
					for (String key : map.keySet()) {
						Log.v(LOG_TAG,
								"media thumbnail - " + key + "=["
										+ map.get(key) + "]");
					}
					Log.v(LOG_TAG,
							"audio thumbnail -----------------------------");
					// } while (c.moveToNext());
					//change by vic.fang 
					//AudioColumns.ALBUM_ART -> AudioColumns.DATA
					albumArt = c
							.getString(c
									.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
				}
			}
			// albumUri = uri;
		} catch (Exception e) {
			Log.e(LOG_TAG, "Failed to read image thumnail for " + id, e);
			return null;
		} finally {
			if (c != null)
				c.close();
		}

		if(albumArt == null){
			Log.d(LOG_TAG, "Failed to read image thumnail for " + id + ", albumArt is null");
			return null;
		}
			
		
		File albumArtFile = new File(albumArt);
		if (albumArtFile.exists()) {
			try {
				return new FileInputStream(albumArtFile);
			} catch (FileNotFoundException e) {
				Log.e(LOG_TAG, "Failed to get thumbnail for audio " + id, e);
				return null;
			}
		} else {
			// fallback to a private API
			Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
			Uri uri = ContentUris.withAppendedId(sArtworkUri, albumID);
			try {
				return cr.openInputStream(uri);
			} catch (FileNotFoundException e) {
				Log.e(LOG_TAG, "Failed to get thumbnail for audio " + id, e);
				return null;
			}
		}
	}
	
	@Override
	public Pair<Integer, Long> getNumberAndSize(Context ctx, Uri uri) {
		String[] projection = ItemAudioProjection;
		
		return getDeviceMediaNumberAndSize(ctx, uri, projection);
	}

	@Override
	public long getContentIdByFilePath(Context ctx, String filePath) {
		long id = -1;
		Cursor cursor = null;
		
		try{
			Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

			cursor = ctx.getContentResolver().query(uri,
					new String[] { MediaStore.Audio.Media._ID },
					MediaStore.Audio.Media.DATA + "=? ",
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
		Bitmap bitmap = null;
		
		byte[] rawArt;
		BitmapFactory.Options bfo;
		
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();
			
			bfo = new BitmapFactory.Options();

			Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			
			uri = ContentUris.withAppendedId(uri, contentId);
			try {
				mmr.setDataSource(ctx.getApplicationContext(), uri);
			} catch (Exception e) {
				return null;
			}
			rawArt = mmr.getEmbeddedPicture();
		} else {
			return null;
		}
		

		// if rawArt is null then no cover art is embedded in the file or is not
		// recognized as such.
		if (null != rawArt)
			bitmap = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length,
					bfo);
		
		return bitmap;
	}

	@Override
	public ArrayList<FileInfo> getDeviceMediaFileInfo(Context ctx, Uri uri) {
		ArrayList<FileInfo> paths = new ArrayList<FileInfo>();
		
		paths.addAll(getDeviceMediaFileInfo(ctx, uri, ItemAudioProjection));
		
		return paths;
	}

	@Override
	public MediaDetailInfo getMediaDetailInfoById(Context ctx, long id) {
		Cursor cursor = null;
		try {
			ContentResolver mContentResolver = ctx.getContentResolver();
			try{
				cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ItemAudioDetailProjection, 
						MediaStore.Audio.Media._ID + "=? ", 
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
				
				AudioDetailInfo tempInfo = new AudioDetailInfo();
				tempInfo.setName(cursor.getString(0));
				tempInfo.setPath(cursor.getString(1));
				tempInfo.setSize(cursor.getLong(2));
				tempInfo.setId(cursor.getLong(3));
				tempInfo.setModifiedDate(cursor.getLong(4));
				tempInfo.setTrack(cursor.getInt(5));
				tempInfo.setAlbum(cursor.getString(6));
				tempInfo.setDuration(cursor.getInt(7));
				tempInfo.setArtist(cursor.getString(8));
				tempInfo.setYear(cursor.getInt(9));
				
				//TODO genre/frequency/bitrate/channels
				tempInfo.setGenre(getGenreName(ctx, id));
				tempInfo.setFrequency(0);
				tempInfo.setBitrate(0);
				tempInfo.setChannels(0);
				
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
		String selection = MediaStore.Audio.Media.DATA + " = '" + path + "'";
		
		FileInfo info = getDeviceMediaFileInfoByPath(ctx, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ItemAudioProjection, selection);
		info.setType(MediaType.AUDIO.toString());
		
		return info;
	}
}

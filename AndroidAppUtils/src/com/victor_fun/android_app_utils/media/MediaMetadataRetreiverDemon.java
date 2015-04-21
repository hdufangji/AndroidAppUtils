package com.victor_fun.android_app_utils.media;

import android.annotation.SuppressLint;
import android.media.MediaMetadataRetriever;
import android.util.Log;

public class MediaMetadataRetreiverDemon {
	static final String TAG = MediaMetadataRetreiverDemon.class.getSimpleName();
	
	@SuppressLint("NewApi") 
	public void getMetadata(String path){
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();  
		
        Log.d(TAG, "str:" + path);  
        try {  
            mmr.setDataSource(path);  
       
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);  
            Log.d(TAG, "album:" + album);  
            Log.d(TAG, "METADATA_KEY_ALBUMARTIST:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST));
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);  
            Log.d(TAG, "artist:" + artist); 
            Log.d(TAG, "METADATA_KEY_AUTHOR:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
            String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE); // 从api level 14才有，即从ICS4.0才有此功能  
            Log.d(TAG, "bitrate:" +bitrate);  
            Log.d(TAG, "METADATA_KEY_CD_TRACK_NUMBER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
            Log.d(TAG, "METADATA_KEY_COMPILATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPILATION));
            Log.d(TAG, "METADATA_KEY_COMPOSER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER));
            Log.d(TAG, "METADATA_KEY_DATE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));
            Log.d(TAG, "METADATA_KEY_DISC_NUMBER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DISC_NUMBER));
            Log.d(TAG, "METADATA_KEY_DURATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            Log.d(TAG, "METADATA_KEY_GENRE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
            Log.d(TAG, "METADATA_KEY_HAS_AUDIO:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO));
            Log.d(TAG, "METADATA_KEY_HAS_VIDEO:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO));
            Log.d(TAG, "METADATA_KEY_LOCATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION));
            Log.d(TAG, "METADATA_KEY_MIMETYPE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));
        
            Log.d(TAG, "METADATA_KEY_NUM_TRACKS:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS));
            Log.d(TAG, "METADATA_KEY_TITLE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            Log.d(TAG, "METADATA_KEY_VIDEO_HEIGHT:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            Log.d(TAG, "METADATA_KEY_VIDEO_ROTATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));
            Log.d(TAG, "METADATA_KEY_VIDEO_WIDTH:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            Log.d(TAG, "METADATA_KEY_WRITER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WRITER));
            Log.d(TAG, "METADATA_KEY_YEAR:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR));
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}
}

package com.victor_fun.android_app_utils.media.video;

import org.json.JSONException;
import org.json.JSONObject;

import com.victor_fun.android_app_utils.media.MediaDetailInfo;

public class VideoDetailInfo extends MediaDetailInfo{
	private int width;
	private int height;
	private String location;
	private String cameraInfo;
	private String videoDetail;
	private String audioBitrate;
	private int audioChannels;
	
	public VideoDetailInfo() {
		this(0, null, 0, 0, null, 0, 0, null, null, null, null, 0);
	}
	public VideoDetailInfo(long id, String name, long size, long modifiedDate,
			String path) {
		this(id, name, size, modifiedDate, path, 0, 0, null, null, null, null, 0);
	}
	
	//TODO use factory to instead
	public VideoDetailInfo(long id, String name, long size, long modifiedDate,
			String path, int width, int height, String location, String cameraInfo, 
			String videoDetail, String audioBitrate, int audioChannels) {
		super(id, name, size, modifiedDate, path);
		this.width = width;
		this.height = height;
		this.location = location;
		this.cameraInfo = cameraInfo;
		this.videoDetail = videoDetail;
		this.audioBitrate = audioBitrate;
		this.audioChannels = audioChannels;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCameraInfo() {
		return cameraInfo;
	}
	public void setCameraInfo(String cameraInfo) {
		this.cameraInfo = cameraInfo;
	}
	public String getVideoDetail() {
		return videoDetail;
	}
	public void setVideoDetail(String videoDetail) {
		this.videoDetail = videoDetail;
	}
	public String getAudioBitrate() {
		return audioBitrate;
	}
	public void setAudioBitrate(String audioBitrate) {
		this.audioBitrate = audioBitrate;
	}
	public int getAudioChannels() {
		return audioChannels;
	}
	public void setAudioChannels(int audioChannels) {
		this.audioChannels = audioChannels;
	}
	public static Object Parse(VideoDetailInfo info) {
		JSONObject json = new JSONObject();
		
		try {
			json.put("id", info.getId());
			
			if(info.getName() == null){
				json.put("name", "");
			}else{
				json.put("name", info.getName());				
			}
			
			if(info.getPath() == null){
				json.put("path", "");
			}else{
				json.put("path", info.getPath());		
			}
			
			json.put("size", info.getSize());
			
			json.put("width", info.getWidth());
			json.put("height", info.getHeight());
			json.put("modified_date", info.getModifiedDate());
			
			if(info.getLocation() == null){
				json.put("location", "");
			}else{
				json.put("location", info.getLocation());				
			}
			
			if(info.getCameraInfo() == ""){
				json.put("camera_information", "");
			}else{
				json.put("camera_information", info.getCameraInfo());				
			}
			
			if(info.getVideoDetail() == null){
				json.put("video_detail", "");
			}else{
				json.put("video_detail", info.getVideoDetail());				
			}
			
			json.put("audio_channels", info.getAudioChannels());
			
			if(info.getAudioBitrate() == null){
				json.put("audio_bitrate", "");
			}else{
				json.put("audio_bitrate", info.getAudioBitrate());				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	
}

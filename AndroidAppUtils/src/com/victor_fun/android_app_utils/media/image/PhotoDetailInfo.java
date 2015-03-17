package com.victor_fun.android_app_utils.media.image;

import org.json.JSONException;
import org.json.JSONObject;

import com.victor_fun.android_app_utils.media.MediaDetailInfo;

public class PhotoDetailInfo extends MediaDetailInfo{
	private int width;
	private int height;
	private long latitude;
	private long longitude;
	private String location;
	private String cameraInfo;
	
	public PhotoDetailInfo() {
		this(0, null, 0, 0, null, 0, 0, 0, 0, null, null);
	}

	public PhotoDetailInfo(long id, String name, long size, long modifiedDate,
			String path) {
		this(id, name, size, modifiedDate, path, 0, 0, 0, 0, null, null);
	}
	
	public PhotoDetailInfo(long id, String name, long size, long modifiedDate,
			String path, int width, int height, long latitude, long longitude, String location, String cameraInfo) {
		super(id, name, size, modifiedDate, path);
		this.width = width;
		this.height = height;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.cameraInfo = cameraInfo;
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

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
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

	public static Object Parse(PhotoDetailInfo info) {
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
			
			json.put("modified_date", info.getModifiedDate());

			json.put("width", info.getWidth());
			json.put("height", info.getHeight());
			
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
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
}

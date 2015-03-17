package com.victor_fun.android_app_utils.media;

import org.json.JSONException;
import org.json.JSONObject;

public class FileInfo {
	private String filePath;
	private long id;
	private long size;
	private String fileName;
	private String mimeType;
	private String type;
	
	public FileInfo() {
		super();
		this.filePath = "";
		this.id = 0;
		this.size = 0;
		this.fileName = "";
		this.mimeType = "";
		this.type = "";
	}

	public FileInfo(long id, String fileName, String filePath, long size, String mimeType, String type) {
		super();
		this.filePath = filePath;
		this.id = id;
		this.size = size;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.type = type;
	}
	
	public static JSONObject Parse(FileInfo info) {
		return Parse(info.getId(), info.getFileName(), info.getFilePath(), info.getSize(), info.getMimeType(), info.getType());
	}

	public static JSONObject Parse(long id, String fileName, String filePath, long size, String mimeType, String type){
		JSONObject json = new JSONObject();
		
		try {
			json.put("id", id);
			
			if(fileName == null){
				json.put("name", "");
			}else{
				json.put("name", fileName);				
			}
			
			if(filePath == null){
				json.put("path", "");
			}else{
				json.put("path", filePath);		
			}

			if(mimeType == null){
				json.put("mime_type", "");
			}else{
				json.put("mime_type", mimeType);
			}
			
			if (type == null) {
				json.put("type", "");
			} else {
				json.put("type", type);
			}
			
			json.put("size", size);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

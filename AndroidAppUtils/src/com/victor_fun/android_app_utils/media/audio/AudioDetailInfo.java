package com.victor_fun.android_app_utils.media.audio;

import org.json.JSONException;
import org.json.JSONObject;

import com.victor_fun.android_app_utils.media.MediaDetailInfo;


public class AudioDetailInfo extends MediaDetailInfo {
	private int track;
	private String album;
	private int duration;
	private String artist;
	private int year;
	private String genre;
	private int bitrate;
	private int frequency;
	private int channels;

	public AudioDetailInfo(long id, String name, long size, long modifiedDate,
			String path) {
		this(id, name, size, modifiedDate, path, 0, null, 0, null, 0, null, 0, 0, 0);
	}

	public AudioDetailInfo() {
		this(0, null, 0, 0, null, 0, null, 0, null, 0, null, 0, 0, 0);
	}
	
	// TODO need to use factory function to instead
	public AudioDetailInfo(long id, String name, long size, long modifiedDate,
			String path, int track, String album, int duration, String artist,
			int year, String genre, int bitrate, int frequency, int channels) {
		super(id, name, size, modifiedDate, path);
		this.track = track;
		this.album = album;
		this.duration = duration;
		this.artist = artist;
		this.year = year;
		this.genre = genre;
		this.bitrate = bitrate;
		this.frequency = frequency;
		this.channels = channels;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public static JSONObject Parse(AudioDetailInfo info) {
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
			
			json.put("track", info.getTrack());
			
			if(info.getAlbum() == null){
				json.put("album", "");
			}else{
				json.put("album", info.getAlbum());				
			}
			
			json.put("duration", info.getDuration());
			
			if(info.getArtist() == null){
				json.put("artist", "");
			}else{
				json.put("artist", info.getArtist());
			}
			
			json.put("year", info.getYear());
			
			if(info.getGenre() == null){
				json.put("genre", "");				
			}else{
				json.put("genre", info.getGenre());	
			}
			
			json.put("bitrate", info.getBitrate());
			json.put("frequency", info.getFrequency());
			json.put("channels", info.getChannels());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}

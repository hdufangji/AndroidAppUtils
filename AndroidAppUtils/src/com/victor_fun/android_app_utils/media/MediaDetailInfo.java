package com.victor_fun.android_app_utils.media;

public abstract class MediaDetailInfo {
	protected long id;
	protected String name;
	protected long size;
	protected long modifiedDate;
	protected String path;
	
	public MediaDetailInfo(long id, String name, long size, long modifiedDate,
			String path) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.modifiedDate = modifiedDate;
		this.path = path;
	}

	public MediaDetailInfo() {
		this(0, null, 0, 0, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

package com.victorFun.androidapputils.file;

import android.os.FileObserver;
import android.util.Log;

public class SimpleFileObserver extends FileObserver {
	static final String LOG_TAG = SimpleFileObserver.class.getSimpleName();
	
	public static int CHANGES_ONLY = CLOSE_WRITE | MOVE_SELF | MOVED_FROM | MODIFY | CREATE | DELETE;
	String mPath;
	int mMask;
	    
	public SimpleFileObserver(String path) {
        this(path, CHANGES_ONLY);
    }
    
    public SimpleFileObserver(String path, int mask) {
        super(path, mask);
        mPath = path;
        mMask = mask;
    }
	
	@Override
	public void onEvent(int event, String path) {
		Log.e(LOG_TAG, path + event);
		if(mListener != null){
        	mListener.onFileChanged(event, path);        	
        }
	}

	onFileChangedListener mListener;

	public void setOnFileChangedListener(onFileChangedListener mListener) {
		this.mListener = mListener;
	}

	public interface onFileChangedListener {
		public void onFileChanged(int event, String path);
	}
}

package com.victor_fun.android_app_utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.os.FileObserver;
import android.util.Log;

/**
 * RecursiveFileObserver is used to monitor folder/file changes.
 * @author VFang
 *
 */
public class RecursiveFileObserver {
	static final String LOG_TAG = RecursiveFileObserver.class.getSimpleName();

    public static int CHANGES_ONLY = FileObserver.CLOSE_WRITE | FileObserver.MOVE_SELF | FileObserver.MOVED_FROM | 
    									FileObserver.MODIFY | FileObserver.CREATE | FileObserver.DELETE;
    
    List<SingleFileObserver> mObservers;
    String mPath;
    int mMask;
    
    onFileChangedListener mListener;
    
    public void setOnFileChangedListener(onFileChangedListener mListener) {
		this.mListener = mListener;
	}

	public RecursiveFileObserver(String path) {
        this(path, CHANGES_ONLY);
    }
    
    public RecursiveFileObserver(String path, int mask) {
        mPath = path;
        mMask = mask;
    }

    public void startWatching() {
        if (mObservers != null) return;
        mObservers = new ArrayList<SingleFileObserver>();
        Stack<String> stack = new Stack<String>();
        stack.push(mPath);
        
        while (!stack.empty()) {
            String parent = stack.pop();
            mObservers.add(new SingleFileObserver(parent, mMask));
            File path = new File(parent);
            File[] files = path.listFiles();
            if (files == null) continue;
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory() && !files[i].getName().equals(".")
                    && !files[i].getName().equals("..")) {
                    stack.push(files[i].getPath());
                }
            }
        }
        for (int i = 0; i < mObservers.size(); i++)
            mObservers.get(i).startWatching();
    }
    
    public void stopWatching() {
        if (mObservers == null) return;
        
        for (int i = 0; i < mObservers.size(); ++i)
            mObservers.get(i).stopWatching();

        mObservers.clear();
        mObservers = null;
    }
    
    public void onEvent(int event, String path) {
    	Log.d(LOG_TAG, path);
        if(mListener != null){
        	mListener.onFileChanged(event, path);        	
        }
    }
    
    private class SingleFileObserver extends FileObserver {
        private String mPath;

        public SingleFileObserver(String path, int mask) {
            super(path, mask);
            mPath = path;
        }
        
        @Override
        public void onEvent(int event, String path) {
            String newPath = mPath + "/" + path;
            RecursiveFileObserver.this.onEvent(event, newPath);
        } 
        
    }
    
    public interface onFileChangedListener{
    	public void onFileChanged(int event, String path);
    }
}

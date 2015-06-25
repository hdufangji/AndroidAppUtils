package com.victor_fun.android_app_utils.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.os.FileObserver;
import android.util.Log;

import com.victor_fun.android_app_utils.utils.PathUtil;

/**
 * RecursiveFileObserver is used to monitor folder/file changes.
 * @author VFang
 *
 */
public class RecursiveFileObserver {
	static final String LOG_TAG = RecursiveFileObserver.class.getSimpleName();

	//CHANGES_ONLY doesn't include FileObserver.MODIFIED, because we can receive FileObserver.CLOSE_WRITE after file modified.
    public static int CHANGES_ONLY = FileObserver.CLOSE_WRITE | FileObserver.CREATE | FileObserver.MOVED_TO |
    									FileObserver.MOVED_FROM | FileObserver.DELETE | FileObserver.DELETE_SELF;
    
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
        for (int i = 0; i < mObservers.size(); i++){
            mObservers.get(i).startWatching();
            Log.d(LOG_TAG, mObservers.get(i).mPath + " start watching!");
        }
    }
    
    public void stopWatching() {
        if (mObservers == null) return;
        
        for (int i = 0; i < mObservers.size(); ++i){
            mObservers.get(i).stopWatching();
            Log.d(LOG_TAG, mObservers.get(i).mPath + " stop watching!");
        }
            
        mObservers.clear();
        mObservers = null;
    }
    
    public void onEvent(int event, String path) {
        if(mListener != null){
        	mListener.onFileChanged(event, path);        	
        }
    }
    
    public interface onFileChangedListener{
    	public void onFileChanged(int event, String path);
    }
    
    private class SingleFileObserver extends FileObserver {
        private String mPath;

        public SingleFileObserver(String path, int mask) {
            super(path, mask);
            mPath = path;
        }
        
        @Override
        public void onEvent(int event, String path) {
            path = mPath + "/" + path;
            event = event&FileObserver.ALL_EVENTS;
            Log.d(LOG_TAG, String.format("path = %s, event = %d", path, event));
            
            //notice: Now we assume that create a folder at one time.
            // if we create several files at same time, we need to change the implementation.
            if (monitorIsNewFolderCreated(event, path)) {
            	return;
            }
            
            RecursiveFileObserver.this.onEvent(event, path);
        } 
        
        private boolean monitorIsNewFolderCreated(int event, String path){
			boolean flag = PathUtil.isFolder(path);
			// Log.d(LOG_TAG, path + (flag ? " is a folder!" : " is not a folder!"));
			if (flag) {
				if ((FileObserver.CREATE & event) != 0) {
					mCurrentMoveToFolder = new MonitorFolderCreate(path);
					mCurrentMoveToFolder.receiveCreateEvent();
					return true;
				} else if ((FileObserver.MOVED_TO & event) != 0) {
					mCurrentMoveToFolder.receiveMoveToEvent(path);
					mCurrentMoveToFolder = null;
					return true;
				}
			} else {
				if ((FileObserver.MOVED_FROM & event) != 0 && path.equals(mCurrentMoveToFolder.path)) {
					return true;
				}
			}
			
			return false;
        }
        
        private MonitorFolderCreate mCurrentMoveToFolder = null;
        class MonitorFolderCreate {
        	SingleFileObserver newObserver;
        	String path;
        	
        	MonitorFolderCreate (String path) {
        		this.path = path;
        	}
        	
        	void receiveCreateEvent(){
				newObserver = new SingleFileObserver(path, CHANGES_ONLY);
				mObservers.add(newObserver);
				Log.d(LOG_TAG, path + " start watching!");
				newObserver.startWatching();
        	}
        	
        	void receiveMoveToEvent(String newPath){
        		if (newObserver != null) {
        			Log.d(LOG_TAG, "stop watching old path:" + this.path);
        			newObserver.stopWatching();
        			newObserver = null;
        		}
        		newObserver = new SingleFileObserver(newPath, CHANGES_ONLY);
        		mObservers.add(newObserver);
        		Log.d(LOG_TAG, newPath + " start watching!");
        		newObserver.startWatching();
        	}
        }
    }
    
}
